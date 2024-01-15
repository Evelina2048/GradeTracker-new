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
import java.util.ArrayList;
import java.io.BufferedReader;

import java.io.FileReader;

//importing files
import main.view.MainWindow;
import main.view.NewUser;
import main.view.Creator;
import main.view.Set;


public class StudentStatCollect extends JFrame {
    private JFrame window;
    private Creator creator;
    private JPanel backNextButtonsPanel;
    private JButton newTypeButton;
    private Set set;
    private JPanel eastPanel;
    private JPanel classLabelPanel = new JPanel();
    private int index = 0;

    public StudentStatCollect(JFrame main,NewUser newUser, String studentOrTeacher, String existingOrNew, Set set) {
        this.window = main;
        this.set = set;
        creator = new Creator(set);
        studentStatCollectLaunch(window);
        createNewTypeButton();
        buttonSetUpAction(main, newUser, studentOrTeacher, existingOrNew);
        //
        DisplayClasses();
        //
    }

    public void studentStatCollectLaunch(JFrame window) {
        System.out.println("in student classes");
        window.setTitle("StudentStatCollect");
    }

    public void buttonSetUpAction(JFrame main, NewUser newUser, String studentOrTeacher, String existingOrNew) {
        JButton backButton = creator.backButtonCreate();
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hideWindow();
                StudentClasses studentClasses = new StudentClasses(main, newUser, studentOrTeacher, existingOrNew, set);
                studentClasses.studentClassesLaunch(main, newUser, studentOrTeacher, existingOrNew, set);
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
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //write to file.
                hideWindow();
                PrintStudentGrades print = new PrintStudentGrades(main, studentOrTeacher,existingOrNew);
            }
        });
        backNextButtonsPanel = creator.makeBackNextButtonsPanel(backButtonPanel, saveButtonPanel, nextButtonPanel);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
    }

   private void createNewTypeButton() {
       newTypeButton = new JButton("New Type");
       newTypeButton.setPreferredSize(new Dimension(80, 50));
       eastPanel = new JPanel(new BorderLayout());
       eastPanel.add(newTypeButton, BorderLayout.NORTH);

       window.add(eastPanel,BorderLayout.EAST);
   }

    // For i in classes, give classes : class1 
    //     for j in types :
    //         type1gradeBox.


    //read classes array, first five classes
    private void DisplayClasses() {
        String filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() +"/class"+ ".txt";
        ArrayList<String> classList = set.getClassList();
        //will be updated later
        for (String className : classList) {
            readClass(classList);
            index++;
        }
        //index++;
    }

    private void readClass(ArrayList<String> classList) { 
        //filepath is username.txt
        //for (String className : classList) {
        
        JLabel classLabel = new JLabel(classList.get(index));
        classLabelPanel.add(creator.createTextBox(window, "Enter Class Name"));

        classLabelPanel.add(classLabel);
            //System.out.println(className);
        //}
        window.add(classLabelPanel);
        creator.windowFix(window);
    }


    private void hideWindow() {
        newTypeButton.setVisible(false);
        backNextButtonsPanel.setVisible(false);
        newTypeButton.setVisible(false);
        eastPanel.setVisible(false);
    }
    }