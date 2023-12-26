package src.view;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class NewUser {
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

    public NewUser(String selectedButtonText, int windowX, int windowY) {
        windowSetUp(windowX, windowY);

        instructionsWordsWindow(selectedButtonText);

        radioButtonSetUp();

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
        instructionsWords = new JLabel("You are a "+selectedButtonText+". Are you a new user?");
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

    private void radioButtonSetUp() {
        JPanel choicesPanel;
        teacherStudentGroup = new ButtonGroup();
        Color choicesPanelColor = Color.decode("#AFA2A2");

        choicesPanel= new JPanel(new GridBagLayout());
        choicesPanel.setBackground(choicesPanelColor);

        //initialize buttons with color
        teacherButton = new JRadioButton("Existing");
        choicesButtonDecorate(teacherButton);
        teacherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            selectedText = teacherButton.getText();
            moveOnPossible = true;
            }
            
        });

        studentButton = new JRadioButton("New User");
        choicesButtonDecorate(studentButton);
        studentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedText = studentButton.getText();
                moveOnPossible = true;
            }
        });

        addToChoicesPanel(teacherStudentGroup, teacherButton, studentButton, choicesPanel);

        window.add(choicesPanel);
    }

    private void choicesButtonDecorate(JRadioButton button) {
        Font buttonFont = new Font("Roboto", Font.PLAIN, 25); // Change the font and size here
        button.setForeground(Color.WHITE);
        button.setFont(buttonFont);
    }

    private void addToChoicesPanel(ButtonGroup teacherStudentGroup, JRadioButton teacherButton, JRadioButton studentButton, JPanel choicesPanel) {
        teacherStudentGroup.add(teacherButton);
        teacherStudentGroup.add(studentButton);
        choicesPanel.add(teacherButton);
        choicesPanel.add(studentButton);
        choicesPanel.add(teacherButton, choiceGbc());
    }

    private GridBagConstraints choiceGbc() {
        //radio buttons distance
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 0, 0, 0); // Increase the horizontal spacing between components
        return gbc;
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
                MainWindow main = new MainWindow();
                main.show(window.getX(),window.getY());
                main.setButtonSelected(selectedButtonText);
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
                    Gather gatherFrame = new Gather();
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

    public void setButtonSelected(String selectedButtonText) {
        selectedText = selectedButtonText;
        if (selectedButtonText == "New User") {
            studentButton.setSelected(true);
            moveOnPossible = true;
        }
        
        else if(selectedButtonText == "Existing") {
            teacherButton.setSelected(true);
            moveOnPossible = true;
        }
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

    public void showWindow() {
    if (windowX != 0 && windowY != 0) {
        window.setLocation(windowX, windowY);
        setWindowX(windowX);
        setWindowY(windowY);

    }

    else {
        window.setLocationRelativeTo(null);
    }

    window.setVisible(true);
    }

    private void hideWindow() {
        window.setVisible(false);
    }

}