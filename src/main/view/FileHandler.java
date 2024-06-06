package main.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class FileHandler {

        public ArrayList<String> readFileToList(String filePath) {
            String line;
            ArrayList<String> myList = new ArrayList<>() {
                
            };
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                while ((line = reader.readLine()) != null) {
                    System.out.println("printing file..."+ line);
                    myList.add(line);                 
                }
                reader.close();
            }
            catch (IOException e) { 
                e.printStackTrace();
            }
            
            return myList;
        }


        public boolean fileIsNotEmpty(String filePath) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                System.out.println("File exists"); 
                reader.close();
                return true;
            }

            catch (FileNotFoundException e) {
                System.out.println("File does not exist"); 
                return false;
            }
    
            catch (IOException e) {
                // long fileSize = Files.size(path);
                // if (fileSize != 0) {
                //     return false;
                // }
                System.err.println("an error occured in creator.java in isFileEmpty");
                return false;
            }
        
    }


        public JPanel loadTextboxes(JFrame window, String filePath, Set set) {
            Creator creator = new Creator(set);
            ArrayList<String> arrayList = readFileToList(filePath);
            JPanel bigPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            System.out.println("arrayList"+arrayList);
            for (int i = 0; i<arrayList.size(); i++) {
                //creator.createTextBox(window, arrayList.get(i), 10, 50);
                bigPanel.add(creator.boxCreate(window, arrayList.get(i), "JTextField", true));

            }
            set.setTextFieldPanel(bigPanel);
            //set.setEmptiedState(textField);
            //creator.windowFix(window);
            return bigPanel;

            // Creator creator = new Creator(set);
            // JPanel bigPanel = new JPanel();
            // JPanel containerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            // bigPanel.add(creator.boxCreate(window, "testing", "JTextField"));
            // bigPanel.add(creator.boxCreate(window, "test", "JTextField"));
            // bigPanel.add(creator.boxCreate(window, "tester", "JTextField"));
            // bigPanel.add(creator.boxCreate(window, "tests", "JTextField"));
            // bigPanel.add(creator.boxCreate(window, "testy", "JTextField"));
            // return bigPanel;
    
        }
}