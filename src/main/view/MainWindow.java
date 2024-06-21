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

import main.model.Set;
import main.controller.Decorator;

public class MainWindow extends JFrame {
//private JFrame window;
private String studentOrTeacher;
private boolean moveOnPossible = false;
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

public MainWindow() {
    //window = window2;
    MainWindowLaunch();

}

public void MainWindowLaunch() {
    this.set = Set.getInstance();
    window = set.getWindow();
    windowSetUp();

    InstructionsWordsWindow();

    radioButtonSetUp();

    backNextButton();

    EnterAction enterAction = new EnterAction();

    window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
    window.getRootPane().getActionMap().put("enterAction", enterAction);

    window.requestFocusInWindow();
}

private void windowSetUp() {
    //window set up
    window.setTitle("Launcher");
    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    window.setLayout(new BorderLayout());
    window.setSize(windowWidth, windowHeight);
}
 
private void InstructionsWordsWindow() {
        JLabel instructionsWords = new JLabel("Welcome! Are you a student or a teacher?");
        instructionsPanel = decorator.InstructionsPanelDecorate(instructionsPanel, instructionsWords);
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

    //next
    nextButton = new JButton("Next >");
    backNextButtonsPanel.add(nextButton, BorderLayout.EAST);
    window.add(backNextButtonsPanel, BorderLayout.SOUTH);



    nextButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            doNextButtonProcedure();
            set.setStudentOrTeacher(studentOrTeacher);
        }
    });
}

private void doNextButtonProcedure() {

    if (moveOnPossible) {
        setUserInfo();
        set.setWindow(window);
        NewUser newUser = new NewUser();
        if (set.getExistingOrNew() != null) {
            newUser.setButtonSelected();
        }
        decorator.hideWindow(instructionsPanel, choicesPanel, backNextButtonsPanel);
    }
    else if (!moveOnPossible) {
        decorator.errorMessageSetUp(studentButton);
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

public class EnterAction extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent e) {
        doNextButtonProcedure();
    }
}

}