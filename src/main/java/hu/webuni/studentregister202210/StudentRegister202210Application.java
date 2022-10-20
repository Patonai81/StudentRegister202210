package hu.webuni.studentregister202210;

import hu.webuni.studentregister202210.model.Course;
import hu.webuni.studentregister202210.model.Student;
import hu.webuni.studentregister202210.model.Teacher;
import hu.webuni.studentregister202210.repository.CourseRepository;
import hu.webuni.studentregister202210.repository.StudentRepository;
import hu.webuni.studentregister202210.repository.TeacherRepository;
import hu.webuni.studentregister202210.service.CourseFilter;
import hu.webuni.studentregister202210.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
@SpringBootApplication
@EnableCaching
public class StudentRegister202210Application implements CommandLineRunner {


   private CourseService courseService;

   private StudentRepository studentRepository;

   private CourseRepository courseRepository;

   private TeacherRepository teacherRepository;

    public static void main(String[] args) {
        SpringApplication.run(StudentRegister202210Application.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        /*
        Student.StudentBuilder studentBuilder =  Student.builder();
        Student s1 = studentBuilder.birthDate(LocalDate.now()).name("Szabi tanuló").semester(1).build();
        Student savedStudent=studentRepository.save(s1);

        Teacher.TeacherBuilder teacherBuilder = Teacher.builder();
        Teacher t1 = teacherBuilder.birthDate(LocalDate.of(1988,4,23)).name("Tícsör").build();
        Teacher savedTeacher= teacherRepository.save(t1);

        Course.CourseBuilder courseBuilder = Course.builder();
        Course c1 = courseBuilder.name("Envers tanfolyam").build();
        Course savedCourse= courseRepository.save(c1);

        courseService.addStudent(savedCourse.getId(),savedStudent);
        courseService.addTeacher(savedCourse.getId(),savedTeacher);
*/





    }
}
