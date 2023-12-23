package src.view;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

import java.awt.FlowLayout;
//import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

public class MainWindow {
private JFrame window;
JRadioButton teacherButton;
JRadioButton studentButton;
ButtonGroup teacherStudentGroup;
JPanel instructionsPanel;
JPanel choicesPanel;
JLabel instructionsWords;
JButton backButton;
JButton nextButton;
JPanel backNextButtonsPanel;




public MainWindow() {
    //window set up
    window = new JFrame();
    window.setTitle("Launcher");
    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    //window.setLayout(new FlowLayout());
    window.setLayout(new BorderLayout());
    window.setSize(800, 500);
    window.setLocationRelativeTo(null);

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

    //teacher and student radiobuttons initialize
    choicesPanel= new JPanel(new GridBagLayout());
    Color backgroundColor = Color.decode("#AFA2A2");
    choicesPanel.setBackground(backgroundColor);
    teacherButton = new JRadioButton("Teacher");
    teacherButton.setForeground(Color.WHITE);
    studentButton = new JRadioButton("Student");
    studentButton.setForeground(Color.WHITE);
    teacherStudentGroup = new ButtonGroup();

    

    //set the font for radio buttons
    Font buttonFont = new Font("Roboto", Font.PLAIN, 25); // Change the font and size here
    teacherButton.setFont(buttonFont);
    studentButton.setFont(buttonFont);


    //teacher and student radiobuttons add to group and make visible
    teacherStudentGroup.add(teacherButton);
    teacherStudentGroup.add(studentButton);
    choicesPanel.add(teacherButton);
    choicesPanel.add(studentButton);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.insets = new Insets(10, 0, 0, 0); // Increase the horizontal spacing between components
    choicesPanel.add(teacherButton, gbc);

    window.add(choicesPanel);

    //buttons
    backNextButtonsPanel = new JPanel(new BorderLayout());
    backButton = new JButton("< Back");
    backButton.setEnabled(false);
    backNextButtonsPanel.add(backButton, BorderLayout.WEST);

    //next
    nextButton = new JButton("Next >");
    backNextButtonsPanel.add(nextButton, BorderLayout.EAST);


    window.add(backNextButtonsPanel, BorderLayout.SOUTH);
  



}

public void show() {
   window.setVisible(true);
}

}