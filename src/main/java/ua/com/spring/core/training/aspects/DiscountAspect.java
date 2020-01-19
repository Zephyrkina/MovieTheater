package ua.com.spring.core.training.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.spring.core.training.domain.Discount;
import ua.com.spring.core.training.domain.User;
import ua.com.spring.core.training.exceptions.DiscountNotFoundException;
import ua.com.spring.core.training.service.DiscountCounterService;
import ua.com.spring.core.training.service.UserDiscountCounterService;

import static ua.com.spring.core.training.util.AspectUtils.*;

@Slf4j
@Aspect
public class DiscountAspect {

    @Autowired
    private DiscountCounterService discountCounterService;

    @Autowired
    private UserDiscountCounterService userDiscountCounterService;

    @AfterReturning(pointcut = "execution(* ua.com.spring.core.training.service.impl.DefaultDiscountService.getDiscount(..))",
            returning = "discountValue")
    public void countTimesDiscountGivenTotal(Byte discountValue) {
        Discount discount = getDiscountByValue(discountValue);

        discountCounterService.increaseTotalGivenDiscountCounter(discount);

        log.info("Discount {} was given", discount);
    }

    @AfterReturning(pointcut = "execution(* ua.com.spring.core.training.service.impl.DefaultDiscountService.getDiscount(..))",
            returning = "discountValue")
    public void countTimesDiscountGivenTotalForSpecificUser(JoinPoint joinPoint, Byte discountValue) {
        Object[] joinPointArgs = joinPoint.getArgs();
        User user = getUserFromArguments(joinPointArgs);

        if (user != null) {
            Discount discount = getDiscountByValue(discountValue);

            userDiscountCounterService.increaseGivenUserDiscountCounter(discount, user);

            log.info("Discount {} was given for specific user: {}", discount, user.getEmail());
        }
    }

    private Discount getDiscountByValue(Byte discountValue) {
        if (discountValue.equals(Discount.NO_DISCOUNT.getValue())) {
            return Discount.NO_DISCOUNT;
        } else if (discountValue.equals(Discount.BIRTHDAY_DISCOUNT.getValue())) {
            return Discount.BIRTHDAY_DISCOUNT;
        } else if (discountValue.equals(Discount.EVERY_10TH_TICKET_DISCOUNT.getValue())) {
            return Discount.EVERY_10TH_TICKET_DISCOUNT;
        } else {
            throw new DiscountNotFoundException();
        }
    }

}
