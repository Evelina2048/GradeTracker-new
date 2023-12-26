package src.view;

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

public class Gather {
    private JFrame window;
    private String selectedText;
    private boolean moveOnPossible = false;
    private int windowX;
    private int windowY;
    JRadioButton studentButton;
    JRadioButton teacherButton;
    ButtonGroup teacherStudentGroup;
    int windowWidth = 800;
    int windowHeight = 500;

    public Gather(String selectedButtonText, int windowX, int windowY) {
        windowSetUp(windowX, windowY);

        instructionsWordsWindow(selectedButtonText);

        inputName();

        buttonSetUp(selectedButtonText);
    }


    private void windowSetUp(int windowX, int windowY) {
        //window set up
        window = new JFrame();
        window.setTitle("New User?");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setSize(800, 500);
        window.setLocation(windowX, windowY);
        showWindow();
    }
    
    private void instructionsWordsWindow(String selectedButtonText) {
        JPanel instructionsPanel;
        JLabel instructionsWords;
        //instructions (north section for borderlayout)
        System.out.println(selectedButtonText+"hhhhh");
        if (selectedButtonText == "New User") {
            instructionsWords = new JLabel("You are a new user. Create a user name.");
        }
        else if (selectedButtonText == "Existing") {
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
        JPanel choicesPanel;
        teacherStudentGroup = new ButtonGroup();
        Color choicesPanelColor = Color.decode("#AFA2A2");

        choicesPanel= new JPanel(new GridBagLayout());
        choicesPanel.setBackground(choicesPanelColor);

        //need to add move on possible

        ///
        // Create a JTextField
        JTextField textField = new JTextField(10); // 20 columns wide
        textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, 50)); // Set the height to 50 pixels
        textField.setFont(new Font("Roboto", Font.BOLD, 14));
        // Add the text field to the panel
        choicesPanel.add(textField);

        window.add(choicesPanel);
        ///

        window.add(choicesPanel);
    }

    private void buttonSetUp(String selectedButtonText) {
        JButton backButton;
        JButton nextButton;
        JPanel backNextButtonsPanel;
        //buttons
        backNextButtonsPanel = new JPanel(new BorderLayout());
        backButton = new JButton("< Back");
        backNextButtonsPanel.add(backButton, BorderLayout.WEST);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NewUser newUser = new NewUser(selectedText, windowX, windowY);
                newUser.showWindow(window.getX(), window.getY());
                newUser.setButtonSelected(selectedButtonText);
                hideWindow();
            }
        });
        
        //next
        nextButton = new JButton("Next >");
        backNextButtonsPanel.add(nextButton, BorderLayout.EAST);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
        
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Frame2 next hit ");
                int windowX = window.getX();
                int windowY = window.getY();

                if (moveOnPossible) {
                    ///write to file
                    ///
                    Gather gatherFrame = new Gather(selectedText, windowX, windowY);
                    System.out.println(selectedText+"selectedstuff");
                    //window.setVisible(false);
                    window.dispose();
                }
                else if (!moveOnPossible) {
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
        //window.setLocationRelativeTo(null);
        window.setLocation(window.getX(), window.getY());
    }

    window.setVisible(true);
    }

    private void hideWindow() {
        window.setVisible(false);
    }

}