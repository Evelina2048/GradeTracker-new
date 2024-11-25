package controller;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

//classes
import model.Set;
import model.SetUserInformation;
import model.SetListeners;
import model.SetState;
import view.student.StudentStatCollect;

import java.io.IOException;
//files
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//key listening
public class Decorator {
    JFrame window;
    Set set;
    SetState setState;
    SetUserInformation setUserInformation;

    SetListeners setListeners;
    JDialog dialog;
    String reason;
    JTextField textField = new JTextField();
    String yesOrNoDialog;
    TextFieldColorFocusListener colorFocusListener;

    Font currentTextFieldFont;

    public Decorator() {
        set = Set.getInstance();
        setState = SetState.getInstance();
        setListeners = SetListeners.getInstance();
        window = set.getWindow();
        colorFocusListener = new TextFieldColorFocusListener();
    }

    public JPanel InstructionsPanelDecorate(JPanel instructionsPanel, JLabel instructionsWords) {
        JFrame window = set.getWindow();
        instructionsPanel= new JPanel();
        Color instructionsColor = Color.decode("#7A6D6D");
        instructionsPanel.setBackground(instructionsColor);

        instructionsPanel.add(instructionsWords);
        Color instructionsWordsColor = Color.decode("#edebeb");
        instructionsWords.setForeground(instructionsWordsColor); //color
        window.add(instructionsPanel, BorderLayout.NORTH);

        //set the font for instructions
        Font instructionsFont = new Font("Roboto", Font.PLAIN, 30); // Change the font and size here
        instructionsWords.setFont(instructionsFont);

        return instructionsPanel;
    }

    public GridBagConstraints choiceGbc() {
        //radio buttons distance
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 0, 0); // Increase the horizontal spacing between components
        return gbc;
    }

    public String areYouSureMessage(JTextField importedTextField, String myReason, String text, int width, int height) {
        System.out.println("mean 6666");
        reason = myReason;
        textField = importedTextField;
        dialog = new JDialog(window, true);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //10/21 to remove window close default and not

        dialog.setResizable(false);
        dialog.setLayout(new FlowLayout());
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(label);

        JButton yesButton = new JButton("Yes");
        yesButton.setVisible(true);
        dialog.add(yesButton);

        dialog.addWindowListener(new WindowAdapter() {  //11/23 so that back button still works after close
            @Override
            public void windowClosing(WindowEvent e) {
                noButtonAction();
            }
        });

        JButton noButton = new JButton("Cancel");
        noButton.setVisible(true);
        dialog.add(noButton);
        dialog.setSize(width,height); //250, 120

        yesButtonActionListener(yesButton);
        noButtonActionListener(noButton);

        dialog.setLocationRelativeTo(window);
        dialog.setVisible(true);

        return yesOrNoDialog;
    }

    private void yesButtonActionListener(JButton yesButton) {
        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("mean 7777");
                yesButtonAction();
            }
        });
        yesOrNoDialog = "yes";
    }

    private void yesButtonAction() {
        System.out.println("mean 8888");
        if (reason == "deleting") {
            reasonIsDeletingActionYes();
        }

        else if (reason == "studentStatsEmpty") {
            System.out.println("mean 9999");
            CompositeActionListenerWithPriorities.getInstance().reset(); //11/25
            
            reasonIsStudentStatsEmptyYes();
        }

        else if (reason == "closing window") {
        window.dispose();
        }

        else {
        reasonIsChangingUsernameYes();
        }

    }

    private void reasonIsDeletingActionYes() { //for student classes
        Creator create = new Creator();
        textField.setVisible(false);
        JPanel panelForDeleting = new JPanel();
        panelForDeleting.add(textField);
        create.deleteTextBox(panelForDeleting);
        Path filePath = Paths.get(setUserInformation.getPathToClassInformationFileWithTextField(textField));
        try {
            Files.deleteIfExists(filePath);
            setUserInformation.addDeleteToQueue(textField.getText());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        dialog.setVisible(false);
        dialog.dispose();

    }

    private void reasonIsChangingUsernameYes() {
        int caretPosition = textField.getCaretPosition();

        textField.removeFocusListener(setListeners.getDialogFocusListener());
        textField.setSelectionColor(textField.getBackground());

        FocusListener yesFocusListener = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> {
                    textField.setCaretPosition(caretPosition);
                });
            }
        };

        textField.addFocusListener(yesFocusListener);
        setListeners.setYesFocusListener(yesFocusListener);

        dialog.setVisible(false);
        dialog.dispose();

        SwingUtilities.invokeLater(() -> {
            textField.requestFocusInWindow();
            if (textField.getText().length() >= 28) {
                textField.setCaretPosition(textField.getText().length());
            } else {
                textField.setCaretPosition(0); // Initially place caret at the beginning
            }
            textField.setSelectionColor(UIManager.getColor("TextField.selectionBackground"));
        });

    }

    private void reasonIsStudentStatsEmptyYes() {
        //go to next class or print class
        String actionCause = setState.getAreYouSureMessageCause();
        System.out.println("mean 10 10 10 10");

        StudentStatCollect studentStat = setState.getStudentStatCollect();//new StudentStatCollect();
        if (actionCause.equals("nextButton")) {
            studentStat.doNextButtonProcedure2();
        }

        else if (actionCause.equals("backButton")) {
            System.out.println("mean 11 11 11 11");
            //make sure you can go back even with placeholders:
            setState.setCanContinue(true);
            studentStat.backAction2();
            //studentStat.deci
        }

        System.out.println("dialog visi "+dialog.isVisible());

        setState.setCanContinue(true); //11/19
        dialog.setVisible(false);
        dialog.dispose();
    }

    public void deleteFocusListeners(int amount) {
        FocusListener[] listeners = textField.getFocusListeners();
        for (int i = 0; i < amount; i++) {
            textField.removeFocusListener(listeners[listeners.length-1-i]);
        }

    }

    private void noButtonActionListener(JButton noButton) {
        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                noButtonAction();
            }
        });
        yesOrNoDialog = "no";
    }

    private void noButtonAction() {
        System.out.println("in no button action");
        if (reason == "closing window") {
            window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
        window.requestFocusInWindow();
        dialog.setVisible(false);
        window.requestFocusInWindow();
        if (dialog != null) {
            dialog.dispose();
            setState.setCanContinue(true); //11/19
        }
        window.requestFocusInWindow();

        FocusListener noFocusListener = new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    window.requestFocusInWindow();

                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            textField.requestFocusInWindow();
                        }
                    });

                    textField.removeFocusListener(this);
                }

        };

        if (textField != null) {
            textField.addFocusListener(noFocusListener);
            textField.removeFocusListener(setListeners.getDialogFocusListener());
        }
        setListeners.setNoFocusListener(noFocusListener);
    }

    public void setCaretPositionToZero(JTextField importedTextField) {
        textField = importedTextField;
        textField.setCaretPosition(1);
        textField.setSelectionStart(0);
        textField.setSelectionEnd(0);

        textField.moveCaretPosition(textField.getCaretPosition());
    }

    public JDialog genericPopUpMessage(String text,JRadioButton button, int width, int height) {
        dialog = new JDialog(window, true);
        dialog.setResizable(false);
        dialog.setLayout(new FlowLayout());
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(label);
        JButton okButton = new JButton("OK");
        okButton.setVisible(true);
        dialog.add(okButton);
        dialog.setSize(width, height); //200,90
        setState.setCanContinue(false);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
                setState.setCanContinue(false);
            }
        });
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.dispose();
                setState.setCanContinue(false);
            }
        });
        return dialog;

    }

    public void pleaseChooseAnOptionWithRadioButtons(JRadioButton button) {
        dialog = genericPopUpMessage("<html><center>Please choose an option", button, 200, 90);
        dialog.setResizable(false);

        dialog.setLocationRelativeTo(button);
        dialog.setLocation(dialog.getX(), dialog.getY() + 15);
        dialog.setVisible(true);
    }

    public void hideWindow(JPanel instructionsPanel, JPanel choicesPanel, JPanel backNextButtonsPanel) {
        instructionsPanel.setVisible(false);
        choicesPanel.setVisible(false);
        backNextButtonsPanel.setVisible(false);
    }

    public JTextField decorateTextBox(String placeholderText) {
        if (setState.getCurrentClass() == "StudentStatCollect.java") {
        textField.setSize(new Dimension(144, 50));
        }
        else {
        textField.setPreferredSize(new Dimension(144, 50));
        }

        textField.setFont(new Font("Roboto", Font.PLAIN, 14));

        if (currentTextFieldFont == null) {

            currentTextFieldFont = textField.getFont();
            setState.setTextFieldFont(currentTextFieldFont);
        }

        textField.setForeground(Color.gray);
        textField.setBorder(BorderFactory.createLineBorder(Color.gray, 2));

        Color defaultTextColor = Color.decode("#B0B0B0");
        textField.setSelectedTextColor(defaultTextColor);

        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setText(placeholderText);

        colorFocusListener.textFieldFocusListener(textField, placeholderText);
        CreateButton createButton = new CreateButton();
        createButton.addDocumentListenerForSaving(textField);

        return textField;
    }

    public void areYouSureMessageListenerForEditingUsername() {
        areYouSureMessage(textField, "editing username", "<html><center>Editing this username will create or <br>login to an account under this name. <br>Do you wish to continue?", 250, 120);
        window.requestFocusInWindow();
    }

    public void maximumAmountReachedPopup() {
        if (setState.getCanContinue() == true) {
            JDialog dialog = genericPopUpMessage("<html><center>Maximum amount reached.", null, 200 , 100);
            dialog.setLocationRelativeTo(window);
            dialog.setLocation(dialog.getX(), dialog.getY() + 15);
            dialog.setVisible(true);
        }
    }

    public Font getFontFromTextFields() {
        return currentTextFieldFont;
    }


    public void removeForNewUserWindow(JPanel instructionsPanel, JPanel choicesPanel, JPanel backNextButtonsPanel) {
        JFrame window = Set.getInstance().getWindow();
        window.remove(instructionsPanel);
        window.remove(choicesPanel);
        window.remove(backNextButtonsPanel);
    }

}