package com.d1nsol.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Aspect
@Component
public class LoggerAop {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggerAop.class);

    /**
     * execution : 표현식에 부합되는 함수를 선택
     * within : 특정 타입에 속하는 함수 전체를 지정
     * 표현식 : [리턴타입] [패키지] [클래스이름] [메소드명](파라미터)
     * ex) * com.d1nsol.controller.*.*(..) : 모든 타입 com.d1nsol.controller.모든 클래스.모든 메소드(모든 파라미터)
     * ex) 
     */

    /**
     * @Pointcut : aspectJ 를 적용할 타겟을 정의. 전체 컨트롤러 함수 대상, 특정 어노테이션을 설정한 함수, 특정 메소드 등
     * ex) PostMapping, GetMapping annotaion 이 설정된 특정 클래스 또는 메소드에 적용
     * ex) @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
     * ex) @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
     */
    
    /**
     * @Before : aspectJ 를 적용할 타겟 메소드가 실행되기 전
     */

     /**
      * @AfterReturning : aspectJ 를 적용할 타겟 메소드가 실행된 후
      */

    /**
     * @Around : aspectJ 를 적용할 타겟 메소드 실행 전, 후 처리 모두
     */

     @Pointcut("within(com.d1nsol.controller..*)")
     public void onRequest() {

     }

     /**
     * @param joinPoint
     */
    @Before("onRequest()")
    public void before(JoinPoint joinPoint) {
        logger.info("================= aspectJ before START =================");
        logger.info("================= aspectJ before END ===================");
    }

    /**
     * @param joinPoint
     * @param result
     */
    @AfterReturning(pointcut = "onRequest()", returning = "result")
    public void AfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("================= aspectJ AfterReturning START =================");
        logger.info("================= aspectJ AfterReturning END ===================");
    }

     @Around("onRequest()")
     public Object logging(ProceedingJoinPoint proceedJoinPoint) throws Throwable{
        logger.error("================= aspectJ Around START ===================");

        Object result = null;
        try {
            result = proceedJoinPoint.proceed(proceedJoinPoint.getArgs());

            MethodSignature methodSignature = (MethodSignature) proceedJoinPoint.getSignature();
            Method method = methodSignature.getMethod();

            RequestMapping requestMapping = (RequestMapping) proceedJoinPoint.getTarget().getClass().getAnnotation(RequestMapping.class);
            String url = requestMapping.value()[0];

            String requestUrl = Stream.of(GetMapping.class, PutMapping.class, DeleteMapping.class, PostMapping.class, RequestMapping.class)
                .filter(mappingClass -> method.isAnnotationPresent(mappingClass))
                .map(mappingClass -> {
                    String httpMethod = null;
                    Annotation annotaion = method.getAnnotation(mappingClass);
                    
                    String[] value;
                    try {
                        value = (String[]) mappingClass.getMethod("value").invoke(annotaion);
                        httpMethod = (mappingClass.getSimpleName().replace("Mapping", "")).toUpperCase();
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                            | NoSuchMethodException | SecurityException e) {
                        return null;
                    }
                    return String.format("%s %s%s", httpMethod, url, value.length > 0 ? value[0] : "");
                }).findFirst().orElse(null);

            CodeSignature codeSignature = (CodeSignature) proceedJoinPoint.getSignature();
            String[] paramNames = codeSignature.getParameterNames();
            Object[] args = proceedJoinPoint.getArgs();
            Map<String, Object> params = new HashMap<>();
            for (int i = 0; i < paramNames.length; i++) {
                params.put(paramNames[i], args[i]);
            }

            ObjectMapper mapper = new ObjectMapper();
            
            logger.info(requestUrl);
            logger.info("parameters : {}", mapper.writeValueAsString(params));
            logger.info("response : {}", mapper.writeValueAsString(result));
            logger.info("================= aspectJ Around END ===================");
            return result;
        } catch (Exception e) {
            logger.error("================= aspectJ Around Exception ===================");
            e.printStackTrace();
            return null;
        } finally {
            // logger.info(getRequestUrl(proceedJoinPoint, proceedJoinPoint.getTarget().getClass()));
            // logger.info("parameters" + JSON.toJSONString(params(proceedJoinPoint)));
            // logger.info("response: " + JSON.toJSONString(result, true));
        }
     }
}
