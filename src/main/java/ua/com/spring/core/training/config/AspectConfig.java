package ua.com.spring.core.training.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ua.com.spring.core.training.aspects.CounterAspect;
import ua.com.spring.core.training.aspects.DiscountAspect;
import ua.com.spring.core.training.aspects.LuckyWinnerAspect;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

    @Bean
    public CounterAspect counterAspect() {
        return new CounterAspect();
    }

    @Bean
    public DiscountAspect discountAspect() {
        return new DiscountAspect();
    }

    @Bean
    public LuckyWinnerAspect luckyWinnerAspect() {
        return new LuckyWinnerAspect();
    }
}
