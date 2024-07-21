package main.model;

public class SetUserInformation {
    private String username;
    private String existingOrNew;
    private String studentOrTeacher;

    //Below is for singleton design pattern
    // Static variable to hold the single instance of the class
    private static SetUserInformation instance;

    // Private constructor to prevent instantiation
    private SetUserInformation() {}

    // Public method to provide access to the single instance
    public static SetUserInformation getInstance() {
        if (instance == null) {
            instance = new SetUserInformation();
        }
        return instance;
    }

    public void setUsername(String my_username) {
       username = my_username;
    }

    public String getUsername() {
        return username;
    }

    public void setExistingOrNew(String myExistingOrNew) {
        existingOrNew = myExistingOrNew;
    }

    public String getExistingOrNew() {
        return existingOrNew;
    }

    public void setStudentOrTeacher(String myStudentOrTeacher) {
        studentOrTeacher = myStudentOrTeacher;
    }

    public String getStudentOrTeacher() {
        return studentOrTeacher;
    }
}