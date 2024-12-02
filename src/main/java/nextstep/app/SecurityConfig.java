package nextstep.app;

import nextstep.security.authorization.SecuredAuthorizationMethodInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class SecurityConfig {

    @Bean
    public SecuredAuthorizationMethodInterceptor securedAuthorizationMethodInterceptor() {
        return new SecuredAuthorizationMethodInterceptor();
    }
}
