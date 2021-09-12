package ru.red.reactivelearn.aop;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Log
@Aspect
@Component
public class RestControllerLoggingAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void isRestController() {}


    @Around("isRestController()")
    public Object advice(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = simplifyClassName(joinPoint.getTarget().toString());
        String endpoint = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        log.info(
                String.format(
                        "%s call on the endpoint {%s} with arguments %s",
                        className,
                        endpoint,
                        args
                )
        );
        try {
            Object result = joinPoint.proceed(joinPoint.getArgs());
            if (result instanceof Publisher) {
                Flux.from((Publisher<?>) result)
                        .collectList()
                        .subscribe(
                                next -> logReturns(className, endpoint, args, next),
                                error -> logException(className, endpoint, args, error)
                        );
            } else {
                logReturns(className, endpoint, args, result);
            }

            return result;
        } catch (Throwable throwable) {
            logException(className, endpoint, args, throwable);
            throw throwable;
        }
    }

    private String simplifyClassName(String className) {
        String[] path = className.split("\\.");
        for (int i = 0; i < path.length - 1; i++) {
            path[i] = String.valueOf(path[i].charAt(0));
        }
        return String.join(".", path);
    }

    private void logReturns(String className, String endpoint, String args, Object result) {
        log.info(
                String.format(
                        "%s call on the endpoint {%s} with arguments %s returned - %s",
                        className,
                        endpoint,
                        args,
                        result.toString()
                )
        );
    }


    private void logException(String className, String endpoint, String args, Throwable throwable) {
        log.warning(
                String.format(
                        "%s call on the endpoint {%s} with arguments %s produced exception - %s",
                        className,
                        endpoint,
                        args,
                        throwable.toString()
                )
        );
    }
}
