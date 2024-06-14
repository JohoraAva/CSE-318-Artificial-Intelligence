import java.util.ArrayList;

public class Vertex
{
    ArrayList<Edge> edgeList;
    boolean isInPartition;

    public Vertex()
    {
        edgeList = new ArrayList<Edge>();
        isInPartition = false;
    }

    void addEdge(Edge e)
    {
        edgeList.add(e);
    }


    
}
