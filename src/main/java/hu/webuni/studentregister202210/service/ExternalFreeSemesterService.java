package hu.webuni.studentregister202210.service;


import hu.webuni.studentregister202210.aspect.CustomAOPRetry;
import hu.webuni.studentregister202210.exception.ExternalSystemNotAvailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class ExternalFreeSemesterService implements RetryAbleTest{

    @CustomAOPRetry(retryAttempts=5,sleepInterval = 500)
    public int getFinancedSemesterNumber(Long externalStudentId) throws ExternalSystemNotAvailableException{
        String methodName = "getFinancedSemesterNumber";

        log.info(methodName + " " + "started");
        Random random = new Random();
        int result = random.nextInt(0, 10);
        log.info("Returned remaining " + result + " for student " + externalStudentId);

        // The values true and false are produced with (approximately) equal probability.
        if (random.nextBoolean())
            throw new ExternalSystemNotAvailableException("A háttérrendszer nem elérhető");

        return result;
    }


    public void recover(ExternalSystemNotAvailableException e, Long externalId){
        log.info("Recover method has been called");
    }

}
