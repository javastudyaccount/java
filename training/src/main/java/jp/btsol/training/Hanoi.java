package jp.btsol.training;

/**
 * Hanoi tower algorithm
 * 
 * @author B&T Solutions Inc.
 *
 */
public class Hanoi {
    /**
     * find moves for 3 tower
     * 
     * @param dishes count of dishes(<= 12)
     * @return moves(return 0 if dishes > 12)
     */
    public static int findMoves3(int dishes) {
        if (dishes < 1) {
            System.out.println("please give dishes > 0");
        }
        if (dishes > 12) {
            System.out.println("please give dishes <= 12");
            return 0;
        }
        if (dishes == 1) {
            return 1;
        }
        if (dishes == 2) {
            return 3;
        }
        if (dishes == 3) {
            return 7;
        }
        return findMoves3(dishes - 1) + 1 + findMoves3(dishes - 1);
    }

    /**
     * find moves for 4 tower
     * 
     * @param dishes count of dishes(<= 12)
     * @return moves(return 0 if dishes > 12)
     */
    public static int findMoves(int dishes) {
        // parameter check
        if (dishes < 1) {
            System.out.println("please give dishes > 0");
        }
        if (dishes > 12) {
            System.out.println("please give dishes <= 12");
            return 0;
        }

        if (dishes == 1) {
            return 1;
        }
        if (dishes == 2) {
            return 3;
        }
        if (dishes == 3) {
            return 5;
        }
        int moves = 32767;
        for (int first = 1; first <= dishes - 1; first++) {
            int thisMoves = findMoves(first) + findMoves3(dishes - first) + findMoves(first);
            if (thisMoves < moves) {
                moves = thisMoves;
            }
        }
        return moves;
    }
}
