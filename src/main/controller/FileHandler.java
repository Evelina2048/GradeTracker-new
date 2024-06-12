package main.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.FlowLayout;

import main.model.Set;

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


        public boolean fileExists(String filePath) {
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

        public boolean fileIsNotEmpty(String filePath) {
            Path path = Paths.get(filePath);
            try {
                if(Files.size(path) > 0) {
                    return true;
                }

                else if(Files.size(path) <= 0) {
                    return false;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("something went wrong in the file is not empty function");
            return true;
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