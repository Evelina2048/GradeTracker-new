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
import controller.Decorator;
import controller.FileHandling;
import controller.FileWriting;

import java.awt.event.KeyEvent;

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
        this.set = Set.getInstance();
        this.setState = SetState.getInstance();
        this.setUserInformation = SetUserInformation.getInstance();

        this.setListeners = SetListeners.getInstance();
        this.actionPriorities = CompositeActionListenerWithPriorities.getInstance();
        actionPriorities.setCurrentClass("Gather Loading");

        existingOrNew = setUserInformation.getExistingOrNew();
        pathToUsernameFolder = setUserInformation.getPathToUsernameFolder();
        window = set.getWindow();

        makeUsernameBox();
        gatherLaunch();

    }

    public void gatherLaunch () {
        actionPriorities.setCurrentClass("Gather Loading");
        window.setTitle("Gather");
        window = set.getWindow();

        instructionsWordsWindow();

        makeUsernameBox();

        inputName();

        buttonSetUpAction();

        currentClass = "Gather";
        actionPriorities.setCurrentClass(currentClass);

        EnterAction enterAction = new EnterAction();
        window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        window.getRootPane().getActionMap().put("enterAction", enterAction);
        currentClass = "Gather";
        actionPriorities.setCurrentClass(currentClass);
    }

    private void makeUsernameBox() {
        if (setUserInformation.getUsername() == null && set.getNewOrExistingChanged() == false) {
            textField =  decorate.decorateTextBox("Enter user name");
            setState.setLoadedState(textField, false);
            setState.setEmptiedState(textField, false);
        }
        else if (setUserInformation.getUsername() == null && set.getNewOrExistingChanged() == true) { //user came back to gather after changing newuser setting
            textField = decorate.decorateTextBox("Enter user name");
        }

        else {
            textField = decorate.decorateTextBox(setUserInformation.getUsername());
            setState.setLoadedState(textField, true);

            setState.setEmptiedState(textField, true);
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
                        window.requestFocusInWindow();
                        decorate.areYouSureMessageListenerForEditingUsername();
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
        Boolean didNotChangeUsernameFromDefault = (textField.getText().trim().equals("Enter user name")) && (setState.getEmptiedState(textField)==false);
        Boolean isExistingUser = fileHandler.folderExists(pathToUsernameFolder) && setState.getEmptiedState(textField);

        if (newUser && (username == null || didNotChangeUsernameFromDefault) && previousSettingsNotChanged) {
            instructionsWordsLabel = new JLabel("You are a new user. Create a user name.");
        }
        //should be below
        else if (newUser && username != null && previousSettingsNotChanged && isExistingUser){
            instructionsWordsLabel = new JLabel("<html><center>Welcome back!<br> Already created account. Click to edit.");
        }

        else if (existingUser && username != null && previousSettingsNotChanged) {
            instructionsWordsLabel = new JLabel("<html><center>Welcome back!");
        }

        else if (newUser && username == null && previousSettingsChanged) {
            instructionsWordsLabel = new JLabel("<html><center>You changed your NewUser/Existing settings. <br> You are a new user. Create a user name.");
        }

        else if (existingUser && previousSettingsNotChanged && didNotChangeUsernameFromDefault) {
            instructionsWordsLabel = new JLabel("You are an existing user. Type in your user name");
        }

        else if (existingUser && username == null && previousSettingsChanged) {
            instructionsWordsLabel = new JLabel("<html><center>You changed your NewUser/Existing settings. <br> You are an existing user. Type in your user name");
        }

        else if (newUser && username != null && previousSettingsChanged) {
            instructionsWordsLabel = new JLabel("<html><center>You changed your NewUser/Existing settings. <br> You are a new user. Create a user name.");
        }

        else if (existingUser && username != null && previousSettingsChanged) {
            instructionsWordsLabel = new JLabel("<html><center>You changed your NewUser/Existing settings. <br> You are an existing user. Type in your user name");
        }

        else {
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
            hideWindow();
            setUsername();
            NewUser newUser = new NewUser();
            newUser.newUserSetup();
            if (setUserInformation.getExistingOrNew() != null) {
                newUser.setButtonSelected();
            }
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
                currentClass = "Gather";
                actionPriorities.setCurrentClass(currentClass);
                actionPriorities.addClassActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { // remember this won't run if just enter without a click
                        doNextButtonProcedure();
                    }
                }, 1, "nextButton", null, currentClass);  // Add this ActionListener with priority 1
            }
        });
    }

    private void doNextButtonProcedure() {
        actionPriorities.setCurrentClass(currentClass);
        set.setWindow(window);
        nextButtonAction();
        setUsername();

        if (setUserInformation.getExistingOrNew().equals("New User") && !fileHandler.folderExists(pathToUsernameFolder)) {
            fileWrite.writeFolderToFile();
        }
    }

    private void nextButtonAction() {
        boolean textFieldEmpty = textField.getText().trim().isEmpty();
        boolean textFieldHasntChanged = textField.getText().equals("Enter user name") &&  !setState.getEmptiedState(textField);
        boolean textFieldFilled = textField.getText().trim().isEmpty() == false;
        //check if the username is not empty
        System.out.println(textFieldEmpty || textFieldHasntChanged && setState.getLoadedState(textField) == false);

        setUsername();

        if (textFieldEmpty || textFieldHasntChanged && setState.getLoadedState(textField) == false) {
            errorMessageSetUp("<html><center>Please choose a username",200,90);
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

        dialog.setLocationRelativeTo(textField);
        dialog.setVisible(true);
    }

    private void setUsername() {
        if (setState.getEmptiedState(textField)) {
            setUserInformation.setUsername(textField.getText());
            pathToUsernameFolder = setUserInformation.getPathToUsernameFolder();
        }
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
        @Override
        public void actionPerformed(ActionEvent e) {
            actionPriorities.setCurrentClass(currentClass);
            actionPriorities.addClassActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doNextButtonProcedure();
                }
            }, 1, "EnterAction", null, currentClass);  // Add this ActionListener with priority 1
        }
}
}
