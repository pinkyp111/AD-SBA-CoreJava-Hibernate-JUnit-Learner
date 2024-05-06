package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Course is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'course' in the database. A Course object contains fields that represent course
 * information and a mapping of 'courses' that indicate an inverse or referencing side
 * of the relationship. Implement Lombok annotations to eliminate boilerplate code.
 */

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "course")
public class Course {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int course_id;//primary key
    @NonNull
    @Column(length = 50)
    private String courseName;
    @NonNull
    @Column(length = 50)
    private String instructorName;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER, mappedBy = "courses")
    private Set<Student> students = new HashSet<>();

    public String toString() {
        return ("id" + getCourse_id() + "Course Name" + getCourseName() + "Instructor Name" + getInstructorName());
    }

    /**
     *      equals method compares course_id,courseName,instructorName whenever a new course is added to the set of courses
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return course_id == course.course_id && Objects.equals(courseName, course.courseName) && Objects.equals(instructorName, course.instructorName) && Objects.equals(students, course.students);
    }

    /**
     *     hashCode() method calculates hash value based on these columns
     */
    @Override
    public int hashCode() {
        return Objects.hash(course_id, courseName, instructorName);
    }

    /**
     *          Helper method used to add student to the student set
     */
    public void addStudent(Student s) {
        this.getStudents().add(s);
    }
}