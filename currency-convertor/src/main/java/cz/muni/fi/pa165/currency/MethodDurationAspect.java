package cz.muni.fi.pa165.currency;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class MethodDurationAspect {
    @Around("execution(public * *(..))")
    public void methodDuration(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        joinPoint.proceed();
        long end = System.currentTimeMillis();
        System.out.println("Duration: " + (end -start));

    }
}
