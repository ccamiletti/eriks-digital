package nl.eriksdigital.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.validation.Valid;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${disable.csrf}")
    private String disableCSRF;

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        if (disableCSRF.equalsIgnoreCase("true")) {
            http.authorizeRequests().antMatchers("/order").permitAll()
                    .antMatchers("/order/**").permitAll();
        } else {
            http.authorizeRequests()
                    .antMatchers("/swagger-ui/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .oauth2Login()
                    .and()
                    .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/user/logoutMessage");
        }
        http.csrf().disable();
    }
}
