/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;


public class Case
{
    RunTime currentRunTime = new RunTime();
    public void setCurrentRunTime(RunTime current)
    {
        currentRunTime = current;
    }
    public Map<String,Conditions> conditions = new HashMap<String,Conditions>();
    void ParseCase(String statment,String variable) throws FileNotFoundException
    {
        String casekey = "";
        String value = "";
        boolean isCase = false;
        for(int i = 0 ; i < statment.length() ; i++)
        {
            if(statment.charAt(i) == ' ')
                continue;
            if(!casekey.equals("case")&& isCase == false)
                casekey+= statment.charAt(i);
            else if(casekey.equals("case"))
                isCase = true;
            if(statment.charAt(i) == ':')
                break;
            if(isCase == true && statment.charAt(i) != ' ')
                value += statment.charAt(i);
        }
        Conditions temp = new Conditions();
        temp.setCurrentRunTime(currentRunTime);
        conditions.put(statment,temp);
        conditions.get(statment).ifCondition("if(" + variable + " == " + value + ')');
    }
}
