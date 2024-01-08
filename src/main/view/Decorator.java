package main.view;

import javax.swing.JPanel;

public class Decorator {
    private int windowX;
    private int windowY;
    
    public Decorator() {

    }

    public void setWindowX(int newWindowX) {
        windowX = newWindowX;
    }

    public void setWindowY(int newWindowY) {
        windowY = newWindowY;
    }

    // public void showWindow(JFrame window, int windowX, int windowY) {
    //     if (windowX != 0 && windowY != 0) {
    //         window.setLocation(windowX, windowY);
    //         setWindowX(windowX);
    //         setWindowY(windowY);
     
    //     }
     
    //     else {
    //      window.setLocationRelativeTo(null);
    //     }
     
    //      window.setVisible(true);
    //  }

    public void hideWindow(JPanel instructionsPanel, JPanel choicesPanel, JPanel backNextButtonsPanel) {
        instructionsPanel.setVisible(false);
        choicesPanel.setVisible(false);
        backNextButtonsPanel.setVisible(false);
    
    }

}