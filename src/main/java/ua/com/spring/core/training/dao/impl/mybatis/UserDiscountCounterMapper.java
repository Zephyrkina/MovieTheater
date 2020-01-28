package ua.com.spring.core.training.dao.impl.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ua.com.spring.core.training.dao.UserDiscountCounterRepository;
import ua.com.spring.core.training.domain.UserDiscountCounter;

import java.util.Optional;

//@Mapper
public interface UserDiscountCounterMapper extends UserDiscountCounterRepository {

    @Override
    @Select("SELECT * FROM user_discount_counters WHERE id = #{id}")
    Optional<UserDiscountCounter> getById(@Param("id") Long id);

    @Override
    @Insert("INSERT into user_discount_counters(user_id, discount_id, given_times) " +
            "VALUES(#{userId}, #{discountId}, #{givenTimes})")
    UserDiscountCounter save(UserDiscountCounter object);
}
