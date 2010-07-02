package ru.sgu.csit.selectioncommittee.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
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
    private Map<String, List<GrantedAuthority>> authoritiesMap;

    private static final GrantedAuthority ROLE_SUPERVISOR = new GrantedAuthorityImpl("ROLE_SUPERVISOR");
    private static final GrantedAuthority ROLE_WORKER = new GrantedAuthorityImpl("ROLE_WORKER");
    private static final GrantedAuthority ROLE_VIEWER = new GrantedAuthorityImpl("ROLE_VIEWER");

    public SimpleAuthenticationManager() {
        authoritiesMap = new HashMap<String, List<GrantedAuthority>>();
        authoritiesMap.put("postgres",         Arrays.asList(ROLE_SUPERVISOR, ROLE_WORKER, ROLE_VIEWER));
        authoritiesMap.put("committee_worker", Arrays.asList(ROLE_WORKER, ROLE_VIEWER));
        authoritiesMap.put("committee_viewer", Arrays.asList(ROLE_VIEWER));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),
                authoritiesMap.get(authentication.getName()));
    }
}
