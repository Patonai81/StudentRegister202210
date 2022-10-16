package hu.webuni.studentregister202210.repository;

import hu.webuni.studentregister202210.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
}
