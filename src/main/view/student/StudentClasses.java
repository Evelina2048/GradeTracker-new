package main.view.student;

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
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import main.model.Set;
import main.controller.CreateButton;
import main.controller.Creator;
import main.controller.Decorator;
import main.controller.FileHandler;
import main.controller.FileWriting;
import main.view.MainWindow;
import main.view.student.StudentClasses;

import java.awt.event.KeyEvent;

import java.awt.Point;
import java.awt.event.MouseMotionAdapter;


public class StudentClasses extends JFrame {
    private JFrame window;
    private Creator creator;
    private Decorator decorator;
    private CreateButton createButton;
    private JPanel backNextButtonsPanel;
    private JButton saveButton;
    private JTextField selectedTextBox;
    private Point initialClick;
    private JTextField draggedTextField = new JTextField();
    private FileWriting fileWrite = new FileWriting();
    Border borderRegular = BorderFactory.createLineBorder(Color.GRAY, 2);
    JPanel southContainer = new JPanel(new GridLayout(2,1,0,0));
    AtomicBoolean textFieldEmptied = new AtomicBoolean(false);;
    JButton newClassButton;
    JButton deleteClassButton;
    FileHandler fileHandler = new FileHandler();
    JLayeredPane layeredPane = new JLayeredPane();
    
    Set set;
    JPanel instructionsPanel;

    private static Boolean isDragging = false;

    public StudentClasses() {
    }

    public void studentClassesLaunch() {
        southContainer.setName("SouthContainer");
        this.set = Set.getInstance();
        set.setCurrentClass("StudentClasses.java");
        window = set.getWindow();
        window.setName("window");
        creator = new Creator();
        decorator = new Decorator();
        createButton = new CreateButton();
        System.out.println("in student classes");

        EnterAction enterAction = new EnterAction();
        window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        window.getRootPane().getActionMap().put("enterAction", enterAction);
        
        window.setTitle("StudentClasses");
        //window.setLayout(null);
        //window.setLayout(new BorderLayout());

// works below
        // JTextField waterfalls = new JTextField("Hi");
        // waterfalls.setOpaque(true);
        // waterfalls.setBounds(50, 50, 100, 30);
        // layeredPane.add(waterfalls);
        // layeredPane.setBackground(Color.pink);
        // layeredPane.setVisible(true);
        // layeredPane.setBounds(100,100,100,100);
        // layeredPane.setOpaque(true);
        // window.add(layeredPane);
//
        
        instructionsWordsAndPanel("Edit Classes Mode "+"(for " +set.getStudentOrTeacher()+"s)");
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
                creator.createTextBox(myList.get(index), "JTextField", true);
            }
            set.setClassList(myList);
            set.setFinalClassList(set.getCurrentPanelList());
            
        }

        else {
           creator.createTextBox("Enter Class Name", "JTextField", false);
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
                hideWindow(); 
                set.setWindow(window);
                MainWindow main = new MainWindow();
                main.MainWindowLaunch();
                main.setButtonSelected();
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
        JButton nextButton = createButton.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        nextButton.setPreferredSize(new Dimension(87, 29));
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doNextButtonProcedure();
            }
        });
        return nextButtonPanel;
    }
    private void doNextButtonProcedure() {
        ArrayList<String> classList;
        classList = set.getCurrentPanelList();
        set.setClassList(classList);
        writeType();
        System.out.println("nextbuttonhit");
        fileWrite.writeTextToFile();
        set.setFinalClassList(classList);
        hideWindow();
        creator.hideContainer();

        set.setFinalClassList(set.getCurrentPanelList());
        StudentStatCollect studentStatCollect = new StudentStatCollect();
        if (fileHandler.fileExists("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() + "/" + set.getFinalClassList().get(0) + ".txt")) {
            creator.hideContainer();
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
                creator.createTextBox("Enter Class Name", "JTextField",false);
                System.out.println("class list in new class button"+ set.getCurrentPanelList());
        }
    });
    }

    private void deleteClassButton() {
        deleteClassButton = new JButton("Delete Class Mode");
        deleteClassButton.setPreferredSize(new Dimension(150, 50));
        deleteClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("deleteClass Mode hit");
                deleteMode();
            }
        });
    }

    private void writeType() {
        System.out.println(1111);
        creator.setClassList();
        set.setFilePath("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + set.getUsername() + "/class.txt");
        System.out.println("5555 filepath "+set.getFilePath());
       //creator.writeTextToFile();
       fileWrite.writeTextToFileWithAppend(set.getTextFieldPanel());
    }

    private void backToDefaultDeleteButton() {
        removeDeleteClassButtonActionListeners();
        deleteClassButton.setText("Delete Class Mode");
        deleteClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked delete class mode");
                requestFocusInWindow();
                deleteMode();
                saveButton.setEnabled(true);
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
        instructionsPanel = decorator.InstructionsPanelDecorate(instructionsPanel, instructionsWords);
        creator.windowFix();
    }

    private void prepareTextboxForDeleteMode() {
    //     JTextField[] tempTextField = new JTextField[1];
    //     for (int i = 0; i < set.getTextFieldPanel().getComponentCount(); i++) { 
    //         //tempTextField = new JTextField[1];
    //         JTextField textField = new JTextField();
    //         if (set.getTextFieldPanel().getComponent(i) instanceof JTextField) {
    //             textField = (JTextField) set.getTextFieldPanel().getComponent(i);
    //             addMouseListenerToTextboxAndFrame(textField);
    //         }
    //         else if (set.getTextFieldPanel().getComponent(i) instanceof JPanel) {
    //             textField = creator.goIntoPanelReturnTextbox((JPanel) set.getTextFieldPanel().getComponent(i), 0);
                
    //             addMouseListenerToTextboxAndFrame(textField);
    //         }
            
    //         else {
    //             System.out.println("There was an issue in delete mode. student classes" + set.getTextFieldPanel().getComponent(i).getClass().getName());
    //         }

    //         ////
    //         //final JTextField finalTextField = textField;
    //         //draggingListeners();//
    //         //finalTextField.addMouseMotionListener(
                
    //         //});
            
    //         //finalTextField.addMouseListener(
                
    //         // addDraggingMouseReleasedListener(textField, tempTextField);
    //         // System.out.println(1111);

    //         ///

    //         textField.addMouseMotionListener(new MouseMotionAdapter() {
    //             public void mouseDragged(MouseEvent e) {
    //                 initialClick = e.getPoint();
    //                 draggedTextField = (JTextField) e.getSource();
                    
    //                 tempTextField[0] = new JTextField(draggedTextField.getText());
    //                 tempTextField[0].setBounds(draggedTextField.getBounds());
    //                 tempTextField[0].setBackground(new Color(200, 200, 200, 150)); // Light color with transparency
    //                 tempTextField[0].setEditable(false);
    //                 tempTextField[0].setFocusable(false);
                    
    //                 layeredPane.add(tempTextField[0], Integer.valueOf(JLayeredPane.DRAG_LAYER));
    //                 layeredPane.repaint();
    //                 if (tempTextField[0] != null) {
    //                     int thisX = tempTextField[0].getLocation().x;
    //                     int thisY = tempTextField[0].getLocation().y;
                        
    //                     int xMoved = e.getX() - initialClick.x;
    //                     int yMoved = e.getY() - initialClick.y;
                        
    //                     int X = thisX + xMoved;
    //                     int Y = thisY + yMoved;
                        
    //                     tempTextField[0].setLocation(X, Y);
    //                 }
    //             }
    //         });
            
    //         //textField.addMouseListener(new MouseAdapter() {
    //         tempTextField[0].addMouseListener(new MouseAdapter() {
    //             public void mouseReleased(MouseEvent e) {
    //                     layeredPane.remove(tempTextField[0]);
    //                     tempTextField[0] = null;
    //                     layeredPane.repaint();
    //             }
    //         });
    // }
    //JTextField tempTextField = new JTextField();
    JTextField[] tempTextField = new JTextField[1];
    tempTextField[0] = new JTextField("ERROR"); //if not overwritten will show error
        for (int i = 0; i < set.getTextFieldPanel().getComponentCount(); i++) { 
            //tempTextField = new JTextField[1];
            JTextField textField = new JTextField();
            if (set.getTextFieldPanel().getComponent(i) instanceof JTextField) {
                textField = (JTextField) set.getTextFieldPanel().getComponent(i);
                addMouseListenerToTextboxAndFrame(textField);
            }
            else if (set.getTextFieldPanel().getComponent(i) instanceof JPanel) {
                textField = creator.goIntoPanelReturnTextbox((JPanel) set.getTextFieldPanel().getComponent(i), 0);
                
                addMouseListenerToTextboxAndFrame(textField);
            }
            
            else {
                System.out.println("There was an issue in delete mode. student classes" + set.getTextFieldPanel().getComponent(i).getClass().getName());
            }

            ////
            //final JTextField finalTextField = textField;
            //draggingListeners();//
            //finalTextField.addMouseMotionListener(
                
            //});
            
            //finalTextField.addMouseListener(
                
            // addDraggingMouseReleasedListener(textField, tempTextField);

            ///
            // MouseAdapter mouseReleasedForDragging = new MouseAdapter() {
            //     public void mouseReleased(MouseEvent e) {
            //     }
            // };

            textField.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    //
                    draggedTextField = (JTextField) e.getSource();
                    //System.out.println("draggedtextfield: "+ draggedTextField.getText());
                    
                    tempTextField[0] = new JTextField(draggedTextField.getText());
                    //tempTextField[0].setBounds(draggedTextField.getBounds());
                    tempTextField[0].setBounds(0,0,100,100);
                    tempTextField[0].setSize(draggedTextField.getWidth(), draggedTextField.getHeight());
                    tempTextField[0].setLocation(0, 0);
                    tempTextField[0].setForeground(Color.pink);
                    //tempTextField[0].setBackground(new Color(200, 200, 200, 150)); // Light color with transparency
                    tempTextField[0].setEditable(false);
                    tempTextField[0].setFocusable(false);

                    System.out.println("temptextfield "+ tempTextField[0].getText());
                    //tempTextField[0].mouseDragged() = true;
                    //tempTextField[0].copy();
                    //tempTextField[0].addMouseMotionListener(mouseReleasedForDragging);
                    //textField.addMouseListener(mouseReleasedForDragging);
                    layeredPane.add(tempTextField[0]);
                    layeredPane.setVisible(true);
                    tempTextField[0].setOpaque(true);
                    tempTextField[0].setVisible(true);
                    window.add(layeredPane);

                
                //     tempTextField[0].setBounds(draggedTextField.getBounds());
                //     tempTextField[0].setBackground(new Color(200, 200, 200, 150)); // Light color with transparency
                //     tempTextField[0].setEditable(false);
                //     tempTextField[0].setFocusable(false);
                    
                //     layeredPane.add(tempTextField[0], Integer.valueOf(JLayeredPane.DRAG_LAYER));
                //     layeredPane.repaint();
                //     if (tempTextField[0] != null) {
                //         int thisX = tempTextField[0].getLocation().x;
                //         int thisY = tempTextField[0].getLocation().y;
                        
                //         int xMoved = e.getX() - initialClick.x;
                //         int yMoved = e.getY() - initialClick.y;
                        
                //         int X = thisX + xMoved;
                //         int Y = thisY + yMoved;
                        
                //     tempTextField[0].setLocation(X, Y);
                    
                    
                }
            });
            
            textField.addMouseListener(new MouseAdapter() {
            //tempTextField[0].addMouseListener( new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        System.out.println("mouselistener"+3333);
                    }
            });
    }
            

            ////
    }
    

    private void addDraggingMouseReleasedListener(JTextField textField, JTextField[] tempTextField) {
        // MouseAdapter draggingAdapter = new MouseAdapter() {
        //     public void mouseDragged(MouseEvent e) {
        //         initialClick = e.getPoint();
        //         draggedTextField = (JTextField) e.getSource();
                
        //         tempTextField[0] = new JTextField(draggedTextField.getText());
        //         tempTextField[0].setBounds(draggedTextField.getBounds());
        //         tempTextField[0].setBackground(new Color(200, 200, 200, 150)); // Light color with transparency
        //         tempTextField[0].setEditable(false);
        //         tempTextField[0].setFocusable(false);
                
        //         layeredPane.add(tempTextField[0], Integer.valueOf(JLayeredPane.DRAG_LAYER));
        //         layeredPane.repaint();
        //         if (tempTextField != null) {
        //             int thisX = tempTextField[0].getLocation().x;
        //             int thisY = tempTextField[0].getLocation().y;
                    
        //             int xMoved = e.getX() - initialClick.x;
        //             int yMoved = e.getY() - initialClick.y;
                    
        //             int X = thisX + xMoved;
        //             int Y = thisY + yMoved;
                    
        //             tempTextField[0].setLocation(X, Y);
        //         }
        //     }
        // };
        // textField.addMouseMotionListener(draggingAdapter);
        // MouseAdapter draggingMouseReleased = new MouseAdapter() {
        // public void mouseReleased(MouseEvent e) {
        //     //JTextField mouseTextField = textField;
        //     System.out.println(3333);
        //     System.out.println("temp ("+ tempTextField[0].getX()+" , "+ tempTextField[0].getY() + " ) textfield (" + draggedTextField.getX()+ " , "+ draggedTextField.getY()+")");
        //     if (tempTextField[0] != null) {
        //         layeredPane.remove(tempTextField[0]);
        //         tempTextField[0] = null;
        //         layeredPane.repaint();
        //     }

        //     if (tempTextField[0] != null && (tempTextField[0].getX() < draggedTextField.getX()) && (tempTextField[0].getY() < draggedTextField.getY())) {
        //         System.out.println("temp is lower and to the right of the textfield");
        //     }
        // }
        // };
        // draggedTextField.addMouseMotionListener(draggingMouseReleased);

        ///
        

        // textField.addMouseMotionListener(new MouseMotionAdapter() {
        //     public void mouseDragged(MouseEvent e) {
        //         initialClick = e.getPoint();
        //         draggedTextField = (JTextField) e.getSource();
                
        //         tempTextField[0] = new JTextField(draggedTextField.getText());
        //         tempTextField[0].setBounds(draggedTextField.getBounds());
        //         tempTextField[0].setBackground(new Color(200, 200, 200, 150)); // Light color with transparency
        //         tempTextField[0].setEditable(false);
        //         tempTextField[0].setFocusable(false);
                
        //         layeredPane.add(tempTextField[0], Integer.valueOf(JLayeredPane.DRAG_LAYER));
        //         layeredPane.repaint();
        //         if (tempTextField[0] != null) {
        //             int thisX = tempTextField[0].getLocation().x;
        //             int thisY = tempTextField[0].getLocation().y;
                    
        //             int xMoved = e.getX() - initialClick.x;
        //             int yMoved = e.getY() - initialClick.y;
                    
        //             int X = thisX + xMoved;
        //             int Y = thisY + yMoved;
                    
        //             tempTextField[0].setLocation(X, Y);
        //         }
        //     }
        // });
        
        // textField.addMouseListener(new MouseAdapter() {
        //     public void mouseReleased(MouseEvent e) {
        //         if (tempTextField[0] != null) {
        //             layeredPane.remove(tempTextField[0]);
        //             tempTextField[0] = null;
        //             layeredPane.repaint();
        //             System.out.println(3333);
        //         }
        //     }
        // });
}

    private void draggingListeners() {
        //
    }

    private void addMouseListenerToTextboxAndFrame(JTextField textField) {
        turnOffEditability(textField);
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("mouselistener #2");
                System.out.println("clicked");
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
        String filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+set.getUsername()+"/"+selectedTextBox.getText()+".txt";
        removeDeleteClassButtonActionListeners();//deleteClassButton.getActionListeners().length);
        deleteClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newClassButton.setEnabled(true);
                //if the file has loaded information attached
                if (set.getLoadedState(selectedTextBox) && (fileHandler.fileExists(filePath)) && fileHandler.fileIsNotEmpty(filePath)) {
                
                    yesOrNoDialog[0] = decorator.areYouSureMessage(selectedTextBox, "deleting", "<html><center>Deleting this class will optiondelete <br>its loaded information.<br>Do you wish to continue?");

                    selectedTextBox.setForeground(Color.GRAY);
                    selectedTextBox.setBorder(borderRegular);
                }

                else {
                    JPanel selectedBoxPanel = new JPanel();
                    selectedBoxPanel.add(selectedTextBox); //this also makes the selected textbox invisible
                    creator.deleteTextBox(selectedBoxPanel);
                    saveButton.setEnabled(true);
                }

                if (yesOrNoDialog[0].equals("yes") || yesOrNoDialog[0].equals("nothing yet")) {
                    saveButton.setEnabled(true);
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

    private void removeDeleteClassButtonActionListeners() {//int amountToDelete) {
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
                for (int i = 0; i < set.getTextFieldPanel().getComponentCount(); i++) {

                    JTextField textField = new JTextField();
                    if (set.getTextFieldPanel().getComponent(i) instanceof JTextField) {
                        textField = (JTextField) set.getTextFieldPanel().getComponent(i);
                    }

                    else {
                        textField = creator.goIntoPanelReturnTextbox((JPanel) set.getTextFieldPanel().getComponent(i), 0);
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
        creator.getTextFieldContainer().setVisible(false);
        instructionsPanel.setVisible(false);
        creator.hideContainer();
        
        for (MouseListener listener : deleteClassButton.getMouseListeners()) {
            window.removeMouseListener(listener);
        }

    }

    public class EnterAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            doNextButtonProcedure();
        }
    }

    }