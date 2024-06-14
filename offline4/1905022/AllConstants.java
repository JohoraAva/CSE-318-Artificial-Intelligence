public class AllConstants
{
    static final String attributes[]={"buying","maint","doors","persons","lugBoot","safety"};
    static final String attValues[][]=new String[6][];
    static final String values[]={"unacc","acc","good","vgood"};

    AllConstants()
    {
        setAttributeValues();
    }
    static void setAttributeValues()
    {
        for(int i=0;i<6;i++)
        {
            if(i==0 || i==1)
            {
                attValues[i]=new String[4];
                attValues[i][0]="vhigh";
                attValues[i][1]="high";
                attValues[i][2]="med";
                attValues[i][3]="low";
            }
            else if(i==2)
            {
                attValues[i]=new String[5];
                attValues[i][0]="2";
                attValues[i][1]="3";
                attValues[i][2]="4";
                attValues[i][3]="5more";
            }
            else if(i==3)
            {
                attValues[i]=new String[3];
                attValues[i][0]="2";
                attValues[i][1]="4";
                attValues[i][2]="more";
            }
            else if(i==4 || i==5)
            {
                attValues[i]=new String[3];
                if(i==4)
                    attValues[i][0]="small";
                else
                    attValues[i][0]="low";
                attValues[i][1]="med";
                if(i==4)
                    attValues[i][2]="big";
                else
                    attValues[i][2]="high";
            }
            else if(i==5)
            {
                attValues[i]=new String[3];
                attValues[i][0]="low";
                attValues[i][1]="med";
                attValues[i][2]="high";
            }
        }
    }

    static int getAttributeIdx(String attributeName)
    {
        for(int i=0;i<attributes.length;i++)
        {
            if(attributes[i].equals(attributeName))
                return i;
        }
        return -1;
    }

    static int getValIdx(Car car,Node node)
    {
        int idx=AllConstants.getAttributeIdx(node.attribute);
//        System.out.println("first= "+node.attribute+" index= "+idx+"value="+car.getAttributeValue(node.attribute));
        for(int i=0;i<AllConstants.attValues[idx].length;i++)
        {
            if(car.getAttributeValue(node.attribute).equals(AllConstants.attValues[idx][i]))
            {
                return i;
            }

        }
        return -1;
    }




}
