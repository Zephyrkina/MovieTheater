package ua.com.spring.core.training.dao.impl.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ua.com.spring.core.training.dao.UserDiscountCounterRepository;
import ua.com.spring.core.training.domain.UserDiscountCounter;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JDBCTemplateUserDiscountCounterRepository implements UserDiscountCounterRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    private RowMapper<UserDiscountCounter> ROW_MAPPER = (ResultSet rs, int rowNum) -> {
        return UserDiscountCounter.builder()
                .userId(rs.getLong("user_id"))
                .discountId(rs.getLong("discount_id"))
                .givenTimes(rs.getInt("given_times"))
                .build();
    };

    public JDBCTemplateUserDiscountCounterRepository(final DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("user_discount_counters");
    }

    @Override
    public Optional<UserDiscountCounter> getById(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("user_id", id);
        final String query = "SELECT * FROM discount_counters WHERE user_id = :user_id";
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query,namedParameters, ROW_MAPPER));
    }

    @Override
    public UserDiscountCounter save(UserDiscountCounter object) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", object.getUserId());
        parameters.put("discount_id", object.getDiscountId());
        parameters.put("given_times", object.getGivenTimes());

        simpleJdbcInsert.execute(parameters);

        return object;
    }
}
