package src.view;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.BorderLayout;

public class NewUser {
    JFrame window;
    public NewUser (String buttonName, int windowX, int windowY){
        System.out.println("Hello "+ buttonName);
        windowSetUp(windowX, windowY);
        buttonSetUp();
        }
    
    private void buttonSetUp() {
        JButton backButton;
        JButton nextButton;
        JPanel backNextButtonsPanel;
        //buttons
        backNextButtonsPanel = new JPanel(new BorderLayout());
        backButton = new JButton("< Back");
        backNextButtonsPanel.add(backButton, BorderLayout.WEST);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainWindow main = new MainWindow();
                main.show();
                hideWindow();
            }
        });
        
        //next
        nextButton = new JButton("Next >");
        backNextButtonsPanel.add(nextButton, BorderLayout.EAST);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
        
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Frame2 next hit ");
            }
        });
    }

    private void windowSetUp(int windowX, int windowY) {
        //window set up
        window = new JFrame();
        window.setTitle("New User?");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setSize(800, 500);
        window.setLocation(windowX, windowY);
        showWindow();
    }

    private void showWindow() {
        window.setVisible(true);
    }

    private void hideWindow() {
        window.setVisible(false);
    }
}