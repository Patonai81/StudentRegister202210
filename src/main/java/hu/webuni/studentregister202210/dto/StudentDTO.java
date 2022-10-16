package hu.webuni.studentregister202210.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StudentDTO {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private int semester;

}
