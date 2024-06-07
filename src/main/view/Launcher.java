package main.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Set set;
                set = Set.getInstance();
                set.setWindow(new JFrame());
                MainWindow main = new MainWindow();
                main.show(0,0);
            }
        });
    }
}

