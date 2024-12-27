package view.student;

import javax.swing.JButton;
import javax.swing.JFrame;
// import javax.swing.JLabel; //9/20
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Component;
import java.util.ArrayList;

// import java.awt.Color; //9/20

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Set;
import model.SetState;
import model.SetUserInformation;
import model.SetList;
import model.GoIntoPanel;
import model.SETTEST;

import controller.Creator;
import controller.Decorator;
import controller.FileHandling;
import controller.FileWriting;
import controller.CompositeActionListenerWithPriorities;
import controller.CreateButton;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.KeyEvent;

import java.awt.*;
import javax.swing.*;

public class StudentStatCollect extends JFrame {
    private JFrame window;
    private Creator create;
    private GoIntoPanel goIntoPanel;
    private static JPanel backNextButtonsPanel = new JPanel();
    private JButton newTypeButton;
    private JButton nextButton;
    private JButton backButton;
    private Set set;
    private SetState setState;
    private SetList setList;
    private SETTEST sETTEST;
    private String currentClass;

    private SetUserInformation setUserInformation;

    private CreateButton createButton = new CreateButton();
    private FileWriting fileWrite = new FileWriting();
    private CompositeActionListenerWithPriorities actionPriorities;

    private JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel newDelContainerFlow = new JPanel(new FlowLayout(FlowLayout.LEFT,0,5));
    private JPanel classLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel allBoxesPanel = new JPanel();
    private FileHandling fileHandler;
    private JPanel textBoxPanel = new JPanel(new GridLayout(0,4,5,5));

    private static int typeNumber;
    private int numOfBoxes = 0;
    private int maxBoxes = 26;
    private int totalPercentage = 0;

    private StudentStatCollect currentInstance;


    JPanel credits;

    public StudentStatCollect() {
        SETTEST.getInstance().TESTSETCURRENTINSTANCE(this);
        this.set = Set.getInstance();
        this.setState = SetState.getInstance();
        this.setUserInformation = SetUserInformation.getInstance();
        this.setList = SetList.getInstance();
        this.actionPriorities = CompositeActionListenerWithPriorities.getInstance();
        System.out.println("in studentstatcollect currclass "+actionPriorities.getCurrentClass());
        this.sETTEST = SETTEST.getInstance();

        SETTEST.getInstance().TESTSETCURRENTINSTANCE(this);

        actionPriorities.setCurrentClass(currentClass);
        create = new Creator();
        goIntoPanel = new GoIntoPanel();
        fileHandler = new FileHandling();

        setState.setCurrentClass("StudentStatCollect.java");
        window = set.getWindow();
        currentClass = "StudentStatCollect";
        actionPriorities.setCurrentClass(currentClass);
        System.out.println("actionprio "+actionPriorities.getCurrentClass());

        Component[] windowComponents4 = window.getContentPane().getComponents();
            int i = 0;
            System.out.println("current class "+currentClass);
            for (Component windowComp : windowComponents4) {
                Container container = (Container) windowComp;
                System.out.println("compcount after " + i + " classname "+ windowComp.getClass().getName() + " regname "+windowComp.getName() + " isvisible "+windowComp.isVisible()+ " component count "+ container.getComponentCount());
                i++;
            }

    }

    public void studentStatCollectLaunch() {
        currentInstance = this;
        window.setName("window");
        container.setName("contianer");
        textBoxPanel.setName("gridlayout textboxpanel");
        allBoxesPanel.setName("allboxespanel");
        classLabelPanel.setName("classLabelPanel student stat");
        EnterAction enterAction = new EnterAction();
        window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        window.getRootPane().getActionMap().put("enterAction", enterAction);
        setList.getFinalClassList();
        setList.getFinalClassList();
        create.hideContainer();

        createNewTypeButton();
        //buttonSetUpAction(); //location 11/18
        window.setTitle("StudentStatCollect");

        currentClass = "StudentStatCollect";
        actionPriorities.setCurrentClass(currentClass);

        if (textBoxPanel.getComponentCount() < 2) {
        }
        else if (textBoxPanel.getComponent(1) instanceof JPanel) {
            goIntoPanel.goIntoPanelReturnTextbox((JPanel) textBoxPanel.getComponent(1), 0);
        }
        else {
            setState.getTextFieldPanel().getComponent(1);
        }

        textBoxPanel.setVisible(true);
        buttonSetUpAction(); //location 11/18

        actionPriorities.setCurrentClass(currentClass);

        // window.remove(backNextButtonsPanel);
        //window.add(backNextButtonsPanel);
        typeNumber = 0;
    }

    public void buttonSetUpAction() {
        backButton = createButton.backButtonCreate();
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        backAction(backButton);
        JButton saveButton = createButton.saveButtonCreate();
        JPanel saveButtonPanel = new JPanel();
        saveButton.setEnabled(false);
        saveButtonPanel.add(saveButton);
        saveAction(saveButton);
        saveButton.setEnabled(false);

        nextButton = createButton.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        nextButtonAction(nextButton);
        backNextButtonsPanel = createButton.makeBackNextButtonsPanel(backButtonPanel, saveButtonPanel, nextButtonPanel);
        backNextButtonsPanel.setName("backnextbuttonspanel studentstatcollect");
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
    }

    private void backAction(JButton backButton) {
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("chop 1111");
                actionPriorities.setCurrentClass(currentClass);
                actionPriorities.addClassActionListener(b -> {
                    currentClass = "StudentStatCollect";
                    actionPriorities.setCurrentClass(currentClass);
                    System.out.println("chop 2222");
                    if(setState.getCanContinue()) {
                        System.out.println("chop 3333");
                        decideBackActionBasedOnClass();
                    }}, 2, "backButton",null, currentClass);}});
    }

    private void decideBackActionBasedOnClass() {
        System.out.println("chop 4444 " +(setState.getClassListIndex() > 0) +" placeholders is zero? "+ (fileWrite.howManyPlaceholders() == 0));

        if (setState.getClassListIndex() == 0 && (fileWrite.howManyPlaceholders() == 0)) { //case for back to classes
            hideWindow();
            StudentClasses studentClasses = new StudentClasses();
            saveButtonAction("backButton");
            studentClasses.studentClassesLaunch();
            //create.getTextFieldContainer().setVisible(false); //added 12/21
            //
            Component[] windowComponents4 = window.getContentPane().getComponents();
            int i = 0;
            for (Component windowComp : windowComponents4) {
                // if (windowComp.getName()!=null && windowComp.getName().equals("classlabelpanelinreadclass") ) {
                //     window.remove(windowComp);
                // }
                System.out.println("compcount after " + i + " classname "+ windowComp.getClass().getName() + " regname "+windowComp.getName() + " isvisible "+windowComp.isVisible());
                i++;
            }
            //
        }
        else if ((setState.getClassListIndex() > 0) && (fileWrite.howManyPlaceholders() == 0)) {
            System.out.println("chop 5555");
            window.remove(backNextButtonsPanel); //added 12/10 bc savebttn wasnt changing and therefore not working
            buttonSetUpAction();
            System.out.println("chop 6666");
            goToPreviousClasses();
            // JFrame window = Set.getInstance().getWindow();
            // Component[] windowComponents = window.getContentPane().getComponents();
        } else if (fileWrite.howManyPlaceholders() > 0) {
            Decorator decorate = new Decorator();
            SetState.getInstance().setStudentStatCollect(currentInstance);
            SetState.getInstance().setAreYouSureMessageCause("backButton");

            String yesOrNoDialog = decorate.areYouSureMessage(null, "studentStatsEmpty", "Remove placeholder(s) to continue?", 230, 90);
        }
    }

    public void backAction2() { //necessary because if the user tries to go back with placeholders and clicks "yes" option, will get stuck in an infinite loop when back action recalled.
                    currentClass = "StudentStatCollect";
                    actionPriorities.setCurrentClass(currentClass);
                    if(setState.getCanContinue()) {
                        //decideBackActionBasedOnClass();
                        //
                        if (setState.getClassListIndex() == 0) { //case for back to classes
                            hideWindow();
                            StudentClasses studentClasses = new StudentClasses();
                            //saveButtonAction("backButton");
                            studentClasses.studentClassesLaunch();
                        }
                        else if ((setState.getClassListIndex() > 0)) {
                            //goToPreviousClasses();
                            setState.decrementClassListIndex();
                            setList.getClassLabelPanel().removeAll();
                            classLabelPanel.removeAll();
                            textBoxPanel.removeAll();
                            SETTEST.getInstance().SETTESTTEXTBOXPANEL(textBoxPanel);
                            SETTEST.getInstance().TESTSETCURRENTINSTANCE(this);
                            addLoadedBoxes();
                            System.out.println("inbackaction2"+ numOfBoxes);
                            if (numOfBoxes == 2) { //added 12/27 for if saved only credits and label
                                newSet();
                            }

                            classLabelPanel.setVisible(true); //11/25
                            textBoxPanel.setVisible(true); //11/25
                            JFrame window = Set.getInstance().getWindow();

                            Component[] windowComponents = window.getContentPane().getComponents();
                            for (Component windowCompon:windowComponents) {//(int k = 0; k<8;k++) {
                                //Component component = windowComponents[k];
                                //component.setVisible(false);
                                //window.remove(component);
                                window.remove(windowCompon);
                            } //11/18

                            //backNextButtonsPanel = 
                            //window.add(backNextButtonsPanel,BorderLayout.SOUTH);
                            window.remove(backNextButtonsPanel);
                            buttonSetUpAction();
                            window.add(newDelContainerFlow, BorderLayout.EAST);
                            window.add(classLabelPanel); //12/20 because classes werent showing going backwards from 4th class to 3rd

                            classLabelPanel.setVisible(true); //11/21
                            textBoxPanel.setVisible(true); //11/21

                            if (setState.getClassListIndex() < 2) {
                                window.add(classLabelPanel, BorderLayout.CENTER); //commented 11/17 because it would cause a bug with the third class new type
                            }
                            create.windowFix();
                        }
                        //
                    }//}, 2, "backButton",null, currentClass);
        }

    private void goToPreviousClasses() {
        System.out.println("chop 7777");
        saveButtonAction("backButton");
        setState.decrementClassListIndex();
        numOfBoxes = 0; //added 12/6 to prevent bug
        if (setList.getClassLabelPanel() != null) { //12/3 other would have null pointer error sometimes
            setList.getClassLabelPanel().removeAll();
        }

        classLabelPanel.removeAll();
        textBoxPanel.removeAll();
        SETTEST.getInstance().SETTESTTEXTBOXPANEL(textBoxPanel);
        SETTEST.getInstance().TESTSETCURRENTINSTANCE(this);
        addLoadedBoxes();
        System.out.println("chop 8888 numOfBoxes: "+numOfBoxes);

        if (numOfBoxes == 1) { //added 12/27 for if saved only credits
            newSet();
        }
    }

    public void addLoadedBoxes() {

        String filePath = setUserInformation.getPathToClassInformationFileWithIndex();

        JPanel loadedBoxesPanel = fileHandler.loadTextboxes(filePath);
        loadedBoxesPanel.setName("loadedBoxesPanel");

        int numberOfComponents = loadedBoxesPanel.getComponentCount();
        numOfBoxes += numberOfComponents;
        System.out.println(" boxmanagecreateOne "+numOfBoxes+ " maxboxes "+maxBoxes);
        System.out.println("");
        for (int i = 0; i < numberOfComponents; i++) {
            // if ((JTextField) loadedBoxesPanel.getComponent(0).getText().equals("Credits (optional)")) {
            //     setState.setLoadedState((JTextField) loadedBoxesPanel.getComponent(0), false); //used for if credits was not filled out last time
            // }
            textBoxPanel.add(loadedBoxesPanel.getComponent(0));
        }
        classLabelPanel.add(textBoxPanel);
        sETTEST.SETTESTCLASSLABELPANEL(classLabelPanel);

        if (numOfBoxes == 0) {
            boxStarterPack();
        }

        textBoxPanel.setVisible(true);
        classLabelPanel.setVisible(true);

        create.windowFix();

        sETTEST.SETTESTTEXTBOXPANEL(textBoxPanel);

        window.add(classLabelPanel);
        sETTEST.SETTESTCLASSLABELPANEL(classLabelPanel);
        set.setWindow(window);

        setState.setTextFieldPanel(textBoxPanel);

        //determine gradetypenumber starting amount
        typeNumber = (textBoxPanel.getComponentCount()-2)/3;

        classLabelPanel.setVisible(true); //11/21
        textBoxPanel.setVisible(true); //11/21

        if (setState.getClassListIndex() < 2) {
            window.add(classLabelPanel, BorderLayout.CENTER); //commented 11/17 because it would cause a bug with the third class new type
        }
        create.windowFix();

        Component[] windowComponents4 = window.getContentPane().getComponents();
            int i = 0;
            System.out.println("current class "+currentClass);
            for (Component windowComp : windowComponents4) {
                Container container = (Container) windowComp;
                System.out.println("compcount after " + i + " classname "+ windowComp.getClass().getName() + " regname "+windowComp.getName() + " isvisible "+windowComp.isVisible()+ " component count "+ container.getComponentCount());
                i++;
            }
    }

    private void saveAction(JButton saveButton) {
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check if textbox is vaild
                saveButton.setEnabled(false);
                saveButtonAction("Neither");
            }
        });
    }

    private void saveButtonAction(String actionCause) {
        SETTEST.getInstance().TESTSETCURRENTINSTANCE(this);
        set.setFilePath(setUserInformation.getPathToClassInformationFileWithIndex());
        create.setTextFieldContainer(setState.getTextFieldPanel());

        fileWrite.writeTextToFile();
        System.out.println("HIT SAVE "+ setList.getFinalClassList().get(setState.getClassListIndex()));

        if (fileWrite.howManyPlaceholders() > 0 && !actionCause.equals("Neither")){
            Decorator decorate = new Decorator();
            System.out.println("up Z how many placeholders? "+fileWrite.howManyPlaceholders()+" current class "+ setList.getFinalClassList().get(setState.getClassListIndex()));
            setState.setStudentStatCollect(this);
            setState.setAreYouSureMessageCause(actionCause);
            decorate.areYouSureMessage(null, "studentStatsEmpty", "Remove placeholder(s) to continue?", 230, 90);
            setState.setCanContinue(false);
        }

        SETTEST.getInstance().TESTSETCURRENTINSTANCE(this);

    }

    private void nextButtonAction(JButton nextButton) {
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
        //         actionPriorities.addClassActionListener(new ActionListener() {
        //             @Override
        //             public void actionPerformed(ActionEvent e) {// remember wont run  if just enter without a click
        //                 System.out.println("enteraction");
        //                 //doNextButtonProcedure();
        //                 //
        //                 actionPriorities.setCurrentClass(currentClass);
        //                 actionPriorities.addClassActionListener(new ActionListener() {
        //                     @Override
        //                     public void actionPerformed(ActionEvent e) { // remember won't run if just enter without a click
        //                         System.out.println("enteraction");
        //                         doNextButtonProcedure();

        //                     }
        //                 }, 1, "EnterAction", null, currentClass);  // Add this ActionListener with priority 1
        //                 //
        //             }
        //         }, 1, "nextButton", null, currentClass);  // Add this ActionListener with priority 1
        //     }
        // });
                actionPriorities.setCurrentClass(currentClass);
                actionPriorities.addClassActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { // remember won't run if just enter without a click
                        System.out.println("enteraction");
                        doNextButtonProcedure();

                    } //added 12/8 to make nextbutton work like enter
                }, 1, "EnterAction", null, currentClass);  // Add this ActionListener with priority 1
            }
            });
        
    }

    public void doNextButtonProcedure() {
        System.out.println("hit next button procedure");
        if(setState.getCanContinue()) {
            saveButtonAction("nextButton");
            System.out.println("innextbuttonprocedure typenumberSet "+typeNumber + " class "+setList.getFinalClassList().get(setState.getClassListIndex()));
            if (typeNumber!=0) {
                //System.out.println("class "+setList.getFinalClassList().get(setState.getClassListIndex())+ " type number " +typeNumber);
                System.out.println("typenumberSet "+typeNumber + " class "+setList.getFinalClassList().get(setState.getClassListIndex()));
                // setList.addGradeTypeList(typeNumber); //replaced by below 12/4
                setList.setGradeTypeList(setState.getClassListIndex(), typeNumber);
            }
            //typeNumber = 0; //11/28
        }
        credits = (JPanel) textBoxPanel;

        while (credits.getComponentCount() < 5) { // for layered panel
            credits = (JPanel) credits.getComponent(0);
        }
        //is greater or equal to 5
        credits = (JPanel) credits.getComponent(1);

        if (credits instanceof JPanel) {
            ifCreditsIsJPanel();
        }
    }

    private void ifCreditsIsJPanel() {
        JTextField creditsTextField = goIntoPanel.goIntoPanelReturnTextbox((JPanel) credits, 0);
        String text = creditsTextField.getText();

        Boolean matcherBoolean = checkCreditsFormat(text); //11/25

        Boolean gradesFormatOkay = checkGradesFormat(); //11/25

        Boolean percentageFormatOkay = checkPercentageOfGradesFormat(); //11/25

        Boolean creditsFieldChanged = (setState.getEmptiedState(creditsTextField) != false);

        Boolean reminderActive = false;

        Boolean creditsInWrongFormatAndChanged = ((matcherBoolean == false) && creditsFieldChanged);

        //textboxpanel automatically updates
        setState.setTextFieldPanel(textBoxPanel);

        if ((creditsInWrongFormatAndChanged || (gradesFormatOkay == false) || (percentageFormatOkay == false)) && (fileWrite.howManyPlaceholders() == 0) && (reminderActive == false)) { //11/25
            reminderActive = true;
            makeFormatReminderDialog(); //11/25
            reminderActive = false;

        } //11/25

        else if ((setState.getClassListIndex()+1 < setList.getFinalClassList().size())) {
            setState.incrementClassListIndex();
            //typeNumber = 0; //11/28
            allPassedGoToStudentStats();
        }

        //else if ((!creditsFieldChanged)) { //commented 11/27. Im unsure what I was thinking
        else if((setState.getClassListIndex()+1 >= setList.getFinalClassList().size())) {
            goToPrintStudentGrades();
        }

        else {
            System.out.println("matcher credit boolean, something wrong");
        }
    }

    private Boolean checkPercentageOfGradesFormat() {

        Pattern patternForPercentage = Pattern.compile("^[-+]?\\d*\\.?\\d+(e[-+]?\\d+)?%?$", Pattern.CASE_INSENSITIVE);
        Boolean percentageFormatOkay = correctBoxFormatChecker(patternForPercentage, 3, "Percentage of Grade"); //for percentage

        if ((percentageFormatOkay == true) && (totalPercentage == 100)) {
            return true; //got out of loop with every box that was checked, matched.
        }

        return false;

    }

    private Boolean checkCreditsFormat(String text) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(text);
        Boolean matcherBoolean = matcher.find();
        return matcherBoolean;
    }

    private Boolean checkGradesFormat() {
        Pattern patternForGrades = Pattern.compile("^(?:[0-9]*(?:.[0-9]+))*\s*$|^[0-9]*\s*$", Pattern.CASE_INSENSITIVE);
        Boolean gradesFormatOkay = correctBoxFormatChecker(patternForGrades, 4, "Check Grades"); //for grades
        return gradesFormatOkay;
    }

    private void makeFormatReminderDialog() {
        Decorator decorate = new Decorator();
        JDialog dialog = decorate.genericPopUpMessage("<html><center>Invalid Format.<br>-Credits must be an integer<br>-Grades must be numbers separated by spaces<br>-Percentage of Grade must be an integer or decimal<br>-Percentage must total to 100%",null,400,150);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        //setState.setCanContinue(false); //commented out on 12/26 because it prevents saving
        //setState.setCanContinue(true); //added 12/26 because of above
    }

    public void allPassedGoToStudentStats() {
        textBoxPanel.removeAll();

        setState.setTextFieldPanel(textBoxPanel);

        window.remove(backNextButtonsPanel); //commented out on 12/10 because it is needed regardless of going > or < so moved to hide window
        window.remove(set.getCurrentSaveButton());
        newDelContainerFlow.removeAll();
        studentStatCollectLaunch();

        visitNextStudentClass();
        if (setState.getClassListIndex() < 2) {
            buttonSetUpAction(); //location 11/18
        }
    }

    public void goToPrintStudentGrades() {
        window.remove(newDelContainerFlow);

        hideWindow();
        removeVariablesInWindow();

        window.remove(set.getCurrentSaveButton());

        new PrintStudentGrades(set.getWindow(), setUserInformation.getStudentOrTeacher(), setUserInformation.getExistingOrNew());
    }

    public void doNextButtonProcedure2() { //necessary to prevent endless loop when need nextbutton action "areyousure" popup.
        if(setState.getCanContinue()) {
            saveButtonAction("nextButton"); //replaced line above 10/7
            if (typeNumber!=0) {
                // setList.addGradeTypeList(typeNumber); //replaced by below 12/4
                setList.setGradeTypeList(setState.getClassListIndex(), typeNumber);
                System.out.println("typenumbernewset "+typeNumber + " class "+setList.getFinalClassList().get(setState.getClassListIndex()));
            }
            //typeNumber = 0; //11/28
            credits = (JPanel) textBoxPanel;
        //} //removed 12/1
        credits = (JPanel) textBoxPanel;

        while (credits.getComponentCount() < 5) { // for layered panel
            credits = (JPanel) credits.getComponent(0);
        }
        //is greater or equal to 5
        credits = (JPanel) credits.getComponent(1);

        if (credits instanceof JPanel) {
            ifCreditsIsJPanel();
        }

        setState.setCanContinue(true);

    }//added 12/1

    window.requestFocus(); //added 12/16 to select window focus when using remove placeholders to continue

    }

    public void visitNextStudentClass() {
        String filePath = setUserInformation.getPathToClassInformationFileWithIndex();
        numOfBoxes = 0; //added 12/6 to prevent bug with maxboxes
        if (fileHandler.fileExists(filePath)) {
            addLoadedBoxes();
            classLabelPanel.add(textBoxPanel);
            if (textBoxPanel.getComponentCount() <= 1) { //The reason it is <=1 and not 0, is to account for the classLabel
            //if (textBoxPanel.getComponentCount() <= ) {
                boxStarterPack();
            }
            else if (textBoxPanel.getComponentCount() == 2) { //added 12/27 for if saved only credits
                newSet();
            }
            create.windowFix();
            SETTEST.getInstance().TESTSETCURRENTINSTANCE(this);

        }
        else { //first time visiting next class
            createButton.removeFromWindow();
            window.remove(backNextButtonsPanel);
            set.getWindow().remove(backNextButtonsPanel);
            set.getCurrentSaveButton().setVisible(false);

            hideWindow();
            StudentStatCollect studentStatCollect = new StudentStatCollect();
            sETTEST.TESTSETCURRENTINSTANCE(studentStatCollect);

            studentStatCollect.DisplayClasses();
            textBoxPanel = setState.getTextFieldPanel();
        }

        if (setState.getClassListIndex() >= 2) {
            classLabelPanel.add(textBoxPanel);
            classLabelPanel.setVisible(true);

            //classLabelPanel.setBackground(Color.pink);

            Component[] windowComponents3 = window.getContentPane().getComponents();
            int i = 0;
            for (Component windowComp : windowComponents3) {
                System.out.println("compcount before " + i + " classname "+ windowComp.getClass().getName() + " regname "+windowComp.getName() + " isvisible "+windowComp.isVisible());
                i++;
                window.remove(windowComp);
            }
            window.add(classLabelPanel,BorderLayout.CENTER);
            window.add(newDelContainerFlow, BorderLayout.EAST);
            buttonSetUpAction();

        }

    }

    private void boxStarterPack() {
        boxManageCreate("Credits (optional)", "JTextField", false);
        newSet();
    }

    private void createNewTypeButton() {
        Component[] windowComponents3 = window.getContentPane().getComponents();
        int i = 0;
        for (Component windowComp : windowComponents3) {
            if (windowComp.getName()==null){
                window.remove(windowComp);
            }
            System.out.println("compcount before " + i + " classname "+ windowComp.getClass().getName() + " regname "+windowComp.getName() + " isvisible "+windowComp.isVisible());
            i++;
        }
        newTypeButton = new JButton("New Type");
        newTypeButton.setPreferredSize(new Dimension(90, 50));
        JPanel newTypeButtonPanel = new JPanel(new BorderLayout());
        newTypeButtonPanel.add(newTypeButton,BorderLayout.NORTH);

        newDelContainerFlow.setName("newdelcontainerflow");

        JPanel newDelContainer = new JPanel(new GridLayout(3,1,0,5));
        newDelContainer.setName("newDelContainer");
       //gridlayout allows any time of resizing
       //flowlayout allows resizing of width
        newTypeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            newSetButtonAction();
        }
        });
        JPanel delTypeButtonPanel = deleteButtonPanel();
        newDelContainer.add(newTypeButtonPanel);

        newDelContainer.add(delTypeButtonPanel);
        newDelContainerFlow.add(newDelContainer);
        window.add(newDelContainerFlow, BorderLayout.EAST);

        create.windowFix();

    }
    private void newSetButtonAction() {

        typeNumber++;
        //setList.

        boxManageCreate("Grade Type "+typeNumber, "JTextField",false);
        boxManageCreate("Percentage of Grade", "JTextField",false);
        boxManageCreate("Grades(format:# # #)", "JTextField",false);
        classLabelPanel.setVisible(true); //11/21
        textBoxPanel.setVisible(true); //11/21

        if (setState.getClassListIndex() < 2) {
            window.add(classLabelPanel, BorderLayout.CENTER); //commented 11/17 because it would cause a bug with the third class new type
        }
        create.windowFix();

        if (setState.getClassListIndex() > 0) {
            setList.removeStudentStatCollectReadClassLabelPanel(); //causes bugs if used for first class
        }

        setState.setTextFieldPanel(textBoxPanel);

        Component[] windowComponents4 = window.getContentPane().getComponents();
        int i = 0;
        for (Component windowComp : windowComponents4) {
            System.out.println("compcount after " + i + " classname "+ windowComp.getClass().getName() + " regname "+windowComp.getName() + " isvisible "+windowComp.isVisible());
            i++;
        }

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

        private Boolean correctBoxFormatChecker(Pattern pattern, int index, String calledFrom) {
            System.out.println("about to check for index "+index+" classlabelpanel length is "+classLabelPanel.getComponentCount());
            totalPercentage = 0;
            Boolean matcherBoolean = null;
            for (int i=1; i<=8; i++) { //max num of grade type
                String text = goIntoPanel.goIntoPanel(textBoxPanel, index).trim(); //.trim()
                System.out.println("text " +text);

                if (text.equals("does not exist")) {
                    System.out.println("fail");
                    break;
                }
                System.out.println("index "+index+" text "+text);
                index = index + 3;
                Matcher matcher = pattern.matcher(text); //12/21 was previously Matcher matcher = pattern.matcher(text.trim());
                matcherBoolean = matcher.find();

                if (matcherBoolean == false) {
                    return false;
                }

                if (calledFrom.equals("Percentage of Grade")) {
                    System.out.println("percentage of grade, what is text ["+text+"]");
                    totalPercentage += Integer.parseInt(text); //12/21 was previously totalPercentage += Integer.parseInt(text);
                }
            }
            return true;
        }
        public void DisplayClasses() {
            setUserInformation.getUsername();
            ArrayList<String> typeList = setList.getCurrentPanelList();
            readClass(typeList);
            setState.setTextFieldPanel(textBoxPanel);
        }

        private void readClass(ArrayList<String> typeList) {
            boxManageCreate(setList.getFinalClassList().get(setState.getClassListIndex()), "JLabel", false); //necessary
            boxManageCreate("Credits (optional)", "JTextField", false);

            newSet();
            classLabelPanel.setName("classlabelpanelinreadclass");

            if (setList.getReadClassLabelPanel() != null) {
                setList.removeStudentStatCollectReadClassLabelPanel();
                //window.add()
            }
            setList.setReadClassLabelPanel(classLabelPanel);
            setList.setClassLabelPanel(classLabelPanel);

            window.add(classLabelPanel, BorderLayout.CENTER);
            create.windowFix();
        }

        private void hideWindow() {
            create.hideContainer();
            if (backNextButtonsPanel != null) {
                backNextButtonsPanel.setVisible(false);
                //window.remove(backNextButtonsPanel); //not used
            }
            classLabelPanel.setVisible(false);
            if (setState.getClassListIndex() == 0){
                newDelContainerFlow.setVisible(false); //11/23 to prevent newdelcontainerflow from being in classes
            }
            textBoxPanel.setVisible(false);
        }

        private void boxManageCreate(String placeholder, String type, Boolean boxLoaded) {
            if (numOfBoxes < maxBoxes) {
                textBoxPanel.add(create.typeBox(placeholder, type, boxLoaded));
                classLabelPanel.add(textBoxPanel);
                create.windowFix();
                numOfBoxes++;
                System.out.println(" boxmanagecreateTwo "+numOfBoxes+ " maxboxes "+maxBoxes);
            }
            else if (numOfBoxes == maxBoxes) {
                Decorator decorate = new Decorator();
                decorate.maximumAmountReachedPopup();
                setState.setCanContinue(false);
            }

            classLabelPanel.setVisible(true);
            textBoxPanel.setVisible(true);
            setState.setTextFieldPanel(textBoxPanel);
        }

        private void newSet() {
            //window.getContentPane().getComponents(); //
            typeNumber++;
            System.out.println("typenumbernewset "+typeNumber + " class "+setList.getFinalClassList().get(setState.getClassListIndex()));
            boxManageCreate("Grade Type "+typeNumber, "JTextField",false);
            boxManageCreate("Percentage of Grade", "JTextField",false);
            boxManageCreate("Grades(format:# # #)", "JTextField",false);
            setState.setCanContinue(true);
            setState.setTextFieldPanel(textBoxPanel);
        }

        private void removeVariablesInWindow() {
            window.remove(newDelContainerFlow);
            window.remove(classLabelPanel);
            window.remove(backNextButtonsPanel);
            SetList.getInstance().removeStudentStatCollectSettings();
        }

        public class EnterAction extends AbstractAction {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPriorities.setCurrentClass(currentClass);
                actionPriorities.addClassActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { // remember won't run if just enter without a click
                        System.out.println("enteraction");
                        doNextButtonProcedure();

                    }
                }, 1, "EnterAction", null, currentClass);  // Add this ActionListener with priority 1
            }
        }

        public JButton TESTNEXTBUTTON() {
            return nextButton;
        }

        public JButton TESTNEWTYPEBUTTON() {
            return newTypeButton;
        }

        public JButton TESTBACKBUTTON() {
            return backButton;
        }
        }

// before
// compcount 3 classname javax.swing.JPanel regname classlabelpanelinreadclass isvisible true
// compcount 4 classname javax.swing.JPanel regname backnextbuttonspanel studentstatcollect isvisible true


// after
// compcount after 3 classname javax.swing.JPanel regname classlabelpanelinreadclass isvisible true
// compcount after 4 classname javax.swing.JPanel regname backnextbuttonspanel studentstatcollect isvisible true
// compcount after 7 classname javax.swing.JPanel regname classLabelPanel student stat isvisible true