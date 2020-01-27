package ua.com.spring.core.training.dao.impl.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ua.com.spring.core.training.dao.EventCounterRepository;
import ua.com.spring.core.training.domain.EventCounter;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JDBCTemplateEventCounterRepository implements EventCounterRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    private RowMapper<EventCounter> ROW_MAPPER = (ResultSet rs, int rowNum) -> {
        return EventCounter.builder()
                .eventId(rs.getLong("event_id"))
                .nameRequests(rs.getLong("name_requests"))
                .priceRequests(rs.getLong("price_requests"))
                .bookRequests(rs.getLong("book_requests"))
                .build();
    };

    public JDBCTemplateEventCounterRepository(final DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("event_counters");
    }

    @Override
    public Optional<EventCounter> getById(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("event_id", id);
        final String query = "SELECT * FROM event_counters WHERE event_id = :event_id";
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query,namedParameters, ROW_MAPPER));
    }

    @Override
    public EventCounter save(EventCounter object) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("event_id", object.getEventId());
        parameters.put("name_requests", object.getNameRequests());
        parameters.put("price_requests", object.getPriceRequests());
        parameters.put("book_requests", object.getBookRequests());

        simpleJdbcInsert.execute(parameters);

        return object;
    }
}
