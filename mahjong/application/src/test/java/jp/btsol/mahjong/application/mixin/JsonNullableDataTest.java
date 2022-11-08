package jp.btsol.mahjong.application.mixin;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@DirtiesContext
@SpringBootTest
@DisplayName("JsonNullaleDataのテストケース")
@Slf4j
class JsonNullableDataTest {
    private final ObjectMapper MAPPER = mapperWithModule();

    @Test
    @DisplayName("JsonNullaleDataのテストケース")
    void test_readString2JsonNullableData() {
        try {
            JsonNullableData data = MAPPER.readValue("{\"myString\":\"simpleString\"}", JsonNullableData.class);
            assertNotNull(data);
        } catch (JsonProcessingException e) {
            log.info("exception {}", e.getLocalizedMessage());
            fail(e.getLocalizedMessage());
        }
    }

    @Test
    @DisplayName("JsonNullaleDataのテストケース")
    void test_writeJsonNullableData2String() {
        try {
            JsonNullableData data = new JsonNullableData();
            data.myString = JsonNullable.of("simpleString");
            String json = MAPPER.writeValueAsString(data);

            assertEquals("{\"myString\":\"simpleString\"}", json);
        } catch (JsonProcessingException e) {
            log.info("exception {}", e.getLocalizedMessage());
            fail(e.getLocalizedMessage());
        }
    }

    protected ObjectMapper mapperWithModule() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JsonNullableModule());
        return mapper;
    }
}
