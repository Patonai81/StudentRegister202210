package hu.webuni.studentregister202210.web;


import com.querydsl.core.types.Predicate;
import hu.webuni.studentregister202210.mapper.CourseMapper;
import hu.webuni.studentregister202210.dto.CourseDTO;
import hu.webuni.studentregister202210.model.Course;
import hu.webuni.studentregister202210.service.CourseFilter;
import hu.webuni.studentregister202210.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/course")
@AllArgsConstructor
public class CourseController {

    private CourseService courseService;

    private CourseMapper courseMapper;


    @PostMapping
    public List<CourseDTO> getAllCourses(@RequestBody  CourseFilter courseFilter){
            return courseMapper.toCourseDTOList(courseService.getCourses(courseFilter));
    }

    @GetMapping
    public List<CourseDTO> getAllCourses(@RequestParam Optional<Boolean> full, @QuerydslPredicate(root = Course.class) Predicate predicate,@SortDefault("id") Pageable pageable){
        boolean isFull = full.orElse(false);
        if (isFull){
            return courseMapper.toCourseDTOListFull(courseService.getCoursesFull(predicate,pageable));
        }
        return courseMapper.toCourseDTOList(courseService.getCourses(predicate));
    }

    @GetMapping("/{id}")
    public CourseDTO getCoursebyId(@PathVariable("id") Long id){
        return courseMapper.toCourseDTO(courseService.getCourse(id));
    }

}
