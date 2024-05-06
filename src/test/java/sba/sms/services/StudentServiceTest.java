package sba.sms.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sba.sms.models.Course;
import sba.sms.models.Student;

import java.util.List;


class StudentServiceTest {

    @BeforeAll
    public static void setup() {
        StudentService studentService = new StudentService();
        studentService.createStudent(new Student("reema@gmail.com", "reema brown", "password"));

        CourseService courseService = new CourseService();
        courseService.createCourse(new Course("Machine Learning", "Francisco Edgar"));
    }

   //Test-Case 1:Check whether the above added course is persisted into the DB.
    @Test
    public void checkCourseInDatabase() {
        List<Course> courseFromDB = new CourseService().getAllCourses();
        boolean found= false;
        for(Course c: courseFromDB) {
            if(c.getCourseName().equals("Machine Learning")) {
                found = true;
                break;
            }
        }
        Assertions.assertTrue(found, "Found course in database");
    }
    //Test-Case 2: Checking Whether student added is persisted into DB using student email
    @Test
    public void checkStudentInDatabase(){
        Student studentFromDB;
        studentFromDB = new StudentService().getStudentByEmail("reema@gmail.com");
        boolean found=false;
        if(studentFromDB.getEmail().equals("reema@gmail.com")) {
            found = true;
        }
            Assertions.assertTrue(found, "Found student in database");

    }
}