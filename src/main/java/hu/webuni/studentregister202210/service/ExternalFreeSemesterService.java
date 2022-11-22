package hu.webuni.studentregister202210.service;


import hu.webuni.definition.PaymentMesssage;
import hu.webuni.studentregister202210.aspect.CustomAOPRetry;
import hu.webuni.studentregister202210.dto.FinancedSemesterRequestDTO;
import hu.webuni.studentregister202210.exception.ExternalSystemNotAvailableException;
import hu.webuni.studentregister202210.wsclient.GetFinancedSemesterNumber;
import hu.webuni.studentregister202210.wsclient.SemesterXmlWs;
import hu.webuni.studentregister202210.wsclient.SemesterXmlWsImplService;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.Topic;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ExternalFreeSemesterService implements RetryAbleTest {


    public static final String SZABI_TOPIC="SzabiReplyTo";
    public static final String REQUEST_QUEUE="SEMESTER_IN";
    @Autowired
    @Qualifier("semesterTemplate")
    private JmsTemplate jmsTemplate;


    @CustomAOPRetry(retryAttempts = 5, sleepInterval = 500)
    public int getFinancedSemesterNumber(Long externalStudentId) throws ExternalSystemNotAvailableException {
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

    @Scheduled(cron = "${externalSystem.scheduler.cron}")
    public void askFreeSemester(){
        log.info("Scheduler is RUNNING...");
        getFinancedSemesterNumberJMS(1L);
    }


    public int getFinancedSemesterNumberJMS(Long externalStudentId) {
        String methodName = "getFinancedSemesterNumberWS";
        log.info(methodName + " " + "started");

        FinancedSemesterRequestDTO req = new FinancedSemesterRequestDTO();
        req.setStudentId(externalStudentId);

      /*  Topic myTopic = jmsTemplate.execute(session -> {
            return session.createTopic(SZABI_TOPIC);
        });

      */
        jmsTemplate.convertAndSend(REQUEST_QUEUE, req, message -> {
            message.setJMSReplyTo(new ActiveMQTopic(SZABI_TOPIC));
            return message;
        });
        log.info("Payment has been succesfully sent....");

        return 0;
    }
    @JmsListener(destination = SZABI_TOPIC,containerFactory = "semesterFactory")
    public void getFinancedSemesterNumberJMSReceiver(FinancedSemesterRequestDTO paymentMesssage) {
        log.info("Message has been arrived");
        log.info(paymentMesssage.toString());
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
        final AtomicInteger rsp = new AtomicInteger();
        SemesterXmlWs ws = new SemesterXmlWsImplService().getSemesterXmlWsImplPort();
        GetFinancedSemesterNumber req = new GetFinancedSemesterNumber();
        req.setArg0(externalStudentId);
        ws.getFinancedSemesterNumberAsync(req, response -> {
            try {
                rsp.set(response.get().intValue());
                log.info("Response has been arrived from Async call: " + rsp);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
        for (int i = 0; i < 2000; i++) {
            log.info("Waiting for the response");
        }
        return rsp.get();
    }


    public void recover(ExternalSystemNotAvailableException e, Long externalId) {
        log.info("Recover method has been called");
    }

}
