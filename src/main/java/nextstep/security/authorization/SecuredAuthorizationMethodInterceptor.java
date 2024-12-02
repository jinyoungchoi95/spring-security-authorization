package nextstep.security.authorization;

import nextstep.security.authentication.Authentication;
import nextstep.security.authentication.AuthenticationException;
import nextstep.security.context.SecurityContextHolder;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

public class SecuredAuthorizationMethodInterceptor implements MethodInterceptor, PointcutAdvisor {

    private final Pointcut pointcut;

    public SecuredAuthorizationMethodInterceptor() {
        this.pointcut = new AnnotationMatchingPointcut(null, Secured.class);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        if (method.isAnnotationPresent(Secured.class)) {
            Secured secured = method.getAnnotation(Secured.class);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                throw new AuthenticationException();
            }

            if (isNotAuthorized(authentication, secured)) {
                throw new ForbiddenException();
            }
        }
        return invocation.proceed();
    }

    private boolean isNotAuthorized(Authentication authentication, Secured secured) {
        Set<String> authorities = authentication.getAuthorities();
        return Arrays.stream(secured.value())
                .noneMatch(authorities::contains);
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this;
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }
}
