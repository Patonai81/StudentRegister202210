package hu.webuni.studentregister202210.service;


import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import hu.webuni.studentregister202210.dto.CourseEntityHistoryWrapper;
import hu.webuni.studentregister202210.model.Course;
import hu.webuni.studentregister202210.model.QCourse;
import hu.webuni.studentregister202210.model.Student;
import hu.webuni.studentregister202210.model.Teacher;
import hu.webuni.studentregister202210.repository.CourseRepository;
import hu.webuni.studentregister202210.repository.CustomCourseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CourseService {

    private CustomCourseRepository customCourseRepository;

    private CourseRepository courseRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Course> getCourses(CourseFilter courseFilter) {
        return customCourseRepository.findAll(courseFilter);
    }

    public List<Course> getCourses(Predicate courseFilter) {
        return Lists.newArrayList(courseRepository.findAll(courseFilter));
    }

    @Transactional
    public void addTeacher(Long courseId, Teacher teacher) {
        Course course = courseRepository.findById(courseId).get();
        if (course.getTeachers() == null) {
            course.setTeachers(new HashSet<>());
        }
        course.getTeachers().add(teacher);
        if (teacher.getTeachingCourses() == null) {
            teacher.setTeachingCourses(new HashSet<>());
        }
        teacher.getTeachingCourses().add(course);
    }

    @Transactional
    public void addStudent(Long courseId, Student student) {
        Course course = courseRepository.findById(courseId).get();
        if (null == course.getStudents()) {
            course.setStudents(new HashSet<>());
        }
        course.getStudents().add(student);

        if (null == student.getEnrolledCourses()) {
            student.setEnrolledCourses(new HashSet<>());
        }

        student.getEnrolledCourses().add(course);
    }


    @Cacheable("courseCache")
    @Transactional
    public List<Course> getCoursesFull(Predicate courseFilter, Pageable pageable) {
        Page<Course> course = courseRepository.findAll(courseFilter, pageable);
        courseRepository.findAll(QCourse.course.in(course.getContent()), "Course.students");
        courseRepository.findAll(QCourse.course.in(course.getContent()), pageable.getSort(), "Course.teachers");
        return course.getContent();
    }

    public Course getCourse(Long id) {
        return courseRepository.findById(id).get();
    }

    @Transactional
    @SuppressWarnings({"unchecked"})
    public List<CourseEntityHistoryWrapper> getHistoryOfCourseWithId(Long id) {
        List result = AuditReaderFactory.get(entityManager).createQuery().forRevisionsOfEntity(Course.class, false, true).add(AuditEntity.property("id").eq(id)).getResultList();

        return (List<CourseEntityHistoryWrapper>) result.stream().map(item -> {
            Object[] itemArray = (Object[]) item;
            Course course = (Course) itemArray[0];
            //Forcing to load connected
            course.getStudents().size();
            course.getTeachers().size();
            DefaultRevisionEntity defaultRevisionEntity = (DefaultRevisionEntity) itemArray[1];
            RevisionType revisionType = (RevisionType) itemArray[2];

            CourseEntityHistoryWrapper courseEntityHistoryWrapper = new CourseEntityHistoryWrapper<Course>(course, defaultRevisionEntity, revisionType);
            log.info(courseEntityHistoryWrapper.toString());
            return courseEntityHistoryWrapper;
        }).collect(Collectors.toList());

    }


    @Transactional
    @SuppressWarnings({"unchecked"})
    public CourseEntityHistoryWrapper getCourseSnapshotWithId(Long id, LocalDateTime snapshot) {

        List result = AuditReaderFactory.get(entityManager).createQuery().forRevisionsOfEntity(Course.class, false, false)
                .add(AuditEntity.revisionProperty("timestamp").le(Date.from(snapshot.atZone(ZoneId.systemDefault()).toInstant()).getTime()))
                .add(AuditEntity.revisionProperty("timestamp").maximize()
                        .add(AuditEntity.property("id").eq(id))).getResultList();

        Object[] itemArray = (Object[]) result.get(0);
        Course course = (Course) itemArray[0];
        //Forcing to load connected
        course.getStudents().size();
        course.getTeachers().size();
        DefaultRevisionEntity defaultRevisionEntity = (DefaultRevisionEntity) itemArray[1];
        RevisionType revisionType = (RevisionType) itemArray[2];

        return  new CourseEntityHistoryWrapper<Course>(course, defaultRevisionEntity, revisionType);
    }

}
