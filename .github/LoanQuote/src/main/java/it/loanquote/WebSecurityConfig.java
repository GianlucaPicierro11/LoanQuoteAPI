package it.loanquote;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import it.loanquote.filter.AuthenticationFilter;
import it.loanquote.log.RequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${testMode:false}")
  private boolean testMode;

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {

    httpSecurity.httpBasic().and().csrf().disable().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors().and()
        .authorizeRequests()
        .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
            "/configuration/security", "/swagger-ui.html", "/webjars/**", "/v1/isRunning")
        .permitAll().antMatchers("/public/**").permitAll();

    httpSecurity.authorizeRequests().anyRequest().authenticated().and()
        .addFilterBefore(aifaAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(requestFilterBean(), AuthenticationFilter.class);

    httpSecurity.headers().cacheControl();
  }

  public AuthenticationFilter aifaAuthenticationFilterBean() {
    return new AuthenticationFilter(testMode);
  }

  public RequestFilter requestFilterBean() {
    return new RequestFilter();
  }

}
