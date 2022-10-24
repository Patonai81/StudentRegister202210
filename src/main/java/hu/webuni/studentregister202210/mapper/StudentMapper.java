package hu.webuni.studentregister202210.mapper;


import hu.webuni.studentregister202210.model.Student;
import hu.webuni.studentregister202210.model.StudentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDTO toStudentDTO(Student student);
}
