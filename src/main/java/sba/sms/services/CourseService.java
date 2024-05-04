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
        if (course == null) {
            System.out.println("Course cannot be empty. Please provide a valid course!");
        } else {
            Transaction transaction = null;
            try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
                 Session session = factory.openSession()) {
                transaction = session.beginTransaction();
                session.persist(course);
                transaction.commit();
                System.out.println("Courses added successfully");
            } catch (Exception e) {
                if (transaction != null)
                    transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public Course getCourseById(int courseId) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {
            return getCourseFromDB(courseId, session);
        }
    }

    public Course getCourseById(int courseId, Session session) {
        return getCourseFromDB(courseId, session);
    }

    private Course getCourseFromDB(int courseId, Session session) {
        String hql = String.format("FROM Course c WHERE c.course_id=%d", courseId);
        TypedQuery<Course> query = session.createQuery(hql, Course.class);
        List<Course> results = query.getResultList();
        return results != null && !results.isEmpty() ? results.get(0) : null;
    }


    @Override
    public List<Course> getAllCourses() {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {
            return session.createQuery("SELECT c FROM Course c", Course.class).getResultList();
        }
    }

}
