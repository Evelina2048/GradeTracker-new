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


public class PrintStudentGrades extends JFrame {
    private JFrame window;
    private Creator creator = new Creator();
    private JPanel backNextButtonsPanel;

    public PrintStudentGrades(JFrame main,NewUser newUser, String studentOrTeacher, String existingOrNew) {
        System.out.println("in print student grades");
        studentStatCollectLaunch(main);
        //createNewTypeButton();
        buttonSetUpAction(main, newUser, studentOrTeacher, existingOrNew);
    }

    public void studentStatCollectLaunch(JFrame main) {
        System.out.println("in student grades");
        this.window = main;
        window.setTitle("PrintStudentGrades");
    }

    public void buttonSetUpAction(JFrame main, NewUser newUser, String studentOrTeacher, String existingOrNew) {
        JButton backButton = creator.backButtonCreate();
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //backButtonAction(main, newUser, studentOrTeacher, existingOrNew);
            }
        });

       
        
        JButton nextButton = creator.nextButtonCreate();
        nextButton.setEnabled(false);
        backNextButtonsPanel = creator.makeBackNextButtonsPanel(backButton, nextButton);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
    }


//North print grades
//For i in classes:
    //print class: percentageGrade, gpa (if applicable)

//overall gpa

    }