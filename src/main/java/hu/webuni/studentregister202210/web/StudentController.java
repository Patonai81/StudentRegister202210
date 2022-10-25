package hu.webuni.studentregister202210.web;

import hu.webuni.studentregister202210.mapper.StudentMapper;
import hu.webuni.studentregister202210.model.StudentDTO;
import hu.webuni.studentregister202210.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class StudentController implements StudentControllerApi{


    private final StudentService studentService;
    private final StudentMapper studentMapper;
    private final NativeWebRequest nativeWebRequest;
    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nativeWebRequest);
    }

    @Override
    public ResponseEntity<StudentDTO> getStudentbyId(Long id) {
        return ResponseEntity.ok(studentMapper.toStudentDTO(studentService.getStudent(id)));

    }
}
