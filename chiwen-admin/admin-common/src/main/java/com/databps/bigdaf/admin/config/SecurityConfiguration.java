package com.databps.bigdaf.admin.config;

import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.security.Http401UnauthorizedEntryPoint;
import com.databps.bigdaf.admin.security.jwt.JWTConfigurer;
import com.databps.bigdaf.admin.security.jwt.TokenProvider;
import io.github.jhipster.config.JHipsterProperties;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  private final UserDetailsService userDetailsService;

  private final TokenProvider tokenProvider;

  private final JHipsterProperties jHipsterProperties;

  private final CorsFilter corsFilter;

  private final RememberMeServices rememberMeServices;

  public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder,
      JHipsterProperties jHipsterProperties, RememberMeServices rememberMeServices,
      UserDetailsService userDetailsService,
      TokenProvider tokenProvider,
      CorsFilter corsFilter) {

    this.authenticationManagerBuilder = authenticationManagerBuilder;
    this.userDetailsService = userDetailsService;
    this.tokenProvider = tokenProvider;
    this.corsFilter = corsFilter;
    this.jHipsterProperties = jHipsterProperties;
    this.rememberMeServices = rememberMeServices;
  }


  @Bean
  public Http401UnauthorizedEntryPoint http401UnauthorizedEntryPoint() {
    return new Http401UnauthorizedEntryPoint();
  }

  @PostConstruct
  public void init() {
    try {
      authenticationManagerBuilder
          .userDetailsService(userDetailsService)
          .passwordEncoder(passwordEncoder());
    } catch (Exception e) {
      throw new BeanInitializationException("Security configuration failed", e);
    }
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers(HttpMethod.OPTIONS, "/**")
        .antMatchers("/resources/**/*.{js,html}","/favicon.ico")
    .antMatchers("/lib/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling()
        .authenticationEntryPoint(http401UnauthorizedEntryPoint())
        .and()
        .csrf()
        .disable()
        .headers()
        .frameOptions()
        .disable()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/register").permitAll()
        .antMatchers("/resources/**").permitAll()
        .antMatchers("/resources/static/**").permitAll()
        .antMatchers("/static/**").permitAll()
        .antMatchers("/lib/**").permitAll()
        .antMatchers("/js/**").permitAll()
        .antMatchers("/img/**").permitAll()
        .antMatchers("/css/**").permitAll()
        .antMatchers("/fonts/**").permitAll()
        .antMatchers("/authenticate").permitAll()
        .antMatchers("/account/reset_password/init").permitAll()
        .antMatchers("/account/reset_password/finish").permitAll()
        .antMatchers("/vulnerability/**").permitAll()
        .antMatchers("/api/v2/**").permitAll()
        .antMatchers("/api/**").authenticated()
//        .antMatchers("/home/**").permitAll()
//        .antMatchers("/user/**").permitAll().antMatchers("/group/**").permitAll()
        .antMatchers("/privilege/**").permitAll()
        .antMatchers("/hbase/privilege/getPolicy").permitAll()
            .antMatchers("/defaultKaptcha").permitAll()
            .antMatchers("/imgvrifyControllerDefaultKaptcha").permitAll()
//        .antMatchers("/log/**").permitAll()
//        .antMatchers("/jurisdiction/**").permitAll()
//        .antMatchers("/services/**").permitAll()

        //.antMatchers("/privilege/**").permitAll()
        //.antMatchers("/browse/**").permitAll().antMatchers("/sendbox/**").permitAll()
        .antMatchers("/**").authenticated()
        .antMatchers("/management/health").permitAll()
        .antMatchers("/management/**").hasAuthority(
        AuthoritiesConstants.ADMIN)
        .and()
        .apply(securityConfigurerAdapter());

  }

  private JWTConfigurer securityConfigurerAdapter() {
    return new JWTConfigurer(tokenProvider);
  }


  @Bean
  public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
    return new SecurityEvaluationContextExtension();
  }

}
