package main.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Launcher {
    private Set set;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Set set = new Set();
                MainWindow main = new MainWindow(new JFrame(), new Set());
                main.show(0,0);
            }
        });
    }
}

