package com.lgu.ccss.common.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
public class DebugAop {
	private static final Logger logger = LoggerFactory.getLogger(DebugAop.class);
	
	@Pointcut("execution(* doService(..))")
	private void pointCutService() {
		
	}
	
	@Around("pointCutService()")
	public Object debugService(ProceedingJoinPoint joinPoint) throws Throwable {

		Object[] args = joinPoint.getArgs();
		
		if (logger.isDebugEnabled()) {
			if (args.length == 2) {
				logger.debug("[REQUEST] ==================================================\ndeviceCtn({}) {} {}\n"
						+ "============================================================\n", args[0], args[1]);
			} else {
				logger.debug("[REQUEST] ==================================================\ndeviceCtn({}) {}\n"
						+ "============================================================\n", args[0]);
			}
		}

		Object obj = null;
		try {
			obj = joinPoint.proceed();
			return obj;
			
		} finally {
			if (logger.isDebugEnabled()) {
				logger.debug("[RESPONSE] =================================================\ndeviceCtn({}) {}\n"
					+ "===========================================================\n", obj);
			}
		}
	}
}
