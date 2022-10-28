package hu.webuni.studentregister202210.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseAVGData {
    private long courseID;
    private double avgSemester;
}
