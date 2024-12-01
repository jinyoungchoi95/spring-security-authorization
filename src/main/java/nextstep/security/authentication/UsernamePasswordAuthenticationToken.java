package nextstep.security.authentication;

import java.util.Collections;
import java.util.Set;

public class UsernamePasswordAuthenticationToken implements Authentication {
    private final String principal;
    private final String credentials;
    private final boolean authenticated;
    private final Set<String> authorities;

    private UsernamePasswordAuthenticationToken(String principal, String credentials, boolean authenticated, Set<String> authorities) {
        this.principal = principal;
        this.credentials = credentials;
        this.authenticated = authenticated;
        this.authorities = authorities;
    }

    public static UsernamePasswordAuthenticationToken unauthenticated(String principal, String credentials) {
        return new UsernamePasswordAuthenticationToken(principal, credentials, false, null);
    }

    public static UsernamePasswordAuthenticationToken authenticated(String principal, String credentials, Set<String> authorities) {
        return new UsernamePasswordAuthenticationToken(principal, credentials, true, authorities);
    }

    @Override
    public String getCredentials() {
        return credentials;
    }

    @Override
    public String getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public Set<String> getAuthorities() {
        return Collections.unmodifiableSet(authorities);
    }
}
