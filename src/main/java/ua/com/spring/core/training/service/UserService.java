package ua.com.spring.core.training.service;


import ua.com.spring.core.training.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface UserService extends AbstractDomainObjectService<User> {

    @Nullable User getUserByEmail(@Nonnull String email);

}
