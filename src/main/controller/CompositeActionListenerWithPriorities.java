package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JRadioButton;
import javax.swing.Timer;

public class CompositeActionListenerWithPriorities implements ActionListener {
  private Map<Integer, ArrayList<ActionListener>> listeners = 
    new TreeMap<Integer,ArrayList<ActionListener>>();
    private boolean isProcessing = false;
    private String currentClass = null;//"Current class not changed";
    private static CompositeActionListenerWithPriorities instance;
    //this.set = Set.getInstance();
    private CompositeActionListenerWithPriorities() {}
    public static CompositeActionListenerWithPriorities getInstance() {
      if (instance == null) {
          instance = new CompositeActionListenerWithPriorities();
      }
      return instance;
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    if (isProcessing) {
      return; // Prevent re-entry
    }
    isProcessing = true;
    TreeSet<Integer> t = new TreeSet<Integer>();
    t.addAll(listeners.keySet());
    Iterator<Integer> it = t.descendingIterator();
    while(it.hasNext()){
      int x = it.next();
      ArrayList<ActionListener> l = listeners.get(x);
      for(ActionListener a : l){
        a.actionPerformed(e);
      }
    }
    reset();
  }
  
  public boolean deleteActionListener(ActionListener a){
    for(Integer x : listeners.keySet()){
      for(int i=0;i<listeners.get(x).size();i++){
        if(listeners.get(x).get(i) == a){
          listeners.get(x).remove(i);
          return true;
        }
      }
    }
    return false;
  }

  public void addClassActionListener(ActionListener a, int priority, String keyCause, JRadioButton button, String listenerSource){
    //System.out.println("currentclass: "+currentClass+" listenersource "+listenerSource);
    System.out.println("TTT.AAA ");
    //deleteActionListener(a);
    if(!listeners.containsKey(priority)){
      listeners.put(priority,new ArrayList<ActionListener>());
    }
    listeners.get(priority).add(a);

    if (listeners.size() == 2) {
      System.out.println("its being run and stuff 1");
     actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "PerformAllActions"));
    }

    //if (a isinstanceof EnterKey) {
      //set timer for 1 sec
      // if listeners.size() ==2 {
        //actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "PerformAllActions"));
      //}
    //}
    //System.out.println("adding action listener currentclass: "+currentClass+" listenersource "+listenerSource);
    System.out.println("should be here: currentclass "+currentClass+ " listenersource "+listenerSource+" keyCause "+ keyCause);
    if (currentClass == listenerSource && !keyCause.equals("click")) {
      //ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "PerformThisAction");
     // System.out.println("its being run and stuff 2. current class "+ currentClass);
      CompositeActionListenerWithPriorities.this.actionPerformed(new ActionEvent(CompositeActionListenerWithPriorities.this, ActionEvent.ACTION_PERFORMED, "PerformThisActions"));
      CompositeActionListenerWithPriorities.this.actionPerformed(new ActionEvent(CompositeActionListenerWithPriorities.this, ActionEvent.ACTION_PERFORMED, "PerformAllActions"));
    }

    else if (keyCause == "EnterAction" || keyCause == "nextButton") {
      Timer timer = new Timer(1, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Decorator();

            if (listeners.size() == 2) {
                // Correctly reference the outer class for the action event
                //System.out.println("its being run and stuff 3");
                CompositeActionListenerWithPriorities.this.actionPerformed(
                new ActionEvent(CompositeActionListenerWithPriorities.this, ActionEvent.ACTION_PERFORMED, "PerformAllActions"));
            } 
            else if (listeners.size() == 1) {
            //     //decorate.errorMessageSetUp("<html><center>Username already exists.<br> Please choose another.",200,100);


            //     //decorate.errorMessageSetUp(button);

            //     System.out.println("Maybe not needed?");
            //     //decorate.pleaseChooseAnOptionWithRadioButtons(button);
                
            //     //button.setEnabled(false);
            //     //button.setForeground(Color.WHITE); // Set the foreground color to white
            //     //UIManager.put("RadioButton.disabledText", Color.WHITE);
            //     listeners.clear();

            //     //button.setForeground(Color.white);
                
            //     //button.setColor(Color.white);
            //     //decorate.errorMessageSetUp(button);
            //     return;

            } 
            else {
                System.out.println("Something went wrong in CompositeActionListenerWithPriorities addClassActionListener \u00AF\\_(\u30C4)_/\u00AF");
            }
        }
    });
    timer.setRepeats(false);
    timer.start();
      // timer.setRepeats(false);
      // timer.start();
    }
  }

  // public void clearListeners() {

  // }

  public int DEBUGLISTENERSIZE() {
    //System.out.println(listeners.size());
    return listeners.size();
  }

  public interface EnterKeyListener extends ActionListener {
  }

  public void reset() {
    listeners.clear();
    isProcessing = false;
    currentClass = null;
  }

  public void setCurrentClass(String thisClass) {
    if (currentClass == null) {
      currentClass = thisClass;
    }
    System.out.println("setclassto "+currentClass);
  }

  public String getCurrentClass() {
    return currentClass;
  }

  public Map<Integer, ArrayList<ActionListener>> DEBUGLISTENERMAP() {
    return listeners;
  }

  public void TESTFORCECURRENTCLASS(String thisClass) {
    currentClass = thisClass;
  }

  public void TESTRESETACTIONPRIORITIES() {
    instance = null;
  }

}