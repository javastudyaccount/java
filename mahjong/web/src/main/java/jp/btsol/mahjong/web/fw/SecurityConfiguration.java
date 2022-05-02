package jp.btsol.mahjong.web.fw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jp.btsol.mahjong.web.service.PlayerService;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * service PlayerService
     */
    private final PlayerService service;
    /**
     * passwordEncoder PasswordEncoder
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * rememberMeTokenRepositoryImpl RememberMeTokenRepositoryImpl
     */
    private final RememberMeTokenRepositoryImpl rememberMeTokenRepositoryImpl;

    @Autowired
    public SecurityConfiguration(PlayerService service, PasswordEncoder passwordEncoder,
            RememberMeTokenRepositoryImpl rememberMeTokenRepositoryImpl) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.rememberMeTokenRepositoryImpl = rememberMeTokenRepositoryImpl;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(//
                "/favicon.ico", //
                "/css/**", //
                "/image/**", //
                "/js/**", //
                "/webjars/**", //
                "/gs-guide-websocket/**", //
                "/index.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 「login.html」はログイン不要でアクセス可能に設定
                .antMatchers("/", "/signin", "/login", "/error", "/system-error", "/logout", "/afterLogout").permitAll()
                // disable remember-me authentication for important page
                .antMatchers("/remember-me/high-level").fullyAuthenticated()// Remember-Me によるログインの場合は重要な処理の実行を許可しない
                // 上記以外は直リンク禁止
                .anyRequest().authenticated()// Remember-Me認証も許可する
                .and() //
                .formLogin()
                // ログイン処理のパス
                .loginProcessingUrl("/login")
                // ログインページ
                .loginPage("/login")
                // ログインエラー時の遷移先 ※パラメーターに「error」を付与
                .failureUrl("/login?error")
                // ログイン成功時の遷移先
                .defaultSuccessUrl("/players", false)
                // ログイン時のキー：名前
                .usernameParameter("loginId")
                // ログイン時のパスワード
                .passwordParameter("password") //
                .and()
                // Logout
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //
                .logoutUrl("/logout") // ログアウトのURL
//                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                // ログアウト時の遷移先 POSTでアクセス
                .logoutSuccessUrl("/")//
                .and()//
                .rememberMe() // ログイン状態を保持する
//                .tokenValiditySeconds(604800) //秒数。デフォルトでは前回のアクセスから 2 週間は 有効です。
//                .key("uniqueAndSecret")//
//                .alwaysRemember(true)//
                .useSecureCookie(true)// Cookie の secure 属性を有効にする

                .tokenRepository(rememberMeTokenRepositoryImpl);
        http.headers().frameOptions().sameOrigin(); // X-Frame-Options
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(passwordEncoder);
    }
}