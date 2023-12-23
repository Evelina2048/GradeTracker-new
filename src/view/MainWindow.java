package src.view;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

import java.awt.FlowLayout;
//import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;


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
    instructionsPanel.add(instructionsWords);
    window.add(instructionsPanel, BorderLayout.NORTH);

    //set the font for instructions
    Font instructionsFont = new Font("Arial", Font.PLAIN, 40); // Change the font and size here
    instructionsWords.setFont(instructionsFont);

    //teacher and student radiobuttons initialize
    choicesPanel= new JPanel();
    teacherButton = new JRadioButton("Teacher");
    studentButton = new JRadioButton("Student");
    teacherStudentGroup = new ButtonGroup();

    //set the font for radio buttons
    Font buttonFont = new Font("Arial", Font.PLAIN, 30); // Change the font and size here
    teacherButton.setFont(buttonFont);
    studentButton.setFont(buttonFont);


    //teacher and student radiobuttons add to group and make visible
    teacherStudentGroup.add(teacherButton);
    teacherStudentGroup.add(studentButton);
    choicesPanel.add(teacherButton);
    choicesPanel.add(studentButton);
    window.add(choicesPanel);

    //buttons
    backNextButtonsPanel = new JPanel();
    backButton = new JButton("< Back");
    backButton.setEnabled(false);
    backNextButtonsPanel.add(backButton);
    window.add(backNextButtonsPanel, BorderLayout.SOUTH);
  



}

public void show() {
   window.setVisible(true);
}

}