package hu.webuni.studentregister202210.service;

import hu.webuni.studentregister202210.model.Student;
import hu.webuni.studentregister202210.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class StudentService {

    StudentRepository studentRepository;

    ExternalFreeSemesterService externalFreeSemesterService;

    public Student getStudent(Long id) {
        return studentRepository.findById(id).get();
    }

    public Set<Student> getAllStudents(){
        return studentRepository.findAll().stream().collect(Collectors.toSet());
    }


    @Scheduled(cron = "${externalSystem.scheduler.cron}")
    public void updateUsedSemesters(){
    String methodName = "updateUsedSemesters";
        log.info(methodName+" started");
        getAllStudents().stream().forEach( student -> {
         log.info("Updating student :"+student.toString());
         int usedForStudent = externalFreeSemesterService.getFinancedSemesterNumber(student.getId());
         log.info("Used semester for student: "+usedForStudent);
         student.setUsedFreeSemesters(usedForStudent);
         studentRepository.save(student);
        });
        log.info(methodName+" ended");
    }
}
