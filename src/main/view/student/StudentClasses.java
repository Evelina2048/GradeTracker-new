package main.view.student;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;

import java.awt.Rectangle;

//importing files
import main.view.MainWindow;
import main.view.NewUser;
import main.view.Set;
import main.view.Creator;
import main.view.Decorator;


public class StudentClasses extends JFrame {
    private JFrame window;
    private Creator creator = new Creator();
    private JPanel backNextButtonsPanel;
    private JPanel textFieldContainer = new JPanel(new GridLayout(0, 5)); // Panel to hold text fields
    private JTextField textField;
    private int textboxCounter = 0;
    JPanel southContainer = new JPanel(new BorderLayout());
    //JPanel westPanel = new JPanel(new BorderLayout());
    //JPanel westPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    //JPanel westPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    AtomicBoolean textFieldEmptied = new AtomicBoolean(false);;
    JButton newClassButton;
    JButton deleteClassButton;
    Decorator decorate = new Decorator();
    Set set;

    //JTextField textField = decorate.decorateTextBox();

    public StudentClasses(JFrame main,NewUser newUser, String studentOrTeacher, String existingOrNew, Set set) {

        studentClassesLaunch(main, set);
        //createNewClassButton();
        westPanelCreate();
        buttonSetUpAction(main, newUser, studentOrTeacher, existingOrNew);
    }

    public void studentClassesLaunch(JFrame main, Set set2) {
        set = set2;
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

        JButton saveButton = creator.saveButtonCreate();
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //nextButtonAction(existingOrNew, studentOrTeacher);
            }
        });

        JButton nextButton = creator.nextButtonCreate();
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("nextbuttonhit");
                writeTextToFile();
                hideWindow();
                StudentStatCollect statCollect = new StudentStatCollect(window, newUser, studentOrTeacher, existingOrNew);
            }
        });
        backNextButtonsPanel = creator.makeBackNextButtonsPanel(backButton, saveButton, nextButton);
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

    private void writeTextToFile() {
        String username = set.getUsername();
        System.out.println("usernameeeeeeeeeeee" + username);
        String filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + username + ".txt";
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            List<String> textList = new ArrayList<>();
    
            writer.write("Class:"+"\n");
            for (Component component : textFieldContainer.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel textFieldPanel = (JPanel) component;
                    for (Component innerComponent : textFieldPanel.getComponents()) {
                        if (innerComponent instanceof JTextField) {
                            JTextField textField = (JTextField) innerComponent;
                            String text = textField.getText();
                            textList.add(text);
                        }
                    }
                }
            }
    
            // Write the list as an array to the file
            writer.write(textList.toString());
    
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    //create JButton "New Class"
    private void hideWindow() {
        backNextButtonsPanel.setVisible(false);
        newClassButton.setVisible(false);
        southContainer.setVisible(false);
        textFieldContainer.setVisible(false);
        //textField.setVisible(false);
    }

    }