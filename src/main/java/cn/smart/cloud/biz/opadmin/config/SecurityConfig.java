package cn.smart.cloud.biz.opadmin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    //@Autowired
    //private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("sf2015").roles("USER")
                .and()
                .withUser("smart").password("sfb20150707").roles("ADMIN");//"USER",
        /*auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new Md5PasswordEncoder())
                .getUserDetailsService()
                .setEnableGroups(true)*/
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //.antMatchers("/security/**").permitAll()
                .anyRequest().permitAll();
                //.anyRequest().authenticated()
                //.and()
                //    .formLogin().loginPage("/login").permitAll()
                //.and()
                //    .logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll();
        /*http
            .csrf().disable()
            .authorizeRequests()
                    .antMatchers("/", "/home", "/info", "/auth/**").permitAll()//ROLE_ANONYMOUS
                    .antMatchers("/", "/login", "/oauth/authorize", "/oauth/confirm_access").permitAll()
                    .antMatchers("/**").access("hasRole('USER')")//hasRole('USER') and
                    .anyRequest().authenticated()
            .and()
                .logout()//.logoutUrl("/logout").logoutSuccessUrl("/login").permitAll()//定义logout不需要验证  
            .and()
                .formLogin().loginPage("/login").permitAll(); //不自定义
            //.and() //与 permitAll 相反，使所有请求均需要通过鉴权
            //    .httpBasic();
            /*.and()
                .requestMatchers()
                .antMatchers("/", "/login", "/oauth/authorize", "/oauth/confirm_access")
            .and()
                .authorizeRequests()
                .anyRequest().authenticated();*/
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
