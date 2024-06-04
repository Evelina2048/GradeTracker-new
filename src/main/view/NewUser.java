package main.view;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//key listening
import java.awt.event.KeyEvent;
public class NewUser extends JFrame {
    private JFrame window;
    private JFrame main;
    private String studentOrTeacher;
    private String existingOrNew;
    private boolean moveOnPossible = false;
    private Gather gatherFrame;
    private Set set;
    JRadioButton newUserButton;
    JRadioButton existingButton;
    ButtonGroup teacherStudentGroup;
    int windowWidth = 800;
    int windowHeight = 500;

    //panel
    JPanel instructionsPanel;
    JPanel choicesPanel;
    JPanel backNextButtonsPanel;

    Decorator decorator = new Decorator();

    public NewUser(Set set) {
        this.set = set;
       newUserSetup();
    }

    public void newUserSetup() {
        main = set.getWindow();
        studentOrTeacher = set.getStudentOrTeacher();
        main.setTitle("New User");
        this.window = main;

        instructionsWordsWindow();

        radioButtonSetUp();

        buttonSetUp();

        EnterAction enterAction = new EnterAction();

        window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        window.getRootPane().getActionMap().put("enterAction", enterAction);

        window.requestFocusInWindow();
    }
    
    private void instructionsWordsWindow() {
        //instructions (north section for borderlayout)
        JLabel instructionsWords = new JLabel("You are a "+studentOrTeacher+". Are you a new user?");
        instructionsPanel = decorator.InstructionsPanelDecorate(window, instructionsPanel, instructionsWords);
    }

    private void radioButtonSetUp() {
        teacherStudentGroup = new ButtonGroup();
        Color choicesPanelColor = Color.decode("#AFA2A2");

        choicesPanel= new JPanel(new GridBagLayout());
        choicesPanel.setBackground(choicesPanelColor);

        existingButton();

        newUserButton();

        addToChoicesPanel(teacherStudentGroup, existingButton, newUserButton, choicesPanel);

        window.add(choicesPanel);
    }

    private void existingButton() {
        //initialize buttons with color
        existingButton = new JRadioButton("Existing");
        choicesButtonDecorate(existingButton);
        existingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            existingOrNew = existingButton.getText();
            moveOnPossible = true;
            }
            
        });
    }

    private void newUserButton() {
        newUserButton = new JRadioButton("New User");
        choicesButtonDecorate(newUserButton);
        newUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                existingOrNew = newUserButton.getText();
                moveOnPossible = true;
            }
        });
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
        choicesPanel.add(existingButton, decorator.choiceGbc());
    }

    private void buttonSetUp() {
        JButton backButton;
        JButton nextButton;
        //buttons
        backNextButtonsPanel = new JPanel(new BorderLayout());
        backButton = new JButton("< Back");
        backNextButtonsPanel.add(backButton, BorderLayout.WEST);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                set.setExistingOrNew(existingOrNew);
                set.setWindow(window);
                decorator.hideWindow(instructionsPanel, choicesPanel, backNextButtonsPanel);     
                MainWindow main = (MainWindow) set.getWindow();
                main.MainWindowLaunch(set);
                main.setButtonSelected();
            }
        });
        
        //next
        nextButton = new JButton("Next >");
        backNextButtonsPanel.add(nextButton, BorderLayout.EAST);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
        
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doNextButtonProcedure();
                set.setExistingOrNew(existingOrNew);
            }
        });
    }

    private void doNextButtonProcedure(){
        int windowX = window.getX();
            int windowY = window.getY();
            if (moveOnPossible) {
                decorator.hideWindow(instructionsPanel, choicesPanel, backNextButtonsPanel);
                set.setExistingOrNew(existingOrNew);

                if (gatherFrame == null) {
                    // Create a new instance of Gather if it doesn't exist
                    gatherFrame = new Gather(set);
                    gatherFrame.gatherLaunch();
                    System.out.println("gatherframe is null");
                } else {
                    // Update the existing Gather window panels
                    gatherFrame.gatherLaunch();
                    gatherFrame.showWindow(windowX, windowY);  // Show the Gather window
                }    
            }
            else if (!moveOnPossible) {
                decorator.errorMessageSetUp(window, newUserButton);
            }            
    }

    public void setButtonSelected() {
        existingOrNew = set.getExistingOrNew().trim();
        if (existingOrNew == "New User") {
            newUserButton.setSelected(true);
            moveOnPossible = true;
        }
        
        else if(existingOrNew == "Existing") {
            existingButton.setSelected(true);
            moveOnPossible = true;
        }
    }

    public void showWindow(int windowX, int windowY) {
    if (windowX != 0 && windowY != 0) {
        window.setLocation(windowX, windowY);
        // decorator.setWindowX(windowX);
        // decorator.setWindowY(windowY);

    }

    else {
        window.setLocation(window.getX(), window.getY());
    }

    window.setVisible(true);
    }

    public class EnterAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            doNextButtonProcedure();
        }
    }
}
