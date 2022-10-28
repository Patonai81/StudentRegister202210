package hu.webuni.studentregister202210.web;

import com.querydsl.core.types.Predicate;
import hu.webuni.studentregister202210.mapper.CourseFilterMapper;
import hu.webuni.studentregister202210.mapper.CourseMapper;
import hu.webuni.studentregister202210.mapper.HistoryDataMapper;
import hu.webuni.studentregister202210.model.Course;
import hu.webuni.studentregister202210.model.CourseDTO;
import hu.webuni.studentregister202210.model.CourseEntityHistoryWrapperCourseDTO;
import hu.webuni.studentregister202210.model.CourseFilter;
import hu.webuni.studentregister202210.service.CourseService;
import hu.webuni.studentregister202210.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
public class CourseController implements CourseControllerApi{

    private final CourseService courseService;

    private final ImageService imageService;
    private final CourseMapper courseMapper;
    private final NativeWebRequest nativeWebRequest;
    private final PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver;
    private final QuerydslPredicateArgumentResolver querydslPredicateArgumentResolver;
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
    public ResponseEntity<List<CourseEntityHistoryWrapperCourseDTO>> getCourseHistorybyCourseId(Long id) {

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
    public ResponseEntity<CourseEntityHistoryWrapperCourseDTO> getSnapshotCoursebyCourseIdAndTime(Long id, LocalDateTime time) {
        return ResponseEntity.ok(historyDataMapper.toCourseEntityHistoryWrapper(courseService.getCourseSnapshotWithId(id,time)));
    }

    @Override
    public ResponseEntity<List<CourseDTO>> getAllCourses1(Boolean full, Integer page, Integer size, String sort, Long id, String name, String enrolledCoursesName, List<Integer> studentsSemester) {
        boolean isFull = full == null ? false: full;
        Pageable pageable = getPageable("configPageable",0);
        Predicate predicate = getPredicate("configPredicate",0);
        if (isFull){
            return ResponseEntity.ok(courseMapper.toCourseDTOListFull(courseService.getCoursesFull(predicate,pageable)));
        }
        return ResponseEntity.ok(courseMapper.toCourseDTOList(courseService.getCourses(predicate)));
    }

    private Predicate getPredicate(String methodName, int paramIndex) {

        Method method = null;
        try {
            method = this.getClass().getMethod(methodName, Predicate.class);
            MethodParameter methodParameter= new MethodParameter(method,paramIndex);
            return (Predicate) querydslPredicateArgumentResolver.resolveArgument(methodParameter,null,nativeWebRequest,null);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Pageable getPageable(String methodName,int paramIndex) {
        Method method = null;
        try {
            method = this.getClass().getMethod(methodName, Pageable.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        MethodParameter methodParameter= new MethodParameter(method,paramIndex);
        Pageable pageable=pageableHandlerMethodArgumentResolver.resolveArgument(methodParameter,null,nativeWebRequest,null);
        return pageable;
    }

    public void configPageable(@SortDefault("id") Pageable pageable){}

    public void configPredicate(@QuerydslPredicate(root = Course.class) Predicate predicate){}

    @Override
    public ResponseEntity<Long> uploadImageForStudent(Long id, String fileName, MultipartFile content) {
        try {
            return ResponseEntity.ok(imageService.save(content.getInputStream(), fileName, id));
        }catch (Exception e) {
            log.error(e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

