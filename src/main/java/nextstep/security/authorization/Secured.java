package nextstep.security.authorization;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Secured {

    String[] value();
}
