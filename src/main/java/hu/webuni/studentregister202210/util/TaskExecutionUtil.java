package hu.webuni.studentregister202210.util;

import hu.webuni.studentregister202210.exception.ExternalSystemNotAvailableException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskExecutionUtil {

    public static <T> T execute(Task<T> task,
                                int noOfRetryAttempts,
                                long sleepInterval) {

        if (noOfRetryAttempts < 1) {
            noOfRetryAttempts = 1;
        }

        T result = null;
        for (int retryCount = 1; retryCount <= noOfRetryAttempts; retryCount++) {
            log.info("Executing the task. Attemp#" + retryCount);
            try {
                result = task.execute();
                break;
            } catch (RuntimeException t) {
                log.info("Failed at Retry attempt :" + retryCount + " of : " + noOfRetryAttempts);
                if (retryCount >= noOfRetryAttempts) {
                    log.info("Maximum retrial attempts exceeded.");
                    log.info("Throwing exception to the caller");
                    throw t;
                }
                try {
                    Thread.sleep(sleepInterval);
                } catch (InterruptedException e1) {
                }

            }


        }
    return result;
    }
}
