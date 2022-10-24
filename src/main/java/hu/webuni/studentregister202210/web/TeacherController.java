package hu.webuni.studentregister202210.web;


import hu.webuni.studentregister202210.mapper.TeacherMapper;
import hu.webuni.studentregister202210.model.TeacherDTO;
import hu.webuni.studentregister202210.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher")
@AllArgsConstructor
public class TeacherController {

    private TeacherService teacherService;

    private TeacherMapper teacherMapper;

    @GetMapping("/{id}")
    public TeacherDTO getTeacherbyId(@PathVariable("id") Long id){
        return teacherMapper.toTeacherDTO(teacherService.getTeacher(id));
    }

}
