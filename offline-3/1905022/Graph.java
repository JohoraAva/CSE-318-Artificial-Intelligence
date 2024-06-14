import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class Graph
{
    int v, e; //vertex,edge

    ArrayList<Edge> edge;
    ArrayList<Vertex> vertices;

    Graph(int v,int e)
    {
        this.v=v;
        this.e=e;
        edge=new ArrayList<Edge>(e);
        vertices=new ArrayList<Vertex>(v);
        for(int i=0;i<v;i++)
        {
            vertices.add(new Vertex());
        }
    }

    static String fileName;//="g";

    public static void main(String[] args) throws IOException {

        for(int f=1;f<55;f++)
        {
            fileName="g"+f+".rud";

            BufferedReader br=new BufferedReader(new FileReader(fileName));
            String line=br.readLine();
            String [] token=line.split(" ");
            int p= Integer.parseInt(token[0]);
            int q= Integer.parseInt(token[1]);
            Graph graph=new Graph(p,q);



            while((line=br.readLine())!=null)
            {
                token=line.split(" ");
                int u= Integer.parseInt(token[0])-1;
                int v1= Integer.parseInt(token[1])-1;
                double w= Double.parseDouble(token[2]);
                graph.edge.add(new Edge(u,v1,w));

                graph.vertices.get(u).addEdge(new Edge(u,v1,w));
                graph.vertices.get(v1).addEdge(new Edge(v1,u,w));
            }
//
            br.close();
//

            MaxCut maxCut=new MaxCut(graph);
            double alpha=0.6;

//        System.out.println("File name\tVertex No\tEdge No\tRandomized-1\tSemi-Greedy-.1\tSemi-Greedy-.2\tSemi-Greedy-.3\tSemi-Greedy-.4\tSemi-Greedy-.5\tSemi-Greedy-.6\tSemi-Greedy-.7\tSemi-Greedy-.8\tSemi-Greedy-.9\tGreedy-1");
            System.out.print("G"+f+","+graph.v+","+graph.e+ ","+maxCut.getMaxCutValue( maxCut.semiGreedySearch(0))+",");
            for(double j=0.1;j<=0.9;j+=0.1)
                System.out.print(maxCut.getMaxCutValue(maxCut.semiGreedySearch(j))+",");
            System.out.print( maxCut.getMaxCutValue(maxCut.semiGreedySearch(1.0))+",");
            int totalIteration=50;

            List<Integer> partition=maxCut.GRASP(totalIteration,alpha);
//            System.out.println(maxCut.maxIteration);
            System.out.print(maxCut.maxIteration+","+ maxCut.getMaxCutValue(partition)+","+totalIteration+","+maxCut.getMaxCutValue(partition));

            System.out.println();


        }

    }
}
