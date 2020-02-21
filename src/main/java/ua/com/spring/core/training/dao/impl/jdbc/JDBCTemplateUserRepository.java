package ua.com.spring.core.training.dao.impl.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ua.com.spring.core.training.dao.UserRepository;
import ua.com.spring.core.training.domain.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.*;

public class JDBCTemplateUserRepository implements UserRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    private RowMapper<User> ROW_MAPPER = (ResultSet rs, int rowNum) -> {
        return  User.builder()
                .id(rs.getLong("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .email(rs.getString("email"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    };

    public JDBCTemplateUserRepository(final DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<User> getByEmail(String email) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email);
        final String query = "SELECT * FROM users WHERE email = :email;";
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query, namedParameters, ROW_MAPPER));
    }

    @Override
    public List<User> getAll() {
        return namedParameterJdbcTemplate.query("SELECT * FROM users", ROW_MAPPER);
    }

    @Override
    public Optional<User> getById(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        final String query = "SELECT * FROM users WHERE id = :id";
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query,namedParameters, ROW_MAPPER));
    }

    @Override
    public User save(User object) {
        System.out.println("in jdbc repository .............................");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", object.getId());
        parameters.put("first_name", object.getFirstName());
        parameters.put("last_name", object.getLastName());
        parameters.put("email", object.getEmail());
        parameters.put("birthday", object.getBirthday());

        Number number = simpleJdbcInsert.executeAndReturnKey(parameters);

        object.setId(number.longValue());

        return object;
    }

    @Override
    public User remove(User object) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", object.getId());
        String query = "DELETE FROM users WHERE id = :id";
        namedParameterJdbcTemplate.update(query, namedParameters);
        return object;
    }
}
