package hu.webuni.studentregister202210.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    private Long id;
    private String name;
    private Set<StudentDTO> students;
    private Set<TeacherDTO> teachers;

}
