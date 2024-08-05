package view.student;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import controller.Creator;
import controller.Decorator;
import model.SetList;
import model.SetUserInformation;
import controller.CreateButton;

public class PrintStudentGrades extends JFrame {
    private JFrame window;
    private JPanel backNextButtonsPanel;
    private Decorator decorate = new Decorator();
    private Creator create;
    private SetList setList;
    private SetUserInformation setUserInformation;
    private CreateButton createButton = new CreateButton();

    public PrintStudentGrades(JFrame main, String studentOrTeacher, String existingOrNew) {
        System.out.println("in print student grades");
        studentStatCollectLaunch(main);
        //createNewTypeButton();
        JLabel instructionsWords = new JLabel("Grades");
        JPanel instructionsPanel = new JPanel();
        instructionsPanel = decorate.InstructionsPanelDecorate(instructionsPanel, instructionsWords );
        window.add(instructionsPanel);
        buttonSetUpAction(main, studentOrTeacher, existingOrNew);
        setList = SetList.getInstance();
        setUserInformation = SetUserInformation.getInstance();


        //go into file. (For loop of files)
        // for (int i = 0; i < setList.getFinalClassList().size();i++) {
        //     System.out.println("printgradestest "+setList.getFinalClassList().get(i));
        //     // class.readFileIntoList(pathtofilewithclasslistindex);
        // }
        for (int i = 0; i < setList.getFinalClassList().size();i++) {
            System.out.println("printgradestest "+setList.getFinalClassList().get(i));
            String filePathForClass = setUserInformation.getPathToClassInformationFileWithChosenIndex(i);
            // class.readFileIntoList(pathtofilewithclasslistindex);
        }
    }

    public void studentStatCollectLaunch(JFrame main) {
        System.out.println("in student grades");
        this.window = main;
        window.setTitle("PrintStudentGrades");
    }

    public void buttonSetUpAction(JFrame main, String studentOrTeacher, String existingOrNew) {
        JButton backButton = createButton.backButtonCreate();
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //backButtonAction(main, newUser, studentOrTeacher, existingOrNew);
            }
        });

        JButton saveButton = createButton.saveButtonCreate();
        JPanel saveButtonPanel = new JPanel();
        saveButtonPanel.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //nextButtonAction(existingOrNew, studentOrTeacher);
            }
        });

        JButton nextButton = createButton.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        nextButton.setEnabled(false);
        backNextButtonsPanel = createButton.makeBackNextButtonsPanel(backButtonPanel, saveButtonPanel, nextButtonPanel);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
    }

    //window.setVisible(true);


    // public static void main(String[] args) {
    //     JFrame main = new JFrame(); // Initialize your main JFrame here

    //     main.setSize(800, 600); // Set the size as needed
    //     main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set close operation
    //     main.setVisible(true); // Make the frame visible

    //     String studentOrTeacher = "Student"; // Initialize studentOrTeacher
    //     String existingOrNew = "Existing"; // Initialize existingOrNew

    //     SwingUtilities.invokeLater(new Runnable() {
    //         @Override
    //         public void run() {
    //             PrintStudentGrades edit = new PrintStudentGrades(main, studentOrTeacher, existingOrNew);
    //         }
    //     });
    // }


//North print grades
//For i in classes:
    //print class: percentageGrade, gpa (if applicable)

//overall gpa

    }