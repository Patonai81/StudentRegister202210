package hu.webuni.studentregister202210.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public  @interface CustomAOPRetry {

    public int retryAttempts() default 3;

    public long sleepInterval() default 1000L;

}
