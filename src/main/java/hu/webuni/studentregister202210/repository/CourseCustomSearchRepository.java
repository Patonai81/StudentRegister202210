package hu.webuni.studentregister202210.repository;

import com.querydsl.core.types.Predicate;
import hu.webuni.studentregister202210.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseCustomSearchRepository {
    Page<Course> findAll(Predicate predicate, Pageable pageable, String entityGraphName);
}
