package main.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import main.model.Set;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Set set;
                set = Set.getInstance();
                JFrame window = new JFrame();
                set.setWindow(window);
                // MainWindow main = new MainWindow();
                // main.show(0,0);
                NewUser newUser = new NewUser();
                newUser.showWindow();
            }
        });
    }
}

