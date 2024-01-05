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

    public Gather(MainWindow main, NewUser newUser, String studentOrTeacher, String existingOrNew, int windowX, int windowY) {
    }

    public void gatherLaunch(MainWindow main, NewUser newUser, String studentOrTeacher, String existingOrNew) {
        //this.newUser = newUser;
        
        // System.out.println("in gather frame, the studentOrTeacher is "+ studentOrTeacher);
        // System.out.println("in gather frame, the existingOrNew is "+existingOrNew);

        this.window = newUser;
        //gatherLaunch(main, newUser, studentOrTeacher, existingOrNew);

        studentOrTeacherString = studentOrTeacher;
        
        windowSetUp(window, windowX, windowY);

        instructionsWordsWindow(existingOrNew);

        inputName();

        buttonSetUp(main, newUser, existingOrNew, studentOrTeacher);




        //showWindow(windowX, window);
    }


    private void windowSetUp(JFrame window, int windowX, int windowY) {
        //window set up

        window.setTitle("Input Username");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setSize(800, 500);
        window.setLocation(windowX, windowY);
        showWindow();
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

               backButtonAction(main, studentOrTeacher);

            }
        });
        
        //next
        nextButton = new JButton("Next >");
        backNextButtonsPanel.add(nextButton, BorderLayout.EAST);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
        
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextButtonAction(existingOrNew);
            }
        });
    }

    private void nextButtonAction(String existingOrNew) {
            int windowX = window.getX();
            int windowY = window.getY();

            //check if the username is not empty
            if (textField.getText().trim().isEmpty()) {
                moveOnPossible = false;
                errorMessageSetUp();
            }

            else if (textField.getText().equals("Enter user name") && textFieldEmptied == false) {
                System.out.println("in special cas");
                moveOnPossible = false;
                errorMessageSetUp();
            }

            else if (textField.getText().trim().isEmpty() == false) {
                moveOnPossible = true;
                String filePath = "somethingwentwrong";//if not overwritten, somethingwent wrong
                if (existingOrNew.trim().equals("New User")) { //if new user,
                writeUsername(filePath);
                }

            }

            else {
                moveOnPossible = false;
                System.out.println("is else");
                errorMessageSetUp();
            }
            
    }
    private void backButtonAction(MainWindow main, String studentOrTeacher) {
        //1/4/24 5:14
        // System.out.println("in new user, selectedText is"+selectedText);
        // hideWindow();
        // NewUser newUser = new NewUser(new MainWindow(window), studentOrTeacherString, existingOrNew,windowX, windowY);
        // newUser.setButtonSelected(existingOrNew);
        //

         //♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡
         newUser.newUserSetup(main, studentOrTeacher);
         newUser.showWindow(window.getX(),window.getY());
         newUser.setButtonSelected(studentOrTeacher);
         newUser.setButtonSelected(studentOrTeacher);
         hideWindow(); 
         //♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡

        // public void actionPerformed(ActionEvent e) {
        //     main.MainWindowLaunch();
        //     main.show(window.getX(),window.getY());
        //     main.setButtonSelected(studentOrTeacher);
        //     main.setExistingOrNew(existingOrNew);
        //     hideWindow(); 

                
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

    private void writeUsername(String filePath) {
        //and username not taken
        if ("Student".equals(studentOrTeacherString)) {
            filePath = "/Users/evy/Documents/Personal Projects/GradeTracker-new/src/main/view/Student.csv";
            System.out.println("going to student.csv");
        }

        else if ("Teacher".equals(studentOrTeacherString)) {
            filePath = "/Users/evy/Documents/Personal Projects/GradeTracker-new/src/main/view/Teacher.csv";
            System.out.println("going to teacher.csv");
        }

        String username = textField.getText().trim();

        System.out.println("the username is "+ username);
        System.out.println("the filepath is " + filePath);


        try (FileWriter writer = new FileWriter(filePath, true)) {
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
    private void setWindowX(int newWindowX) {
        windowX = newWindowX;
    }

    private void setWindowY(int newWindowY) {
        windowY = newWindowY;
    }

    public void showWindow() {
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