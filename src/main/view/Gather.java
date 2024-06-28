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

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.BufferedReader;

import java.io.FileReader;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import main.model.Set;
import main.controller.Creator;
import main.controller.Decorator;
import main.controller.FileHandler;

import java.awt.event.KeyEvent;

//import class files

public class Gather {
    private JFrame window;
    private NewUser newUser;
    private int windowX;
    private int windowY;
    private AtomicBoolean textFieldEmptied = new AtomicBoolean(false); 
    private Set set;
    private Creator creator;
    private String existingOrNew;
    private String studentOrTeacher;
    
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

    public Gather() {
        this.set = Set.getInstance();
        whatToSetTextFieldTo();

        existingOrNew = set.getExistingOrNew();
        studentOrTeacher = set.getStudentOrTeacher();
        this.window = set.getWindow();
        creator = new Creator();
        newUser = new NewUser();

        //
        EnterAction enterAction = new EnterAction();
        window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        window.getRootPane().getActionMap().put("enterAction", enterAction);
        //
        gatherLaunch();
        

    }

    private void whatToSetTextFieldTo() {
        if (set.getUsername() == null) {
            textField = decorate.decorateTextBox("Enter user name");
        }
        else {
            textField = decorate.decorateTextBox(set.getUsername());
            textFieldMouseListener();
        }
    }

    private void textFieldMouseListener() {
        //check if file path to original username's class.txt exists and is not empty, if true,
        FileHandler fileHandler = new FileHandler();
        String filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+set.getUsername()+"/class.txt";
        if (fileHandler.fileExists(filePath) && fileHandler.fileIsNotEmpty(filePath)) {

        //add focus listener to textbox
        //decorate.removeFocusListeners(textField);
        // //
        set.setCanContinue(true);
        textField.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            //generic pop up message : "Editing the username will not carry over class information"
            System.out.println("focused");
        //     JDialog dialog = decorate.genericPopUpMessage("Editing the username will not carry over class information");
        //     dialog.setLocationRelativeTo(window);
        //     dialog.setLocation(dialog.getX(), dialog.getY() + 15); 
        //     dialog.setVisible(true);
            if (set.getCanContinue() == true ) {
                set.setCanContinue(false);
                window.requestFocusInWindow();
                decorate.areYouSureMessageDelete(textField, "editing username");

                //textField.requestFocus();
            } 
        
        }
           // decorate.areYouSureMessageSetUp(nextButton, textField, filePath);
        });
        }
        // //

    }

    public void gatherLaunch () {
        
        window.setTitle("Gather");
        this.window = set.getWindow();

        instructionsWordsWindow();
        
        inputName();

        buttonSetUpAction();

    }
    
    private void instructionsWordsWindow() {
        JLabel instructionsWordsLabel;
        //instructions (north section for borderlayout)
        if (existingOrNew == "New User") {
            instructionsWordsLabel = new JLabel("You are a new user. Create a user name.");
        }
        else if (existingOrNew == "Existing") {
            instructionsWordsLabel = new JLabel("You are an existing user. Type in your user name");
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

        textFieldEmptied.set(false);

        creator.textFieldFocusListener(textField, "Enter user name");

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
        backNextButtonsPanel = creator.makeBackNextButtonsPanel(backButtonPanel,saveButtonPanel, nextButtonPanel);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
    }

    private void makeBackButton() {
        JButton backButton = creator.backButtonCreate();
        backButtonPanel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               set.setUsername(textField.getText());
               backButtonAction();
            }
        });
    }

    private void makeSaveButton() {
        JButton saveButton = creator.saveButtonCreate();
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //writeToFile();
                //nextButtonAction(existingOrNew, studentOrTeacher);
            }
        });
    }

    private void makeNextButton() {
        nextButton = creator.nextButtonCreate();
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
        set.setUsername(textField.getText());
        System.out.println("nextbutton action in gather");
        nextButtonAction();
    }

    private void nextButtonAction() {
        boolean textFieldEmpty = textField.getText().trim().isEmpty();
        boolean textFieldHasntChanged = textField.getText().equals("Enter user name") && !textFieldEmptied.get();
        boolean textFieldFilled = textField.getText().trim().isEmpty() == false;
        //check if the username is not empty
        if (textFieldEmpty || textFieldHasntChanged) {
            errorMessageSetUp("<html><center>Please choose an option",200,90);
        }
        else if (textFieldFilled) { //good case
            String filePath = "somethingwentwrong";//if not overwritten, somethingwent wrong
            if (existingOrNew.trim().equals("New User")) { //if new user,
                goToStudentClasses(filePath);
            }
        }
        else {
            System.out.println("Something went wrong in username input");
            errorMessageSetUp("<html><center>Something went wrong in username input",200,90);
        }
    }

private void backButtonAction() {
    System.out.println("backbuttonaction for gather");
    hideWindow(); 
    newUser.newUserSetup();
    newUser.showWindow(window.getX(),window.getY());
    newUser.setButtonSelected();          
}

private void errorMessageSetUp(String labelWords, int width, int height) {
    JDialog dialog = new JDialog(window, true);
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

private void writeUsername(String filePath) {
    //and username not taken
    String usernamePath = "somethingwentwrong.txt";
    String username = textField.getText().trim();
    set.setUsername(username);
    if ("Student".equals(studentOrTeacher)) {
        usernamePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/studentUsername.txt";
    }

    else if ("Teacher".equals(studentOrTeacher)) {
        usernamePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/teacherUsername.txt";
    }

    checkIfExisting(usernamePath, username);
}

    private void checkIfExisting(String filePath, String username) {
        boolean usernametaken = false;

        readNames(filePath, username, usernametaken);
        if (usernametaken == false) {
            writeNewName(filePath, username);    
        }
    }

    private void writeNewName(String filePath, String username) {
            try (FileWriter writer = new FileWriter(filePath, true)) {
                writer.write(username + "\n");

            } catch (IOException e1) {
                e1.printStackTrace();
            }
    }

    private boolean readNames(String filePath, String username, Boolean usernametaken) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            readLine(reader, username, usernametaken);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return usernametaken;
    }

    private boolean readLine(BufferedReader reader, String username, boolean usernametaken){
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (line.equals(username) && set.getUsername() == null) {//if matches username
                    errorMessageSetUp("<html><center>Username already exists.<br> Please choose another.",200,100);
                    usernametaken = true;
                    break;
                }
            }
        } catch (IOException e) { 
            e.printStackTrace();
        } return usernametaken;
    }

    private void setWindowX(int newWindowX) {
        windowX = newWindowX;
    }

    public int getWindowX() {
        return windowX;
    }

    private void setWindowY(int newWindowY) {
        windowY = newWindowY;
    }

    public int getWindowY() {
        return windowY;
    }

    public void showWindow(int windowX, int windowY) {
    if (windowX != 0 && windowY != 0) {
        window.setLocation(windowX, windowY);
        setWindowX(windowX);
        setWindowY(windowY);

    }

    else {
        window.setLocation(window.getX(), window.getY());
    }

    window.setVisible(true);
    }

    private void hideWindow() {
        instructionsPanel.setVisible(false);
        choicesPanel.setVisible(false);
        backNextButtonsPanel.setVisible(false);
    }

    public void setTextToUsername() {
        String username = set.getUsername();
        textField.setText(username);
    }

    private void goToStudentClasses(String filePath) {
        writeUsername(filePath);
        //move on to studentclasses class
        hideWindow();
        StudentClasses studentClasses = new StudentClasses();
        studentClasses.studentClassesLaunch();
    }

    public class EnterAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            doNextButtonProcedure();
        }
    }
}