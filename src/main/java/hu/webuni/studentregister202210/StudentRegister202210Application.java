package hu.webuni.studentregister202210;

import hu.webuni.studentregister202210.model.Course;
import hu.webuni.studentregister202210.model.Teacher;
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
import java.util.List;

@AllArgsConstructor
@SpringBootApplication
@EnableCaching
public class StudentRegister202210Application implements CommandLineRunner {


   private CourseService courseService;


    public static void main(String[] args) {
        SpringApplication.run(StudentRegister202210Application.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        /*
        Student.StudentBuilder studentBuilder =  Student.builder();
        Student s1 = studentBuilder.birthDate(LocalDate.now()).name("Szabi").semester(1).enrolledCourses(new HashSet<>()).build();
        studentRepository.save(s1);

        Course.CourseBuilder courseBuilder = Course.builder();
        Course c1 = courseBuilder.name("Konyhai praktikák").students(new HashSet<>()).build();
        c1.getStudents().add(s1);
        s1.getEnrolledCourses().add(c1);
        courseRepository.save(c1);
*/





    }
}
