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
ButtonGroup teacherStudentGroup;




public MainWindow() {
    //window set up
    window = new JFrame();
    window.setTitle("Launcher");
    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    window.setLayout(new FlowLayout());
    window.setSize(800, 500);
    window.setLocationRelativeTo(null);

    //teacher and student radiobuttons initialize
    teacherButton = new JRadioButton("Teacher");
    studentButton = new JRadioButton("Student");
    teacherStudentGroup = new ButtonGroup();

    //teacher and student radiobuttons add to group and make visible
    teacherStudentGroup.add(teacherButton);
    teacherStudentGroup.add(studentButton);
    window.add(teacherButton);
    window.add(studentButton);

}

public void show() {
   window.setVisible(true);
}

}