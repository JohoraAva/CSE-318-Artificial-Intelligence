package Heuristic;

import Board.MancalaBoard;

public abstract class Heuristic
{
    public abstract int getHeuristicValue(MancalaBoard board);
}
