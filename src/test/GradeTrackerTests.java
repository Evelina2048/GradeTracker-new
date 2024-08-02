import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import view.student.StudentClasses;
import model.Set;

public class GradeTrackerTests {

    @Test
    public void test() {
        // MyClass myClass = new MyClass();
        // int result = myClass.someMethod();
        // assertEquals(42, result);  // Adjust the expected value to fit your test

        String myString = "Hello<3";

        StudentClasses studentClasses = new StudentClasses();
        Set set = Set.getInstance();


        assertEquals(set.getWindow(), null);
    }


    @Test
    public void testCaseCode() {
       System.out.println("This is the testcase in this class");
       String str1="This is the testcase in this class";
       assertEquals("This is the testcase in this class", str1);
   }

    // Add more test methods as needed
}
