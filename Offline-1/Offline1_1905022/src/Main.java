import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Scanner input=new Scanner(System.in);
        int n=input.nextInt();
        int cnt=1;
        Grid grid=new Grid(n);

//        System.out.println("grid:");
        int val;

        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                val=input.nextInt();
                grid.grid[i][j]=val;
                cnt++;
                if(val==0)
                    grid.blank_pos=cnt-1;
            }
        }


//        System.out.println("ham dis="+grid.getHamDistance());
//        System.out.println("man dis="+grid.getManDistance());

        //inverse count
//        System.out.println("inverse= "+ grid.getInverseCount());


        //is solvable?

//        System.out.println(grid.isSolvable());

        if(grid.isSolvable())
           {
//               System.out.println("Ham:");
////            grid.solveUsingHam();
//               System.out.println("Man");
            grid.solveUsingMan();
           }

        else
        {
            System.out.println("Unsolvable puzzle");
        }
    }
}