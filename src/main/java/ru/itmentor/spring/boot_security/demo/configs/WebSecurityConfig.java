package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, @Qualifier("userDetailServiceImpl") UserDetailsService userDetailsService) {
        this.successUserHandler = successUserHandler;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected BCryptPasswordEncoder bCryptPasswordEncoder() { // энкодер паролей

        return new BCryptPasswordEncoder(12);
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        // сверяет userDetailsService с поступившим юзером
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // конфиги в которых указывается доступы пользователей
        http
                .cors().disable()
                .csrf().disable() //  защита от CSRF-атак( типо подставного сайта где злоумышленник его использует и заставляет
                // от имени пользователя отправлять пароли, деньги со счёта на счёт и т.п
                .authorizeRequests() //авторизацуем запрос
                .antMatchers("/login", "/").permitAll()
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN") //прописываем доступ для юрл /user/**
                .antMatchers("/admin/**").hasRole("ADMIN") //прописываем доступ для юрл /admin/**
                .anyRequest().authenticated() // все запросы должны быть авторизованы и аутентифицированы
                .and()
                .formLogin() // задаю форму для ввода логина-пароля, по дефолту это "/login"
                .successHandler(successUserHandler)
                .permitAll() // доступно всем
                .and()
                .logout().permitAll(); // настройка логаута
//                .cors().disable()
//                .csrf().disable() //  защита от CSRF-атак( типо подставного сайта где злоумышленник его использует и заставляет
//                // от имени пользователя отправлять пароли, деньги со счёта на счёт и т.п
//                .authorizeRequests() //авторизацуем запрос
//                .antMatchers("/auth/login", "/", "/eror", "/users/new").permitAll()
//                .antMatchers("/users/**").hasAnyRole("USER", "ADMIN") //прописываем доступ для юрл /user/**
//                .antMatchers("/admin/**").hasRole("ADMIN") //прописываем доступ для юрл /admin/**
//                .anyRequest().authenticated() // все запросы должны быть авторизованы и аутентифицированы
//                .and()
//                .formLogin().loginPage("/auth/login") // задаю форму для ввода логина-пароля, по дефолту это "/login"
//                .loginProcessingUrl("/process_login")
//                .defaultSuccessUrl("/users",true)
//                .failureUrl("/auth/login?eror")
//                .successHandler(successUserHandler)
//                .permitAll() // доступно всем
//
//                .and()
//                .logout().logoutUrl("/logout")
//                .logoutSuccessUrl("/auth/login")
//                .permitAll(); // настройка логаута
    }

}
