package main.controller;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.awt.Component;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;

import main.controller.Creator;
import main.model.Set;

import java.awt.Container;


public class Creator {
    private JFrame window;
    private JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private Set set;
    private FileHandler fileHandler = new FileHandler();
    private int textboxCounter;
    private JTextField textField = new JTextField();
    private ArrayList<String> classList = new ArrayList<>();
    private ArrayList<String>textFieldPanelText = new ArrayList<>();
    private JPanel textFieldContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private Boolean focusGranted = true;
    private int attachedBoxes = 0;
    private int maxAttachedBoxes = 0;
    private boolean loaded;
    private String filePath;
    private String text = textField.getText().trim();
    private CreateButton createButton = new CreateButton();
    
    public Creator() {
        this.set = Set.getInstance();
        this.window = set.getWindow();
    }

    public void textFieldFocusListener(JTextField focusTextField, String placeholder) { 
        focusTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                //focusTextField.setHorizontalAlignment(JTextField.LEFT);
                //focusTextField.setText(focusTextField.getText());
                focusTextField.setCaretPosition(focusTextField.getText().length());
                //focusTextField.set

                //System.out.println("focuslistener#1");
                if (focusGranted) {
                    focusTextField.setForeground(Color.gray);
                    //textField.setHorizontalAlignment(JTextField.LEFT);
                    //System.out.println("focusGranted: "+ focusTextField.getText());
                    // if (previousTextbox != null) {
                    //     //previousTextbox.setForeground(Col
                    if (focusTextField.getText().equals(placeholder) && set.getEmptiedState(focusTextField) == false && set.getLoadedState(textField) == false) {
                        //System.out.println("focuslistener#1.1");
                        //textField.setHorizontalAlignment(JTextField.LEFT);
                        focusTextField.setText("");
                        set.setEmptiedState(textField, true);
                        //textField.setForeground(Color.gray);
                    }else if (!focusTextField.getText().equals(placeholder) && !focusTextField.getText().isEmpty() && set.getEmptiedState(textField) == true) {
                        //textField.setHorizontalAlignment(JTextField.LEFT);
                        //System.out.println("focuslistener#1.2 text "+focusTextField.getText());
                        //textField.setForeground(Color.gray);
                        set.setEmptiedState(focusTextField, true);
                    }else if (focusTextField.getText().equals(placeholder) && set.getEmptiedState(textField) == true && set.getLoadedState(textField) == false) {
                        //textField.setHorizontalAlignment(JTextField.LEFT);
                        //System.out.println("focuslistener#1.3");
                        focusTextField.setText(""); // Clear the placeholder text when the field gains focus
                        //textField.setForeground(Color.gray);
                        set.setEmptiedState(focusTextField, true);
                        set.setSaveable(true);
                    }
                    //else if (textField.getText)
                    
                    else {
                        //textField.setHorizontalAlignment(JTextField.LEFT);
                        //System.out.println("something went wrong in Creator class, focus gained");
                        //System.out.println("focuslistener#1.4");
                    }
                    //previousTextbox = textField;
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                focusLostLeftAlign();
                focusTextField.setForeground(Color.lightGray);
                FontMetrics fontMetrics = textField.getFontMetrics(textField.getFont());
                //if (fontmetrics greate than box) {
                    //textField.setHorizontalAlignment(JTextField.LEFT);
                //}
                System.out.println("focus is lost "+fontMetrics.stringWidth(textField.getText())+ text);
                if (fontMetrics.stringWidth(textField.getText()) >= textField.getWidth()) {//if is already less than max with username
                    System.out.println("heyo");
                    focusTextField.setCaretPosition(0);
                    //textField.setHorizontalAlignment(JTextField.LEFT_ALIGNMENT);
                    }
                
                if (focusTextField.getText().isEmpty()) {
                    //textField.setForeground(Color.LIGHT_GRAY);
                    focusTextField.setText(placeholder);
                    set.setEmptiedState(focusTextField, false);
                }
                
                else if (!focusTextField.getText().isEmpty() && set.getEmptiedState(textField) == true){
                    //textField.setForeground(Color.LIGHT_GRAY);
                }

                // else if (!textField.getText().isEmpty()) {

                // }
                // else {
                //     System.out.println("something went wrong in focus lost");
                // }
            }
        });
        focusLost(placeholder);//(textField, placeholder);
    }

    private void focusLost(String placeholder){//(JTextField textField, String placeholder) {
        window.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println("mouselistener #1");
                int topLeftX = 332;
                int topLeftY = 221;
                int topRightX = 467;
                int topRightY = 222;
                int bottomLeftX = 334;
                int bottomLeftY = 270;
                int bottomRightX = 467;
                int bottomRightY = 269;

                int width = Math.abs(topRightX - topLeftX);
                int height = Math.abs(bottomLeftY - topLeftY);

                int x = Math.min(Math.min(topLeftX, topRightX), Math.min(bottomLeftX, bottomRightX));
                int y = Math.min(Math.min(topLeftY, topRightY), Math.min(bottomLeftY, bottomRightY));

                Rectangle newBounds = new Rectangle(x, y, width, height);

                boolean pointNotInTextbox = !newBounds.contains(e.getPoint());

                if (pointNotInTextbox && textField.getText().isEmpty()) {
                    //textField.setHorizontalAlignment(JTextField.LEFT);
                    //textField.setCaretPosition(0);
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                    set.setEmptiedState(textField, false);
                    window.requestFocusInWindow();
                }
                else if (pointNotInTextbox &&  set.getEmptiedState(textField) == true && !textField.getText().isEmpty()) {
                    //textField.setHorizontalAlignment(JTextField.LEFT);
                    //textField.setCaretPosition(0);
                    window.requestFocusInWindow();
                }
                else if (pointNotInTextbox) {
                    //textField.setHorizontalAlignment(JTextField.LEFT);
                    //textField.setCaretPosition(0);
                    window.requestFocusInWindow();

                }
                else {
                    System.out.println("something went wrong");
                    //textField.setHorizontalAlignment(JTextField.LEFT);
                }
            }
        });
    }

    private void focusLostLeftAlign() {
        textField.grabFocus();
        textField.setCaretPosition(0);
        window.requestFocusInWindow();
    }

    // public void writeFolderToFile() {
    //     String username = set.getUsername();
    //     String folderPath = "/Users/evy/Documents/GradeTracker-new/src/main/view/UserInfo/StudentInfo/"+username; //+ username;
    //     File folder = new File(folderPath);
    //     if (!folder.exists()) {
    //         folder.mkdirs();
    //     }
    // }

    // public void decideIfWrite(Component component, BufferedWriter writer) {
    //     //filePath =
    //     Decorator decorator = new Decorator();
    //     textField = (JTextField) component;
    //     Boolean studentStatNonWritablePlaceholder = set.getEmptiedState(textField) == false && set.getCurrentClass() == "StudentStatCollect.java" && !textField.getText().equals("Credits (Optional)");

    //     if (set.getEmptiedState(textField) == true) {
    //         tryToWrite(writer);
    //     }
    //     else if (studentStatNonWritablePlaceholder) {
    //         removeUnwritablePlaceholders();
            
    //         if (set.getCanContinue()) {
    //             //JDialog dialog = decorator.genericPopUpMessage("<html><center>Must fill in placeholder.<br>Will not save sections with placeholders",null);
                
    //             //JDialog dialog = 
    //             decorator.areYouSureMessage(null, "studentStatsEmpty", "Hello friends");
    //             // dialog.setLocationRelativeTo(window);
    //             // dialog.setLocation(dialog.getX(), dialog.getY() + 15); 
    //             // dialog.setVisible(true);
    //             return;
    //         }
    //     }
    // }

    // public void makeUsernameFile() {

    // }

    // private void tryToWrite(BufferedWriter writer) {
    //     text = textField.getText().trim();
    //     if (!text.isEmpty()) {
    //         try {
    //             if (attachedBoxes == maxAttachedBoxes) {
    //                 //System.out.println("writing in decide if write: "+text);
    //                 classList.add(text);
    //                 writer.write(text + "\n");
    //             }
    //             else {
    //                 attachedBoxes++;
    //             }
    //         } catch (IOException e) {
    //             e.printStackTrace();
    //         }                             
    //         //System.out.println("should be writing");
    //     }
    // }

    // public void writeTextToFile(){//JPanel textFieldPanel) {//(String importedFilePath, JPanel textFieldPanel) {;
    //     //filePath = importedFilePath;

    //     System.out.println("6666 in write text to file");
    //     set.setCanContinue(true);
    //     //System.out.println("Step4: begin writeTextToFile."+ set.getCurrentPanelList());
    //     debugPrintPanel();
    //     set.getUsername();
    //     tryToWriteWithoutAppend();
    //     //System.out.println("in write text to file: "+set.getCurrentPanelList());
    //     set.setClassList(classList);
    // }

    // private void tryToWriteWithoutAppend() {
    //     //writer = new BufferedWriter(new FileWriter(filePath));
    //     filePath = set.getFilePath();
    //     System.out.println("7777 in tryToWriteWithoutAppend");
    //     try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
    //         System.out.println("8888 in trytowritewithoutappend");
    //         if (!classList.isEmpty()) {
    //            classList.clear();
    //         }
    //         for (Component component : textFieldPanel.getComponents()) {
    //             System.out.println("9999 in trytowritewithoutappend");
    //             if (component instanceof JTextField ) {
    //                 tryToWriteTextFieldWithoutAppend(component, writer);
    //             }
    //             else if (component instanceof JPanel) {
    //                 //System.out.println("JPanel");
    //                 //filePath =
    //                 writeTextToFileWithAppend((JPanel) component);
    //             }
    //             else {
    //                 System.out.println("component is something else. Something went wrong"+ component.getClass().getName());
    //             }
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    // private void tryToWriteTextFieldWithoutAppend(Component component, BufferedWriter writer) { //i think for student stat
    //     JTextField textField = (JTextField) component;
    //     if (set.getEmptiedState(textField) == true && attachedBoxes == maxAttachedBoxes) {
    //         String text = textField.getText().trim();
    //         if (!text.isEmpty()) {
    //             if (attachedBoxes == maxAttachedBoxes) {
    //                 classList.add(text);
    //                 try {
    //                     writer.write(text + "\n");
    //                 } catch (IOException e) {
    //                     e.printStackTrace();
    //                 }                             
    //                 //System.out.println("should be writing");
    //             }
    //             else {
    //                 attachedBoxes++;
    //             }
    //     }
    //     else if (set.getEmptiedState(textField) == false) {
    //         seeHowManyPlaceholdersToSkip();
    //     }}
    // }

    // public void clearFile(String filePath) {
    //     // Clear the file content or reset relevant fields/variables
    //     try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
    //         writer.write(""); // Write empty string to clear the file
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    // public void deleteLines(String importedFilePath, String text) {
    //     filePath = importedFilePath;
    //     //read to array list
    //     ArrayList<String> lines = fileHandler.readFileToList(filePath);
    //     if (!lines.isEmpty()) {            
    //         lines.remove(lines.size() -1);
    //         classList.remove(classList.size() -1);
    // }
    //     fileHandler.writeArrayListToFile(filePath, lines);
    // }

    public JTextField createTextBox(String placeholder, String my_type, Boolean boxLoaded) {
        loaded = boxLoaded;
        int width = 144;
        int height = 50;
        Decorator decorate = new Decorator();
        textboxCounter++;
        if (textboxCounter <= 30 && my_type.equals("JTextField")) {
            textField = decorate.decorateTextBox(placeholder);
            //set.setEmptiedState(textField, false);
            createButton.addDocumentListenerForSaving(textField);
            textFieldPanel.add(textField); 
            textFieldPanelText.add(textField.getText());
            textField.setPreferredSize(new Dimension(width, height));
            window.add(textFieldPanel);
            textFieldFocusListener(textField, placeholder);
            windowFix();
        }
        else if (my_type.equals("JLabel")) {
            JLabel toAddType = new JLabel(placeholder);
            textFieldPanel.add(toAddType);
        }
        checkIfLoadedAndAction();
        set.setTextFieldPanel(textFieldPanel);
        return textField;
    }

    private void checkIfLoadedAndAction() {
        if (loaded) {
            set.setEmptiedState(textField, true);
            set.setLoadedState(textField, true);
            //textField.setForeground(Color.pink);

            textField.setForeground(Color.lightGray);
        }
        else if (loaded == false) {
            set.setLoadedState(textField, false);
        }
    }

    public void setTextFieldContainer(JPanel thisTextFieldPanel) {
        textFieldPanel = thisTextFieldPanel;
    }

    public JPanel getTextFieldContainer() {
        return textFieldPanel;
    }

    // public void resetTextFieldContainer() {

    // }

    public void deleteTextBox(JPanel container) {
        System.out.println("this is accessed, right?");
        int componentsCount = container.getComponentCount();
        if (componentsCount > 0) {
            Component lastComponent = container.getComponent(componentsCount - 1);
            container.remove(lastComponent); 

            //TODO remove from classlist
            //set.removeClassFromClassList();

            windowFix();
            textboxCounter--;
        }
    }

    public void windowFix() {
        window.revalidate(); 
        window.repaint();
    }

    public void hideContainer() {
        textFieldContainer.setVisible(false);
        textFieldPanel.setVisible(false);
    }

    // public void debugPrintPanel() {
    //     for (Component component : textFieldPanel.getComponents()) {
    //         if (component instanceof JTextField) {
    //             JTextField textField = (JTextField) component;
    //             System.out.println("debugPrintPanel " + textField.getText());
    //         }
    //     }
    // }

    public void setClassList() {
        set.setClassList(classList);
    }
    
    public JPanel typeBox(String placeholder, String my_type, Boolean boxLoaded) {
        hideContainer(); //needed unless jlabels will be missing
        loaded = boxLoaded;
        //System.out.println("");
        JPanel northTypePanel = new JPanel(new BorderLayout());
        JPanel gradeTypePanel = new JPanel(new BorderLayout()); //second border layout so things not cut out


        if (my_type.equals("JTextField")) {
            JTextField toAddType = createTextBox(placeholder, "JTextField", loaded);
            gradeTypePanel.add(toAddType);
        }

        else if(my_type.equals("JLabel")) {
            System.out.println("hi creating jlabel for "+placeholder+" which is loaded? "+boxLoaded);
            JLabel toAddType = new JLabel(placeholder);
            //set.setDEBUGBOX(toAddType);
            gradeTypePanel.add(toAddType);
        }
        gradeTypePanel.setPreferredSize(new Dimension( 155,50));
        northTypePanel.add(gradeTypePanel, BorderLayout.NORTH);
        windowFix();

        return northTypePanel;
    }

    public String goIntoPanel(JPanel panel, int index) {
        Container container = panel;
        if (index >= container.getComponentCount()) { //check component is not null
            return "does not exist";
        }
        Component component = container.getComponent(index);
        if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                String text = textField.getText();
                return text;
            } 
        else if (component instanceof JPanel) {
                JPanel jpanel = (JPanel) component;
                String text = goIntoPanel(jpanel, 0);
                if (text != null) {
                    return text;
                }
            }
            System.out.println("none of these" +component.getClass().getName());
            return "something went wrong goIntoPanel";
        }

        public JTextField goIntoPanelReturnTextbox(JPanel panel, int index) {
            Container container = panel;
            Component component = container.getComponent(index);
            if (component instanceof JTextField) {
                    JTextField textField = (JTextField) component;
                    return textField;
                } 
            else if (component instanceof JPanel) {
                    JPanel jpanel = (JPanel) component;
                    goIntoPanel(jpanel, 0);
                }
                System.out.println("none of these" +component.getClass().getName());
                return textField;
            }
        
            public Boolean checkIfHasPlaceholder(JPanel textFieldPanel2) {
                JTextField textField = new JTextField();
                for (int i = 0; i < textFieldPanel2.getComponentCount()-1; i++) {
                    Component component = textFieldPanel2.getComponent(i);
                    if (component instanceof JTextField) {
                        textField = (JTextField) component;
                    } 
                    else if (component instanceof JPanel) {
                        textField = (JTextField) goIntoPanelReturnTextbox(textFieldPanel2, i);
                    }
                    if (set.getEmptiedState(textField) == false) {
                        return false;
                    }}
                    return true;
            }

    public void deleteMouseListeners(JTextField textField, int numberToDelete) {
        MouseListener[] listeners = textField.getMouseListeners();
        for (int i = 5; i >= 0; i--) {
            textField.removeMouseListener(listeners[i]);
        }
    }
}