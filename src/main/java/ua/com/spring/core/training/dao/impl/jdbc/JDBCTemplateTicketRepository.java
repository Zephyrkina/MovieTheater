package ua.com.spring.core.training.dao.impl.jdbc;

import com.google.common.collect.Sets;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ua.com.spring.core.training.dao.TicketRepository;
import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.Ticket;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.*;

public class JDBCTemplateTicketRepository implements TicketRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    private RowMapper<Ticket> ROW_MAPPER = (ResultSet rs, int rowNum) -> {
        return Ticket.builder()
                .id(rs.getLong("id"))
                //.event(rs.getString("first_name"))
                .seat(rs.getLong("seat"))
                .dateTime(rs.getTimestamp("dateTime").toLocalDateTime())
                //.user(rs.getDate("birthday"))
                .build();
    };

    public JDBCTemplateTicketRepository(final DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("tickets")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Ticket> getAll() {
        return namedParameterJdbcTemplate.query("SELECT * FROM tickets", ROW_MAPPER);
    }

    @Override
    public Optional<Ticket> getById(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        final String query = "SELECT * FROM tickets WHERE id = :id";
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query,namedParameters, ROW_MAPPER));
    }

    @Override
    public Ticket save(Ticket object) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", object.getId());
        //parameters.put("first_name", object.getEvent());
        parameters.put("seat", object.getSeat());
        parameters.put("dateTime", object.getDateTime());
        //parameters.put("user", object.getUser());

        Number number = simpleJdbcInsert.executeAndReturnKey(parameters);

        object.setId(number.longValue());

        return object;
    }

    @Override
    public Ticket remove(Ticket object) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", object.getId());
        String query = "DELETE FROM tickets WHERE id = :id";
        namedParameterJdbcTemplate.update(query, namedParameters);
        return object;
    }

    @Override
    public Set<Ticket> getByEventAndTime(Event event, LocalDateTime time) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("event", event)
                .addValue("dateTime", time);
        final String query = "SELECT * FROM tickets WHERE event = :event AND dateTime = :dateTime;";
        return Sets.newHashSet(namedParameterJdbcTemplate.queryForObject(query, namedParameters, ROW_MAPPER));
    }
}
