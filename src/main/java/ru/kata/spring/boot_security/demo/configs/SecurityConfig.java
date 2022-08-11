package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;

    public SecurityConfig(@Qualifier("userServiceImpl") UserDetailsService userDetailsService, LoginSuccessHandler loginSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService ( userDetailsService );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin ( )
                .loginPage ( "/login" )
                .successHandler ( loginSuccessHandler )
                .loginProcessingUrl ( "/login" )
                .usernameParameter ( "username" )
                .passwordParameter ( "password" )
                .permitAll ( );

        http.logout ( )

                .permitAll ( )
                .logoutRequestMatcher ( new AntPathRequestMatcher ( "/logout" ) )
                .logoutSuccessUrl ( "/login?logout" )
                .and ( ).csrf ( ).disable ( );

        http

                .authorizeRequests ( )
                .antMatchers ( "/login" ).anonymous ( )
                .antMatchers ( "/admin" ).hasAuthority ( "ADMIN" )
                .antMatchers ( "/user" ).hasAnyAuthority ( "USER", "ADMIN" )
                .anyRequest ( ).authenticated ( );
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder ( );
    }
}
