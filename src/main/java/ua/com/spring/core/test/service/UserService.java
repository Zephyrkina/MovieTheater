package ua.com.spring.core.test.service;


import ua.com.spring.core.test.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface UserService extends AbstractDomainObjectService<User> {

    @Nullable User getUserByEmail(@Nonnull String email);

}
