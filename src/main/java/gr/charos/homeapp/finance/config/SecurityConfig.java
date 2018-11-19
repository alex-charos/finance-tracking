package gr.charos.homeapp.finance.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Autowired
	ApplicationProperties appProperties;
    

	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
		  JwtWebSecurityConfigurer
	        .forRS256(appProperties.getAuth0().getAudience(), appProperties.getAuth0().getIssuer())
	        .configure(http);
		http.authorizeRequests()
	        .antMatchers(HttpMethod.GET, "/**").authenticated()
	        ;
	    http.authorizeRequests()
	        .antMatchers(HttpMethod.POST, "/**").authenticated()
	        ;
	    http.authorizeRequests()
	        .antMatchers(HttpMethod.PUT, "/**").authenticated()
	        ;
	    http.authorizeRequests()
	        .antMatchers(HttpMethod.DELETE, "/**").authenticated()
	        ;
	    
	   
	  }
   
}