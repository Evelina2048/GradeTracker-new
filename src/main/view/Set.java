package main.view;

import java.util.ArrayList;

import javax.swing.JPanel;

public class Set {
    private String username;
    private ArrayList<String> classList;

    public Set () {

    }

    public void setUsername(String my_username) {
       username = my_username;
    }

    public String getUsername() {
        return username;
    }

    public void setClassList(ArrayList<String> classList) {
        this.classList = classList;
    }

    public ArrayList<String> getClassList() {
        return classList;
    }

}