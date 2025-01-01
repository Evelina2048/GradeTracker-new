package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JRadioButton;
import javax.swing.Timer;

public class CompositeActionListenerWithPriorities implements ActionListener {
  private Map<Integer, ArrayList<ActionListener>> listeners =
    new TreeMap<Integer,ArrayList<ActionListener>>();
    private boolean isProcessing = false;
    private String currentClass = null; //"Current class not changed";
    private static CompositeActionListenerWithPriorities instance;
    private CompositeActionListenerWithPriorities() {}
    public static CompositeActionListenerWithPriorities getInstance() {
      if (instance == null) {
          instance = new CompositeActionListenerWithPriorities();
      }
      return instance;
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("in action performed");
    if (isProcessing) {
      return; // Prevent re-entry
    }
    isProcessing = true;
    try {
        TreeSet<Integer> priorities = new TreeSet<>(listeners.keySet()); // Create a copy of the key set
        for (int priority : priorities.descendingSet()) {
            ArrayList<ActionListener> listenerList = new ArrayList<>(listeners.get(priority)); // Make a copy of the list
            for (ActionListener listener : listenerList) {
              listener.actionPerformed(e); // Execute the action
              System.out.println("actionn performed");
            }
        }
    } finally {
        System.out.println("about to reset.");
        reset(); // Reset the processing state
        isProcessing = false;
    }
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
    if(!listeners.containsKey(priority)){
      listeners.put(priority,new ArrayList<ActionListener>());
    }
    listeners.get(priority).add(a);

    if (listeners.size() == 2) {
      actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "PerformAllActions"));
    }

    if (currentClass == listenerSource && !keyCause.equals("click")) {
      CompositeActionListenerWithPriorities.this.actionPerformed(new ActionEvent(CompositeActionListenerWithPriorities.this, ActionEvent.ACTION_PERFORMED, "PerformThisActions"));
    }

    else if (keyCause == "EnterAction" || keyCause == "nextButton") {
      Timer timer = new Timer(1, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Decorator();
            if (listeners.size() == 2) {
              CompositeActionListenerWithPriorities.this.actionPerformed(
              new ActionEvent(CompositeActionListenerWithPriorities.this, ActionEvent.ACTION_PERFORMED, "PerformAllActions"));
            }
            else if (listeners.size() == 1) {
              CompositeActionListenerWithPriorities.this.actionPerformed(
              new ActionEvent(CompositeActionListenerWithPriorities.this, ActionEvent.ACTION_PERFORMED, "PerformAllActions")); //added 12/21 because would not work when clicking next>
            }
        }
    });
    timer.setRepeats(false);
    timer.start();
    }
    System.out.println("end of action options");
  }

  public int DEBUGLISTENERSIZE() {
    return listeners.size();
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