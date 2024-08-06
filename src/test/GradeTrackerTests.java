import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        SetState.getInstance().TESTRESETSTATE();
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

   @Test
    public void testWindowVisisble() {

    //    set.TESTRESETSET();
       Launcher.initialize();
       Set set = Set.getInstance();
       NewUser newUser = new NewUser();
       newUser.showWindow();
    //set = Set.getInstance();
    // JFrame window = set.getWindow();



        JFrame window = set.getWindow();
        assertEquals(true, window.isVisible());
    //    set.TESTRESETSET();
   }

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
//     @Test
//     public void testAddingLoaded() {
//         //class where deletemode
//         Set set = Set.getInstance();
//         SETTEST sETTEST = SETTEST.getInstance();
//         SetUserInformation setUserInformation = SetUserInformation.getInstance();
//         Launcher.initialize();
//         //NewUser newUser = new NewUser();
//         //newUser.showWindow();
//         setUserInformation.setUsername("TESTHELLO");

//         Creator create = new Creator();
//     //     SetList setList = SetList.getInstance();
//     //     SetState setState = SetState.getInstance();


//         StudentClasses studentClasses = new StudentClasses();
//         studentClasses.studentClassesLaunch();

//         JPanel textFieldPanel = new JPanel();

//         // textFieldPanel.add(create.createTextBox("English", "JTextField",false));
//         // textFieldPanel.add(create.createTextBox("Math", "JTextField",false));
//         //textFieldPanel.add(create.createTextBox("English", "JTextField",true));
//         //textFieldPanel.add(create.createTextBox("Math", "JTextField",true));

//         //JButton saveButton = studentClasses.TESTSAVEBUTTON();
//         JButton nextButton = studentClasses.TESTNEXTBUTTON();
//         ActionEvent nextActionEvent = new ActionEvent(nextButton, ActionEvent.ACTION_PERFORMED, "Click");
//         for (ActionListener listener : nextButton.getActionListeners()) {
//             listener.actionPerformed(nextActionEvent);
//         }
//         System.out.println("hi");

//         JPanel textBoxPanel = sETTEST.GETTESTTEXTBOXPANEL();
//         System.out.println("textBoxPanelcomponent "+textBoxPanel.getComponentCount());
//         assertEquals(textBoxPanel.getComponentCount(), 5);



//     //     ActionEvent saveActionEvent = new ActionEvent(saveButton, ActionEvent.ACTION_PERFORMED, "Click");

//     //     for (ActionListener listener : saveButton.getActionListeners()) {
//     //         listener.actionPerformed(saveActionEvent);
//     //     }

//     //     setState.setTextFieldPanel(textFieldPanel);
//     //     //studentClasses.deleteMode();

//     //     JButton deleteClassButton = studentClasses.TESTDELETECLASSBUTTON();

//     //     ActionEvent actionEvent = new ActionEvent(deleteClassButton, ActionEvent.ACTION_PERFORMED, "Click");

//     // for (ActionListener listener : deleteClassButton.getActionListeners()) {
//     //     listener.actionPerformed(actionEvent);
//     // }

//     // deleteClassButton = studentClasses.TESTDELETECLASSBUTTON();
//     // actionEvent = new ActionEvent(deleteClassButton, ActionEvent.ACTION_PERFORMED, "Click");

//     // for (ActionListener listener : deleteClassButton.getActionListeners()) {
//     //     listener.actionPerformed(actionEvent);
//     // }

//     // deleteClassButton = studentClasses.TESTDELETECLASSBUTTON();
//     // actionEvent = new ActionEvent(deleteClassButton, ActionEvent.ACTION_PERFORMED, "Click");

//     // for (ActionListener listener : deleteClassButton.getActionListeners()) {
//     //     listener.actionPerformed(actionEvent);
//     // }

//     // deleteClassButton = studentClasses.TESTDELETECLASSBUTTON();
//     // actionEvent = new ActionEvent(deleteClassButton, ActionEvent.ACTION_PERFORMED, "Click");

//     // assertFalse(saveButton.isEnabled());
//    }

}

