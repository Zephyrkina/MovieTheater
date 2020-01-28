package ua.com.spring.core.training.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.com.spring.core.training.dao.impl.mybatis.*;

import javax.sql.DataSource;

@Configuration
//@Profile("mock")
@MapperScan("ua.com.spring.core.training.dao.impl.mybatis")
public class MyBatisRepoConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:jdbc/schema.sql")
                .addScript("classpath:jdbc/test-data.sql")
                .build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public MapperFactoryBean<UserMapper> userRepository() throws Exception {
        MapperFactoryBean<UserMapper> factoryBean = new MapperFactoryBean<>(UserMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<EventMapper> eventRepository() throws Exception {
        MapperFactoryBean<EventMapper> factoryBean = new MapperFactoryBean<>(EventMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<TicketMapper> ticketRepository() throws Exception {
        MapperFactoryBean<TicketMapper> factoryBean = new MapperFactoryBean<>(TicketMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<UserDiscountCounterMapper> userDiscountCounterRepository() throws Exception {
        MapperFactoryBean<UserDiscountCounterMapper> factoryBean = new MapperFactoryBean<>(UserDiscountCounterMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<DiscountCounterMapper> discountCounterRepository() throws Exception {
        MapperFactoryBean<DiscountCounterMapper> factoryBean = new MapperFactoryBean<>(DiscountCounterMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<EventCounterMapper> eventCounterRepository() throws Exception {
        MapperFactoryBean<EventCounterMapper> factoryBean = new MapperFactoryBean<>(EventCounterMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean;
    }
}
