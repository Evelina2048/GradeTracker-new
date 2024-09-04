package controller;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
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
    private Color lightgrayColor = Color.decode("#AFA2A2");
    
    public Creator() {
        textFieldPanel.setName("textfieldpanel in create");
        textFieldContainer.setName("textfieldcontainer");
        this.set = Set.getInstance();
        this.setState = SetState.getInstance();
        
        this.window = set.getWindow();
        colorFocusListener = new TextFieldColorFocusListener();
        //textFieldPanel.setBackground(lightgrayColor);
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
        else if (textboxCounter >= 30) {
            //Decorator decorate = new Decorator();
            System.out.println("umm hello?"); //for student classes...
            decorate.maximumAmountReachedPopup(); 
        }
        else if (my_type.equals("JLabel")) {
            JLabel toAddType = new JLabel(placeholder);
            textFieldPanel.add(toAddType);
        }

        System.out.println("textboxCounter: "+ textboxCounter);

        checkIfLoadedAndAction();
        setState.setTextFieldPanel(textFieldPanel);
        System.out.println("in create textbox. panel: "+setState.getTextFieldPanel().getComponentCount());
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
        System.out.println("this is accessed, right?");
        int componentsCount = container.getComponentCount();
        if (componentsCount > 0) {
            Component lastComponent = container.getComponent(componentsCount - 1);
            container.remove(lastComponent); 

            //TODO remove from classlist
            //set.removeClassFromClassList();

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

    public void setClassList() {
        //set.setClassList(classList);
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
            System.out.println("jlabel to be created");
            System.out.println("hi creating jlabel for "+placeholder+" which is loaded? "+boxLoaded);
            JLabel toAddType = new JLabel(placeholder);
            //window.setLayer(toAddType, JLayeredPane.PALETTE_LAYER);
            //JTextField toAddType = new JTextField(placeholder);

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
        //gradeTypePanel.setBackground(Color.pink);
        //gradeTypePanel.setBackground(lightgrayColor);
        windowFix();


        return northTypePanel;
    }

    // public String goIntoPanel(JPanel panel, int index) {
    //     Container container = panel;
    //     if (index >= container.getComponentCount()) { //check component is not null
    //         return "does not exist";
    //     }
    //     Component component = container.getComponent(index);
    //     if (component instanceof JTextField) {
    //             JTextField textField = (JTextField) component;
    //             String text = textField.getText();
    //             return text;
    //         }
    //     else if (component instanceof JPanel) {
    //             JPanel jpanel = (JPanel) component;
    //             String text = goIntoPanel(jpanel, 0);
    //             if (text != null) {
    //                 return text;
    //             }
    //         }
    //         System.out.println("none of these" +component.getClass().getName());
    //         return "something went wrong goIntoPanel";
    //     }

    // public JTextField goIntoPanelReturnTextbox(JPanel panel, int index) {
    //     Container container = panel;
    //     Component component = container.getComponent(index);
    //     if (component instanceof JTextField) {
    //             JTextField textField = (JTextField) component;
    //             return textField;
    //         } 
    //     else if (component instanceof JPanel) {
    //             JPanel jpanel = (JPanel) component;
    //             goIntoPanel(jpanel, 0);
    //         }
    //         System.out.println("none of these" +component.getClass().getName());
    //         return textField;
    //     }
        
    // public Boolean checkIfHasPlaceholder(JPanel textFieldPanel2) {
    //     JTextField textField = new JTextField();
    //     for (int i = 0; i < textFieldPanel2.getComponentCount()-1; i++) {
    //         Component component = textFieldPanel2.getComponent(i);
    //         if (component instanceof JTextField) {
    //             textField = (JTextField) component;
    //         } 
    //         else if (component instanceof JPanel) {
    //             textField = (JTextField) goIntoPanelReturnTextbox(textFieldPanel2, i);
    //         }
    //         if (set.getEmptiedState(textField) == false) {
    //             return false;
    //         }}
    //         return true;
    // }

    public void deleteMouseListeners(JTextField textField, int numberToDelete) {
        MouseListener[] listeners = textField.getMouseListeners();
        for (int i = 5; i >= 0; i--) {
            textField.removeMouseListener(listeners[i]);
        }
    }
}