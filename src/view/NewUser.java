package src.view;
import javax.swing.JFrame;
import java.awt.BorderLayout;

public class NewUser {
    JFrame window;
    public NewUser (String buttonName){
        System.out.println("Hello "+ buttonName);
        windowSetUp();
        }
    
    private void windowSetUp() {
        //window set up
        window = new JFrame();
        window.setTitle("New User?");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setSize(800, 500);
        window.setLocationRelativeTo(null);
        showWindow();
        }

    private void showWindow() {
        window.setVisible(true);
    }
}