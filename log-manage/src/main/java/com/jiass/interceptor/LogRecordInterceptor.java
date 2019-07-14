package com.jiass.interceptor;

import com.jiass.annotation.LogRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

/**
 * 日志记录注解实现（注解）
 */
@Component
public class LogRecordInterceptor implements HandlerInterceptor {

    private Logger log = LoggerFactory.getLogger(getClass());
    private ThreadLocal<Date> threadLocal = new ThreadLocal();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Class<?> clazz = ((HandlerMethod) handler).getBean().getClass();
        String className = clazz.getSimpleName();
        Method method = ((HandlerMethod) handler).getMethod();
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(currentDate);
        threadLocal.set(currentDate);
        String threadName = Thread.currentThread().getName();
        if (method.isAnnotationPresent(LogRecord.class)) {
            String methodName = method.getName();
            log.info("类:【{}】的:【{}】方法开始执行,线程名称:【{}】,开始时间:【{}】", className, methodName, threadName, time);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Class<?> clazz = ((HandlerMethod) handler).getBean().getClass();
        String className = clazz.getSimpleName();
        Method method = ((HandlerMethod) handler).getMethod();
        String threadName = Thread.currentThread().getName();
        if (method.isAnnotationPresent(LogRecord.class)) {
            String methodName = method.getName();
            Date currentDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(currentDate);
            long i = System.currentTimeMillis() - threadLocal.get().getTime();
            log.info("类:【{}】的:【{}】方法结束执行,线程名称:【{}】,结束时间:【{}】,执行总时间为:【{}】毫秒", className, methodName, threadName, time, i);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Optional.ofNullable(ex).ifPresent(
                z -> {
                    Class<?> clazz = ((HandlerMethod) handler).getBean().getClass();
                    String className = clazz.getSimpleName();
                    Method method = ((HandlerMethod) handler).getMethod();
                    String threadName = Thread.currentThread().getName();
                    if (method.isAnnotationPresent(LogRecord.class)) {
                        String methodName = method.getName();
                        Date currentDate = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = format.format(currentDate);
                        long i = System.currentTimeMillis() - threadLocal.get().getTime();
                        log.info("类:【{}】的:【{}】方法结束执行,出现异常:【{}】线程名称:【{}】,结束时间:【{}】,执行总时间为:【{}】毫秒", className, methodName, ex, threadName,time,i);
                    }
                }
        );
    }
}
