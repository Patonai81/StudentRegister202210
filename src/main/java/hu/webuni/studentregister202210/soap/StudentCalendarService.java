package hu.webuni.studentregister202210.soap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentCalendarService implements StudentCalendarWS {


    @Override
    public List<StudentCalendarItem> getStudentCalendar(long studentId, LocalDate from, LocalDate to) {
        String methodname = "getStudentCalendar";
        log.info(methodname + " ENTRY");
        log.info(from.toString());
        //TODO WEEK 4 Homework implementation
        log.info(methodname + " EXIT");
        return new ArrayList<>();
    }
}
