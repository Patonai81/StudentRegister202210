package hu.webuni.studentregister202210.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//@Cacheable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthDate", nullable = false)
    private LocalDate birthDate;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "teacher_course",
            joinColumns = { @JoinColumn(name = "fk_teacher_id") },
            inverseJoinColumns = { @JoinColumn(name = "fk_course_id") })
    Set<Course> teachingCourses= new HashSet<>();


}
