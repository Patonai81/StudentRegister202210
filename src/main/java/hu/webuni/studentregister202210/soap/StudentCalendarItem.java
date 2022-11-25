package hu.webuni.studentregister202210.soap;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentCalendarItem {
    private String subject;
    private LocalDate start;


}
