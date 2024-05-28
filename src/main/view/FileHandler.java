package main.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {

        public void readFile(String filePath) {
            String line;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                while ((line = reader.readLine()) != null) {
                    System.out.println("printing file..."+ line);
                }
                }
            catch (IOException e) { 
                e.printStackTrace();
            }
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
