package ua.com.spring.core.training.dao.impl.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ua.com.spring.core.training.dao.EventRepository;
import ua.com.spring.core.training.domain.Event;
import ua.com.spring.core.training.domain.EventRating;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JDBCTemplateEventRepository implements EventRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    private RowMapper<Event> ROW_MAPPER = (ResultSet rs, int rowNum) -> {
        return Event.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .basePrice(rs.getDouble("base_price"))
                .rating(EventRating.valueOf(rs.getString("rating")))
                .build();
    };

    public JDBCTemplateEventRepository(final DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("events")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Event> getAll() {
        return namedParameterJdbcTemplate.query("SELECT * FROM events", ROW_MAPPER);
    }

    @Override
    public Optional<Event> getById(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        final String query = "SELECT * FROM events WHERE id = :id";
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query,namedParameters, ROW_MAPPER));
    }

    @Override
    public Event save(Event object) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", object.getId());
        parameters.put("name", object.getName());
        parameters.put("base_price", object.getBasePrice());
        parameters.put("rating", object.getRating());

        Number number = simpleJdbcInsert.executeAndReturnKey(parameters);

        object.setId(number.longValue());

        return object;
    }

    @Override
    public Event remove(Event object) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", object.getId());
        String query = "DELETE FROM events WHERE id = :id";
        namedParameterJdbcTemplate.update(query, namedParameters);
        return object;
    }

    @Override
    public Optional<Event> getByName(String name) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("name", name);
        final String query = "SELECT * FROM events WHERE name = :name;";
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query, namedParameters, ROW_MAPPER));
    }
}
