package jp.btsol.mahjong.web.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
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
import org.springframework.web.servlet.ModelAndView;

import jp.btsol.mahjong.model.RoomModel;
import jp.btsol.mahjong.web.controller.RoomControllerTest.TestConfig;
import jp.btsol.mahjong.web.service.RoomService;

@DirtiesContext
@SpringBootTest(classes = {TestConfig.class})
@AutoConfigureMockMvc
@DisplayName("RoomControllerTestのテストケース")
class RoomControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private RoomController roomController;
//    @Autowired
//    private HandlerExceptionResolver handlerExceptionResolver;
    @MockBean
    private RoomService roomService;

    /**
     * 各テストメソッド開始前に実行される.
     */
    @BeforeEach
    void beforeEach() {
//        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
//        mockMvc = MockMvcBuilders.standaloneSetup(roomController).setHandlerExceptionResolvers(handlerExceptionResolver)
//                .setMessageConverters(mappingJackson2HttpMessageConverter).build();
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).build(); // build pattern
    }

    @Nested
    @DisplayName("roomメソッドのテストケース")
    class Rooms {
        @Test
        void testRooms() throws Exception {
//            fail("まだ実装されていません");
            // モック設定
            List<RoomModel> rooms = new ArrayList<>();
            when(roomService.getRooms()).thenReturn(rooms);

            // 実行、検証
            MvcResult result = mockMvc.perform(get("/rooms"))//
                    .andDo(print())//
                    .andExpect(status().isOk())//
                    .andReturn();
            ModelAndView mav = result.getModelAndView();
            Assertions.assertEquals("room/rooms", mav.getViewName());
            Assertions.assertFalse((boolean) mav.getModel().get("entered"));
            Assertions.assertEquals(0, ((List<RoomModel>) mav.getModel().get("rooms")).size());
        }
    }

    @Configuration
    @ComponentScan(value = "jp.btsol.mahjong")
    public static class TestConfig {
    }
}
