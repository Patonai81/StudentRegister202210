package hu.webuni.studentregister202210.repository;

import hu.webuni.studentregister202210.model.Course;
import hu.webuni.studentregister202210.model.QCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Iterator;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long>, QuerydslPredicateExecutor<Course>,
        QuerydslBinderCustomizer<QCourse>, CourseCustomSearchRepository {
    @Override
    default void customize(QuerydslBindings bindings, QCourse course){

        bindings.bind(course.name).first( (path,value) -> path.startsWithIgnoreCase(value));
        bindings.bind(course.id).first(((path, value) -> path.eq(value)));
        bindings.bind(course.teachers.any().name).first((path, value) -> path.startsWith(value));
        bindings.bind(course.students.any().id).first((path, value) -> path.eq(value));

        bindings.bind(course.students.any().semester).all((path, values) ->{
            if (values.size()!= 2)
                return Optional.empty();
            Iterator<? extends Integer> iterator = values.iterator();
            return Optional.of(path.between(iterator.next(), iterator.next()));
        } );

    }
}
