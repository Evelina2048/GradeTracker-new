import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.awt.Component;
import java.awt.event.ActionEvent;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.CompositeActionListenerWithPriorities;
import controller.Creator;
import view.Launcher;
import view.NewUser;
import view.student.StudentClasses;
import view.student.StudentStatCollect;
import model.GoIntoPanel;
import model.SETTEST;
import model.Set;
import model.SetList;
import model.SetState;
import model.SetUserInformation;

import controller.FileHandling;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GradeTrackerTests {
    @BeforeEach
    public void setUp() {
        // Reset the state before each test
        Set.getInstance().TESTRESETSET();
        Set.getInstance().setWindow(null);
        SetState.getInstance().TESTRESETSTATE();
        SETTEST.getInstance().TESTRESETSETTEST();
        SetUserInformation.getInstance().TESTRESETSETUSERINFORMATION();
        CompositeActionListenerWithPriorities.getInstance().TESTRESETACTIONPRIORITIES();
    }

    @AfterEach
    public void endUp() {
        // Reset the state before each test
        Set.getInstance().TESTRESETSET();
        Set.getInstance().setWindow(null);
        SetState.getInstance().TESTRESETSTATE();
        SETTEST.getInstance().TESTRESETSETTEST();
        SetUserInformation.getInstance().TESTRESETSETUSERINFORMATION();
        CompositeActionListenerWithPriorities.getInstance().TESTRESETACTIONPRIORITIES();
    }
    
    @Test
    public void testCaseCode() {
       System.out.println("This is the testcase in this class");
       String str1="This is the testcase in this class";
       assertEquals("This is the testcase in this class", str1);
   }

   @Test
    public void testWindowMade() {
       Launcher.initialize();
       Set set = Set.getInstance();
       JFrame window = set.getWindow();

       assertEquals(false, window == null);
       //set.TESTRESETSET();
   }

//    @Test
//     public void testWindowVisisble() {
//        Launcher.initialize();
//        System.out.println("butfirstfirst "+(Set.getInstance().getWindow()==null)+ " "+Set.getInstance().getWindow().isVisible());
//        Set set = Set.getInstance();
//        System.out.println("butfirst "+(set.getWindow()==null)+ " "+Set.getInstance().getWindow().isVisible());
//        JFrame window = set.getWindow();
//        assertEquals(true, window.isVisible());
//    }

   @Test
   public void testGradeTrackerSaveButtonEnabled() {
     //class where deletemode

     Launcher.initialize();
     NewUser newUser = new NewUser();
     newUser.showWindow();


     Creator create = new Creator();
     SetState setState = SetState.getInstance();


     StudentClasses studentClasses = new StudentClasses();
     studentClasses.studentClassesLaunch();

     //FileHandling fileHandler = new FileHandler();

     JPanel textFieldPanel = new JPanel();

     textFieldPanel.add(create.createTextBox("English", "JTextField",false));
     textFieldPanel.add(create.createTextBox("Math", "JTextField",false));

     JButton saveButton = studentClasses.TESTSAVEBUTTON();

     ActionEvent saveActionEvent = new ActionEvent(saveButton, ActionEvent.ACTION_PERFORMED, "Click");

     for (ActionListener listener : saveButton.getActionListeners()) {
         listener.actionPerformed(saveActionEvent);
     }

     setState.setTextFieldPanel(textFieldPanel);
     //studentClasses.deleteMode();

     JButton deleteClassButton = studentClasses.TESTDELETECLASSBUTTON();

     ActionEvent actionEvent = new ActionEvent(deleteClassButton, ActionEvent.ACTION_PERFORMED, "Click");

   for (ActionListener listener : deleteClassButton.getActionListeners()) {
       listener.actionPerformed(actionEvent);
   }

   deleteClassButton = studentClasses.TESTDELETECLASSBUTTON();
   actionEvent = new ActionEvent(deleteClassButton, ActionEvent.ACTION_PERFORMED, "Click");

   for (ActionListener listener : deleteClassButton.getActionListeners()) {
       listener.actionPerformed(actionEvent);
   }

   deleteClassButton = studentClasses.TESTDELETECLASSBUTTON();
   actionEvent = new ActionEvent(deleteClassButton, ActionEvent.ACTION_PERFORMED, "Click");

   for (ActionListener listener : deleteClassButton.getActionListeners()) {
       listener.actionPerformed(actionEvent);
   }

   deleteClassButton = studentClasses.TESTDELETECLASSBUTTON();
   actionEvent = new ActionEvent(deleteClassButton, ActionEvent.ACTION_PERFORMED, "Click");

   assertFalse(saveButton.isEnabled());
   //setState.TESTRESETSTATE();
}

@Test
    public void testAddingLoaded() {
        //class where deletemode
        SETTEST sETTEST = SETTEST.getInstance();
        JPanel classLabelPanel;
        SetUserInformation setUserInformation = SetUserInformation.getInstance();
        CompositeActionListenerWithPriorities actionPriorities = CompositeActionListenerWithPriorities.getInstance();
        Launcher.initialize();
        Set set = Set.getInstance();
        Boolean windowHasClassLabelPanel = false;
        setUserInformation.setUsername("TESTHELLO");

        //System.out.println("1111 "+window.getComponentCount());

        StudentClasses studentClasses = new StudentClasses();
        studentClasses.studentClassesLaunch();

        actionPriorities.TESTFORCECURRENTCLASS("StudentClasses");

        JButton nextButton = studentClasses.TESTNEXTBUTTON();
        ActionEvent nextActionEvent = new ActionEvent(nextButton, ActionEvent.ACTION_PERFORMED, "Click");
        for (ActionListener listener : nextButton.getActionListeners()) {
            listener.actionPerformed(nextActionEvent);
        }

        //JPanel textBoxPanel = sETTEST.GETTESTTEXTBOXPANEL();
        //actionPriorities.TESTFORCECURRENTCLASS("StudentStatCollect");

        classLabelPanel = sETTEST.GETTESTCLASSLABELPANEL();
        System.out.println("classlabelpanel"+ (classLabelPanel==null));
        System.out.println("hi "+ classLabelPanel.getName());
        //System.out.println("textBoxPanelcomponent "+classLabelPanel.getComponentCount());

        JFrame window = set.getWindow();
        Component[] windowComponents = window.getContentPane().getComponents();
        JPanel windZero = (JPanel) windowComponents[0];

        for (Component windowComp : windowComponents) {
            if (windowComp == classLabelPanel) {
                windowHasClassLabelPanel = true;
                break;
            }

        }
        assertTrue(windowHasClassLabelPanel);
   }


   //still in progress
   @Test
    public void testNewTypeWithLoaded() {
        SETTEST sETTEST = SETTEST.getInstance();
        JPanel classLabelPanel;
        SetUserInformation setUserInformation = SetUserInformation.getInstance();
        CompositeActionListenerWithPriorities actionPriorities = CompositeActionListenerWithPriorities.getInstance();
        Launcher.initialize();
        Set set = Set.getInstance();
        SetList setList = SetList.getInstance();

        // FileHandler fileHandler = new FileHandler();
        FileHandling fileHandler = new FileHandling();
        Boolean windowHasClassLabelPanel = false;
        setUserInformation.setUsername("TESTNEWTYPEWITHLOADED");

        StudentClasses studentClasses = new StudentClasses();
        studentClasses.studentClassesLaunch();

        actionPriorities.TESTFORCECURRENTCLASS("StudentClasses");

        StudentStatCollect studentStatCollect = new StudentStatCollect();
        studentStatCollect.studentStatCollectLaunch();
        //getfinalclasslist //will be make up of file

        ArrayList<String> myFiles = fileHandler.readFileToList(setUserInformation.getPathToClassTextFile());
        //setList.setFinalClasslist();
        setList.setFinalClassList(myFiles);
        studentStatCollect.addLoadedBoxes();

        JButton nextButton = studentStatCollect.TESTNEXTBUTTON();
        ActionEvent nextActionEvent = new ActionEvent(nextButton, ActionEvent.ACTION_PERFORMED, "Click");
        for (ActionListener listener : nextButton.getActionListeners()) {
            listener.actionPerformed(nextActionEvent);
        }

        //JButton statNextButton = studentStatCollect.TESTNEXTBUTTON();

        //assertTrue(true);
   }













































// Add more test methods as needed
@Test
    public void testNewClassButtonDisabledAfterDeleting() {
       //class where deletemode
       GoIntoPanel goIntoPanel = new GoIntoPanel();

        Launcher.initialize();
        NewUser newUser = new NewUser();
        newUser.showWindow();


        Creator create = new Creator();
        SetState setState = SetState.getInstance();


        StudentClasses studentClasses = new StudentClasses();
        studentClasses.studentClassesLaunch();

        //FileHandling fileHandler = new FileHandler();

        JPanel textFieldPanel = new JPanel();

        textFieldPanel.add(create.createTextBox("English", "JTextField",false));
        textFieldPanel.add(create.createTextBox("Math", "JTextField",false));

        //JButton saveButton = studentClasses.TESTSAVEBUTTON();
        JButton saveButton = studentClasses.TESTSAVEBUTTON();


        ActionEvent saveActionEvent = new ActionEvent(saveButton, ActionEvent.ACTION_PERFORMED, "Click");

        for (ActionListener listener : saveButton.getActionListeners()) {
            listener.actionPerformed(saveActionEvent);
        }

        setState.setTextFieldPanel(textFieldPanel);

        JButton deleteClassButton = studentClasses.TESTDELETECLASSBUTTON();

        ActionEvent actionEvent = new ActionEvent(deleteClassButton, ActionEvent.ACTION_PERFORMED, "Click");

        for (ActionListener listener : deleteClassButton.getActionListeners()) {
            listener.actionPerformed(actionEvent);
        }

        //JTextField english = (JTextField) goIntoPanel.goIntoPanelReturnTextbox((JPanel) textFieldPanel.getComponent(0), 0);
        JTextField english = (JTextField) textFieldPanel.getComponent(0);


        MouseEvent mouseEvent = new MouseEvent(
            english,                     // Source component
            MouseEvent.MOUSE_CLICKED,    // Event type (e.g., MOUSE_CLICKED)
            System.currentTimeMillis(),  // When the event occurred (current time)
            0,                           // No modifiers (no Shift, Ctrl, etc.)
            0, 0,                        // X and Y coordinates (could be 0, 0 for simplicity)
            1,                           // Click count (1 for single click)
            false                        // Not a popup trigger
            );

        for (MouseListener listener : english.getMouseListeners()) {
            listener.mouseClicked(mouseEvent);
        }


    deleteClassButton = studentClasses.TESTDELETECLASSBUTTON();
    actionEvent = new ActionEvent(deleteClassButton, ActionEvent.ACTION_PERFORMED, "Click");

    for (ActionListener listener : deleteClassButton.getActionListeners()) {
        listener.actionPerformed(actionEvent);
    }

    // deleteClassButton = studentClasses.TESTDELETECLASSBUTTON();
    // actionEvent = new ActionEvent(deleteClassButton, ActionEvent.ACTION_PERFORMED, "Click");

    // for (ActionListener listener : deleteClassButton.getActionListeners()) {
    //     listener.actionPerformed(actionEvent);
    // }

    ////

    JButton newClassButton = studentClasses.TESTNEWCLASSBUTTON();
    actionEvent = new ActionEvent(newClassButton, ActionEvent.ACTION_PERFORMED, "Click");

    assertFalse(newClassButton.isEnabled());
    //setState.TESTRESETSTATE();
}
}

