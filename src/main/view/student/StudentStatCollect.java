package main.view.student;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

import java.util.ArrayList;
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
    //private JPanel classLabelPanel = new JPanel(new GridLayout(4,4));
    private JPanel classLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private int index = 0;
    private int typeNumber = 0;
    private JPanel northCreditsPanel;

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
        window.setPreferredSize(new Dimension(1000, 1000));
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
       eastPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
       eastPanel.setBackground(Color.PINK);
       eastPanel.add(newTypeButton);
       newTypeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            typeNumber++;
            //classLabelPanel.add(creator.createTextBox(window, "Grade Type"+typeNumber, 50, 50));
            //classLabelPanel.add(creator.createTextBox(window, "Percentage of Grade", 80, 50));
        }
    });
    
       window.add(classLabelPanel);
       window.add(eastPanel,BorderLayout.EAST);
       //creator.windowFix();
       creator.windowFix(window);
   }

    // For i in classes, give classes : class1 
    //     for j in types :
    //         type1gradeBox.


    //read classes array, first five classes
    private void DisplayClasses() {
        ///
        northCreditsPanel = new JPanel(new BorderLayout());
        JPanel creditTypePanel = new JPanel(new BorderLayout());
        JTextField creditType = creator.createTextBox(window, "Credits (optional)", 50, 50);
        creditTypePanel.add(creditType);
        creditTypePanel.setPreferredSize(new Dimension(130,50));
        northCreditsPanel.add(creditTypePanel, BorderLayout.NORTH);
        ///
        //JTextField credits = creator.createTextBox(window, "Credits (optional)", 50, 50);
        String filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() +"/class"+ ".txt";
        ArrayList<String> classList = set.getClassList();
        //will be updated later

        //for (String className : classList) {
        readClass(classList);
        //classLabelPanel.add(creator.createTextBox(window, "Credits (optional)", 50, 50));
        index++;
        //}
        //index++;
    }

    private void readClass(ArrayList<String> classList) { 
        JLabel classLabel = new JLabel(classList.get(index));
        classLabelPanel.add(classLabel,BorderLayout.WEST);
        classLabelPanel.add(northCreditsPanel);

        gradePercentageBox();

        JPanel northTypePanel = new JPanel(new BorderLayout());
        JPanel gradeTypePanel = new JPanel(new BorderLayout());
        JTextField gradeType = creator.createTextBox(window, "Grade Type"+typeNumber, 50, 50);
        gradeTypePanel.add(gradeType);
        gradeTypePanel.setPreferredSize(new Dimension( 100,50));
        northTypePanel.add(gradeTypePanel, BorderLayout.NORTH);

        classLabelPanel.add(northTypePanel);
        // classLabelPanel.add(northCreditsPanel);
        window.add(classLabelPanel, BorderLayout.WEST);
        typeNumber++;
       
        //window.add(classLabelPanel);
        creator.windowFix(window);
    }
    private void gradePercentageBox() {
        JPanel northGradePercentagePanel = new JPanel(new BorderLayout());
        JPanel gradePercentagePanel = new JPanel(new BorderLayout());
        JTextField gradePercentage = creator.createTextBox(window, "Percentage of Grade", 80, 50);
        gradePercentagePanel.add(gradePercentage);
        gradePercentagePanel.setPreferredSize(new Dimension(155,50));
        northGradePercentagePanel.add(gradePercentagePanel, BorderLayout.NORTH);
        classLabelPanel.add(northGradePercentagePanel);

    }


    private void hideWindow() {
        newTypeButton.setVisible(false);
        backNextButtonsPanel.setVisible(false);
        newTypeButton.setVisible(false);
        eastPanel.setVisible(false);
    }
    }