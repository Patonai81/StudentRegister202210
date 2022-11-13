package hu.webuni.studentregister202210.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Audited
//@Cacheable
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Student extends UserSecurity{

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

    @Builder
    private Student(String userName, String password, Set<String> roles, Long balance, String name, LocalDate birthDate, int semester, int usedFreeSemesters, int externalId, Image image, Set<Course> enrolledCourses) {
        super(userName, password, roles);
        this.balance = balance;
        this.name = name;
        this.birthDate = birthDate;
        this.semester = semester;
        this.usedFreeSemesters = usedFreeSemesters;
        this.externalId = externalId;
        this.image = image;
        this.enrolledCourses = enrolledCourses;
    }
}
