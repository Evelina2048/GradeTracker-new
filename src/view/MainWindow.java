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

public class MainWindow {
private JFrame window;
private String studentOrTeacher;
private boolean moveOnPossible = false;
private int windowX;
private int windowY;
private String existingOrNew;
JRadioButton studentButton;
JRadioButton teacherButton;
ButtonGroup teacherStudentGroup;
int windowWidth = 800;
int windowHeight = 500;

//panels
JPanel instructionsPanel;
JPanel choicesPanel;
JPanel backNextButtonsPanel;
//

public MainWindow() {
    windowSetUp();

    instructionsWordsWindow();

    radioButtonSetUp();

    backNextButton();
}


private void windowSetUp() {
    //window set up
    window = new JFrame();
    window.setTitle("Launcher");
    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    window.setLayout(new BorderLayout());
    window.setSize(windowWidth, windowHeight);
    window.setLocationRelativeTo(null);
}
 
private void instructionsWordsWindow() {
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

private void radioButtonSetUp() {
    teacherStudentGroup = new ButtonGroup();
    Color choicesPanelColor = Color.decode("#AFA2A2");

    choicesPanel= new JPanel(new GridBagLayout());
    choicesPanel.setBackground(choicesPanelColor);

    //initialize buttons with color
    teacherButton = new JRadioButton("Teacher");
    choicesButtonDecorate(teacherButton);
    teacherButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           studentOrTeacher = teacherButton.getText();
           moveOnPossible = true;
        }
        
    });

    studentButton = new JRadioButton("Student");
    choicesButtonDecorate(studentButton);
    studentButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            studentOrTeacher = studentButton.getText();
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

    gbc.insets = new Insets(10, 0, 0, 0); // Increase the horizontal spacing between components
    return gbc;
}

private void backNextButton() {
    JButton backButton;
    JButton nextButton;
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
            int windowX = window.getX();
            int windowY = window.getY();
            if (moveOnPossible) {
                System.out.println("hello");
                NewUser newUser = new NewUser(window, studentOrTeacher,existingOrNew, windowX, windowY);
                newUser.setButtonSelected(existingOrNew);
                hidePanels();
                //window.setVisible(false);
                //window.dispose();
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
    studentOrTeacher = selectedButtonText;
    if (selectedButtonText == "Student") {
        studentButton.setSelected(true);
        moveOnPossible = true;
    }
    
    else if(selectedButtonText == "Teacher") {
        teacherButton.setSelected(true);
        moveOnPossible = true;
    }
}

public void setExistingOrNew(String existingOrNew2) {
    System.out.println("setExistingOrNew in Main Window "+existingOrNew2);
    existingOrNew = existingOrNew2;
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

public void show(int windowX, int windowY) {
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

private void hidePanels() {
    instructionsPanel.setVisible(false);
    choicesPanel.setVisible(false);
    backNextButtonsPanel.setVisible(false);
}

}