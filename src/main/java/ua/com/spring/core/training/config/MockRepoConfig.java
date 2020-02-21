package ua.com.spring.core.training.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import ua.com.spring.core.training.dao.EventRepository;
import ua.com.spring.core.training.dao.UserRepository;
import ua.com.spring.core.training.dao.impl.*;

import javax.annotation.PostConstruct;

@Primary
@Profile("mock")
@Configuration
public class MockRepoConfig {

    @PostConstruct
    public void blalbalba() {
        System.out.println("in post construct: " + this.getClass().getSimpleName());
    }

    @Bean
    public UserRepositoryImpl userRepository() {
        return new UserRepositoryImpl();
    }

    @Bean
    public TicketRepositoryImpl ticketRepository() {
        return new TicketRepositoryImpl();
    }

    @Bean
    public EventRepository eventRepository() {
        return new EventRepositoryImpl();
    }

    @Bean
    public UserDiscountCounterRepositoryImpl userDiscountCounterRepository() {
        return new UserDiscountCounterRepositoryImpl();
    }

    @Bean
    public EventCounterRepositoryImpl eventCounterRepository() {
        return new EventCounterRepositoryImpl();
    }

    @Bean
    public DiscountCounterRepositoryImpl discountCounterRepository() {
        return new DiscountCounterRepositoryImpl();
    }


}
