package view.student;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

import controller.Decorator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;

import controller.FileHandling;
import model.Set;
import model.SetList;
import model.SetUserInformation;
import controller.CompositeActionListenerWithPriorities;
import controller.CreateButton;

import java.awt.Color;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
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
    private static int whichCurrClassIndex;

    private ArrayList<String> gradeTypeList = new ArrayList<>();
    private int gradeTypeIndex = 0;
    private ArrayList<Integer> gradeBoxTotal = new ArrayList<>();

    private ArrayList<String> allTextListForClass;
    private ArrayList<Integer> percentageOfGradeList;
    private ArrayList<Integer> listOfAverages;
    private ArrayList<ArrayList<String>> gradeList;
    private ArrayList<Float> amountOfFinalGrade;
    private CompositeActionListenerWithPriorities actionPriorities = CompositeActionListenerWithPriorities.getInstance();
    private JButton nextButton = createButton.nextButtonCreate();
    JLabel instructionsWords = new JLabel("Grades (none valid left)"); // moved 12/30
    JPanel instructionsPanel = new JPanel(); //moved 12/30

    public PrintStudentGrades(JFrame main, String studentOrTeacher, String existingOrNew) {
        whichCurrClassIndex = 0;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Set set = Set.getInstance();
        JFrame window = set.getWindow();
        window.removeWindowListener(set.getCurrentWindowClosing());
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Component[] windowComponents3 = window.getContentPane().getComponents();
        for (Component windowComp : windowComponents3) {
            if ((windowComp.getName() != null) && (windowComp.getName().equals("classlabelpanelinreadclass"))) {
                window.remove(windowComp);
            }
        }

        printStudentGradesLaunch();

        EnterAction enterAction = new EnterAction();
        window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        window.getRootPane().getActionMap().put("enterAction", enterAction);
        actionPriorities.setCurrentClass("PrintStudentGrades");
    }

    public void printStudentGradesLaunch() {
        actionPriorities.setCurrentClass("PrintStudentGrades");

        setUserInformation = SetUserInformation.getInstance();
        String filePathForClass = setUserInformation.getPathToClassInformationFileWithChosenIndex(whichCurrClassIndex); //classIndex
        allTextListForClass = fileHandler.readFileToList(filePathForClass);

        buttonSetUpAction(); //moved 12/30

        if (allTextListForClass.size() > 1) { //moved 12/30
        System.out.println("check");
        studentStatCollectLaunch();

        setList = SetList.getInstance();

        gradeList = new ArrayList<>();
        amountOfFinalGrade = new ArrayList<>();
        listOfAverages = new ArrayList<>();
        percentageOfGradeList = new ArrayList<>();

        printArray(allTextListForClass);

            ArrayList<String> tempList = new ArrayList<String>(Arrays.asList(allTextListForClass.get(3).split(" ")));

            gradeList.add(tempList); //get grades sect for class

            makePercentageOfGradeList();

            makeGradeTypeList();

            makeListOfAveragesList();

            makeAmountOfFinalGradeList();

            float total = getGradeTotal();

            addEverythingToWindow(total);
            instructionsPanel.setVisible(false);
        }
        else {
            instructionsPanel = decorate.InstructionsPanelDecorate(instructionsPanel, instructionsWords );
            Set.getInstance().getWindow().add(instructionsPanel);

        }
        actionPriorities.setCurrentClass("PrintStudentGrades");
    }

    private void addEverythingToWindow(float total) {
        Border blackline = BorderFactory.createLineBorder(Color.black);
        ArrayList <Integer> gradeTypeNumberList = setList.getGradeTypeList();

        int gradeTypeAmount = gradeTypeNumberList.get(whichCurrClassIndex);
        JPanel allContainer = new JPanel(new GridLayout((1+gradeTypeAmount),3,4,5)); //rows,cols //////
        allContainer.setName("allContainr");
        borderContainer = new JPanel(new BorderLayout());

        JLabel placeholdType2 = new JLabel("Class name: "+setList.getFinalClassList().get(whichCurrClassIndex));
        placeholdType2.setBorder(blackline);
        placeholdType2.setHorizontalAlignment(JLabel.CENTER);
        borderContainer.add(placeholdType2,BorderLayout.NORTH);

        borderContainer.add(allContainer,BorderLayout.CENTER);

        JLabel finalgrade = new JLabel("Final Grade: "+Math.round(total * 10.0) / 10.0);
        finalgrade.setBorder(blackline);
        finalgrade.setHorizontalAlignment(JLabel.CENTER);
        borderContainer.add(finalgrade,BorderLayout.SOUTH);

        JLabel placeholdType = new JLabel("Grade Type name: ");//+gradeTypeList.get(whichCurrClassIndex));
        placeholdType.setBorder(blackline);
        placeholdType.setHorizontalAlignment(JLabel.CENTER);
        allContainer.add(placeholdType,BorderLayout.NORTH);

        JLabel average = new JLabel("Average");
        average.setBorder(blackline);
        average.setHorizontalAlignment(JLabel.CENTER);
        allContainer.add(average);

        JLabel percgrade = new JLabel("Perc. of final grade");
        percgrade.setBorder(blackline);
        percgrade.setHorizontalAlignment(JLabel.CENTER);
        allContainer.add(percgrade);

        JLabel contributiongrade = new JLabel("Contribution");
        contributiongrade.setBorder(blackline);
        contributiongrade.setHorizontalAlignment(JLabel.CENTER);
        allContainer.add(contributiongrade);

        // 12/7 for (int i = 0; i < gradeTypeNumberList.get(whichCurrClassIndex); i++) { //shouldnt be for classes, should be for gradetypes then classes
        for (int i = 0; i < gradeTypeNumberList.get(whichCurrClassIndex); i++) { //shouldnt be for classes, should be for gradetypes then classes
            // commendedout 12/7 JLabel gradeTypeNameLabel = new JLabel(gradeTypeList.get(whichCurrClassIndex+i));
            JLabel gradeTypeNameLabel = new JLabel(gradeTypeList.get(gradeTypeIndex)); //added 12/7
            gradeTypeIndex++; //added 12/7

            gradeTypeNameLabel.setBorder(blackline);
            allContainer.add(gradeTypeNameLabel);

            JLabel listOfAveragesLabel = new JLabel(String.valueOf(listOfAverages.get(i)));
            listOfAveragesLabel.setBorder(blackline);
            allContainer.add(listOfAveragesLabel);

            JLabel percentageOfGradeLabel = new JLabel(String.valueOf(percentageOfGradeList.get(i)));
            percentageOfGradeLabel.setBorder(blackline);
            allContainer.add(percentageOfGradeLabel);

            float currPercentageOfGrade = percentageOfGradeList.get(i);

            JLabel contributionLabel = new JLabel((String.valueOf(((float) currPercentageOfGrade *  listOfAverages.get(i))/100))); //11/29 changed from gradeBoxTotal to listOfAverages because would cause a bug when multiple in one gradebox
            contributionLabel.setBorder(blackline);
            allContainer.add(contributionLabel);
        }
        window.add(borderContainer);
        Component[] windowComponents3 = window.getContentPane().getComponents();
        for (Component windowComp : windowComponents3) {
            if ((windowComp.getName() != null) && (windowComp.getName().equals("classlabelpanelinreadclass"))) {
                window.remove(windowComp);
            }
        }
    }

    private float getGradeTotal() {
        float total = 0; //changed below 11/30
        for (int i = 0; i < amountOfFinalGrade.size(); i++) { //get loop through all possible gradeTypeIndexes (amountoffinalgrade.size())
            total += amountOfFinalGrade.get(i);
        }
        return total;
    }

    private void makeAmountOfFinalGradeList() {
        int averageXpercentageOfGrade;
        for (int boxSetIndex = 0; boxSetIndex < listOfAverages.size(); boxSetIndex++) {
            averageXpercentageOfGrade = listOfAverages.get(boxSetIndex)*percentageOfGradeList.get(boxSetIndex);
            amountOfFinalGrade.add(((float) averageXpercentageOfGrade)/100);
        }
    }

    private void makeListOfAveragesList() {
        listOfAverages = new ArrayList<>();
        ArrayList<ArrayList<String>> seperatedBySpaceListOfGrades = new ArrayList<ArrayList<String>>();
        for (int boxPanelIndex = 3; boxPanelIndex < allTextListForClass.size(); boxPanelIndex += 3) { //for each grades box
            List <String> gradeArrayForOneBox = Arrays.asList(allTextListForClass.get(boxPanelIndex).split(" "));

            seperatedBySpaceListOfGrades.add(new ArrayList<>(gradeArrayForOneBox));

        }//added 11/10
            int average = 0;
            int thisGradeBoxTotal = 0;

            for (int i = 0; i < seperatedBySpaceListOfGrades.size(); i++) {
                thisGradeBoxTotal = seperatedBySpaceListOfGrades.get(i).stream()
                            .mapToInt(Integer::parseInt)  // Convert String to int
                            .sum();
                gradeBoxTotal.add(thisGradeBoxTotal);
                average = gradeBoxTotal.get(i)/seperatedBySpaceListOfGrades.get(i).size();
                listOfAverages.add(average);
            }
    }

    private void makeGradeTypeList() {
        for (int gradeTypePanelIndex = 1; gradeTypePanelIndex < allTextListForClass.size(); gradeTypePanelIndex += 3) {
            gradeTypeList.add(allTextListForClass.get(gradeTypePanelIndex));
        }
    }

    private void makePercentageOfGradeList() {
        for (int percentageOfGradePanelIndex = 2; percentageOfGradePanelIndex < allTextListForClass.size(); percentageOfGradePanelIndex += 3) {
            percentageOfGradeList.add(Integer.parseInt(allTextListForClass.get(percentageOfGradePanelIndex)));
        }
    }

    public void studentStatCollectLaunch() {
        this.window = Set.getInstance().getWindow();
        window.setTitle("PrintStudentGrades");
    }

    public void buttonSetUpAction() {
        JButton backButton = createButton.backButtonCreate();
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        actionPriorities.setCurrentClass("PrintStudentGrades");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPriorities.setCurrentClass("PrintStudentGrades");
                actionPriorities.addClassActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        doBackButtonProcedure();
            }
        }, 1, "backButton", null, "PrintStudentGrades");
    }});

        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);

        Boolean atEndOfClasses = (whichCurrClassIndex == SetList.getInstance().getFinalClassList().size() -1) ; //-1 to account for index starting from 0 and size not
        if (atEndOfClasses) {
            nextButton.setEnabled(false);

        }

        else {
            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    checkForNextButtonProcedure();
                }
            });
        }

        //checkForNextButtonProcedure();

        backNextButtonsPanel = createButton.makeBackNextButtonsPanel(backButtonPanel, new JPanel(), nextButtonPanel);
        Set.getInstance().getWindow().add(backNextButtonsPanel, BorderLayout.SOUTH);
    }
    public void doBackButtonProcedure() {
        //if at class index 0
        if (whichCurrClassIndex > 0) { //if no previous print grades classes
            hideWindow();
            whichCurrClassIndex--; //only if there are more
            nextButton.setEnabled(true); //added 11/29
            gradeBoxTotal.clear(); //added 12/31
            printStudentGradesLaunch();
            actionPriorities.setCurrentClass("PrintStudentGrades");
        }

        else {
            hideWindow();
            StudentStatCollect studentStatCollect = new StudentStatCollect();
            studentStatCollect.addLoadedBoxes();
            studentStatCollect.studentStatCollectLaunch();
            actionPriorities.setCurrentClass("StudentStatCollect");
        }
    }
    private void checkForNextButtonProcedure() {
        Boolean atEndOfClasses = (whichCurrClassIndex == SetList.getInstance().getFinalClassList().size() -1) ; //-1 to account for index starting from 0 and size not
        if (atEndOfClasses) {
            nextButton.setEnabled(false);
        }

        else {
            doNextButtonProcedure();
        }
    }

    public void doNextButtonProcedure() {
        hideWindow();
        whichCurrClassIndex++; //only if there are more
        gradeBoxTotal.clear();
        printStudentGradesLaunch();
    }

    public void printArray(ArrayList<String> list) {
        System.out.println("list.size() "+list.size());
        for (int i = 0; i < list.size(); i++) {
            System.out.println("item "+list.get(i));
        }
    }

    public void hideWindow() {
        backNextButtonsPanel.setVisible(false);
        borderContainer.setVisible(false);
        borderContainer.removeAll();
        backNextButtonsPanel.removeAll();
    }

    public class EnterAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            actionPriorities.setCurrentClass("PrintStudentGrades");
            actionPriorities.addClassActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { // remember won't run if just enter without a click
                    checkForNextButtonProcedure();
                }
            }, 1, "EnterAction", null, "PrintStudentGrades");  // Add this ActionListener with priority 1
        }
    }
    }