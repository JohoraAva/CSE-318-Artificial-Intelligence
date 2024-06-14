package Heuristic;

import Board.MancalaBoard;

import java.util.Random;

public class Heuristic3 extends Heuristic {
    @Override
    public int getHeuristicValue(MancalaBoard board)
    {
        int cur=0;
        int other=1;

        int stonesInStorageCur=board.stonesInStorage(cur);
        int stonesInStorageOther=board.stonesInStorage(other);


        int stonesInCur=board.getTotalStones(cur);
        int stonesInOther=board.getTotalStones(other);


        int w1=11;
        int w2=7;
        int w3=2;

        int addMOve=board.extraMoves[cur];


        return w1*(stonesInStorageCur-stonesInStorageOther)+w2*(stonesInCur-stonesInOther)+w3*addMOve;
    }
}
