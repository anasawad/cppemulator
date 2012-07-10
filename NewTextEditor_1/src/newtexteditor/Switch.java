/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

import java.io.FileNotFoundException;
import java.util.Vector;

public class Switch
{
    RunTime currentRunTime = new RunTime();
    public void setCurrentRunTime(RunTime current)
    {
        currentRunTime = current;
    }
    private Case theCase = new Case();
    private Vector<String> lastCaseVector = new Vector<String>();
    private void setLastCaseVector(Vector<String> lastCase)
    {
        lastCaseVector = lastCase;
    }
    private Vector<String> getlastCaseVector()
    {
        return lastCaseVector;
    }
    private int switchCounter = 0;
    private boolean isNestedSwitch()
    {
        if(switchCounter > 1)
            return true;
        return false;
    }
    private void switchCounterIncreament()
    {
        switchCounter++;
    }
    private void switchCounterDecreament()
    {
        switchCounter--;
    }
    static private boolean isCalledByLoop()
    {
        return RunTime.calledByLoop;
    }
    void switchFunction(String statment) throws FileNotFoundException
    {
        boolean isNotSpaced = false;
        String unSpacedStatment = "";
        for(int i = 0 ; i < statment.length() ; i++)
        {
            if(statment.charAt(i) == ' ' && isNotSpaced == false)
                continue;
            else
            {
                isNotSpaced = true;
                unSpacedStatment += statment.charAt(i);
            }
        }
        String switchkey = "";
        String unparsedStatment = "";
        for(int i = 0 ; i < unSpacedStatment.length() ; i++)
        {
            if(switchkey.equals("switch"))
            {
                unparsedStatment = unSpacedStatment.substring(i);
                break;
            }
            switchkey += unSpacedStatment.charAt(i);
        }
        String statmentWithoutSpace = "";
        for(int i = 0 ; i < unparsedStatment.length() ; i++)
        {
            if(unparsedStatment.charAt(i) == ' ')
                continue;
            statmentWithoutSpace += unparsedStatment.charAt(i);
        }
        if(statmentWithoutSpace.charAt(0) != '(')
        {
            RunTime.ErrorList.add("missing bracket \'(\'");
            RunTime.ErrorNumberLine.add(RunTime.NumberLine);
            return ;
        }
        if(statmentWithoutSpace.charAt(statmentWithoutSpace.length()-1) != ')')
        {
            RunTime.ErrorList.add("missing bracket \')\'");
            RunTime.ErrorNumberLine.add(RunTime.NumberLine);
            return ;
        }
        String variable = "";
        int i = 0;
        while(unparsedStatment.charAt(i) != ')')
        {
            i++;
            if(unparsedStatment.charAt(i) == ')')
                break;
            if(unparsedStatment.charAt(i) == ' ')
                continue;
            variable += unparsedStatment.charAt(i);
            
        }
        switchCounterIncreament();
        Vector<String> cases = new Vector<String>();
        if(isCalledByLoop())
            switchCounterIncreament();
        if(isNestedSwitch())
            cases = getlastCaseVector();
        String tempCase = "";
        if(currentRunTime.variables.CheckIfExist(variable) == -1 || currentRunTime.variables.CheckIfExist(variable) >= 0)
        {
            theCase.setCurrentRunTime(currentRunTime);
            if(cases.size() == 0)
            {
                tempCase = currentRunTime.codeFile.nextLine();
                RunTime.NumberLine++;
                tempCase = currentRunTime.codeFile.nextLine();
                RunTime.NumberLine++;
                isNotSpaced = false;
                unSpacedStatment = "";
                for(int j = 0 ; j < tempCase.length() ; j++)
                {
                    if(tempCase.charAt(j) == ' ' && isNotSpaced == false)
                        continue;
                    else
                    {
                        isNotSpaced = true;
                        unSpacedStatment += tempCase.charAt(j);
                    }
                }
                while(!unSpacedStatment.equals("}"))
                {
                    theCase.ParseCase(unSpacedStatment,variable);
                    cases.add(tempCase);
                    tempCase = currentRunTime.codeFile.nextLine();
                    RunTime.NumberLine++;
                    isNotSpaced = false;
                    unSpacedStatment = "";
                    for(int j = 0 ; j < tempCase.length() ; j++)
                    {
                        if(tempCase.charAt(j) == ' ' && isNotSpaced == false)
                            continue;
                        else
                        {
                            isNotSpaced = true;
                            unSpacedStatment += tempCase.charAt(j);
                        }
                    }
                }
            }
            else
                for(int j = 0 ; j < cases.size() ; j++)
                    theCase.ParseCase(cases.get(j),variable);
        }
        switchCounterDecreament();
        setLastCaseVector(cases);
    }
}
