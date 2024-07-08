package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

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

  public void addClassActionListener(ActionListener a, int priority){
    deleteActionListener(a);
    if(!listeners.containsKey(priority)){
      listeners.put(priority,new ArrayList<ActionListener>());
    }
    listeners.get(priority).add(a);

    if (listeners.size() == 2) {
     //performAllActions();
     actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "PerformAllActions"));
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

}
