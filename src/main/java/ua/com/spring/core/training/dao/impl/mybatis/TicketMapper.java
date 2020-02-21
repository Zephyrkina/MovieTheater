package ua.com.spring.core.training.dao.impl.mybatis;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ua.com.spring.core.training.dao.TicketRepository;
import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.Ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

//@Mapper
public interface TicketMapper extends TicketRepository {

    //TODO
    @Override
    Set<Ticket> getByEventAndTime(Event event, LocalDateTime time);

    @Override
    @Select("SELECT * FROM tickets")
    List<Ticket> getAll();

    @Override
    @Select("SELECT * FROM tickets WHERE id = #{id}")
    Optional<Ticket> getById(@Param("id") Long id);

    @Override
    @Insert("INSERT into tickets(seat, date_time) " +
            "VALUES(#{seat}, #{dateTime})")
    Ticket save(Ticket object);

    @Override
    @Delete("DELETE * FROM tickets WHERE id = #{id}")
    Ticket remove(@Param("id") Ticket object);
}
