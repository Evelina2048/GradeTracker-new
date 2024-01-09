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
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
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

import java.io.BufferedReader;

import java.io.FileReader;

//importing files
import main.view.MainWindow;
import main.view.NewUser;
import main.view.Creator;


public class StudentClasses extends JFrame {
    private JFrame window;
    private Creator creator = new Creator();
    private JPanel backNextButtonsPanel;

    public StudentClasses(JFrame main,NewUser newUser, String studentOrTeacher, String existingOrNew) {
        studentClassesLaunch(main);
        createNewClassButton();
        buttonSetUpAction(main, newUser, studentOrTeacher, existingOrNew);
    }

    public void studentClassesLaunch(JFrame main) {
        System.out.println("in student classes");
        this.window = main;
        window.setTitle("StudentClasses");
    }

    public void buttonSetUpAction(JFrame main, NewUser newUser, String studentOrTeacher, String existingOrNew) {
        JButton backButton = creator.backButtonCreate();
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //backButtonAction(main, newUser, studentOrTeacher, existingOrNew);
            }
        });

       
        
        JButton nextButton = creator.nextButtonCreate();
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //nextButtonAction(existingOrNew, studentOrTeacher);
            }
        });
        backNextButtonsPanel = creator.makeBackNextButtonsPanel(backButton, nextButton);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
    }

    //create textbox "Class" placeholder
    private void createNewClassButton() {
        JButton newClassButton = new JButton("New Class");
        newClassButton.setPreferredSize(new Dimension(80, 50));
        JPanel westPanel = new JPanel(new BorderLayout());
        westPanel.add(newClassButton, BorderLayout.SOUTH);

        window.add(westPanel,BorderLayout.WEST);
    }

    //create JButton "New Class"


    }