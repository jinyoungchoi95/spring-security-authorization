package nextstep.security.authorization;

import nextstep.security.authentication.Authentication;
import nextstep.security.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class AuthorizationFilter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean authenticated = authentication.isAuthenticated();
        Set<String> authorities = authentication.getAuthorities();
        String uri = request.getRequestURI();
        uriHandle(uri, authorities, authenticated);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void uriHandle(String uri, Set<String> authorities, boolean authenticated) {
        if (uri.equals("/members")) {
            if (!authorities.contains("ADMIN")) {
                throw new ForbiddenException();
            }
            return;
        }
        if (uri.equals("/members/me")) {
            if (!authenticated) {
                throw new ForbiddenException();
            }
            return;
        }
        if (uri.equals("/search") || uri.equals("/login")) {
            return;
        }
        throw new ForbiddenException();
    }
}
