package com.example.adlogin.ldap;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.InetOrgPersonContextMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @PostMapping("/api/login")
    public String authenticateUser(@RequestBody AuthenticationRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        if (authenticateWithLDAP(username, password)) {
            return "Authentication successful";
        } else {
            return "Authentication failed";
        }
    }

    // Implement LDAP authentication logic here
    private boolean authenticateWithLDAP(String username, String password) {
        try {
            DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource("ldap://localhost:50000");
            contextSource.setUserDn("cn=MRS,dc=CONTOSO,dc=COM");
            contextSource.setPassword("Mahmud.10310650");
            contextSource.afterPropertiesSet();

            BindAuthenticator bindAuthenticator = new BindAuthenticator(contextSource);
            //bindAuthenticator.setUserDnPatterns(new String[] { "uid={0},ou=people" });

            LdapAuthenticationProvider ldapAuthenticationProvider = new LdapAuthenticationProvider(bindAuthenticator);
            ldapAuthenticationProvider.setUserDetailsContextMapper(new InetOrgPersonContextMapper());

            Authentication authentication = ldapAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return authentication.isAuthenticated();
        } catch (AuthenticationException e) {
            return false;
        }
    }
}
