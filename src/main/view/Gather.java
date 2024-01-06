package main.view;

//visual stuff
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;

//for reading csv
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.opencsv.CSVWriter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
//
import java.io.Writer;

public class Gather {
    private JFrame window;
    private String selectedText;
    private boolean moveOnPossible = false;
    private int windowX;
    private int windowY;
    private String studentOrTeacherString;
    private boolean textFieldEmptied;

    private NewUser newUser;

    JRadioButton studentButton;
    JRadioButton teacherButton;
    ButtonGroup teacherStudentGroup;
    int windowWidth = 800;
    int windowHeight = 500;
    // Create a JTextField
    JTextField textField = new JTextField(10); // 20 columns wide

    //panels
    JPanel instructionsPanel;
    JPanel choicesPanel;
    JPanel backNextButtonsPanel;



    public Gather(MainWindow main, NewUser newUser, String studentOrTeacher, String existingOrNew,int windowX, int windowY) {
        //window = window2; 
        //MainWindow main = main2;
       gatherLaunch(main, newUser, studentOrTeacher, existingOrNew);
    }

    public void gatherLaunch (MainWindow main, NewUser newUser, String studentOrTeacher, String existingOrNew) {
        main.setTitle("NEW USRE");
        this.window = main;

        instructionsWordsWindow(existingOrNew);
        
        inputName();

        buttonSetUp(main, newUser, existingOrNew, studentOrTeacher);

        //radioButtonSetUp();

        //buttonSetUp(main, studentOrTeacher);
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

        textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, 50)); // Set the height to 50 pixels
        textField.setFont(new Font("Roboto", Font.PLAIN, 14));
        textField.setForeground(Color.orange);

        Color defaultTextColor = Color.decode("#B0B0B0");
        textField.setSelectedTextColor(defaultTextColor);

        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setText("Enter user name");
        textFieldEmptied = false;

        KeyListener deleteKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    textFieldEmptied = true;
                    textField.removeKeyListener(this); // Remove the KeyListener
                }
            }
        };

        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField.setText("");
                textFieldEmptied = true;
                textField.setForeground(Color.BLACK);
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;

        choicesPanel.add(textField, constraints);
        window.add(choicesPanel);
        window.add(choicesPanel);
    }

    private void buttonSetUp(MainWindow main, NewUser newUser, String existingOrNew, String studentOrTeacher) {
        JButton backButton;
        JButton nextButton;
        //buttons
        backNextButtonsPanel = new JPanel(new BorderLayout());
        backButton = new JButton("< Back");
        backNextButtonsPanel.add(backButton, BorderLayout.WEST);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

               backButtonAction(main, newUser, studentOrTeacher, existingOrNew);

            }
        });
        
        //next
        nextButton = new JButton("Next >");
        backNextButtonsPanel.add(nextButton, BorderLayout.EAST);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
        
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextButtonAction(existingOrNew, studentOrTeacher);
            }
        });
    }

    //>//
    private void nextButtonAction(String existingOrNew, String studentOrTeacherString) {
        int windowX = window.getX(); 
        int windowY = window.getY();
        boolean textFieldEmpty = textField.getText().trim().isEmpty();
        boolean textFieldHasntChanged = textField.getText().equals("Enter user name") && textFieldEmptied == false;
        boolean textFieldFilled = textField.getText().trim().isEmpty() == false;

        //delete after testing
        checkIfExisting();
        //delete after testing

        //check if the username is not empty
        if (textFieldEmpty) {
            moveOnPossible = false;
            errorMessageSetUp();
        }

        else if (textFieldHasntChanged) {
            System.out.println("in special cas");
            moveOnPossible = false;
            errorMessageSetUp();
        }

        else if (textFieldFilled) {
            moveOnPossible = true;
            String filePath = "somethingwentwrong";//if not overwritten, somethingwent wrong
            System.out.println("neworexisting"+existingOrNew);
            if (existingOrNew.trim().equals("New User")) { //if new user,
                System.out.println("studentorteacherstring"+studentOrTeacherString);
                writeUsername(filePath,studentOrTeacherString);
            }
        }

        else {
            moveOnPossible = false;
            System.out.println("Something went wrong in username input");
            errorMessageSetUp();
        }
        
}
private void backButtonAction(MainWindow main, NewUser newUser, String studentOrTeacher, String existingOrNew) {

     newUser.newUserSetup(main, studentOrTeacher);
     newUser.showWindow(window.getX(),window.getY());
     newUser.setButtonSelected(existingOrNew);
     hideWindow(); 
 
            
}
private void errorMessageSetUp() {
    JDialog dialog = new JDialog(window, null, true);
    dialog.setLayout(new FlowLayout());
    JLabel label = new JLabel("<html><center>Please choose an option");
    label.setHorizontalAlignment(SwingConstants.CENTER);
    dialog.add(label);
    JButton okButton = new JButton("OK");
    okButton.setVisible(true);
    dialog.add(okButton);
    dialog.setSize(200,90);
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
    System.out.println("studentOrTeacher"+ studentOrTeacher);
    if ("Student".equals(studentOrTeacher)) {
        filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/student/Student.csv";
        System.out.println("going to student.csv");
    }

    else if ("Teacher".equals(studentOrTeacher)) {
        filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/Teacher.csv";
        System.out.println("going to teacher.csv");
    }

    String username = textField.getText().trim();

    System.out.println("the username is "+ username);
    System.out.println("the filepath is " + filePath);


    try (FileWriter writer = new FileWriter(filePath, true)) {
        checkIfExisting();
        int commaCountInt = commaCount(username);
        writer.write(username + "," + commaCountInt + "," + "\n");
    } catch (IOException e1) {
        e1.printStackTrace();
    }

}

private int commaCount(String username) {
    int commaCount = 0;
    for (int i = 0; i < username.length(); i++) {
        if (username.charAt(i) == ',') {
            commaCount++;
        }
    }
    System.out.println(commaCount);
    return commaCount;
}
    //>//


    private void choicesButtonDecorate(JRadioButton button) {
        Font buttonFont = new Font("Roboto", Font.PLAIN, 25); // Change the font and size here
        button.setForeground(Color.WHITE);
        button.setFont(buttonFont);
    }

    private void addToChoicesPanel(ButtonGroup teacherStudentGroup, JRadioButton existingButton, JRadioButton newUserButton, JPanel choicesPanel) {
        teacherStudentGroup.add(existingButton);
        teacherStudentGroup.add(newUserButton);
        choicesPanel.add(existingButton);
        choicesPanel.add(newUserButton);
        choicesPanel.add(existingButton, choiceGbc());
    }

    private GridBagConstraints choiceGbc() {
        //radio buttons distance
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 0, 0); // Increase the horizontal spacing between components
        return gbc;
    }

    private void checkIfExisting() {
        //sample
        String sample = "0, sampleuser";
        //>sample

        //loop through file to see if username in the first column
        System.out.println("in check if existing");

        //takes comma count then keep going until that number of commas+1 has been found 
        for (int i=0; i<sample.length(); i++) {
            System.out.println("i:"+sample.charAt(i));
        }
        //then take all that minus last character, thats the username

        //put that in an array
        //check if username there

        //if found, then give pop up

        //if not, write to file
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
}