package view;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//key listening
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import model.Set;
import model.SetUserInformation;

import view.student.StudentClasses;
import controller.Creator;
import controller.Decorator;
import controller.CreateButton;
import controller.CompositeActionListenerWithPriorities;

public class MainWindow extends JFrame {
private String studentOrTeacher;
private boolean moveOnPossible = false;
private Set set;
private SetUserInformation setUserInformation;
private CompositeActionListenerWithPriorities actionPriorities;
private String currentClass = "StudentStatCollect Loading";

private CreateButton createButton = new CreateButton();

JLabel instructionsWords;

JRadioButton studentButton;
JRadioButton teacherButton;
ButtonGroup teacherStudentGroup;
int windowWidth = 750;
int windowHeight = 500;

//panels
JPanel instructionsPanel;
JPanel choicesPanel;
JPanel backNextButtonsPanel;
JFrame window;

Decorator decorate = new Decorator();

public MainWindow() {
    MainWindowLaunch();

}

public void MainWindowLaunch() {
    System.out.println("entering main window");
    this.set = Set.getInstance();
    this.setUserInformation = SetUserInformation.getInstance();
    this.actionPriorities = CompositeActionListenerWithPriorities.getInstance();
    //actionPriorities.setCurrentClass(currentClass);

    window = set.getWindow();

    windowSetUp();

    InstructionsWordsWindow();

    radioButtonSetUp();

    backNextButton();

    EnterAction enterAction = new EnterAction();

    window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
    window.getRootPane().getActionMap().put("enterAction", enterAction);

    window.requestFocusInWindow();
    currentClass = "MainWindow";
    actionPriorities.setCurrentClass(currentClass);
}

private void windowSetUp() {
    //window set up
    window.setTitle("Launcher");
    //window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    //window.setLayout(null);
    window.setSize(windowWidth, windowHeight);
}
 
private void InstructionsWordsWindow() {
        //JLabel instructionsWords = new JLabel("Hello 1234567890! Are you a student or a teacher?");
        instructionsWords = new JLabel("Hello "+setUserInformation.getUsername() + "! Are you a student or a teacher?");
        instructionsPanel = decorate.InstructionsPanelDecorate(instructionsPanel, instructionsWords);
        truncateLabelText("Hello ", "! Are you a student or a teacher?", window.getWidth());
        //System.out.println
        instructionsPanel = decorate.InstructionsPanelDecorate(instructionsPanel, instructionsWords);
    }

private void truncateLabelText(String prefix, String suffix, int maxWidth) {
    FontMetrics fontMetrics = instructionsWords.getFontMetrics(instructionsWords.getFont());
    JLabel usernameLabel = new JLabel(setUserInformation.getUsername());
    usernameLabel.getText();

    System.out.println("username "+setUserInformation.getUsername());

    String username = setUserInformation.getUsername();
    String ellipsis = " ...";
    int prefixWidth = fontMetrics.stringWidth(prefix);
    int suffixWidth = fontMetrics.stringWidth(suffix);
    int usernameWidth = fontMetrics.stringWidth(username);
    int ellipsisWidth = fontMetrics.stringWidth(ellipsis);
    int availableWidth = maxWidth - prefixWidth - suffixWidth - ellipsisWidth;

    //how many ~ to get to max width?
    System.out.println("~ width "+ fontMetrics.stringWidth("~")+" max width "+ maxWidth);

    System.out.println("usernamewidth: "+ usernameWidth+ ": available width: "+availableWidth);

    //need to find sweet spot where is not less than max

    if (fontMetrics.stringWidth(username) <= availableWidth) {//if is already less than max with username
    //then return. because no changes necessary;
    return;
    }

    int endIndex = 7;
    String currentUsernamePermittedAmount = setUserInformation.getUsername().substring(0, endIndex);
    System.out.println("currentUsernamePermittedAmount "+currentUsernamePermittedAmount);

    while (fontMetrics.stringWidth(username.substring(0, endIndex+1)) <= availableWidth) {    
        System.out.println("it works "+ fontMetrics.stringWidth(username.substring(0, endIndex+1))+" "+availableWidth);
        endIndex++;
        System.out.println("it works2: "+currentUsernamePermittedAmount+" "+setUserInformation.getUsername().substring(0, endIndex));
    }
    if(currentUsernamePermittedAmount.charAt(currentUsernamePermittedAmount.length()-1)==' ' && currentUsernamePermittedAmount.charAt(currentUsernamePermittedAmount.length()-2)!=' ') {
        instructionsWords =  new JLabel("Hello "+ setUserInformation.getUsername().substring(0, endIndex-1) + " ...! Are you a student or a teacher?");
    }
    else {
    instructionsWords =  new JLabel("Hello "+ setUserInformation.getUsername().substring(0, endIndex) + " ...! Are you a student or a teacher?");
    }

}


private void radioButtonSetUp() {
    teacherStudentGroup = new ButtonGroup();
    Color choicesPanelColor = Color.decode("#AFA2A2");

    choicesPanel = new JPanel(new GridBagLayout());
    choicesPanel.setBackground(choicesPanelColor);

    //initialize buttons with color
    teacherButton = new JRadioButton("Teacher");
    teacherButton.setEnabled(false);
    choicesButtonDecorate(teacherButton);
    teacherButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            studentOrTeacher = teacherButton.getText();
            setUserInformation.setStudentOrTeacher(studentOrTeacher);
            moveOnPossible = true;
        }});

    studentButton = new JRadioButton("Student");
    choicesButtonDecorate(studentButton);
    studentButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            studentOrTeacher = studentButton.getText();
            setUserInformation.setStudentOrTeacher(studentOrTeacher);
            moveOnPossible = true;
        }});

    addToChoicesPanel(teacherStudentGroup, teacherButton, studentButton, choicesPanel);

    window.add(choicesPanel);
}

private void choicesButtonDecorate(JRadioButton button) {
    Font buttonFont = new Font("Roboto", Font.PLAIN, 25); // Change the font and size here
    button.setForeground(Color.WHITE);
    button.setFont(buttonFont);
}

private void addToChoicesPanel(ButtonGroup teacherStudentGroup, JRadioButton teacherButton, JRadioButton studentButton, JPanel choicesPanel) {
    teacherStudentGroup.add(teacherButton);
    teacherStudentGroup.add(studentButton);
    choicesPanel.add(teacherButton);
    choicesPanel.add(studentButton);
    choicesPanel.add(teacherButton, decorate.choiceGbc());
}

private void backNextButton() {
    backNextButtonsPanel = new JPanel(new BorderLayout());
    new Creator();
    JButton backButton = createButton.backButtonCreate();
    // backButton.addActionListener(new ActionListener() {
    //     public void actionPerformed(ActionEvent e) {;
    //         decorate.hideWindow(instructionsPanel, choicesPanel, backNextButtonsPanel);    
    //         doBackButtonProcedure();
    //     }
    // });
    //:
    backButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            actionPriorities.setCurrentClass(currentClass);
            actionPriorities.addClassActionListener(b -> {
                decorate.hideWindow(instructionsPanel, choicesPanel, backNextButtonsPanel);
                decorate.removeForNewUserWindow(instructionsPanel, choicesPanel, backNextButtonsPanel);
                doBackButtonProcedure();
            }, 2, "backButton",null, currentClass);
    }});
    //:
    JPanel backButtonPanel = new JPanel();
    backButtonPanel.add(backButton);

    JButton nextButton = createButton.nextButtonCreate();
    JPanel nextButtonPanel = new JPanel();
    nextButtonPanel.add(nextButton);

    nextButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            actionPriorities.setCurrentClass(currentClass);
            actionPriorities.addClassActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {// remember wont run  if just enter without a click
                    //System.out.println("enteraction");
                    doNextButtonProcedure();
                }
            }, 1, "nextButton", null, currentClass);  // Add this ActionListener with priority 1
        }
    });
    //:
    backNextButtonsPanel = createButton.makeBackNextButtonsPanel(backButtonPanel, new JPanel(), nextButtonPanel);
    window.add(backNextButtonsPanel, BorderLayout.SOUTH);
}

private void doBackButtonProcedure() {
    hideWindow();
    Gather gather = new Gather();
    gather.gatherLaunch();
}

private void doNextButtonProcedure() {
    if (moveOnPossible) {
        set.setWindow(window);
        String filePath = "somethingwentwrong";//if not overwritten, somethingwent wrong
            //if (setUserInformation.getExistingOrNew().trim().equals("New User")) { //if new user,
                //System.out.println("about to go to student classes");
                goToStudentClasses(filePath);
                decorate.hideWindow(instructionsPanel, choicesPanel, backNextButtonsPanel);
            //}
    }
    else if (!moveOnPossible) {
        //decorate.errorMessageSetUp(studentButton);
        decorate.pleaseChooseAnOptionWithRadioButtons(studentButton);
    }
}

private void goToStudentClasses(String filePath) {
    writeUsername(filePath);
    //move on to studentclasses class
    decorate.hideWindow(instructionsPanel, choicesPanel, backNextButtonsPanel);
    StudentClasses studentClasses = new StudentClasses();
    studentClasses.studentClassesLaunch();
}

private void writeUsername(String filePath) {
    //and username not taken
    String usernamePath = "somethingwentwrong.txt";
    String username = setUserInformation.getUsername();//textField.getText().trim();
    //setUserInformation.setUsername(username);
    if ("Student".equals(studentOrTeacher)) {
        //usernamePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/studentUsername.txt"; //KEEP
        usernamePath = "src/main/view/UserInfo/studentUsername.txt";
    }

    else if ("Teacher".equals(studentOrTeacher)) {
        //usernamePath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/teacherUsername.txt";
        usernamePath = "src/main/view/UserInfo/teacherUsername.txt";
    }

    checkIfExisting(usernamePath, username);
}

private void checkIfExisting(String filePath, String username) {
    boolean usernametaken = false;

    readNames(filePath, username, usernametaken);
    if (usernametaken == false) {
        writeNewName(filePath, username);
    }
}

private boolean readNames(String filePath, String username, Boolean usernametaken) {
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(filePath));
        readLine(reader, username, usernametaken);
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    return usernametaken;
}

private boolean readLine(BufferedReader reader, String username, boolean usernametaken){
    String line;
    try {
        while ((line = reader.readLine()) != null) {
            if (line.equals(username) && setUserInformation.getUsername() == null) {//if matches username
                //errorMessageSetUp("<html><center>Username already exists.<br> Please choose another.",200,100);
                decorate.genericPopUpMessage("<html><center>Username already exists.<br> Please choose another.", studentButton, 200 , 100);
                usernametaken = true;
                break;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    } return usernametaken;
}

// private void errorMessageSetUp(String labelWords, int width, int height) {
//     JDialog dialog = new JDialog(window, true);
//     dialog.setResizable(false);
//     dialog.setLayout(new FlowLayout());
//     JLabel label = new JLabel(labelWords);
//     label.setHorizontalAlignment(SwingConstants.CENTER);
//     dialog.add(label);
//     JButton okButton = new JButton("OK");
//     okButton.setVisible(true);
//     dialog.add(okButton);
//     dialog.setSize(width,height);
//     okButton.addActionListener(new ActionListener() {
//         public void actionPerformed(ActionEvent e) {
//             dialog.setVisible(false);
//             dialog.dispose(); 
//         }
//     });
    
//     dialog.setLocationRelativeTo(studentButton);
//     dialog.setLocation(dialog.getX(), dialog.getY() + 15); 
//     dialog.setVisible(true);
// }

private void writeNewName(String filePath, String username) {
    try (FileWriter writer = new FileWriter(filePath, true)) {
        writer.write(username + "\n");

    } catch (IOException e1) {
        e1.printStackTrace();
    }
    }

public void setButtonSelected() {
    String selectedButtonText = setUserInformation.getStudentOrTeacher();
    if (selectedButtonText == "Student") {
        studentButton.setSelected(true);
        moveOnPossible = true;
    }
    
    else if(selectedButtonText == "Teacher") {
        teacherButton.setSelected(true);
        moveOnPossible = true;
    }
}

public void show(int windowX, int windowY) {
    window.setVisible(true);
}

public void hideWindow() {

}

public class EnterAction extends AbstractAction {
    // @Override
    // public void actionPerformed(ActionEvent e) {
    //     // Introduce a small delay before calling doNextButtonProcedure()
    //     Timer timer = new Timer(10, new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent evt) {
    //             doNextButtonProcedure();
    //         }
    //     });
    //     timer.setRepeats(false); // Only execute once
    //     timer.start();
    // }

    @Override
    public void actionPerformed(ActionEvent e) {
        actionPriorities.setCurrentClass(currentClass);
        actionPriorities.addClassActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // remember won't run if just enter without a click
                // Introduce a small delay before calling doNextButtonProcedure()
                Timer timer = new Timer(10, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        doNextButtonProcedure();
                    }
                });
                timer.setRepeats(false); // Only execute once
                timer.start();
            }
        }, 1, "EnterAction", null, currentClass);  // Add this ActionListener with priority 1
    }
}
}
