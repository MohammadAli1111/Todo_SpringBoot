package org.todo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.todo.services.UserService;


@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"org.todo.*"})
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    String cookie_key="AbcdefghiJklmNoPqRstUvXyz123123";

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/home").hasAnyAuthority("ADMIN","User")
                .antMatchers("/User/**").hasAuthority("User")
                .antMatchers( "/Admin/**").hasAuthority("ADMIN")
                .and().csrf().disable()
                .formLogin().permitAll()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error=true")
                .and()
                .rememberMe()
                .tokenValiditySeconds((60*60*24*30*12))
                .key(cookie_key)
                .rememberMeParameter("checkRememberMe")
                .and()
                .logout().logoutSuccessUrl("/index")
                .and().httpBasic();



    }

    @Override
    public void configure(WebSecurity web)  {
        web.ignoring().antMatchers("/resources/**",
                "/static/**",
                "/assets/**",
                "/css/**",
                "/js/**",
                "/scss/**",
                "/images/**",
                "/webfonts/**");


    }



}
