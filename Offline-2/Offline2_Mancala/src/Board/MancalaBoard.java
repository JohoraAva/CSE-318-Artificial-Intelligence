package Board;

import Heuristic.*;

public class MancalaBoard
{

    final int inf=Integer.MAX_VALUE;
    final int negInf=Integer.MIN_VALUE;
    final int binNo=6;
    final int stoneperBin=4;
    //bin =6, stones per bin=4

    public int totalStone,curPlayer;
    int[][] stonesInBin;
    public int[] extraMoves, capturedStones;
    public Heuristic[] heuristic;
    public int[] steps;


    public MancalaBoard(int s1,int s2)
    {
        totalStone=binNo*stoneperBin*2;
        stonesInBin=new int[2][binNo+1];

        for(int i=0;i<2;i++)
        {
            for(int j=1;j<=binNo;j++)
                stonesInBin[i][j]=stoneperBin;
        }
        curPlayer=0;
        heuristic=new Heuristic[2];
        steps=new int[2];
        steps[0]=s1;
        steps[1]=s2;
        extraMoves=new int[2];
        capturedStones=new int[2];
    }

    public void setHeuristic(int idx,Heuristic h)
    {
        heuristic[idx]=h;
    }


    public int getTotalStone(int playerNo) //without storage
    {
        int total=0;
        for(int i=1;i<=binNo;i++)
            total+=stonesInBin[playerNo][i];


        return total;

    }

    public int getProbableStoneCaptured(int playerNo)
    {
        int maxStoneCaptured=0;

        for(int i=1;i<=binNo;i++)
        {
            if(stonesInBin[playerNo][i]==0)
            {
                int stonesCaptured=stoneCaptured(playerNo,i);
                if(stonesCaptured>maxStoneCaptured)
                    maxStoneCaptured=stonesCaptured;
            }
        }

        return maxStoneCaptured;
    }
    public int stoneCaptured(int playerNo,int binNo)
    {
        int other=otherPlayer(playerNo);
        int stones=stonesInBin[other][6-binNo+1];
        // stonesInBin[other][binNo]=0;

        return stones;
    }

    public int getProbableExtraMove(int playerNo)
    {
        int count=0;

        for(int i=1;i<=binNo;i++)
        {
            if(stonesInBin[playerNo][i]==i)
                count++;
        }

        return count;
    }

    public int getTotalStones(int playerNo) //with storage
    {
        int total=0;
        for(int i=0;i<=binNo;i++)
            total+=stonesInBin[playerNo][i];

        return total;
    }

    public int otherPlayer(int playerNo)
    {
        return (playerNo+1)%2;
    }
    public int stonesInStorage(int playerNo)
    {
        return stonesInBin[playerNo][0];
    }

    public boolean isGameOver(int playerNo)
    {
        if(getTotalStone(playerNo)==0 || getTotalStone(otherPlayer(playerNo))==0)
            return true;
        return false;
    }


    public void print()
    {
        System.out.println("-----------------");
        System.out.print("| |");
        for(int i=1;i<=binNo;i++)
            System.out.print(stonesInBin[0][i]+"|");
        System.out.println(" |");


        System.out.print("|"+stonesInBin[0][0]+"|");
        System.out.print("-----------");
         System.out.println("|"+stonesInBin[1][0]+"|");


        System.out.print("| |");
        for(int i=binNo;i>0;i--)
            System.out.print(stonesInBin[1][i]+"|");
        System.out.println(" |");
        System.out.println("-----------------");
         System.out.println();
    }



    public MancalaBoard copy(int steps1,int steps2) //successor
    {
        MancalaBoard newBoard=new MancalaBoard(steps1,steps2);
        newBoard.heuristic=heuristic;
        newBoard.curPlayer=curPlayer;
        newBoard.totalStone=totalStone;
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<=binNo;j++)
                newBoard.stonesInBin[i][j]=stonesInBin[i][j];

            newBoard.extraMoves[i]=extraMoves[i];
            newBoard.capturedStones[i]=capturedStones[i];
        }
        newBoard.steps[0]=steps1;
        newBoard.steps[1]=steps2;


        return newBoard;
    }

    public MancalaBoard move(int playerNo,int bin)
    {
        MancalaBoard newBoard=copy(steps[0],steps[1]);

        int stones=stonesInBin[playerNo][bin];
        newBoard.stonesInBin[playerNo][bin]=0;
        int tem=playerNo;
        
        while(stones>0)
        {
            bin--;
            if(bin==0 && playerNo!=tem)
            {
                continue;
            }
            if(bin<0)
            {
                bin=binNo;
                playerNo=otherPlayer(playerNo);
            }
            newBoard.stonesInBin[playerNo][bin]++;
            stones--;

        }
        //extra move
        if(bin==0 && playerNo==tem)
        {
            extraMoves[tem]++;
           return newBoard;

        }
        //capture stones
        if(newBoard.stonesInBin[playerNo][bin]==1 && bin!=0 &&playerNo==tem)
        {

            int other=otherPlayer(playerNo);
            int bin2=binNo-bin+1;
            if(newBoard.stonesInBin[other][bin2]!=0)
            {
//                System.out.println("wrong things happenning here");
                capturedStones[curPlayer]+=newBoard.stonesInBin[other][bin2];
                newBoard.stonesInBin[playerNo][0]+= newBoard.stonesInBin[other][bin2]+1;
                newBoard.stonesInBin[other][bin2]=0;
                newBoard.stonesInBin[playerNo][bin]=0;

            }
        }

//        System.out.println("before cur="+curPlayer);
        newBoard.curPlayer=otherPlayer(curPlayer);
//        System.out.println("after cur="+newBoard.curPlayer);
        return newBoard;

      
    }


    public int getMove(int playerNo)
    {
        if(isGameOver(playerNo))
        {
            return -1;
        }
        int value,ans=0;
        if( curPlayer==0) //max
        {
            value=negInf;
        }
        else
        {
            value=inf;
        }

        for(int i=1;i<=binNo;i++)
        {
            if(stonesInBin[playerNo][i]>0)
            {
//                System.out.println("before cur1="+curPlayer);
                MancalaBoard board=move(playerNo,i);
//                System.out.println("after cur1="+board.curPlayer);
                int val=board.MiniMax(steps[curPlayer]-1,negInf,inf);
                if( curPlayer==0)
                {
                    if(val>value)
                    {
                        value=val;
                        ans=i;
                    }
                }
                else
                {
                    if(val<value)
                    {
                        value=val;
                        ans=i;
                    }
                }
            }
        }
//        System.out.println("ans="+ans+" val= "+value);
        return ans;
    }

    public int MiniMax(int step,int alpha,int beta)
    {
        if(step==0 || isGameOver(curPlayer))
        {
//            System.out.println("h check: "+curPlayer+" get H:"+heuristic[curPlayer].getClass().getName());
            return heuristic[curPlayer].getHeuristicValue(this);
        }
        int value;
        if( curPlayer==0)
        {
            value=negInf;
        }
        else
        {
            value=inf;
        }
        // MancalaBoard temp;
        for(int i=1;i<=binNo;i++)
        {
            if(stonesInBin[curPlayer][i]>0)
            {
                MancalaBoard board=move(curPlayer,i);
                int val=board.MiniMax(step-1,alpha,beta);
                if( curPlayer==0)
                {
                    value=Math.max(value,val);
                    alpha=Math.max(alpha,val);
                }
                else
                {
                    value=Math.min(value,val);
                    beta=Math.min(beta,val);
                }
                if(beta<alpha)
                    break;
            }
        }

        return value;
    }

}
