package com.example.springbootspringsecuritykeycloak.keycloak.config;

import com.example.springbootspringsecuritykeycloak.keycloak.config.resolver.KeycloakDynamicConfigResolver;
import com.example.springbootspringsecuritykeycloak.keycloak.register.KeycloakUserDetailsAuthenticationProvider;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationEntryPoint;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author aidan.liu
 */
@KeycloakConfiguration
public class KeycloakConfig extends KeycloakWebSecurityConfigurerAdapter {

    public static final RequestMatcher DEFAULT_REQUEST_MATCHER =
            new OrRequestMatcher(
                    new AntPathRequestMatcher("/*")
            );

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(keycloakUserDetailsAuthenticationProvider());
    }

    @Bean
    public KeycloakUserDetailsAuthenticationProvider keycloakUserDetailsAuthenticationProvider() {
        return new KeycloakUserDetailsAuthenticationProvider();
    }

    @Override
    protected KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter() throws Exception {
        KeycloakAuthenticationProcessingFilter filter = new KeycloakAuthenticationProcessingFilter(authenticationManagerBean(), DEFAULT_REQUEST_MATCHER);
        filter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());
        return filter;
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }

    @Bean
    public KeycloakDynamicConfigResolver keycloakConfigResolver() {
        return new KeycloakDynamicConfigResolver();
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().defaultsDisabled();
        http.headers().cacheControl()
                //X-Content-Type-Options nosniff
                .and().contentTypeOptions()
                //"Strict-Transport-Security", "max-age=31536000 ; includeSubDomains"
                .and().httpStrictTransportSecurity().includeSubDomains(true).maxAgeInSeconds(31536000)
                //Referrer-Policy: same-origin
                .and().referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN)
                //"X-Frame-Options", "SAMEORIGIN"
                .and().frameOptions().sameOrigin();

        http.csrf().ignoringAntMatchers("/kaptcha/**", "/rest/**", "/keycloak/**")
                .requireCsrfProtectionMatcher(keycloakCsrfRequestMatcher())
                .and()
                .sessionManagement()
                .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
                .and()
                //.addFilterBefore(keycloakPreAuthActionsFilter(), LogoutFilter.class)
                .addFilterBefore(keycloakAuthenticationProcessingFilter(), LogoutFilter.class)
                .addFilterAfter(keycloakSecurityContextRequestFilter(), SecurityContextHolderAwareRequestFilter.class)
                .addFilterAfter(keycloakAuthenticatedActionsRequestFilter(), KeycloakSecurityContextRequestFilter.class)
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/keycloak/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.jsf").permitAll()
                .failureUrl("/login.jsf?error=true").defaultSuccessUrl("/home.jsf")
                .and().logout().addLogoutHandler(keycloakLogoutHandler()).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login.jsf")
                .and().exceptionHandling().accessDeniedPage("/access_denied");
    }
}
