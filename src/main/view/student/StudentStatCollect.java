package view.student;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Set;
import model.SetState;
import model.SetUserInformation;
import model.SetList;

import controller.Creator;
import model.GoIntoPanel;
import controller.Decorator;
import controller.FileHandling;
import controller.FileWriting;
import controller.CompositeActionListenerWithPriorities;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.KeyEvent;
import controller.CreateButton;

public class StudentStatCollect extends JFrame {
    private JFrame window;
    private Creator create;
    private GoIntoPanel goIntoPanel;
    private JPanel backNextButtonsPanel;
    private JButton newTypeButton;
    private Set set;
    private SetState setState;
    private SetList setList;
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
    private JPanel thisClassPanel = new JPanel();

    public StudentStatCollect() {
        System.out.println("hi");
        this.set = Set.getInstance();
        this.setState = SetState.getInstance();
        this.setUserInformation = SetUserInformation.getInstance();
        this.setList = SetList.getInstance();
        this.actionPriorities = CompositeActionListenerWithPriorities.getInstance();


        actionPriorities.setCurrentClass(currentClass);
        create = new Creator();
        goIntoPanel = new GoIntoPanel();
        fileHandler = new FileHandling();

        setState.setCurrentClass("StudentStatCollect.java");
        window = set.getWindow();


        //studentStatCollectLaunch();
    }

    public void studentStatCollectLaunch() {
        System.out.println("hi");

        ///System.out.println("entering student july"+ textBoxPanel.ge);
        window.setName("window");
        container.setName("contianer");
        //container.setBackground(Color.pink);
        textBoxPanel.setName("gridlayout textboxpanel");
        allBoxesPanel.setName("allboxespanel");
        //textBoxPanel.setBackground(lightgrayColor);
        classLabelPanel.setName("classLabelPanel student stat");
        EnterAction enterAction = new EnterAction();
        window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        window.getRootPane().getActionMap().put("enterAction", enterAction);

        finalClassList = setList.getFinalClassList();
        //System.out.println(" "+setList.getFinalClassList());
        System.out.println("final class list issssss before: "+ finalClassList);
        finalClassList = setList.getFinalClassList();
        System.out.println("final class list issssss: "+ finalClassList);
        create.hideContainer();

        createNewTypeButton();
        buttonSetUpAction();
        window.setTitle("StudentStatCollect");

        currentClass = "StudentStatCollect";
        actionPriorities.setCurrentClass(currentClass);


        //<
        JTextField testTextField = new JTextField("Error");
        if (textBoxPanel.getComponentCount() < 2) {
            System.out.println("1111dirt it does not exist");
        }
        else if (textBoxPanel.getComponent(1) instanceof JPanel) {
            testTextField = (JTextField) goIntoPanel.goIntoPanelReturnTextbox((JPanel) textBoxPanel.getComponent(1), 0);
        }
        else {
            testTextField = (JTextField) setState.getTextFieldPanel().getComponent(1);
        }
        System.out.println("1111dirt "+testTextField.getText());

        //>

    }

    public void buttonSetUpAction() {
        System.out.println("hi");
        System.out.println("in button set up action");
        JButton backButton = createButton.backButtonCreate();
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        backAction(backButton);
        JButton saveButton = createButton.saveButtonCreate();
        System.out.println("is savebutton set version? "+(saveButton==set.getCurrentSaveButton()));
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
        System.out.println("hi");
        textBoxPanel.removeAll();
        textBoxPanel.revalidate();
        textBoxPanel.repaint();
    }
    
    private void backAction(JButton backButton) {
        System.out.println("hi");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textBoxPanel.getComponentCount() >= 2) {
                    JTextField testTextField = (JTextField) goIntoPanel.goIntoPanelReturnTextbox((JPanel) textBoxPanel.getComponent(1), 0);
                    System.out.println("3333dirt "+testTextField.getText());
                }

                else {
                    System.out.println("3333dirt issue");
                }
                actionPriorities.setCurrentClass(currentClass);
                actionPriorities.addClassActionListener(b -> {
                    if(setState.getCanContinue()) {
                        //hideWindow();
                        //setState.decrementClassListIndex();
                        System.out.println("classlabelpanel "+classLabelPanel.getComponentCount());
                        saveButtonAction();
                        if (setState.getClassListIndex() == 0) { //case for back to classes
                            System.out.println("hidewindow1");
                            hideWindow();
                            StudentClasses studentClasses = new StudentClasses();
                            studentClasses.studentClassesLaunch();
                            saveButtonAction();
                        }
                        else if (setState.getClassListIndex() > 0) {
                            //hideWindow();
                            //textBoxPanel.removeAll();
                            goToPreviousClasses();      
                        }
                    }
                }, 2, "backButton",null, currentClass);
        }});
        //:
    }

    private void goToPreviousClasses() {
        System.out.println("hi");
        saveButtonAction();
        if (setState.getCanContinue()) {
            System.out.println("there are previous classes");
            setState.decrementClassListIndex();

            //window.setVisible(false);
            Component[] windowComponents = window.getContentPane().getComponents();

            //for (Component component:windowComponents) {
            System.out.println("windowcomps "+windowComponents.length);
            //for (int i = 0; i<windowComponents.length;i++) {
            //for (int i = 13; i<25;i++) {
            //for (int i = 20; i<25;i++) {
            //for (int i = 20; i<23;i++) {
            //for (int i = 21; i<23;i++) {
            for (int i = 22; i<23;i++) {

            //thisClassPanel.setVisible(false);

            // for (int i = 22; i<23;i++) { //component is at index
                Component component = windowComponents[i];
                //if (component.contains(textField)) {
                System.out.println("component "+component.getName());
                System.out.println("componentclasslabelpanelinreadclasscheck " + classLabelPanel.isVisible());
                //}
                component.setVisible(false);
                System.out.println("componentclasslabelpanelinreadclasscheck " + classLabelPanel.isVisible());
            }
            //setList.getClassLabelPanel().setVisible(false);

            setList.getClassLabelPanel().removeAll();
            classLabelPanel.removeAll();
            //textBoxPanelReset();
            addLoadedBoxes();
        }
    }

    public void addLoadedBoxes() {
        System.out.println("hi");
        String filePath = setUserInformation.getPathToClassInformationFileWithIndex();
        //textBoxPanelReset(); //8/3
        //setState.setTextFieldPanel(textBoxPanel); //8/3

        ///////////////// //create.hideContainer();
        
        JPanel loadedBoxesPanel = fileHandler.loadTextboxes(filePath);
        loadedBoxesPanel.setName("loadedBoxesPanel");

        int numberOfComponents = loadedBoxesPanel.getComponentCount();
        numOfBoxes += numberOfComponents;
        for (int i = 0; i < numberOfComponents; i++) {
           textBoxPanel.add(loadedBoxesPanel.getComponent(0));
        }
        //setState.setTextFieldPanel(textBoxPanel); //8/3
        classLabelPanel.add(textBoxPanel);




        // ////<
        // //setList.setClassLabelPanel(classLabelPanel);
        // // ArrayList<String> loadedBoxesList = fileHandler.readFileToList(filePath);
        // // int numberOfComponents = loadedBoxesList.size();
        // // numOfBoxes += numberOfComponents;
        // // boxManageCreate("hello", "JLabel",false);
        // // for (int i = 0; i < numberOfComponents; i++) {
        // //     boxManageCreate(loadedBoxesList.get(i), "JTextField", true);
        // // }

        // //create.hideContainer();





        // //classLabelPanel.setBackground(Color.PINK);
        // //classLabelPanel.setBackground(lightgrayColor);
        // ////>
        if (numOfBoxes == 0) {
            boxStarterPack();
        }    

        textBoxPanel.setVisible(true);
        classLabelPanel.setVisible(true);
        // //window.add(classLabelPanel);
        
        create.windowFix();
    }

    private void saveAction(JButton saveButton) {
        System.out.println("hi");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check if textbox is vaild
                System.out.println("8/3.03 panel count. for: "+setList.getFinalClassList().get(setState.getClassListIndex())+" should be 0-5"+textBoxPanel.getComponentCount());
                saveButton.setEnabled(false);
                saveButtonAction();
            }
        });
    }

    private void saveButtonAction() {
        System.out.println("hi");
        System.out.println("8/3.04 panel count. for: "+setList.getFinalClassList().get(setState.getClassListIndex())+" should be 0-5"+textBoxPanel.getComponentCount());
        //Pattern pattern = Pattern.compile("^(?:[0-9]*(?:.[0-9]+))*\s*$|^[0-9]*\s*$", Pattern.CASE_INSENSITIVE);
        Pattern pattern = Pattern.compile("^(?:[0-9]*(?:.[0-9]+))*\s*$|^[0-9]*\s*$", Pattern.CASE_INSENSITIVE);
        correctGradeFormatChecker(pattern);
        
        //if (textBoxPanel.getComponentCount() == 5 || 8 || 11 || 14 || 17 || 20 || 23 || 26 || 29 || 32) {
        //if ((textBoxPanel.getComponentCount() - 5) % 3 == 0) { //only want to write if 
        System.out.println("testtesttest: "+ setState.getCurrentClass());
        //set.setFilePath("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + setUserInformation.getUsername() +"/ClassInformation/"+finalClassList.get(setState.getClassListIndex())+ ".txt");
        set.setFilePath(setUserInformation.getPathToClassInformationFileWithIndex());
        create.setTextFieldContainer(setState.getTextFieldPanel());


        //System.out.println("000.1 "+goIntoPanel.goIntoPanelReturnTextbox((JPanel) textBoxPanel.getComponent(1), 0).getText());
        System.out.println("11 textboxpanelcomps "+setState.getTextFieldPanel().getComponentCount() +" textboxpanel "+textBoxPanel.getComponentCount()+" "+ setList.getFinalClassList().get(setState.getClassListIndex()));
        setState.setTextFieldPanel(textBoxPanel);//8/3

        System.out.println("22 textboxpanelcomps "+setState.getTextFieldPanel().getComponentCount()+" "+textBoxPanel.getComponentCount()+setList.getFinalClassList().get(setState.getClassListIndex()));
        //if (fileWrite.howManyPlaceholders() == 0) {
        //setState.setCanContinue(true);


        fileWrite.writeTextToFile();


        //}


        if (fileWrite.howManyPlaceholders() > 0) { 
            Decorator decorate = new Decorator();
            decorate.areYouSureMessage(null, "studentStatsEmpty", "Remove placeholder(s) to continue?", 230, 90);
            setState.setCanContinue(false);
        }

       // }
    }

    private void nextButtonAction(JButton nextButton) {
        System.out.println("hi");
        // nextButton.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         doNextButtonProcedure();
        //     }
        // });

        //:
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPriorities.addClassActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {// remember wont run  if just enter without a click
                        System.out.println("enteraction");
                        doNextButtonProcedure();
                    }
                }, 1, "nextButton", null, currentClass);  // Add this ActionListener with priority 1
            }
        });
        //:
    }

    public void doNextButtonProcedure() {
        System.out.println("hi");
        //System.out.println("8/3.03 panel count in donextbuttonprocedure. for: "+setList.getFinalClassList().get(setState.getClassListIndex())+" should be 0-5"+textBoxPanel.getComponentCount());
        saveButtonAction();
        if(setState.getCanContinue()) {
            //if (setState.getClassListIndex() != setList.getFinalClassList().size()) {
                setState.incrementClassListIndex();
                //JTextField testTextField = (JTextField) goIntoPanel.goIntoPanelReturnTextbox((JPanel) textBoxPanel.getComponent(1), 0);
                //System.out.println("2222dirt "+testTextField.getText());
            //}
            if (setState.getClassListIndex()+1 <= setList.getFinalClassList().size()) {
                System.out.println("the jlabel name: "+setList.getFinalClassList().get(setState.getClassListIndex()));
                //hideWindow();

                //check if credits is valid
                //setState.getTextFieldPanel().getComponent(1);

                Component credits = setState.getTextFieldPanel().getComponent(1);
                if (credits instanceof JPanel) {
                    //TODO regex
                    JTextField creditsTextField = goIntoPanel.goIntoPanelReturnTextbox((JPanel) credits, 0);
                    System.out.println("credits? "+ creditsTextField.getText());
                    String text = creditsTextField.getText();
                    Pattern pattern = Pattern.compile("[0-9]+");
                    Matcher matcher = pattern.matcher(text);
                    Boolean matcherBoolean = matcher.find();

                    if (matcherBoolean == true) {
                        System.out.println("Boo");
                        textBoxPanel.removeAll();

                        //textBoxPanelReset();

                        setState.setTextFieldPanel(textBoxPanel);//8/3
                        visitNextStudentClass();
                        studentStatCollectLaunch();
                    }

                    else if (matcherBoolean == false) {
                        System.out.println("*HI*");
                        //decorate.errorMessageSetUp();
                        Decorator decorate = new Decorator();
                        JDialog dialog = decorate.genericPopUpMessage("Credits text is invalid. Must be integer",null,250,90);

                        //dialog = genericPopUpMessage("<html><center>Please choose an option", button, 200, 90);
                        dialog.setResizable(false);
                        
                        dialog.setLocationRelativeTo(null);
                        //dialog.setLocation(dialog.getX(), dialog.getY() + 15); 
                        dialog.setVisible(true);
                    }

                    else {
                        System.out.println("matcher credit boolean, something wrong");
                    }
                    
                    //Matcher matcher = pattern.matcher(text);
                    //Boolean matcherBoolean = matcher.find();


                    //L
                    // int index = 4;
                    // for (int i=1; i<=8; i++) { //max num of grade type
                    //     //String text = create.goIntoPanel(classLabelPanel, index);
                    //     String text = goIntoPanel.goIntoPanel(classLabelPanel, index);
            
                    //     if (text.equals("does not exist")) {
                    //         System.out.println("fail");
                    //         break;
                    //     }
                    //     index = index + 3;
                    //     System.out.println("text before matcher works?"+text);
                    //     Matcher matcher = pattern.matcher(text);
                    //     Boolean matcherBoolean = matcher.find();
            
                    //     System.out.println("matcher works?: "+ matcherBoolean);
                    // }
                    //L
                }
                //backNextButtonsPanel.setVisible(false);
            }
            else {
                //check if credits is valid
                setState.getTextFieldPanel().getComponent(1);

                //\\
                //check if credits is valid
                Component credits = setState.getTextFieldPanel().getComponent(1);
                if (credits instanceof JPanel) {
                    //TODO regex
                    JTextField creditsTextField = goIntoPanel.goIntoPanelReturnTextbox((JPanel) credits, 0);
                    System.out.println("credits? "+ creditsTextField.getText());
                    String text = creditsTextField.getText();
                    Pattern pattern = Pattern.compile("[0-9]+");
                    Matcher matcher = pattern.matcher(text);
                    Boolean matcherBoolean = matcher.find();

                    if (matcherBoolean == true) {
                        System.out.println("Boo");
                        // visitNextStudentClass();
                        // studentStatCollectLaunch();
                        System.out.println("hidewindow2");
                        hideWindow();
                        new PrintStudentGrades(set.getWindow(), setUserInformation.getStudentOrTeacher(), setUserInformation.getExistingOrNew());
                    }

                    else if (matcherBoolean == false) {
                        System.out.println("*HI*");
                        Decorator decorate = new Decorator();
                        JDialog dialog = decorate.genericPopUpMessage("Credits text is invalid. Must be integer",null,250,90);
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
    }

    public void visitNextStudentClass() {
        System.out.println("hi");
        //readClass(finalClassList);
        String filePath = setUserInformation.getPathToClassInformationFileWithIndex();
        if (fileHandler.fileExists(filePath)) {
            //textBoxPanelReset(); //8/3

            addLoadedBoxes();
            
            classLabelPanel.add(textBoxPanel);
            if (textBoxPanel.getComponentCount() <= 1) { //The reason it is <=1 and not 0, is to account for the classLabel
                boxStarterPack();
            }
            create.windowFix();
        }
        else { //first time visiting next class //TODO
            System.out.println("hidewindow3");
            hideWindow();
            //System.out.println("8/3.025 panel count. for: "+setList.getFinalClassList().get(setState.getClassListIndex())+" should be 5"+textBoxPanel.getComponentCount());
            StudentStatCollect studentStatCollect = new StudentStatCollect();
            //studentStatCollect.studentStatCollectLaunch();

            //System.out.println("8/3.040 panel count. for: "+setList.getFinalClassList().get(setState.getClassListIndex())+" should be 5"+textBoxPanel.getComponentCount());
            studentStatCollect.DisplayClasses();
            textBoxPanel = setState.getTextFieldPanel();
            System.out.println("8/3.1 panel count. for: "+setList.getFinalClassList().get(setState.getClassListIndex())+" should be 5"+textBoxPanel.getComponentCount());
            //System.out.println("8/3.1 panel count. should be 5"+textBoxPanel.getComponentCount());
        }

    }

    private void boxStarterPack() {
        System.out.println("hi");
        boxManageCreate("Credits (optional)", "JTextField", false);
        newSet();
    }

   private void createNewTypeButton() {
    System.out.println("hi");
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
       //JPanel swapTypesButtonPanel = swapTypesButtonPanel();
       newDelContainer.add(newTypeButtonPanel);
       //newDelContainer.add(swapTypesButtonPanel);
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
    System.out.println("hi");
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

//    private JPanel swapTypesButtonPanel() {
//     JButton swapClassesButton = new JButton("Swap Types Mode");
//         swapClassesButton.setPreferredSize(new Dimension(90, 50));
//         swapClassesButton.addActionListener(new ActionListener() {
//         public void actionPerformed(ActionEvent e) {
//             if (textBoxPanel.getComponentCount() >= 8) {
//                 create.deleteTextBox(textBoxPanel);
//                 create.deleteTextBox(textBoxPanel);
//                 create.deleteTextBox(textBoxPanel);
//                 numOfBoxes = numOfBoxes - 3;
//                 typeNumber--;
                
//                 createButton.saveButtonEnable();
//             }
//     }
//     });  
//     JPanel swapClassesButtonPanel = new JPanel(new BorderLayout());
//     swapClassesButtonPanel.add(swapClassesButton,BorderLayout.NORTH);
//     return swapClassesButtonPanel;
    
// }

   private void correctGradeFormatChecker(Pattern pattern) {
    System.out.println("hi");
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
        System.out.println("hi");
        setUserInformation.getUsername();
        ArrayList<String> typeList = setList.getCurrentPanelList();
        readClass(typeList);
        System.out.println("8/3.028 panel count. for: "+setList.getFinalClassList().get(setState.getClassListIndex())+" should be 5"+textBoxPanel.getComponentCount());
        setState.setTextFieldPanel(textBoxPanel);
    }

    private void readClass(ArrayList<String> typeList) { 
        System.out.println("hi");
        System.out.println("8/3.025 panel count. for: "+setList.getFinalClassList().get(setState.getClassListIndex())+" should be 5"+textBoxPanel.getComponentCount());
        System.out.println("readclass test:"+ setList.getFinalClassList()+"index: "+setState.getClassListIndex());
        boxManageCreate(setList.getFinalClassList().get(setState.getClassListIndex())+"AHC", "JLabel", false); //necessary
        boxManageCreate("Credits (optional)", "JTextField", false);
        newSet();
        System.out.println("8/3.026 panel count. for: "+setList.getFinalClassList().get(setState.getClassListIndex())+" should be 5"+textBoxPanel.getComponentCount());
        //thisClassPanel = classLabelPanel;
        classLabelPanel.setName("classlabelpanelinreadclass");
        setList.setClassLabelPanel(classLabelPanel);

        window.add(classLabelPanel, BorderLayout.CENTER);
        create.windowFix();
        System.out.println("8/3.027 panel count. for: "+setList.getFinalClassList().get(setState.getClassListIndex())+" should be 5"+textBoxPanel.getComponentCount());
    }

    private void hideWindow() {
        System.out.println("hi");
        //System.out.println("about to hide window in "+setList.getFinalClassList().get(setState.getClassListIndex()));
        create.hideContainer();
        backNextButtonsPanel.setVisible(false);
        newDelContainerFlow.setVisible(false);
        classLabelPanel.setVisible(false);
        //classLabelPanel.removeAll();
        textBoxPanel.setVisible(false);

        //window.removeAll();
        //window.setAllComponentsVisible(false);
        
    }

    private void boxManageCreate(String placeholder, String type, Boolean boxLoaded) {
        System.out.println("hi");
        System.out.println("8/3.01 panel count. for: "+setList.getFinalClassList().get(setState.getClassListIndex())+" should be 0-5"+textBoxPanel.getComponentCount());
        if (numOfBoxes < maxBoxes) {
            System.out.println("test.1 "+ placeholder +" "+type+ "textboxpanel components"+textBoxPanel.getComponentCount());
            textBoxPanel.add(create.typeBox(placeholder, type, boxLoaded));//false));
            System.out.println("test.2 "+ placeholder +" "+type+ "textboxpanel components"+textBoxPanel.getComponentCount());
            System.out.println("8/3.02 panel count. for: "+setList.getFinalClassList().get(setState.getClassListIndex())+" should be 1-5 "+textBoxPanel.getComponentCount());
            classLabelPanel.add(textBoxPanel);
            create.windowFix();
            numOfBoxes++;
        }
        else if (numOfBoxes == maxBoxes) {
            Decorator decorate = new Decorator(); 
            decorate.maximumAmountReachedPopup();
            setState.setCanContinue(false);
        }
        //setState.setTextFieldPanel(textBoxPanel);//8/3
        classLabelPanel.setVisible(true);
        textBoxPanel.setVisible(true);
        setState.setTextFieldPanel(textBoxPanel);
        System.out.println("8/3.021 panel count. for: "+setList.getFinalClassList().get(setState.getClassListIndex())+" should be 0-5"+textBoxPanel.getComponentCount());
    }

    private void newSet() {
        System.out.println("hi");
        Component[] windowComponents = window.getContentPane().getComponents();
        System.out.println("windowcomponents "+windowComponents.length);
        //for (int i = 0; i<windowComponents.length;i++) {
        //for (int i = 0; i<20;i++) {
        //for (int i = 10; i<20;i++) {
        // for (int i = 18; i<19;i++) {


        // //     //thisClassPanel.setVisible(false);

        // //     // for (int i = 22; i<23;i++) { //component is at index
        //          Component component = windowComponents[i];
        // //         if (component instanceof JPanel) {
        // //             JPanel jpanel = (JPanel) component;
        // //             if (jpanel.getComponentCount() == 0) {
        // //                 System.out.println("i "+i);
        // //                 component.setVisible(false);
        // //                 //component.dispose();
        // //             }
        // //         }
        // //         //if (component.contains(textField)) {
        // //         System.out.println("component "+component.getName());
        // //         System.out.println("componentclasslabelpanelinreadclasscheck " + classLabelPanel.isVisible());
        // //         //}
        //          component.setVisible(false);
        // //         System.out.println("componentclasslabelpanelinreadclasscheck " + classLabelPanel.isVisible());
        // }


        System.out.println("8/3.022 panel count. for: "+setList.getFinalClassList().get(setState.getClassListIndex())+" should be 0-5"+textBoxPanel.getComponentCount());
        System.out.println("classlabelpanel visible"+classLabelPanel.isVisible());
        typeNumber++;
        boxManageCreate("Grade Type "+typeNumber, "JTextField",false);
        boxManageCreate("Percentage of Grade", "JTextField",false);
        boxManageCreate("Grades(format:# # #)", "JTextField",false);
        System.out.println("8/3.023 panel count. for: "+setList.getFinalClassList().get(setState.getClassListIndex())+" should be 0-5"+textBoxPanel.getComponentCount());
        setState.setCanContinue(true);
        System.out.println("classlabelpanel visible"+classLabelPanel.isVisible());
        System.out.println("8/3.024 panel count. for: "+setList.getFinalClassList().get(setState.getClassListIndex())+" should be 0-5"+textBoxPanel.getComponentCount());
        setState.setTextFieldPanel(textBoxPanel);






        // Component[] windowComponents = window.getContentPane().getComponents();
        // System.out.println("windowcomponents "+windowComponents.length);

        // for (int i = 0; i<windowComponents.length;i++) {

        //     //thisClassPanel.setVisible(false);

        //     // for (int i = 22; i<23;i++) { //component is at index
        //         Component component = windowComponents[i];
        //         if (component instanceof JPanel) {
        //             JPanel jpanel = (JPanel) component;
        //             if (jpanel.getComponentCount() == 0) {
        //                 System.out.println("i "+i);
        //                 component.setVisible(false);
        //                 //component.dispose();
        //             }
        //         }
        //         //if (component.contains(textField)) {
        //         System.out.println("component "+component.getName());
        //         System.out.println("componentclasslabelpanelinreadclasscheck " + classLabelPanel.isVisible());
        //         //}
        //         //component.setVisible(false);
        //         System.out.println("componentclasslabelpanelinreadclasscheck " + classLabelPanel.isVisible());
        // }
    }

    // private void calculate() {
    //     //parse the grade situation. but then needs correct input.
    // }

    ////
    public class EnterAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("hi");
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
    }