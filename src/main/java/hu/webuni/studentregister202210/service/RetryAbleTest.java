package hu.webuni.studentregister202210.service;


import hu.webuni.studentregister202210.exception.ExternalSystemNotAvailableException;
import org.springframework.stereotype.Service;

@Service
public interface RetryAbleTest {

   // @Retryable(value = ExternalSystemNotAvailableException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    public int getFinancedSemesterNumber(Long externalStudentId) throws ExternalSystemNotAvailableException;

   // @Recover
    public void recover(ExternalSystemNotAvailableException e, Long externalId);


    }
