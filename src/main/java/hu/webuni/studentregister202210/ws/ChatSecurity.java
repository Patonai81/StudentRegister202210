package hu.webuni.studentregister202210.ws;

import hu.webuni.studentregister202210.model.Student;
import hu.webuni.studentregister202210.model.Teacher;
import hu.webuni.studentregister202210.model.UserSecurity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import javax.transaction.Transactional;

@Slf4j
public class ChatSecurity {

    @Transactional
    public boolean canSubscribe(Authentication authentication, int id){
        UserSecurity userSecurity = (UserSecurity)authentication.getPrincipal();
        if (userSecurity instanceof Student){
            return ((Student)userSecurity).getEnrolledCourses().stream().anyMatch( item ->  item.getId().intValue()==id);
        }
        if (userSecurity instanceof Teacher){
            return ((Teacher)userSecurity).getTeachingCourses().stream().anyMatch( item ->  item.getId().intValue()==id);
        }

        return false;
    }
}
