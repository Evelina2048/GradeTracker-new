package main.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
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
                    //System.out.println("printing file..."+ line);
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
                //System.out.println("File exists"); 
                reader.close();
                return true;
            }

            catch (FileNotFoundException e) {
                //System.out.println("File does not exist"); 
                return false;
            }
    
            catch (IOException e) {
                System.err.println("an error occured in creator.java in isFileEmpty");
                return false;
            }
        
        }

        public boolean folderExists(String folderPath) {
            File folder = new File(folderPath);

            if (folder.exists() && folder.isDirectory()) {
                //System.out.println("The directory exists.");
                return true;
            } else {
                //System.out.println("The directory does not exist.");
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
            bigPanel.setName("bigPanel in loadtextboxes");
            System.out.println("arrayList"+arrayList);
            bigPanel.add(creator.typeBox(set.getFinalClassList().get(set.getClassListIndex())+"CAT", "JLabel", true));
            for (int i = 0; i<arrayList.size(); i++) {
                bigPanel.add(creator.typeBox(arrayList.get(i), "JTextField", true));
            }
            set.setTextFieldPanel(bigPanel);
            return bigPanel;
    
        }

        public void writeArrayListToFile(String filePath, ArrayList<String> lines) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void hideLoadedBoxes() {
            //bigPanel.setVisible(false);
        }

        public static void deleteClass(Path filePath) throws IOException {
            Files.walkFileTree(filePath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
    
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }

        //public void 
}