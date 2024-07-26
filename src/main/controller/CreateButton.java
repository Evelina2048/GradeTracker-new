package main.controller;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Dimension;
import java.awt.FlowLayout;

import main.model.Set;
import main.model.SetState;

public class CreateButton {
    private JPanel backNextButtonsPanel;
    private JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JButton saveButton;
    private JPanel textFieldContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private Set set;
    private SetState setState;

    public CreateButton() {
        textFieldPanel.setName("textFieldPanel");
        textFieldContainer.setName("textFieldContainer");
        this.set = Set.getInstance();
        this.setState = SetState.getInstance();
    }

    public JButton backButtonCreate() {
        JButton backButton;
        backButton = new JButton("< Back");
        backButton.setPreferredSize(new Dimension(87, 29));

        return backButton;
        
    }

    public JButton saveButtonCreate() {
        saveButton = new JButton("Save");
        set.setCurrentSaveButton(saveButton);

        return saveButton;
    }

    public JButton nextButtonCreate() {
        JButton nextButton;

        nextButton = new JButton("Next >");
        nextButton.setPreferredSize(new Dimension(87, 29));

        return nextButton;
    }

    public JPanel makeBackNextButtonsPanel(JPanel backButton, JPanel saveButtonPanel, JPanel nextButton) {
        backNextButtonsPanel = new JPanel(new BorderLayout());
        backNextButtonsPanel.add(backButton, BorderLayout.WEST);
        backNextButtonsPanel.add(saveButtonPanel, BorderLayout.CENTER);
        backNextButtonsPanel.add(nextButton, BorderLayout.EAST);
        backNextButtonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        return backNextButtonsPanel;
    }

    
    // public void setTextFieldContainer(JPanel thisTextFieldPanel) {
    //     textFieldPanel = thisTextFieldPanel;
    // }

    // public JPanel getTextFieldContainer() {
    //     return textFieldPanel;
    // }

    // public void hideContainer() {
    //     textFieldContainer.setVisible(false);
    //     textFieldPanel.setVisible(false);
    // }

    public void setTextFieldPanel(JPanel myPanel) {
        textFieldPanel = myPanel;
    }

    public void addDocumentListenerForSaving(JTextField textField) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            saveButtonEnable(); 
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            System.out.println("removeUpdate/// ");
            //set.setEmptiedState(true);
            System.out.println(1111.5);
            setState.setEmptiedState(textField, true);
            //textField.getParent();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            System.out.println("changedUpdate");
        }
        });
    }

    public void saveButtonEnable() {
        if (saveButton != null) {
        set.getCurrentSaveButton().setEnabled(true);
        }

        else {
            System.err.println("savebutton is null");
        }
        //saveButton.setEnabled(true);
    }

}