import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MaxCut
{
    Graph graph;
    int maxIteration;
    MaxCut(Graph g)
    {
        graph = g;
        maxIteration=0;
    }

    int getMaxCutValue(List<Integer> partition)
    {
        int cutVal=0;
        for(Edge e: graph.edge)
        {
            if(partition.get(e.src) != partition.get(e.des))
            {
                cutVal += e.weight;
            }
        }
        return cutVal;
    }

    int getOtherPartition(int partitionNo)
    {
        return (partitionNo+1)%2;
    }
    int getLocalSearchHeuristicValue(int vertexNo,List<Integer> partition)
    {
        int val=0;
        for(int j=0;j<graph.vertices.get(vertexNo).edgeList.size();j++)
        {
            Edge e=graph.vertices.get(vertexNo).edgeList.get(j);

            //if the neighbour is in the partition
            //as vertexNo will be shifted to another partition, same partitioned vertices will be in diff partition
            //so value will be added
            if(partition.get(vertexNo)==partition.get(e.des))
            {
                val+=e.weight;
            }
            //else will be in same partition, so value will be minused
            else
            {
                val-=e.weight;
            }
        }

        return val;
    }

    List<Integer> localSearch(List<Integer> partition)
    {

       while(true)
       {
           int maxVertex=-1;
           int maxCutVal=0;
           for(int i=0;i<graph.v;i++)
           {
               //heuristic for local-search
               int val=getLocalSearchHeuristicValue(i,partition);

               if(val>maxCutVal)
               {
                   maxCutVal=val;
                   maxVertex=i;
               }

           }

           if(maxVertex==-1)
           {
               break;
           }
           else
           {
//               System.out.println("chk max Vertex= "+maxVertex+" val= "+maxCutVal);
               partition.set(maxVertex, getOtherPartition(partition.get(maxVertex)));
               maxIteration++;
           }
       }
       return partition;
    }
    List<Integer> semiGreedySearch(double alpha)
    {
        double maxWeight=Integer.MIN_VALUE;
        double minWeight=Integer.MAX_VALUE;

        for(Edge e: graph.edge)
        {
            if(e.weight>maxWeight)
                maxWeight=e.weight;
            if(e.weight<minWeight)
                minWeight=e.weight;
            
        }
        double threshold= minWeight+alpha*(maxWeight-minWeight);

        //rcl list
        List<Edge> edgeList=new ArrayList<Edge>();

        for(Edge e: graph.edge)
        {
            if(e.weight>=threshold)
                edgeList.add(e);
            
        }

        Random random = new Random();
        //initialize
        List<Integer> curPartition=new ArrayList<Integer>(graph.v);
        for(int i=0;i<graph.v;i++)
        {
            curPartition.add(-1);
        }

        int edgeToMove=random.nextInt(edgeList.size());
        Edge e=edgeList.get(edgeToMove);

        curPartition.set(e.src,0);
        curPartition.set(e.des,1);
        graph.vertices.get(e.src).isInPartition=true;
        graph.vertices.get(e.des).isInPartition=true;


        int remainingVertex=graph.v-2;

        while(remainingVertex>0)
        {
            int maxHeuristic=Integer.MIN_VALUE;
            int minHeuristic=Integer.MAX_VALUE;

            for(int i=0;i<graph.v;i++)
            {
                if(graph.vertices.get(i).isInPartition)
                    continue;
                int heuristic1=getHeuristicValue(i,curPartition,0);
                int heuristic2=getHeuristicValue(i, curPartition, 1);
                
                maxHeuristic=Math.max(maxHeuristic, Math.max(heuristic1, heuristic2));
                minHeuristic=Math.min(minHeuristic, Math.min(heuristic1, heuristic2));

            }
            threshold=minHeuristic+alpha*(maxHeuristic-minHeuristic);


            //form rcl
            List<Integer> vertexList=new ArrayList<Integer>();
            for(int i=0;i<graph.v;i++)
            {
                if(!graph.vertices.get(i).isInPartition)
                {
                    int heuristic1=getHeuristicValue(i,curPartition,0);
                    int heuristic2=getHeuristicValue(i, curPartition, 1);
//                    System.out.println(i + " " + heuristic1 + " " + heuristic2);
                    if(Math.max(heuristic1, heuristic2)>=threshold)
                        vertexList.add(i);
                }
            }

//            System.out.println("chk == "+vertexList.size());
//            if(vertexList.size()<=0)
//                break;
            //move vertex
            int vertexToMove=vertexList.get(random.nextInt(vertexList.size()));
            int heuristic1=getHeuristicValue(vertexToMove,curPartition,0);
            int heuristic2=getHeuristicValue(vertexToMove, curPartition,  1);
            graph.vertices.get(vertexToMove).isInPartition=true;
            remainingVertex--;
           
            if(heuristic1>heuristic2)
            {
                curPartition.set(vertexToMove,1);
            }
            else
            {
                curPartition.set(vertexToMove,0);
            }
        }

        reset();
        return curPartition;

    }

    int getHeuristicValue(int vertexNo,List<Integer> curPartition,int partitionNo)
    {
        int heuristicValue=0;
        for(int i=0;i<graph.vertices.get(vertexNo).edgeList.size();i++)
        {
            Edge e=graph.vertices.get(vertexNo).edgeList.get(i);
            Vertex v=graph.vertices.get(e.des);
            //if the neighbour is in the partition
            if(v.isInPartition && curPartition.get(e.des)==partitionNo)
            {
                heuristicValue+=e.weight;
            }
        }
        return heuristicValue;
    }


    List<Integer> GRASP(int totalIteration,double alpha)
    {

        List<Integer> bestPartition = new ArrayList<>();
        for(int i=0;i<totalIteration;i++)
        {
            List<Integer> curPartition = semiGreedySearch(alpha);
            maxIteration=0;
            curPartition = localSearch(curPartition);
//            System.out.println("chk after ="+getMaxCutValue(curPartition));
            if(i==0 || ( i>0 && getMaxCutValue(curPartition)>getMaxCutValue(bestPartition)))
            {
                bestPartition=curPartition;
            }

            reset();
        }
        return bestPartition;
    }

    void printPartition(List<Integer> partition)
    {
        System.out.println("Printing partition 1: ");
        for(int i=0;i<partition.size();i++)
        {
            if(partition.get(i)==0)
                System.out.print(i+1+" ");
            
        }
        System.out.println();
        System.out.println("Printing partition 2: ");
        for(int i=0;i<partition.size();i++)
        {
            if(partition.get(i)==1)
                System.out.print(i+1+" ");
            
        }
        System.out.println();
    }

    void reset()
    {
        for(int i=0;i<graph.v;i++)
        {
            graph.vertices.get(i).isInPartition=false;
        }
    }
}
