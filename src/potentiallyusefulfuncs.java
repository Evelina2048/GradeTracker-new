// import javax.swing.BorderFactory;
// import javax.swing.JButton;
// import javax.swing.JDialog;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JTextField;
// import java.awt.BorderLayout;
// import java.awt.Color;
// import javax.swing.JPanel;
// import java.awt.Rectangle;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
// import java.awt.event.MouseListener;
// import java.awt.event.FocusAdapter;
// import java.awt.event.FocusEvent;

// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.concurrent.atomic.AtomicBoolean;
// import java.io.BufferedWriter;
// import java.io.File;
// import java.awt.Component;

// import java.awt.Dimension;
// import java.awt.FlowLayout;

// import main.controller.Creator;
// import main.model.Set;
// import javax.swing.event.DocumentEvent;
// import javax.swing.event.DocumentListener;

// import java.awt.Container;


// public class Creator {
//     private JFrame window;
//     private JPanel backNextButtonsPanel;
//     private JPanel textFieldPanel= new JPanel(new FlowLayout(FlowLayout.LEFT));
//     private JTextField previousTextbox;
//     private Set set;
//     private FileHandler fileHandler = new FileHandler();
//     //private Decorator decorate = new Decorator();
//     private int textboxCounter;
//     private JTextField textField = new JTextField();
//     private String placeholder;
//     private ArrayList<String> classList = new ArrayList<>();
//     private JButton saveButton;
//     private ArrayList<String>textFieldPanelText = new ArrayList<>();
//     private JPanel textFieldContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
//     private Boolean focusGranted = true;
//     private int attachedBoxes = 0;
//     private int maxAttachedBoxes = 0;
//     private boolean loaded;
//     private String filePath;
//     //private BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true));
//     private String text = textField.getText().trim();
    
//     public Creator() {
//         this.set = Set.getInstance();
//         this.window = set.getWindow();
//     }

   
    
//     ///
//     // public void traversePanelAndWrite(Container container) {//(String importedFilePath, Container container) {
//     //     //filePath = importedFilePath;
//     //     filePath = set.getFilePath();
//     //     try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
//     //         System.out.println("final filepath: "+ filePath);
//     //         traverseComponents(writer, container);
//     //     } catch (IOException e) {
//     //         e.printStackTrace();
//     //     }
//     // }
    
//     // private void traverseComponents(BufferedWriter writer, Container container) throws IOException {
//     //     for (Component component : container.getComponents()) {
//     //         if (component instanceof JTextField) {
//     //             JTextField textField = (JTextField) component;
//     //             String text = textField.getText();
    
//     //             if (!text.isBlank() && !text.equals(placeholder) && set.getEmptiedState(textField) && !classList.contains(text)) {
//     //                 classList.add(text);   
//     //                 writer.write(text + "\n");
//     //             }
//     //         } else if (component instanceof Container) {
//     //             traverseComponents(writer, (Container) component);
//     //         }
//     //     }
//     // }

//     ///
