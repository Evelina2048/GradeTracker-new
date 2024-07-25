package main.view.student;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Color;

import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.model.Set;
import main.model.SetState;
import main.model.SetUserInformation;
import main.model.SetList;

import main.controller.Creator;
import main.model.GoIntoPanel;
import main.controller.Decorator;
import main.controller.FileHandling;
import main.controller.FileWriting;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import main.controller.CreateButton;

public class StudentStatCollect extends JFrame {
    private JFrame window;
    private Creator create;
    private GoIntoPanel goIntoPanel;
    private JPanel backNextButtonsPanel;
    private JButton newTypeButton;
    private JButton swapClassesButton;
    private Set set;
    private SetState setState;
    private SetList setList;
    private Color lightgrayColor = Color.decode("#AFA2A2");
    
    private SetUserInformation setUserInformation;

    private CreateButton createButton = new CreateButton();
    private FileWriting fileWrite = new FileWriting();
    private JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel newDelContainerFlow;
    private JPanel classLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel allBoxesPanel = new JPanel();
    private ArrayList<String> finalClassList;
    private FileHandling fileHandler;
    private JPanel textBoxPanel = new JPanel(new GridLayout(0,4,5,5));

    private int typeNumber = 0;
    private int numOfBoxes = 0;
    private int maxBoxes = 25;

    public StudentStatCollect() {
        studentStatCollectLaunch();
    }

    public void studentStatCollectLaunch() {
        this.set = Set.getInstance();
        this.setState = SetState.getInstance();
        this.setUserInformation = SetUserInformation.getInstance();
        this.setList = SetList.getInstance();

        setState.setCurrentClass("StudentStatCollect.java");
        window = set.getWindow();
        container.setName("contianer");
        //container.setBackground(Color.pink);
        textBoxPanel.setName("gridlayout textboxpanel");
        textBoxPanel.setBackground(lightgrayColor);
        classLabelPanel.setName("classLabelPanel student stat");
        EnterAction enterAction = new EnterAction();
        window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        window.getRootPane().getActionMap().put("enterAction", enterAction);

        finalClassList = setList.getFinalClassList();
        System.out.println("final class list issssss before: "+ finalClassList);
        finalClassList = setList.getFinalClassList();
        System.out.println("final class list issssss: "+ finalClassList);
        create = new Creator();
        create.hideContainer();

        goIntoPanel = new GoIntoPanel();
        fileHandler = new FileHandling();
        
        createNewTypeButton();
        buttonSetUpAction();
        window.setTitle("StudentStatCollect");
    }

    public void buttonSetUpAction() {
        JButton backButton = createButton.backButtonCreate();
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        backAction(backButton);
        JButton saveButton = createButton.saveButtonCreate();
        JPanel saveButtonPanel = new JPanel();
        saveButton.setEnabled(false);
        saveButtonPanel.add(saveButton);
        saveAction(saveButton);
        saveButton.setEnabled(false);
        
        JButton nextButton = createButton.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        nextButtonAction(nextButton);
        backNextButtonsPanel = createButton.makeBackNextButtonsPanel(backButtonPanel, saveButtonPanel, nextButtonPanel);
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
                if(setState.getCanContinue()) {
                    if (setState.getClassListIndex() == 0) { //case for back to classes
                        hideWindow();
                        StudentClasses studentClasses = new StudentClasses();
                        studentClasses.studentClassesLaunch();
                        saveButtonAction();
                    }
                    else if (setState.getClassListIndex() > 0) {
                        goToPreviousClasses();              
                    }
                }
            }
        });
    }

    private void goToPreviousClasses() {
        saveButtonAction();
        if (setState.getCanContinue()) {
            System.out.println("there are previous classes");
            setState.decrementClassListIndex();
            classLabelPanel.removeAll();
            classLabelPanel.revalidate();
            classLabelPanel.repaint();
            textBoxPanelReset();

            addLoadedBoxes();
        }
    }

    public void addLoadedBoxes() {
        String filePath = setUserInformation.getPathToClassInformationFileWithIndex();
        
        JPanel loadedBoxesPanel = fileHandler.loadTextboxes(filePath);
        int numberOfComponents = loadedBoxesPanel.getComponentCount();
        numOfBoxes += numberOfComponents;
        for (int i = 0; i < numberOfComponents; i++) {
            textBoxPanel.add(loadedBoxesPanel.getComponent(0));
        }
        classLabelPanel.add(textBoxPanel);
        //classLabelPanel.setBackground(Color.PINK);
        classLabelPanel.setBackground(lightgrayColor);
        window.add(classLabelPanel);
        
        create.windowFix();
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

        setState.setTextFieldPanel(textBoxPanel);
        
        //if (textBoxPanel.getComponentCount() == 5 || 8 || 11 || 14 || 17 || 20 || 23 || 26 || 29 || 32) {
        //if ((textBoxPanel.getComponentCount() - 5) % 3 == 0) { //only want to write if 
        System.out.println("testtesttest: "+ setState.getCurrentClass());
        set.setFilePath("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + setUserInformation.getUsername() +"/ClassInformation/"+finalClassList.get(setState.getClassListIndex())+ ".txt");
        
        create.setTextFieldContainer(setState.getTextFieldPanel());
        fileWrite.writeTextToFile();
       // }
    }

    private void nextButtonAction(JButton nextButton) {
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doNextButtonProcedure();
            }
        });
    }

    public void doNextButtonProcedure() {
        System.out.println("6666");
        saveButtonAction();
        if(setState.getCanContinue()) {
            System.out.println();
            setState.incrementClassListIndex();
            if (setState.getClassListIndex()+1 <= setList.getFinalClassList().size()) {
                System.out.println();
                visitNextStudentClass();
            }
            else {
                hideWindow();
                new PrintStudentGrades(set.getWindow(), setUserInformation.getStudentOrTeacher(), setUserInformation.getExistingOrNew());
            }
        }
    }

    public void visitNextStudentClass() {
        //readClass(finalClassList);
        String filePath = setUserInformation.getPathToClassInformationFileWithIndex();
        if (fileHandler.fileExists(filePath)) {
            textBoxPanelReset();

            addLoadedBoxes();
            
            classLabelPanel.add(textBoxPanel);
            if (textBoxPanel.getComponentCount() <= 1) { //The reason it is <=1 and not 0, is to account for the classLabel
                boxStarterPack();
            }
            create.windowFix();
        }
        else { //first time visiting next class
            hideWindow();
            StudentStatCollect studentStatCollect = new StudentStatCollect();
            studentStatCollect.DisplayClasses();
        }

    }

    private void boxStarterPack() {
        boxManageCreate("Credits (optional)", "JTextField", false);
        newSet();
    }

   private void createNewTypeButton() {
       newTypeButton = new JButton("New Type");
       newTypeButton.setPreferredSize(new Dimension(90, 50));
       JPanel newTypeButtonPanel = new JPanel(new BorderLayout());
       newTypeButtonPanel.add(newTypeButton,BorderLayout.NORTH);

       newDelContainerFlow = new JPanel(new FlowLayout(FlowLayout.LEFT,0,5));
       newDelContainerFlow.setName("newdelcontainerflow");

       JPanel newDelContainer = new JPanel(new GridLayout(3,1,0,5));
       newDelContainer.setName("newDelContainer");
       //gridlayout allows any time of resizing
       //flowlayout allows resizing of width
       newTypeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            newSet();
            System.out.println("ClassLabelPanel after adding: "+ allBoxesPanel.getComponentCount());
        }
        });
       JPanel delTypeButtonPanel = deleteButtonPanel();
       JPanel swapTypesButtonPanel = swapTypesButtonPanel();
       newDelContainer.add(newTypeButtonPanel);
       newDelContainer.add(swapTypesButtonPanel);
       newDelContainer.add(delTypeButtonPanel);
       newDelContainerFlow.add(newDelContainer);
       window.add(newDelContainerFlow, BorderLayout.EAST);

    //    newDelContainer.add(newTypeButtonPanel);
    //    newDelContainer.add(delTypeButtonPanel);
    //    newDelContainerFlow.add(newDelContainer);
    //    window.add(newDelContainerFlow, BorderLayout.EAST);
       create.windowFix();
   }

   private JPanel deleteButtonPanel() {
    JButton deleteTypeButton = new JButton("Delete Type");
        deleteTypeButton.setPreferredSize(new Dimension(90, 50));
        deleteTypeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (textBoxPanel.getComponentCount() >= 8) {
                create.deleteTextBox(textBoxPanel);
                create.deleteTextBox(textBoxPanel);
                create.deleteTextBox(textBoxPanel);
                numOfBoxes = numOfBoxes - 3;
                typeNumber--;
                
                createButton.saveButtonEnable();
            }
    }
    });  
    JPanel delTypeButtonPanel = new JPanel(new BorderLayout());
    delTypeButtonPanel.add(deleteTypeButton,BorderLayout.NORTH);
    return delTypeButtonPanel;
   }

   private JPanel swapTypesButtonPanel() {
    JButton swapClassesButton = new JButton("Swap Types");
        swapClassesButton.setPreferredSize(new Dimension(90, 50));
        swapClassesButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (textBoxPanel.getComponentCount() >= 8) {
                create.deleteTextBox(textBoxPanel);
                create.deleteTextBox(textBoxPanel);
                create.deleteTextBox(textBoxPanel);
                numOfBoxes = numOfBoxes - 3;
                typeNumber--;
                
                createButton.saveButtonEnable();
            }
    }
    });  
    JPanel swapClassesButtonPanel = new JPanel(new BorderLayout());
    swapClassesButtonPanel.add(swapClassesButton,BorderLayout.NORTH);
    return swapClassesButtonPanel;
    
}

   private void correctGradeFormatChecker(Pattern pattern) {
        int index = 4;
        for (int i=1; i<=8; i++) { //max num of grade type
            //String text = create.goIntoPanel(classLabelPanel, index);
            String text = goIntoPanel.goIntoPanel(classLabelPanel, index);

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
        setUserInformation.getUsername();
        ArrayList<String> typeList = setList.getCurrentPanelList();
        readClass(typeList);
    }

    private void readClass(ArrayList<String> typeList) { 
        System.out.println("readclass test:"+ setList.getFinalClassList()+"index: "+setState.getClassListIndex());
        boxManageCreate(setList.getFinalClassList().get(setState.getClassListIndex())+"AHC", "JLabel", false); //necessary
        boxManageCreate("Credits (optional)", "JTextField", false);
        newSet();
        container.add(classLabelPanel);
        container.setName("container in readclass");
        //classLabelPanel.setBackground(Color.pink);
        window.add(classLabelPanel, BorderLayout.CENTER);
        create.windowFix();
    }

    private void hideWindow() {
        create.hideContainer();
        backNextButtonsPanel.setVisible(false);
        newDelContainerFlow.setVisible(false);
        classLabelPanel.setVisible(false);
        classLabelPanel.removeAll();
        textBoxPanel.setVisible(false);
        
    }

    private void boxManageCreate(String placeholder, String type, Boolean boxLoaded) {
        if (numOfBoxes < maxBoxes) {
            System.out.println("test.1 "+ placeholder +" "+type+ "textboxpanel components"+textBoxPanel.getComponentCount());
            textBoxPanel.add(create.typeBox(placeholder, type, boxLoaded));//false));
            System.out.println("test.2 "+ placeholder +" "+type+ "textboxpanel components"+textBoxPanel.getComponentCount());
            classLabelPanel.add(textBoxPanel);
            //classLabelPanel.setBackground(Color.pink);
            create.windowFix();
            //System.out.println("visibility"+set.getDEBUGBOX().isVisible());
            numOfBoxes++;
            //window.add(classLabelPanel);
        }
        else if (numOfBoxes == maxBoxes) {
            Decorator decorate = new Decorator();
            // JDialog dialog = decorate.genericPopUpMessage("<html><center>Maximum amount reached.", null, 200 , 100);
            // dialog.setLocationRelativeTo(window);
            // dialog.setLocation(dialog.getX(), dialog.getY() + 15); 
            // dialog.setVisible(true);        

            decorate.maximumAmountReachedPopup();
        }
    }

    private void newSet() {
        typeNumber++;
        boxManageCreate("Grade Type "+typeNumber, "JTextField",false);
        boxManageCreate("Percentage of Grade", "JTextField",false);
        boxManageCreate("Grades(format:# # #)", "JTextField",false);
    }

    // private void calculate() {
    //     //parse the grade situation. but then needs correct input.
    // }

    ////
    public class EnterAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            doNextButtonProcedure();
        }
    }
    }