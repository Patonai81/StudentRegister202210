package hu.webuni.studentregister202210.web;

import hu.webuni.studentregister202210.dto.CourseAVGData;
import hu.webuni.studentregister202210.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/async")
@AllArgsConstructor
public class AsyncCourseController {

    private final CourseService courseService;

    @GetMapping("/avg")
    @Async
    public CompletableFuture<List<CourseAVGData>> getAVGDataForCourses(){
        CompletableFuture<List<CourseAVGData>> result = courseService.getAVGSemesterForCourses();
        return result;
    }

}
