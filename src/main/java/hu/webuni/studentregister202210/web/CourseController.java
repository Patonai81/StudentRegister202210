package hu.webuni.studentregister202210.web;

import hu.webuni.studentregister202210.mapper.CourseFilterMapper;
import hu.webuni.studentregister202210.mapper.CourseMapper;
import hu.webuni.studentregister202210.mapper.HistoryDataMapper;
import hu.webuni.studentregister202210.model.CourseDTO;
import hu.webuni.studentregister202210.model.CourseEntityHistoryWrapperDTO;
import hu.webuni.studentregister202210.model.CourseFilter;
import hu.webuni.studentregister202210.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CourseController implements CourseControllerApi{

    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final NativeWebRequest nativeWebRequest;

    private final HistoryDataMapper historyDataMapper;
    private final CourseFilterMapper courseFilterMapper;
    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nativeWebRequest);
    }

    @Override
    public ResponseEntity<List<CourseDTO>> getAllCourses(CourseFilter courseFilter) {
        return ResponseEntity.ok(courseMapper.toCourseDTOList(courseService.getCourses(courseFilterMapper.toCourseFilter(courseFilter))));
    }

    @Override
    public ResponseEntity<List<CourseDTO>> getAllCourses1(Object predicate, Pageable pageable, Boolean full) {
        return CourseControllerApi.super.getAllCourses1(predicate, pageable, full);
    }

    @Override
    public ResponseEntity<List<CourseEntityHistoryWrapperDTO>> getCourseHistorybyCourseId(Long id) {

        return ResponseEntity.ok( courseService.getHistoryOfCourseWithId(id).stream().map(
                item -> {
                  return historyDataMapper.toCourseEntityHistoryWrapper(item); }
        ).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<CourseDTO> getCoursebyId(Long id) {
        return ResponseEntity.ok(courseMapper.toCourseDTO(courseService.getCourse(id)));
    }

    @Override
    public ResponseEntity<CourseEntityHistoryWrapperDTO> getSnapshotCoursebyCourseIdAndTime(Long id, LocalDateTime time) {
        return ResponseEntity.ok(historyDataMapper.toCourseEntityHistoryWrapper(courseService.getCourseSnapshotWithId(id,time)));
    }
}
