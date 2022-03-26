package jp.btsol.mahjong.application.fw.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * セキュリティ設定。
 * <p>
 * app.security.enabled の場合は認証機能を有効化します。
 * <p>
 * app.security.enabled の場合は認証機能を無効化します。
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Environment
     */
    @Autowired
    private Environment environment;

    /**
     * userInfoUri
     */
    @Value("${spring.security.oauth2.resourceserver.opaquetoken.userinfo-uri:http://localhost/}") //
    private String userinfoUri;

    /**
     * ConnectTimeout
     */
    @Value("${spring.security.oauth2.resourceserver.opaquetoken.connect-timeout:60}") //
    private Integer connectTimeout;

    /**
     * ReadTimeout
     */
    @Value("${spring.security.oauth2.resourceserver.opaquetoken.read-timeout:60}") //
    private Integer readTimeout;

    @Override //
    protected void configure(HttpSecurity http) throws Exception {

        boolean appSecurityEnabled = Boolean.parseBoolean(this.environment.getProperty("app.security.enabled", "true"));
        if (!appSecurityEnabled) {
            http.authorizeRequests().anyRequest().permitAll();
            http.csrf().disable();
            return;
        }

        //@formatter:off
        http//
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(http401UnauthorizedEntryPoint())
            .and()
            .csrf()
            .disable();
        
        //@formatter:on
    }

    @Bean
    public AuthenticationEntryPoint http401UnauthorizedEntryPoint() {
        return (request, response, ae) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
        };
    }

}
