package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Student is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'student' in the database. A Student object contains fields that represent student
 * login credentials and a join table containing a registered student's email and course(s)
 * data. The Student class can be viewed as the owner of the bi-directional relationship.
 * Implement Lombok annotations to eliminate boilerplate code.
 */

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "student")
public class Student {
    @Column(name = "email", length = 50)
    @Id
    private String email;
    @NonNull
    @Column(length = 50)
    private String name;
    @NonNull
    @Column(length = 50)
    private String password;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinTable(name = "student_courses",
            joinColumns = {@JoinColumn(name = "student_email")},
            inverseJoinColumns = {@JoinColumn(name = "courses_id")})
    private Set<Course> courses = new HashSet<>();

    @Override
    public String toString() {
        return ("email" + getEmail() + "Student Name" + getName() + "Password" + getPassword() + "Courses" + getCourses());
    }

    public Student(String email, @NonNull String name, @NonNull String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

   //equals method compares email,studentName,password whenever a new student is added to the set of students
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(email, student.email) && Objects.equals(name, student.name) && Objects.equals(password, student.password) && Objects.equals(courses, student.courses);
    }

    //hashCode() method calculates hash value based on these columns
    @Override
    public int hashCode() {
        return Objects.hash(email, name, password, courses);
    }

    //Helper method used to add course to set of courses student is taking
    public void addCourse(Course c) {
        this.getCourses().add(c);
    }

}


