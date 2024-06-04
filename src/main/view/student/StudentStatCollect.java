package main.view.student;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.util.ArrayList;
import main.view.Creator;
import main.view.Set;
import main.view.FileHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentStatCollect extends JFrame {
    private JFrame window;
    private Creator creator;
    private JPanel backNextButtonsPanel;
    private JButton newTypeButton;
    private Set set;
    private JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel newDelContainerFlow;
    private JPanel classLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel allBoxesPanel = new JPanel();
    private ArrayList<String> finalClassList;
    private FileHandler fileHandler;
    private JPanel textBoxPanel = new JPanel(new GridLayout(0,4,5,5));

    private int typeNumber = 0;
    private int numOfBoxes = 0;
    private int maxBoxes = 25;

    public StudentStatCollect(Set set) {
        studentStatCollectLaunch(set);
    }

    public void studentStatCollectLaunch(Set set) {
        this.window = set.getWindow();
        this.set = set;
        System.out.println("final class list issssss before: "+ finalClassList);
        finalClassList = set.getFinalClassList();
        System.out.println("final class list issssss: "+ finalClassList);
        creator = new Creator(set);
        fileHandler = new FileHandler();
        createNewTypeButton();
        buttonSetUpAction();
        DisplayClasses();
        window.setTitle("StudentStatCollect");
        window.setPreferredSize(new Dimension(1000, 1000));
    }

    public void buttonSetUpAction() {
        JButton backButton = creator.backButtonCreate();
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        backAction(backButton);
        JButton saveButton = creator.saveButtonCreate();
        JPanel saveButtonPanel = new JPanel();
        saveButton.setEnabled(false);
        saveButtonPanel.add(saveButton);
        saveAction(saveButton);
        saveButton.setEnabled(false);
        
        JButton nextButton = creator.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        nextButtonAction(nextButton);
        backNextButtonsPanel = creator.makeBackNextButtonsPanel(backButtonPanel, saveButtonPanel, nextButtonPanel);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
    }

    private void textBoxPanelReset() {
        textBoxPanel.removeAll();
        textBoxPanel.revalidate();
        textBoxPanel.repaint();
    }
    
    private void backAction(JButton backButton) {
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (set.getClassListIndex() == 0) { //case for back to classes
                    hideWindow();
                    StudentClasses studentClasses = new StudentClasses(set);
                    studentClasses.studentClassesLaunch(set);
                    saveButtonAction();
                }
                else if (set.getClassListIndex() > 0) {
                    goToPreviousClasses();              
                }
            }
        });
    }

    private void goToPreviousClasses() {
        System.out.println("there are previous classes");
        saveButtonAction();
        set.decrementClassListIndex();
        classLabelPanel.removeAll();
        classLabelPanel.revalidate();
        classLabelPanel.repaint();
        textBoxPanelReset();
        new JPanel(new GridLayout(0,4,5,5));
        new JPanel(new FlowLayout(FlowLayout.LEFT));
        boxManageCreate(set.getFinalClassList().get(set.getClassListIndex()), "JLabel"); //displays class label 
        String filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+set.getUsername()+"/"+set.getFinalClassList().get(set.getClassListIndex())+".txt";
        JPanel testPanel = fileHandler.loadTextboxes(window, filePath, set);
        int numberOfComponents = testPanel.getComponentCount();
        numOfBoxes += numberOfComponents;
        for (int i = 0; i < numberOfComponents; i++) {
            textBoxPanel.add(testPanel.getComponent(0));
        }
        classLabelPanel.add(textBoxPanel);
        creator.windowFix(window);    
    }

    private void saveAction(JButton saveButton) {
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check if textbox is vaild
                saveButton.setEnabled(false);
                saveButtonAction();
            }
        });
    }

    private void saveButtonAction() {
        Pattern pattern = Pattern.compile("^(?:[0-9]*(?:.[0-9]+))*\s*$|^[0-9]*\s*$", Pattern.CASE_INSENSITIVE);
        correctGradeFormatChecker(pattern);

        set.setTextFieldPanel(textBoxPanel);
        creator.writeTextToFile("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() +"/"+finalClassList.get(set.getClassListIndex())+ ".txt", set.getTextFieldPanel());
    }

    private void nextButtonAction(JButton nextButton) {
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveButtonAction();
                set.incrementClassListIndex();
                if (set.getClassListIndex()+1 <= set.getFinalClassList().size()) {
                    visitNextStudentClass();
                }
                else {
                    hideWindow();
                    new PrintStudentGrades(set.getWindow(), set.getStudentOrTeacher(), set.getExistingOrNew());
                }
            }
        });
    }

    private void visitNextStudentClass() {
        String filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+set.getUsername()+"/"+set.getFinalClassList().get(set.getClassListIndex())+".txt";
        if (fileHandler.fileIsNotEmpty(filePath)) {
            textBoxPanelReset();
            boxManageCreate(set.getFinalClassList().get(set.getClassListIndex()), "JLabel"); //displays class label 
            JPanel testPanel = fileHandler.loadTextboxes(window, filePath, set);
            int numberOfComponents = testPanel.getComponentCount();
            for (int i = 0; i < numberOfComponents; i++) {
                textBoxPanel.add(testPanel.getComponent(0));
            }
            classLabelPanel.add(textBoxPanel);
            creator.windowFix(window);
        }
        else { //first time visiting next class
            hideWindow();
            new StudentStatCollect(set);
        }
    }

   private void createNewTypeButton() {
       newTypeButton = new JButton("New Type");
       newTypeButton.setPreferredSize(new Dimension(90, 50));
       JPanel newTypeButtonPanel = new JPanel(new BorderLayout());
       newTypeButtonPanel.add(newTypeButton,BorderLayout.NORTH);

       newDelContainerFlow = new JPanel(new FlowLayout(FlowLayout.LEFT,0,5));
       JPanel newDelContainer = new JPanel(new GridLayout(2,1,0,5));
       //gridlayout allows any time of resizing
       //flowlayout allows resizing of width
       newTypeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            newSet();
            System.out.println("ClassLabelPanel after adding: "+ allBoxesPanel.getComponentCount());
        }
        });
       JPanel delTypeButtonPanel = deleteButtonPanel();
       newDelContainer.add(newTypeButtonPanel);
       newDelContainer.add(delTypeButtonPanel);
       newDelContainerFlow.add(newDelContainer);
       window.add(newDelContainerFlow, BorderLayout.EAST);
       creator.windowFix(window);
   }

   private JPanel deleteButtonPanel() {
    JButton delTypeButton = new JButton("Delete Type");
        delTypeButton.setPreferredSize(new Dimension(90, 50));
        delTypeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (textBoxPanel.getComponentCount() >= 8) {
                creator.deleteTextBox(window, textBoxPanel);
                creator.deleteTextBox(window, textBoxPanel);
                creator.deleteTextBox(window, textBoxPanel);
                numOfBoxes = numOfBoxes - 3;
                typeNumber--;
                
                creator.saveButtonEnable();
            }
    }
    });  
    JPanel delTypeButtonPanel = new JPanel(new BorderLayout());
    delTypeButtonPanel.add(delTypeButton,BorderLayout.NORTH);
    return delTypeButtonPanel;
   }

   private void correctGradeFormatChecker(Pattern pattern) {
        int index = 4;
        for (int i=1; i<=8; i++) { //max num of grade type
            String text = creator.goIntoPanel(classLabelPanel, index);
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
    //read classes array, first five classes
    private void DisplayClasses() {
        set.getUsername();
        ArrayList<String> typeList = set.getCurrentPanelList();
        readClass(typeList);
    }

    private void readClass(ArrayList<String> typeList) { 
        boxManageCreate(set.getFinalClassList().get(set.getClassListIndex()), "JLabel");
        boxManageCreate("Credits (optional)", "JTextField");
        newSet();
        container.add(classLabelPanel);
        window.add(classLabelPanel, BorderLayout.CENTER);
        creator.windowFix(window);
    }

    private void hideWindow() {
        backNextButtonsPanel.setVisible(false);
        newDelContainerFlow.setVisible(false);
        classLabelPanel.setVisible(false);
        
    }

    private void boxManageCreate(String placeholder, String type) {
        if (numOfBoxes <= maxBoxes) {
            textBoxPanel.add(creator.boxCreate(window, placeholder, type));
            classLabelPanel.add(textBoxPanel);
            creator.windowFix(window);
            numOfBoxes++;
        }
    }

    private void newSet() {
        typeNumber++;
        boxManageCreate("Grade Type "+typeNumber, "JTextField");
        boxManageCreate("Percentage of Grade", "JTextField");
        boxManageCreate("Grades(format:# # #)", "JTextField");
    }

    // private void calculate() {
    //     //parse the grade situation. but then needs correct input.
    // }

    ////
    }