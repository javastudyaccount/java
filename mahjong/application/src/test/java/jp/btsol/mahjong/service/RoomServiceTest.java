package jp.btsol.mahjong.service;

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
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import jp.btsol.mahjong.entity.Room;
import jp.btsol.mahjong.loader.XlsDataSetLoader;
import jp.btsol.mahjong.service.RoomServiceTest.TestConfig;

@DirtiesContext
@SpringBootTest(classes = {TestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
//@TestPropertySource(locations = "classpath:application-unittest.properties")
@Transactional
@DisplayName("RoomServiceTestのテストケース")
class RoomServiceTest {
    @Nested
    @DisplayName("getRoomsメソッドのテストケース")
    @DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
    class getRooms {
        @Autowired
        RoomService roomService;

        @Test
        @DisplayName("データが0件のテストケース")
        // testdata/room/in/rooms-empty.xlsx
        @DatabaseSetup(value = {"/testdata/room/in/rooms-empty.xlsx"}, type = DatabaseOperation.CLEAN_INSERT)
        void testGetRoomsEmtpty() {
            List<Room> rooms = roomService.getRooms();
            Assertions.assertEquals(0, rooms.size());
        }

        @Test
        @DisplayName("データが1件のテストケース")
        // testdata/room/in/rooms-1.xlsx
        @DatabaseSetup(value = {"/testdata/room/in/rooms-1.xlsx"}, type = DatabaseOperation.CLEAN_INSERT)
        void testGetRooms1() {
            List<Room> rooms = roomService.getRooms();
            Assertions.assertEquals(1, rooms.size());
            Room room = rooms.get(0);
            // テストを途中で止めずに一気に評価したい
            Assertions.assertAll(() -> Assertions.assertEquals(1, room.getRoomId()),
                    () -> Assertions.assertEquals("testRoom", room.getRoomName()));
        }

        @Test
        @DisplayName("データが複数件のテストケース")
        // testdata/room/in/rooms.xlsx
        @DatabaseSetup(value = {"/testdata/room/in/rooms.xlsx"}, type = DatabaseOperation.CLEAN_INSERT)
        void testGetRooms() {
            List<Room> rooms = roomService.getRooms();
            Assertions.assertEquals(33, rooms.size());
            Room room = rooms.get(0);
            Room room33 = rooms.get(32);
            // テストを途中で止めずに一気に評価したい
            Assertions.assertAll(() -> Assertions.assertEquals(1, room.getRoomId()),
                    () -> Assertions.assertEquals("testRoom", room.getRoomName()),
                    () -> Assertions.assertEquals(33, room33.getRoomId()),
                    () -> Assertions.assertEquals("test create new room2", room33.getRoomName()));
        }
    }

    @Nested
    @DisplayName("createNewRoomメソッドのテストケース")
    @DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
    class createNewRoom {
        @Autowired
        RoomService roomService;

        @Test
        void testCreateNewRoom() {
            RuntimeException e = Assertions.assertThrows(RuntimeException.class,
                    () -> roomService.createNewRoom(null, null));
            Assertions.assertEquals("room name can not be null.", e.getLocalizedMessage());
        }
    }

    @Configuration
    // @formatter:off
    @ComponentScan(value = "jp.btsol.mahjong")
    //@formatter:on
    public static class TestConfig {
    }
}
