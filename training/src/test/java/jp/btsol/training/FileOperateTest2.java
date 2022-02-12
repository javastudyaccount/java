package jp.btsol.training;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileOperateTest2 {

    @Test
    void testSearch() {
        String filename = "./file/english_utf8.txt";
        try {
            List<String> lines = FileOperate.search(filename, "w");
            Assertions.assertEquals(8, lines.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSearchFilenameIsNull() {
        String filename = null;
        try {
            List<String> lines = FileOperate.search(filename, "w");
            Assertions.assertEquals(8, lines.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
