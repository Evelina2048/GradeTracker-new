import javax.swing.*;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import java.awt.*;

public class CustomRadioButtonUI extends BasicRadioButtonUI {
    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton button = (AbstractButton) c;
        ButtonModel model = button.getModel();

        if (!model.isEnabled()) {
            g.setColor(Color.RED); // Custom color for disabled state
            g.fillRect(0, 0, c.getWidth(), c.getHeight()); // Example: Custom background
            super.paint(g, c);
        } else {
            super.paint(g, c);
        }
    }
}
