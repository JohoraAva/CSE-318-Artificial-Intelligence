package Heuristic;

import Board.MancalaBoard;

public class Heuristic1 extends Heuristic {
    @Override
    public int getHeuristicValue(MancalaBoard board) 
    {
        int cur=0;

        int other=1;

        int stonesInCur=board.stonesInStorage(cur);
        int stonesInOther=board.stonesInStorage(other);



        return stonesInCur-stonesInOther;
    }
}
