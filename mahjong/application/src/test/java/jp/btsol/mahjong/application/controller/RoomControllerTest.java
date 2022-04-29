package jp.btsol.mahjong.application.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.btsol.mahjong.application.controller.RoomControllerTest.TestConfig;
import jp.btsol.mahjong.application.fw.exception.DuplicateKeyException;
import jp.btsol.mahjong.application.service.RoomService;
import jp.btsol.mahjong.entity.ErrorDataEntity;
import jp.btsol.mahjong.entity.Room;
import jp.btsol.mahjong.model.RoomModel;

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

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 各テストメソッド開始前に実行される.
     */
    @BeforeEach
    void beforeEach() {
//        mockMvc = MockMvcBuilders.standaloneSetup(roomController).setHandlerExceptionResolvers(handlerExceptionResolver)
//                .build();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).setHandlerExceptionResolvers(handlerExceptionResolver)
                .setMessageConverters(mappingJackson2HttpMessageConverter).build();
    }

    @Nested
    @DisplayName("getRoomsメソッドのテストケース")
    class getRooms {
        @Test
        void testGetRoomsEmpty() throws Exception {
            // モック設定
            List<RoomModel> rooms = new ArrayList<>();
            when(roomService.getRooms()).thenReturn(rooms);

            // 実行、検証
            MvcResult result = mockMvc.perform(get("/room/all"))//
                    .andDo(print())//
                    .andExpect(status().isOk())//
                    .andReturn();
            String content = result.getResponse().getContentAsString();
            List<Room> roomsArr = objectMapper.readValue(content, ArrayList.class);
            Assertions.assertArrayEquals(rooms.toArray(new Room[0]), roomsArr.toArray(new Room[0]));
        }

        @Test
        void testGetRoomsKanji() throws Exception {
            // モック設定
            List<RoomModel> rooms = new ArrayList<>();
            RoomModel room = new RoomModel();
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
            List<Room> roomsArr = objectMapper.readValue(content, ArrayList.class);

            Assertions.assertArrayEquals(rooms.toArray(new Room[0]), roomsArr.toArray(new Room[0]));
        }

        @Test
        void testGetRooms() throws Exception {
            // モック設定
            List<RoomModel> rooms = new ArrayList<>();
            RoomModel room = new RoomModel();
            room.setRoomId(1);
            room.setRoomName("日本語");
            rooms.add(room);

            room = new RoomModel();
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
            List<Room> roomsArr = objectMapper.readValue(content, ArrayList.class);

            Assertions.assertArrayEquals(rooms.toArray(new Room[0]), roomsArr.toArray(new Room[0]));
        }
    }

    @Nested
    @DisplayName("createNewRoomメソッドのテストケース")
    class createNewRoom {
        @Test
        @Disabled
        @DisplayName("request-id is checked by HandlerInterceptor")
        void testCreateNewRoomNoRequestIDError() throws Exception {
            // 実行、検証
            mockMvc.perform(MockMvcRequestBuilders.multipart("/room/new")//
                    .contentType(MediaType.APPLICATION_JSON)//
                    .content("test room"))//
                    .andDo(print())//
                    .andExpect(status().isBadRequest());
        }

        @Test
        void testCreateNewRoomNameNullError() throws Exception {
            // 実行、検証
            mockMvc.perform(MockMvcRequestBuilders.multipart("/room/new")//
                    .header("request-id", "test-id")//
                    .contentType(MediaType.APPLICATION_JSON)//
                    .content(""))//
                    .andDo(print())//
                    .andExpect(status().isBadRequest());
        }

        @Test
        void testCreateNewRoomNameMoreThan50Error() throws Exception {
            // 実行、検証
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/room/new")//
                    .header("request-id", "test-id")//
                    .contentType(MediaType.APPLICATION_JSON)//
                    .content(
                            "{\"@class\" : \"jp.btsol.mahjong.model.RoomName\",\"roomName\":\"aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeef\"}"))//
                    .andDo(print())//
                    .andExpect(status().isBadRequest())//
                    .andReturn();
            ErrorDataEntity error = objectMapper
                    .readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), ErrorDataEntity.class);

            Assertions.assertEquals("size must be between 0 and 50", error.getErrorDetail());
        }

        @Test
        void testCreateNewRoom() throws Exception {
            Room room = new Room(1, //
                    "test room", //
                    false, //
                    new Timestamp(System.currentTimeMillis()), //
                    "test-id", //
                    new Timestamp(System.currentTimeMillis()), //
                    "test-id");
            when(roomService.createRoom("test room")).thenReturn(room);
            // 実行、検証
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/room/new")//
                    .header("request-id", "test-id")//
                    .contentType(MediaType.APPLICATION_JSON)//
                    .content("{\"@class\" : \"jp.btsol.mahjong.model.RoomName\",\"roomName\": \"test room\"}"))//
                    .andDo(print())//
                    .andExpect(status().isOk())//
                    .andReturn();
            String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
            Room roomRet = objectMapper.readValue(content, Room.class);

            Assertions.assertEquals(room, roomRet);
        }

        @Test
        void testCreateNewRoomDuplicateNameError() throws Exception {
            when(roomService.createRoom("test room")).thenThrow(new DuplicateKeyException("Room name exists."));
            // 実行、検証
            mockMvc.perform(MockMvcRequestBuilders.multipart("/room/new")//
                    .header("request-id", "test-id")//
                    .contentType(MediaType.APPLICATION_JSON)//
                    .content("{\"@class\" : \"jp.btsol.mahjong.model.RoomName\",\"roomName\":\"test room\"}"))//
                    .andDo(print())//
                    .andExpect(status().isInternalServerError())//
                    .andExpect(result -> Assertions.assertEquals("Room name exists.",
                            result.getResolvedException().getLocalizedMessage()));
        }
    }

    @Configuration
    @ComponentScan(value = "jp.btsol.mahjong")
    public static class TestConfig {
    }
}
