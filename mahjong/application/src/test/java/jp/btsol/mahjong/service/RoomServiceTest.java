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
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import jp.btsol.mahjong.application.fw.exception.DuplicateKeyException;
import jp.btsol.mahjong.application.service.RoomService;
import jp.btsol.mahjong.entity.Room;
import jp.btsol.mahjong.loader.XlsDataSetLoader;
import jp.btsol.mahjong.service.RoomServiceTest.TestConfig;

@DirtiesContext
@SpringBootTest(classes = {TestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
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
                    () -> Assertions.assertEquals("testRoom1", room.getRoomName()),
                    () -> Assertions.assertEquals(33, room33.getRoomId()),
                    () -> Assertions.assertEquals("test create new room233", room33.getRoomName()));
        }
    }

    @Nested
    @DisplayName("createNewRoomメソッドのテストケース")
    @DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
    class createNewRoom {
        @Autowired
        RoomService roomService;

        @Test
        void testCreateNewRoomNameIsNullError() {
            RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> roomService.createNewRoom(null));
            Assertions.assertEquals("room name can not be null.", e.getLocalizedMessage());
        }

        @Test
        void testCreateNewRoomNameIsTooLongError() {
            String nameIsMoreThan50 = "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeef";
            RuntimeException e = Assertions.assertThrows(RuntimeException.class,
                    () -> roomService.createNewRoom(nameIsMoreThan50));
            Assertions.assertEquals("Lenght of column roomName is more than 50.", e.getLocalizedMessage());
        }

        @Test
        void testCreateNewRoomKanji() {
            String name = "お楽しみ室";
            Room room = roomService.createNewRoom(name);
            Assertions.assertNotNull(room);
            Assertions.assertEquals("お楽しみ室", room.getRoomName());
        }

        @Test
        void testCreateNewRoomMaxLength() {
            String name = "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeee";
            Room room = roomService.createNewRoom(name);
            Assertions.assertNotNull(room);
            Assertions.assertEquals("aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeee", room.getRoomName());
        }

        @Test
        void testCreateNewRoomKanjiTooLongError() {
            String nameIsMoreThan50 = "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十・";
            RuntimeException e = Assertions.assertThrows(RuntimeException.class,
                    () -> roomService.createNewRoom(nameIsMoreThan50));
            Assertions.assertEquals("Lenght of column roomName is more than 50.", e.getLocalizedMessage());
        }

        @Test
        void testCreateNewRoomKanjiMaxLength() {
            String name = "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
            Room room = roomService.createNewRoom(name);
            Assertions.assertNotNull(room);
            Assertions.assertEquals("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十", room.getRoomName());
        }

        @Test
        @DisplayName("Use excel to assert")
        @DatabaseSetup(value = {"/testdata/room/in/rooms-empty.xlsx"}, type = DatabaseOperation.CLEAN_INSERT)
        @ExpectedDatabase(value = "/testdata/room/out/rooms-1.xlsx", assertionMode = DatabaseAssertionMode.NON_STRICT)
        void testCreateNewRoomAssertionByExcel() {
            String name = "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
            Room room = roomService.createNewRoom(name);
            Assertions.assertNotNull(room);
//            Assertions.assertEquals("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十", room.getRoomName());
        }

        @Test
        void testCreateNewRoomNameUniqueError() {
            String name = "一二三四五六七八九十";
            Room room = roomService.createNewRoom(name);
            RuntimeException e = Assertions.assertThrows(DuplicateKeyException.class,
                    () -> roomService.createNewRoom(name));
            Assertions.assertEquals("Room name exists.", e.getLocalizedMessage());
        }
    }

    @Configuration
    // @formatter:off
    @ComponentScan(value = "jp.btsol.mahjong")
    //@formatter:on
    public static class TestConfig {
    }
}