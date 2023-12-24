package src.view;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
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
import javax.swing.JOptionPane;

public class MainWindow {
private JFrame window;
private String selectedText;
private boolean moveOnPossible = false;
JRadioButton teacherButton;
JRadioButton studentButton;

public MainWindow() {
    windowSetUp();

    instructionsWordsWindow();

    RadiobuttonSetUp();

    backNextButton();
}


private void windowSetUp() {
    //window set up
    window = new JFrame();
    window.setTitle("Launcher");
    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    window.setLayout(new BorderLayout());
    window.setSize(800, 500);
    window.setLocationRelativeTo(null);
}
 
private void instructionsWordsWindow() {
    JPanel instructionsPanel;
    JLabel instructionsWords;
    //instructions (north section for borderlayout)
    instructionsWords = new JLabel("Welcome! Are you a student or a teacher?");
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

private void RadiobuttonSetUp() {
    ButtonGroup teacherStudentGroup;
    JPanel choicesPanel;
    teacherStudentGroup = new ButtonGroup();
    Color choicesPanelColor = Color.decode("#AFA2A2");

    choicesPanel= new JPanel(new GridBagLayout());
    choicesPanel.setBackground(choicesPanelColor);

    //initialize buttons with color
    teacherButton = new JRadioButton("Teacher");
    choicesButtonDecorate(teacherButton);
    teacherButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           selectedText = teacherButton.getText();
           moveOnPossible = true;
        }
    });

    studentButton = new JRadioButton("Student");
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
    gbc.insets = new Insets(10, 0, 0, 0); // Increase the horizontal spacing between components
    return gbc;
}

private void backNextButton() {
    JButton backButton;
    JButton nextButton;
    JPanel backNextButtonsPanel;
    //buttons
    backNextButtonsPanel = new JPanel(new BorderLayout());
    backButton = new JButton("< Back");
    backButton.setEnabled(false);
    backNextButtonsPanel.add(backButton, BorderLayout.WEST);

    //next
    nextButton = new JButton("Next >");
    backNextButtonsPanel.add(nextButton, BorderLayout.EAST);
    window.add(backNextButtonsPanel, BorderLayout.SOUTH);

    nextButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (moveOnPossible) {
                int windowX = window.getX();
                int windowY = window.getY();
                NewUser newUser = new NewUser(selectedText, windowX, windowY);
                window.setVisible(false);
            }
            else if (!moveOnPossible) {
                JOptionPane.showMessageDialog(null, "Please choose an option", null, JOptionPane.PLAIN_MESSAGE);
            }
        }
});
}

public void setButtonSelected(String selectedButtonText) {
    System.out.println("in set button selected");
    if (selectedButtonText == "Student") {
        studentButton.setSelected(true);
    }
    
    else if(selectedButtonText == "Teacher") {
        teacherButton.setSelected(true);
    }
    //System.out.println(selectedButton+"selectedbut");
}

public void show(int windowX, int windowY) {
   if (windowX != 0 && windowY != 0) {
       window.setLocation(windowX, windowY);
   }

   else {
    window.setLocationRelativeTo(null);
   }

   window.setVisible(true);
}

}