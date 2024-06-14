public class Car
{
    //node=car
    String buying,maint,doors,persons,lugBoot,safety; //attributes
    String value; //label
    
    public Car(String b,String m,String d,String p,String l,String s,String v)
    {
        buying = b;
        maint = m;
        doors = d;
        persons = p;
        lugBoot = l;
        safety = s;
        value=v;
    }

    String getAttributeValue(String attributeName)
    {
        if(attributeName.equals("buying"))
            return buying;
        else if(attributeName.equals("maint"))
            return maint;
        else if(attributeName.equals("doors"))
            return doors;
        else if(attributeName.equals("persons"))
            return persons;
        else if(attributeName.equals("lugBoot"))
            return lugBoot;
        else if(attributeName.equals("safety"))
            return safety;
        else
            return null;
    }
  
}
