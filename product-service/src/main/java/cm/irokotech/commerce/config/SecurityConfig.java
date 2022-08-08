package cm.irokotech.commerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.buildersE.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) {

/*        httpSecurity.authorizeRequests(authorize -> authorize.anyRequest().authenticated());
                oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);*/

    }

}
