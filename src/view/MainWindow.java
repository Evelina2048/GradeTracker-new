package src.view;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;


public class MainWindow {
//initialization
private JFrame window;
JRadioButton teacherButton;
JRadioButton studentButton;




public MainWindow() {
    window = new JFrame();
    window.setTitle("Launcher");
    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    window.setLayout(new FlowLayout());

    teacherButton = new JRadioButton("Teacher");
    studentButton = new JRadioButton("Student");

    ButtonGroup teacherStudentGroup = new ButtonGroup();
    teacherStudentGroup.add(teacherButton);
    teacherStudentGroup.add(studentButton);

    window.add(teacherButton);
    window.add(studentButton);


    window.setSize(800, 500);
    window.setLocationRelativeTo(null);

}

public void show() {
   window.setVisible(true);
}

}