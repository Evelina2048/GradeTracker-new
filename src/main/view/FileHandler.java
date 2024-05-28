package main.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
            }
            catch (IOException e) { 
                e.printStackTrace();
            }
            return myList;
        }


        public boolean isFileEmpty(String filePath) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                System.out.println("File exists"); 
                return false;
            }

            catch (FileNotFoundException e) {
                System.out.println("File does not exist"); 
                return true;
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
}
