package hu.webuni.studentregister202210.mapper;

import hu.webuni.studentregister202210.service.CourseFilter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseFilterMapper {

    CourseFilter toCourseFilter(hu.webuni.studentregister202210.model.CourseFilter input);
}
