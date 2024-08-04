import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.junit.jupiter.api.Test;

import controller.Creator;
import view.Launcher;
import view.NewUser;
import view.student.StudentClasses;
import model.Set;
import model.SetList;
import model.SetState;

public class GradeTrackerTests {

    @Test
    public void testCaseCode() {
       System.out.println("This is the testcase in this class");
       String str1="This is the testcase in this class";
       assertEquals("This is the testcase in this class", str1);
   }

   @Test
    public void testWindowMade() {
       //Launcher launcher = new Launcher();
       Launcher.initialize();
       Set set = Set.getInstance();
       JFrame window = set.getWindow();

       assertEquals(false, window == null);
   }

   @Test
    public void testWindowVisisble() {
       Set set = Set.getInstance();
       Launcher.initialize();
       NewUser newUser = new NewUser();
       newUser.showWindow();
       JFrame window = set.getWindow();


       assertEquals(true, window.isVisible());
   }



    // Add more test methods as needed
    @Test
    public void testGradeTrackerButtonEnabled() {
      //class where deletemode
      Set set = Set.getInstance();
      Launcher.initialize();
      NewUser newUser = new NewUser();
      newUser.showWindow();
      Creator create = new Creator();
      SetList setList = SetList.getInstance();
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
   }

}

