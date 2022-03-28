package jp.btsol.mahjong.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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

import jp.btsol.mahjong.application.loader.XlsDataSetLoader;
import jp.btsol.mahjong.application.service.DBSetTest.TestConfig;

@DirtiesContext
@SpringBootTest(classes = {TestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
//@Transactional
@DisplayName("DBSetTestのテストケース")
class DBSetTest {
    @Nested
    @DisplayName("SetDB")
    @DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
    class setDbData {

        @Test
        @DisplayName("SET DB DATA")
        // testdata/room/in/rooms-empty.xlsx
        @DatabaseSetup(value = {"/testdata/dbdata_all.xlsx"}, type = DatabaseOperation.CLEAN_INSERT)
        void setDbData() {
            // insert excel data
        }

    }

    @Configuration
    // @formatter:off
    @ComponentScan(value = "jp.btsol.mahjong")
    //@formatter:on
    public static class TestConfig {
    }
}
