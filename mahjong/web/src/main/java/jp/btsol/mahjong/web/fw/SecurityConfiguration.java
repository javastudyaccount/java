package jp.btsol.mahjong.web.fw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Autowired
    private PlayerService service;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico", "/css/**", "/image/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 「login.html」はログイン不要でアクセス可能に設定
                .antMatchers("/signin", "/login", "/error", "/system-error", "/logout", "/afterLogout").permitAll()
                // 上記以外は直リンク禁止
                .anyRequest().authenticated()//
                .and() //
                .formLogin()
                // ログイン処理のパス
                .loginProcessingUrl("/login")
                // ログインページ
                .loginPage("/login")
                // ログインエラー時の遷移先 ※パラメーターに「error」を付与
                .failureUrl("/login?error")
                // ログイン成功時の遷移先
                .defaultSuccessUrl("/players", true)
                // ログイン時のキー：名前
                .usernameParameter("loginId")
                // ログイン時のパスワード
                .passwordParameter("password") //
                .and()
                // Logout
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //
                .logoutUrl("/logout") // ログアウトのURL
                .invalidateHttpSession(true)
                // ログアウト時の遷移先 POSTでアクセス
                .logoutSuccessUrl("/afterLogout.html");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // フォームの値と比較するDBから取得したパスワードは暗号化されているのでフォームの値も暗号化するために利用
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        auth.userDetailsService(service).passwordEncoder(encoder);
    }

}