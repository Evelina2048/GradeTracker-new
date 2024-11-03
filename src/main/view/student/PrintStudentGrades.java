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
    private int whichCurrClassIndex;

    private ArrayList<String> gradeTypeList = new ArrayList<>();
    private int gradeTypeIndex = 0;
    private ArrayList<Integer> gradeBoxTotal = new ArrayList<>();

    private ArrayList<String> allTextListForClass;
    private ArrayList<Integer> percentageOfGradeList;
    private ArrayList<Integer> listOfAverages;
    private ArrayList<ArrayList<String>> gradeList;
    private ArrayList<Float> amountOfFinalGrade;
    private CompositeActionListenerWithPriorities actionPriorities = CompositeActionListenerWithPriorities.getInstance();

    public PrintStudentGrades(JFrame main, String studentOrTeacher, String existingOrNew) {
        Component[] windowComponents3 = window.getContentPane().getComponents();
        int i = 0;
        for (Component windowComp : windowComponents3) {
            System.out.println("compcount " + i + " classname "+ windowComp.getClass().getName() + " regname "+windowComp.getName() + " isvisible "+windowComp.isVisible());
            i++;
        }
        whichCurrClassIndex = 0;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Set set = Set.getInstance();
        JFrame window = set.getWindow();
        window.removeWindowListener(set.getCurrentWindowClosing());
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        printStudentGradesLaunch();

        EnterAction enterAction = new EnterAction();
        window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        window.getRootPane().getActionMap().put("enterAction", enterAction);
        actionPriorities.setCurrentClass("PrintStudentGrades");
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
        listOfAverages = new ArrayList<>();
        percentageOfGradeList = new ArrayList<>();
            String filePathForClass = setUserInformation.getPathToClassInformationFileWithChosenIndex(whichCurrClassIndex); //classIndex
            allTextListForClass = fileHandler.readFileToList(filePathForClass);

            printArray(allTextListForClass);

            ArrayList<String> tempList = new ArrayList<String>(Arrays.asList(allTextListForClass.get(3).split(" ")));
            gradeList.add(tempList); //get grades sect for class

            makePercentageOfGradeList();

            makeGradeTypeList();

            makeListOfAveragesList();

            makeAmountOfFinalGradeList();

        float total = getGradeTotal();

        addEverythingToWindow(total);
        actionPriorities.setCurrentClass("PrintStudentGrades");
    }

    private void addEverythingToWindow(float total) {
        Border blackline = BorderFactory.createLineBorder(Color.black);
        ArrayList <Integer> gradeTypeNumberList = setList.getGradeTypeList();

        int gradeTypeAmount = gradeTypeNumberList.get(whichCurrClassIndex);
        JPanel allContainer = new JPanel(new GridLayout((1+gradeTypeAmount),3,5,5)); //rows,cols //////
        allContainer.setName("allContainr");
        borderContainer = new JPanel(new BorderLayout());

        JLabel placeholdType = new JLabel("Grade Type name: "+gradeTypeList.get(whichCurrClassIndex));
        placeholdType.setBorder(blackline);
        placeholdType.setHorizontalAlignment(JLabel.CENTER);
        borderContainer.add(placeholdType,BorderLayout.NORTH);

        borderContainer.add(allContainer,BorderLayout.CENTER);

        JLabel finalgrade = new JLabel("Final Grade: "+total);
        finalgrade.setBorder(blackline);
        finalgrade.setHorizontalAlignment(JLabel.CENTER);
        borderContainer.add(finalgrade,BorderLayout.SOUTH);

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

        for (int i = 0; i < gradeTypeNumberList.get(whichCurrClassIndex); i++) { //shouldnt be for classes, should be for gradetypes then classes
            JLabel listOfAveragesLabel = new JLabel(String.valueOf(listOfAverages.get(i)));
            listOfAveragesLabel.setBorder(blackline);
            allContainer.add(listOfAveragesLabel);

            JLabel percentageOfGradeLabel = new JLabel(String.valueOf(percentageOfGradeList.get(i)));
            percentageOfGradeLabel.setBorder(blackline);
            allContainer.add(percentageOfGradeLabel);

            allContainer.add(new JLabel("heyo "+(String.valueOf((float) percentageOfGradeList.get(i)/100*gradeBoxTotal.get(i)))));
        }
        window.add(borderContainer);
    }

    private float getGradeTotal() {
        float total = 0;
            total += amountOfFinalGrade.get(gradeTypeIndex);
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
        System.out.println("listofaverages -3 "+allTextListForClass);
        for (int boxPanelIndex = 3; boxPanelIndex < allTextListForClass.size(); boxPanelIndex += 3) { //for each grades box
            List <String> gradeArrayForOneBox = Arrays.asList(allTextListForClass.get(boxPanelIndex).split(" "));

            seperatedBySpaceListOfGrades.add(new ArrayList<>(gradeArrayForOneBox));

            int average = 0;
            for (int i = 0; i< seperatedBySpaceListOfGrades.size(); i++) {
                int thisGradeBoxTotal = seperatedBySpaceListOfGrades.get(i).stream()
                            .mapToInt(Integer::parseInt)  // Convert String to int
                            .sum();
                gradeBoxTotal.add(thisGradeBoxTotal);

                average = gradeBoxTotal.get(i)/seperatedBySpaceListOfGrades.size();
            }

            listOfAverages.add(average);
        }
    }

    private void makeGradeTypeList() {
        //gradeTypeList = new ArrayList<>();
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

        JButton nextButton = createButton.nextButtonCreate();

        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);
        Boolean atEndOfClasses = whichCurrClassIndex == SetList.getInstance().getFinalClassList().size() -1 ; //-1 to account for index starting from 0 and size not
        if (atEndOfClasses) {
            nextButton.setEnabled(false);

        }

        else {
            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    doNextButtonProcedure();
                }
            });
        }
        backNextButtonsPanel = createButton.makeBackNextButtonsPanel(backButtonPanel, new JPanel(), nextButtonPanel);
        window.add(backNextButtonsPanel, BorderLayout.SOUTH);
    }
    public void doBackButtonProcedure() {
        //if at class index 0
        System.out.println("whichcurrclassindex backbutton "+whichCurrClassIndex);
        if (whichCurrClassIndex > 0) { //if no previous print grades classes
            hideWindow();
            whichCurrClassIndex--; //only if there are more
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

    public void doNextButtonProcedure() {
        hideWindow();
        whichCurrClassIndex++; //only if there are more
        printStudentGradesLaunch();
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
        borderContainer.removeAll();
        backNextButtonsPanel.removeAll();

        Component[] windowComponents3 = window.getContentPane().getComponents();
        int i = 0;
        for (Component windowComp : windowComponents3) {
            System.out.println("compcount " + i + " classname "+ windowComp.getClass().getName() + " regname "+windowComp.getName() + " isvisible "+windowComp.isVisible());
            i++;
        }

    }

    public class EnterAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            actionPriorities.setCurrentClass("PrintStudentGrades");
            actionPriorities.addClassActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { // remember won't run if just enter without a click
                    System.out.println("enteraction");
                    doNextButtonProcedure();
                }
            }, 1, "EnterAction", null, "PrintStudentGrades");  // Add this ActionListener with priority 1
        }
    }
    }