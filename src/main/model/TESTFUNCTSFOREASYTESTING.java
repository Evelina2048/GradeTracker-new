package model;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.CompositeActionListenerWithPriorities;
import controller.FileHandling;
import view.student.StudentStatCollect;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.util.logging.FileHandler;
import java.awt.Component;
import java.awt.event.ActionEvent;

import controller.Creator;
import view.Launcher;
import view.NewUser;
import view.student.StudentClasses;
import model.GoIntoPanel;
import model.SETTEST;
import model.Set;
import model.SetList;
import model.SetState;
import model.SetUserInformation;

import java.awt.event.MouseListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TESTFUNCTSFOREASYTESTING {
    private JPanel TESTTEXTBOXPANEL;

    //Below is for singleton design pattern
    // Static variable to hold the single instance of the class
    private static TESTFUNCTSFOREASYTESTING instance;
    private JPanel classLabelPanel;

    CompositeActionListenerWithPriorities actionPriorities = CompositeActionListenerWithPriorities.getInstance();
    FileHandling fileHandler = new FileHandling();
    SetUserInformation setUserInformation = SetUserInformation.getInstance();
    SETTEST sETTEST = SETTEST.getInstance();

    // Private constructor to prevent instantiation
    private TESTFUNCTSFOREASYTESTING() {}

    // Public method to provide access to the single instance
    public static TESTFUNCTSFOREASYTESTING getInstance() {
        if (instance == null) {
            instance = new TESTFUNCTSFOREASYTESTING();
        }
        return instance;
    }

    public void goToStudentStatAndNextLoaded() {
        CompositeActionListenerWithPriorities actionPriorities = CompositeActionListenerWithPriorities.getInstance();
        FileHandling fileHandler = new FileHandling();
        SetList setList = SetList.getInstance();
        SetUserInformation setUserInformation = SetUserInformation.getInstance();
    
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
    }
    
    public void goToStudentStatWithNewStatsAndNextLoaded() {
        //setUserInformation.setUsername("GOTOSTUDENTSTATWITHNEWSTATSANDNEXTLOADED");
    
        actionPriorities.TESTFORCECURRENTCLASS("StudentClasses");
        StudentStatCollect studentStatCollect = new StudentStatCollect();
        studentStatCollect.studentStatCollectLaunch();
        
        //getfinalclasslist //will be make up of file
    
        //ArrayList<String> myFiles = fileHandler.readFileToList(setUserInformation.getPathToClassTextFile());
        //setList.setFinalClasslist();
        //SetList setList = SetList.getInstance();
        //SetList.getInstance().getFinalClassList();
        //System.out.println("instanceclasslist "+ SetList.getInstance().getFinalClassList()+ " "+ myFiles);
        studentStatCollect.addLoadedBoxes();
    
        //change boxes
        //studentStatCollect.TEST
        JPanel textBoxPanel = sETTEST.GETTESTTEXTBOXPANEL();
        for (int i = 0; i < textBoxPanel.getComponentCount(); i++) {
        MouseEvent mouseEvent = new MouseEvent(
               textBoxPanel.getComponent(i),                     // Source component
               MouseEvent.MOUSE_CLICKED,    // Event type (e.g., MOUSE_CLICKED)
               System.currentTimeMillis(),  // When the event occurred (current time)
               0,                           // No modifiers (no Shift, Ctrl, etc.)
               0, 0,                        // X and Y coordinates (could be 0, 0 for simplicity)
               1,                           // Click count (1 for single click)
               false                        // Not a popup trigger
               );
    
           for (MouseListener listener : textBoxPanel.getMouseListeners()) {
               listener.mouseClicked(mouseEvent);
           }
    
        }
        //
        
    
        JButton nextButton = studentStatCollect.TESTNEXTBUTTON();
        ActionEvent nextActionEvent = new ActionEvent(nextButton, ActionEvent.ACTION_PERFORMED, "Click");
        for (ActionListener listener : nextButton.getActionListeners()) {
            listener.actionPerformed(nextActionEvent);
        }
    }
    
    public void goToClassesAddEnglishAndMath() {
    Launcher.initialize();
    SetState setState = SetState.getInstance();
    StudentClasses studentClasses = new StudentClasses();
    studentClasses.studentClassesLaunch();
    FileHandling fileHandler = new FileHandling();
    SetUserInformation setUserInformation = SetUserInformation.getInstance();
    setUserInformation.setUsername("TESTGOTOCLASSESADDENGLISHANDMATH");

    Creator create = new Creator();
    JPanel textFieldPanel = new JPanel();

     textFieldPanel.add(create.createTextBox("English", "JTextField",false));
     textFieldPanel.add(create.createTextBox("Math", "JTextField",false));

     ArrayList<String> myFiles = fileHandler.readFileToList(setUserInformation.getPathToClassTextFile());
     System.out.println("myfilesnull? "+ (myFiles.size()));
     SetList.getInstance().setFinalClassList(myFiles);

     JButton saveButton = studentClasses.TESTSAVEBUTTON();

     ActionEvent saveActionEvent = new ActionEvent(saveButton, ActionEvent.ACTION_PERFORMED, "Click");

     for (ActionListener listener : saveButton.getActionListeners()) {
         listener.actionPerformed(saveActionEvent);
     }

     setState.setTextFieldPanel(textFieldPanel);
}


    
}