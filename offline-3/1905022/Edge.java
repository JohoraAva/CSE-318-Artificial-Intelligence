public class Edge implements Comparable<Edge>
{
    int src,des;
    double weight;
    Edge(int u,int v,double w)
    {
        src=u;
        des=v;
        weight=w;
    }

    @Override
    public int compareTo(Edge e) {
        return (int)(this.weight-e.weight);
    }

    public void print()
    {
        System.out.println("Source: "+src+" Dest: "+ des+ "weight:"+weight);
    }
}

