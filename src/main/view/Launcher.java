package view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Color;
import model.Set;
import view.NewUser;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Set set;
                set = Set.getInstance();
                JFrame window = new JFrame();
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

