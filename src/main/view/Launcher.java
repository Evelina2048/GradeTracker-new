package main.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Launcher {
    private Set set;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Set set = new Set();
                set.setWindow(new JFrame());
                MainWindow main = new MainWindow(new Set());
                main.show(0,0);
            }
        });
    }
}

