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
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Rectangle;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import main.model.Set;
import main.model.SetUserInformation;
import main.model.SetState;
import main.model.SetList;

import main.controller.CreateButton;
import main.controller.Creator;
import main.controller.Decorator;
import main.controller.FileHandling;
import main.controller.FileWriting;
import main.controller.CompositeActionListenerWithPriorities;

import main.model.GoIntoPanel;

import main.view.MainWindow;
import main.view.student.StudentClasses;

public class StudentClasses extends JFrame {
    private JFrame window;

    private Creator create;
    private GoIntoPanel goIntoPanel;
    private Decorator decorate;
    private CreateButton createButton;
    private Color lightgrayColor = Color.decode("#AFA2A2");

    private JPanel backNextButtonsPanel;
    private JButton saveButton;
    private JTextField selectedTextBox;
    private Point initialClick;
    private JTextField draggedTextField = new JTextField();
    private FileWriting fileWrite = new FileWriting();
    private CompositeActionListenerWithPriorities actionPriorities;

    Border borderRegular = BorderFactory.createLineBorder(Color.GRAY, 2);
    JPanel southContainer = new JPanel(new GridLayout(2,1,0,0));
    AtomicBoolean textFieldEmptied = new AtomicBoolean(false);;
    JButton newClassButton;
    JButton deleteClassButton;
    FileHandling fileHandler = new FileHandling();
    JLayeredPane layeredPane = new JLayeredPane();
    private String currentClass = "StudentClasses Loading";
    
    Set set;
    SetState setState;
    private SetUserInformation setUserInformation;
    private SetList setList;

    JPanel instructionsPanel;

    private static Boolean isDragging = false;

    public StudentClasses() {
    }

    public void studentClassesLaunch() {
        southContainer.setName("SouthContainer");
        //southContainer.setBackground(Color.pink);
        southContainer.setForeground(Color.pink);
        this.set = Set.getInstance();
        this.setState = SetState.getInstance();
        this.setUserInformation = SetUserInformation.getInstance();
        this.setList = SetList.getInstance();
        this.actionPriorities = CompositeActionListenerWithPriorities.getInstance();
        actionPriorities.setCurrentClass(currentClass);

        setState.setCurrentClass("StudentClasses.java");
        window = set.getWindow();

        ///
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (set.getCurrentSaveButton().isEnabled() && saveButton != null) {
                    decorate.areYouSureMessage(null, "closing window", "<html><center> You did not save <br> Close anyways?", 176, 107);
                }
            }
        });
        ///
        //window.setBackground(Color.pink);
        window.setName("window");
        create = new Creator();
        decorate = new Decorator();
        createButton = new CreateButton();
        System.out.println("in student classes");

        EnterAction enterAction = new EnterAction();
        window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        window.getRootPane().getActionMap().put("enterAction", enterAction);
        
        window.setTitle("StudentClasses");

        layeredPane.setBackground(Color.pink);
            layeredPane.setVisible(true);
            layeredPane.setOpaque(false);
            layeredPane.setDoubleBuffered(true);
            window.add(layeredPane);
            create.windowFix();
        
        instructionsWordsAndPanel("Edit Classes Mode "+"(for " +setUserInformation.getStudentOrTeacher()+"s)");
        loadIfNeeded();
        westPanelCreate();
        buttonSetUpAction();
        currentClass = "StudentClasses";
        actionPriorities.setCurrentClass(currentClass);
    }

    private void loadIfNeeded() {
        String filePath = setUserInformation.getPathToClassTextFile();
        if (fileHandler.fileExists(filePath)) {//case for if existing file
            System.out.println("I have info to load!");
            ArrayList<String> myList = fileHandler.readFileToList(filePath);
            for (int index=0; index<myList.size(); index++) {
                create.createTextBox(myList.get(index), "JTextField", true);
            }
            setList.setClassList(myList);
            setList.setFinalClassList(setList.getCurrentPanelList());
            
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
        // backButton.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         hideWindow(); 
        //         set.setWindow(window);
        //         MainWindow main = new MainWindow();
        //         main.MainWindowLaunch();
        //         main.setButtonSelected();
        // }});

        //:
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPriorities.setCurrentClass(currentClass);
                actionPriorities.addClassActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {// remember wont run  if just enter without a click
                        hideWindow(); 
                        set.setWindow(window);
                        MainWindow main = new MainWindow();
                        main.MainWindowLaunch();
                        main.setButtonSelected();
                    }
            }, 1, "backButton", null, currentClass);  // Add this ActionListener with priority 1
        }});
        //:
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
        return nextButtonPanel;
    }
    private void doNextButtonProcedure() {
        ArrayList<String> classList;
        classList = fileWrite.getClassList();//set.getTextFieldPanel();//set.getCurrentPanelList();
        setList.setClassList(classList);
        writeType();
        System.out.println("nextbuttonhit");
        fileWrite.writeTextToFile();
        setList.setFinalClassList(classList);
        hideWindow();
        create.hideContainer();

        setList.setFinalClassList(setList.getCurrentPanelList());
        System.out.println("thestuffclasslist "+classList+" "+setList.getCurrentPanelList());
        StudentStatCollect studentStatCollect = new StudentStatCollect();
        if (fileHandler.fileExists("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/" + setUserInformation.getUsername() + "/" +"ClassInformation"+"/"+setList.getFinalClassList().get(0) + ".txt")) { //needs to keep path because its with index 0
            create.hideContainer();
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
                create.createTextBox("Enter Class Name", "JTextField",false);
                System.out.println("class list in new class button"+ setList.getCurrentPanelList());
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
        //set.setFinalClassList(set.getClassList());
        System.out.println("testing? "+setList.getCurrentPanelList());
        setList.setFinalClassList(setList.getCurrentPanelList());
        System.out.println("set.getcurrentpanellist "+setList.getCurrentPanelList());
        set.setFilePath(setUserInformation.getPathToClassTextFile());
        fileWrite.writeTextToFile();
        fileWrite.debugPrintPanel();
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
        instructionsPanel = decorate.InstructionsPanelDecorate(instructionsPanel, instructionsWords);
        create.windowFix();
    }

    private void prepareTextboxForDeleteMode() {

    JTextField[] tempTextField = new JTextField[1];
    //JLabel[] tempTextField = new JLabel[1];
    tempTextField[0] = new JTextField("ERROR"); //if not overwritten will show error
    //tempTextField[0] = new JLabel("ERROR");
    BufferedImage[] textFieldImage = new BufferedImage[1];
    textFieldImage[0] = null;
    Component lastComponent = setState.getTextFieldPanel().getComponent(setState.getTextFieldPanel().getComponentCount()-1);
    //Bounds lastComponentBounds = lastComponent.getBounds();
    Rectangle lastComponentBounds = lastComponent.getBounds();

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
            //Component currentComponent = setState.getTextFieldPanel().getComponent(i);

            //window.setLayout(null);
            textField.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {

                    //
                    Point cursorPoint = e.getPoint();
                    Point leftPoint = new Point(cursorPoint.x - 40, cursorPoint.y);
                    Point rightPoint = new Point(cursorPoint.x - 40, cursorPoint.y);
                    //JTextField leftTextField = (JTextField) ;//(JTextField) e.getSource();
                    
                    if ((layeredPane.getComponentAt(leftPoint) instanceof JTextField && layeredPane.getComponentAt(rightPoint) instanceof JTextField) || (window.getComponentAt(leftPoint) instanceof JTextField && window.getComponentAt(rightPoint) instanceof JTextField)) {
                        Component comp = layeredPane.getComponentAt(leftPoint);
                        System.out.println("comp "+comp.getClass().getName());
                        for (int i = 0; i < setState.getTextFieldPanel().getComponentCount(); i++) {
                            //JTextField textfieldComponent = 
                            //if (setState.getTextFieldPanel().getComponent(i).getText().equals(window.getComponentAt(leftPoint).getText())) {
                                JPanel textFieldPanel = setState.getTextFieldPanel();
                                //textFieldPanel.getComponent(i+1) = create.createTextBox();
                                textFieldPanel.add(create.createTextBox("", "JTextField", false));
                                create.windowFix();

                            //}
                        }
                    }

                    else if (window.getComponentAt(leftPoint) instanceof JTextField && window.getComponentAt(rightPoint) instanceof JTextField) {
                        Component comp = window.getComponentAt(leftPoint);
                        System.out.println("comp2 "+comp.getClass().getName());
                    }


                    // if () {

                    // }
                    //Component 
                    // if (window.getComponentAt(leftPoint) instanceof JTextField) {
                    //     JTextField componentToLeft = (JTextField) SwingUtilities.getDeepestComponentAt(window, leftPoint.x, leftPoint.y);
                    //     System.out.println("about to text: "+componentToLeft.getText());
                    // }

                    // else if (window.getComponentAt(leftPoint) instanceof JFrame){
                    //     JFrame jframe = (JFrame) window.getComponentAt(leftPoint);
                    //     Component compon = jframe.getContentPane(;

                    //     System.out.println("is jframe instance?"+ (compon instanceof JTextField));
                    //     System.out.println("is a instance of jframe "+ window.getComponentAt(leftPoint).getClass().getName()); //SwingUtilities.getDeepestComponentAt(window, leftPoint.x, leftPoint.y));//
                        
                    // }

                    // else if (window.getComponentAt(leftPoint) != null){
                    //     //System.out.println("is a instance of"+ window.getComponentAt(leftPoint).getClass().getName()); //SwingUtilities.getDeepestComponentAt(window, leftPoint.x, leftPoint.y));//
                    //     //JTextField()
                    // }
                    
                    // else {
                    //     System.out.println("failing atm");
                    // }
                    //
                    //if ( lastComponent == currentComponent) {

                        
                    //}
                    JTextField newBackgroundField = new JTextField("EMPTY");
                    
                    if (draggedTextField != null) { 
                        draggedTextField = (JTextField) e.getSource();
                        //newBackgroundField = (JTextField) e.getSource();
                    }

                    if (tempTextField[0].getText().equals("ERROR")){
                    tempTextField[0] = create.createTextBox("Hello. Ur cute", "JTextField", false);
                    //tempTextField[0] = new JLabel("Hello <3");
                    tempTextField[0].setOpaque(true);
                    }

                    if ((draggedTextField.getX() > tempTextField[0].getX()) && (draggedTextField.getY() > tempTextField[0].getY())) {
                        System.out.println("made it :) ");
                    }

                   // tempTextField[0].repaint();
                    tempTextField[0].setBounds(e.getX(), e.getY(), 144, 30);
                    //tempTextField[0].setForeground(new Color(230, 230, 230));

                    //tempTextField[0].repaint();

                    // Rectangle bounds = new Rectangle(e.getX() - draggedTextField.getWidth() / 2,
                    // e.getY() - draggedTextField.getHeight() / 2,
                    // draggedTextField.getWidth(), 
                    // draggedTextField.getHeight());

                    //draggedTextField.repaint(bounds);
                    //tempTextField[0].repaint();

                    //System.out.println("temptextfield "+ tempTextField[0].getText());
                    //draggedTextField.setVisible(false);
                    
                    if (!layeredPane.isAncestorOf(tempTextField[0])) {
                        newBackgroundField = create.createTextBox(draggedTextField.getText(), "JTextField", false);//"BACK");
                        newBackgroundField.setForeground(Color.lightGray);
                        newBackgroundField.setBounds(draggedTextField.getX(), draggedTextField.getY(), draggedTextField.getWidth(), draggedTextField.getHeight());

                        //newBackgroundField.setFocu

                        draggedTextField.setBounds(0, 0,layeredPane.getWidth(), layeredPane.getHeight());
                        draggedTextField.setVisible(false);

                        newBackgroundField.setOpaque(true);
                        newBackgroundField.setVisible(true);

                        layeredPane.add(tempTextField[0], Integer.valueOf(JLayeredPane.DRAG_LAYER));
                        newBackgroundField.setEditable(false);
                        layeredPane.add(newBackgroundField, Integer.valueOf(JLayeredPane.FRAME_CONTENT_LAYER));
                        layeredPane.setVisible(true);
                        tempTextField[0].setOpaque(true);
                        tempTextField[0].setVisible(true);
                    }
                    //newBackgroundField.setEditable(false);
                    //tempTextField[0].repaint();


                    
                    //layeredPane.add(waterfalls);
                    // if (textFieldImage[0] == null) {
                    //     textFieldImage[0] = new BufferedImage(draggedTextField.getWidth(), draggedTextField.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    //     Graphics2D g2d = textFieldImage[0].createGraphics();
                    //     tempTextField[0].paint(g2d); // Render the JTextField onto the BufferedImage
                    //     g2d.dispose();
                    // }
    
                    // int x = e.getXOnScreen() - textFieldImage[0].getWidth() / 2;
                    // int y = e.getYOnScreen() - textFieldImage[0].getHeight() / 2;

                    // // Create a new BufferedImage to draw the image onto
                    // BufferedImage tempImage = new BufferedImage(layeredPane.getWidth(), layeredPane.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    // Graphics2D g = tempImage.createGraphics();
                    // //g.drawImage(textFieldImage[0], x, y, null);
                    // g.dispose();

                    // // Paint the BufferedImage onto the layeredPane
                    // Graphics gPane = layeredPane.getGraphics();
                    // gPane.drawImage(tempImage, 0, 0, null);
                    // gPane.dispose();

                    // if (!layeredPane.isAncestorOf(tempTextField[0])) {
                    //     layeredPane.add(tempTextField[0], Integer.valueOf(JLayeredPane.DRAG_LAYER));
                    // }
                    // layeredPane.setVisible(true);
                    // tempTextField[0].setOpaque(true);
                    // tempTextField[0].setVisible(true);

                }
            });
            
            textField.addMouseListener(new MouseAdapter() {
            //tempTextField[0].addMouseListener( new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        layeredPane.remove(tempTextField[0]);
                    }
            });
    }
    }

//     private void addDraggingMouseReleasedListener(JTextField textField, JTextField[] tempTextField) {
// }

//     private void draggingListeners() {
//         //
//     }

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
        //String filePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+setUserInformation.getUsername()+"/"+"ClassInformation"+"/"+selectedTextBox.getText()+".txt";
        String filePath = setUserInformation.getPathToClassInformationFileWithTextField(selectedTextBox);
        removeDeleteClassButtonActionListeners();//deleteClassButton.getActionListeners().length);
        deleteClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newClassButton.setEnabled(true);
                //if the file has loaded information attached
                if (setState.getLoadedState(selectedTextBox) && (fileHandler.fileExists(filePath)) && fileHandler.fileIsNotEmpty(filePath)) {
                
                    yesOrNoDialog[0] = decorate.areYouSureMessage(selectedTextBox, "deleting", "<html><center>Deleting this class will optiondelete <br>its loaded information.<br>Do you wish to continue?", 250, 120);

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


                pickAppropriateInstructionWordsAndPanels();

                leaveDeleteModeButton();
            }
            });
    }

    private void pickAppropriateInstructionWordsAndPanels() {
        window.remove(instructionsPanel);
        if (setState.getTextFieldPanel().getComponentCount() > 0) {
            instructionsWordsAndPanel("Box deleted");
            System.out.println("boxdeleted");
            
        }

        else if (setState.getTextFieldPanel().getComponentCount() == 0) {
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

    public class EnterAction extends AbstractAction {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        //     System.out.println("2.5");
        //     doNextButtonProcedure();
        // }

        @Override
        public void actionPerformed(ActionEvent e) {
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