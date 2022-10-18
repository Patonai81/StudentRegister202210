package hu.webuni.studentregister202210.aspect;

import hu.webuni.studentregister202210.util.Task;
import hu.webuni.studentregister202210.util.TaskExecutionUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
@Aspect
public class RetryAspect {

    @Around("@annotation(hu.webuni.studentregister202210.aspect.CustomAOPRetry)")
    public Object interceptor(ProceedingJoinPoint pjp) {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        CustomAOPRetry retry = method.getDeclaredAnnotation(CustomAOPRetry.class);
        int retryAttempts = retry.retryAttempts();
        long sleepInterval = retry.sleepInterval();

        Task<Object> task = () -> {
            try {
                return pjp.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };

        return TaskExecutionUtil.execute(task, retryAttempts, sleepInterval);
    }
}

