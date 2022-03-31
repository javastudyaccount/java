package jp.btsol.mahjong.web.fw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/image/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/signin").permitAll().antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();
        http.formLogin().loginProcessingUrl("/login").loginPage("/signin").failureUrl("/error")
                .defaultSuccessUrl("/", false).usernameParameter("loginId").passwordParameter("password").and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("signout")).logoutSuccessUrl("/signin")
                .deleteCookies("JSESSIONID").invalidateHttpSession(true).permitAll();
        http.sessionManagement().invalidSessionUrl("/signin");
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery(
//                        "select mail_address as username, password, enabled from accounts where mail_address = ?")
//                .authoritiesByUsernameQuery(
//                        "select mail_address, role from accounts where mail_address = ?")
//                .passwordEncoder(new ShaPasswordEncoder(256));
    }
}