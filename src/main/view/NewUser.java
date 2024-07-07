package main.view;

import javax.swing.Timer;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//key listening
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;

import main.model.Set;
import main.controller.Creator;
import main.controller.Decorator;
import main.controller.CompositeActionListenerWithPriorities;

import javax.swing.SwingUtilities;

public class NewUser extends JFrame {
    private JFrame window;
    private JFrame main;
    private String studentOrTeacher;
    private String existingOrNew;
    private boolean moveOnPossible = false;
    private Gather gatherFrame;
    private Set set;
    long clickTimeMillis;
    CompositeActionListenerWithPriorities actionPriorities = new CompositeActionListenerWithPriorities();

    Boolean newUserActionStarted = false;
    Boolean newUserActionCompleted = false;
    

    JRadioButton newUserButton;
    JRadioButton existingButton;
    ButtonGroup teacherStudentGroup;
    int windowWidth = 750;
    int windowHeight = 500;

    //panel
    JPanel instructionsPanel;
    JPanel choicesPanel;
    JPanel backNextButtonsPanel;

    Decorator decorator = new Decorator();
    private String originalExistingOrNew;

    public NewUser() {
        this.set = Set.getInstance();
        window = set.getWindow();
        //window.setLocationRelativeTo(null);
        newUserSetup();
    }

    public void newUserSetup() {
        window = set.getWindow();

        CompositeActionListenerWithPriorities actionPriorities = new CompositeActionListenerWithPriorities();

        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setSize(windowWidth, windowHeight);
        
        studentOrTeacher = set.getStudentOrTeacher();
        window.setTitle("New User");

        if (set.getUsername() != null) {
            originalExistingOrNew = set.getExistingOrNew();
        }

        instructionsWordsWindow();

        radioButtonSetUp();

        buttonSetUp();        

        SwingUtilities.invokeLater(() -> {
            window.setVisible(true);
        });
    
    }
    
    private void instructionsWordsWindow() {
        //instructions (north section for borderlayout)
        JLabel instructionsWords = new JLabel("You are a "+studentOrTeacher+". Are you a new user?");
        instructionsPanel = decorator.InstructionsPanelDecorate(instructionsPanel, instructionsWords);
    }

    private void radioButtonSetUp() {
        teacherStudentGroup = new ButtonGroup();
        Color choicesPanelColor = Color.decode("#AFA2A2");

        choicesPanel = new JPanel(new GridBagLayout());
        choicesPanel.setBackground(choicesPanelColor);

        existingButton();

        newUserButton();

        addToChoicesPanel(teacherStudentGroup, existingButton, newUserButton, choicesPanel);

        window.add(choicesPanel);
    }

    private void existingButton() {
        //initialize buttons with color
        existingButton = new JRadioButton("Existing");
        choicesButtonDecorate(existingButton);
        existingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            existingOrNew = existingButton.getText();
            moveOnPossible = true;
            checkIfExistingChangedWithUsername();
            }
            
        });
    }
    
    private void newUserButton() {
        newUserButton = new JRadioButton("New User");
        choicesButtonDecorate(newUserButton);
        //newUserButton.addActionListener(new ActionListener() {
            // public void actionPerformed(ActionEvent e) {
            //     actionPriorities.addClassActionListener(e, 1);
            //     newUserActionStarted = true;
            //     // try {
            //     //     SwingUtilities.invokeAndWait(new Runnable() {
            //     //         @Override
            //     //         public void run() {
            //                 //decorator.errorMessageSetUp(newUserButton);              
            //         clickTimeMillis = System.currentTimeMillis();
            //         moveOnPossible = true;
            //         existingOrNew = newUserButton.getText();
            //         checkIfExistingChangedWithUsername();
            //         newUserActionCompleted = true;

            //     //         } 
            //     //     });
            //     // } catch (InvocationTargetException e1) {
            //     //     //Auto-generated catch block
            //     //     e1.printStackTrace();
            //     // } catch (InterruptedException e1) {
            //     //     //Auto-generated catch block
            //     //     e1.printStackTrace();
            //     // }
            // }
            // });

            newUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            actionPriorities.addClassActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent b) {
                    System.out.println(5555);
                    // Perform actions for this listener with priority 1
                    clickTimeMillis = System.currentTimeMillis();
                    moveOnPossible = true;
                    existingOrNew = newUserButton.getText();
                    checkIfExistingChangedWithUsername();
                    newUserActionCompleted = true;


                    //actionPriorities.actionPerformed(b);
                }
            }, 1);  // Add this ActionListener with priority 1

            }
            });

            EnterAction enterAction = new EnterAction();

            window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
            window.getRootPane().getActionMap().put("enterAction", enterAction);
    
            window.requestFocusInWindow();
        }
    
    private void checkIfExistingChangedWithUsername() {
        if (set.getUsername() != null && existingOrNew.equals(originalExistingOrNew)) { //user has already been in gather frame and theyre the same
            set.setExistingOrNewChanged(false);
        }

        else if((set.getUsername() != null && !existingOrNew.equals(originalExistingOrNew))) { //user has already been in gather frame and theyre different
            set.setExistingOrNewChanged(true);
        }
    }

    private void choicesButtonDecorate(JRadioButton button) {
        Font buttonFont = new Font("Roboto", Font.PLAIN, 25); // Change the font and size here
        button.setForeground(Color.WHITE);
        button.setFont(buttonFont);
    }

    private void addToChoicesPanel(ButtonGroup teacherStudentGroup, JRadioButton existingButton, JRadioButton newUserButton, JPanel choicesPanel) {
        teacherStudentGroup.add(existingButton);
        teacherStudentGroup.add(newUserButton);
        choicesPanel.add(existingButton);
        choicesPanel.add(newUserButton);
        choicesPanel.add(existingButton, decorator.choiceGbc());
    }

    private void buttonSetUp() {
        backNextButtonsPanel = new JPanel(new BorderLayout());

        Creator creator = new Creator();
        JButton backButton = creator.backButtonCreate();
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);

        JButton nextButton = creator.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doNextButtonProcedure();
            }
        });
        nextButtonPanel.add(nextButton);
        
        backNextButtonsPanel = creator.makeBackNextButtonsPanel(backButtonPanel, new JPanel(), nextButtonPanel);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);

            
            backButton.setEnabled(false);
    }

    private void doNextButtonProcedure(){
        System.out.println("in the do next button procedure in new user");
        System.out.println(7777);
            // set.setWindow(window);
            //int windowX = window.getX();
            //int windowY = window.getY();

        //SwingUtilities.invokeLater(new Runnable() {
                //@Override
                //public void run() {
                    if (newUserButton.isSelected() || existingButton.isSelected()){//moveOnPossible) {
                        System.out.println(8888);
                        System.out.println("move on possible");
                        set.setWindow(window);
                        decorator.hideWindow(instructionsPanel, choicesPanel, backNextButtonsPanel);
        
                        set.setExistingOrNew(existingOrNew);
        
                        if (gatherFrame == null) {
                            // Create a new instance of Gather if it doesn't exist
                            gatherFrame = new Gather();
                            gatherFrame.gatherLaunch();
                            System.out.println("gatherframe is null");
                        } 
                        else {
                            // Update the existing Gather window panels
                            gatherFrame.gatherLaunch();
                            // gatherFrame.showWindow(windowX, windowY);  // Show the Gather window
                        }    
                    }
                    else if ((!newUserButton.isSelected() && !existingButton.isSelected())) {
                        System.out.println("move on not possible"+ newUserButton.isSelected());
                        getErrorMessage();
                        // decorator.errorMessageSetUp(newUserButton);
                    }  

                    else{
                        System.out.println("something went wrong in new users nextbutton procedure");
                    }
                    
                    set.setExistingOrNew(existingOrNew);
                    if (originalExistingOrNew != existingOrNew) { //user changed the existing or new
                        set.setUsername(null);
                        System.out.println("right after setting username to null: "+set.getUsername());
                    }

            //}
            //});
    }

    private void getErrorMessage() {
        // SwingUtilities.invokeLater(new Runnable() {
        //    @Override
        //         public void run() {
                    decorator.errorMessageSetUp(newUserButton);
        //     }
        // });
    }

    public void setButtonSelected() {
        originalExistingOrNew = set.getExistingOrNew();
        existingOrNew = set.getExistingOrNew().trim();
        if (existingOrNew == "New User") {
            newUserButton.setSelected(true);
            moveOnPossible = true;
        }
        
        else if(existingOrNew == "Existing") {
            existingButton.setSelected(true);
            moveOnPossible = true;
        }
    }

    public void showWindow() {
        window.setLocationRelativeTo(null);
        // if (windowX != 0 && windowY != 0) {
        //     window.setLocation(windowX, windowY);
        // }
     
        // else {
        //     window.setLocationRelativeTo(null);
        // }
     
        //////window.setVisible(true);
    
    // if (windowX != 0 && windowY != 0) {
    //     //window.setLocation(windowX, windowY);
    //     // decorator.setWindowX(windowX);
    //     // decorator.setWindowY(windowY);

    // }

    // else {
    //     //window.setLocation(window.getX(), window.getY());
    // }

    // window.setVisible(true);
    }

    // private void hideWindow() {
    //     backButton.setVisible(false);
    //     nextButton.setVisible(false);
    // }

    public class EnterAction extends AbstractAction {
        //SwingUtilities.invokeLater(new Runnable() {
                //@Override
        @Override
        public void actionPerformed(ActionEvent e) {
                //public void run() {
                    System.out.println(newUserActionStarted+" "+ newUserActionCompleted);
                    System.out.println(1111);
                    //decorator.errorMessageSetUp(newUserButton);

                    //if (newUserButton.isSelected()) {
                        //doNextButtonProcedure();
                        //SwingUtilities.invokeLater(() -> {
                            // doNextButtonProcedure();
                        //});
                   //}

                //     else {
                //         // SwingUtilities.invokeLater(new Runnable() {
                //         //     @Override
                //         //     public void run() {
                //                 decorator.errorMessageSetUp(newUserButton);              
                // // } 
                // //         });
                //     }
                    System.out.println("clicktime millis: "+ clickTimeMillis);


                    actionPriorities.addClassActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println(6666);
                            // // Perform actions for this listener with priority 1
                            // clickTimeMillis = System.currentTimeMillis();
                            // moveOnPossible = true;
                            // existingOrNew = newUserButton.getText();
                            // checkIfExistingChangedWithUsername();
                            // newUserActionCompleted = true;
        
        
                            // actionPriorities.actionPerformed(e);
                            doNextButtonProcedure();
                        }
                    }, 2);  // Add this ActionListener with priority 1

                //}
            //});
        }
    }
    // public class EnterAction extends AbstractAction {
    //     @Override
    //     public void actionPerformed(ActionEvent e) {
    //         // Introduce a small delay before calling doNextButtonProcedure()
    //             @Override
    //             public void actionPerformed(ActionEvent evt) {
    //                 //SwingUtilities.invokeLater(new Runnable() {
    //                     doNextButtonProcedure();
    //                 //});
    //             }
    // }
    

    // private void processEnterAction() {
    //     doNextButtonProcedure();
    // }
}

