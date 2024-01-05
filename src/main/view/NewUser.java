package main.view;

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

public class NewUser extends JFrame {
    private JFrame window;
    private MainWindow main;
    private String studentOrTeacher;
    private String existingOrNew;
    private boolean moveOnPossible = false;
    private int windowX;
    private int windowY;
    private Gather gatherFrame;
    JRadioButton newUserButton;
    JRadioButton existingButton;
    ButtonGroup teacherStudentGroup;
    int windowWidth = 800;
    int windowHeight = 500;

    //panel
    JPanel instructionsPanel;
    JPanel choicesPanel;
    JPanel backNextButtonsPanel;

    public NewUser(MainWindow main, String studentOrTeacher, String newOrExisting,int windowX, int windowY) {
        //window = window2;
        //MainWindow main = main2;
       newUserSetup(main, studentOrTeacher);
    }

    public void newUserSetup(MainWindow main2, String studentOrTeacherString) {
        main = main2;
        main.setTitle("NEW USE");
        this.window = main;
        studentOrTeacher = studentOrTeacherString;

        instructionsWordsWindow(studentOrTeacher);

        radioButtonSetUp();

        buttonSetUp(main, studentOrTeacher);

        // EnterAction enterAction = new EnterAction();

        // this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        // this.getRootPane().getActionMap().put("enterAction", enterAction);

        // this.requestFocusInWindow();
    }


    private void windowSetUp(int windowX, int windowY) {
        //window set up
        window.setTitle("New User?");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setSize(800, 500);
        window.setLocation(windowX, windowY);
        showWindow(windowX,windowY);
    }
    
    private void instructionsWordsWindow(String studentOrTeacher) {
        JLabel instructionsWords;
        //instructions (north section for borderlayout)
        instructionsWords = new JLabel("You are a "+studentOrTeacher+". Are you a new user?");
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
        teacherStudentGroup = new ButtonGroup();
        Color choicesPanelColor = Color.decode("#AFA2A2");

        choicesPanel= new JPanel(new GridBagLayout());
        choicesPanel.setBackground(choicesPanelColor);

        //initialize buttons with color
        existingButton = new JRadioButton("Existing");
        choicesButtonDecorate(existingButton);
        existingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            existingOrNew = existingButton.getText();
            moveOnPossible = true;
            }
            
        });

        newUserButton = new JRadioButton("New User");
        choicesButtonDecorate(newUserButton);
        newUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                existingOrNew = newUserButton.getText();
                moveOnPossible = true;
            }
        });

        addToChoicesPanel(teacherStudentGroup, existingButton, newUserButton, choicesPanel);

        window.add(choicesPanel);
    }

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

    private void buttonSetUp(MainWindow main, String studentOrTeacher) {
        JButton backButton;
        JButton nextButton;
        //buttons
        backNextButtonsPanel = new JPanel(new BorderLayout());
        backButton = new JButton("< Back");
        backNextButtonsPanel.add(backButton, BorderLayout.WEST);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main.MainWindowLaunch();
                main.show(window.getX(),window.getY());
                main.setButtonSelected(studentOrTeacher);
                main.setExistingOrNew(existingOrNew);
                hideWindow(); 
                
            }
        });
        
        //next
        nextButton = new JButton("Next >");
        backNextButtonsPanel.add(nextButton, BorderLayout.EAST);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
        
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doNextButtonProcedure();
            }
        });
    }

    private void doNextButtonProcedure(){
        //>/
        int windowX = window.getX();
            int windowY = window.getY();
            if (moveOnPossible) {
                hideWindow();

                //
                if (gatherFrame == null) {
                    // Create a new instance of Gather if it doesn't exist
                    gatherFrame = new Gather(main, NewUser.this, studentOrTeacher, existingOrNew, windowX, windowY);
                    gatherFrame.gatherLaunch(main, NewUser.this, studentOrTeacher, existingOrNew);
                    System.out.println("gatherframe is null");
                } else {
                    // Update the existing Gather window panels
                    gatherFrame.gatherLaunch(main, NewUser.this, studentOrTeacher, existingOrNew);
                    gatherFrame.showWindow(windowX, windowY);  // Show the Gather window
                }
                //
                
                
            }
            else if (!moveOnPossible) {
                errorMessageSetUp();
            }
                
        //>/
    }
    // private void call(MainWindow main, String studentOrTeacher) {
    //     Gather gatherFrame = new Gather(main, NewUser.this, studentOrTeacher, existingOrNew, windowX, windowY);
    //     gatherFrame.gatherLaunch(main, this, studentOrTeacher, existingOrNew);
                    
    // }

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
        
        dialog.setLocationRelativeTo(newUserButton);
        dialog.setLocation(dialog.getX(), dialog.getY() + 15); 
        dialog.setVisible(true);
    }

    public void setButtonSelected(String studentOrTeacher) {
        existingOrNew = studentOrTeacher;
        if (studentOrTeacher == "New User") {
            newUserButton.setSelected(true);
            moveOnPossible = true;
        }
        
        else if(studentOrTeacher == "Existing") {
            existingButton.setSelected(true);
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