package com.mth.webquiz.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {
	
	private Logger log;
	
	@Pointcut("execution(* com.mth.webquiz.config..*(..))")
	public void allMethods() {/*Pega todos os métodos de todos os pacotes*/}
	
	@Before("allMethods()")
	public void before(JoinPoint joinPoint) {
		log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
		log.trace("[Iniciou: {}] com parametros [{}]", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
	}
	
	@AfterReturning(pointcut = "allMethods()", returning = "returnValue")
	public void after(JoinPoint joinPoint, Object returnValue) {
		log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
		Signature signature = joinPoint.getSignature();
		log.trace("[Saiu: {}] com retorno tipo [{}] e valor [{}]", signature.toShortString(), 
				((MethodSignature) signature).getReturnType(), returnValue);
	}
	
}
