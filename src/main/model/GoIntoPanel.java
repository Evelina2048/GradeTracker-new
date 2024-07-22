package main.model;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.Container;

import main.model.Set;


public class GoIntoPanel {
    private Set set;
    private JTextField textField = new JTextField();
    
    public GoIntoPanel() {
        this.set = Set.getInstance();
    }

    public String goIntoPanel(JPanel panel, int index) {
        Container container = panel;
        if (index >= container.getComponentCount()) { //check component is not null
            return "does not exist";
        }
        Component component = container.getComponent(index);
        if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                String text = textField.getText();
                return text;
            } 
        else if (component instanceof JPanel) {
                JPanel jpanel = (JPanel) component;
                String text = goIntoPanel(jpanel, 0);
                if (text != null) {
                    return text;
                }
            }
            System.out.println("none of these" +component.getClass().getName());
            return "something went wrong goIntoPanel";
    }

    public JTextField goIntoPanelReturnTextbox(JPanel panel, int index) {
        Container container = panel;
        Component component = container.getComponent(index);
        if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                return textField;
            } 
        else if (component instanceof JPanel) {
                JPanel jpanel = (JPanel) component;
                goIntoPanel(jpanel, 0);
            }
            System.out.println("none of these" +component.getClass().getName());
            return textField;
        }
        
    public Boolean checkIfHasPlaceholder(JPanel textFieldPanel2) {
        JTextField textField = new JTextField();
        for (int i = 0; i < textFieldPanel2.getComponentCount()-1; i++) {
            Component component = textFieldPanel2.getComponent(i);
            if (component instanceof JTextField) {
                textField = (JTextField) component;
            } 
            else if (component instanceof JPanel) {
                textField = (JTextField) goIntoPanelReturnTextbox(textFieldPanel2, i);
            }
            if (set.getEmptiedState(textField) == false) {
                return false;
            }}
            return true;
    }
}