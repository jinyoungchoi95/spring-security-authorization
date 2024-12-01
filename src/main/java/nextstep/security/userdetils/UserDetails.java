package nextstep.security.userdetils;

import java.util.Set;

public interface UserDetails {
    String getUsername();

    String getPassword();

    Set<String> getAuthorities();
}
