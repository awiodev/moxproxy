package moxproxy.webservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebServiceSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    WebServiceConfiguration webServiceConfiguration;

    @Autowired
    AuthenticationEntryPoint authEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authEntryPoint);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        String user = webServiceConfiguration.getService().getBasicAuthUserName();
        String password = "{noop}" + webServiceConfiguration.getService().getBasicAuthPassword();

        auth.inMemoryAuthentication()
                .withUser(user)
                .password(password)
                .roles();
    }
}
