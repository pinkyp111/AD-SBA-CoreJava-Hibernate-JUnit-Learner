package sba.sms.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
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
    @Column(name = "email")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String email;
    private String name;
    private String password;
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "student_courses",
            joinColumns = {@JoinColumn(name = "student_email")},
            inverseJoinColumns = {@JoinColumn(name = "courses_id")})

    private Set<Course> courses = new HashSet<>();

    public String toString() {
        return ("email" + getEmail() + "Student Name" + getName() + "Password" + getPassword() + "Courses" + getCourses());
    }
}
//toString (exclude collections to avoid infinite loops)
//override equals and hashcode methods (don't use lombok here)
//helper method


