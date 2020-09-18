package com.zzxkj.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/*定义切面*/
@Component
@Aspect
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);
    /*设置切面*/
    @Pointcut("execution(* com.zzxkj.blog.controller.*.*(..))")
    public void log() {

    }

    /*方法返回前*/
    @Before("log()")
    /**JoinPoint此反射信息用于跟踪和记录应用程序。*/
    public void doBefore(JoinPoint joinPoint){
        /**ServletRequestAttributes类，从servlet请求和HTTP会话范围访问对象*/
        /**RequestContextHolder,类以线程绑定的形式公开web请求*/
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        /*得到发出请求的URL。*/
        String url = request.getRequestURL().toString();
        /*返回客户端或上一个代理的Internet协议（IP）地址*/
        String ip = request.getRemoteAddr();
        /**getDeclaringTypeName()返回声明此成员的类、接口或方面*/
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        /**这个连接点的参数*/
        Object[] args = joinPoint.getArgs();
        /*整体的信息对象*/
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        logger.info("Request : {}",requestLog);
    }

    /*方法返回后*/
    @After("log()")
    public  void doAfter(){
       // logger.info("---------doAfter--------");
    }
    /*方法返回的内容*/
    @AfterReturning(returning ="result",pointcut ="log()" )
    public  void  doAfterReturn(Object result) {
        logger.info("Result : {}",result);
    }
    private  class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }


}
