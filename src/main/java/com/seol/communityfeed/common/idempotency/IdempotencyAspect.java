package com.seol.communityfeed.common.idempotency;

import com.seol.communityfeed.common.ui.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class IdempotencyAspect {
    private final IdempotencyRepository idempotencyRepository;
    private final HttpServletRequest request;

    @Around("@annotation(Idempotent)")
    public Object checkIdempotency(ProceedingJoinPoint joinPoint) throws Throwable{
        String idempotencyKey = request.getHeader("Idempotency-Key");
        if(idempotencyKey == null){
            return joinPoint.proceed();
        }

        Idempotency idempotency = idempotencyRepository.getByKey(idempotencyKey);

        if(idempotency != null){
            return idempotency.getResponse(); //로직 수행하지 않고 저장된 응답 값 반환
        }

        Object result = joinPoint.proceed(); //로직 수행

        Idempotency newIdempotency = new Idempotency(idempotencyKey,(Response<?>) result);
        idempotencyRepository.save(newIdempotency);

        return result;
    }
}
