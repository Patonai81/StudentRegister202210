package hu.webuni.studentregister202210.mapper;

import hu.webuni.studentregister202210.dto.CourseEntityHistoryWrapper;
import hu.webuni.studentregister202210.model.CourseEntityHistoryWrapperDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryDataMapper {

    CourseEntityHistoryWrapperDTO toCourseEntityHistoryWrapper(CourseEntityHistoryWrapper input);
}
