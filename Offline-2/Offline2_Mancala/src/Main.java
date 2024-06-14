import Board.MancalaBoard;
import Heuristic.*;

import java.util.Scanner;

public class Main {

    MancalaBoard board;
    public static void main(String[] args) {

        Scanner input=new Scanner(System.in);
        System.out.println("Select one:\n1. Human vs AI\n2. AI vs AI");
        int option=input.nextInt();

        System.out.println("Select a Heuristic function for player 1 from 1 to 4");

        int heuristicNo=input.nextInt();
        MancalaBoard board=new MancalaBoard(9,4);

        board.setHeuristic(0,getHeuristic(heuristicNo));


        System.out.println("Select a Heuristic function for player 2 from 1 to 4");


        heuristicNo=input.nextInt();
        board.setHeuristic(1,getHeuristic(heuristicNo));


        board.curPlayer=0;
        int moveNo=0;
        if(option==1)
        {
            System.out.println("Do you want to make 1st move?\n1.Yes\n2.No");
            moveNo=input.nextInt();
            moveNo--;

        }
        board.print();
        int[] moves;
        moves=new int[2];

       while(true)
       {
           if(board.isGameOver(board.curPlayer))
           {
//               System.out.println(board.getTotalStone(board.curPlayer));
               break;
           }


         //  System.out.println("printing cnt= "+ ++cnt+" curplayer= "+board.curPlayer+ " ismax= "+ board.maxPlayer);
           int move=0;
           if(option==1)
          {

              if(board.curPlayer==moveNo)
              {
                  move=input.nextInt();

              }
              else
              {
                  move=board.getMove(board.curPlayer);
              }
          }
           else
           {
//               board.curPlayer=1;
               move=board.getMove(board.curPlayer);
           }

           System.out.println("Player= "+(board.curPlayer+1)+ " selects "+move+ " no bin .Moves: "+ moves[board.curPlayer]);
           board=board.move(board.curPlayer,move);
           moves[board.curPlayer]++;
           board.print();

       }

        System.out.println("------------------ooooooooo------------------------");
        int diff=board.getTotalStones(0)-board.getTotalStones(1);
        if(diff>0)
        {
            System.out.println("Player 1 wins");
        }
        else if(diff<0)
        {
            System.out.println("Player 2 wins");
        }
        else
        {
            System.out.println("Match Draw");
        }

        System.out.println("Player 1: "+board.getTotalStones(0));
        System.out.println("Player 2: "+board.getTotalStones(1));

        System.out.println("Summary:");
        System.out.println("Topic\t\t\t\tPlayer 1\t\t\tPlayer 2");
        System.out.println("Total moves\t\t\tPlayer 1: "+moves[0]+"\t\tPlayer 2: "+moves[1]);
        System.out.println("Extra Moves\t\t\tPlayer 1: "+board.extraMoves[0]+"\t\tPlayer 2: "+board.extraMoves[1]);
        System.out.println("Captured Stones\t\tPlayer 1: "+board.capturedStones[0]+"\t\tPlayer 2: "+board.capturedStones[1]);
//        System.out.println(board.stonesInStorage(0)-board.stonesInStorage(1));


    }

    public static Heuristic getHeuristic(int heuristicNo)
    {
        if(heuristicNo==1)
        {
            return new Heuristic1();
        }
        else if(heuristicNo==2)
        {
            return new Heuristic2();
        }
        else if(heuristicNo==3)
        {
            return new Heuristic3();
        }
        else if(heuristicNo==4)
        {
            return new Heuristic4();
        }

        return null;
    }
}