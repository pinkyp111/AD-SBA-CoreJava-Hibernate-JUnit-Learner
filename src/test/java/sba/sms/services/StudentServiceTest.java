package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class StudentServiceTest {

    @BeforeAll
    public static void setup() {
        StudentService studentService = new StudentService();
        studentService.createStudent(new Student("reema@gmail.com", "reema brown", "password"));

        CourseService courseService = new CourseService();
        courseService.createCourse(new Course("Machine Learning", "Francisco Edgar"));
    }

    @Test
    public void checkCourseInDatabase() {
        List<Course> courseFromDB = new CourseService().getAllCourses()
                .stream()
                .filter(c -> c.getCourseName().equals("Machine Learning"))
                .collect(Collectors.toList());
        assertEquals("Machine Learning", !courseFromDB.isEmpty() ? courseFromDB.get(0).getCourseName() : "");
    }

    @Test
    public void testForDuplicateEntriesInJoinTable() {
        List<Course> courseFromDB = new CourseService().getAllCourses()
                .stream()
                .filter(c -> c.getCourseName().equals("Machine Learning"))
                .collect(Collectors.toList());
        int courseId = !courseFromDB.isEmpty() ? courseFromDB.get(0).getCourse_id() : 0;
        StudentService ss = new StudentService();
        ss.registerStudentToCourse("reema@gmail.com", courseId);
        ss.registerStudentToCourse("reema@gmail.com", courseId);
        List<Course> courses = ss.getStudentCourses("reema@gmail.com")
                .stream()
                .filter(c -> c.getCourseName().equals("Machine Learning"))
                .collect(Collectors.toList());
        assertEquals(1, courses.size());
    }

}