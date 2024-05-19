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
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Component;
import java.io.BufferedReader;

import java.util.ArrayList;
import main.view.NewUser;
import main.view.Creator;
import main.view.Set;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Container;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentStatCollect extends JFrame {
    private JFrame window;
    private Creator creator;
    private JPanel backNextButtonsPanel;
    private JButton newTypeButton;
    private Set set;
    private JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel classLabelPanelContainer = new JPanel(new BorderLayout());
    private JPanel newDelContainerFlow;
    //private JPanel classLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel classLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    //private int index = set.getClassListIndex();
    private int typeNumber = 0;
    private int numOfBoxes = 0;

    private int maxBoxes = 25;
    
    //private boolean underMaxBoxes = (numOfBoxes <=27);
    private JPanel northCreditsPanel;
    private JPanel northNewTypePanel;
    private JPanel allBoxesPanel = new JPanel();
    private ArrayList<String> classList;
    private ArrayList<String> typeList;

    public StudentStatCollect(JFrame main,NewUser newUser, String studentOrTeacher, String existingOrNew, Set set) {
        //set.resetContainerAndPanel();
        System.out.println("classlistinstudentstatcollectbegining"+ set.getClassList());
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
        classList = set.getClassList();
        System.out.println("in student classes...........********"+classList);
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
        saveButton.setEnabled(false);
        saveButtonPanel.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check if textbox is vaild

                //if valid. regex?
                Pattern pattern = Pattern.compile("[0-9]*", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher("88");
                boolean matchFound = matcher.find();
                System.out.println("matchfound? "+matchFound);
                //
                saveButton.setEnabled(false);
                creator.setTextFieldPanel(classLabelPanel);
                System.out.println("classlabelpanel save print::::::: "+classLabelPanel.getComponentCount());
                creator.writeTextToFile("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() +"/"+classList.get(set.getClassListIndex())+ ".txt", creator.getTextFieldContainer());
            }
        });
        
        JButton nextButton = creator.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //write to file.
                hideWindow();

                
                set.incrementClassListIndex();
                if (set.getClassListIndex()+1 <= set.getClassList().size()) {
                    StudentStatCollect statCollect = new StudentStatCollect(window, newUser, studentOrTeacher, existingOrNew, set);
                }

                else {
                PrintStudentGrades print = new PrintStudentGrades(main, studentOrTeacher,existingOrNew);
                }
            }
        });
        backNextButtonsPanel = creator.makeBackNextButtonsPanel(backButtonPanel, saveButtonPanel, nextButtonPanel);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
    }

   private void createNewTypeButton() {
       newTypeButton = new JButton("New Type");
       newTypeButton.setPreferredSize(new Dimension(90, 50));
       JPanel newTypeButtonPanel = new JPanel(new BorderLayout());

       JButton delTypeButton = new JButton("Delete Type");
       delTypeButton.setPreferredSize(new Dimension(90, 50));
       JPanel delTypeButtonPanel = new JPanel(new BorderLayout());

       newTypeButtonPanel.add(newTypeButton,BorderLayout.NORTH);
       delTypeButtonPanel.add(delTypeButton,BorderLayout.NORTH);

       newDelContainerFlow = new JPanel(new FlowLayout(FlowLayout.LEFT,0,5));
       JPanel newDelContainer = new JPanel(new GridLayout(2,1,0,5));
       newDelContainer.add(newTypeButtonPanel);
       newDelContainer.add(delTypeButtonPanel);

       newDelContainerFlow.add(newDelContainer);

       //gridlayout allows any time of resizing
       //flowlayout allows resizing of width

       newTypeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            //typeNumber++;
            newSet();
            System.out.println("ClassLabelPanel after adding: "+ allBoxesPanel.getComponentCount());
        }
        });

        delTypeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("deltypebutton hitt");
                System.out.println("deleteclasslist"+ classLabelPanel.getComponentCount());
                if (classLabelPanel.getComponentCount() >= 8) {
                    creator.deleteTextBox(window, classLabelPanel);
                    creator.deleteTextBox(window, classLabelPanel);
                    creator.deleteTextBox(window, classLabelPanel);
                    creator.saveButtonEnable();
                }
                System.out.println("deleteclasslistafter...."+ classLabelPanel.getComponentCount());
        }
        });

       window.add(newDelContainerFlow, BorderLayout.EAST);
       creator.windowFix(window);
   }

    // For i in classes, give classes : class1 
    //     for j in types :
    //         type1gradeBox.


    //read classes array, first five classes
    private void DisplayClasses() {
        ///
        //creditTypeBox();
        ///
        String filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() +"/class"+ ".txt";
        ArrayList<String> classList = set.getClassList();
        //will be updated later

        //for (String className : classList) {
        readClass(classList);
    }

    private void readClass(ArrayList<String> classList) { 
        System.out.println("test1: in read class");
        classLabel(classList);
        creditTypeBox();
        newSet();

        //typeNumber++;
        container.add(classLabelPanel);
        //set.setTextFieldPanel(classLabelPanel);
        //set.setTextFieldContainer(container);

        System.out.println("classLabelPanelcomps"+classLabelPanel.getComponentCount());
        System.out.println("heloooooo"+ allBoxesPanel.getComponentCount());
        
        window.add(classLabelPanel, BorderLayout.CENTER);
        //window.add(allBoxesPanel, BorderLayout.CENTER);
        creator.windowFix(window);
    }

    private void classLabel(ArrayList<String> classList) {
        if (numOfBoxes <= maxBoxes) {
        creator.hideContainer();
        JPanel northClassPanelContainer = new JPanel(new BorderLayout());
        JPanel classLabelPanelContainer = new JPanel(new BorderLayout());
        //JLabel classLabel = new JLabel("h");
        JLabel classLabel = new JLabel(set.getClassList().get(set.getClassListIndex())); //new JLabel("h"); 
        classLabelPanelContainer.add(classLabel);
        classLabelPanelContainer.setPreferredSize(new Dimension(155,50));
        northClassPanelContainer.add(classLabelPanelContainer, BorderLayout.NORTH);
        classLabelPanel.add(northClassPanelContainer);
        //
        northClassPanelContainer.setBackground(Color.PINK);
        //
        classLabel.setVisible(true);
        creator.windowFix(window);
        numOfBoxes++;
        }
    }

    private void gradePercentageBox() {
        if (numOfBoxes <= maxBoxes) {
        JPanel northGradePercentagePanel = new JPanel(new BorderLayout());
        JPanel gradePercentagePanel = new JPanel(new BorderLayout());
        JTextField gradePercentage = creator.createTextBox(window, "Percentage of Grade", 80, 10); //the sol

        //allBoxesPanel.add(gradePercentage);
        System.out.println("Components after adding: "+ allBoxesPanel.getComponentCount());

        gradePercentagePanel.add(gradePercentage); //issue
        gradePercentagePanel.setPreferredSize(new Dimension(155,50));
        northGradePercentagePanel.add(gradePercentagePanel, BorderLayout.NORTH);
        classLabelPanel.add(northGradePercentagePanel);


        ////
        //gradePercentage.setPreferredSize(new Dimension(155,50));
        ////
        creator.windowFix(window); 
        numOfBoxes++;
        }

    }

    private void gradeTypeBox() {
        if (numOfBoxes <= maxBoxes) {
        JPanel northTypePanel = new JPanel(new BorderLayout());
        JPanel gradeTypePanel = new JPanel(new BorderLayout());
        typeNumber++;
        JTextField gradeType = creator.createTextBox(window, "Grade Type "+typeNumber, 10, 10);
        allBoxesPanel.add(gradeType);
        System.out.println("Components after addingg: "+ allBoxesPanel.getComponentCount());
        System.out.println("ClassLabelPanel components: "+ classLabelPanel.getComponentCount());

        gradeTypePanel.add(gradeType);
        gradeTypePanel.setPreferredSize(new Dimension( 155,50));
        northTypePanel.add(gradeTypePanel, BorderLayout.NORTH);
        classLabelPanel.add(northTypePanel);
        creator.windowFix(window);
        numOfBoxes++;
        }
    }

    private void creditTypeBox() {
        if (numOfBoxes <= maxBoxes) {
        northCreditsPanel = new JPanel(new BorderLayout());
        JPanel creditTypePanel = new JPanel(new BorderLayout());
        JTextField creditType = creator.createTextBox(window, "Credits (optional)", 50, 50);
        
        allBoxesPanel.add(creditType);
        System.out.println("Components after adding: "+ allBoxesPanel.getComponentCount());
        
        creditTypePanel.add(creditType);
        creditTypePanel.setPreferredSize(new Dimension(155,50));
        northCreditsPanel.add(creditTypePanel, BorderLayout.NORTH);
        classLabelPanel.add(northCreditsPanel);
        creator.windowFix(window);
        numOfBoxes++;
        }
    }

    private void gradesBox() {
        if (numOfBoxes <= maxBoxes) {
            northCreditsPanel = new JPanel(new BorderLayout());
            JPanel creditTypePanel = new JPanel(new BorderLayout());
            JTextField creditType = creator.createTextBox(window, "Grades(format:# # #)", 10, 10);
            
            allBoxesPanel.add(creditType);
            System.out.println("Components after adding: "+ allBoxesPanel.getComponentCount());
            
            creditTypePanel.add(creditType);
            creditTypePanel.setPreferredSize(new Dimension(155,50));
            northCreditsPanel.add(creditTypePanel, BorderLayout.NORTH);
            classLabelPanel.add(northCreditsPanel);
            creator.windowFix(window);
            numOfBoxes++;
            }
    }

    private void writeType() {
        creator.setTextFieldPanel(classLabelPanel);
        ArrayList<String> classList = set.getClassList();
        creator.writeTextToFile("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() +"/"+classList.get(set.getClassListIndex())+ ".txt", creator.getTextFieldContainer());
        System.out.println("Complete! Class list is: "+ set.getFinalClassList());
        set.setClassList(classList);
    }


    private void hideWindow() {
        backNextButtonsPanel.setVisible(false);
        newDelContainerFlow.setVisible(false);
        classLabelPanel.setVisible(false);
        
    }

    private void debugTest(JPanel classLabePanel) {
        // Iterate over the components of the classLabelPanel
        for (Component component : classLabelPanel.getComponents()) {
            // Check if the component is an instance of JLabel
            if (component instanceof JLabel) {
                // Cast the component to JLabel
                JLabel label = (JLabel) component;
                // Print the text of the label
                System.out.println("printing labels....:::>..."+label.getText());
    }
}

    }

    private void newSet() {
        gradeTypeBox();
        gradePercentageBox();
        gradesBox();
    }

    // private void calculate() {
    //     //parse the grade situation. but then needs correct input.
    // }

    ////
    }