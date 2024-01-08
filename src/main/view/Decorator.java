package main.view;

import javax.swing.JPanel;

public class Decorator {
    
    public Decorator() {

    }

    public void hideWindow(JPanel instructionsPanel, JPanel choicesPanel, JPanel backNextButtonsPanel) {
        instructionsPanel.setVisible(false);
        choicesPanel.setVisible(false);
        backNextButtonsPanel.setVisible(false);
    
    }

}