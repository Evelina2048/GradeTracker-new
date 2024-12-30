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

        //
        Component[] windowComponents3 = window.getContentPane().getComponents();
        int i = 0;
        for (Component windowComp : windowComponents3) {
            if ((windowComp.getName() != null) && (windowComp.getName().equals("classlabelpanelinreadclass"))) {
                window.remove(windowComp);
            }
            System.out.println("compcount " + i + " classname "+ windowComp.getClass().getName() + " regname "+windowComp.getName() + " isvisible "+windowComp.isVisible());
            i++;
        }
        //

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

        //JLabel instructionsWords = new JLabel("Grades (none valid left)"); // moved 12/30
        //JPanel instructionsPanel = new JPanel();
        //instructionsPanel = decorate.InstructionsPanelDecorate(instructionsPanel, instructionsWords );
        //Set.getInstance().getWindow().add(instructionsPanel); //
        buttonSetUpAction(); //moved 12/30

        if (allTextListForClass.size() > 1) { //moved 12/30
        System.out.println("check");
        studentStatCollectLaunch();
        // JLabel instructionsWords = new JLabel("Grades (none valid left)");
        // JPanel instructionsPanel = new JPanel();
        // instructionsPanel = decorate.InstructionsPanelDecorate(instructionsPanel, instructionsWords );
        // window.add(instructionsPanel);
        // buttonSetUpAction(); //12/30
        setList = SetList.getInstance();
        // setUserInformation = SetUserInformation.getInstance(); //commented 12/30

        gradeList = new ArrayList<>();
        amountOfFinalGrade = new ArrayList<>();
        listOfAverages = new ArrayList<>();
        percentageOfGradeList = new ArrayList<>();
        //String filePathForClass = setUserInformation.getPathToClassInformationFileWithChosenIndex(whichCurrClassIndex); //classIndex //commented 12/30
        // allTextListForClass = fileHandler.readFileToList(filePathForClass); //commented 12/30

        printArray(allTextListForClass);
        System.out.println("after print array");
        System.out.println("minute 4444");

        // if (allTextListForClass.size() > 1) { //commented 12/30
            System.out.println("minute 5555");
            ArrayList<String> tempList = new ArrayList<String>(Arrays.asList(allTextListForClass.get(3).split(" ")));
            System.out.println("minute 6666 "+tempList);

            gradeList.add(tempList); //get grades sect for class
            System.out.println("minute 7777 "+gradeList);

            makePercentageOfGradeList();
            System.out.println("minute 8888");

            makeGradeTypeList();

            makeListOfAveragesList();

            makeAmountOfFinalGradeList();

            float total = getGradeTotal();

            addEverythingToWindow(total);
            System.out.println("minute end");
            instructionsPanel.setVisible(false);
        }
        else {
            instructionsPanel = decorate.InstructionsPanelDecorate(instructionsPanel, instructionsWords );
            Set.getInstance().getWindow().add(instructionsPanel);

        }
        actionPriorities.setCurrentClass("PrintStudentGrades");
    }

    private void addEverythingToWindow(float total) {
        System.out.println("minute 9999");
        Border blackline = BorderFactory.createLineBorder(Color.black);
        ArrayList <Integer> gradeTypeNumberList = setList.getGradeTypeList();

        System.out.println("12/2 gradeTypeNumberList"+gradeTypeNumberList+" whichcurrclassindex "+whichCurrClassIndex);
        int gradeTypeAmount = gradeTypeNumberList.get(whichCurrClassIndex);
        JPanel allContainer = new JPanel(new GridLayout((1+gradeTypeAmount),3,4,5)); //rows,cols //////
        allContainer.setName("allContainr");
        borderContainer = new JPanel(new BorderLayout());

        //JLabel placeholdType2 = new JLabel("Grade Type name: "+gradeTypeList.get(whichCurrClassIndex));
        JLabel placeholdType2 = new JLabel("Class name: "+setList.getFinalClassList().get(whichCurrClassIndex));
        placeholdType2.setBorder(blackline);
        placeholdType2.setHorizontalAlignment(JLabel.CENTER);
        borderContainer.add(placeholdType2,BorderLayout.NORTH);

        borderContainer.add(allContainer,BorderLayout.CENTER);

        JLabel finalgrade = new JLabel("Final Grade: "+total);
        finalgrade.setBorder(blackline);
        finalgrade.setHorizontalAlignment(JLabel.CENTER);
        borderContainer.add(finalgrade,BorderLayout.SOUTH);

        JLabel placeholdType = new JLabel("Grade Type name: "+gradeTypeList.get(whichCurrClassIndex));
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
            System.out.println("minute 10 10 10 10");
            // commendedout 12/7 JLabel gradeTypeNameLabel = new JLabel(gradeTypeList.get(whichCurrClassIndex+i));
            JLabel gradeTypeNameLabel = new JLabel(gradeTypeList.get(gradeTypeIndex)); //added 12/7
            gradeTypeIndex++; //added 12/7

            gradeTypeNameLabel.setBorder(blackline);
            allContainer.add(gradeTypeNameLabel);

            JLabel listOfAveragesLabel = new JLabel("that 0000 "+" class "+setList.getFinalClassList().get(whichCurrClassIndex) + String.valueOf(listOfAverages.get(i)));
            listOfAveragesLabel.setBorder(blackline);
            allContainer.add(listOfAveragesLabel);

            JLabel percentageOfGradeLabel = new JLabel(String.valueOf(percentageOfGradeList.get(i)));
            percentageOfGradeLabel.setBorder(blackline);
            allContainer.add(percentageOfGradeLabel);

            //new JLabel("heyo "+(String.valueOf((float) percentageOfGradeList.get(i)/100*gradeBoxTotal.get(i))))
            float currPercentageOfGrade = percentageOfGradeList.get(i);

            JLabel contributionLabel = new JLabel("heyo "+(String.valueOf(((float) currPercentageOfGrade *  listOfAverages.get(i))/100))); //11/29 changed from gradeBoxTotal to listOfAverages because would cause a bug when multiple in one gradebox
            contributionLabel.setBorder(blackline);
            allContainer.add(contributionLabel);
        }
        window.add(borderContainer);
        
        //12/30
        Component[] windowComponents3 = window.getContentPane().getComponents();
        int i = 0;
        for (Component windowComp : windowComponents3) {
            if ((windowComp.getName() != null) && (windowComp.getName().equals("classlabelpanelinreadclass"))) {
                window.remove(windowComp);
            }
            System.out.println("compcount " + i + " classname "+ windowComp.getClass().getName() + " regname "+windowComp.getName() + " isvisible "+windowComp.isVisible());
            i++;
        }
        //12/30
        //window.add(new JLabel("Test"));
    }

    private float getGradeTotal() {
        float total = 0; //changed below 11/30
        for (int i = 0; i < amountOfFinalGrade.size(); i++) { //get loop through all possible gradeTypeIndexes (amountoffinalgrade.size())
            //System.out.println("total " + total +" + "+amountOfFinalGrade.get(gradeTypeIndex+1) + " = " +total+amountOfFinalGrade.get(gradeTypeIndex+1));
            total += amountOfFinalGrade.get(i);//(gradeTypeIndex);
        }
        return total;
    }

    private void makeAmountOfFinalGradeList() {
        int averageXpercentageOfGrade;
        for (int boxSetIndex = 0; boxSetIndex < listOfAverages.size(); boxSetIndex++) {
            averageXpercentageOfGrade = listOfAverages.get(boxSetIndex)*percentageOfGradeList.get(boxSetIndex);
            System.out.println("7:44 averageXpercentageOfGrade "+averageXpercentageOfGrade);
            amountOfFinalGrade.add(((float) averageXpercentageOfGrade)/100);
            System.out.println("7:45 amount of final grade "+amountOfFinalGrade);
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
                //11/28 average = gradeBoxTotal.get(whichCurrClassIndex)/seperatedBySpaceListOfGrades.get(i).size();
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
                        System.out.println("minute 1111");
                        doBackButtonProcedure();
            }
                }, 1, "backButton", null, "PrintStudentGrades");
    }});

        JPanel nextButtonPanel = new JPanel();
        nextButtonPanel.add(nextButton);

        //
        System.out.println("at end of classes? "+whichCurrClassIndex+ " ==? "+(SetList.getInstance().getFinalClassList().size() -1));
        Boolean atEndOfClasses = (whichCurrClassIndex == SetList.getInstance().getFinalClassList().size() -1) ; //-1 to account for index starting from 0 and size not
        if (atEndOfClasses) {
            nextButton.setEnabled(false);

        }

        else {
            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //doNextButtonProcedure();
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
        System.out.println("minute 2222");
        if (whichCurrClassIndex > 0) { //if no previous print grades classes
            System.out.println("minute 3333");
            hideWindow();
            System.out.println("now index before "+whichCurrClassIndex);
            whichCurrClassIndex--; //only if there are more
            nextButton.setEnabled(true); //added 11/29
            printStudentGradesLaunch();
            System.out.println("now index after "+whichCurrClassIndex);
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
        System.out.println("at end of classes? "+whichCurrClassIndex+ " ==? "+(SetList.getInstance().getFinalClassList().size() -1));
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
                    // System.out.println("enteraction");
                    // if (nextButton.isEnabled()) {
                    //     doNextButtonProcedure();
                    // }
                    checkForNextButtonProcedure();
                }
            }, 1, "EnterAction", null, "PrintStudentGrades");  // Add this ActionListener with priority 1
        }
    }
    }