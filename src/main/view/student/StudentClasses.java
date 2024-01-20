package main.view.student;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

import java.awt.GridBagConstraints;

import java.util.concurrent.atomic.AtomicBoolean;
import main.view.NewUser;
import main.view.Set;
import main.view.Creator;
import main.view.Decorator;
import main.view.Gather;



public class StudentClasses extends JFrame {
    private JFrame window;
    private Creator creator;
    private JPanel backNextButtonsPanel;
    // private JPanel textFieldContainer = new JPanel(new GridLayout(0, 5)); // Panel to hold text fields
    private JPanel textFieldContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    private JTextField textField;
    private JButton saveButton;
    private int textboxCounter = 0;
    JPanel southContainer = new JPanel(new BorderLayout());
    AtomicBoolean textFieldEmptied = new AtomicBoolean(false);;
    JButton newClassButton;
    JButton deleteClassButton;
    Decorator decorate = new Decorator();
    Set set;

    //JTextField textField = decorate.decorateTextBox();

    public StudentClasses(JFrame main,NewUser newUser, String studentOrTeacher, String existingOrNew, Set set) {
        //studentClassesLaunch(main, newUser, studentOrTeacher, existingOrNew, set);
        //createNewClassButton();
        // westPanelCreate();
        // buttonSetUpAction(main, newUser, studentOrTeacher, existingOrNew);
    }

    public void studentClassesLaunch(JFrame main, NewUser newUser, String studentOrTeacher, String existingOrNew, Set set) {
        this.window = main;
        creator = new Creator(set);
        this.set = set;
        System.out.println("in student classes");
        window.setTitle("StudentClasses");
        window.setLayout(new BorderLayout());
        creator.createTextBox(window, "Enter Class Name", 50, 50);
        //textField.setVisible(true);
        westPanelCreate();
        buttonSetUpAction(main, newUser, studentOrTeacher, existingOrNew);
    }

    public void buttonSetUpAction(JFrame main, NewUser newUser, String studentOrTeacher, String existingOrNew) {
        JButton backButton = creator.backButtonCreate();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        ///
        JPanel backButtonPanel = new JPanel();
        gbc.gridx = 0; // Set the column index for backButton
        gbc.gridy = 0; // Set the row index for backButton
        gbc.weightx = 1.0; // Make backButton horizontally resizable
        backButtonPanel.add(backButton, gbc); // Add backButton to backButtonPanel with constraints
        ////
        backButton.setPreferredSize(new Dimension(87, 29));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Gather gather = new Gather(window, newUser, studentOrTeacher, existingOrNew, window.getX(), window.getY(), set);
                gather.showWindow(window.getX(), window.getY());
                hideWindow(); 
               //backButtonAction(main, newUser, studentOrTeacher, existingOrNew);
            }
        });

        saveButton = creator.saveButtonCreate();
        JPanel saveButtonPanel = new JPanel();
        gbc.gridx = 1; // Set the column index for saveButton
        gbc.gridy = 0; // Set the row index for saveButton
        gbc.weightx = 1.0; // Make saveButton horizontally resizable
        backButtonPanel.add(saveButton, gbc); // Add saveButton to backButtonPanel with constraints

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                creator.writeFolderToFile(textFieldEmptied);
                creator.writeTextToFile(textFieldEmptied, "Enter Class Name");
                saveButton.setEnabled(false);
                //nextButtonAction(existingOrNew, studentOrTeacher);
            }
        });

        JButton nextButton = creator.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        gbc.gridx = 2; // Set the column index for nextButton
        gbc.gridy = 0; // Set the row index for nextButton
        gbc.weightx = 1.0; // Make nextButton horizontally resizable
        backButtonPanel.add(nextButton, gbc); // Add nextButton to backButtonPanel with constraints

        nextButton.setPreferredSize(new Dimension(87, 29));
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("nextbuttonhit");
                creator.writeFolderToFile(textFieldEmptied);
                creator.writeTextToFile(textFieldEmptied, "Enter Class Name");
                hideWindow();
                StudentStatCollect statCollect = new StudentStatCollect(window, newUser, studentOrTeacher, existingOrNew, set);
            }
        });
        System.out.println("test11 (should be 29) "+backButton.getHeight());
        backNextButtonsPanel = creator.makeBackNextButtonsPanel(backButtonPanel, saveButtonPanel, nextButtonPanel);
        southContainer.add(backNextButtonsPanel, BorderLayout.SOUTH);
        
        window.add(southContainer, BorderLayout.SOUTH);
    }

    //create textbox "Class" placeholder
    private void createNewClassButton() {
        newClassButton = new JButton("New Class");
        newClassButton.setPreferredSize(new Dimension(80, 50));
        newClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                creator.createTextBox(window, "Enter Class Name", 50, 50);
                saveButton.setEnabled(true);
                
        }
    });
        // westPanel.add(newClassButton, BorderLayout.SOUTH);

        // window.add(westPanel,BorderLayout.WEST);
    }

    private void deleteClassButton() {
        deleteClassButton = new JButton("Delete Class");
        deleteClassButton.setPreferredSize(new Dimension(100, 50));
        deleteClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("deleteClassButtonPressed");
                creator.deleteTextBox(window, creator.getTextFieldContainer());
                saveButton.setEnabled(true);
                
            }
        });
    }

    private void westPanelCreate() {
        createNewClassButton();
        deleteClassButton();


        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        ///

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add newClassButton
        gbc.gridx = 0; // Set the column index for newClassButton
        gbc.gridy = 0; // Set the row index for newClassButton
        gbc.weightx = 1.0; // Make newClassButton horizontally resizable
        buttonsPanel.add(newClassButton, gbc); // Add newClassButton to buttonsPanel with constraints

        // Add deleteClassButton
        gbc.gridx = 1; // Set the column index for deleteClassButton
        gbc.gridy = 0; // Set the row index for deleteClassButton
        gbc.weightx = 1.0; // Make deleteClassButton horizontally resizable
        buttonsPanel.add(deleteClassButton, gbc); // Add deleteClassButton to buttonsPanel with constraints

        // ...

        // Add buttonsPanel
        southContainer.add(buttonsPanel, BorderLayout.CENTER); // Add buttonsPanel to southContainer
        ///

        //window.add(southContainer, BorderLayout.SOUTH);

    }

    //create JButton "New Class"
    private void hideWindow() {
        backNextButtonsPanel.setVisible(false);
        newClassButton.setVisible(false);
        deleteClassButton.setVisible(false);
        southContainer.setVisible(false);
        creator.getTextFieldContainer().setVisible(false);

        //textField.setVisible(false);
    }

    }