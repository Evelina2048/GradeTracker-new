package main.view.student.existing;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import main.controller.Creator;
import main.model.Set;
import main.controller.CreateButton;


public class EditOrResults extends JFrame {
    private JFrame window;
    private JPanel backNextButtonsPanel;
    private Creator create;
    private Set set;
    private CreateButton createButton = new CreateButton();

    public EditOrResults(JFrame main, String studentOrTeacher, String existingOrNew, Set set) {
        this.set = Set.getInstance();
        System.out.println("in print student grades");
        editOrResultsLaunch(main);
        //createNewTypeButton();
        buttonSetUpAction(main, studentOrTeacher, existingOrNew);
        create = new Creator();
    }

    public void editOrResultsLaunch(JFrame main) {
        System.out.println("in student grades");
        window = main;
        window.setTitle("EditOrResults");
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

    //
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
    //             EditOrResults edit = new EditOrResults(main, studentOrTeacher, existingOrNew, set);
    //         }
    //     });
    // }
    //

    }

    