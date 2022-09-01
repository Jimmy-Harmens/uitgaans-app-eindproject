package com.example.stapp.config;

import com.example.stapp.filter.JWTRequestFilter;
import com.example.stapp.services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    public CustomUserDetailService customUserDetailsService;

    @Autowired
    private JWTRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //JWT token authentication
        http
                .httpBasic()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/location").hasAnyRole("USER", "ADMIN") //Het gaat om deze
                .antMatchers(HttpMethod.POST, "/location").hasAnyRole("BUSINESS", "ADMIN")
                .antMatchers(HttpMethod.GET, "/location/favorites").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/location/owned").hasAnyRole("BUSINESS", "ADMIN")
                .antMatchers(HttpMethod.POST, "/location/hardcode").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/location/byid").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/favorite").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/favorite").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/ratings").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/ratings").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "/picture/location").hasAnyRole("BUSINESS","ADMIN")
                .antMatchers(HttpMethod.POST, "/picture/user").hasAnyRole("BUSINESS","ADMIN")
                .antMatchers(HttpMethod.POST,"/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                .antMatchers("/authenticated").authenticated()//no touch
                .antMatchers("/authenticate").permitAll()//also, no touch
                .anyRequest().permitAll()
                .and()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
