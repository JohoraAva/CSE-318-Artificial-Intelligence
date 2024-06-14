import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main
{
    
    public static void main(String[] args) throws IOException {
        
        String fileName="Car.data";
//        BufferedReader br=new BufferedReader(new FileReader(fileName));
        List<Car> exCarList=new ArrayList<Car>();
        List<Car> testCarList=new ArrayList<>();
        String line;//=br.readLine();
        String [] token;//=line.split(" ");
        Random rand=new Random();
        new AllConstants();

        int remainder;//=rand.nextInt()%5;
        final int TOTAL=20;
        double exampleTotalSum=0.0, testingTotalSum=0.0;
        double deviation[]=new double[2];
        double accuracy[][]=new double[2][TOTAL];

        for(int k=0;k<TOTAL;k++)
        {
            remainder=rand.nextInt()%5;
            testCarList.clear();
            exCarList.clear();
            BufferedReader br=new BufferedReader(new FileReader(fileName));
            while((line=br.readLine())!=null)
            {
                int random=rand.nextInt()%5;
                token=line.split(",");
                if(random==remainder)
                    testCarList.add(new Car(token[0],token[1],token[2],token[3],token[4],token[5],token[6]));
                else
                    exCarList.add(new Car(token[0],token[1],token[2],token[3],token[4],token[5],token[6]));

            }
//
            br.close();

            DecisionTree  decisionTree=new DecisionTree(exCarList);
            decisionTree.buildTree();
//        decisionTree.printDecisionTree(decisionTree.root,0);

            System.out.println("Iteration No: "+ (k+1) +" Accuracy for example data set: "+decisionTree.getAccuracy(exCarList) );
            System.out.println("Iteration No: "+ (k+1)+" Accuracy for test data set: "+ decisionTree.getAccuracy(testCarList));
            accuracy[0][k]=decisionTree.getAccuracy(exCarList);
            accuracy[1][k]=decisionTree.getAccuracy(testCarList);
            exampleTotalSum+=decisionTree.getAccuracy(exCarList);
            testingTotalSum+=decisionTree.getAccuracy(testCarList);

        }
        double mean[]=new double[2];
        mean[0]= exampleTotalSum/TOTAL;
        mean[1]=testingTotalSum/TOTAL;
        System.out.println("Mean for example data set: "+mean[0]);
        System.out.println("Mean for test data set: "+ mean[1]);
        //deviation
        for(int i=0;i<TOTAL;i++)
        {
            deviation[0]+= (accuracy[0][i]-mean[0])*(accuracy[0][i]-mean[0]);
            deviation[1]+= (accuracy[1][i]-mean[1])*(accuracy[1][i]-mean[1]);
        }
        deviation[0]=Math.sqrt(deviation[0]/TOTAL);
        deviation[1]=Math.sqrt(deviation[1]/TOTAL);

        System.out.println("Deviation for example data set: "+deviation[0]);
        System.out.println("Deviation for test data set: "+deviation[1]);

    }
}