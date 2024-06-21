package main.view.student;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.model.Set;
import main.controller.Creator;
import main.controller.FileHandler;

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

    public StudentStatCollect() {
        studentStatCollectLaunch();
    }

    public void studentStatCollectLaunch() {
        this.set = Set.getInstance();
        set.setCurrentClass("StudentStatCollect.java");
        this.window = set.getWindow();
        finalClassList = set.getFinalClassList();
        System.out.println("final class list issssss before: "+ finalClassList);
        finalClassList = set.getFinalClassList();
        System.out.println("final class list issssss: "+ finalClassList);
        creator = new Creator();
        fileHandler = new FileHandler();
        createNewTypeButton();
        buttonSetUpAction();
        //DisplayClasses();
        window.setTitle("StudentStatCollect");
        //window.setPreferredSize(new Dimension(800, 1000));
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
                    StudentClasses studentClasses = new StudentClasses();
                    studentClasses.studentClassesLaunch();
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
        addLoadedBoxes();
    }

    public void addLoadedBoxes() {
        //textBoxPanel.add(creator.typeBox(set.getFinalClassList().get(0), "JLabel", true));
        //System.out.println(set.getFinalClassList().get(0));
        textBoxPanel.add(creator.typeBox(set.getFinalClassList().get(0), "JLabel", true));
    
        String filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+set.getUsername()+"/"+set.getFinalClassList().get(set.getClassListIndex())+".txt";
        JPanel testPanel = fileHandler.loadTextboxes(filePath);
        int numberOfComponents = testPanel.getComponentCount();
        numOfBoxes += numberOfComponents;
        for (int i = 0; i < numberOfComponents; i++) {
            textBoxPanel.add(testPanel.getComponent(0));
        }
        classLabelPanel.add(textBoxPanel);
        window.add(classLabelPanel);
        creator.windowFix();
        if (numOfBoxes == 0) {
            boxStarterPack();
        }    
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
        // JPanel test = (JPanel) textBoxPanel.getComponent(0);
        // JPanel testTwo = (JPanel) test.getComponent(0);
        // JLabel testThree = (JLabel) testTwo.getComponent(0);
        // String testText = testThree.getText();
        // System.out.println("textBoxPanelComponens: "+ textBoxPanel.getComponentCount() +"..."+ testText);
        
        //if (textBoxPanel.getComponentCount() == 5 || 8 || 11 || 14 || 17 || 20 || 23 || 26 || 29 || 32) {
        //if ((textBoxPanel.getComponentCount() - 5) % 3 == 0) { //only want to write if 
        System.out.println("testtesttest: "+ set.getCurrentClass());
        creator.writeTextToFileWithoutAppend("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() +"/"+finalClassList.get(set.getClassListIndex())+ ".txt", set.getTextFieldPanel());
       // }
    }

    private void nextButtonAction(JButton nextButton) {
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveButtonAction();
                set.incrementClassListIndex();
                if (set.getClassListIndex()+1 <= set.getFinalClassList().size()) {
                    visitNextStudentClass();
                }
                if (set.getClassListIndex()+1 > set.getFinalClassList().size() && creator.getHasPlaceholder() == false) {
                    hideWindow();
                    System.out.println("test 1 dont want: "+ creator.getHasPlaceholder());
                    new PrintStudentGrades(set.getWindow(), set.getStudentOrTeacher(), set.getExistingOrNew());
                }
            }
        });
    }

    private void visitNextStudentClass() {
        //readClass(finalClassList);
        String filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+set.getUsername()+"/"+set.getFinalClassList().get(set.getClassListIndex())+".txt";
        if (fileHandler.fileExists(filePath)) {
            textBoxPanelReset();
            boxManageCreate(set.getFinalClassList().get(set.getClassListIndex()), "JLabel"); //displays class label 
            JPanel testPanel = fileHandler.loadTextboxes(filePath);
            int numberOfComponents = testPanel.getComponentCount();
            for (int i = 0; i < numberOfComponents; i++) {
                textBoxPanel.add(testPanel.getComponent(0));
            }
            classLabelPanel.add(textBoxPanel);
            if (textBoxPanel.getComponentCount() <= 1) { //The reason it is <=1 and not 0, is to account for the classLabel
                boxStarterPack();
            }
            creator.windowFix();
        }
        else { //first time visiting next class
            hideWindow();
            StudentStatCollect studentStatCollect = new StudentStatCollect();
            studentStatCollect.DisplayClasses();
        }

    }

    private void boxStarterPack() {
        boxManageCreate("Credits (optional)", "JTextField");
        newSet();
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
       creator.windowFix();
   }

   private JPanel deleteButtonPanel() {
    JButton deleteTypeButton = new JButton("Delete Type");
        deleteTypeButton.setPreferredSize(new Dimension(90, 50));
        deleteTypeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (textBoxPanel.getComponentCount() >= 8) {
                creator.deleteTextBox(textBoxPanel);
                creator.deleteTextBox(textBoxPanel);
                creator.deleteTextBox(textBoxPanel);
                numOfBoxes = numOfBoxes - 3;
                typeNumber--;
                
                creator.saveButtonEnable();
            }
    }
    });  
    JPanel delTypeButtonPanel = new JPanel(new BorderLayout());
    delTypeButtonPanel.add(deleteTypeButton,BorderLayout.NORTH);
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
    public void DisplayClasses() {
        set.getUsername();
        ArrayList<String> typeList = set.getCurrentPanelList();
        readClass(typeList);
    }

    private void readClass(ArrayList<String> typeList) { 
        System.out.println("readclass test:"+ set.getFinalClassList()+"index: "+set.getClassListIndex());
        boxManageCreate(set.getFinalClassList().get(set.getClassListIndex()), "JLabel");
        boxManageCreate("Credits (optional)", "JTextField");
        newSet();
        container.add(classLabelPanel);
        window.add(classLabelPanel, BorderLayout.CENTER);
        creator.windowFix();
    }

    private void hideWindow() {
        backNextButtonsPanel.setVisible(false);
        newDelContainerFlow.setVisible(false);
        classLabelPanel.setVisible(false);
        
    }

    private void boxManageCreate(String placeholder, String type) {
        if (numOfBoxes <= maxBoxes) {
            textBoxPanel.add(creator.typeBox(placeholder, type, false));
            classLabelPanel.add(textBoxPanel);
            creator.windowFix();
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