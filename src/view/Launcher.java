package src.view;

import javax.swing.SwingUtilities;
//import src.MainWindow;

public class Launcher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow main = new MainWindow();
                main.show(0,0);
            }
        });
    }
}

