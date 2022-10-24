package hu.webuni.studentregister202210.mapper;

import hu.webuni.studentregister202210.model.*;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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
