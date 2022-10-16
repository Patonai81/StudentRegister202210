package hu.webuni.studentregister202210.repository;

import hu.webuni.studentregister202210.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface  StudentRepository extends JpaRepository<Student, Long>, QuerydslPredicateExecutor<Student> {
}
