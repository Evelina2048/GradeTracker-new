package model;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SETTEST {
    private JPanel TESTTEXTBOXPANEL;

    //Below is for singleton design pattern
    // Static variable to hold the single instance of the class
    private static SETTEST instance;
    private JPanel classLabelPanel;

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
        System.out.println("in settestboxpanel");
        TESTTEXTBOXPANEL = textBoxPanel;
    }

    public JPanel GETTESTTEXTBOXPANEL() {
        return TESTTEXTBOXPANEL;
    }

    public void SETTESTCLASSLABELPANEL(JPanel thisClassLabelPanel) {
        System.out.println("YYY "+(classLabelPanel==null));
        classLabelPanel = thisClassLabelPanel;
    }

    public JPanel GETTESTCLASSLABELPANEL() {
        System.out.println("Z "+(classLabelPanel==null));
        return classLabelPanel;
    }

    public void TESTRESETSETTEST() {
        instance = null;
    }


    
}