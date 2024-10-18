package view.student;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

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
import java.util.stream.IntStream;
import java.awt.event.ActionEvent;
import controller.Creator;
import controller.Decorator;
import controller.FileHandling;
import model.Set;
import model.SetList;
import model.SetState;
import model.SetUserInformation;
import controller.CompositeActionListenerWithPriorities;
import controller.CreateButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.AbstractAction;
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

    private ArrayList<String> gradeTypeList = new ArrayList<>();
    private int gradeTypeIndex = 0;
    private ArrayList<Integer> gradeBoxTotal = new ArrayList<>();

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

        EnterAction enterAction = new EnterAction();
        window.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        window.getRootPane().getActionMap().put("enterAction", enterAction);

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
        //gradeTypeList = new ArrayList<>(); //10/2
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
            System.out.println("hello print grades. grade type number is "+gradeTypeList.size()+" and the classes place is "+SetState.getInstance().getClassListIndex()+"/"+SetList.getInstance().getFinalClassList().size());

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
        System.out.println("gradeTypeAmount "+gradeTypeAmount);
        JPanel allContainer = new JPanel(new GridLayout((1+gradeTypeAmount),3,5,5)); //rows,cols //////
        allContainer.setName("allContainr");
        borderContainer = new JPanel(new BorderLayout());

        JLabel placeholdType = new JLabel("Placeholder name: "+gradeTypeList.get(gradeTypeIndex));
        placeholdType.setBorder(blackline);
        placeholdType.setHorizontalAlignment(JLabel.CENTER); //TODO center might cause bugs 10/12
        borderContainer.add(placeholdType,BorderLayout.NORTH);


        borderContainer.add(allContainer,BorderLayout.CENTER);

        JLabel finalgrade = new JLabel("Final Grade: "+total);
        finalgrade.setBorder(blackline);
        finalgrade.setHorizontalAlignment(JLabel.CENTER);
        borderContainer.add(finalgrade,BorderLayout.SOUTH);

        Creator create = new Creator();

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

        //if ()
        System.out.println("*gradetypenumberlist* "+gradeTypeNumberList);
        System.out.println("&whichCurrClassIndex& "+whichCurrClassIndex);
        for (int i = 0; i < gradeTypeNumberList.get(whichCurrClassIndex); i++) { //shouldnt be for classes, should be for gradetypes then classes
        //for (int i = 0; i < gradeTypeNumberList.get(gradeTypeIndex); i++) { //10/12
            System.out.println("i "+gradeTypeNumberList);
            System.out.println("i listOfAverages"+listOfAverages);
            System.out.println("i 180 "+i);
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
            allContainer.add(new JLabel("heyo "+(String.valueOf((float) percentageOfGradeList.get(i)/100*gradeBoxTotal.get(i)))));

            //gradeTypeIndex++;
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
        //gradeBoxTotal = 0; //commented out 10/15 12:52AM
        ArrayList<ArrayList<String>> seperatedBySpaceListOfGrades = new ArrayList<ArrayList<String>>();
        System.out.println("listofaverages -3 "+allTextListForClass);
        for (int boxPanelIndex = 3; boxPanelIndex < allTextListForClass.size(); boxPanelIndex += 3) { //for each grades box
            System.out.println("10/12/12:01 should print twice");
            List <String> gradeArrayForOneBox = Arrays.asList(allTextListForClass.get(boxPanelIndex).split(" "));
            System.out.println("tempArray "+gradeArrayForOneBox);

            seperatedBySpaceListOfGrades.add(new ArrayList<>(gradeArrayForOneBox));
            System.out.println("shouldbefinalseperatedbyspace "+seperatedBySpaceListOfGrades);
            //10/13 seperatedBySpaceListOfGrades = new ArrayList<>(Arrays.asList(allTextListForClass.get(boxPanelIndex).split(" ")));

            //10/13 12:47 gradeList.add(seperatedBySpaceListOfGrades);

            System.out.println(("seperatedbyspacelistofgrades "+seperatedBySpaceListOfGrades));
            System.out.println(("listofaverages -2.2 "+seperatedBySpaceListOfGrades.get(0)));
            //for (ArrayList<String> grades : seperatedBySpaceListOfGrades) {
            int average = 0;
            for (int i = 0; i< seperatedBySpaceListOfGrades.size(); i++) {
                //gradeList.add(IntStream.of(seperatedBySpaceListOfGrades.get(k)).sum());
                int thisGradeBoxTotal = seperatedBySpaceListOfGrades.get(i).stream()
                            .mapToInt(Integer::parseInt)  // Convert String to int
                            .sum();
                //10/13 12:47 gradeBoxTotal += Integer.parseInt(seperatedBySpaceListOfGrades.get(k));
                gradeBoxTotal.add(thisGradeBoxTotal);
                System.out.println("10/13 12:59 "+gradeBoxTotal);

                average = gradeBoxTotal.get(i)/seperatedBySpaceListOfGrades.size();
            }

            System.out.println("screen "+seperatedBySpaceListOfGrades + "size "+seperatedBySpaceListOfGrades.size());
            System.out.println("listofaverages -2 "+average);
            listOfAverages.add(average);
            System.out.println("listofaverages -1 "+listOfAverages);

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
        // System.out.println("hello print grades. grade type number is "+gradeTypeList.size()+" and the classes place is "+SetState.getInstance().getClassListIndex()+"/"+SetList.getInstance().getFinalClassList().size());
        Boolean atEndOfClasses = SetState.getInstance().getClassListIndex() == SetList.getInstance().getFinalClassList().size();
        //0 == 
        //at first grade with two classes with one grade each
        if (atEndOfClasses) {
            nextButton.setEnabled(false);

        }

        else {
            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    hideWindow();
                    whichCurrClassIndex++; //only if there are more
                }
            });
        }
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

    public class EnterAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            actionPriorities.setCurrentClass("PrintStudentGrades");
            actionPriorities.addClassActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { // remember won't run if just enter without a click
                    System.out.println("enteraction");
                    //doNextButtonProcedure();

                }
            }, 1, "EnterAction", null, "PrintStudentGrades");  // Add this ActionListener with priority 1
        }
    }
    }