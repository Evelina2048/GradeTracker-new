package view.student;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import model.Set;
import model.SetUserInformation;
import model.SetState;
import model.SetList;

import controller.CreateButton;
import controller.Creator;
import controller.Decorator;
import controller.FileHandling;
import controller.FileWriting;
import controller.CompositeActionListenerWithPriorities;

import model.GoIntoPanel;
import model.SETTEST;
import view.MainWindow;
import view.student.StudentClasses;

import java.awt.Component;

public class StudentClasses extends JFrame {
    private JFrame window;

    private Creator create;
    private GoIntoPanel goIntoPanel;
    private Decorator decorate;
    private CreateButton createButton;
    private JPanel backNextButtonsPanel;
    private JButton saveButton;
    private JTextField selectedTextBox;
    private FileWriting fileWrite = new FileWriting();
    private CompositeActionListenerWithPriorities actionPriorities;
    Border borderRegular = BorderFactory.createLineBorder(Color.GRAY, 2);
    JPanel southContainer = new JPanel(new GridLayout(2,1,0,0));
    AtomicBoolean textFieldEmptied = new AtomicBoolean(false);;
    JButton newClassButton;
    JButton deleteClassButton;

    JButton nextButton;

    FileHandling fileHandler = new FileHandling();
    JLayeredPane layeredPane = new JLayeredPane();
    private String currentClass = "StudentClasses Loading";

    Set set;
    SetState setState;
    private SetUserInformation setUserInformation;
    private SetList setList;
    private WindowAdapter windowCloseAdapter;
    JPanel instructionsPanel;

    public StudentClasses() {
    }

    public void studentClassesLaunch() {
        southContainer.setName("SouthContainer");
        this.set = Set.getInstance();
        this.setState = SetState.getInstance();
        this.setUserInformation = SetUserInformation.getInstance();
        this.setList = SetList.getInstance();
        this.actionPriorities = CompositeActionListenerWithPriorities.getInstance();

        setState.setCurrentClass("StudentClasses.java");
        window = set.getWindow();

        window.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("clickkkkk");
                Component component = window.getComponentAt(e.getPoint());
                if (component != null) {
                    System.out.println("Component type at clicked point: " + component.getClass().getName() + " " + component.getName());
                } else {
                    System.out.println("No component at clicked point.");
                }
            }
        });

        window.setName("window");
        create = new Creator();
        decorate = new Decorator();
        createButton = new CreateButton();

        EnterAction enterAction = new EnterAction();
        window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        window.getRootPane().getActionMap().put("enterAction", enterAction);

        window.setTitle("StudentClasses");

        layeredPane.setVisible(true);
        layeredPane.setOpaque(false);
        layeredPane.setDoubleBuffered(true);
        window.add(layeredPane);
        create.windowFix();

        instructionsWordsAndPanel("Edit Classes Mode "+"(for " +setUserInformation.getStudentOrTeacher()+"s)");
        loadIfNeeded();
        westPanelCreate();
        buttonSetUpAction();

        actionPriorities.setCurrentClass(currentClass);

        WindowAdapter windowCloseAdapter = new WindowAdapter() {//moved 12/30
            @Override
            public void windowClosing(WindowEvent e) {
                Boolean actionPrioritiesNull = (CompositeActionListenerWithPriorities.getInstance().getCurrentClass() == null);
                if (actionPrioritiesNull) {
                    window.dispose(); //added 12/29 for when save button doesn't work
                    return;
                }
                Boolean equalsStudentClasses = CompositeActionListenerWithPriorities.getInstance().getCurrentClass().equals("StudentClasses Loading");
                Boolean equalsStudentStatCollect = CompositeActionListenerWithPriorities.getInstance().getCurrentClass().equals("StudentStatCollect");
                if ((set.getCurrentSaveButton() != null) && (set.getCurrentSaveButton().isEnabled() && (equalsStudentClasses || equalsStudentStatCollect)) ) {
                    decorate.areYouSureMessage(null, "closing window", "<html><center> You did not save <br> Close anyways?", 176, 107);
                }
                else {
                    window.dispose(); //added 12/29 for when save button doesn't work
                }
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                System.out.println("Window gained focus");
    }
        };
        Set.getInstance().setCurrentWindowClosing(windowCloseAdapter);

        window.addWindowListener(windowCloseAdapter);
    }

    private void loadIfNeeded() {
        String filePath = setUserInformation.getPathToClassTextFile();
        if (fileHandler.fileExists(filePath)) {//case for if existing file
            ArrayList<String> myList = fileHandler.readFileToList(filePath);
            for (int index=0; index<myList.size(); index++) {
                create.createTextBox(myList.get(index), "JTextField", true);
            }
            setList.setClassList(myList);
        }

        else {
            create.createTextBox("Enter Class Name", "JTextField", false);
        }
    }

    private void westPanelCreate() {
        createNewClassButton();
        deleteClassButton();

        JPanel buttonsPanel = new JPanel(new BorderLayout());

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        buttonsPanel.setPreferredSize(new Dimension(100,0));
        buttonsPanel.add(newClassButton,BorderLayout.WEST);
        buttonsPanel.add(deleteClassButton, BorderLayout.EAST);

        southContainer.add(buttonsPanel,BorderLayout.CENTER);

        window.add(southContainer, BorderLayout.SOUTH);

    }

    public void buttonSetUpAction() {
        JPanel backButtonPanel = makeBackButtonPanel();

        JPanel saveButtonPanel = makeSaveButtonPanel();

        JPanel nextButtonPanel = makeNextButtonPanel();

        backNextButtonsPanel = createButton.makeBackNextButtonsPanel(backButtonPanel, saveButtonPanel, nextButtonPanel);
        southContainer.add(backNextButtonsPanel, BorderLayout.SOUTH);
        window.add(southContainer, BorderLayout.SOUTH);
    }

    private JPanel makeBackButtonPanel() {
        JButton backButton = createButton.backButtonCreate();
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        backButton.setPreferredSize(new Dimension(87, 29));

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPriorities.setCurrentClass(currentClass);
                actionPriorities.addClassActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {// remember wont run  if just enter without a click
                        saveButtonAction();
                        hideWindow();
                        Set.getInstance().setCurrentSaveButtonToNull(); //added 12/28 to prevent windowCloseAdapter in previous classes
                        window.remove(backNextButtonsPanel); //added 12/28 to prevent windowCloseAdapter in previous classes

                        set.setWindow(window);
                        MainWindow main = new MainWindow();
                        main.MainWindowLaunch();
                        main.setButtonSelected();

                        window.removeWindowListener(windowCloseAdapter);
                    }
            }, 1, "backButton", null, currentClass);  // Add this ActionListener with priority 1
        }});
        return backButtonPanel;
    }

    private JPanel makeSaveButtonPanel() {
        saveButton = createButton.saveButtonCreate();

        saveButton.setEnabled(false);
        JPanel saveButtonPanel = new JPanel();
        saveButtonPanel.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveButtonAction();
            }
        });

        return saveButtonPanel;
    }

    private void saveButtonAction() {
        writeType();
        saveButton.setEnabled(false);
    }

    private JPanel makeNextButtonPanel() {
        nextButton = createButton.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        nextButton.setPreferredSize(new Dimension(87, 29));

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentClass = "StudentClasses";
                actionPriorities.setCurrentClass(currentClass);
                System.out.println("TTT ");
                actionPriorities.addClassActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {// remember wont run  if just enter without a click
                        currentClass = "StudentClasses";
                        System.out.println("UUU ");
                        doNextButtonProcedure();
                    }
                }, 1, "nextButton", null, currentClass);  // Add this ActionListener with priority 1
            }
        });
        return nextButtonPanel;
    }
    private void doNextButtonProcedure() {
        currentClass = "StudentClasses";

        writeType();
        System.out.println("nextbuttonhit");
        fileWrite.writeTextToFile();

        if(setList.getFinalClassList().size()>0) {
            hideWindow();
            removeFromWindow();
            create.hideContainer();

            StudentStatCollect studentStatCollect = new StudentStatCollect();
            SETTEST.getInstance().TESTSETCURRENTINSTANCE(studentStatCollect);
            if (fileHandler.fileExists("/Users/evy/Documents/GradeTracker-new/src/main/model/UserInfo/StudentInfo/" + setUserInformation.getUsername() + "/" +"ClassInformation"+"/"+setList.getFinalClassList().get(0) + ".txt")) { //needs to keep path because its with index 0
                create.hideContainer();
                studentStatCollect.studentStatCollectLaunch();
                studentStatCollect.addLoadedBoxes();
            }

            else {
                studentStatCollect.studentStatCollectLaunch();
                studentStatCollect.DisplayClasses();
            }
        }

        else {
            System.out.println("problem in studentclasses doNextButtonProcedure");
        }
    }

    private void createNewClassButton() {
        newClassButton = new JButton("New Class");
        newClassButton.setPreferredSize(new Dimension(87, 50));
        newClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                create.createTextBox("Enter Class Name", "JTextField",false);
        }
    });
    }

    private void deleteClassButton() {
        deleteClassButton = new JButton("Delete Class Mode");
        deleteClassButton.setPreferredSize(new Dimension(150, 50));
        deleteClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteMode();
            }
        });
    }

    private void writeType() {
        set.setFilePath(setUserInformation.getPathToClassTextFile());
        fileWrite.writeTextToFile();
        setList.setFinalClassList(fileWrite.getClassList());
    }

    private void backToDefaultDeleteButton() {
        removeDeleteClassButtonActionListeners();
        deleteClassButton.setText("Delete Class Mode");
        deleteClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                requestFocusInWindow();
                deleteMode();
            }
        });
    }

    private void deleteMode() {
        prepareTextboxForDeleteMode();
        saveButtonAction();
        newClassButton.setEnabled(false);
        leaveDeleteModeButton();
        window.remove(instructionsPanel);
        instructionsWordsAndPanel("Click on a box to select it");
    }

    private void instructionsWordsAndPanel(String text) {
        instructionsPanel = new JPanel();
        JLabel instructionsWords = new JLabel(text);
        instructionsPanel = decorate.InstructionsPanelDecorate(instructionsPanel, instructionsWords);
        create.windowFix();
    }

    private void prepareTextboxForDeleteMode() {
    JTextField[] tempTextField = new JTextField[1];
    tempTextField[0] = new JTextField("ERROR"); //if not overwritten will show error
    BufferedImage[] textFieldImage = new BufferedImage[1];
    textFieldImage[0] = null;

    tempTextField[0] = new JTextField("ERROR"); //if not overwritten will show error
    textFieldImage[0] = null;

        for (int i = 0; i < setState.getTextFieldPanel().getComponentCount(); i++) {
            JTextField textField = new JTextField();
            if (setState.getTextFieldPanel().getComponent(i) instanceof JTextField) {
                textField = (JTextField) setState.getTextFieldPanel().getComponent(i);
                addMouseListenerToTextboxAndFrame(textField);
            }
            else if (setState.getTextFieldPanel().getComponent(i) instanceof JPanel) {
                textField = goIntoPanel.goIntoPanelReturnTextbox((JPanel) setState.getTextFieldPanel().getComponent(i), 0);
                addMouseListenerToTextboxAndFrame(textField);
            }
            else {
                System.out.println("There was an issue in delete mode. student classes" + setState.getTextFieldPanel().getComponent(i).getClass().getName());
            }
        }
    }

    private void addMouseListenerToTextboxAndFrame(JTextField textField) {
        turnOffEditability(textField);
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedTextBox != null) {
                    selectedTextBox.setForeground(Color.GRAY);
                    selectedTextBox.setBorder(borderRegular);
                }
                Border borderHighlighted = BorderFactory.createLineBorder(Color.decode("#FF6961"), 2); //red
                textField.setForeground(Color.decode("#FF6961"));
                selectedTextBox = textField;
                textField.setBorder(borderHighlighted);
                removeDeleteClassButtonActionListeners();
                deleteQuestionButtonAndAction();
                }});

        windowMouseListener();
    }

    private void turnOffEditability(JTextField textField) {
        textField.setEditable(false);
        textField.setFocusable(false);
    }


    private void deleteQuestionButtonAndAction() {
        final String[] yesOrNoDialog = new String[1];
        yesOrNoDialog[0] = "nothing yet";
        deleteClassButton.setText("Delete?");
        window.remove(instructionsPanel);
        instructionsWordsAndPanel("Hit Delete Button to Delete");
        String filePath = setUserInformation.getPathToClassInformationFileWithTextField(selectedTextBox);
        removeDeleteClassButtonActionListeners();
        deleteClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //if the file has loaded information attached
                if (setState.getLoadedState(selectedTextBox) && (fileHandler.fileExists(filePath)) && fileHandler.fileIsNotEmpty(filePath)) {
                    yesOrNoDialog[0] = decorate.areYouSureMessage(selectedTextBox, "deleting", "<html><center>Deleting this class will delete <br>its loaded information.<br>Do you wish to continue?", 250, 120);

                    selectedTextBox.setForeground(Color.GRAY);
                    selectedTextBox.setBorder(borderRegular);
                }

                else {
                    JPanel selectedBoxPanel = new JPanel();
                    selectedBoxPanel.add(selectedTextBox); //this also makes the selected textbox invisible
                    create.deleteTextBox(selectedBoxPanel);
                    saveButton.setEnabled(true);
                }

                if (yesOrNoDialog[0].equals("yes") || yesOrNoDialog[0].equals("nothing yet")) {
                    saveButton.setEnabled(true);
                }

                pickAppropriateInstructionWordsAndPanels(yesOrNoDialog[0]);

                leaveDeleteModeButton();
            }
        });
    }

    private void pickAppropriateInstructionWordsAndPanels(String yesOrNo) {
        System.out.println("ours "+setState.getTextFieldPanel().getComponentCount());
        window.remove(instructionsPanel);
        if (setState.getTextFieldPanel().getComponentCount() > 0 && (yesOrNo == "yes")) {
            instructionsWordsAndPanel("Box deleted");
        }

        else if (setState.getTextFieldPanel().getComponentCount() > 0 && (yesOrNo == "no")){
            instructionsWordsAndPanel("Nothing deleted");
        }

        else if (setState.getTextFieldPanel().getComponentCount() == 0) {
            instructionsWordsAndPanel("All boxes deleted. Leave delete mode to edit.");
        }

        else {
            instructionsWordsAndPanel("Error");
        }
    }

    private void removeDeleteClassButtonActionListeners() {
        for (ActionListener listener : deleteClassButton.getActionListeners()) {
            deleteClassButton.removeActionListener(listener);
        }
    }

    private void leaveDeleteModeButton() {
        for (ActionListener listener : deleteClassButton.getActionListeners()) {
            deleteClassButton.removeActionListener(listener);
        }
        deleteClassButton.setText("Leave Delete Mode");

        leaveDeleteModeAction();
    }

    private void leaveDeleteModeAction() {
        removeDeleteClassButtonActionListeners();
        deleteClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.remove(instructionsPanel);
                instructionsWordsAndPanel("Left Delete Mode. In Edit Mode");
                backToDefaultDeleteButton();
                newClassButton.setEnabled(true);
                for (int i = 0; i < setState.getTextFieldPanel().getComponentCount(); i++) {
                    JTextField textField = new JTextField();
                    if (setState.getTextFieldPanel().getComponent(i) instanceof JTextField) {
                        textField = (JTextField) setState.getTextFieldPanel().getComponent(i);
                    }

                    else {
                        textField = goIntoPanel.goIntoPanelReturnTextbox((JPanel) setState.getTextFieldPanel().getComponent(i), 0);
                    }
                    textField.setEditable(true);
                    textField.setFocusable(true);
                    MouseListener[] listeners = textField.getMouseListeners();
                    if (listeners.length > 0) {
                        MouseListener lastListener = listeners[listeners.length - 1];
                        textField.removeMouseListener(lastListener);
                    }
                }
                return;
            }
        });
    }

    private void windowMouseListener() {
        window.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedTextBox != null) {
                    selectedTextBox.setForeground(Color.lightGray);
                    selectedTextBox.setBorder(borderRegular);
                    selectedTextBox = null;
                    leaveDeleteModeButton();
                }
            }
        });
    }

    private void hideWindow() {
        backNextButtonsPanel.setVisible(false);
        newClassButton.setVisible(false);
        deleteClassButton.setVisible(false);
        southContainer.setVisible(false);
        create.getTextFieldContainer().setVisible(false);
        instructionsPanel.setVisible(false);
        create.hideContainer();

        for (MouseListener listener : deleteClassButton.getMouseListeners()) {
            window.removeMouseListener(listener);
        }

    }

    private void removeFromWindow() {
        window.remove(backNextButtonsPanel);
        window.remove(newClassButton);
        window.remove(deleteClassButton);
        window.remove(southContainer);
        window.remove(create.getTextFieldContainer());
        window.remove(instructionsPanel);

        create.removeContainer();
    }

    public JButton TESTDELETECLASSBUTTON() {
        return deleteClassButton;
    }

    public JButton TESTSAVEBUTTON() {
        return saveButton;
    }

    public JButton TESTNEXTBUTTON() {
        return nextButton;
    }

    public JButton TESTNEWCLASSBUTTON() {
        return newClassButton;
    }


    public void TESTWRITETYPE() {
        set.setFilePath(setUserInformation.getPathToClassTextFile());
        fileWrite.writeTextToFile();
        setList.setFinalClassList(fileWrite.getClassList());
    }

    public class EnterAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            actionPriorities.setCurrentClass(currentClass);
            actionPriorities.addClassActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { // remember won't run if just enter without a click
                    doNextButtonProcedure();
                }
            }, 1, "EnterAction", null, currentClass);  // Add this ActionListener with priority 1
        }
    }

    }