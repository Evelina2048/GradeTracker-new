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
    JDialog dialog;
    String reason;
    JTextField textField = new JTextField();;
    String yesOrNoDialog;
    
    public Decorator() {
        set = Set.getInstance();
        window = set.getWindow();
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

    public String areYouSureMessageDelete(JTextField importedTextField, String myReason, String text) {
        reason = myReason;
        textField = importedTextField;
        dialog = new JDialog(window, true);
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
        dialog.setSize(250,120);
        
        yesButtonActionListener(yesButton);
        noButtonActionListener(noButton);
        dialogCloseActionListener();
        dialog.setLocationRelativeTo(window);
        dialog.setVisible(true);

        return yesOrNoDialog;
    }

    private void yesButtonActionListener(JButton yesButton) {
        Creator creator = new Creator();
        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
               yesButtonAction();
            }
        });
        yesOrNoDialog = "yes";
    }

    private void yesButtonAction() {
         if (reason == "deleting") {   
            reasonIsDeletingAction();
         }  
         else {
            reasonIsChangingUsername();
         }
    }

    private void reasonIsDeletingAction() {
        Creator create = new Creator();
        textField.setVisible(false);
        JPanel panelForDeleting = new JPanel();
        System.out.println("does textfield be null?: "+textField==null+ "textfield text"+ textField.getText());
        panelForDeleting.add(textField);
        System.out.println(panelForDeleting.getComponentCount() + " :beforeselectedBoxPanelComponenents");
        create.deleteTextBox(panelForDeleting);
        System.out.println(panelForDeleting.getComponentCount() + " :afterselectedBoxPanelComponenents");
        Path filePath = Paths.get("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+set.getUsername()+"/"+textField.getText()+".txt");
        
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        dialog.setVisible(false);
        dialog.dispose(); 

        // create.windowFix();
    }

    private void reasonIsChangingUsername() {
        int caretPosition = textField.getCaretPosition();
        set.setCanContinue(false);
        dialog.setVisible(false);
        dialog.dispose(); 


        if (textField.getText().length() >=28) {
            textField.grabFocus();
        }
        else{
            textField.setSelectionColor(Color.white);
            textField.setCaretPosition(0); // Initially place caret at the beginning
            removeFocusListeners(textField);

            // Add custom focus listener
            textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> {
                        textField.requestFocus();
                        textField.setCaretPosition(caretPosition);
                        //System.out.println("textfield background color: "+textField.getBackground()+ textField.getdefaultselectioncolor());
                });
            }
            });
            textField.requestFocus();
            Color defaultSelectionColor = UIManager.getColor("TextField.selectionBackground");
            textField.setSelectionColor(defaultSelectionColor);
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
        set.setCanContinue(true);
        dialog.setVisible(false);
        dialog.dispose();
        window.requestFocusInWindow(); 
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

    // public void areYouSureMessageEditUsername(JTextField textField) {
    //     Creator creator = new Creator();
    //     JDialog dialog = new JDialog(window, true);
    //     dialog.setLayout(new FlowLayout());
    //     JLabel label = new JLabel("<html><center>Deleting this class will delete <br>its loaded information.<br>Do you wish to continue?");
    //     label.setHorizontalAlignment(SwingConstants.CENTER);
    //     dialog.add(label);

    //     JButton yesButton = new JButton("Yes");
    //     yesButton.setVisible(true);
    //     dialog.add(yesButton);

    //     JButton noButton = new JButton("Cancel");
    //     noButton.setVisible(true);
    //     dialog.add(noButton);

    //     dialog.setSize(250,120);
    //     JPanel panel = new JPanel();
    //     System.out.println("does textfield be null?: "+textField==null+ "textfield text"+ textField.getText());
    //     panel.add(textField);
    //     window.requestFocusInWindow();

    //     yesButton.addActionListener(new ActionListener() {
    //         public void actionPerformed(ActionEvent e) {    
    //                 textField.setVisible(false);
    //                 for (MouseListener listener : textField.getMouseListeners()) {
    //                     textField.removeMouseListener(listener);
    //                 }
                    
    //                 creator.deleteTextBox(panel);
    //                 //Files.deleteIfExists("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/Username/e.txt");
    //                 Path filePath = Paths.get("/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+set.getUsername()+"/"+textField.getText()+".txt");
                    
    //                 try {
    //                     Files.deleteIfExists(filePath);
    //                 } catch (IOException e1) {
    //                     e1.printStackTrace();
    //                 }

    //                 dialog.setVisible(false);
    //                 dialog.dispose();   
    //             // else {
    //             //     removeFocusListeners(textField);
    //             //     textField.requestFocus();
    //             // }
    //         }
    //     });
    //     noButton.addActionListener(new ActionListener() {
    //         public void actionPerformed(ActionEvent e) {
    //             dialog.setVisible(false);
    //             dialog.dispose(); 
    //         }
    //     });
        
    //     dialog.setLocationRelativeTo(window);
    //     //dialog.setLocation(null); 
    //     dialog.setVisible(true);
    // }

    public JDialog genericPopUpMessage(String text) {
        dialog = new JDialog(window, true);
        dialog.setLayout(new FlowLayout());
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(label);
        JButton okButton = new JButton("OK");
        okButton.setVisible(true);
        dialog.add(okButton);
        dialog.setSize(275,100);
        set.setCanContinue(false);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose(); 
                set.setCanContinue(false);
                //textField.getFocusListeners.Count()
                // FocusListener[] listeners = textField.getFocusListeners();
                // int listenerCount = listeners.length;
                // System.out.println("listenercount: "+listenerCount);
            }
        });
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //window.requestFocusInWindow();
                dialog.dispose();
                set.setCanContinue(false);

                //
                //
            }
        });
        return dialog;
        
    }

    public void removeFocusListeners(JTextField importedTextField) {
        textField = importedTextField;
        FocusListener[] listeners = textField.getFocusListeners();
        int listenerCount = listeners.length;
        
        // Iterate through all listeners except the last one
        // for (int i = listenerCount-1; listenerCount-x; i++) {
        //     textField.removeFocusListener(listeners[i]);
        // }

        // for (int i = listenerCount-1; listenerCount-x; i++) {
        //     textField.removeFocusListener(listeners[i]);
        // }

        // textField.removeFocusListener(listeners[listenerCount-1]);
        // textField.removeFocusListener(listeners[listenerCount-2]);
        // textField.removeFocusListener(listeners[listenerCount-3]);
        //textField.removeFocusListener(listeners[listenerCount-4]);
    }

    public void errorMessageSetUp(JRadioButton button) {
        dialog = genericPopUpMessage("<html><center>Please choose an option");
        
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
       //JTextField textField = new JTextField(); //prev 10
       //textField.setSize(new Dimension(10, 50)); // Set the height to 50 pixels
       if (set.getCurrentClass() == "StudentStatCollect.java") {
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
       textField.setText(placeholderText);

       Creator creator = new Creator();
       creator.textFieldFocusListener(textField, placeholderText);
       return textField;
    }

    
}