package hu.webuni.studentregister202210.service;


import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import hu.webuni.studentregister202210.model.Course;
import hu.webuni.studentregister202210.model.QCourse;
import hu.webuni.studentregister202210.repository.CourseRepository;
import hu.webuni.studentregister202210.repository.CustomCourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@AllArgsConstructor
public class CourseService {

    private CustomCourseRepository customCourseRepository;

    private CourseRepository courseRepository;

    public List<Course> getCourses(CourseFilter courseFilter) {
        return customCourseRepository.findAll(courseFilter);
    }

    public List<Course> getCourses(Predicate courseFilter) {

        return Lists.newArrayList(courseRepository.findAll(courseFilter));
    }

    @Transactional
    public List<Course> getCoursesFull(Predicate courseFilter, Pageable pageable) {
        Page<Course> course=courseRepository.findAll(courseFilter,pageable);
        courseRepository.findAll(QCourse.course.in(course.getContent()),"Course.students");
        courseRepository.findAll(QCourse.course.in(course.getContent()), pageable.getSort(), "Course.teachers");
        return course.getContent();
    }

    public Course getCourse(Long id) {
        return courseRepository.findById(id).get();
    }


}
