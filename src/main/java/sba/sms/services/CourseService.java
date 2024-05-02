package sba.sms.services;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;

import java.util.List;

/**
 * CourseService is a concrete class. This class implements the
 * CourseI interface, overrides all abstract service methods and
 * provides implementation for each method.
 */
public class CourseService implements CourseI {

    @Override
    public void createCourse(Course course) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        CourseService courseService = new CourseService();
        try {
            Course course1 = new Course();
            Course course2 = new Course();
            Course course3 = new Course();
            Course course4 = new Course();
            Course course5 = new Course();
            Course course6 = new Course();
            Course course7 = new Course();
            Course course8 = new Course();

            session.persist(course1);
            session.persist(course2);
            session.persist(course3);
            session.persist(course4);
            session.persist(course5);
            session.persist(course6);
            session.persist(course7);
            session.persist(course8);

            transaction.commit();
            System.out.println("Courses added successfully");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Course getCourseById(int courseId) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM Course c WHERE c.course_id>0";
        TypedQuery<Course> query = session.createQuery(hql, Course.class);
        List<Course> results = query.getResultList();
        // System.out.printf("%s%13s%17s%34s%21s%n", "|User Id", "|Full name", "|Email", "|Password", "|Salary");
        for (Course c : results) {
            System.out.println(c.getCourse_id() + " " + c.getCoursename() + " " + c.getInstructorname() + " " + c.getStudents());
        }

        return (Course) results;
    }


    @Override
    public List<Course> getAllCourses() {

        return null;
    }
}
