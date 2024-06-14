import java.util.ArrayList;
import java.util.List;

public class DecisionTree
{
    Node root;
    
    DecisionTree(List<Car> cars)
    {
        root=new Node();
        root.exampleSet=cars;
        root.isLeaf=false;
        root.child=null;

        root.curAttributes=new ArrayList<String>();
        for(int i=0;i<AllConstants.attributes.length;i++)
            root.curAttributes.add(AllConstants.attributes[i]);
    }

    void buildTree()
    {
        //form the tree
        DecisionTreeLearning(root);
        
    }

    void DecisionTreeLearning(Node node)
    {
        if(node.exampleSet.isEmpty())
        {
            //plutrality value of parent
            node.isLeaf=true;
            node.result=node.par.getPluralValue();
            return; 
        }
        if(node.allExamplesSameClass())
        {
            node.isLeaf=true;
            node.result=node.exampleSet.get(0).value;
            return;
        }
        if(node.curAttributes.isEmpty())
        {
            node.isLeaf=true;
            node.result=node.getPluralValue();
            return;
        }
        node.attribute=node.selectAttribute();
        node.isLeaf=false;
        node.child=new Node[AllConstants.attValues[AllConstants.getAttributeIdx(node.attribute)].length];
        for(int i=0;i<AllConstants.attValues[AllConstants.getAttributeIdx(node.attribute)].length;i++)
        {
             Node child=new Node();
             child.par=node;
             child.exampleSet=node.formExampleSet(node.attribute,AllConstants.attValues[AllConstants.getAttributeIdx(node.attribute)][i]);
             child.setAttributeList(node.curAttributes,node.attribute);
             node.child[i]=child;
             DecisionTreeLearning(child);
        }

    }

    void printDecisionTree(Node node,int tabCount)
    {

//        for(int i=0;i<tabCount;i++)
//            System.out.print("\t");
        if(node.isLeaf)
        {
            System.out.println("\t\tClass: "+node.result);
            return;
        }
        System.out.println("tabcount= "+ tabCount+"Attribute: "+node.attribute);
        for(int i=0;i<node.child.length;i++)
        {
//             for(int j=0;j<tabCount;j++)
//                 System.out.print("\t");
            // System.out.println("check idx="+node.attribute_idx +"child size="+node.child.length);
            System.out.println("\tValue: "+AllConstants.attValues[AllConstants.getAttributeIdx(node.attribute)][i]);
            printDecisionTree(node.child[i],tabCount+1);
        }
    }

    boolean checkResultMatch(Car car,Node node)
    {
        if(node.isLeaf)
            return node.result.equals(car.value);
        return checkResultMatch(car,node.child[AllConstants.getValIdx(car,node)]);
    }

    double getAccuracy(List<Car> carList)
    {
        int count=0;
        for(Car c:carList)
        {
            if(checkResultMatch(c,root))
                count++;
        }
       double accuracy=((double)count/(double)carList.size())*100;
       return accuracy;
    }

  

}
