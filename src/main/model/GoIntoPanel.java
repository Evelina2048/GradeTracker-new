package model;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.Container;

import model.Set;


public class GoIntoPanel {
    private Set set;
    private SetState setState;

    private JTextField textField = new JTextField();
    
    public GoIntoPanel() {
        this.set = Set.getInstance();
        this.setState = SetState.getInstance();
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
                System.out.println("should print last");
                JTextField textField = (JTextField) component;
                System.out.println("textfield text and stuff "+textField.getText()+ " emptiedstate "+setState.getEmptiedState(textField));
                return textField;
            } 
        else if (component instanceof JPanel) {
            System.out.println("should print first");
                JPanel jpanel = (JPanel) component;
                //TODO was goIntoPanel(jpanel, 0);
                goIntoPanelReturnTextbox(jpanel, 0);
            }
        
        else {
            System.out.println("im confused " + component.getClass().getName());
        }
            //System.out.println("none of these" +component.getClass().getName());
        //return textField;
        JPanel jpanel = (JPanel) component;
        return goIntoPanelReturnTextbox(jpanel, 0);
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
            if (setState.getEmptiedState(textField) == false) {
                return false;
            }}
            return true;
    }
}