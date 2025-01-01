//WOULD NOT BELONG IN A PUBLIC VERSION, AS IT COULD BE USED BY BAD ACTORS.
//ALL FUNCTIONS AND CALLING OF THESE FUNCTIONS WOULD BE REMOVED

package model;

import javax.swing.JPanel;

import view.student.StudentStatCollect;

public class SETTEST {
    private JPanel TESTTEXTBOXPANEL;

    //Below is for singleton design pattern
    // Static variable to hold the single instance of the class
    private static SETTEST instance;
    private JPanel classLabelPanel;
    private StudentStatCollect studentStatCollect;

    // Private constructor to prevent instantiation
    private SETTEST() {}

    // Public method to provide access to the single instance
    public static SETTEST getInstance() {
        if (instance == null) {
            instance = new SETTEST();
        }
        return instance;
    }

    public void SETTESTTEXTBOXPANEL(JPanel textBoxPanel) {
        TESTTEXTBOXPANEL = textBoxPanel;
    }

    public JPanel GETTESTTEXTBOXPANEL() {
        return TESTTEXTBOXPANEL;
    }

    public void SETTESTCLASSLABELPANEL(JPanel thisClassLabelPanel) {
        classLabelPanel = thisClassLabelPanel;
    }

    public JPanel GETTESTCLASSLABELPANEL() {
        return classLabelPanel;
    }

    public void TESTRESETSETTEST() {
        instance = null;
    }

    public void TESTSETCURRENTINSTANCE(StudentStatCollect thisInstance) {
        studentStatCollect = thisInstance;
    }
    public StudentStatCollect TESTGETCURRENTINSTANCE() {
        return studentStatCollect;
    }

}