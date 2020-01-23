package ua.com.spring.core.training.dao.impl.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ua.com.spring.core.training.dao.UserRepository;
import ua.com.spring.core.training.domain.User;
import ua.com.spring.core.training.mapper.UserRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JDBCTemplateUserRepository implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    public JDBCTemplateUserRepository(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("USERS");
        //.usingGeneratedKeyColumns("ID");
    }

    @Override
    public Optional<User> getByEmail(String email) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM USERS WHERE EMAIL = :email", namedParameters, new UserRowMapper()));
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM USERS", new UserRowMapper());
    }

    @Override
    public Optional<User> getById(Long id) {
        final String query = "SELECT * FROM USERS WHERE ID = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new Object[] { id }, new UserRowMapper()));
    }

    @Override
    public User save(User object) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ID", object.getId());
        parameters.put("FIRST_NAME", object.getFirstName());
        parameters.put("LAST_NAME", object.getLastName());
        parameters.put("EMAIL", object.getEmail());
        parameters.put("BIRTHDAY", object.getBirthday());

        //simpleJdbcInsert.executeAndReturnKey(parameters);
        simpleJdbcInsert.execute(parameters);

        return object;
    }

    @Override
    public User remove(User object) {
        String query = "DELETE FROM USERS WHERE id = ?";
        jdbcTemplate.update(query, object.getId());
        return object;
    }
}
