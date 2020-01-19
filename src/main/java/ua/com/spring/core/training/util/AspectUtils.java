package ua.com.spring.core.training.util;

import lombok.experimental.UtilityClass;
import ua.com.spring.core.training.domain.User;

@UtilityClass
public class AspectUtils {

     public User getUserFromArguments(Object[] joinPointArgs) {
        User user = null;
        for (Object o : joinPointArgs) {
            if (o instanceof User) {
                user = (User) o;
            }
        }
        return user;
    }
}
