package com.tenco.blog._core.aop.firewall;

import com.tenco.blog._core.errors.exception.Exception400;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

@Aspect
@Component
@Slf4j
public class ChinaDefense {

    @Before("execution(* com.tenco.blog..*(..))")
    public void ChinaFirewall(JoinPoint joinPoint) throws Throwable {
        log.debug("=== AOP 유효성 검사 시작 ===");
        log.debug("실행 메서드 - {}", joinPoint.getSignature().getName());

        Object[] args = joinPoint.getArgs();

    }
}
