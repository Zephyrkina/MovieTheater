package ua.com.spring.core.test.service;


import ua.com.spring.core.test.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Yuriy_Tkach
 */
public interface UserService extends AbstractDomainObjectService<User> {

    /**
     * Finding user by email
     * 
     * @param email
     *            Email of the user
     * @return found user or <code>null</code>
     */
    public @Nullable User getUserByEmail(@Nonnull String email);

}
