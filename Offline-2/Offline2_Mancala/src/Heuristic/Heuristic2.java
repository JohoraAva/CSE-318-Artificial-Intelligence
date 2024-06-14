package Heuristic;

import java.util.Random;

import Board.MancalaBoard;

public class Heuristic2 extends Heuristic {
     @Override
    public int getHeuristicValue(MancalaBoard board) 
    {
        int cur=0;

        int other=1;

        int stonesInStorageCur=board.stonesInStorage(cur);
        int stonesInStorageOther=board.stonesInStorage(other);


        int stonesInCur=board.getTotalStones(cur);
        int stonesInOther=board.getTotalStones(other);

        Random r=new Random();
        int w1=7;
        int w2=3;



        return w1*(stonesInStorageCur-stonesInStorageOther)+w2*(stonesInCur-stonesInOther);
    }
}

