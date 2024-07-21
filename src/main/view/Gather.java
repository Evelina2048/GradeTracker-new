package main.view;

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

import main.view.student.StudentClasses;

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
import java.awt.event.FocusListener;

import main.model.Set;
import main.model.SetListeners;
import main.model.SetUserInformation;

import main.controller.CompositeActionListenerWithPriorities;
import main.controller.CreateButton;
import main.controller.Creator;
import main.controller.Decorator;
import main.controller.FileHandling;
import main.controller.FileWriting;

import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;


//import class files

public class Gather {
    private JFrame window;
    private NewUser newUser;
    private int windowX;
    private int windowY;
    private Set set;
    private SetUserInformation setUserInformation;

    private SetListeners setListeners;
    private Creator create;
    private FileWriting fileWrite = new FileWriting();
    private String existingOrNew;
    private String studentOrTeacher;

    private Boolean firstTimeInTextbox = true;
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
        this.setUserInformation = SetUserInformation.getInstance();

        this.setListeners = SetListeners.getInstance();
        this.actionPriorities = CompositeActionListenerWithPriorities.getInstance();
        actionPriorities.setCurrentClass("Gather Loading");

        existingOrNew = setUserInformation.getExistingOrNew();
        studentOrTeacher = setUserInformation.getStudentOrTeacher();
        pathToUsernameFolder = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + setUserInformation.getUsername();
        window = set.getWindow();
        create = new Creator();

        System.out.println();
        makeUsernameBox();
        gatherLaunch();
    }

    public void gatherLaunch () {
        actionPriorities.setCurrentClass("Gather Loading");
        //set.setCurrentClass("Gather");
        //actionPriorities.setCurrentClass("Gather"); //needs to be set here as well because if going between classes really quick on multiple threads, want to make sure actionPriorities has the right class. And using integers that represent view order for comparison logic in class
        
        window.setTitle("Gather");
        window = set.getWindow();

        instructionsWordsWindow();

        makeUsernameBox();
        
        inputName();

        buttonSetUpAction();

        EnterAction enterAction = new EnterAction();
        window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        window.getRootPane().getActionMap().put("enterAction", enterAction);
    }

    private void makeUsernameBox() {
        System.out.println("username: "+setUserInformation.getUsername()+"haschanged? "+set.getNewOrExistingChanged());
        if (setUserInformation.getUsername() == null && set.getNewOrExistingChanged() == false) {
            System.out.println("whatToSetTextFieldTo opt 1");
            textField = decorate.decorateTextBox("Enter user name");
        }
        else if (setUserInformation.getUsername() == null && set.getNewOrExistingChanged() == true) { //user came back to gather after changing newuser setting
            System.out.println("whatToSetTextFieldTo opt 2");
            textField = decorate.decorateTextBox("Enter user name");
        }

        else {
            System.out.println("whatToSetTextFieldTo opt 3");
            textField = decorate.decorateTextBox(setUserInformation.getUsername());
            set.setLoadedState(textField, true);
            textFieldMouseListener();
        }
    }

    private void textFieldMouseListener() {
        FocusAdapter textfieldFocusListener;
         // Check if file path to original username's class.txt exists and is not empty
         FileHandling fileHandler = new FileHandling();
         String filePath = pathToUsernameFolder + "/class.txt";
         if (fileHandler.fileExists(filePath) && fileHandler.fileIsNotEmpty(filePath)) {
            textfieldFocusListener = new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    System.out.println("focuslistener#5");
                        window.requestFocusInWindow();
                        decorate.areYouSureMessageListenerForEditingUsername();
                        firstTimeInTextbox = false;
                        
                }
            };

            setListeners.setDialogFocusListener(textfieldFocusListener);

            decorate.deleteFocusListeners(textField.getFocusListeners().length-2);

            textField.addFocusListener(textfieldFocusListener);

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
        Boolean didNotChangeUsernameFromDefault = (textField.getText().trim().equals("Enter user name")) && (set.getEmptiedState(textField)==false);
    
        if (newUser && (username == null || didNotChangeUsernameFromDefault) && previousSettingsNotChanged) {
            System.out.println("instruction words option 1");
            instructionsWordsLabel = new JLabel("You are a new user. Create a user name.");
        }
        else if (newUser && username != null && previousSettingsNotChanged && fileHandler.folderExists(pathToUsernameFolder) && set.getEmptiedState(textField)){//readNames(pathToUsernameFolder, set.getUsername())){ //checkIfExisting(pathToUsernameFolder, set.getUsername())){//fileHandler.fileExists("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername())){//fileHandler.fileExists(pathToUsernameFolder)) { //file name exists
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
            System.out.println("instruction words option 9. new user: "+ newUser+" username: "+ username +" previoussettingsnotchanged: "+ previousSettingsNotChanged+ " fileExists? " +fileHandler.fileExists(pathToUsernameFolder));
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

        set.setEmptiedState(textField, false);

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
        currentClass = "Gather";
        actionPriorities.setCurrentClass("Gather");
    }

    private void makeBackButton() {
        JButton backButton = createButton.backButtonCreate();
        backButtonPanel.add(backButton);
        System.out.println(1111);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(2222);
                actionPriorities.setCurrentClass("Gather");
                actionPriorities.addClassActionListener(b -> {
                    setUserInformation.setExistingOrNew(existingOrNew);
                    backButtonAction();
                }, 2, "backButton",null, "Gather");
        }});

    }

        private void backButtonAction() {
            System.out.println("hidewindow1");
            hideWindow(); 
            NewUser newUser = new NewUser();
            newUser.newUserSetup();
            if (setUserInformation.getExistingOrNew() != null) {
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
            public void actionPerformed(ActionEvent e) {
                doNextButtonProcedure();
            }
        });
    }

    private void doNextButtonProcedure() {

        setUserInformation.setUsername(textField.getText());
        set.setWindow(window);
        System.out.println("nextbutton action in gather");
        nextButtonAction();
        fileWrite.writeFolderToFile();
    }

    private void nextButtonAction() {
        boolean textFieldEmpty = textField.getText().trim().isEmpty();
        boolean textFieldHasntChanged = textField.getText().equals("Enter user name") &&  !set.getEmptiedState(textField);
        boolean textFieldFilled = textField.getText().trim().isEmpty() == false;
        //check if the username is not empty
        System.out.println(textFieldEmpty || textFieldHasntChanged && set.getLoadedState(textField) == false);
        if (textFieldEmpty || textFieldHasntChanged && set.getLoadedState(textField) == false) {
            errorMessageSetUp("<html><center>Please choose an option",200,90);
        }
        else if (textFieldFilled) { //good case
            String filePath = "somethingwentwrong";//if not overwritten, somethingwent wrong
            if (existingOrNew.trim().equals("New User")) { //if new user,
                System.out.println("hidewindow2");
                hideWindow();
                MainWindow main = new MainWindow();
            }
        }
        else {
            System.out.println("Something went wrong in username input");
            errorMessageSetUp("<html><center>Something went wrong in username input",200,90);
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
        System.out.println("current class "+ currentClass);
        instructionsPanel.setVisible(false);
        choicesPanel.setVisible(false);
        backNextButtonsPanel.setVisible(false);
    }

    public class EnterAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            doNextButtonProcedure();
        }
    }
}