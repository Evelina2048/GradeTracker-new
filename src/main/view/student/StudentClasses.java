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
    private JPanel textFieldContainer = new JPanel(new GridLayout(0, 5)); // Panel to hold text fields
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
        this.set = set;
        System.out.println("in student classes");
        this.window = main;
        window.setTitle("StudentClasses");
        window.setLayout(new BorderLayout());
        createTextBox();
        //textField.setVisible(true);
    }

    private void createTextBox() {
        textboxCounter++;
        if (textboxCounter <= 30) {
            System.out.println("In create textbox new");
            JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            textFieldEmptied.set(false);
            
            JTextField textField = decorate.decorateTextBox("Enter Class Name");
            
            textFieldPanel.add(textField);
            
            textField.setPreferredSize(new Dimension(50, 50));
            textFieldContainer.add(textFieldPanel); // Add to the container panel
            textFieldContainer.setVisible(true);
            window.add(textFieldContainer, BorderLayout.NORTH);

            creator.textFieldFocusListener(window, textField, "Enter Class Name");
            
            
            window.revalidate(); // Refresh the layout

        }


    }

    public void buttonSetUpAction(JFrame main, NewUser newUser, String studentOrTeacher, String existingOrNew) {
        JButton backButton = creator.backButtonCreate();
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //backButtonAction(main, newUser, studentOrTeacher, existingOrNew);
            }
        });

        saveButton = creator.saveButtonCreate();
        saveButton.setPreferredSize(new Dimension(80,40));
        JPanel saveButtonPanel = new JPanel();
        saveButtonPanel.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                creator.writeTextToFile(textFieldContainer, textFieldEmptied);
                saveButton.setEnabled(false);
                //nextButtonAction(existingOrNew, studentOrTeacher);
            }
        });

        JButton nextButton = creator.nextButtonCreate();
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("nextbuttonhit");
                creator.writeTextToFile(textFieldContainer, textFieldEmptied);
                hideWindow();
                StudentStatCollect statCollect = new StudentStatCollect(window, newUser, studentOrTeacher, existingOrNew);
            }
        });
        backNextButtonsPanel = creator.makeBackNextButtonsPanel(backButton, saveButtonPanel, nextButton);
        southContainer.add(backNextButtonsPanel, BorderLayout.SOUTH);
        
        window.add(southContainer, BorderLayout.SOUTH);
    }

    //create textbox "Class" placeholder
    private void createNewClassButton() {
        newClassButton = new JButton("New Class");
        newClassButton.setPreferredSize(new Dimension(80, 50));
        newClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createTextBox();
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
                deleteTextBox();
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

    private void deleteTextBox() {
        int componentsCount = textFieldContainer.getComponentCount();
        if (componentsCount > 0) {
            Component lastComponent = textFieldContainer.getComponent(componentsCount - 1);
            textFieldContainer.remove(lastComponent);
            window.revalidate();
            window.repaint();
            textboxCounter--;
        }
    }

    public JPanel getTextFieldContianer() {
        return textFieldContainer;
    }
    //create JButton "New Class"
    private void hideWindow() {
        backNextButtonsPanel.setVisible(false);
        newClassButton.setVisible(false);
        southContainer.setVisible(false);
        textFieldContainer.setVisible(false);
        //textField.setVisible(false);
    }

    }