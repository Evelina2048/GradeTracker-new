package main.controller;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import java.awt.Dimension;

import java.io.IOException;
import main.model.Set;
import main.model.SetUserInformation;
import main.model.SetListeners;
import main.model.SetState;
import main.view.student.StudentStatCollect;

//files
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
///
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.FocusListener;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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
        reason = myReason;
        textField = importedTextField;
        dialog = new JDialog(window, true);

        dialog.setResizable(false);
        dialog.setLayout(new FlowLayout());
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(label);

        JButton yesButton = new JButton("Yes");
        yesButton.setVisible(true);
        dialog.add(yesButton);

        JButton noButton = new JButton("Cancel");
        noButton.setVisible(true);
        dialog.add(noButton);
        dialog.setSize(width,height); //250, 120
        
        yesButtonActionListener(yesButton);
        noButtonActionListener(noButton);
        //dialogCloseActionListener();
        dialog.setLocationRelativeTo(window);
        dialog.setVisible(true);

        return yesOrNoDialog;
    }

    private void yesButtonActionListener(JButton yesButton) {
        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
               yesButtonAction();
               //dialog.dispose(); 
            }
        });
        yesOrNoDialog = "yes";
    }

    private void yesButtonAction() {
         if (reason == "deleting") {   
            reasonIsDeletingActionYes();
         }  

         else if (reason == "studentStatsEmpty") {
            reasonIsStudentStatsEmptyYes();
         }

         else if (reason == "closing window") {
            window.dispose();
         }

         else {
            reasonIsChangingUsernameYes();
         }
        // dialog.setVisible(false);
        // dialog.dispose(); 

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
            //TODO
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
                //System.out.println("focuslistener#3");
                SwingUtilities.invokeLater(() -> {
                    System.out.println("focusered");
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

        //TODO
        StudentStatCollect studentStat = new StudentStatCollect();
        //studentStat.visitNextStudentClass();
        studentStat.doNextButtonProcedure();
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
        //set.setDialogBeingDisplayed(false);
        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                noButtonAction();
            }
        });
        yesOrNoDialog = "no";
    }
       
    private void noButtonAction() {
        //System.out.println("dialog stats: "+dialog.getSize());
        window.requestFocusInWindow(); 
        dialog.setVisible(false);
        window.requestFocusInWindow(); 
        if (dialog != null) {
            dialog.dispose();
        }
        window.requestFocusInWindow(); 
        
        FocusListener noFocusListener = new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    System.out.println("focuslistener#4");
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

    private void dialogCloseActionListener() {
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                noButtonAction();
            }
        });
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
                //button.setEnabled(true);
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

    public void removeFocusListeners(JTextField importedTextField) {
        textField = importedTextField;
        FocusListener[] listeners = textField.getFocusListeners();
        int listenerCount = listeners.length;
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
       textField.setForeground(Color.gray);
       textField.setBorder(BorderFactory.createLineBorder(Color.gray, 2));

       Color defaultTextColor = Color.decode("#B0B0B0");
       textField.setSelectedTextColor(defaultTextColor);

       textField.setHorizontalAlignment(JTextField.CENTER);
       //textField.setHorizontalAlignment(JTextField.LEFT);
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
        JDialog dialog = genericPopUpMessage("<html><center>Maximum amount reached.", null, 200 , 100);
            dialog.setLocationRelativeTo(window);
            dialog.setLocation(dialog.getX(), dialog.getY() + 15); 
            dialog.setVisible(true);        
    }

    // public void errorMessageSetUp(String labelWords, int width, int height, JRad) {
    //     JDialog dialog = new JDialog(window, true);
    //     dialog.setResizable(false);
    //     dialog.setLayout(new FlowLayout());
    //     JLabel label = new JLabel(labelWords);
    //     label.setHorizontalAlignment(SwingConstants.CENTER);
    //     dialog.add(label);
    //     JButton okButton = new JButton("OK");
    //     okButton.setVisible(true);
    //     dialog.add(okButton);
    //     dialog.setSize(width,height);
    //     okButton.addActionListener(new ActionListener() {
    //         public void actionPerformed(ActionEvent e) {
    //             dialog.setVisible(false);
    //             dialog.dispose(); 
    //         }
    //     });
        
    //     dialog.setLocationRelativeTo(studentButton);
    //     dialog.setLocation(dialog.getX(), dialog.getY() + 15); 
    //     dialog.setVisible(true);
    // }

    // private void removeLastFocusListener(JTextField textField) {
    //     FocusListener[] listeners = textField.getFocusListeners();
    //     textField.removeFocusListener(listeners[listeners.length-1]);
    // }

}