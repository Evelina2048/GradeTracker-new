package main.view.student;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import main.model.Set;
import main.controller.Creator;
import main.controller.Decorator;
import main.controller.FileHandler;
import main.view.Gather;



public class StudentClasses extends JFrame {
    private JFrame window;
    private Creator creator;
    private Decorator decorator;
    private JPanel backNextButtonsPanel;
    private JButton saveButton;
    private JTextField selectedTextBox;
    Border borderRegular = BorderFactory.createLineBorder(Color.GRAY, 2);
    JPanel southContainer = new JPanel(new GridLayout(2,1,0,0));
    AtomicBoolean textFieldEmptied = new AtomicBoolean(false);;
    JButton newClassButton;
    JButton deleteClassButton;
    Decorator decorate = new Decorator();
    FileHandler fileHandler = new FileHandler();
    Set set;
    JPanel instructionsPanel;

    //JTextField textField = decorate.decorateTextBox();

    public StudentClasses() {
        //createNewClassButton();
        // westPanelCreate();
        // buttonSetUpAction(main, newUser, studentOrTeacher, existingOrNew);
    }

    public void studentClassesLaunch() {
        this.set = Set.getInstance();
        this.window = set.getWindow();
        creator = new Creator();
        decorator = new Decorator();
        System.out.println("in student classes");
        window.setTitle("StudentClasses");
        window.setLayout(new BorderLayout());
        instructionsWordsAndPanel("Edit Textbox Mode");
        loadIfNeeded();
        westPanelCreate();
        buttonSetUpAction();
    }

    private void loadIfNeeded() {
        String filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+set.getUsername()+"/class.txt";
        if (fileHandler.fileExists(filePath)) {//case for if existing file
            System.out.println("I have info to load!");
            ArrayList<String> myList = fileHandler.readFileToList(filePath);
            for (int index=0; index<myList.size(); index++) {
                creator.createTextBox(myList.get(index), 10, 50, true);
            }
            set.setClassList(myList);
            set.setFinalClassList(set.getCurrentPanelList());
            prepareTextboxForDeleteMode();
            
        }

        else {
            creator.createTextBox("Enter Class Name", 50, 50, false);
        }
    }

    private void westPanelCreate() {
        createNewClassButton();
        System.out.println("before creating delete class button"+set.getCurrentPanelList());
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

        backNextButtonsPanel = creator.makeBackNextButtonsPanel(backButtonPanel, saveButtonPanel, nextButtonPanel);
        southContainer.add(backNextButtonsPanel, BorderLayout.SOUTH);
        window.add(southContainer, BorderLayout.SOUTH);
    }

    private JPanel makeBackButtonPanel() {
        JButton backButton = creator.backButtonCreate();
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        backButton.setPreferredSize(new Dimension(87, 29));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Gather gather = new Gather();
                gather.setTextToUsername();
                hideWindow(); 
        }});
        return backButtonPanel;
    }

    private JPanel makeSaveButtonPanel() {
        saveButton = creator.saveButtonCreate();
        saveButton.setEnabled(false);
        JPanel saveButtonPanel = new JPanel();
        saveButtonPanel.add(saveButton);
        //saveButtonAction();
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
        JButton nextButton = creator.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        nextButton.setPreferredSize(new Dimension(87, 29));
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextButtonAction();
            }
        });
        return nextButtonPanel;
    }
    private void nextButtonAction() {
            ArrayList<String> classList;
            classList = set.getCurrentPanelList();
            set.setClassList(classList);
            writeType();
            System.out.println("nextbuttonhit");
            creator.writeFolderToFile(textFieldEmptied);
            creator.writeTextToFile("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() + "/class.txt", creator.getTextFieldContainer());
            set.setFinalClassList(classList); //lookie
            hideWindow();
            creator.hideContainer();
            set.setFinalClassList(set.getCurrentPanelList());
            StudentStatCollect studentStatCollect = new StudentStatCollect();
             if (fileHandler.fileExists("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() + "/" + set.getFinalClassList().get(0) + ".txt")) {
                studentStatCollect.addLoadedBoxes();
            }

            else {
                studentStatCollect.DisplayClasses();
            }
    }

    private void createNewClassButton() {
        newClassButton = new JButton("New Class");
        newClassButton.setPreferredSize(new Dimension(87, 50));
        newClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                creator.createTextBox("Enter Class Name", 50, 50, false);
                System.out.println("class list in new class button"+ set.getCurrentPanelList());
                
        }
    });
        // westPanel.add(newClassButton, BorderLayout.SOUTH);

        // window.add(westPanel,BorderLayout.WEST);
    }

    private void deleteClassButton() {
        deleteClassButton = new JButton("Delete Class Mode");
        deleteClassButton.setPreferredSize(new Dimension(150, 50));
        deleteClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //decorator.errorMessageSetUp(window, button);
                //creator.deleteTextBox(creator.getTextFieldContainer());
                System.out.println("deleteClass Mode hit");
                deleteMode();
                saveButton.setEnabled(true);
            }
        });
    }

    private void writeType() {
        creator.writeFolderToFile(textFieldEmptied);
        creator.setClassList();
        creator.writeTextToFile("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() + "/class.txt", creator.getTextFieldContainer());
    }

    private void backToDefaultDeleteButton() {
        //if (set.getTextFieldPanel().getComponent(0).get) {}
        removeActionListeners();
        deleteClassButton.setText("Delete Class Mode");
        deleteClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //decorator.errorMessageSetUp(window, button);
                //creator.deleteTextBox(creator.getTextFieldContainer());
                System.out.println("clicked delete class mode");
                deleteMode();
                saveButton.setEnabled(true);
            }
        });
    }

    private void deleteMode() {
        saveButtonAction();
        newClassButton.setEnabled(false);
        leaveDeleteModeButton();
        prepareTextboxForDeleteMode();

        window.remove(instructionsPanel);
        instructionsWordsAndPanel("Click on a box to select it");
        
    }

    private void instructionsWordsAndPanel(String text) {
        instructionsPanel = new JPanel();
        //instructionsWords = new JLabel();
        JLabel instructionsWords = new JLabel(text);
        instructionsPanel = decorator.InstructionsPanelDecorate(instructionsPanel, instructionsWords);
        creator.windowFix();
    }

    private void prepareTextboxForDeleteMode() {
        for (int i = 0; i < set.getTextFieldPanel().getComponentCount(); i++) { 
            if (set.getTextFieldPanel().getComponent(i) instanceof JTextField) {
                JTextField textField = (JTextField) set.getTextFieldPanel().getComponent(i);
                addMouseListenerToTextboxAndFrame(textField);
            }
            else if (set.getTextFieldPanel().getComponent(i) instanceof JPanel) {
                JTextField textField = creator.goIntoPanelReturnTextbox((JPanel) set.getTextFieldPanel().getComponent(i), 0);
                addMouseListenerToTextboxAndFrame(textField);
            }
            
            else {
                System.out.println("There was an issue in delete mode. student classes" + set.getTextFieldPanel().getComponent(i).getClass().getName());
            }
    }
    }

    private void addMouseListenerToTextboxAndFrame(JTextField textField) {
        turnOffEditability(textField);
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("clicked");
                if (selectedTextBox != null) {
                    selectedTextBox.setForeground(Color.GRAY);
                    selectedTextBox.setBorder(borderRegular);
                }
                Border borderHighlighted = BorderFactory.createLineBorder(Color.decode("#FF6961"), 2);
                textField.setForeground(Color.decode("#FF6961"));
                selectedTextBox = textField;
                textField.setBorder(borderHighlighted);
                removeActionListeners();
                deleteQuestionButtonAndAction();
                }}); 

        windowMouseListener();
    }

    private void turnOffEditability(JTextField textField) {
        textField.setEditable(false);
        textField.setFocusable(false);
    }


    private void deleteQuestionButtonAndAction() {
        deleteClassButton.setText("Delete?");
        window.remove(instructionsPanel);
        instructionsWordsAndPanel("Hit Delete Button to Delete");
        String filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+set.getUsername()+"/"+selectedTextBox.getText()+".txt";
        removeActionListeners();
        System.out.println("is this even being run");
        deleteClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("letssss gooooo");
                System.out.println("count:" + set.getTextFieldPanel().getComponentCount());
                saveButtonAction();
                
                if (set.getLoadedState(selectedTextBox) && (fileHandler.fileExists(filePath)) && fileHandler.fileIsNotEmpty(filePath)) {
                    //System.out.println("text: "+ selectedTextBox.getText()+" loaded state: "+ set.getLoadedState(textField));
                    decorator.areYouSureMessageSetUp(deleteClassButton, selectedTextBox);
                
                    selectedTextBox.setForeground(Color.GRAY);
                    selectedTextBox.setBorder(borderRegular);
                }

                else {
                    JPanel selectedBoxPanel = new JPanel();
                    selectedBoxPanel.add(selectedTextBox);
                    creator.deleteTextBox(selectedBoxPanel);
                }


                pickAppropriateInstructionWordsAndPanels();

                leaveDeleteModeButton();
            }
            });
    }

    private void pickAppropriateInstructionWordsAndPanels() {
        window.remove(instructionsPanel);
        if (set.getTextFieldPanel().getComponentCount() > 0) {
            instructionsWordsAndPanel("Box deleted");
            System.out.println("boxdeleted");
            
        }

        else if (set.getTextFieldPanel().getComponentCount() == 0) {
            instructionsWordsAndPanel("All boxes deleted. Leave delete mode to edit.");
            System.out.println("all boxes deleted");
            
        }

        else {
            System.out.println("an error");
        }
    }

    private void removeActionListeners() {
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
        removeActionListeners();
        deleteClassButton.addActionListener(new ActionListener() {
            //instructionsWordsAndPanel("<3");
            //instructionsWordsAndPanel("h");
            public void actionPerformed(ActionEvent e) {
                window.remove(instructionsPanel);
                instructionsWordsAndPanel("Left Delete Mode. In Edit Mode");
                backToDefaultDeleteButton();
                newClassButton.setEnabled(true);
                for (int i = 0; i < set.getTextFieldPanel().getComponentCount(); i++) {
                    //JTextField textField = (JTextField) set.getTextFieldPanel().getComponent(i);
                    System.out.println("look: "+set.getTextFieldPanel().getComponent(i).getClass().getName());

                    JTextField textField = new JTextField();
                    if (set.getTextFieldPanel().getComponent(i) instanceof JTextField) {
                        textField = (JTextField) set.getTextFieldPanel().getComponent(i);
                    }

                    else {
                        textField = creator.goIntoPanelReturnTextbox((JPanel) set.getTextFieldPanel().getComponent(i), 0);
                    }
                    System.out.println("about to make editable");
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
                System.out.println("in frame");
                if (selectedTextBox != null) {
                    selectedTextBox.setForeground(Color.GRAY);
                    selectedTextBox.setBorder(borderRegular);
                }
                
            }
        });

    }

    private void hideWindow() {
        backNextButtonsPanel.setVisible(false);
        newClassButton.setVisible(false);
        deleteClassButton.setVisible(false);
        southContainer.setVisible(false);
        creator.getTextFieldContainer().setVisible(false);
        instructionsPanel.setVisible(false);

    }

    }