package view;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

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

import model.Set;
import model.SetState;
import model.SetUserInformation;

import controller.Creator;
import controller.Decorator;
import controller.CompositeActionListenerWithPriorities;
import controller.CreateButton;

public class NewUser extends JFrame {
    private JFrame window;
    //private String studentOrTeacher;
    private String existingOrNew;
    private Gather gatherFrame;

    private Set set;
    private SetState setState;
    private SetUserInformation setUserInformation;

    private CompositeActionListenerWithPriorities actionPriorities;
    private CreateButton createButton = new CreateButton();
    long clickTimeMillis;
    private String currentClass = "NewUser loading";

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

    Decorator decorate = new Decorator();
    private String originalExistingOrNew;

    public NewUser() {
        this.set = Set.getInstance();
        this.setState = SetState.getInstance();
        this.setUserInformation = SetUserInformation.getInstance();
        this.actionPriorities = CompositeActionListenerWithPriorities.getInstance();

        window = set.getWindow();

        actionPriorities.setCurrentClass(currentClass); //needs to be set here as well because if going between classes really quick on multiple threads, want to make sure actionPriorities has the right class. And using integers that represent view order for comparison logic in class

        newUserSetup();

        EnterAction enterAction = new EnterAction();

        window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        window.getRootPane().getActionMap().put("enterAction", enterAction);

        window.requestFocusInWindow();
    }

    public void newUserSetup() {
        window = set.getWindow();
        setState.setCurrentClass(currentClass);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(windowWidth, windowHeight);

        window.setTitle("New User");

        if (setUserInformation.getUsername() != null) {
            originalExistingOrNew = setUserInformation.getExistingOrNew();
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
        JLabel instructionsWords = new JLabel("Welcome! Are you a new user?");
        instructionsPanel = decorate.InstructionsPanelDecorate(instructionsPanel, instructionsWords);
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

        existingButton.addActionListener(e -> {
            addExistingUserActionListener();
    });

    }

    private void newUserButton() {
        newUserButton = new JRadioButton("New User");
        choicesButtonDecorate(newUserButton);
        addNewUserActionListener();
    }

    private void checkIfExistingChangedWithUsername() {
        if (setUserInformation.getUsername() != null && existingOrNew.equals(originalExistingOrNew)) { //user has already been in gather frame and theyre the same
            set.setNewOrExistingChanged(false);
        }

        else if((setUserInformation.getUsername() != null && !existingOrNew.equals(originalExistingOrNew))) { //user has already been in gather frame and theyre different
            set.setNewOrExistingChanged(true);
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
        choicesPanel.add(existingButton, decorate.choiceGbc());
    }

    private void buttonSetUp() {
        backNextButtonsPanel = new JPanel(new BorderLayout());

        new Creator();
        JButton backButton = createButton.backButtonCreate();
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);

        JButton nextButton = createButton.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPriorities.setCurrentClass(currentClass);
                nextButtonActionListenerWithPriorities("nextButton");
            }
        });


        nextButtonPanel.add(nextButton);

        backNextButtonsPanel = createButton.makeBackNextButtonsPanel(backButtonPanel, new JPanel(), nextButtonPanel);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);


        backButton.setEnabled(false);

    }

    private void doNextButtonProcedure(){
        System.out.println("in the do next button procedure in new user");
        if (newUserButton.isSelected() || existingButton.isSelected()){
            decorate.hideWindow(instructionsPanel, choicesPanel, backNextButtonsPanel);
            decorate.removeForNewUserWindow(instructionsPanel, choicesPanel, backNextButtonsPanel);
            set.setWindow(window);

            if (originalExistingOrNew != existingOrNew) { //user changed the existing or new
                setUserInformation.setUsername(null);
                System.out.println("right after setting username to null: "+setUserInformation.getUsername());
            }

            if (gatherFrame == null) {
                // Create a new instance of Gather if it doesn't exist
                gatherFrame = new Gather();
                gatherFrame.gatherLaunch();
            }
            else {
                // Update the existing Gather window panels
                gatherFrame.gatherLaunch();
            }
        }

        else{
            System.out.println("something went wrong in new users nextbutton procedure");
        }

    }

    public void setButtonSelected() {
        existingOrNew = setUserInformation.getExistingOrNew().trim();
        if (existingOrNew == "New User") {
            addNewUserActionListener();
            newUserButton.setSelected(true);
        }

        else if(existingOrNew == "Existing") {
            addExistingUserActionListener();
            existingButton.setSelected(true);
        }

        System.out.println("in new user. listeners: "+ actionPriorities.DEBUGLISTENERSIZE());
    }

    public void showWindow() {
        window.setLocationRelativeTo(null);
        set.setWindow(window);
    }

    private void addNewUserActionListener() {
        newUserButton.addActionListener(e -> {
        actionPriorities.addClassActionListener(b -> {
            existingOrNew = newUserButton.getText();
            setUserInformation.setExistingOrNew(existingOrNew);
            checkIfExistingChangedWithUsername();
        }, 2, "click", newUserButton, currentClass);
        });
    }

    private void addExistingUserActionListener() {
        actionPriorities.addClassActionListener(b -> {
            existingOrNew = existingButton.getText();
            setUserInformation.setExistingOrNew(existingOrNew);
            checkIfExistingChangedWithUsername();
        }, 2, "click", newUserButton, currentClass);
    }

    private void nextButtonActionListenerWithPriorities(String keyCause) {
        actionPriorities.addClassActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {// remember wont run  if just enter without a click
                System.out.println("enteraction");
                doNextButtonProcedure();
            }
        }, 1, "nextButton", newUserButton, currentClass);  // Add this ActionListener with priority 1
    }

    public class EnterAction extends AbstractAction  {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("enter from new user");
            actionPriorities.setCurrentClass(currentClass);
            nextButtonActionListenerWithPriorities("EnterAction");

    }
    }
    }