package hu.webuni.studentregister202210.model;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Audited
//@Cacheable
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

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthDate", nullable = false)
    private LocalDate birthDate;

    @Column(name = "semester", nullable = false)
    private int semester;

    @Column(name = "usedFreeSemesters", nullable = false)
    private int usedFreeSemesters;

    @Column(name = "externalId", nullable = false)
    private int externalId;

    @OneToOne
    private Image image;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "student_course",
            joinColumns = { @JoinColumn(name = "fk_student_id") },
            inverseJoinColumns = { @JoinColumn(name = "fk_course_id") })
    Set<Course> enrolledCourses= new HashSet<>();

}
