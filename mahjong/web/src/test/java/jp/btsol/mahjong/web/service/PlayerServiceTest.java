package jp.btsol.mahjong.web.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;

import jp.btsol.mahjong.web.service.PlayerServiceTest.TestConfig;

@DirtiesContext
@SpringBootTest(classes = {TestConfig.class})
@AutoConfigureMockMvc
@DisplayName("PlayerServiceTestのテストケース")
class PlayerServiceTest {
    @Autowired
    private PlayerService playerService;

    @Nested
    @DisplayName("getInvitesメソッドのテストケース")
    class getInvites {
        @Test
        void testGetInvites() {
            int invites = playerService.getInvites(3);
            Assertions.assertEquals(1, invites);
        }
    }

    @Configuration
    @ComponentScan(value = "jp.btsol.mahjong.web")
    public static class TestConfig {
    }
}
