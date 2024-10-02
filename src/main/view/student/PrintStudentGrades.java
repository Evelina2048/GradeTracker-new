package view.student;

import javax.swing.JButton;
import javax.swing.JFrame;

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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class PrintStudentGrades extends JFrame {
    private JFrame window;
    private JPanel backNextButtonsPanel;
    private JPanel borderContainer;
    private Decorator decorate = new Decorator();
    private SetList setList;
    private SetUserInformation setUserInformation;
    private FileHandling fileHandler = new FileHandling();
    private CreateButton createButton = new CreateButton();
    private int whichCurrClassIndex;

    private ArrayList<String> gradeTypeList;
    private ArrayList<String> allTextListForClass;
    private ArrayList<Integer> percentageOfGradeList;
    private ArrayList<Integer> listOfAverages;
    private ArrayList<ArrayList<String>> gradeList;
    private ArrayList<Integer> amountOfFinalGrade;
    private CompositeActionListenerWithPriorities actionPriorities;

    public PrintStudentGrades(JFrame main, String studentOrTeacher, String existingOrNew) {
        whichCurrClassIndex = 0;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Set set = Set.getInstance();
        JFrame window = set.getWindow();
        window.removeWindowListener(set.getCurrentWindowClosing());
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        actionPriorities = CompositeActionListenerWithPriorities.getInstance();
        printStudentGradesLaunch();

    }

    public void printStudentGradesLaunch() {
        actionPriorities.setCurrentClass("PrintStudentGrades");
        studentStatCollectLaunch();
        JLabel instructionsWords = new JLabel("Grades");
        JPanel instructionsPanel = new JPanel();
        instructionsPanel = decorate.InstructionsPanelDecorate(instructionsPanel, instructionsWords );
        window.add(instructionsPanel);
        buttonSetUpAction();
        setList = SetList.getInstance();
        setUserInformation = SetUserInformation.getInstance();

        gradeList = new ArrayList<>();
        amountOfFinalGrade = new ArrayList<>();
        gradeTypeList = new ArrayList<>();
        listOfAverages = new ArrayList<>();
        percentageOfGradeList = new ArrayList<>();
        for (int classIndex = 0; classIndex < setList.getFinalClassList().size(); classIndex++) { //go through classes
            System.out.println("printgradestest "+setList.getFinalClassList().get(classIndex));
            String filePathForClass = setUserInformation.getPathToClassInformationFileWithChosenIndex(classIndex);
            System.out.println("made it past first");
            allTextListForClass = fileHandler.readFileToList(filePathForClass);


            //grades starts at index 4

            printArray(allTextListForClass);

            ArrayList<String> tempList = new ArrayList<String>(Arrays.asList(allTextListForClass.get(3).split(" ")));
            gradeList.add(tempList); //get grades sect for class

            makePercentageOfGradeList();

            makeGradeTypeList();

            makeListOfAveragesList();

            makeAmountOfFinalGradeList();

        }

        float total = getGradeTotal();

        addEverythingToWindow(total);
        //window.add(allContainer);


        
        // Component[] windowComponents3 = window.getContentPane().getComponents();
        // int i = 0;
        // for (Component windowComp : windowComponents3) {
        //     System.out.println("compcount " + i + " classname "+ windowComp.getClass().getName() + " regname "+windowComp.getName() + " isvisible "+windowComp.isVisible());
        //     i++;
        // }

    }

    private void addEverythingToWindow(float total) {
        Border blackline = BorderFactory.createLineBorder(Color.black);
        ArrayList <Integer> gradeTypeNumberList = setList.getGradeTypeList();

        int gradeTypeAmount = gradeTypeNumberList.get(whichCurrClassIndex);
        JPanel allContainer = new JPanel(new GridLayout(2,gradeTypeAmount,5,5)); //rows,cols
        allContainer.setName("allContainr");
        borderContainer = new JPanel(new BorderLayout());

        JLabel placeholdType = new JLabel("placeholdType");
        placeholdType.setBorder(blackline);
        borderContainer.add(placeholdType,BorderLayout.NORTH);


        borderContainer.add(allContainer,BorderLayout.CENTER);

        JLabel finalgrade = new JLabel("Final Grade: "+total);
        finalgrade.setBorder(blackline);
        borderContainer.add(finalgrade,BorderLayout.SOUTH);

        Creator create = new Creator();

        JLabel average = new JLabel("Average");
        average.setBorder(blackline);
        allContainer.add(average);

        JLabel percgrade = new JLabel("Perc. of final grade");
        percgrade.setBorder(blackline);
        allContainer.add(percgrade);

        JLabel contributiongrade = new JLabel("Contribution");
        contributiongrade.setBorder(blackline);
        allContainer.add(contributiongrade);

        for (int i = 0; i < gradeTypeNumberList.get(whichCurrClassIndex); i++) {
            System.out.println("i "+gradeTypeNumberList);
            System.out.println("i "+listOfAverages);
            System.out.println("i "+i);
            System.out.println("listofaverages.get(i) "+ listOfAverages.get(i));
            System.out.println("i "+listOfAverages.get(i));

            System.out.println("listofavs "+listOfAverages);

            JLabel listOfAveragesLabel = new JLabel(String.valueOf(listOfAverages.get(i)));
            listOfAveragesLabel.setBorder(blackline);
            allContainer.add(listOfAveragesLabel);

            JLabel percentageOfGradeLabel = new JLabel(String.valueOf(percentageOfGradeList.get(i)));
            percentageOfGradeLabel.setBorder(blackline);
            allContainer.add(percentageOfGradeLabel);

            //percentageOfGradeLabel.setBorder(blackline);
            allContainer.add(new JLabel("heyo "+(String.valueOf(percentageOfGradeList.get(i)))));
        }
        window.add(borderContainer);
    }

    private float getGradeTotal() {
        float total = 0;
        //for (int i = 0; i<amountOfFinalGrade.size(); i++) {
            total += amountOfFinalGrade.get(0);
        //}
        return total;
    }

    private void makeAmountOfFinalGradeList() {
        //for (int boxSetIndex = 0; boxSetIndex < gradeList.size(); boxSetIndex++) { //8/24
        for (int boxSetIndex = 0; boxSetIndex < listOfAverages.size(); boxSetIndex++) {
            System.out.println("listofaverages-.01 "+listOfAverages);
            System.out.println("listofaverages "+listOfAverages);
            System.out.println("listofaverages2 "+(listOfAverages.get(boxSetIndex)));
            System.out.println("listofaverages3 "+(percentageOfGradeList));
            System.out.println("listofaverages4 "+(percentageOfGradeList.get(boxSetIndex)));
            System.out.println("8888 instead "+ "boxsetindex "+boxSetIndex+" list of averages "+listOfAverages+" percentageOfGradeList "+percentageOfGradeList);
            amountOfFinalGrade.add((listOfAverages.get(boxSetIndex))*(percentageOfGradeList.get(boxSetIndex)/100));
        }
    }

    private void makeListOfAveragesList() {
        listOfAverages = new ArrayList<>();
        int gradeBoxTotal = 0;
        System.out.println("listofaverages -3 "+allTextListForClass);
        for (int boxPanelIndex = 3; boxPanelIndex < allTextListForClass.size(); boxPanelIndex += 3) { //for each grades box
            ArrayList<String> seperatedBySpaceListOfGrades = new ArrayList<>(Arrays.asList(allTextListForClass.get(boxPanelIndex).split(" ")));
            gradeList.add(seperatedBySpaceListOfGrades);
            System.out.println(("listofaverages -2.2 "+seperatedBySpaceListOfGrades.get(0)));
            for (int k = 0; k < seperatedBySpaceListOfGrades.size(); k++) {
                gradeBoxTotal += Integer.parseInt(seperatedBySpaceListOfGrades.get(k));
            }

            int average = gradeBoxTotal/seperatedBySpaceListOfGrades.size();
            System.out.println("screen "+seperatedBySpaceListOfGrades + "size "+seperatedBySpaceListOfGrades.size());
            System.out.println("listofaverages -2 "+average);
            listOfAverages.add(average);
            System.out.println("listofaverages -1 "+listOfAverages);

        }
    }

    private void makeGradeTypeList() {
        gradeTypeList = new ArrayList<>();
        for (int gradeTypePanelIndex = 2; gradeTypePanelIndex < allTextListForClass.size(); gradeTypePanelIndex += 3) {
            gradeTypeList.add(allTextListForClass.get(gradeTypePanelIndex));
        }
    }

    private void makePercentageOfGradeList() {
        for (int percentageOfGradePanelIndex = 2; percentageOfGradePanelIndex < allTextListForClass.size(); percentageOfGradePanelIndex += 3) {
            percentageOfGradeList.add(Integer.parseInt(allTextListForClass.get(percentageOfGradePanelIndex)));
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
                hideWindow();
                StudentStatCollect studentStatCollect = new StudentStatCollect();
                studentStatCollect.addLoadedBoxes();
                studentStatCollect.studentStatCollectLaunch();
                actionPriorities.setCurrentClass("StudentStatCollect");
            }
        });

        JButton nextButton = createButton.nextButtonCreate();
        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hideWindow();
            }
        });
        backNextButtonsPanel = createButton.makeBackNextButtonsPanel(backButtonPanel, new JPanel(), nextButtonPanel);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
    }

    public void printArray(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("item "+list.get(i));
        }

    }

    public void hideWindow() {
        System.out.println("confirmation");
        backNextButtonsPanel.setVisible(false);
        borderContainer.setVisible(false);

        Component[] windowComponents3 = window.getContentPane().getComponents();
        int i = 0;
        for (Component windowComp : windowComponents3) {
            System.out.println("compcount " + i + " classname "+ windowComp.getClass().getName() + " regname "+windowComp.getName() + " isvisible "+windowComp.isVisible());
            i++;
        }

    }
    }