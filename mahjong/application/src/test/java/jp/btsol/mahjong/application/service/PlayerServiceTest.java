package jp.btsol.mahjong.application.service;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import jp.btsol.mahjong.application.loader.XlsDataSetLoader;
import jp.btsol.mahjong.application.service.PlayerServiceTest.TestConfig;
import jp.btsol.mahjong.fw.UserContext;
import jp.btsol.mahjong.model.PlayerModel;

@DirtiesContext
@SpringBootTest(classes = {TestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@Transactional
@DisplayName("PlayerServiceTestのテストケース")
class PlayerServiceTest {
    @Nested
    @DisplayName("getInvites4Playerメソッドのテストケース")
    @DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
    class getInvites4Player {
        @Autowired
        PlayerService playerService;

        @Test
        @DisplayName("データが複数件のテストケース")
        // testdata/room/in/rooms.xlsx
        // @DatabaseSetup(value = {"/testdata/room/in/rooms.xlsx"}, type =
        // DatabaseOperation.CLEAN_INSERT)
        void test_getInvites4Player() {
            int invites = playerService.getInvites4Player(3);
            Assertions.assertEquals(1, invites);

        }
    }

    @Nested
    @DisplayName("getInvitedメソッドのテストケース")
    @DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
    class getInvited {
        @Autowired
        PlayerService playerService;
        @Autowired
        UserContext userContext;

        @Test
        @DisplayName("データが複数件のテストケース")
        // testdata/room/in/rooms.xlsx
        // @DatabaseSetup(value = {"/testdata/room/in/rooms.xlsx"}, type =
        // DatabaseOperation.CLEAN_INSERT)
        void test_getInvited() {
            userContext.playerId(3);
            List<PlayerModel> invites = playerService.getInvited();
            Assertions.assertEquals(1, invites.size());

        }
    }

    @Configuration
    // @formatter:off
    @ComponentScan(value = "jp.btsol.mahjong")
    //@formatter:on
    public static class TestConfig {
    }
}
