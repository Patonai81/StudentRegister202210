package hu.webuni.studentregister202210.repository;

import com.querydsl.core.types.Predicate;
import hu.webuni.studentregister202210.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CourseCustomSearchRepository {
    Page<Course> findAll(Predicate predicate, Pageable pageable, String entityGraphName);

    List<Course> findAll(Predicate predicate, Sort sort, String entityGraphName);
}
