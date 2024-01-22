package main.view;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
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

//import class files
import main.view.Creator;
import main.view.Set;


public class Gather {
    private JFrame window;
    private NewUser newUser;
    private boolean moveOnPossible = false;
    private int windowX;
    private int windowY;
    private AtomicBoolean textFieldEmptied = new AtomicBoolean(false); 
    private Set set;
    private Creator creator;
    
    JRadioButton studentButton;
    JRadioButton teacherButton;
    ButtonGroup teacherStudentGroup;
    int windowWidth = 800;
    int windowHeight = 500;

    Decorator decorate = new Decorator();
    JTextField textField = decorate.decorateTextBox("Enter user name");


    //panels
    JPanel instructionsPanel;
    JPanel choicesPanel;
    JPanel backNextButtonsPanel;



    public Gather(JFrame window2, NewUser newUser, String studentOrTeacher, String existingOrNew,int windowX, int windowY, Set set) {
        this.set = set;
        creator = new Creator(set);
        gatherLaunch(window2, newUser, studentOrTeacher, existingOrNew);

    }

    public void gatherLaunch (JFrame window2, NewUser newUser2, String studentOrTeacher, String existingOrNew) {

        window2.setTitle("NEW USRE");
        this.window = window2;
        newUser = newUser2;

        instructionsWordsWindow(existingOrNew);
        
        inputName();

        buttonSetUpAction(window2, newUser, existingOrNew, studentOrTeacher);

    }
    
    private void instructionsWordsWindow(String existingOrNew) {
        JLabel instructionsWords;
        //instructions (north section for borderlayout)
        if (existingOrNew == "New User") {
            instructionsWords = new JLabel("You are a new user. Create a user name.");
        }
        else if (existingOrNew == "Existing") {
            instructionsWords = new JLabel("You are an existing user. Type in your user name");
        }

        else {
            instructionsWords = new JLabel("Error");
        }

        instructionsPanel= new JPanel();
        Color instructionsColor = Color.decode("#7A6D6D");
        instructionsPanel.setBackground(instructionsColor);
        
        instructionsPanel.add(instructionsWords);
        Color instructionsWordsColor = Color.decode("#edebeb");
        instructionsWords.setForeground(instructionsWordsColor); //color
        window.add(instructionsPanel, BorderLayout.NORTH);
    
        //set the font for instructions
        Font instructionsFont = new Font("Roboto", Font.PLAIN, 30); // Change the font and size here
        instructionsWords.setFont(instructionsFont);
    }

    private void inputName() {
        teacherStudentGroup = new ButtonGroup();
        Color choicesPanelColor = Color.decode("#AFA2A2");

        choicesPanel= new JPanel(new GridBagLayout());
        choicesPanel.setBackground(choicesPanelColor);

        textFieldEmptied.set(false);

        creator.textFieldFocusListener(window, textField, "Enter user name");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;

        choicesPanel.add(textField, constraints);
        window.add(choicesPanel);
        window.add(choicesPanel);
    }

    private void buttonSetUpAction(JFrame window2, NewUser newUser, String existingOrNew, String studentOrTeacher) {
        JButton backButton = creator.backButtonCreate();
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               backButtonAction(window2, newUser, studentOrTeacher, existingOrNew);
            }
        });

        JButton saveButton = creator.saveButtonCreate();
        JPanel saveButtonPanel = new JPanel(new BorderLayout());
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //writeToFile();
                //nextButtonAction(existingOrNew, studentOrTeacher);
            }
        });
        
        JButton nextButton = creator.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextButtonAction(existingOrNew, studentOrTeacher);
            }
        });
        backNextButtonsPanel = creator.makeBackNextButtonsPanel(backButtonPanel,saveButtonPanel, nextButtonPanel);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
    }

    private void nextButtonAction(String existingOrNew, String studentOrTeacherString) {
        boolean textFieldEmpty = textField.getText().trim().isEmpty();
        boolean textFieldHasntChanged = textField.getText().equals("Enter user name") && !textFieldEmptied.get();
        boolean textFieldFilled = textField.getText().trim().isEmpty() == false;

        //check if the username is not empty
        if (textFieldEmpty) {
            moveOnPossible = false;
            errorMessageSetUp("<html><center>Please choose an option",200,90);
        }

        else if (textFieldHasntChanged) {
            System.out.println("in special cas");
            moveOnPossible = false;
            errorMessageSetUp("<html><center>Please choose an option",200,90);
        }

        else if (textFieldFilled) { //good case
            moveOnPossible = true;
            String filePath = "somethingwentwrong";//if not overwritten, somethingwent wrong
            if (existingOrNew.trim().equals("New User")) { //if new user,
                writeUsername(filePath,studentOrTeacherString);
                //move on to studentclasses class
                System.out.println("should move to studentClasses class");
                hideWindow();
                StudentClasses studentClasses = new StudentClasses(window, newUser, studentOrTeacherString, existingOrNew, set);
                studentClasses.studentClassesLaunch(window, newUser, studentOrTeacherString, existingOrNew, set);
            }
        }

        else {
            moveOnPossible = false;
            System.out.println("Something went wrong in username input");
            errorMessageSetUp("<html><center>Something went wrong in username input",200,90);
        }
        
}
private void backButtonAction(JFrame window2, NewUser newUser, String studentOrTeacher, String existingOrNew) {

     newUser.newUserSetup(window2, studentOrTeacher);
     newUser.showWindow(window.getX(),window.getY());
     newUser.setButtonSelected(existingOrNew);
     hideWindow(); 
 
            
}
private void errorMessageSetUp(String labelWords, int width, int height) {
    JDialog dialog = new JDialog(window, null, true);
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

private void writeUsername(String filePath, String studentOrTeacher) {
    //and username not taken
    String usernamePath = "somethingwentwrong.txt";
    String username = textField.getText().trim();
    set.setUsername(username);
    if ("Student".equals(studentOrTeacher)) {
        //filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+username+".txt";
        usernamePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/studentUsername.txt";
    }

    else if ("Teacher".equals(studentOrTeacher)) {
       // filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/Teacher.csv";
        usernamePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/teacherUsername.txt";
    }

    System.out.println("the username is "+ username);
    System.out.println("the filepath is " + filePath);


    checkIfExisting(usernamePath, username);
    //int commaCountInt = commaCount(username);
}

private int commaCount(String username) {
    int commaCount = 0;
    for (int i = 0; i < username.length(); i++) {
        if (username.charAt(i) == ',') {
            commaCount++;
        }
    }
    return commaCount;
}

    private void checkIfExisting(String filePath, String username) {
        boolean usernametaken = false;

        readNames(filePath, username, usernametaken);
        if (usernametaken == false) {
            writeNewName(filePath, username);    
        }
    }

    private void writeNewName(String filePath, String username) {
        System.out.println("writingtofile");
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
                reader.close();
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
        //textField.setEditable(false);
    }
}