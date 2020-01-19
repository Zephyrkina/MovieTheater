package ua.com.spring.core.training.dao;

import ua.com.spring.core.training.domain.UserDiscountCounter;

import java.util.List;

public interface UserDiscountCounterRepository extends AbstractRepository<UserDiscountCounter> {

    default List<UserDiscountCounter> getAll() { throw new UnsupportedOperationException();}

    default UserDiscountCounter remove(UserDiscountCounter object) { throw new UnsupportedOperationException(); }
}

