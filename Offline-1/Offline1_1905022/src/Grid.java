import java.util.*;

public class Grid
{
    int[][] grid;
    int size,moveNo;
    int blank_pos;
    Grid par;


    public Grid(int n)
    {
        grid= new int[n][];
        size=n;
        moveNo=0;
        par=null;

        for(int i=0;i<n;i++)
        {
            grid[i]=new int[n];
        }

    }

    public int getBlankPos()
    {
        return blank_pos;
    }
    public int getHamDistance()
    {
        int tem=1;
        int ham_dis=0;
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(tem!=grid[i][j] && grid[i][j]!=0)
                {
                    ham_dis++;
                }
                tem++;
            }
        }
        return ham_dis;
    }

    public int getManDistance() {
        int row, col; //present row,column
        int exp_row, exp_col; //goal row, column
        int man_dis=0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(grid[i][j]!=0)
                {
                    exp_row = (grid[i][j] - 1) / 3;
                    exp_col = (grid[i][j] - 1) % 3;

                    row = i;
                    col = j;


                    int dis_row = Math.abs(row - exp_row);
                    int dis_col = Math.abs(col - exp_col);

                    man_dis+=dis_col+dis_row;
                }
            }


        }
        return man_dis;
    }

    public int getInverseCount()
    {
        int inverse=0,idx=0;
        int [] tem=new int[size*size];

        //copy
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(grid[i][j]!=0)
                    tem[idx++]=grid[i][j];
            }
        }


            for(int i=0;i<idx-1;i++)
            {
                for(int j=i+1;j<idx;j++)
                {
                   if(tem[i]>tem[j])
                       inverse++;
                }
            }
        return inverse;
    }

    public boolean isSolvable()
    {
        if(size%2==1)
        {
            if(getInverseCount()%2==0)
                return true;
            return false;
        }

        int blank_row = (blank_pos - 1) / size;

        int inverse = getInverseCount() % 2;
        if (blank_row % 2==1) {
           return !(inverse%2==1);
        } else {
            return (inverse % 2)==1;
        }

    }

    public void solveUsingMan()
    {
        PriorityQueue<Grid> openList=new PriorityQueue<>(new Comparator<Grid>() {
            @Override
            public int compare(Grid o1, Grid o2) {
                return (o1.getManDistance()+o1.moveNo)-(o2.getManDistance()+o2.moveNo);
            }
        });

        HashMap<Grid,Boolean> visited=new HashMap();
        openList.add(this);

        while(!openList.isEmpty())
        {
            Grid cur=openList.poll();
//            cur.print();

            if(cur.getManDistance()==0)
            {
                System.out.println("Minimum number of moves ="+ cur.moveNo);
                printSteps(cur);
                return;
            }

            List<Grid> childList=cur.getChild();
            //print

            for(int i=0;i<childList.size();i++)
            {
                if(!visited.containsKey(childList.get(i).grid))
                {
//                    System.out.println("contains kaj kore???"+ visited.containsKey(childList.get(i).grid));
                    openList.add(childList.get(i));
                    visited.put(childList.get(i),Boolean.TRUE);
//                    System.out.println("contains?");
                }

            }


        }

//        printPath();
    }


    public void solveUsingHam()
    {
        PriorityQueue<Grid> openList=new PriorityQueue<>(new Comparator<Grid>() {
            @Override
            public int compare(Grid o1, Grid o2) {
                return (o1.getHamDistance()+o1.moveNo)-(o2.getHamDistance()+o2.moveNo);
            }
        });

        HashMap<Grid,Boolean> visited=new HashMap();
        openList.add(this);

        while(!openList.isEmpty())
        {
            Grid cur=openList.poll();
//            cur.print();

            if(cur.getHamDistance()==0)
            {
                System.out.println("Total Moves="+ cur.moveNo);
                printSteps(cur);
                return;
            }

            List<Grid> childList=cur.getChild();
            //print

            for(int i=0;i<childList.size();i++)
            {
                if(!visited.containsKey(childList.get(i).grid))
                {
//                    System.out.println("contains kaj kore???"+ visited.containsKey(childList.get(i).grid));
                    openList.add(childList.get(i));
                    visited.put(childList.get(i),Boolean.TRUE);
//                    System.out.println("contains?");
                }

            }


        }

//        printPath();
    }


    private List<Grid> getChild()
    {
        List<Grid> childList=new ArrayList<>();


//        System.out.println("printing blank pos");
        int row=(blank_pos-1)/size;
        int col= (blank_pos-1)%size;

//        System.out.println(row+", "+col);

        if(col>0)
        {
            Grid child=new Grid(size);
            child.copy(this);
            child.moveLeft();
            childList.add(child);
            child.par=this;
           
        }
        if(col<size-1)
        {
            Grid child=new Grid(size);
            child.copy(this);
            child.moveRight();
            childList.add(child);
            child.par=this;
        }
        if(row>0)
        {
            Grid child=new Grid(size);
            child.copy(this);
            child.moveUp();
            childList.add(child);
            child.par=this;
        }
        if(row<size-1)
        {
            Grid child=new Grid(size);
            child.copy(this);
            child.moveDown();

            childList.add(child);
            child.par=this;

        }
        return childList;
    }

    public void copy(Grid temGrid)
    {
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                grid[i][j]= temGrid.grid[i][j];
            }
        }
        blank_pos=temGrid.blank_pos;
        moveNo=temGrid.moveNo;
        par=temGrid.par;
//        System.out.println("in copy="+moveNo);
    }

    public void moveDown()
    {
        int row=(blank_pos-1)/size;
        int col=(blank_pos-1)%size;

        grid[row][col]=grid[row+1][col];


        grid[row+1][col]=0;
//        grid[row+1][col].pos=blank_pos;

        blank_pos=(row+1)*size+ col+1;
        moveNo++;

//        System.out.println("moveDown , moveNo="+ moveNo);
    }

    public void moveUp()
    {
        int row=(blank_pos-1)/size;
        int col=(blank_pos-1)%size;

        grid[row][col]=grid[row-1][col];

        grid[row-1][col]=0;
//        grid[row-1][col].pos=blank_pos;

        blank_pos=(row-1)*size+col+1;
        moveNo++;

//        System.out.println("moveup , moveNo="+ moveNo);
    }

    public void moveLeft()
    {
        int row=(blank_pos-1)/size;
        int col=(blank_pos-1)%size;

        grid[row][col]=grid[row][col-1];

        grid[row][col-1]=0;
//        grid[row][col-1].pos=blank_pos;

        blank_pos=row*size+col;
        moveNo++;

//        System.out.println("moveLeft , moveNo="+ moveNo);
    }

    public void moveRight()
    {
        int row=(blank_pos-1)/size;
        int col=(blank_pos-1)%size;

        grid[row][col]=grid[row][col+1];
//        System.out.println("in move right="+grid[row][col].data);


        grid[row][col+1]=0;

        blank_pos=row*size+col+2;
        moveNo++;

//        System.out.println("moveRight , moveNo="+ moveNo);
    }

    public void print()
    {
        System.out.println("");

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
                System.out.print(grid[i][j]+" ");
            System.out.println();
        }
    }

    public void printSteps(Grid cur)
    {
        if(cur.par!=null)
        {
            printSteps(cur.par);
//            System.out.println("par null??");
        }
        cur.print();

    }

    @Override
    public int hashCode() {
        int r = 0;
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                r+=grid[i][j];
                r*=10;
            }
        }
        return r;
    }
    @Override
    public boolean equals(Object o)
    {
        if(o==this)
            return true;
        if(!(o instanceof Grid))
            return false;

        Grid temp=(Grid) o;

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(grid[i][j]!=temp.grid[i][j])
                    return false;
            }
        }
        return true;


    }



}