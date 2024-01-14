package main.view.student;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import main.view.Creator;
import main.view.Decorator;

public class PrintStudentGrades extends JFrame {
    private JFrame window;
    private JPanel backNextButtonsPanel;
    private Decorator decorator = new Decorator();
    private Creator creator;

    public PrintStudentGrades(JFrame main, String studentOrTeacher, String existingOrNew) {
        System.out.println("in print student grades");
        studentStatCollectLaunch(main);
        //createNewTypeButton();
        JLabel instructionsWords = new JLabel("Grades");
        JPanel instructionsPanel = new JPanel();
        instructionsPanel = decorator.InstructionsPanelDecorate(window, instructionsPanel, instructionsWords );
        window.add(instructionsPanel);
        buttonSetUpAction(main, studentOrTeacher, existingOrNew);
    }

    public void studentStatCollectLaunch(JFrame main) {
        System.out.println("in student grades");
        this.window = main;
        window.setTitle("PrintStudentGrades");
    }

    public void buttonSetUpAction(JFrame main, String studentOrTeacher, String existingOrNew) {
        JButton backButton = creator.backButtonCreate();
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //backButtonAction(main, newUser, studentOrTeacher, existingOrNew);
            }
        });

        JButton saveButton = creator.saveButtonCreate();
        JPanel saveButtonPanel = new JPanel();
        saveButtonPanel.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //nextButtonAction(existingOrNew, studentOrTeacher);
            }
        });

        JButton nextButton = creator.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        nextButton.setEnabled(false);
        backNextButtonsPanel = creator.makeBackNextButtonsPanel(backButtonPanel, saveButtonPanel, nextButtonPanel);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
    }

    //window.setVisible(true);


    public static void main(String[] args) {
        JFrame main = new JFrame(); // Initialize your main JFrame here

        main.setSize(800, 600); // Set the size as needed
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set close operation
        main.setVisible(true); // Make the frame visible

        String studentOrTeacher = "Student"; // Initialize studentOrTeacher
        String existingOrNew = "Existing"; // Initialize existingOrNew

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PrintStudentGrades edit = new PrintStudentGrades(main, studentOrTeacher, existingOrNew);
            }
        });
    }


//North print grades
//For i in classes:
    //print class: percentageGrade, gpa (if applicable)

//overall gpa

    }