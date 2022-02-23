package jp.btsol.mahjong.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
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
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.btsol.mahjong.controller.RoomControllerTest.TestConfig;
import jp.btsol.mahjong.entity.Room;
import jp.btsol.mahjong.service.RoomService;

@DirtiesContext
@SpringBootTest(classes = {TestConfig.class})
@AutoConfigureMockMvc
@DisplayName("RoomControllerTestのテストケース")
class RoomControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private RoomController roomController;
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @MockBean
    private RoomService roomService;

    private ObjectMapper om;

    /**
     * 各テストメソッド開始前に実行される.
     */
    @BeforeEach
    void beforeEach() {
        om = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).setHandlerExceptionResolvers(handlerExceptionResolver)
                .build();
    }

    @Nested
    @DisplayName("getRoomsメソッドのテストケース")
    class getRooms {
        @Autowired
        RoomService roomService;

        @Test
        void testGetRoomsEmpty() throws Exception {
            // モック設定
            List<Room> rooms = new ArrayList<>();
            when(roomService.getRooms()).thenReturn(rooms);

            // 実行、検証
            MvcResult result = mockMvc.perform(get("/room/all"))//
                    .andDo(print())//
                    .andExpect(status().isOk())//
                    .andReturn();
            String content = result.getResponse().getContentAsString();
            Assertions.assertEquals("[]", content);
        }

        @Test
        void testGetRoomsKanji() throws Exception {
            // モック設定
            List<Room> rooms = new ArrayList<>();
            Room room = new Room();
            room.setRoomId(1);
            room.setRoomName("日本語");
            rooms.add(room);
            when(roomService.getRooms()).thenReturn(rooms);

            // 実行、検証
            MvcResult result = mockMvc.perform(get("/room/all"))//
                    .andDo(print())//
                    .andExpect(status().isOk())//
                    .andReturn();
            String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
            Room[] roomsArr = om.readValue(content, Room[].class);

            Assertions.assertArrayEquals(rooms.toArray(new Room[0]), roomsArr);
        }

        @Test
        void testGetRooms() throws Exception {
            // モック設定
            List<Room> rooms = new ArrayList<>();
            Room room = new Room();
            room.setRoomId(1);
            room.setRoomName("日本語");
            rooms.add(room);

            room = new Room();
            room.setRoomId(2);
            rooms.add(room);
            room.setRoomName("Test room");
            when(roomService.getRooms()).thenReturn(rooms);

            // 実行、検証
            MvcResult result = mockMvc.perform(get("/room/all"))//
                    .andDo(print())//
                    .andExpect(status().isOk())//
                    .andReturn();
            String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
            Room[] roomsArr = om.readValue(content, Room[].class);

            Assertions.assertArrayEquals(rooms.toArray(new Room[0]), roomsArr);
        }
    }

    @Nested
    @DisplayName("createNewRoomメソッドのテストケース")
    class createNewRoom {
        @Test
        void testCreateNewRoom() throws Exception {
            Room room = new Room(1, //
                    "test room", //
                    false, //
                    null, //
                    "test-id", //
                    null, //
                    "test-id");
            when(roomService.createNewRoom("test room", "test-id")).thenReturn(room);
            // 実行、検証
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/room/new")//
                    .header("request-id", "test-id")//
                    .contentType(MediaType.APPLICATION_JSON)//
                    .content(om.writeValueAsString(room)))//
                    .andDo(print())//
                    .andExpect(status().isOk())//
                    .andReturn();
            String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
            Room roomRet = om.readValue(content, Room.class);

            Assertions.assertEquals(room, roomRet);
        }
    }

    @Configuration
    // @formatter:off
    @ComponentScan(value = "jp.btsol.mahjong")
    //@formatter:on
    public static class TestConfig {
    }
}
