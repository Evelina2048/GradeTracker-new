import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
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
import model.SETTEST;
import model.Set;
import model.SetList;
import model.SetState;
import model.SetUserInformation;

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
   public void testGradeTrackerButtonEnabled() {
     //class where deletemode

     Launcher.initialize();
     NewUser newUser = new NewUser();
     newUser.showWindow();


     Creator create = new Creator();
     SetState setState = SetState.getInstance();


     StudentClasses studentClasses = new StudentClasses();
     studentClasses.studentClassesLaunch();

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













































//     // Add more test methods as needed
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
        //window.add(classLabelPanel);
        Component[] windowComponents = window.getContentPane().getComponents();
        JPanel windZero = (JPanel) windowComponents[0];
        //JPanel compZero = (JPanel) windowComponents[0];

        //System.out.println("iii"+ compZero.getClass().getName());
        //JPanel compZeroZero = (JPanel) compZero.getComponent(0);
        for (Component windowComp : windowComponents) {
        //for (int i = 0; i < windowComponents.length; i++) {
            //JPanel compzzz = (JPanel) windowComp;
            //JPanel compzzz = 

            //JPanel compZero = (JPanel) compzzz.getComponent(0);

            //System.out.println("ttt "+compzzz.getName()+ " "+ compzzz.getClass().getName()+ " "+ window.getRootPane().getComponentCount());
            //System.out.println("ttt "+(classLabelPanel == null));
            
            // System.out.println("labelpanel ");
            if (windowComp == classLabelPanel) {
                windowHasClassLabelPanel = true;
                break;
            }

            // else {
            //     System.out.println("wind "+windZero.getComponent(0).getClass().getName());
            // }

        }
        assertTrue(windowHasClassLabelPanel);
   }

}

