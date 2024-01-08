package main.view;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
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

//key listening
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;

public class MainWindow extends JFrame {
//private JFrame window;
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

Decorator decorator = new Decorator();

public MainWindow(JFrame window2) {
    //window = window2;

    MainWindowLaunch();

}

public void MainWindowLaunch() {
    windowSetUp();

    instructionsWordsWindow();

    radioButtonSetUp();

    backNextButton();

    EnterAction enterAction = new EnterAction();

    this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
    this.getRootPane().getActionMap().put("enterAction", enterAction);

    this.requestFocusInWindow();
}

private void windowSetUp() {
    //window set up
    this.setTitle("Launcher");
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.setSize(windowWidth, windowHeight);
}
 
private void instructionsWordsWindow() {
    JLabel instructionsWords;
    instructionsWords = new JLabel("Welcome! Are you a student or a teacher?");
    instructionsPanel= new JPanel();
    Color instructionsColor = Color.decode("#7A6D6D");
    instructionsPanel.setBackground(instructionsColor);
     
    instructionsPanel.add(instructionsWords);
    Color instructionsWordsColor = Color.decode("#edebeb");
    instructionsWords.setForeground(instructionsWordsColor); //color
    this.add(instructionsPanel, BorderLayout.NORTH);
 
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

    this.add(choicesPanel);
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
    this.add(backNextButtonsPanel, BorderLayout.SOUTH);



    nextButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            doNextButtonProcedure();
        }
    });
}

private void doNextButtonProcedure() {
    int windowX = this.getX();
    int windowY = this.getY();
    if (moveOnPossible) {
        NewUser newUser = new NewUser(this, studentOrTeacher,existingOrNew, windowX, windowY);
        newUser.setButtonSelected(existingOrNew);
        decorator.hideWindow(instructionsPanel, choicesPanel, backNextButtonsPanel);
    }
    else if (!moveOnPossible) {
        errorMessageSetUp();
    }
}

private void errorMessageSetUp() {
    JDialog dialog = new JDialog(this, null, true);
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
       this.setLocation(windowX, windowY);
       setWindowX(windowX);
       setWindowY(windowY);

   }

   else {
    this.setLocationRelativeTo(null);
   }

    this.setVisible(true);
}

public class EnterAction extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent e) {
        doNextButtonProcedure();
    }
}

}