package bg.guardiankiller.moviessocialapp.joinpoint;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AspectDefinitions {

    @Around("@annotation(ann)")
    public Object executionTime(ProceedingJoinPoint point, TrackExecutionTime ann) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return point.proceed();
        } finally {
            long end = System.currentTimeMillis();
            log.info("Method {} took {} s", point.getSignature(), (end - start)/1000.0);
        }
    }
}
