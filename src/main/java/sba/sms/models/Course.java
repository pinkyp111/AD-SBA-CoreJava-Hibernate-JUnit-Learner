package sba.sms.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Course is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'course' in the database. A Course object contains fields that represent course
 * information and a mapping of 'courses' that indicate an inverse or referencing side
 * of the relationship. Implement Lombok annotations to eliminate boilerplate code.
 */

@Entity
//@Data
@NoArgsConstructor
//@RequiredArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@Table(name = "course")
public class Course {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int course_id;//primary key
    private String Coursename;
    private String Instructorname;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "courses")
    private Set<Student> students = new HashSet<>();

    public Course(String Coursename, String InstructorName) {
        this.Coursename = Coursename;
        this.Instructorname = InstructorName;
    }

    public String toString() {
        return ("id" + getCourse_id() + "Course Name" + getCoursename() + "Instructor Name" + getInstructorname());
    }

}
//toString (exclude collections to avoid infinite loops)
//override equals and hashcode methods (don't use lombok here)
//helper method