package hu.webuni.studentregister202210.repository;

import hu.webuni.studentregister202210.model.Course;
import hu.webuni.studentregister202210.service.CourseFilter;

import java.util.List;

public interface CustomCourseRepository {
    List<Course> findAll(CourseFilter courseFilter);


}
