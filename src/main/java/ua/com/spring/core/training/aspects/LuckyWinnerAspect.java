package ua.com.spring.core.training.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import ua.com.spring.core.training.domain.User;

import java.util.concurrent.ThreadLocalRandom;

import static ua.com.spring.core.training.util.AspectUtils.*;

@Slf4j
@Aspect
public class LuckyWinnerAspect {

    @Around("execution(* ua.com.spring.core.training.service.impl.DefaultBookingService.getTicketsPrice(..))")
    public double luckyWinnerAspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] joinPointArgs = proceedingJoinPoint.getArgs();

        User user = getUserFromArguments(joinPointArgs);

        Double resultPrice = (Double) proceedingJoinPoint.proceed();

        if (user != null) {

            boolean isLuckyUser = checkLucky();
            if (isLuckyUser) {
                resultPrice = 0.0;
                log.info("User {} was a lucky one and won free tickets", user.getEmail());
                return resultPrice;
            }
        }

        return resultPrice;
    }

    private boolean checkLucky() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
