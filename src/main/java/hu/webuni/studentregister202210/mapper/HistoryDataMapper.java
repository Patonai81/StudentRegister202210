package hu.webuni.studentregister202210.mapper;

import hu.webuni.studentregister202210.dto.CourseEntityHistoryWrapper;
import hu.webuni.studentregister202210.model.CourseEntityHistoryWrapperCourseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryDataMapper {

    CourseEntityHistoryWrapperCourseDTO toCourseEntityHistoryWrapper(CourseEntityHistoryWrapper input);
}
