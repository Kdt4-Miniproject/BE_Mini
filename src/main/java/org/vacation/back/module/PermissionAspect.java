package org.vacation.back.module;


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.exception.ErrorCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Slf4j
@Component
public class PermissionAspect {


    @Pointcut("@annotation(org.vacation.back.annotation.Permission)")
    public void cut(){}

    @Pointcut("@annotation(org.vacation.back.annotation.Leader)")
    public void leaderCut(){}

    @Pointcut("@annotation(org.vacation.back.annotation.AdminAndLeader)")
    public void both(){

    }


    @Around("cut()")
    public Object before(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object temp = request.getAttribute("role");
        String role = null;
        if(temp != null) role = temp.toString();

        log.info(" 해당 사용자 권한 ===> {}",role);
        if(role != null && role.equalsIgnoreCase("ADMIN")){
            return proceedingJoinPoint.proceed();
        }else{
            HttpServletResponse response =
                    ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
            //RequestContextHolder를 사용하면 현재 쓰레드에서 처리되고 있는 요청에 관한 HttpServletRequest와 HttpServletResponse를 가져올 수 있음
            Gson gson = new Gson();

            CommonResponse<?> commonResponse =new CommonResponse<>(
                    ErrorCode.ACCESS_RESOURCES_WITHOUT_PERMISSION)
                    .data(false);
            String json = gson.toJson(commonResponse);

            log.warn("Attempt to access resources without permission {}",role);

            return ResponseEntity.status(CodeEnum.FORBIDDEN.getCode()).body(json);
        }
    }

    @Around("leaderCut()")
    public Object leaderCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object temp = request.getAttribute("role");
        String role = null;

        if(temp != null) role = temp.toString();

        log.info(" 해당 사용자 권한 ===> {}",role);
        if(role != null && (role.equalsIgnoreCase("LEADER"))){
            return proceedingJoinPoint.proceed();
        }else{
            HttpServletResponse response =
                    ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
            //RequestContextHolder를 사용하면 현재 쓰레드에서 처리되고 있는 요청에 관한 HttpServletRequest와 HttpServletResponse를 가져올 수 있음
            Gson gson = new Gson();

            CommonResponse<?> commonResponse =new CommonResponse<>(
                    ErrorCode.ACCESS_RESOURCES_WITHOUT_PERMISSION)
                    .data(false);
            String json = gson.toJson(commonResponse);

            log.warn("Attempt to access resources without permission {}",role);

            return ResponseEntity.status(CodeEnum.FORBIDDEN.getCode()).body(json);
        }
    }
    @Around("both()")
    public Object adminAndLeader(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object temp = request.getAttribute("role");
        String role = null;

        if(temp != null) role = temp.toString();

        log.info(" 해당 사용자 권한 ===> {}",role);
        if(role != null && (role.equalsIgnoreCase("LEADER") || role.equalsIgnoreCase("ADMIN"))){
            return proceedingJoinPoint.proceed();
        }else{
            HttpServletResponse response =
                    ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
            //RequestContextHolder를 사용하면 현재 쓰레드에서 처리되고 있는 요청에 관한 HttpServletRequest와 HttpServletResponse를 가져올 수 있음
            Gson gson = new Gson();

            CommonResponse<?> commonResponse =new CommonResponse<>(
                    ErrorCode.ACCESS_RESOURCES_WITHOUT_PERMISSION)
                    .data(false);
            String json = gson.toJson(commonResponse);

            log.warn("Attempt to access resources without permission {}",role);

            return ResponseEntity.status(CodeEnum.FORBIDDEN.getCode()).body(json);
        }
    }

}
