package hu.webuni.studentregister202210.web;

import hu.webuni.studentregister202210.dto.StudentDTO;
import hu.webuni.studentregister202210.mapper.StudentMapper;
import hu.webuni.studentregister202210.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
@AllArgsConstructor
public class StudentController {

    private StudentService studentService;

    private StudentMapper studentMapper;

    @GetMapping("/{id}")
    public StudentDTO getStudentbyId(@PathVariable("id") Long id){
        return studentMapper.toStudentDTO(studentService.getStudent(id));
    }

}
