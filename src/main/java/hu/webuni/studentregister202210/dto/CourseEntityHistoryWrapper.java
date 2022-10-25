package hu.webuni.studentregister202210.dto;


import hu.webuni.studentregister202210.model.Course;
import hu.webuni.studentregister202210.model.CourseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;

@Data
@AllArgsConstructor
@ToString
public class CourseEntityHistoryWrapper {

   Course course;
   DefaultRevisionEntity revEntity;
   RevisionType revType;
}
