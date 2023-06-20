package io.blueharvest.technicalassignment.common.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final ObjectMapper objectMapper;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointcut() {
    }


    @Around(value = "controllerPointcut()")
    public Object aroundRequest(ProceedingJoinPoint jp) throws Throwable {
        final String joinPoints = Arrays.toString(jp.getArgs());
        log.info("Enter: {}.{}() with argument[s] = {}", jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(), joinPoints);
        final Object result = jp.proceed();
        log.info("Exit: {}.{}() with result = {}", jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(), objectMapper.writeValueAsString(result));
        return result;
    }

    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint jp, Throwable e) throws Exception {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ExceptionUtils.getMessage(e))
                .type(e.getClass().getSimpleName())
                .stackTrace(e.getMessage())
                .build();
        log.error("Method {} throws an error action {}",
                jp.getSignature().getName(),
                objectMapper.writeValueAsString(errorMessage));
    }
}
