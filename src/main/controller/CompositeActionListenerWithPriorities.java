package main.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JRadioButton;
import javax.swing.Timer;
import javax.swing.UIManager;

import main.model.Set;

public class CompositeActionListenerWithPriorities implements ActionListener {
  private Map<Integer, ArrayList<ActionListener>> listeners = 
    new TreeMap<Integer,ArrayList<ActionListener>>();
    private boolean isProcessing = false;
    private Set set = Set.getInstance();
    //this.set = Set.getInstance();


  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("%%%"+set.getExistingOrNew());
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

  public void addClassActionListener(ActionListener a, int priority, String keyCause, JRadioButton button){
    
    //deleteActionListener(a);
    if(!listeners.containsKey(priority)){
      listeners.put(priority,new ArrayList<ActionListener>());
    }
    listeners.get(priority).add(a);

    if (listeners.size() == 2) {
     actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "PerformAllActions"));
    }

    //if (a isinstanceof EnterKey) {
      //set timer for 1 sec
      // if listeners.size() ==2 {
        //actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "PerformAllActions"));
      //}
    //}

    if (keyCause == "EnterAction" || keyCause == "nextButton") {
      Timer timer = new Timer(1, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Decorator decorate = new Decorator();
            if (listeners.size() == 2) {
                // Correctly reference the outer class for the action event
                CompositeActionListenerWithPriorities.this.actionPerformed(
                new ActionEvent(CompositeActionListenerWithPriorities.this, ActionEvent.ACTION_PERFORMED, "PerformAllActions"));
            } else if (listeners.size() == 1) {
                //errorMessageSetUp("<html><center>Username already exists.<br> Please choose another.",200,100);
                
                //button.setEnabled(false);
                //button.setForeground(Color.WHITE); // Set the foreground color to white
                //UIManager.put("RadioButton.disabledText", Color.WHITE);
                listeners.clear();

                //button.setForeground(Color.white);
                
                //button.setColor(Color.white);
                decorate.errorMessageSetUp(button);

            } else {
                System.out.println("Something went wrong in CompositeActionListenerWithPriorities addClassActionListener \u00AF\\_(\u30C4)_/\u00AF");
            }
        }
    });
    timer.setRepeats(false);
    timer.start();
      timer.setRepeats(false);
      timer.start();
    }
  }

  // public void clearListeners() {

  // }

  public void DEBUGLISTENERSIZE() {
    System.out.println(listeners.size());
  }

  private void performAllActions() {
    actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "PerformAllActions"));
  }

  public interface EnterKeyListener extends ActionListener {
  }

}
