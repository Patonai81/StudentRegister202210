package hu.webuni.studentregister202210.mapper;

import hu.webuni.studentregister202210.dto.CourseDTO;
import hu.webuni.studentregister202210.dto.StudentDTO;
import hu.webuni.studentregister202210.dto.TeacherDTO;
import hu.webuni.studentregister202210.model.Course;
import hu.webuni.studentregister202210.model.Student;
import hu.webuni.studentregister202210.model.Teacher;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @IterableMapping(qualifiedByName = "basic")
    List<CourseDTO> toCourseDTOList (List<Course> courseList);

    @Named("basic")
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    CourseDTO toCourseDTO(Course course);

    @IterableMapping(qualifiedByName = "full")
    List<CourseDTO> toCourseDTOListFull (List<Course> courseList);

    @Named("full")
    CourseDTO toCourseDTOFull(Course course);

    StudentDTO toStudentDTO(Student student);

    TeacherDTO toTeacherDTO(Teacher teacher);





}
