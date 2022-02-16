package jp.btsol.training;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HanoiTest {
    @Test
    void testFindMovesMinus() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        int moves = new Hanoi().findMoves(-1);
        Assertions.assertEquals("please give dishes > 0\r\n", output.toString());
    }

    @Test
    void testFindMoves() {
        int moves = new Hanoi().findMoves(1);
        Assertions.assertEquals(1, moves);
    }

    @Test
    void testFindMoves2() {
        int moves = new Hanoi().findMoves(2);
        Assertions.assertEquals(3, moves);
    }

    @Test
    void testFindMoves3() {
        int moves = new Hanoi().findMoves(3);
        Assertions.assertEquals(5, moves);
    }

    @Test
    void testFindMoves4() {
        int moves = new Hanoi().findMoves(4);
        Assertions.assertEquals(9, moves);
    }

    @Test
    void testFindMoves5() {
        int moves = new Hanoi().findMoves(5);
        Assertions.assertEquals(13, moves);
    }

    @Test
    void testFindMoves6() {
        int moves = new Hanoi().findMoves(6);
        Assertions.assertEquals(17, moves);
    }

    @Test
    void testFindMoves7() {
        int moves = new Hanoi().findMoves(7);
        Assertions.assertEquals(25, moves);
    }

    @Test
    void testFindMoves8() {
        int moves = new Hanoi().findMoves(8);
        Assertions.assertEquals(33, moves);
    }

    @Test
    void testFindMoves9() {
        int moves = new Hanoi().findMoves(9);
        Assertions.assertEquals(41, moves);
    }

    @Test
    void testFindMoves10() {
        int moves = new Hanoi().findMoves(10);
        Assertions.assertEquals(49, moves);
    }

    @Test
    void testFindMoves11() {
        int moves = new Hanoi().findMoves(11);
        Assertions.assertEquals(65, moves);
    }

    @Test
    void testFindMoves12() {
        int moves = new Hanoi().findMoves(12);
        Assertions.assertEquals(81, moves);
    }

    @Test
    void testFindMoves13() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        int moves = new Hanoi().findMoves(13);
        Assertions.assertEquals("please give dishes <= 12\r\n", output.toString());
    }
}
