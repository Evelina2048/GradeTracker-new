package main.view.student;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Component;

import java.util.ArrayList;
import main.view.NewUser;
import main.view.Creator;
import main.view.Set;

import java.awt.Container;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentStatCollect extends JFrame {
    private JFrame window;
    private Creator creator;
    private JPanel backNextButtonsPanel;
    private JButton newTypeButton;
    private Set set;
    private JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT));
    //private JPanel classLabelPanelContainer = new JPanel(new BorderLayout());
    private JPanel newDelContainerFlow;
    private JPanel classLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    private int typeNumber = 0;
    private int numOfBoxes = 0;

    private int maxBoxes = 25;
    
    private JPanel northCreditsPanel;
    //private JPanel northNewTypePanel;
    private JPanel allBoxesPanel = new JPanel();
    //private ArrayList<String> typeList;
    private ArrayList<String> typeList;
    private ArrayList<String> finalClassList;

    public StudentStatCollect(JFrame main,NewUser newUser, String studentOrTeacher, String existingOrNew, Set set) {
        this.window = main;
        this.set = set;
        System.out.println("final class list issssss before: "+ finalClassList);
        finalClassList = set.getFinalClassList();
        System.out.println("final class list issssss: "+ finalClassList);
        creator = new Creator(set);
        studentStatCollectLaunch(window);
        createNewTypeButton();
        buttonSetUpAction(main, newUser, studentOrTeacher, existingOrNew);
        DisplayClasses();
    }

    public void studentStatCollectLaunch(JFrame window) {
        //List = set.getCurrentPanelList();
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

                if (set.getClassListIndex() == 0) { //case for back to classes
                    System.out.println("I here");
                    StudentClasses studentClasses = new StudentClasses(main, newUser, studentOrTeacher, existingOrNew, set);
                    studentClasses.studentClassesLaunch(main, newUser, studentOrTeacher, existingOrNew, set);
                }

                //case for previous class
            }
        });

        JButton saveButton = creator.saveButtonCreate();
        JPanel saveButtonPanel = new JPanel();
        saveButton.setEnabled(false);
        saveButtonPanel.add(saveButton);
        saveAction(saveButton);
        saveButton.setEnabled(false);
        
        JButton nextButton = creator.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //write to file.
                hideWindow();
                saveAction(saveButton);
                
                set.incrementClassListIndex();
                if (set.getClassListIndex()+1 <= set.getFinalClassList().size()) {
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

    private void saveAction(JButton saveButton) {
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check if textbox is vaild
                Pattern pattern = Pattern.compile("^(?:[0-9]*(?:.[0-9]+))*\s*$|^[0-9]*\s*$", Pattern.CASE_INSENSITIVE);

                correctGradeFormatChecker(pattern);

                creator.setTextFieldPanel(classLabelPanel);
                creator.writeTextToFile("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() +"/"+finalClassList.get(set.getClassListIndex())+ ".txt", creator.getTextFieldContainer());
            }
        });
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
                    numOfBoxes = numOfBoxes - 3;
                    typeNumber--;
                    
                    creator.saveButtonEnable();
                }
                System.out.println("deleteclasslistafter...."+ classLabelPanel.getComponentCount());
        }
        });

       window.add(newDelContainerFlow, BorderLayout.EAST);
       creator.windowFix(window);
   }

   private void correctGradeFormatChecker(Pattern pattern) {
        int index = 4;
        for (int i=1; i<=8; i++) { //max num of grade type
            //if it exists. otherwise, bye bye.
            String text = goIntoPanel(classLabelPanel, index); //will return a text box. probably just first for now??? //base case 5
            if (text.equals("does not exist")) {
                System.out.println("fail");
                break;
            }
            index = index + 3;
            System.out.println("text before matcher works?"+text);
            Matcher matcher = pattern.matcher(text);
            Boolean matcherBoolean = matcher.find();

            System.out.println("matcher works?: "+ matcherBoolean);
        }
    }

    private String goIntoPanel(JPanel panel, int index) {
        Container container = panel;
        System.out.println("component count of panel: "+ container.getComponentCount());

        //check component is not null
        if (index >= container.getComponentCount()) {
            System.out.println("component is null. Function: goIntoPanel. StudentStatCollect class");
            return "does not exist";
        }
        Component component = container.getComponent(index);


        if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                String text = textField.getText();
                return text;
            } 

        else if (component instanceof JPanel) {
                JPanel jpanel = (JPanel) component;
                String text = goIntoPanel(jpanel, 0);

                if (text != null) {
                    return text;
                }
            }

            System.out.println("none of these" +component.getClass().getName());
            return "something went wrong StudentStatCollect";

    }

    //read classes array, first five classes
    private void DisplayClasses() {
        String filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() +"/class"+ ".txt";
        ArrayList<String> typeList = set.getCurrentPanelList();

        readClass(typeList);
    }

    private void readClass(ArrayList<String> typeList) { 
        System.out.println("test1: in read class");
        //classLabel(typeList);
        boxManageCreate(set.getFinalClassList().get(set.getClassListIndex()), "JLabel");
        boxManageCreate("Credits (optional)", "JTextField");
        //creditTypeBox();
        newSet();

        container.add(classLabelPanel);
        
        window.add(classLabelPanel, BorderLayout.CENTER);
        creator.windowFix(window);
    }

    private void classLabel(ArrayList<String> typeList) {
        // if (numOfBoxes <= maxBoxes) {
        //     creator.hideContainer();
        //     JPanel northClassPanelContainer = new JPanel(new BorderLayout());
        //     JPanel classLabelPanelContainer = new JPanel(new BorderLayout());
        //     JLabel classLabel = new JLabel(set.getFinalClassList().get(set.getClassListIndex()));
        //     classLabelPanelContainer.add(classLabel);
        //     classLabelPanelContainer.setPreferredSize(new Dimension(155,50));
        //     northClassPanelContainer.add(classLabelPanelContainer, BorderLayout.NORTH);
        //     classLabelPanel.add(northClassPanelContainer);
        //     northClassPanelContainer.setBackground(Color.PINK);
        //     classLabel.setVisible(true);
        //creator.windowFix(window);
        //numOfBoxes++;


            ////
            // if (numOfBoxes <= maxBoxes) {
            //     JPanel northTypePanel = creator.typeBox(window, placeholder);
            //     classLabelPanel.add(northTypePanel);
            //     typeNumber++;
            //     creator.windowFix(window);
            //     numOfBoxes++;
            // }
            ///
        //}
    }

    // private void gradePercentageBox() {
    //     if (numOfBoxes <= maxBoxes) {
    //         JPanel northGradePercentagePanel = new JPanel(new BorderLayout());
    //         JPanel gradePercentagePanel = new JPanel(new BorderLayout());
    //         JTextField gradePercentage = creator.createTextBox(window, "Percentage of Grade", 80, 10); //the sol

    //         gradePercentagePanel.add(gradePercentage);
    //         gradePercentagePanel.setPreferredSize(new Dimension(155,50));
    //         northGradePercentagePanel.add(gradePercentagePanel, BorderLayout.NORTH);
    //         classLabelPanel.add(northGradePercentagePanel);
    //         creator.windowFix(window); 
    //         numOfBoxes++;
    //     }

    // }

    // private void gradeTypeBox() {
    //     if (numOfBoxes <= maxBoxes) {
    //         JPanel northTypePanel = new JPanel(new BorderLayout());
    //         JPanel gradeTypePanel = new JPanel(new BorderLayout());
    //         typeNumber++;
    //         JTextField gradeType = creator.createTextBox(window, "Grade Type "+typeNumber, 10, 10);
    //         allBoxesPanel.add(gradeType);
    //         System.out.println("Components after addingg: "+ allBoxesPanel.getComponentCount());
    //         System.out.println("ClassLabelPanel components: "+ classLabelPanel.getComponentCount());

    //         gradeTypePanel.add(gradeType);
    //         gradeTypePanel.setPreferredSize(new Dimension( 155,50));
    //         northTypePanel.add(gradeTypePanel, BorderLayout.NORTH);
    //         classLabelPanel.add(northTypePanel);
    //         creator.windowFix(window);
    //         numOfBoxes++;
    //     }
    // }

    // private void creditTypeBox() {
    //     if (numOfBoxes <= maxBoxes) {
    //         northCreditsPanel = new JPanel(new BorderLayout());
    //         JPanel creditTypePanel = new JPanel(new BorderLayout());
    //         JTextField creditType = creator.createTextBox(window, "Credits (optional)", 50, 50);
            
    //         allBoxesPanel.add(creditType);
    //         System.out.println("Components after adding: "+ allBoxesPanel.getComponentCount());
            
    //         creditTypePanel.add(creditType);
    //         creditTypePanel.setPreferredSize(new Dimension(155,50));
    //         northCreditsPanel.add(creditTypePanel, BorderLayout.NORTH);
    //         classLabelPanel.add(northCreditsPanel);
    //         creator.windowFix(window);
    //         numOfBoxes++;
    //     }
    // }

    // private void gradesBox() {
    //     if (numOfBoxes <= maxBoxes) {
    //         northCreditsPanel = new JPanel(new BorderLayout());
    //         JPanel creditTypePanel = new JPanel(new BorderLayout());
    //         JTextField creditType = creator.createTextBox(window, "Grades(format:# # #)", 10, 10);
            
    //         allBoxesPanel.add(creditType);
    //         System.out.println("Components after adding: "+ allBoxesPanel.getComponentCount());
            
    //         creditTypePanel.add(creditType);
    //         creditTypePanel.setPreferredSize(new Dimension(155,50));
    //         northCreditsPanel.add(creditTypePanel, BorderLayout.NORTH);
    //         classLabelPanel.add(northCreditsPanel);
    //         creator.windowFix(window);
    //         numOfBoxes++;
    //         }
    // }

    private void writeType() {
        creator.setTextFieldPanel(classLabelPanel);
        ArrayList<String> typeList = set.getCurrentPanelList();
        creator.writeTextToFile("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() +"/"+typeList.get(set.getClassListIndex())+ ".txt", creator.getTextFieldContainer());
        System.out.println("Complete! Class list is: "+ set.getFinalClassList());
        set.setClassList(typeList);
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
    private void boxManageCreate(String placeholder, String type) {
        if (numOfBoxes <= maxBoxes) {
            creator.hideContainer();
            JPanel northTypePanel = creator.typeBox(window, placeholder, type);
            classLabelPanel.add(northTypePanel);
            typeNumber++;
            creator.windowFix(window);
            numOfBoxes++;
        }
    }

    private void newSet() {
        boxManageCreate("Grade Type "+typeNumber, "JTextField");
        //boxManageCreate("Percentage of Grade");
        boxManageCreate("Percentage of Grade", "JTextField");
        boxManageCreate("Grades(format:# # #)", "JTextField");
        //gradesBox();
    }

    // private void calculate() {
    //     //parse the grade situation. but then needs correct input.
    // }

    ////
    }