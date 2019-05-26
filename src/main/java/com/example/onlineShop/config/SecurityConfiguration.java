package com.example.onlineShop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .usersByUsernameQuery("SELECT username, user_password, enabled FROM Users WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT username, title FROM Users " +
                        "JOIN Roles ON Users.user_role = Roles.id WHERE username = ?")
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers("/products").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/js/*").permitAll()
                .antMatchers("/css/*").permitAll()
                .antMatchers("/sign_up").permitAll()
                .antMatchers("/create_product").hasRole("ADMIN")
                .antMatchers("/admin_overview").hasRole("ADMIN")
                .antMatchers("/admin_edit_product").hasRole("ADMIN")
                .antMatchers("/admin_edit_product/**").hasRole("ADMIN")
                .antMatchers("/admin_products_delete").hasRole("ADMIN")
                .antMatchers("/admin_products").hasRole("ADMIN")
                .antMatchers("/create_category").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout").permitAll();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
