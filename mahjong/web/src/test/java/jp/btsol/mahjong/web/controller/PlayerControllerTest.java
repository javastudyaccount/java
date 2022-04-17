package jp.btsol.mahjong.web.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.web.controller.PlayerControllerTest.TestConfig;
import jp.btsol.mahjong.web.service.PlayerService;

@DirtiesContext
@SpringBootTest(classes = {TestConfig.class})
@AutoConfigureMockMvc
@DisplayName("PlayersControllerTestのテストケース")
class PlayerControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private PlayerController playersController;

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlayerService playerService;

    /**
     * 各テストメソッド開始前に実行される.
     */
    @BeforeEach
    void beforeEach() {
//        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
//        mockMvc = MockMvcBuilders.standaloneSetup(playersController)
//                .setHandlerExceptionResolvers(handlerExceptionResolver)
//                .setMessageConverters(mappingJackson2HttpMessageConverter).build();
        mockMvc = MockMvcBuilders.standaloneSetup(playersController).build();
    }

    @Nested
    @DisplayName("playersメソッドのテストケース")
    class getPlayers {
        @Test
        void testPlayers() throws Exception {
            // モック設定
            List<Player> players = new ArrayList<>();
            when(playerService.getPlayers()).thenReturn(players);

            // 実行、検証
            MvcResult result = mockMvc.perform(get("/players"))//
                    .andDo(print())//
                    .andExpect(status().isOk())//
                    .andReturn();
            String content = result.getResponse().getContentAsString();
        }
    }

    @Test
    void testNewPlayer() {
        fail("まだ実装されていません");
    }

    @Test
    void testCreatePlayer() {
        fail("まだ実装されていません");
    }

    @Configuration
    @ComponentScan(value = "jp.btsol.mahjong.web")
    public static class TestConfig {
    }
}
