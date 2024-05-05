package sba.sms.services;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * StudentService is a concrete class. This class implements the
 * StudentI interface, overrides all abstract service methods and
 * provides implementation for each method. Lombok @Log used to
 * generate a logger file.
 */

public class StudentService implements StudentI {

    @Override
    public List<Student> getAllStudents() {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {
            String hql = "FROM Student ";
            TypedQuery<Student> query = session.createNamedQuery(hql, Student.class);
            return query.getResultList();
        }
    }

    @Override
    public void createStudent(Student student) {
        if (student != null) {
            Transaction transaction = null;
            try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
                 Session session = factory.openSession()) {
                transaction = session.beginTransaction();
                session.persist(student);
                transaction.commit();
                System.out.println("Student added successfully");
            } catch (Exception e) {
                if (transaction != null)
                    transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {
            return getStudentFromDB(email, session);
        }
    }

    public Student getStudentByEmail(String email, Session session) {
        return getStudentFromDB(email, session);
    }

    private Student getStudentFromDB(String email, Session session) {
        String hql = String.format("FROM Student s WHERE s.email='%s'", email);
        TypedQuery<Student> query = session.createQuery(hql, Student.class);
        List<Student> results = query.getResultList();
        return results != null && !results.isEmpty() ? results.get(0) : null;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        Student studentFromDB = getStudentByEmail(email);
        return studentFromDB.getPassword().equals(password);
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Transaction transaction = null;
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {
            Student registeredStudent = getStudentByEmail(email, session);
            Course courseToUpdate = new CourseService().getCourseById(courseId, session);
            if (registeredStudent != null && courseToUpdate != null) {
                transaction = session.beginTransaction();
                registeredStudent.addCourse(courseToUpdate);
                courseToUpdate.addStudent(registeredStudent);
                session.persist(registeredStudent);
                session.persist(courseToUpdate);
                transaction.commit();
                System.out.println("Successfully added course to student!");
            }
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        Student regStudent = getStudentByEmail(email);
        if (regStudent != null) {
            return new ArrayList<>(regStudent.getCourses());
        }
        return null;
    }
}
