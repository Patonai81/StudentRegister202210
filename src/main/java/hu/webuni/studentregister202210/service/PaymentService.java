package hu.webuni.studentregister202210.service;

import hu.webuni.definition.PaymentMesssage;
import hu.webuni.studentregister202210.model.Student;
import hu.webuni.studentregister202210.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentService {

    private final StudentRepository studentRepository;

    @Transactional
    @JmsListener(destination = "payment", containerFactory = "myFactory")
    public void onPayment(PaymentMesssage paymentMesssage) {

        log.info("Message arrived" + paymentMesssage);
        Student student = studentRepository.findById(paymentMesssage.getStundentId()).get();
        if (null != student){
            log.info("Student is: ");
            log.info(student.toString());
            Long currentBalance=student.getBalance() == null ? 0 : student.getBalance();
            log.info("Current balance is: "+currentBalance);
            student.setBalance(currentBalance+paymentMesssage.getAmounts());
            log.info("New balance is: "+student.getBalance());
        }else{
            log.info("Student with id: "+paymentMesssage.getStundentId()+" NOT FOUND");
        }

    }

}
