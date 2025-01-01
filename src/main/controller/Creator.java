package controller;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.util.ArrayList;

import model.Set;
import model.SetState;


public class Creator {
    private JFrame window;
    private JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private Set set;
    private SetState setState;
    private int textboxCounter;
    private JTextField textField = new JTextField();
    private ArrayList<String>textFieldPanelText = new ArrayList<>();
    private JPanel textFieldContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private boolean loaded;
    private CreateButton createButton = new CreateButton();
    private TextFieldColorFocusListener colorFocusListener;

    public Creator() {
        textFieldPanel.setName("textfieldpanel in create");
        textFieldContainer.setName("textfieldcontainer");
        this.set = Set.getInstance();
        this.setState = SetState.getInstance();

        this.window = set.getWindow();
        colorFocusListener = new TextFieldColorFocusListener();
    }

    public JTextField createTextBox(String placeholder, String my_type, Boolean boxLoaded) {
        textFieldPanel.setName("textfieldpanel in create");
        loaded = boxLoaded;
        int width = 144;
        int height = 50;
        Decorator decorate = new Decorator();
        if (textboxCounter < 30 && my_type.equals("JTextField")) {
            textboxCounter++;
            textField = decorate.decorateTextBox(placeholder);
            createButton.addDocumentListenerForSaving(textField);
            textFieldPanel.add(textField); 
            textFieldPanelText.add(textField.getText());
            textField.setPreferredSize(new Dimension(width, height));
            window.add(textFieldPanel);
            colorFocusListener.textFieldFocusListener(textField, placeholder);
            windowFix();
        }
        else if (textboxCounter >= 27) {
            //for student classes
            decorate.maximumAmountReachedPopup();
        }
        else if (my_type.equals("JLabel")) {
            JLabel toAddType = new JLabel(placeholder);
            textFieldPanel.add(toAddType);
        }

        checkIfLoadedAndAction();
        setState.setTextFieldPanel(textFieldPanel);
        return textField;
    }

    private void checkIfLoadedAndAction() {
        if (loaded) {
            setState.setEmptiedState(textField, true);
            setState.setLoadedState(textField, true);

            textField.setForeground(Color.lightGray);
        }
        else if (loaded == false) {
            setState.setLoadedState(textField, false);
        }
    }

    public void setTextFieldContainer(JPanel thisTextFieldPanel) {
        textFieldPanel = thisTextFieldPanel;
    }

    public JPanel getTextFieldContainer() {
        return textFieldPanel;
    }

    public void deleteTextBox(JPanel container) {
        int componentsCount = container.getComponentCount();
        if (componentsCount > 0) {
            Component lastComponent = container.getComponent(componentsCount - 1);
            container.remove(lastComponent); 

            windowFix();
            textboxCounter--;
        }
    }

    public void windowFix() {
        window.revalidate(); 
        window.repaint();
    }

    public void hideContainer() {
        textFieldContainer.setVisible(false);
        textFieldPanel.setVisible(false);
    }

    public void removeContainer() {
        JFrame window = set.getWindow();

        window.remove(textFieldContainer);
        window.remove(textFieldPanel);
    }

    public JPanel typeBox(String placeholder, String my_type, Boolean boxLoaded) {
        hideContainer(); //needed unless jlabels will be missing
        loaded = boxLoaded;
        JPanel northTypePanel = new JPanel(new BorderLayout());
        JPanel gradeTypePanel = new JPanel(new BorderLayout()); //second border layout so things not cut out


        if (my_type.equals("JTextField")) {
            JTextField toAddType = createTextBox(placeholder, "JTextField", loaded);
            gradeTypePanel.add(toAddType);
        }

        else if(my_type.equals("JLabel")) {
            JLabel toAddType = new JLabel(placeholder);

            if (setState.getTextFieldFont() != null) {
                Font currentTextFieldFont = setState.getTextFieldFont();
                Font boldFont = new Font(currentTextFieldFont.getFontName(), Font.BOLD, currentTextFieldFont.getSize());
                toAddType.setFont(boldFont);
            }

            else {
                System.out.println("TEXTFIELDFONTISNULL");
            }
            toAddType.setForeground(Color.gray);

            toAddType.setVisible(true);
            gradeTypePanel.add(toAddType);
        }
        gradeTypePanel.setPreferredSize(new Dimension( 155,50));
        northTypePanel.add(gradeTypePanel, BorderLayout.NORTH);
        windowFix();


        return northTypePanel;
    }

    public void deleteMouseListeners(JTextField textField, int numberToDelete) {
        MouseListener[] listeners = textField.getMouseListeners();
        for (int i = 5; i >= 0; i--) {
            textField.removeMouseListener(listeners[i]);
        }
    }
}