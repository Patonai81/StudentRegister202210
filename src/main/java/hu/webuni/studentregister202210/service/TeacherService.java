package hu.webuni.studentregister202210.service;


import hu.webuni.studentregister202210.model.Teacher;
import hu.webuni.studentregister202210.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeacherService {

    TeacherRepository teacherRepository;

    public Teacher getTeacher(Long id) {
        return teacherRepository.findById(id).get();
    }
}
