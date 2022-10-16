package hu.webuni.studentregister202210.repository;

import com.querydsl.jpa.JPQLQuery;
import hu.webuni.studentregister202210.model.Course;
import hu.webuni.studentregister202210.model.QCourse;
import hu.webuni.studentregister202210.model.QStudent;
import hu.webuni.studentregister202210.model.QTeacher;
import hu.webuni.studentregister202210.service.CourseFilter;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.types.Projections.*;

@Repository
public class CustomCourseRepositoryImpl extends QuerydslRepositorySupport implements CustomCourseRepository {

    public CustomCourseRepositoryImpl() {
        super(Course.class);
    }

    @Override
    public List<Course> findAll(CourseFilter courseFilter) {

        QStudent student = QStudent.student;
        QTeacher teacher = QTeacher.teacher;
        QCourse course = QCourse.course;

        JPQLQuery<Course> query = from(course)
                .select(fields(Course.class,course.name,course.id))
                .leftJoin(course.students,student)
                .leftJoin(course.teachers,teacher);


        if (courseFilter.getCourseName() != null) {
            query = query.where(course.name.startsWithIgnoreCase(courseFilter.getCourseName()));
        }
        if (courseFilter.getCourseId() != null) {
            query = query.where(course.id.eq(courseFilter.getCourseId()));
        }
        if (courseFilter.getCourseTeacher() != null) {
            query = query.where(course.teachers.any().name.startsWithIgnoreCase(courseFilter.getCourseTeacher()));
        }
        if (courseFilter.getCourseStudentId() != null) {
            query = query.where(course.students.any().id.eq(courseFilter.getCourseStudentId()));
        }
        if (courseFilter.getSemesterPeriodFrom() != null && courseFilter.getSemesterPeriodTo() !=null) {
            query = query.where(course.id.between(courseFilter.getSemesterPeriodFrom(),courseFilter.getSemesterPeriodTo()));
        }

        List<Course> courses = query
                .orderBy(course.id.desc())
                .fetch();

        return courses;
    }
}
