package hu.webuni.studentregister202210.soap;

import javax.jws.WebService;
import java.time.LocalDate;
import java.util.List;

@WebService
public interface StudentCalendarWS {
    //http://localhost:8083/szabi/services/calendar?wsdl
    List<StudentCalendarItem> getStudentCalendar(long studentId, LocalDate from, LocalDate to);
}
