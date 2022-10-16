package hu.webuni.studentregister202210.service;

import hu.webuni.studentregister202210.model.Student;
import hu.webuni.studentregister202210.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {

    StudentRepository studentRepository;

    public Student getStudent(Long id) {
        return studentRepository.findById(id).get();
    }
}
