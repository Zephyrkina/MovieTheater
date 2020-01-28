package ua.com.spring.core.training.dao.impl.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ua.com.spring.core.training.dao.EventCounterRepository;
import ua.com.spring.core.training.domain.EventCounter;

import java.util.Optional;

//@Mapper
public interface EventCounterMapper extends EventCounterRepository {

    @Override
    @Select("SELECT * FROM event_counters WHERE id = #{id}")
    Optional<EventCounter> getById(@Param("id") Long id);

    //TODO replace with update? add update?
    @Override
    @Insert("INSERT into event_counters(event_id, name_requests, price_requests, book_requests) " +
            "VALUES(#{eventId}, #{nameRequests}, #{priceRequests}, #{bookRequests})")
    EventCounter save(EventCounter object);
}
