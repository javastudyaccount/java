package jp.btsol.mahjong.web.fw;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/image/**", "/js/**");
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
}