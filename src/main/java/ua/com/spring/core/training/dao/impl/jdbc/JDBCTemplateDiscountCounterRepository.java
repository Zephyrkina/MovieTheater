package ua.com.spring.core.training.dao.impl.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ua.com.spring.core.training.dao.DiscountCounterRepository;
import ua.com.spring.core.training.domain.DiscountCounter;
import ua.com.spring.core.training.domain.DiscountCounter;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JDBCTemplateDiscountCounterRepository implements DiscountCounterRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    private RowMapper<DiscountCounter> ROW_MAPPER = (ResultSet rs, int rowNum) -> {
        return DiscountCounter.builder()
                .discountId(rs.getLong("discount_id"))
                .givenTotalTimes(rs.getLong("given_total_times"))
                .build();
    };

    public JDBCTemplateDiscountCounterRepository(final DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("discount_counters");
    }

    @Override
    public Optional<DiscountCounter> getById(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("discount_id", id);
        final String query = "SELECT * FROM discount_counters WHERE discount_id = :discount_id";
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query,namedParameters, ROW_MAPPER));
    }

    @Override
    public DiscountCounter save(DiscountCounter object) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("discount_id", object.getDiscountId());
        parameters.put("given_total_times", object.getGivenTotalTimes());

        simpleJdbcInsert.execute(parameters);

        return object;
    }
}
