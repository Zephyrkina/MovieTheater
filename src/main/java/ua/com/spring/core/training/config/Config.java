package ua.com.spring.core.training.config;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.com.spring.core.training.aspects.CounterAspect;
import ua.com.spring.core.training.aspects.DiscountAspect;
import ua.com.spring.core.training.aspects.LuckyWinnerAspect;
import ua.com.spring.core.training.dao.impl.*;
import ua.com.spring.core.training.dao.impl.jdbc.JDBCTemplateUserRepository;

import javax.sql.DataSource;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = { "ua.com.spring.core.training.aspects", "ua.com.spring.core.training.dao" })
public class Config {

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

   /* @Bean
    public UserRepositoryImpl userRepository() {
        return new UserRepositoryImpl();
    }*/

    @Bean
    public TicketRepositoryImpl ticketRepository() {
        return new TicketRepositoryImpl();
    }

    @Bean
    public EventRepositoryImpl eventRepository() {
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

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:jdbc/schema.sql")
                .addScript("classpath:jdbc/test-data.sql").build();
    }

    //abstract factory for diff repos impl beans?
    @Bean
    public JDBCTemplateUserRepository userRepository() {
        return new JDBCTemplateUserRepository(dataSource());
    }

}
