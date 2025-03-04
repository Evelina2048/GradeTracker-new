package controller;

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

import model.SetState;
import model.SetUserInformation;
import model.SetList;

public class FileHandling {
        private SetState setState;
        private SetUserInformation setUserInformation;
        private SetList setList;

        public FileHandling() {
            setState = SetState.getInstance();
            setUserInformation = SetUserInformation.getInstance();
            setList = SetList.getInstance();
        }

        public ArrayList<String> readFileToList(String filePath) {
            String line;
            ArrayList<String> myList = new ArrayList<>() {
            };
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                while ((line = reader.readLine()) != null) {
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
                reader.close();
                return true;
            }

            catch (FileNotFoundException e) {
                return false;
            }

            catch (IOException e) {
                System.err.println("an error occured in create.java in isFileEmpty");
                return false;
            }

        }

        public boolean folderExists(String folderPath) {
            File folder = new File(folderPath);

            if (folder.exists() && folder.isDirectory()) {
                return true;
            } else {
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
            Creator create = new Creator();
            ArrayList<String> arrayList = readFileToList(filePath);
            JPanel bigPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            bigPanel.setName("bigPanel in loadtextboxes");

            JPanel classjlabel = create.typeBox(setList.getFinalClassList().get(setState.getClassListIndex()), "JLabel", true);

            bigPanel.add(classjlabel);
            for (int i = 0; i<arrayList.size(); i++) {
                if (arrayList.get(i).equals("Credits (optional)")) {
                    bigPanel.add(create.typeBox(arrayList.get(i), "JTextField", false));//added 12/124 so that if user left credits field blank, would load normally
                }
                else {
                    bigPanel.add(create.typeBox(arrayList.get(i), "JTextField", true));
                }
            }
            setState.setTextFieldPanel(bigPanel);
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

        public static void deleteClass(String filePathString) throws IOException {
            Path filePath = Paths.get(filePathString);
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

        public void deleteFromDeleteQueue() {
            ArrayList <String> deleteQueue = setUserInformation.getDeleteQueue();
            for (String queueObjectFilePath: deleteQueue) {
                try {
                    deleteClass(queueObjectFilePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}