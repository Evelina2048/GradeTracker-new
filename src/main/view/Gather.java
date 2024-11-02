package view;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import model.Set;
import model.SetState;
import model.SetListeners;
import model.SetUserInformation;

import controller.CompositeActionListenerWithPriorities;
import controller.CreateButton;
import controller.Creator;
import controller.Decorator;
import controller.FileHandling;
import controller.FileWriting;

import java.awt.event.KeyEvent;

//import class files

public class Gather {
    private JFrame window;
    private int windowX;
    private int windowY;
    private Set set;
    private SetState setState;
    private SetUserInformation setUserInformation;

    private SetListeners setListeners;
    private FileWriting fileWrite = new FileWriting();
    private String existingOrNew;
    private CreateButton createButton = new CreateButton();

    private String currentClass = "Gather Loading";
    JRadioButton studentButton;
    JRadioButton teacherButton;
    ButtonGroup teacherStudentGroup;

    JButton nextButton;
    int windowWidth = 800;
    int windowHeight = 500;

    Decorator decorate = new Decorator();
    JTextField textField;

    //panels
    JPanel instructionsPanel;
    JPanel choicesPanel;
    JPanel backNextButtonsPanel;

    JPanel backButtonPanel;
    JPanel nextButtonPanel;
    JPanel saveButtonPanel;
    String pathToUsernameFolder;
    FileHandling fileHandler = new FileHandling();
    CompositeActionListenerWithPriorities actionPriorities;

    public Gather() {
        System.out.println("entering gather");
        this.set = Set.getInstance();
        this.setState = SetState.getInstance();
        this.setUserInformation = SetUserInformation.getInstance();

        this.setListeners = SetListeners.getInstance();
        this.actionPriorities = CompositeActionListenerWithPriorities.getInstance();
        actionPriorities.setCurrentClass("Gather Loading");

        existingOrNew = setUserInformation.getExistingOrNew();
        setUserInformation.getStudentOrTeacher();
        pathToUsernameFolder = setUserInformation.getPathToUsernameFolder();
        window = set.getWindow();

        //System.out.println("1111 "+fileHandler.folderExists(pathToUsernameFolder)+ " usernamepath "+ pathToUsernameFolder + " quote on quote username "+(setUserInformation.getUsername()==null)+(setUserInformation.getUsername()=="null"));
        makeUsernameBox();
        gatherLaunch();

    }

    public void gatherLaunch () {
        actionPriorities.setCurrentClass("Gather Loading");

        //System.out.println(" 2222"+fileHandler.folderExists(pathToUsernameFolder)+" quote on quote username "+(setUserInformation.getUsername()==null));

        window.setTitle("Gather");
        window = set.getWindow();

        instructionsWordsWindow();

        makeUsernameBox();

        inputName();

        buttonSetUpAction();

        //System.out.println("3333 "+fileHandler.folderExists(pathToUsernameFolder)+" quote on quote username "+(setUserInformation.getUsername()==null));

        currentClass = "Gather";
        actionPriorities.setCurrentClass(currentClass);

        EnterAction enterAction = new EnterAction();
        window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        window.getRootPane().getActionMap().put("enterAction", enterAction);
        currentClass = "Gather";
        actionPriorities.setCurrentClass(currentClass);

        //System.out.println("4444 "+fileHandler.folderExists(pathToUsernameFolder)+" quote on quote username "+(setUserInformation.getUsername()==null));
    }

    private void makeUsernameBox() {
        System.out.println("username "+setUserInformation.getUsername());
        System.out.println("username: "+setUserInformation.getUsername()+"haschanged? "+set.getNewOrExistingChanged());
        if (setUserInformation.getUsername() == null && set.getNewOrExistingChanged() == false) {
            System.out.println("whatToSetTextFieldTo opt 1");
            textField =  decorate.decorateTextBox("Enter user name");
            setState.setLoadedState(textField, false);
            setState.setEmptiedState(textField, false);
        }
        else if (setUserInformation.getUsername() == null && set.getNewOrExistingChanged() == true) { //user came back to gather after changing newuser setting
            System.out.println("whatToSetTextFieldTo opt 2");
            textField = decorate.decorateTextBox("Enter user name");
        }

        else {
            System.out.println("whatToSetTextFieldTo opt 3 username: "+setUserInformation.getUsername());
            textField = decorate.decorateTextBox(setUserInformation.getUsername());
            setState.setLoadedState(textField, true);

            setState.setEmptiedState(textField, true);
            textFieldMouseListener();
        }
    }

    private void textFieldMouseListener() {
        //System.out.println(" 5555"+fileHandler.folderExists(pathToUsernameFolder));
        FocusAdapter textfieldFocusListener;
        // Check if file path to original username's class.txt exists and is not empty
        FileHandling fileHandler = new FileHandling();
        String filePath = pathToUsernameFolder + "/class.txt";
        if (fileHandler.fileExists(filePath) && fileHandler.fileIsNotEmpty(filePath)) {
            textfieldFocusListener = new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                        window.requestFocusInWindow();
                        decorate.areYouSureMessageListenerForEditingUsername();
                }
            };

            setListeners.setDialogFocusListener(textfieldFocusListener);

            decorate.deleteFocusListeners(textField.getFocusListeners().length-2);

            textField.addFocusListener(textfieldFocusListener);

            //System.out.println("6666 "+fileHandler.folderExists(pathToUsernameFolder));

        }
    }

    private void instructionsWordsWindow() {
        JLabel instructionsWordsLabel;
        String username = setUserInformation.getUsername();
        Boolean newUser = (existingOrNew == "New User");
        Boolean existingUser = (existingOrNew == "Existing");
        Boolean previousSettingsNotChanged = (set.getNewOrExistingChanged() == false);
        Boolean previousSettingsChanged = (set.getNewOrExistingChanged() == true);
        System.out.println("{}"+textField.getText()+"{}");
        Boolean didNotChangeUsernameFromDefault = (textField.getText().trim().equals("Enter user name")) && (setState.getEmptiedState(textField)==false);
        Boolean isExistingUser = fileHandler.folderExists(pathToUsernameFolder) && setState.getEmptiedState(textField);

        if (newUser && (username == null || didNotChangeUsernameFromDefault) && previousSettingsNotChanged) {
            System.out.println("instruction words option 1");
            instructionsWordsLabel = new JLabel("You are a new user. Create a user name.");
        }
        //should be below
        else if (newUser && username != null && previousSettingsNotChanged && isExistingUser){
            System.out.println("instruction words option 2");
            instructionsWordsLabel = new JLabel("<html><center>Welcome back!<br> Already created account. Click to edit.");
        }

        else if (existingUser && username != null && previousSettingsNotChanged) {
            System.out.println("instruction words option 3");
            instructionsWordsLabel = new JLabel("<html><center>Welcome back!");
        }

        else if (newUser && username == null && previousSettingsChanged) {
            System.out.println("instruction words option 4");
            instructionsWordsLabel = new JLabel("<html><center>You changed your NewUser/Existing settings. <br> You are a new user. Create a user name.");
        }

        else if (existingUser && previousSettingsNotChanged && didNotChangeUsernameFromDefault) {
            System.out.println("instruction words option 5");
            instructionsWordsLabel = new JLabel("You are an existing user. Type in your user name");
        }

        else if (existingUser && username == null && previousSettingsChanged) {
            System.out.println("instruction words option 6");
            instructionsWordsLabel = new JLabel("<html><center>You changed your NewUser/Existing settings. <br> You are an existing user. Type in your user name");
        }

        else if (newUser && username != null && previousSettingsChanged) {
            System.out.println("instruction words option 7");
            instructionsWordsLabel = new JLabel("<html><center>You changed your NewUser/Existing settings. <br> You are a new user. Create a user name.");
        }

        else if (existingUser && username != null && previousSettingsChanged) {
            System.out.println("instruction words option 8");
            instructionsWordsLabel = new JLabel("<html><center>You changed your NewUser/Existing settings. <br> You are an existing user. Type in your user name");
        }

        else {
            System.out.println("instruction words option 9. new user: "+ newUser+" username: "+ username +" previoussettingsnotchanged: "+ previousSettingsNotChanged+ " fileExists? " +fileHandler.folderExists(pathToUsernameFolder));
            instructionsWordsLabel = new JLabel("Error");
        }
        decorateInstructions(instructionsWordsLabel);
    }

    private void decorateInstructions(JLabel instructionsWordsLabel) {
        instructionsPanel= new JPanel();
        Color instructionsColor = Color.decode("#7A6D6D");
        instructionsPanel.setBackground(instructionsColor);

        instructionsPanel.add(instructionsWordsLabel);
        Color instructionsWordsColor = Color.decode("#edebeb");
        instructionsWordsLabel.setForeground(instructionsWordsColor); //color
        window.add(instructionsPanel, BorderLayout.NORTH);

        //set the font for instructions
        Font instructionsFont = new Font("Roboto", Font.PLAIN, 30); // Change the font and size here
        instructionsWordsLabel.setFont(instructionsFont);
    }

    private void inputName() {
        teacherStudentGroup = new ButtonGroup();
        Color choicesPanelColor = Color.decode("#AFA2A2");

        choicesPanel= new JPanel(new GridBagLayout());
        choicesPanel.setBackground(choicesPanelColor);

        setState.setEmptiedState(textField, false);

        makeUsernameBox();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;

        choicesPanel.add(textField, constraints);
        window.add(choicesPanel);
        window.add(choicesPanel);
    }

    private void buttonSetUpAction() {
        backButtonPanel = new JPanel();
        saveButtonPanel = new JPanel(new BorderLayout());
        nextButtonPanel = new JPanel();

        makeBackButton();

        makeSaveButton();

        makeNextButton();
        backNextButtonsPanel = createButton.makeBackNextButtonsPanel(backButtonPanel,saveButtonPanel, nextButtonPanel);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
    }

    private void makeBackButton() {
        JButton backButton = createButton.backButtonCreate();
        backButtonPanel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setUsername();
                actionPriorities.setCurrentClass(currentClass);
                actionPriorities.addClassActionListener(b -> {
                    setUserInformation.setExistingOrNew(existingOrNew);
                    backButtonAction();
                }, 2, "backButton",null, currentClass);
        }});

    }

        private void backButtonAction() {
            System.out.println("hidewindow1");
            hideWindow();
            NewUser newUser = new NewUser();
            newUser.newUserSetup();
            System.out.println("back button new user "+actionPriorities.getCurrentClass());
            if (setUserInformation.getExistingOrNew() != null) {
                System.out.println("about to select buttons in new user. actionlistener "+actionPriorities.getCurrentClass());
                newUser.setButtonSelected();
            }
    }

    private void makeSaveButton() {
        JButton saveButton = createButton.saveButtonCreate();
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    private void makeNextButton() {
        nextButton = createButton.nextButtonCreate();
        nextButtonPanel.add(nextButton);
        nextButtonAddActionListener();
    }

    private void nextButtonAddActionListener() {

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("7777 "+fileHandler.folderExists(pathToUsernameFolder));
                currentClass = "Gather";
                actionPriorities.setCurrentClass(currentClass);
                actionPriorities.addClassActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { // remember won't run if just enter without a click
                        System.out.println("enteraction");
                        System.out.println("8888 "+fileHandler.folderExists(pathToUsernameFolder));
                        doNextButtonProcedure();
                    }
                }, 1, "nextButton", null, currentClass);  // Add this ActionListener with priority 1
            }
        });
    }

    private void doNextButtonProcedure() {
        System.out.println("9999 "+fileHandler.folderExists(pathToUsernameFolder)+setUserInformation.getUsername()+" ");
        actionPriorities.setCurrentClass(currentClass);
        //setUsername(); //was here 10/31
        set.setWindow(window);
        System.out.println("nextbutton action in gather");
        System.out.println("10 10 10 10 "+fileHandler.folderExists(pathToUsernameFolder)+" quote on quote username "+setUserInformation.getUsername()+" "+(setUserInformation.getUsername()==null));
        nextButtonAction();
        System.out.println("11 11 11 11 "+fileHandler.folderExists(pathToUsernameFolder)+" quote on quote username "+setUserInformation.getUsername()+" "+(setUserInformation.getUsername()==null));
        setUsername();
        fileWrite.writeFolderToFile();
        System.out.println("12 12 12 12 "+fileHandler.folderExists(pathToUsernameFolder)+" quote on quote username "+setUserInformation.getUsername()+" "+(setUserInformation.getUsername()==null));
    }

    private void nextButtonAction() {
        System.out.println("10.5 10 10 10 "+fileHandler.folderExists(pathToUsernameFolder)+" quote on quote username "+setUserInformation.getUsername()+" "+(setUserInformation.getUsername()==null));
        boolean textFieldEmpty = textField.getText().trim().isEmpty();
        boolean textFieldHasntChanged = textField.getText().equals("Enter user name") &&  !setState.getEmptiedState(textField);
        boolean textFieldFilled = textField.getText().trim().isEmpty() == false;
        //check if the username is not empty
        System.out.println(textFieldEmpty || textFieldHasntChanged && setState.getLoadedState(textField) == false);
        System.out.println("oh hi!");

        setUsername();

        System.out.println("10.6 10 10 10 "+fileHandler.folderExists(pathToUsernameFolder)+" quote on quote username "+setUserInformation.getUsername()+" "+(setUserInformation.getUsername()==null));

        System.out.println("10.7 10 10 10 "+"textfieldfilled "+textFieldFilled+existingOrNew.trim().equals("New User")+(!fileHandler.folderExists(pathToUsernameFolder))+(setUserInformation.getUsername() != null)+pathToUsernameFolder);

        if (textFieldEmpty || textFieldHasntChanged && setState.getLoadedState(textField) == false) {
            errorMessageSetUp("<html><center>Please choose an option",200,90);
        }

        else if (textFieldFilled) { //good case
            if (existingOrNew.trim().equals("New User") && (!fileHandler.folderExists(pathToUsernameFolder))){ //&& (setUserInformation.getUsername() != null))){//usernameNotOnFile()) { //if new user,
                hideWindow();
                removeFromWindow();
                MainWindow main = new MainWindow();
                main.setButtonSelected();
            }

            else if (existingOrNew.trim().equals("New User") && (fileHandler.folderExists(pathToUsernameFolder))){ //if existing user
                errorMessageSetUp("<html><center>This accounts taken.<br>Please go back and select \"Existing\"<br>or pick a different username",250, 120);
            }

            else if (existingOrNew.trim().equals("Existing") && (fileHandler.folderExists(pathToUsernameFolder))){ //&& (setUserInformation.getUsername() != null))){//usernameNotOnFile()) { //if new user,
                hideWindow();
                removeFromWindow();
                MainWindow main = new MainWindow();
                main.setButtonSelected();
            }

            else if (existingOrNew.trim().equals("Existing") && (!fileHandler.folderExists(pathToUsernameFolder))){ //&& (setUserInformation.getUsername() != null))){//usernameNotOnFile()) { //if new user,
                errorMessageSetUp("<html><center>This account does not exist.<br>Please go back and select \"New User\"<br>or pick an existing username",250, 120);
            }
        }
        else {
            System.out.println("Something went wrong in username input");
            errorMessageSetUp("<html><center>Something went wrong in username input",200,90);
        }

        if (setUserInformation.getStudentOrTeacher() != null) {
        }
    }

    public void errorMessageSetUp(String labelWords, int width, int height) {
        JDialog dialog = new JDialog(window, true);
        dialog.setResizable(false);
        dialog.setLayout(new FlowLayout());
        JLabel label = new JLabel(labelWords);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(label);
        JButton okButton = new JButton("OK");
        okButton.setVisible(true);
        dialog.add(okButton);
        dialog.setSize(width,height);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });

        dialog.setLocationRelativeTo(studentButton);
        dialog.setLocation(dialog.getX(), dialog.getY() + 15);
        dialog.setVisible(true);
    }

    private void setUsername() {
        System.out.println("in set username "+setUserInformation.getUsername());
        if (setState.getEmptiedState(textField)) {
            setUserInformation.setUsername(textField.getText());
            pathToUsernameFolder = setUserInformation.getPathToUsernameFolder();
        }
        System.out.println("username after setting is"+setUserInformation.getUsername());
    }

    public int getWindowX() {
        return windowX;
    }

    public int getWindowY() {
        return windowY;
    }

    public void showWindow(int windowX, int windowY) {

    window.setVisible(true);
    }

    private void hideWindow() {
        System.out.println("current class in hidewindow"+ currentClass);
        instructionsPanel.setVisible(false);
        choicesPanel.setVisible(false);
        backNextButtonsPanel.setVisible(false);
    }

    private void removeFromWindow() {
        JFrame window = set.getWindow();
        window.remove(instructionsPanel);
        window.remove(choicesPanel);
        window.remove(backNextButtonsPanel);
    }

    public class EnterAction extends AbstractAction {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        //     doNextButtonProcedure();
        // }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("enter from gather");
            actionPriorities.setCurrentClass(currentClass);
            actionPriorities.addClassActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { // remember won't run if just enter without a click
                    System.out.println("enteraction");
                    System.out.println();
                    doNextButtonProcedure();
                }
            }, 1, "EnterAction", null, currentClass);  // Add this ActionListener with priority 1
        }
}
}
