package jp.btsol.mahjong.web.fw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.ExpiringSession;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
@EnableSpringHttpSession
public class SessionConfig {
    /**
     * sessionRepository FindByIndexNameSessionRepository
     */
    private final FindByIndexNameSessionRepository<ExpiringSession> sessionRepository;

    @Autowired
    public SessionConfig(FindByIndexNameSessionRepository<ExpiringSession> sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Bean
    protected SpringSessionBackedSessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry(this.sessionRepository);
    }
}
