package controller;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Dimension;
import java.awt.FlowLayout;

import model.Set;
import model.SetList;
import model.SetState;

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
        saveButton = new JButton("Save ");
        Set.getInstance().setCurrentSaveButton(saveButton); //11/6

        if ((CompositeActionListenerWithPriorities.getInstance().getCurrentClass()!= null) && CompositeActionListenerWithPriorities.getInstance().getCurrentClass().equals("StudentStatCollect")) {
            saveButton.setText("Save" + SetList.getInstance().getFinalClassList().get(SetState.getInstance().getClassListIndex()));
        }

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
            setState.setEmptiedState(textField, true);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            System.out.println("changedUpdate");
        }
        });
    }

    public void saveButtonEnable() {
        if (set.getCurrentSaveButton() != null) {
            set.getCurrentSaveButton().setEnabled(true);
        }

        else {
            System.err.println(" null");
        }
    }

    public void removeFromWindow() {
        set.getWindow().remove(backNextButtonsPanel);
        set.getWindow().remove(saveButton);
        set.removeSave();
    }

}