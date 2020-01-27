package ua.com.spring.core.training.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.com.spring.core.training.dao.EventRepository;
import ua.com.spring.core.training.dao.UserRepository;
import ua.com.spring.core.training.dao.impl.jdbc.*;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Profile("jdbc")
@Configuration
public class JDBCRepoConfig {

    @PostConstruct
    public void blalbalba() {
        System.out.println("in post construct: " + this.getClass().getSimpleName());
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:jdbc/schema.sql")
                .addScript("classpath:jdbc/test-data.sql").build();
    }

    @Bean
    public JDBCTemplateUserRepository userRepository() {
        return new JDBCTemplateUserRepository(dataSource());
    }

    @Bean
    public EventRepository eventRepository() {
        return new JDBCTemplateEventRepository(dataSource());
    }

    @Bean
    public JDBCTemplateTicketRepository ticketRepository() {
        return new JDBCTemplateTicketRepository(dataSource());
    }

    @Bean
    public JDBCTemplateEventCounterRepository eventCounterRepository() {
        return new JDBCTemplateEventCounterRepository(dataSource());
    }

    @Bean
    public JDBCTemplateDiscountCounterRepository discountCounterRepository() {
        return new JDBCTemplateDiscountCounterRepository(dataSource());
    }

    @Bean
    public JDBCTemplateUserDiscountCounterRepository userDiscountCounterRepository() {
        return new JDBCTemplateUserDiscountCounterRepository(dataSource());
    }
}
