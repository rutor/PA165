package cz.muni.fi.pa165.currency;

import javax.inject.Named;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Named
@Aspect
public class MethodDurationAspect {
    
    @Around("execution(public * *(..))")
    public Object methodDuration(ProceedingJoinPoint joinPoint) throws Throwable {
    	System.out.println("Executing: " + joinPoint.getSignature().toString());
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        System.out.println("Duration: " + (end - start) + " ms");
        return result;
    }
}
