package model;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Set {
    private JFrame window;
    private JButton saveButton;
    private Boolean newOrExistingHasChanged = false;
    private String filePath;

    //Below is for singleton design pattern
    // Static variable to hold the single instance of the class
    private static Set instance;

    private JLabel DEBUGMARKEDBOX;

    // Private constructor to prevent instantiation
    private Set() {}

    // Public method to provide access to the single instance
    public static Set getInstance() {
        if (instance == null) {
            instance = new Set();
        }
        return instance;
    }

    public void setWindow(JFrame myWindow) {
        window = myWindow;
    }

    public JFrame getWindow() {
        return window;
    }

    public void setNewOrExistingChanged(boolean changed) {
        newOrExistingHasChanged = changed;
    }

    public Boolean getNewOrExistingChanged() {
        return newOrExistingHasChanged;
    }

    public void setFilePath(String thisFilePath) {
        filePath = thisFilePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setDEBUGBOX(JLabel MYDEBUGMARKEDBOX) {
        DEBUGMARKEDBOX = MYDEBUGMARKEDBOX;
    }

    public JLabel getDEBUGBOX() {
        return DEBUGMARKEDBOX;
    }

    public void setCurrentSaveButton(JButton thisSaveButton) {
        saveButton = thisSaveButton;
    }

    public JButton getCurrentSaveButton() {
        return saveButton;
    }
}