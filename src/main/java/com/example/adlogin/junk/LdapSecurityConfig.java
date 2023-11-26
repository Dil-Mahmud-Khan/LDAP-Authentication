package com.example.adlogin.junk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration

public class LdapSecurityConfig {

    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request-> request
                                .anyRequest()
                                .fullyAuthenticated())

                .formLogin(Customizer.withDefaults());

        http.authenticationProvider(ldapAuthenticationProvider());
        return http.build();
    }

    @Bean
    LdapAuthenticationProvider ldapAuthenticationProvider() {
        return new LdapAuthenticationProvider(authenticator());
    }

    @Bean
    BindAuthenticator authenticator() {

        FilterBasedLdapUserSearch search = new FilterBasedLdapUserSearch("ou=groups", "(uid={0})", contextSource());
        BindAuthenticator authenticator = new BindAuthenticator(contextSource());
        authenticator.setUserSearch(search);
        return authenticator;
    }

    @Bean
    public DefaultSpringSecurityContextSource contextSource() {
        DefaultSpringSecurityContextSource dsCtx = new DefaultSpringSecurityContextSource("ldap://localhost:8389/dc=springframework,dc=org");
        dsCtx.setUserDn("uid={0},ou=people");
        return dsCtx;
    }
}