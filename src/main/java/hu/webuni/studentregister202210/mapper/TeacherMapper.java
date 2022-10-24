package hu.webuni.studentregister202210.mapper;

import hu.webuni.studentregister202210.model.Teacher;
import hu.webuni.studentregister202210.model.TeacherDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherDTO toTeacherDTO(Teacher teacher);
}
