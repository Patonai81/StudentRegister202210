package hu.webuni.studentregister202210.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;

@Data
@AllArgsConstructor
@ToString
public class CourseEntityHistoryWrapper<T> {

   T course;
   DefaultRevisionEntity revEntity;
   RevisionType revType;
}
