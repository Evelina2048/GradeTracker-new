package model;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.Container;


public class GoIntoPanel {
    private SetState setState;
    
    public GoIntoPanel() {
        Set.getInstance();
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
        if (container.getComponentCount() >= 1) {
            Component component = container.getComponent(index);

            if (component instanceof JTextField) {
                    JTextField textField = (JTextField) component;
                    return textField;
                }
            else if (component instanceof JPanel) {
                    JPanel jpanel = (JPanel) component;
                    goIntoPanelReturnTextbox(jpanel, 0);
                }
            else if (component instanceof JLabel) {
                return null;
            }

            else {
                System.out.println("im confused " + component.getClass().getName());
            }

        if (component instanceof JPanel) {
            JPanel jpanel = (JPanel) component;
            return goIntoPanelReturnTextbox(jpanel, 0);
        }

    }
    return new JTextField("issue in go Into Panel Return Textbox");
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

    public String goIntoPanelReturnTypeOfFirstComponentInPanel(JPanel panel) {
        Container container = panel;
        if (container.getComponentCount() >= 1) {
            Component component = container.getComponent(0);

        if (component instanceof JTextField) {
                System.out.println("should print last");
                JTextField textField = (JTextField) component;
                return textField.getClass().getName();
            } 

        else if (component instanceof JLabel) {
            JLabel jlabel = (JLabel) component;

            return jlabel.getClass().getName();
        }
        else if (component instanceof JPanel) {
                //System.out.println("should print first");
                return goIntoPanelReturnTypeOfFirstComponentInPanel((JPanel) panel.getComponent(0));
            }

        else {
            System.out.println("im confused in return type" + component.getClass().getName());
        }
        }
        return "Some issue in goIntoPanelReturnType";
}
}