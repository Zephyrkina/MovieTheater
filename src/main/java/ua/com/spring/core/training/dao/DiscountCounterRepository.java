package ua.com.spring.core.training.dao;

import ua.com.spring.core.training.domain.DiscountCounter;

import java.util.List;

public interface DiscountCounterRepository extends AbstractRepository<DiscountCounter> {

    default List<DiscountCounter> getAll() { throw new UnsupportedOperationException();}

    default DiscountCounter remove(DiscountCounter object) { throw new UnsupportedOperationException(); }
}
