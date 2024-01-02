package main.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
//import src.MainWindow;

public class Launcher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow main = new MainWindow(new JFrame());
                main.show(0,0);
            }
        });
    }
}

