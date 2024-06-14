import java.util.ArrayList;
import java.util.List;

public class Node
{


    Node par; //parent
    boolean isLeaf; //isLeaf

    List<Car> exampleSet;
    List<String> curAttributes;
    String attribute;//attribute name
    String result;//return value

    Node child[];

    void setAttributeList(List<String> attributes,String attribute)
    {
        this.curAttributes=new ArrayList<String>();
        for(int i=0;i<attributes.size();i++)
        {
            if(!attributes.get(i).equals(attribute))
                this.curAttributes.add(attributes.get(i));
        }
    }

    String getPluralValue()
    {
        int valuesCounts[]=new int[4];
        int max_count=Integer.MIN_VALUE;
        int max_idx=-1;
//        max_count=Math.max(valuesCounts[0],Math.max(valuesCounts[1],Math.max(valuesCounts[2],valuesCounts[3])));
        for(Car c:exampleSet)
        {
            if(c.value.equals(AllConstants.values[0]))
                valuesCounts[0]++;
            else if(c.value.equals(AllConstants.values[1]))
                valuesCounts[1]++;
            else if(c.value.equals(AllConstants.values[2]))
                valuesCounts[2]++;
            else if(c.value.equals(AllConstants.values[3]))
                valuesCounts[3]++;
        }
        for(int i=0;i<4;i++)
        {
            if(valuesCounts[i]>max_count)
            {
                max_count=valuesCounts[i];
                max_idx=i;
            }
        }

        return AllConstants.values[max_idx];
    }
    boolean allExamplesSameClass()
    {
        String classValue=exampleSet.get(0).value;
        for(Car c:exampleSet)
        {
            if(!c.value.equals(classValue))
                return false;
        }
        return true;
    }

    String selectAttribute()
    {
        double max_gain=-Double.MAX_VALUE;
        //System.out.println("start="+max_gain);
        int max_gain_idx=-1;
//        int i=0;
        for(int i=0;i<curAttributes.size();i++)
        {
            double gain=calculateGain(curAttributes.get(i));
            //System.out.println("check gain "+gain +"max gain= "+max_gain);
            if(gain>max_gain)
            {
                //System.out.println("inside if");
                max_gain=gain;
                max_gain_idx=i;
            }
        }
        return curAttributes.get(max_gain_idx);
    }
    double calculateGain(String attributeName)
    {
        double gain=calculateEntropy(exampleSet);
        int idx=AllConstants.getAttributeIdx(attributeName);
        //System.out.println("init gain="+gain);
        for(String value:AllConstants.attValues[idx])
        {
            List<Car> exampleSet1=formExampleSet(AllConstants.attributes[idx],value);
            double temTerm=((double)exampleSet1.size()/(double)exampleSet.size())*calculateEntropy(exampleSet1);
            //System.out.println("tem term = "+temTerm);
            gain-=temTerm;
        }
        return gain;
    }

    List<Car> formExampleSet(String attributeName,String attributeValue)
    {
        List<Car> exampleSet1=new ArrayList<Car>();
        for(Car c:exampleSet)
        {
            if(c.getAttributeValue(attributeName).equals(attributeValue))
                exampleSet1.add(c);
        }
        return exampleSet1;
    }

    double calculateEntropy(List<Car> exampleSet)
    {
        int valuesCounts[]=new int[4];
        double entropy=0;
        for(Car c:exampleSet)
        {
            if(c.value.equals(AllConstants.values[0]))
                valuesCounts[0]++;
            else if(c.value.equals(AllConstants.values[1]))
                valuesCounts[1]++;
            else if(c.value.equals(AllConstants.values[2]))
                valuesCounts[2]++;
            else if(c.value.equals(AllConstants.values[3]))
                valuesCounts[3]++;
        }
        for(int i=0;i<4;i++)
        {
            if(valuesCounts[i]!=0)
            {
                double p=((double)valuesCounts[i]/(double)exampleSet.size());
                entropy-=p*Math.log(p)/Math.log(2);
            }
        }
        //System.out.println("check entropy="+entropy);
        return entropy;
    }

}
