package ru.sgu.csit.admissiondepartment.security;

import com.google.common.collect.ImmutableMap;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Date: Jun 27, 2010
 * Time: 8:00:23 PM
 *
 * @author xx & hd
 */
@Component("authenticationManager")
public class SimpleAuthenticationManager implements AuthenticationManager {

    private static final GrantedAuthority ROLE_SUPERVISOR = new SimpleGrantedAuthority("ROLE_SUPERVISOR");
    private static final GrantedAuthority ROLE_WORKER = new SimpleGrantedAuthority("ROLE_WORKER");
    private static final GrantedAuthority ROLE_VIEWER = new SimpleGrantedAuthority("ROLE_VIEWER");

    private Map<String, List<GrantedAuthority>> authoritiesMap = ImmutableMap.of(
            "postgres",         Arrays.asList(ROLE_SUPERVISOR, ROLE_WORKER, ROLE_VIEWER),
            "committee_worker", Arrays.asList(ROLE_WORKER, ROLE_VIEWER),
            "committee_viewer", Arrays.asList(ROLE_VIEWER)
    );

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal(),
                authentication.getCredentials(),
                authoritiesMap.get(authentication.getName())
        );
    }
}
