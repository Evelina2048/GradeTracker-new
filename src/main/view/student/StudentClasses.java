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

import java.util.concurrent.atomic.AtomicBoolean;
import main.view.NewUser;
import main.view.Set;
import main.view.Creator;
import main.view.Decorator;



public class StudentClasses extends JFrame {
    private JFrame window;
    private Creator creator;
    private JPanel backNextButtonsPanel;
    // private JPanel textFieldContainer = new JPanel(new GridLayout(0, 5)); // Panel to hold text fields
    private JTextField textField;
    private JButton saveButton;
    private int textboxCounter = 0;
    JPanel southContainer = new JPanel(new BorderLayout());
    //JPanel westPanel = new JPanel(new BorderLayout());
    //JPanel westPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));`
    //JPanel westPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    AtomicBoolean textFieldEmptied = new AtomicBoolean(false);;
    JButton newClassButton;
    JButton deleteClassButton;
    Decorator decorate = new Decorator();
    Set set;

    //JTextField textField = decorate.decorateTextBox();

    public StudentClasses(JFrame main,NewUser newUser, String studentOrTeacher, String existingOrNew, Set set) {
        creator = new Creator(set);
        studentClassesLaunch(main, set);
        //createNewClassButton();
        westPanelCreate();
        buttonSetUpAction(main, newUser, studentOrTeacher, existingOrNew);
    }

    public void studentClassesLaunch(JFrame main, Set set) {
        this.window = main;
        this.set = set;
        System.out.println("in student classes");
        window.setTitle("StudentClasses");
        window.setLayout(new BorderLayout());
        creator.createTextBox(window);
        //textField.setVisible(true);
    }

    public void buttonSetUpAction(JFrame main, NewUser newUser, String studentOrTeacher, String existingOrNew) {
        JButton backButton = creator.backButtonCreate();
        ///
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        ////
        backButton.setPreferredSize(new Dimension(87, 29));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //backButtonAction(main, newUser, studentOrTeacher, existingOrNew);
            }
        });

        saveButton = creator.saveButtonCreate();
        JPanel saveButtonPanel = new JPanel();
        saveButtonPanel.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                creator.writeTextToFile(textFieldEmptied);
                saveButton.setEnabled(false);
                //nextButtonAction(existingOrNew, studentOrTeacher);
            }
        });

        JButton nextButton = creator.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        nextButton.setPreferredSize(new Dimension(87, 29));
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("nextbuttonhit");
                creator.writeTextToFile(textFieldEmptied);
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
                creator.createTextBox(window);
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
                creator.deleteTextBox(window);
                saveButton.setEnabled(true);
                
            }
        });
    }

    private void westPanelCreate() {
        createNewClassButton();
        deleteClassButton();


        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.add(newClassButton,BorderLayout.WEST);
        buttonsPanel.add(deleteClassButton, BorderLayout.EAST);
    
        southContainer.add(buttonsPanel,BorderLayout.CENTER);

        window.add(southContainer, BorderLayout.SOUTH);

    }

    //create JButton "New Class"
    private void hideWindow() {
        backNextButtonsPanel.setVisible(false);
        newClassButton.setVisible(false);
        southContainer.setVisible(false);
        creator.getTextFieldContainer().setVisible(false);
        //textField.setVisible(false);
    }

    }