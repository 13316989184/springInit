package com.springAop.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*

@Component
@Aspect
class LogAspect {
    private val LOG=LoggerFactory.getLogger(LogAspect::class.java)


    @Pointcut("execution(public * com.springAop.controller.*.*(..))")
    fun logPointCut(){

    }

    @Before("logPointCut()")
    @Throws(Throwable::class)
    fun doBefore(joinPoint: JoinPoint){
        val attributes=RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        val request=attributes.request

        //记录下请求内容
        LOG.info("请求地址："+request.requestURI.toString())
        LOG.info("HTTP METHOD :" +request.method)
        LOG.info("IP:"+request.remoteAddr)
        LOG.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName())
        LOG.info("参数 : " + Arrays.toString(joinPoint.getArgs()))
    }

    @AfterReturning(returning = "ret",pointcut = "logPointCut()")// returning的值和doAfterReturning的参数名一致
    @Throws(Throwable::class)
    fun doAfterReturning(ret:Any){
        LOG.info("返回值："+ ret)
    }

    @Around("logPointCut()")
    @Throws(Throwable::class)
    fun doAround(pjp:ProceedingJoinPoint): Any? {
        val startTime=System.currentTimeMillis()
        val ob=pjp.proceed()
        LOG.info("耗时:"+(System.currentTimeMillis()-startTime))

        return ob
    }





}