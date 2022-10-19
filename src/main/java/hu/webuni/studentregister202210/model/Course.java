package hu.webuni.studentregister202210.model;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//@Cacheable
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@NamedEntityGraph(name = "Course.students", attributeNodes = @NamedAttributeNode(value = "students"))
@NamedEntityGraph(name = "Course.teachers", attributeNodes = @NamedAttributeNode(value = "teachers"))
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "enrolledCourses")
    private Set<Student> students= new HashSet<>();

    @ManyToMany(mappedBy = "teachingCourses")
    private Set<Teacher> teachers= new HashSet<>();
}
