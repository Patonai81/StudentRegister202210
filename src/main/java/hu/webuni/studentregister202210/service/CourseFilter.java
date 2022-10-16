package hu.webuni.studentregister202210.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseFilter {

    String courseName;
    Long courseId;
    Long courseStudentId;
    Integer semesterPeriodFrom;
    Integer semesterPeriodTo;
    String courseTeacher;

}
