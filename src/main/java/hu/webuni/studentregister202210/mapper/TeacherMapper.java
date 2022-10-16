package hu.webuni.studentregister202210.mapper;

import hu.webuni.studentregister202210.dto.TeacherDTO;
import hu.webuni.studentregister202210.model.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherDTO toTeacherDTO(Teacher teacher);
}
