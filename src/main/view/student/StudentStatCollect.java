package view.student;

import javax.swing.JButton;
import javax.swing.JFrame;
// import javax.swing.JLabel; //9/20

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
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.KeyEvent;

public class StudentStatCollect extends JFrame {
    private JFrame window;
    private Creator create;
    private GoIntoPanel goIntoPanel;
    private JPanel backNextButtonsPanel;
    private JButton newTypeButton;
    private JButton nextButton;
    private JButton backButton;
    private Set set;
    private SetState setState;
    private SetList setList;
    private SETTEST sETTEST;
    private String currentClass = "StudentStatCollect Loading";

    private SetUserInformation setUserInformation;

    private CreateButton createButton = new CreateButton();
    private FileWriting fileWrite = new FileWriting();
    private CompositeActionListenerWithPriorities actionPriorities;

    private JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel newDelContainerFlow;
    private JPanel classLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel allBoxesPanel = new JPanel();
    private ArrayList<String> finalClassList;
    private FileHandling fileHandler;
    private JPanel textBoxPanel = new JPanel(new GridLayout(0,4,5,5));

    private int typeNumber = 0;
    private int numOfBoxes = 0;
    private int maxBoxes = 26;
    private int count = 0;

    private StudentStatCollect currentInstance;


    JPanel credits;

    public StudentStatCollect() {
        SETTEST.getInstance().TESTSETCURRENTINSTANCE(this);
        this.set = Set.getInstance();
        this.setState = SetState.getInstance();
        this.setUserInformation = SetUserInformation.getInstance();
        this.setList = SetList.getInstance();
        this.actionPriorities = CompositeActionListenerWithPriorities.getInstance();
        this.sETTEST = SETTEST.getInstance();

        SETTEST.getInstance().TESTSETCURRENTINSTANCE(this);

        actionPriorities.setCurrentClass(currentClass);
        create = new Creator();
        goIntoPanel = new GoIntoPanel();
        fileHandler = new FileHandling();

        setState.setCurrentClass("StudentStatCollect.java");
        window = set.getWindow();
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

        finalClassList = setList.getFinalClassList();
        System.out.println("final class list issssss before: "+ finalClassList);
        finalClassList = setList.getFinalClassList();
        System.out.println("final class list issssss: "+ finalClassList);
        create.hideContainer();

        createNewTypeButton();
        buttonSetUpAction();
        window.setTitle("StudentStatCollect");

        currentClass = "StudentStatCollect";
        actionPriorities.setCurrentClass(currentClass);

        //JTextField testTextField = new JTextField("Error"); //8/23
        if (textBoxPanel.getComponentCount() < 2) {
        }
        else if (textBoxPanel.getComponent(1) instanceof JPanel) {
            goIntoPanel.goIntoPanelReturnTextbox((JPanel) textBoxPanel.getComponent(1), 0);
        }
        else {
            setState.getTextFieldPanel().getComponent(1);
        }

        textBoxPanel.setVisible(true);
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
                // if (textBoxPanel.getComponentCount() >= 2) { //9/20
                //     //goIntoPanel.goIntoPanelReturnTextbox((JPanel) textBoxPanel.getComponent(1), 0);
                // }
                actionPriorities.setCurrentClass(currentClass);
                actionPriorities.addClassActionListener(b -> {
                    System.out.println("hfwehofohe filewrite how many placeholders "+fileWrite.howManyPlaceholders());
                    if(setState.getCanContinue()) {//&& (fileWrite.howManyPlaceholders() == 0)) {
                        System.out.println("classlabelpanel "+classLabelPanel.getComponentCount());
                        //saveButtonAction("backButton"); //8/22
                        create.setTextFieldContainer(setState.getTextFieldPanel());
                        System.out.println("hfwehofohe2 filewrite how many placeholders "+fileWrite.howManyPlaceholders());
                        if (setState.getClassListIndex() == 0 && (fileWrite.howManyPlaceholders() == 0)) { //case for back to classes
                            System.out.println("hidewindow1 "+ fileWrite.howManyPlaceholders());
                            hideWindow();
                            StudentClasses studentClasses = new StudentClasses();
                            saveButtonAction("backButton");
                            studentClasses.studentClassesLaunch();
                            // saveButtonAction("backButton"); //was uncommented before 9/11 7:03pm
                        }
                        else if (setState.getClassListIndex() > 0) {
                            System.out.println("hidewindow111");
                            goToPreviousClasses();

                            JFrame window = Set.getInstance().getWindow();
                            Component[] windowComponents = window.getContentPane().getComponents();
                            for (int k = 0; k<8;k++) {
                                Component component = windowComponents[k];
                                component.setVisible(false);
                                window.remove(component);
                            }

                            Component[] windowComponents2 = window.getContentPane().getComponents();
                            for (int k = 2; k<=10;k++) {
                                Component component = windowComponents2[k];
                                component.setVisible(false);
                                window.remove(component);
                            }

                            // Component[] windowComponents3 = window.getContentPane().getComponents();
                            // int i = 0;
                            // for (Component windowComp : windowComponents3) {
                            //     System.out.println("compcount " + i + " classname "+ windowComp.getClass().getName() + " regname "+windowComp.getName() + " isvisible "+windowComp.isVisible());
                            //     i++;
                            // }
                        }
                    }
                    else if (fileWrite.howManyPlaceholders() > 0) {
                        Decorator decorate = new Decorator();
                        SetState.getInstance().setStudentStatCollect(currentInstance);
                        SetState.getInstance().setAreYouSureMessageCause("backButton");
                        decorate.areYouSureMessage(null, "studentStatsEmpty", "Remove placeholder(s) to continue?", 230, 90);
                    }

                }, 2, "backButton",null, currentClass);
        }});
    }

    public void backAction2() { //necessary because if the user tries to go back with placeholders and clicks "yes" option, will get stuck in an infinite loop when back action recalled.
        if(setState.getCanContinue()) {//&& (fileWrite.howManyPlaceholders() == 0)) {
            create.setTextFieldContainer(setState.getTextFieldPanel());
            if (setState.getClassListIndex() == 0) { //case for back to classes
                hideWindow();
                StudentClasses studentClasses = new StudentClasses();
                studentClasses.studentClassesLaunch();
                //saveButtonAction("backButton"); //9/20
            }
            else if (setState.getClassListIndex() > 0) {
                goToPreviousClasses();
                JFrame window = Set.getInstance().getWindow();
                Component[] windowComponents = window.getContentPane().getComponents();
                for (int k = 0; k<8;k++) {
                    Component component = windowComponents[k];
                    component.setVisible(false);
                    window.remove(component);
                }
                Component[] windowComponents2 = window.getContentPane().getComponents();
                for (int k = 2; k<=10;k++) {
                    Component component = windowComponents2[k];
                    component.setVisible(false);
                    window.remove(component);
                }
            }
        }
        setState.setCanContinue(false);
    }

    private void goToPreviousClasses() {
        saveButtonAction("backButton");
        //if (setState.getCanContinue()) { //8/24 //TODO 8/24
            setState.decrementClassListIndex();
            setList.getClassLabelPanel().removeAll();
            classLabelPanel.removeAll();
            textBoxPanel.removeAll();
            SETTEST.getInstance().SETTESTTEXTBOXPANEL(textBoxPanel);
            SETTEST.getInstance().TESTSETCURRENTINSTANCE(this);
            addLoadedBoxes();
    }

    public void addLoadedBoxes() {
        String filePath = setUserInformation.getPathToClassInformationFileWithIndex();

        JPanel loadedBoxesPanel = fileHandler.loadTextboxes(filePath);
        loadedBoxesPanel.setName("loadedBoxesPanel");

        int numberOfComponents = loadedBoxesPanel.getComponentCount();
        numOfBoxes += numberOfComponents;
        for (int i = 0; i < numberOfComponents; i++) {
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
        //System.out.println("classlistindex "+ setState.getClassListIndex();
        set.setFilePath(setUserInformation.getPathToClassInformationFileWithIndex());
        create.setTextFieldContainer(setState.getTextFieldPanel());

        //setState.setTextFieldPanel(classLabelPanel); //TODO if you see issue with boxes being weird, this prob source of bug //8/30

        System.out.println("1111");
        fileWrite.writeTextToFile();

        if (fileWrite.howManyPlaceholders() > 0 ){//&& actionCause!=("Neither")) { //commented 9/20
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
                actionPriorities.setCurrentClass(currentClass);
                actionPriorities.addClassActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {// remember wont run  if just enter without a click
                        System.out.println("enteraction");
                        System.out.println("textboxpanel comps B"+setState.getTextFieldPanel().getComponentCount());
                        doNextButtonProcedure();
                    }
                }, 1, "nextButton", null, currentClass);  // Add this ActionListener with priority 1
            }
        });
    }

    public void doNextButtonProcedure() {
        saveButtonAction("nextButton");
        if(setState.getCanContinue()) {
                if (typeNumber!=0) {
                    setList.addGradeTypeList(typeNumber); }
                else {
                    System.out.println("home "+setState.getTextFieldPanel().getComponentCount());

                }

                credits = (JPanel) textBoxPanel;

                while (credits.getComponentCount() < 5) {
                    credits = (JPanel) credits.getComponent(0);
                }
                //is greater or equal to 5
                credits = (JPanel) credits.getComponent(1);

                if (credits instanceof JPanel) {
                    JTextField creditsTextField = goIntoPanel.goIntoPanelReturnTextbox((JPanel) credits, 0);
                    String text = creditsTextField.getText();

                    Pattern pattern = Pattern.compile("[0-9]+");
                    Matcher matcher = pattern.matcher(text);
                    Boolean matcherBoolean = matcher.find();

                    Pattern patternForGrades = Pattern.compile("^(?:[0-9]*(?:.[0-9]+))*\s*$|^[0-9]*\s*$", Pattern.CASE_INSENSITIVE);
                    Boolean gradesFormatOkay = correctBoxFormatChecker(patternForGrades, 4); //for grades

                    Pattern patternForPercentage = Pattern.compile("^[-+]?\\d*\\.?\\d+(e[-+]?\\d+)?%?$", Pattern.CASE_INSENSITIVE);
                    Boolean percentageFormatOkay = correctBoxFormatChecker(patternForPercentage, 3); //for percentage
                    Boolean creditsFieldChanged = (setState.getEmptiedState(creditsTextField) != false);//(!creditsTextField.getText().equals("Credits (optional)"));

                    setState.incrementClassListIndex();
                    if ((setState.getClassListIndex()+1 <= setList.getFinalClassList().size())&&((matcherBoolean == true)|| !creditsFieldChanged) && (gradesFormatOkay) && (percentageFormatOkay)) {
                       
                        allPassedGoToStudentStats();
                    }

                    else if (((matcherBoolean == true)|| !creditsFieldChanged) && (gradesFormatOkay) && (percentageFormatOkay)) {
                        setState.decrementClassListIndex();
                        goToPrintStudentGrades();
                    }

                    else if (((matcherBoolean == false) && creditsFieldChanged) || (gradesFormatOkay == false) || (percentageFormatOkay == false)) {
                        Decorator decorate = new Decorator();
                        JDialog dialog = decorate.genericPopUpMessage("<html><center>Invalid Format.<br>-Credits must be an integer<br>Grades must be numbers separated by spaces<br>-Percentage of Grade must be an integer or decimal",null,400,140);
                        dialog.setResizable(false);
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                    }

                    else {
                        System.out.println("matcher credit boolean, something wrong");
                    }
            }

        }
    }
    public void allPassedGoToStudentStats() {
        textBoxPanel.removeAll();

        setState.setTextFieldPanel(textBoxPanel);

        window.remove(backNextButtonsPanel);
        window.remove(set.getCurrentSaveButton());
        studentStatCollectLaunch(); //before 8/27 was below
        visitNextStudentClass();
        buttonSetUpAction();
    }

    public void goToPrintStudentGrades() {
        window.remove(newDelContainerFlow);
        hideWindow();
        removeVariablesInWindow();
        new PrintStudentGrades(set.getWindow(), setUserInformation.getStudentOrTeacher(), setUserInformation.getExistingOrNew());
    }

    public void doNextButtonProcedure2() { //necessary to prevent endless loop when need nextbutton action "areyousure" popup.

        if(setState.getCanContinue()) {
            setState.incrementClassListIndex();
            if (typeNumber!=0) {
                setList.addGradeTypeList(typeNumber); }
            else {
                System.out.println("home "+setState.getTextFieldPanel().getComponentCount());

            }

        if (setState.getClassListIndex()+1 <= setList.getFinalClassList().size()) {
            System.out.println("the jlabel name: "+setList.getFinalClassList().get(setState.getClassListIndex()));

            credits = SetState.getInstance().getTextFieldPanel();

            while (credits.getComponentCount() < 5) {
                System.out.println("dollar "+credits.getComponentCount());
                credits = (JPanel) credits.getComponent(0);
            }
            //is greater or equal to 5
            credits = (JPanel) credits.getComponent(1);

            if (credits instanceof JPanel) {
                JTextField creditsTextField = goIntoPanel.goIntoPanelReturnTextbox((JPanel) credits, 0);
                String text = creditsTextField.getText();

                Pattern pattern = Pattern.compile("[0-9]+");
                Matcher matcher = pattern.matcher(text);
                Boolean matcherBoolean = matcher.find();

                Pattern patternForGrades = Pattern.compile("^(?:[0-9]*(?:.[0-9]+))*\s*$|^[0-9]*\s*$", Pattern.CASE_INSENSITIVE);
                Boolean gradesFormatOkay = correctBoxFormatChecker(patternForGrades, 4); //for grades

                Pattern patternForPercentage = Pattern.compile("^[-+]?\\d*\\.?\\d+(e[-+]?\\d+)?%?$", Pattern.CASE_INSENSITIVE);
                Boolean percentageFormatOkay = correctBoxFormatChecker(patternForPercentage, 3); //for percentage
                Boolean creditsFieldChanged = (setState.getEmptiedState(creditsTextField) != false);//(!creditsTextField.getText().equals("Credits (optional)"));

                if (((matcherBoolean == true)|| !creditsFieldChanged) && (gradesFormatOkay) && (percentageFormatOkay)) {
                    textBoxPanel.removeAll();

                    setState.setTextFieldPanel(textBoxPanel);
                    studentStatCollectLaunch(); //before 8/27 was below visitnextstudentclass
                    visitNextStudentClass();
                    buttonSetUpAction();
                }

                else if (((matcherBoolean == false) && creditsFieldChanged) || (gradesFormatOkay == false) || (percentageFormatOkay == false)) {
                    Decorator decorate = new Decorator();
                    JDialog dialog = decorate.genericPopUpMessage("<html><center>Invalid Format.<br>-Credits must be an integer<br>Grades must be numbers separated by spaces<br>-Percentage of Grade must be an integer or decimal",null,400,140);

                    dialog.setResizable(false);
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                }

                else {
                    System.out.println("matcher credit boolean, something wrong");
                }

            }
        }
        else {
            credits = SetState.getInstance().getTextFieldPanel();

            while (credits.getComponentCount() < 5) {
                System.out.println("dollar "+credits.getComponentCount());
                credits = (JPanel) credits.getComponent(0);
            }
            //is greater or equal to 5
            credits = (JPanel) credits.getComponent(1);

            if (credits instanceof JPanel) {
                JTextField creditsTextField = goIntoPanel.goIntoPanelReturnTextbox((JPanel) credits, 0);
                String text = creditsTextField.getText();

                Pattern pattern = Pattern.compile("[0-9]+");
                Matcher matcher = pattern.matcher(text);
                Boolean matcherBoolean = matcher.find();

                Pattern patternForGrades = Pattern.compile("^(?:[0-9]*(?:.[0-9]+))*\s*$|^[0-9]*\s*$", Pattern.CASE_INSENSITIVE);
                Boolean gradesFormatOkay = correctBoxFormatChecker(patternForGrades, 4); //for grades

                Pattern patternForPercentage = Pattern.compile("^[-+]?\\d*\\.?\\d+(e[-+]?\\d+)?%?$", Pattern.CASE_INSENSITIVE);
                Boolean percentageFormatOkay = correctBoxFormatChecker(patternForPercentage, 3); //for percentage
                Boolean creditsFieldChanged = (setState.getEmptiedState(creditsTextField) != false);//(!creditsTextField.getText().equals("Credits (optional)"));
                if ((((matcherBoolean == true)|| !creditsFieldChanged) || !creditsFieldChanged) && (gradesFormatOkay) && (percentageFormatOkay)) {
                    hideWindow();
                    window.remove(newDelContainerFlow);
                    removeVariablesInWindow();
                    setState.decrementClassListIndex();
                    new PrintStudentGrades(set.getWindow(), setUserInformation.getStudentOrTeacher(), setUserInformation.getExistingOrNew());
                }

                else if (((matcherBoolean == false) && creditsFieldChanged) || (gradesFormatOkay == false) || (percentageFormatOkay == false)) {
                    Decorator decorate = new Decorator();
                    JDialog dialog = decorate.genericPopUpMessage("<html><center>Invalid Format.<br>-Credits must be an integer<br>Grades must be numbers separated by spaces<br>-Percentage of Grade must be an integer or decimal",null,400,140);
                    dialog.setResizable(false);
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                }

                else {
                    System.out.println("matcher credit boolean, something wrong");
                }
            }
        }
        }

        setState.setCanContinue(true);

    }

    public void visitNextStudentClass() {
        String filePath = setUserInformation.getPathToClassInformationFileWithIndex();
        if (fileHandler.fileExists(filePath)) {
            addLoadedBoxes();
            classLabelPanel.add(textBoxPanel);
            if (textBoxPanel.getComponentCount() <= 1) { //The reason it is <=1 and not 0, is to account for the classLabel
                boxStarterPack();
            }
            create.windowFix();
            SETTEST.getInstance().TESTSETCURRENTINSTANCE(this);

        }
        else { //first time visiting next class
            createButton.removeFromWindow();
            window.remove(backNextButtonsPanel);
            set.getWindow().remove(backNextButtonsPanel);
            set.getCurrentSaveButton().setVisible(false);

            JFrame window = Set.getInstance().getWindow();
            Component[] windowComponents = window.getContentPane().getComponents();

            for (Component windowComp : windowComponents) {
                if (windowComp == backNextButtonsPanel) {
                }
                System.out.println("compcount " + windowComponents.length + " classname "+ windowComp.getClass().getName() + " regname "+windowComp.getName() + " isvisible "+windowComp.isVisible());
            }

            hideWindow();
            StudentStatCollect studentStatCollect = new StudentStatCollect();
            System.out.println("textfieldpanelcount "+setState.getTextFieldPanel().getComponentCount());
            sETTEST.TESTSETCURRENTINSTANCE(studentStatCollect);

            studentStatCollect.DisplayClasses();
            textBoxPanel = setState.getTextFieldPanel();
        }

        if (setState.getClassListIndex() >= 2) {
        classLabelPanel.add(textBoxPanel);
        classLabelPanel.setVisible(true);
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

        boxManageCreate("Grade Type "+typeNumber, "JTextField",false);
        boxManageCreate("Percentage of Grade", "JTextField",false);
        boxManageCreate("Grades(format:# # #)", "JTextField",false);
        classLabelPanel.setVisible(true);
        textBoxPanel.setVisible(true);

        window.add(classLabelPanel, BorderLayout.CENTER);
        create.windowFix();


        JFrame window = Set.getInstance().getWindow();

        setList.removeStudentStatCollectReadClassLabelPanel();

        setState.setTextFieldPanel(textBoxPanel);

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

        private Boolean correctBoxFormatChecker(Pattern pattern, int index) {
            //int index = 4;
            for (int i=1; i<=8; i++) { //max num of grade type
                String text = goIntoPanel.goIntoPanel(classLabelPanel, index);

                if (text.equals("does not exist")) {
                    System.out.println("fail");
                    break;
                }
                index = index + 3;
                Matcher matcher = pattern.matcher(text);
                Boolean matcherBoolean = matcher.find();

                if (matcherBoolean == false) {
                    return false;
                }

                System.out.println("matcher works?: "+ matcherBoolean);
            }
            return true; //got out of loop with every box that was checked, matched.
        }
        //read classes array, first five classes
        public void DisplayClasses() {
            setUserInformation.getUsername();
            ArrayList<String> typeList = setList.getCurrentPanelList();
            readClass(typeList);
            setState.setTextFieldPanel(textBoxPanel);
        }

        private void readClass(ArrayList<String> typeList) {
            boxManageCreate(setList.getFinalClassList().get(setState.getClassListIndex())+"AHC", "JLabel", false); //necessary
            boxManageCreate("Credits (optional)", "JTextField", false);
            newSet();
            classLabelPanel.setName("classlabelpanelinreadclass");
            setList.setReadClassLabelPanel(classLabelPanel);
            setList.setClassLabelPanel(classLabelPanel);

            window.add(classLabelPanel, BorderLayout.CENTER);
            create.windowFix();
        }

        private void hideWindow() {
            create.hideContainer();
            if (backNextButtonsPanel != null) {
                backNextButtonsPanel.setVisible(false);
            }
            if (newDelContainerFlow != null){
                newDelContainerFlow.setVisible(false);
            }
            //if (classLabelPanel != null) {
            classLabelPanel.setVisible(false);
            //}
            //if (textBoxPanel != null) {
            textBoxPanel.setVisible(false);
            //}
        }

        private void boxManageCreate(String placeholder, String type, Boolean boxLoaded) {
            if (numOfBoxes < maxBoxes) {
                textBoxPanel.add(create.typeBox(placeholder, type, boxLoaded));
                classLabelPanel.add(textBoxPanel);
                create.windowFix();
                numOfBoxes++;
            }
            else if (numOfBoxes == maxBoxes) {
                Decorator decorate = new Decorator();
                decorate.maximumAmountReachedPopup();
                setState.setCanContinue(false);
            }

            classLabelPanel.setVisible(true);
            count++;
            textBoxPanel.setVisible(true);
            setState.setTextFieldPanel(textBoxPanel);
            System.out.println("count "+count);
        }

        private void newSet() {
            window.getContentPane().getComponents();
            typeNumber++;
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