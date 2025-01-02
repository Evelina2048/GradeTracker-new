package model;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusListener;

public class SetListeners {
    private FocusAdapter textfieldFocusListener;
    private static SetListeners instance;

    private FocusListener yesFocusListener;
    private FocusListener noFocusListener;

    // Private constructor to prevent instantiation
    private SetListeners() {}

    // Public method to provide access to the single instance
    public static SetListeners getInstance() {
        if (instance == null) {
            instance = new SetListeners();
        }
        return instance;
    }

    public void setDialogFocusListener(FocusAdapter thisTextfieldFocusListener) {
        textfieldFocusListener = thisTextfieldFocusListener;
    }

    public FocusAdapter getDialogFocusListener() {
        return textfieldFocusListener;
    }

    public void setYesFocusListener(FocusListener thisYesFocusListener) {
        yesFocusListener = thisYesFocusListener;
    }

    public FocusListener getYesFocusListener() {
        return yesFocusListener;
    }

    public void setNoFocusListener(FocusListener thisNoFocusListener) {
        noFocusListener = thisNoFocusListener;
    }

    public FocusListener getNoFocusListener() {
        return noFocusListener;
    }
}