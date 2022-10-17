package hu.webuni.studentregister202210.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class ExternalFreeSemesterService {

    public int getFinancedSemesterNumber(Long externalStudentId) {
        String methodName = "getFinancedSemesterNumber";
        log.info(methodName + " " + "started");
        Random random = new Random();
        int result = random.nextInt(0, 10);
        log.info("Returned remaining " + result + " for sutdent " + externalStudentId);
        return result;
    }

}
