package main.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Color;
import main.model.Set;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Set set;
                set = Set.getInstance();
                JFrame window = new JFrame();
                //
                //Color windowColor = Color.decode("#AFA2A2");
                //choicesPanel.setBackground(choicesPanelColor);
                //
                //window.setBackground(Color.pink);//windowColor);
                window.setResizable(false);
                set.setWindow(window);
                // MainWindow main = new MainWindow();
                // main.show(0,0);
NewUser newUser = new NewUser();
                newUser.showWindow();
            }
        });
    }
}

