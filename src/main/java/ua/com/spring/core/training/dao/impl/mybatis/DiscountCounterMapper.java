package ua.com.spring.core.training.dao.impl.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ua.com.spring.core.training.dao.DiscountCounterRepository;
import ua.com.spring.core.training.domain.DiscountCounter;

import java.util.Optional;

//@Mapper
public interface DiscountCounterMapper extends DiscountCounterRepository {


    @Override
    @Select("SELECT * FROM discount_counters WHERE id = #{id}")
    Optional<DiscountCounter> getById(@Param("id") Long id);

    @Override
    @Insert("INSERT into discount_counters(discount_id, given_total_times)" +
            "VALUES(#{discountId}, #{givenTotalTimes})")
    DiscountCounter save(DiscountCounter object);
}
