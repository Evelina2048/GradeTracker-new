package main.view.student;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
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
    JPanel southContainer = new JPanel(new GridLayout(2,1,0,0));
    AtomicBoolean textFieldEmptied = new AtomicBoolean(false);;
    JButton newClassButton;
    JButton deleteClassButton;
    Decorator decorate = new Decorator();
    FileHandler fileHandler = new FileHandler();
    Set set;

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

    
        
        // if (set.getFinalClassList() != null) {//case for if existing file
        //     System.out.println("I have info to load!");

        // }
        String filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+set.getUsername()+"/class.txt";
        if (fileHandler.fileIsNotEmpty(filePath)) {//case for if existing file
            System.out.println("I have info to load!");
            ArrayList<String> myList = fileHandler.readFileToList(filePath);
            System.out.println("classList<3"+set.getFinalClassList());
            for (int index=0; index<myList.size(); index++) {
                creator.createTextBox(myList.get(index), 10, 50, true);
            }
            set.setClassList(myList);
            set.setFinalClassList(set.getCurrentPanelList());
            System.out.println("classList<3``````"+set.getFinalClassList());


        }

        else {
            creator.createTextBox("Enter Class Name", 50, 50, true);
        }
        //textField.setVisible(true);
        westPanelCreate();
        buttonSetUpAction();
    }

    public void buttonSetUpAction() {
        JButton backButton = creator.backButtonCreate();
        ///
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        ////
        backButton.setPreferredSize(new Dimension(87, 29));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Gather gather = new Gather();
                gather.setTextToUsername();
                //gather.showWindow(window.getX(), window.getY());
                hideWindow(); 
               //backButtonAction(main, newUser, studentOrTeacher, existingOrNew);
            }
        });

        saveButton = creator.saveButtonCreate();
        saveButton.setEnabled(false);
        JPanel saveButtonPanel = new JPanel();
        saveButtonPanel.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeType();
                saveButton.setEnabled(false);
            }
        });

        JButton nextButton = creator.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        nextButton.setPreferredSize(new Dimension(87, 29));
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> classList;
                classList = set.getCurrentPanelList();
                System.out.println("aaaaaaaa: "+classList);
                //ArrayList<String> classList = set.getFinalClassList();
                System.out.println("|||before class list set in nexttttttt:<3<3 "+ classList+ "......"+set.getCurrentPanelList());
                set.setClassList(classList);
                writeType();
                System.out.println("nextbuttonhit");
                creator.writeFolderToFile(textFieldEmptied);
                creator.writeTextToFile("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() + "/class.txt", creator.getTextFieldContainer());
                set.setFinalClassList(classList); //lookie
                System.out.println("|||after right before class list set in nexttttttt:<3<3 "+ classList+ "......"+set.getCurrentPanelList());
                
                hideWindow();
                creator.hideContainer();
                System.out.println("before stat collect called: "+set.getCurrentPanelList()+" "+set.getFinalClassList());
                set.setFinalClassList(set.getCurrentPanelList());
                
                //check if there is a file under the next index
                //need command to check if there are other files in this username folder
                StudentStatCollect studentStatCollect = new StudentStatCollect();
                 if (fileHandler.fileIsNotEmpty("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() + "/" + set.getFinalClassList().get(0) + ".txt")) {
                    //load it
                    studentStatCollect.addLoadedBoxes();
                    // int numberOfComponents = loadedPanel.getComponentCount();
                    // //numOfBoxes += numberOfComponents;
                    // for (int i = 0; i < numberOfComponents; i++) {
                    //     southContainer.add(loadedPanel.getComponent(0));
                    // }
                 }

                else {
                    studentStatCollect.DisplayClasses();
                }
                //window.add(southContainer);

                //if (set.getFinalClassList().get(0))
            }
        });
        backNextButtonsPanel = creator.makeBackNextButtonsPanel(backButtonPanel, saveButtonPanel, nextButtonPanel);
        southContainer.add(backNextButtonsPanel, BorderLayout.SOUTH);

        System.out.println("3classList<3"+set.getFinalClassList());
        //set.setTextFieldPanel();
        window.add(southContainer, BorderLayout.SOUTH);
    }

    //create textbox "Class" placeholder
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

    private void writeType() {
        creator.writeFolderToFile(textFieldEmptied);
        creator.setClassList();
        creator.writeTextToFile("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() + "/class.txt", creator.getTextFieldContainer());
    }

    private void backToDefaultDeleteButton() {
        for (ActionListener listener : deleteClassButton.getActionListeners()) {
            deleteClassButton.removeActionListener(listener);
        }
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
        for (ActionListener listener : deleteClassButton.getActionListeners()) {
            deleteClassButton.removeActionListener(listener);
        }
        deleteClassButton.setText("Leave Delete Mode");
        System.out.println("in delete mode");
        // for (ActionListener listener : deleteClassButton.getActionListeners()) {
        //     deleteClassButton.removeActionListener(listener);
        // }

        deleteClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            //to fill in 
            backToDefaultDeleteButton();
            for (int i = 0; i < set.getTextFieldPanel().getComponentCount(); i++) {
                JTextField textField = (JTextField) set.getTextFieldPanel().getComponent(i);
                textField.setEditable(true);
                textField.setFocusable(true);
                // for (MouseListener listener : textField.getMouseListeners()) {
                //     textField.removeMouseListener(listener);
                // }
                MouseListener[] listeners = textField.getMouseListeners();
                if (listeners.length > 0) {
                    MouseListener lastListener = listeners[listeners.length - 1];
                    textField.removeMouseListener(lastListener);
                    System.out.println("Most recent mouse listener removed.");
                }

            
            }
            //break out of rest of function;
            return;

            }
        });
        for (int i = 0; i < set.getTextFieldPanel().getComponentCount(); i++) {
            JTextField textField = (JTextField) set.getTextFieldPanel().getComponent(i);
            textField.setEditable(false);
            textField.setFocusable(false);
            if (textField instanceof JTextField) {
                textField.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("clicked");
                        Border borderHiglighted = BorderFactory.createLineBorder(Color.decode("#FF6961"), 2);
                        textField.setForeground(Color.decode("#FF6961"));
                        selectedTextBox = textField;
                        textField.setBorder(borderHiglighted);
                        for (ActionListener listener : deleteClassButton.getActionListeners()) {
                            deleteClassButton.removeActionListener(listener);
                        }
                        deleteClassButton.setText("Delete?");
                        deleteClassButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                decorator.areYouSureMessageSetUp(deleteClassButton, selectedTextBox);
                                selectedTextBox.setForeground(Color.GRAY);
                                Border borderRegular = BorderFactory.createLineBorder(Color.GRAY, 2);
                                selectedTextBox.setBorder(borderRegular);
                            }
                        });

                    }
                });
                }
            else {
                System.out.println("There was an issue in delete mode. student classes" + textField.getClass().getName());
            }
    
        }

        // textField.addMouseListener(new MouseAdapter() { 
        //     public void mousePressed(MouseEvent me) { 
        //       System.out.println(me); 
        //     } 
        //   }); 
    }

    //create JButton "New Class"
    private void hideWindow() {
        backNextButtonsPanel.setVisible(false);
        newClassButton.setVisible(false);
        deleteClassButton.setVisible(false);
        southContainer.setVisible(false);
        creator.getTextFieldContainer().setVisible(false);

        //textField.setVisible(false);
    }

    }