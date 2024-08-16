package view.student;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import controller.Creator;
import controller.Decorator;
import controller.FileHandling;
import model.SetList;
import model.SetUserInformation;
import controller.CompositeActionListenerWithPriorities;
import controller.CreateButton;

public class PrintStudentGrades extends JFrame {
    private JFrame window;
    private JPanel backNextButtonsPanel;
    private Decorator decorate = new Decorator();
    private Creator create;
    private SetList setList;
    private SetUserInformation setUserInformation;
    private FileHandling fileHandler = new FileHandling();
    private CreateButton createButton = new CreateButton();

    public PrintStudentGrades(JFrame main, String studentOrTeacher, String existingOrNew) {
        System.out.println("sleep4444");
        System.out.println("in print student grades");
        CompositeActionListenerWithPriorities actionPriorities = CompositeActionListenerWithPriorities.getInstance();
        actionPriorities.setCurrentClass("PrintStudentGrades");
        studentStatCollectLaunch(main);
        //createNewTypeButton();
        JLabel instructionsWords = new JLabel("Grades");
        JPanel instructionsPanel = new JPanel();
        instructionsPanel = decorate.InstructionsPanelDecorate(instructionsPanel, instructionsWords );
        window.add(instructionsPanel);
        buttonSetUpAction(main, studentOrTeacher, existingOrNew);
        setList = SetList.getInstance();
        setUserInformation = SetUserInformation.getInstance();
        int gradeBoxTotal = 0;


        //go into file. (For loop of files)
        // for (int i = 0; i < setList.getFinalClassList().size();i++) {
        //     System.out.println("printgradestest "+setList.getFinalClassList().get(i));
        //     // class.readFileIntoList(pathtofilewithclasslistindex);
        // }
        ArrayList<ArrayList<String>> gradeList = new ArrayList<>();
        ArrayList<Integer> amountOfFinalGrade = new ArrayList<>();
        for (int classIndex = 0; classIndex < setList.getFinalClassList().size();classIndex++) {
            System.out.println("printgradestest "+setList.getFinalClassList().get(classIndex));
            String filePathForClass = setUserInformation.getPathToClassInformationFileWithChosenIndex(classIndex);
            ArrayList<String> allTextListForClass = fileHandler.readFileToList(filePathForClass);


            //grades starts at index 4
            //gradeList.add(allList.get(4));

            ArrayList<String> tempList = new ArrayList<String>(Arrays.asList(allTextListForClass.get(4).split(" ")));
            gradeList.add(tempList);

            ArrayList<Integer> percentageOfGradeList = new ArrayList<>();
            for (int percentageOfGradePanelIndex = 3; percentageOfGradePanelIndex < allTextListForClass.size(); percentageOfGradePanelIndex += 3) {
                percentageOfGradeList.add(Integer.parseInt(allTextListForClass.get(percentageOfGradePanelIndex)));
            }
            ArrayList<Integer> listOfAverages = new ArrayList<>();
            for (int boxPanelIndex = 5; boxPanelIndex < allTextListForClass.size(); boxPanelIndex += 3) { //for each grades box
                //gradeList.add(allList.get(j)); //add contents
                ArrayList<String> seperatedBySpaceListOfGrades = new ArrayList<>(Arrays.asList(allTextListForClass.get(boxPanelIndex).split(" ")));
                gradeList.add(seperatedBySpaceListOfGrades);
                for (int k = 0; k < gradeList.get(boxPanelIndex).size(); k++) {
                    //gradeBoxTotal += (int) gradeList[j][k];
                    gradeBoxTotal += Integer.parseInt(gradeList.get(boxPanelIndex).get(k));
                    //add to total
                }

                int average = gradeBoxTotal/allTextListForClass.size();
                listOfAverages.add(average);
                //int totalPercentageOfGrade =

            }

            for (int boxSetIndex = 0; boxSetIndex < gradeList.size(); boxSetIndex++) {
                amountOfFinalGrade.add((listOfAverages.get(boxSetIndex))*(percentageOfGradeList.get(boxSetIndex)));
            }

            /*for (stuff in gradelist)
             * //first box
             * //parse by spaces
             * //add to total
            */

            // for (int k = 0; k < gradeList.size(); k++) {
            // /* //first box
            //  * //parse by spaces
            //  * //add to total
            // */ }

            //ArrayList<String> seperatedBySpaceListOfGrades = new ArrayList<>(Arrays.asList(gradeList.get(allList.get(j)).split(" ")));
            // for (int k = 0; k < gradeList.size(); k++) {
                
            //     //add to total
            // }

            

        }

        float total = 0;
        for (int i = 0; i<amountOfFinalGrade.size(); i++) {
            total += amountOfFinalGrade.get(0);
        }

        ArrayList <Integer> gradeTypeList = setList.getGradeTypeList();
        int gradeTypeAmount = gradeTypeList.get(0);//setList.getCurrentClassIndex();
        JPanel allContainer = new JPanel(new GridLayout(2,gradeTypeAmount,5,5));

        


    }

    public void studentStatCollectLaunch(JFrame main) {
        System.out.println("in student grades");
        this.window = main;
        window.setTitle("PrintStudentGrades");
    }

    public void buttonSetUpAction(JFrame main, String studentOrTeacher, String existingOrNew) {
        JButton backButton = createButton.backButtonCreate();
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //backButtonAction(main, newUser, studentOrTeacher, existingOrNew);
            }
        });

        JButton saveButton = createButton.saveButtonCreate();
        JPanel saveButtonPanel = new JPanel();
        saveButtonPanel.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //nextButtonAction(existingOrNew, studentOrTeacher);
            }
        });

        JButton nextButton = createButton.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        nextButton.setEnabled(false);
        backNextButtonsPanel = createButton.makeBackNextButtonsPanel(backButtonPanel, saveButtonPanel, nextButtonPanel);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
    }

    //window.setVisible(true);


    // public static void main(String[] args) {
    //     JFrame main = new JFrame(); // Initialize your main JFrame here

    //     main.setSize(800, 600); // Set the size as needed
    //     main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set close operation
    //     main.setVisible(true); // Make the frame visible

    //     String studentOrTeacher = "Student"; // Initialize studentOrTeacher
    //     String existingOrNew = "Existing"; // Initialize existingOrNew

    //     SwingUtilities.invokeLater(new Runnable() {
    //         @Override
    //         public void run() {
    //             PrintStudentGrades edit = new PrintStudentGrades(main, studentOrTeacher, existingOrNew);
    //         }
    //     });
    // }


//North print grades
//For i in classes:
    //print class: percentageGrade, gpa (if applicable)

//overall gpa

    }