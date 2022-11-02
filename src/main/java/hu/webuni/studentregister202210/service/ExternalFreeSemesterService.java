package hu.webuni.studentregister202210.service;


import hu.webuni.studentregister202210.aspect.CustomAOPRetry;
import hu.webuni.studentregister202210.exception.ExternalSystemNotAvailableException;
import hu.webuni.studentregister202210.wsclient.GetFinancedSemesterNumber;
import hu.webuni.studentregister202210.wsclient.SemesterXmlWs;
import hu.webuni.studentregister202210.wsclient.SemesterXmlWsImplService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

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

    public int getFinancedSemesterNumberWS(Long externalStudentId) {
        String methodName = "getFinancedSemesterNumberWS";
        log.info(methodName + " " + "started");

        SemesterXmlWs ws = new SemesterXmlWsImplService().getSemesterXmlWsImplPort();
        GetFinancedSemesterNumber req = new GetFinancedSemesterNumber();
        req.setArg0(externalStudentId);
        return ws.getFinancedSemesterNumber(req);

    }

    public int getFinancedSemesterNumberWSAsync(Long externalStudentId) {
        String methodName = "getFinancedSemesterNumberWS Async";
        log.info(methodName + " " + "started");
        final AtomicInteger rsp= new AtomicInteger();
        SemesterXmlWs ws = new SemesterXmlWsImplService().getSemesterXmlWsImplPort();
        GetFinancedSemesterNumber req = new GetFinancedSemesterNumber();
        req.setArg0(externalStudentId);
        ws.getFinancedSemesterNumberAsync(req,response -> {
            try {
                rsp.set(response.get().intValue());
                log.info("Response has been arrived from Async call: "+ rsp);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
        for (int i = 0; i< 2000; i++) {
            log.info("Waiting for the response");
        }
        return rsp.get();
    }




    public void recover(ExternalSystemNotAvailableException e, Long externalId){
        log.info("Recover method has been called");
    }

}
