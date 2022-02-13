package com.example.simple.config;

import com.example.simple.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(request->{
                    request
                            .antMatchers("/**", "/signup", "/login").permitAll()
                            .antMatchers("/swagger-ui.html", "/swagger-ui/**").hasRole("VIEW")
                            .anyRequest().authenticated()
                            ;
                })
                .formLogin(login->
                        login.loginPage("/login")
                                .defaultSuccessUrl("/", true)
                )
                .logout(logout->
                        logout.logoutSuccessUrl("/")
                                .invalidateHttpSession(true))
                .exceptionHandling(error->
                        error.accessDeniedPage("/access-denied")
                )
                .headers(headers -> headers
                        .cacheControl(cache -> cache.disable())
                )
                .csrf().ignoringAntMatchers("/api/**")
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()
                );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());

    }

}
