package hu.webuni.studentregister202210.web;

import hu.webuni.studentregister202210.mapper.TeacherMapper;
import hu.webuni.studentregister202210.model.TeacherDTO;
import hu.webuni.studentregister202210.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@AllArgsConstructor
@RestController
public class TeacherController implements TeacherControllerApi
{
    private final  TeacherService teacherService;
    private final TeacherMapper teacherMapper;
    private final NativeWebRequest nativeWebRequest;


    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nativeWebRequest);
    }

    @Override
    public ResponseEntity<TeacherDTO> getTeacherbyId(Long id) {
        return ResponseEntity.ok(teacherMapper.toTeacherDTO(teacherService.getTeacher(id)));
    }
}
