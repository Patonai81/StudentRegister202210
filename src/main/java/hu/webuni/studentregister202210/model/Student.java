package hu.webuni.studentregister202210.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthDate", nullable = false)
    private LocalDate birthDate;

    @Column(name = "semester", nullable = false)
    private int semester;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "student_course",
            joinColumns = { @JoinColumn(name = "fk_student_id") },
            inverseJoinColumns = { @JoinColumn(name = "fk_course_id") })
    Set<Course> enrolledCourses= new HashSet<>();

}
