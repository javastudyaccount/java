package jp.btsol.mahjong.web.fw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico", "/css/**", "/image/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/signin", "/login", "/error").permitAll()//
                .antMatchers("/admin/**").hasRole("ADMIN")//
                .anyRequest().authenticated();
//        http.formLogin().loginProcessingUrl("/login").loginPage("/login").failureUrl("/error")
//                .defaultSuccessUrl("/", false).usernameParameter("loginId").passwordParameter("password").and().logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("signout")).logoutSuccessUrl("/login")
//                .deleteCookies("JSESSIONID").invalidateHttpSession(true).permitAll();
        http.sessionManagement().invalidSessionUrl("/login");
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        log.info("login");
    }
}