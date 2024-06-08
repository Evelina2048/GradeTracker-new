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
        private Set set;

        public FileHandler() {
            set = Set.getInstance();
        }

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


        public JPanel loadTextboxes(String filePath) {
            Creator creator = new Creator();
            ArrayList<String> arrayList = readFileToList(filePath);
            JPanel bigPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            System.out.println("arrayList"+arrayList);
            for (int i = 0; i<arrayList.size(); i++) {
                bigPanel.add(creator.boxCreate(arrayList.get(i), "JTextField", true));

            }
            set.setTextFieldPanel(bigPanel);
            return bigPanel;
    
        }
}