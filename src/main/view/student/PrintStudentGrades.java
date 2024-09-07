package view.student;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import controller.Creator;
import controller.Decorator;
import controller.FileHandling;
import model.Set;
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
    private int whichCurrClassIndex;

    public PrintStudentGrades(JFrame main, String studentOrTeacher, String existingOrNew) {
        whichCurrClassIndex = 0;
        printStudentGradesLaunch();
    }

    public void printStudentGradesLaunch() {
        System.out.println("in print student grades");
        CompositeActionListenerWithPriorities actionPriorities = CompositeActionListenerWithPriorities.getInstance();
        actionPriorities.setCurrentClass("PrintStudentGrades");
        studentStatCollectLaunch();
        //createNewTypeButton();
        JLabel instructionsWords = new JLabel("Grades");
        JPanel instructionsPanel = new JPanel();
        instructionsPanel = decorate.InstructionsPanelDecorate(instructionsPanel, instructionsWords );
        window.add(instructionsPanel);
        buttonSetUpAction();
        setList = SetList.getInstance();
        setUserInformation = SetUserInformation.getInstance();
        int gradeBoxTotal = 0;

        ArrayList<ArrayList<String>> gradeList = new ArrayList<>();
        ArrayList<Integer> amountOfFinalGrade = new ArrayList<>();
        ArrayList<String> gradeTypeList = new ArrayList<>();
        ArrayList<Integer> listOfAverages = new ArrayList<>(); //8/21
        ArrayList<Integer> percentageOfGradeList = new ArrayList<>();
        //ArrayList<String> listOfAverages = new ArrayList<>();
        for (int classIndex = 0; classIndex < setList.getFinalClassList().size(); classIndex++) {
            System.out.println("printgradestest "+setList.getFinalClassList().get(classIndex));
            String filePathForClass = setUserInformation.getPathToClassInformationFileWithChosenIndex(classIndex);
            System.out.println("made it past first");
            ArrayList<String> allTextListForClass = fileHandler.readFileToList(filePathForClass);


            //grades starts at index 4
            //gradeList.add(allList.get(4));

            printArray(allTextListForClass);
            ArrayList<String> tempList = new ArrayList<String>(Arrays.asList(allTextListForClass.get(3).split(" ")));
            gradeList.add(tempList);

            //percentageOfGradeList = new ArrayList<>();
            for (int percentageOfGradePanelIndex = 3; percentageOfGradePanelIndex < allTextListForClass.size(); percentageOfGradePanelIndex += 3) {
                percentageOfGradeList.add(Integer.parseInt(allTextListForClass.get(percentageOfGradePanelIndex)));
            }

            gradeTypeList = new ArrayList<>();
            for (int gradeTypePanelIndex = 2; gradeTypePanelIndex < allTextListForClass.size(); gradeTypePanelIndex += 3) {
                gradeTypeList.add(allTextListForClass.get(gradeTypePanelIndex));
            }

            listOfAverages = new ArrayList<>();
            System.out.println("listofaverages -3 "+allTextListForClass);
            for (int boxPanelIndex = 3; boxPanelIndex < allTextListForClass.size(); boxPanelIndex += 3) { //for each grades box
                ArrayList<String> seperatedBySpaceListOfGrades = new ArrayList<>(Arrays.asList(allTextListForClass.get(boxPanelIndex).split(" ")));
                gradeList.add(seperatedBySpaceListOfGrades);
                System.out.println(("listofaverages -2.2 "+seperatedBySpaceListOfGrades.get(0)));
                for (int k = 0; k < seperatedBySpaceListOfGrades.size(); k++) {
                //     //gradeBoxTotal += (int) gradeList[j][k];
                    gradeBoxTotal += Integer.parseInt(seperatedBySpaceListOfGrades.get(k));
                //     //add to total
                }

                int average = gradeBoxTotal/allTextListForClass.size();
                System.out.println("listofaverages -2 "+average);
                listOfAverages.add(average);
                System.out.println("listofaverages -1 "+listOfAverages);

            }

            //for (int boxSetIndex = 0; boxSetIndex < gradeList.size(); boxSetIndex++) { //8/24
            for (int boxSetIndex = 0; boxSetIndex < listOfAverages.size(); boxSetIndex++) {
                System.out.println("listofaverages-.01 "+listOfAverages);
                System.out.println("listofaverages "+listOfAverages);
                System.out.println("listofaverages2 "+(listOfAverages.get(boxSetIndex)));
                System.out.println("listofaverages3 "+(percentageOfGradeList));
                System.out.println("listofaverages4 "+(percentageOfGradeList.get(boxSetIndex)));
                amountOfFinalGrade.add((listOfAverages.get(boxSetIndex))*(percentageOfGradeList.get(boxSetIndex)));
            }

        }

        float total = 0;
        for (int i = 0; i<amountOfFinalGrade.size(); i++) {
            total += amountOfFinalGrade.get(0);
        }

        ArrayList <Integer> gradeTypeNumberList = setList.getGradeTypeList();
        int gradeTypeAmount = gradeTypeNumberList.get(whichCurrClassIndex);//setList.getCurrentClassIndex();
        JPanel allContainer = new JPanel(new GridLayout(2,gradeTypeAmount,5,5)); //rows,cols
        JPanel borderContainer = new JPanel(new BorderLayout());
        borderContainer.add(new JLabel("placeholdType"),BorderLayout.NORTH);
        borderContainer.add(allContainer,BorderLayout.CENTER);

        //allContainer.add(new JTextField("allContainerBox"));
        Creator create = new Creator();
        allContainer.add(new JLabel("Average"));//create.createTextBox("Average", "JLabel", false));
        allContainer.add(new JLabel("Perc. of final grade"));//allContainer.add(create.createTextBox("Perc. of final grade", "JLabel", false));
        allContainer.add(new JLabel("Contribution"));//allContainer.add(create.createTextBox("Contribution", "JLabel", false));
        //allContainer.add(new JLabel(gradeTypeList.get(0)));//allContainer.add(create.createTextBox(gradeTypeList.get(0), "JLabel", false));

        for (int i = 0; i < gradeTypeNumberList.get(whichCurrClassIndex); i++) {
            System.out.println("i "+gradeTypeNumberList);
            System.out.println("i "+listOfAverages);
            System.out.println("i "+i);
            System.out.println("listofaverages.get(i) "+ listOfAverages.get(i));
            System.out.println("i "+listOfAverages.get(i));

            allContainer.add(new JLabel(String.valueOf(listOfAverages.get(i))));//allContainer.add(create.createTextBox(String.valueOf(listOfAverages.get(i)), "JLabel", false));
            allContainer.add(new JLabel(String.valueOf(percentageOfGradeList.get(i))));//allContainer.add(create.createTextBox(String.valueOf(percentageOfGradeList.get(i)), "JLabel", false));
            allContainer.add(new JLabel(String.valueOf(amountOfFinalGrade.get(i))));//allContainer.add(create.createTextBox(String.valueOf(amountOfFinalGrade.get(i)), "JLabel", false));
        }
        window.add(borderContainer);
        //window.add(allContainer);


        // JFrame window = Set.getInstance().getWindow();
        // Component[] windowComponents = window.getContentPane().getComponents();

        // for (Component windowComp : windowComponents) {
        //     System.out.println("compcount " + windowComponents.length + " classname "+ windowComp.getClass().getName() + " regname "+windowComp.getName() + " isvisible "+windowComp.isVisible());
        // }

        Component[] windowComponents3 = window.getContentPane().getComponents();
        int i = 0;
        for (Component windowComp : windowComponents3) {
            System.out.println("compcount " + i + " classname "+ windowComp.getClass().getName() + " regname "+windowComp.getName() + " isvisible "+windowComp.isVisible());
            i++;
        }

    }

    public void studentStatCollectLaunch() {
        System.out.println("in student grades");
        this.window = Set.getInstance().getWindow();
        window.setTitle("PrintStudentGrades");
    }

    public void buttonSetUpAction() {
        JButton backButton = createButton.backButtonCreate();
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //backButtonAction(main, newUser, studentOrTeacher, existingOrNew);
            }
        });

        //JButton saveButton = createButton.saveButtonCreate();
        // JPanel saveButtonPanel = new JPanel();
        // saveButtonPanel.add(saveButton);
        // saveButton.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         //nextButtonAction(existingOrNew, studentOrTeacher);
        //     }
        // });

        JButton nextButton = createButton.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        //nextButton.setEnabled(false); //commented 8/18
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                whichCurrClassIndex++;
                printStudentGradesLaunch();
            }
        });
        //backNextButtonsPanel = createButton.makeBackNextButtonsPanel(backButtonPanel, saveButtonPanel, nextButtonPanel);
        backNextButtonsPanel = createButton.makeBackNextButtonsPanel(backButtonPanel, new JPanel(), nextButtonPanel);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
    }

    public void printArray(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("item "+list.get(i));
        }

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