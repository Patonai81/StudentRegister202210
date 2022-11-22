package hu.webuni.studentregister202210.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class FinancedSemesterRequestDTO {

    private long studentId;
    private long remainingFreeSemesters;

}
