package ua.com.spring.core.training.dao.impl.mybatis;

import org.apache.ibatis.annotations.*;
import ua.com.spring.core.training.dao.EventRepository;
import ua.com.spring.core.training.domain.Event;

import java.util.List;
import java.util.Optional;

//@Mapper
public interface EventMapper extends EventRepository {

    @Override
    @Select("SELECT * FROM events WHERE name = #{name}")
    Optional<Event> getByName(@Param("name") String name);

    @Override
    @Select("SELECT * FROM events")
    List<Event> getAll();

    @Override
    @Select("SELECT * FROM events WHERE id = #{id}")
    Optional<Event> getById(@Param("id") Long id);

    @Override
    @Insert("INSERT into events(name, base_price, rating) " +
            "VALUES(#{name}, #{basePrice}, #{rating})")
    Event save(Event object);

    //TODO
    @Override
    @Delete("DELETE * FROM events WHERE id = #{id}")
    Event remove(@Param("id") Event object);
}
