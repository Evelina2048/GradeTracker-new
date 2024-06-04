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

public class MainWindow extends JFrame {
//private JFrame window;
private String studentOrTeacher;
private boolean moveOnPossible = false;
private int windowX;
private int windowY;
private String existingOrNew;
private Set set;
JRadioButton studentButton;
JRadioButton teacherButton;
ButtonGroup teacherStudentGroup;
int windowWidth = 750;
int windowHeight = 500;

//panels
JPanel instructionsPanel;
JPanel choicesPanel;
JPanel backNextButtonsPanel;
JFrame window;

Decorator decorator = new Decorator();

public MainWindow(Set set) {
    //window = window2;
    MainWindowLaunch(set);

}

public void MainWindowLaunch(Set set) {
    this.set = set;
    // //this.window = set.getWindow();
    windowSetUp();

    InstructionsWordsWindow();

    radioButtonSetUp();

    backNextButton();

    EnterAction enterAction = new EnterAction();

    this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
    this.getRootPane().getActionMap().put("enterAction", enterAction);

    this.requestFocusInWindow();
}

private void windowSetUp() {
    //window set up
    System.out.println("hereeeeeeeee");
    this.setTitle("Launcher");
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.setSize(windowWidth, windowHeight);
}
 
private void InstructionsWordsWindow() {
        JLabel instructionsWords = new JLabel("Welcome! Are you a student or a teacher?");
        instructionsPanel = decorator.InstructionsPanelDecorate(this, instructionsPanel, instructionsWords );
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
    choicesPanel.add(teacherButton, decorator.choiceGbc());
}

private void backNextButton() {
    JButton backButton;
    JButton nextButton;
    //buttons
    backNextButtonsPanel = new JPanel(new BorderLayout());

    backButton = new JButton("< Back");
    backButton.setEnabled(false);
    backNextButtonsPanel.add(backButton, BorderLayout.WEST);
    System.out.println("backbuttonpreferedsize "+backButton.getPreferredSize());

    // nextButton = new JButton("Save");
    // backNextButtonsPanel.add(nextButton, BorderLayout.CENTER);
    // this.add(backNextButtonsPanel, BorderLayout.SOUTH);
    
    //next
    nextButton = new JButton("Next >");
    backNextButtonsPanel.add(nextButton, BorderLayout.EAST);
    this.add(backNextButtonsPanel, BorderLayout.SOUTH);



    nextButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            doNextButtonProcedure();
            set.setStudentOrTeacher(studentOrTeacher);
        }
    });
}

private void doNextButtonProcedure() {
    decorator.setWindowX(this.getX());
    decorator.setWindowY(this.getY());

    if (moveOnPossible) {
        setUserInfo();
        NewUser newUser = new NewUser(existingOrNew, windowX, windowY, set);
        if (set.getExistingOrNew() != null) {
            newUser.setButtonSelected();
        }
        decorator.hideWindow(instructionsPanel, choicesPanel, backNextButtonsPanel);
    }
    else if (!moveOnPossible) {
        decorator.errorMessageSetUp(this, studentButton);
    }
}

private void setUserInfo() {
    set.setWindow(this);
    set.setStudentOrTeacher(studentOrTeacher);
}


public void setButtonSelected() {
    String selectedButtonText = set.getStudentOrTeacher();
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

public void show(int windowX, int windowY) {
   if (windowX != 0 && windowY != 0) {
       this.setLocation(windowX, windowY);
       decorator.setWindowX(windowX);
       decorator.setWindowY(windowY);

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