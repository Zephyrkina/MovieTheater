package ua.com.spring.core.training.dao.impl.mybatis;

import org.apache.ibatis.annotations.*;
import ua.com.spring.core.training.dao.UserRepository;
import ua.com.spring.core.training.domain.User;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper extends UserRepository {

    @Override
    @Select("SELECT * FROM users WHERE email = #{email}")
    Optional<User> getByEmail(@Param("email") String email);

    @Override
    @Select("SELECT * FROM users")
    List<User> getAll();

    @Override
    @Select("SELECT * FROM users WHERE id = #{id}")
    Optional<User> getById(@Param("id") Long id);

    @Override
    @Insert("INSERT into users(first_name, last_name, email, birthday) " +
            "VALUES(#{firstName}, #{lastName}, #{email}, #{birthday})")
    User save(User object);


    //TODO
    @Override
    @Delete("DELETE * FROM users WHERE id = #{id}")
    User remove(@Param("id") User object);
}
