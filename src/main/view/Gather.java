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

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import com.opencsv.CSVWriter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;




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

    public Gather(JFrame window2, String studentOrTeacher, String existingOrNew, int windowX, int windowY) {
        window = window2;
        
        System.out.println("in gather frame, the studentOrTeacher is "+ studentOrTeacher);
        System.out.println("in gather frame, the existingOrNew is "+existingOrNew);

        studentOrTeacherString = studentOrTeacher;
        
        windowSetUp(windowX, windowY);

        instructionsWordsWindow(existingOrNew);

        inputName();

        buttonSetUp(existingOrNew);
    }


    private void windowSetUp(int windowX, int windowY) {
        //window set up
        //window = new JFrame();
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

        //need to add move on possible

        ///
        textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, 50)); // Set the height to 50 pixels
        textField.setFont(new Font("Roboto", Font.PLAIN, 14));
        textField.setForeground(Color.orange);

        //textField.setSelectionColor(Color.);
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

        //./
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField.setText("");
                textFieldEmptied = true;
                textField.setForeground(Color.BLACK);
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        //constraints.fill = GridBagConstraints.HORIZONTAL; // Allow horizontal expansion
        constraints.gridx = 0;
        constraints.gridy = 0;
        //constraints.weightx = 1.0; // Allow the component to grow horizontally

        choicesPanel.add(textField, constraints);
        window.add(choicesPanel);
        window.add(choicesPanel);
    }

    private void buttonSetUp(String existingOrNew) {
        JButton backButton;
        JButton nextButton;
        //buttons
        backNextButtonsPanel = new JPanel(new BorderLayout());
        backButton = new JButton("< Back");
        backNextButtonsPanel.add(backButton, BorderLayout.WEST);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("in new user, selectedText is"+selectedText);
                hideWindow();
                NewUser newUser = new NewUser(window, studentOrTeacherString, existingOrNew,windowX, windowY);
                //newUser.showWindow(window.getX(), window.getY());
                newUser.setButtonSelected(existingOrNew);

                
            }
        });
        
        //next
        nextButton = new JButton("Next >");
        backNextButtonsPanel.add(nextButton, BorderLayout.EAST);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
        
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int windowX = window.getX();
                int windowY = window.getY();
                System.out.println("textfieldemptied? "+textFieldEmptied+" textField.gettext " + textField.getText());
                System.out.println("2nd statement " + (textField.getText().trim() == "Enter user name"));
                System.out.println("3rd statement "+textField.getText().trim()+"Enter user name");

                //check if the username is not empty
                System.out.println("textfield "+textField.getText());
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
                        //and username not taken
                        if ("Student".equals(studentOrTeacherString)) {
                            filePath = "/Users/evy/Documents/Personal Projects/GradeTracker-new/src/main/view/Student.csv";
                            System.out.println("going to student.csv");
                        }

                        else if ("Teacher".equals(studentOrTeacherString)) {
                            filePath = "Teacher.csv";
                            System.out.println("going to teacher.csv");
                        }

                        String username = textField.getText();

                        System.out.println("the username is "+ username);
                        System.out.println("the filepath is " + filePath);

                        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, true))) { //write to (teacherOrStudent).csv file with:
                            String[] data = { username }; //username
                            writer.writeNext(data);
                        } catch (IOException e2) {
                            e2.printStackTrace(); 
                        }
                        //CSWriter write= new CSWriter(Writer);

                    }

                    //if existing, search for if this is truly existing in (teacherOrStudent).csv file
                    //then dont write

                }

                else {
                    moveOnPossible = false;
                    System.out.println("is else");
                    errorMessageSetUp();
                }
                
            }
        });
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
        //window.setVisible(false);
    }

}