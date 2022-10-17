package hu.webuni.studentregister202210.service;


import hu.webuni.studentregister202210.exception.ExternalSystemNotAvailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class ExternalFreeSemesterService implements RetryAbleTest{

    static int i=0;

    public int getFinancedSemesterNumber(Long externalStudentId) throws ExternalSystemNotAvailableException{
        String methodName = "getFinancedSemesterNumber";
        log.info("called"+i++);
      /*
        log.info(methodName + " " + "started");
        Random random = new Random();
        int result = random.nextInt(0, 10);
        log.info("Returned remaining " + result + " for sutdent " + externalStudentId);
*/
        throw new ExternalSystemNotAvailableException("Hibaaa");
        //return result;
    }


    public void recover(ExternalSystemNotAvailableException e, Long externalId){
        log.info("Recover method has been called");
    }

}
