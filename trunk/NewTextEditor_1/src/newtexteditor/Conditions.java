/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

import java.io.FileNotFoundException;
import java.util.Vector;

public class Conditions
{
//    static private Functions function = new Functions();
//    static public void setCurrentFunction(Functions currentFunction)
//    {
//        function = currentFunction;
//    }
    public boolean isConditionRunning = false;
    public Vector<String> variablesDeclaredWithinCondition = new Vector<String>();
    public void deleteVariablesDeclaredWithinACondition()
    {
        for(int i = 0 ; i < variablesDeclaredWithinCondition.size() ; i++)
        {
            if(currentRunTime.variables.intMap.containsKey(variablesDeclaredWithinCondition.get(i)))
                currentRunTime.variables.intMap.remove(variablesDeclaredWithinCondition.get(i));
            if(currentRunTime.variables.floatMap.containsKey(variablesDeclaredWithinCondition.get(i)))
                currentRunTime.variables.floatMap.remove(variablesDeclaredWithinCondition.get(i));
            if(currentRunTime.variables.longMap.containsKey(variablesDeclaredWithinCondition.get(i)))
                currentRunTime.variables.longMap.remove(variablesDeclaredWithinCondition.get(i));
            if(currentRunTime.variables.doubleMap.containsKey(variablesDeclaredWithinCondition.get(i)))
                currentRunTime.variables.doubleMap.remove(variablesDeclaredWithinCondition.get(i));
            if(currentRunTime.variables.stringMap.containsKey(variablesDeclaredWithinCondition.get(i)))
                currentRunTime.variables.stringMap.remove(variablesDeclaredWithinCondition.get(i));
            if(currentRunTime.variables.charMap.containsKey(variablesDeclaredWithinCondition.get(i)))
                currentRunTime.variables.charMap.remove(variablesDeclaredWithinCondition.get(i));
        }
    }
    private ParsingConditions conditions = new ParsingConditions();
    RunTime currentRunTime = new RunTime();
    public void setCurrentRunTime(RunTime current)
    {
        currentRunTime = current;
    }
    private String conditionResult;
    private void setConditionResult(String conditionsResult)
    {
        conditionResult = conditionsResult;
    }
    private String getCondition()
    {
        return conditionResult;
    }
    private Vector<String> lastIfInputVector = new Vector<String>();
    private void setLastIfInputVector(Vector<String> lastInput)
    {
        lastIfInputVector = lastInput;
    }
    private Vector<String> getlastIfInputVector()
    {
        Vector<String> temp= new Vector<String>(lastIfInputVector);
        lastIfInputVector.clear();
        return temp;
    }
    private int ifCounter = 0;
    private boolean isNestedIf()
    {
        if(ifCounter > 1)
            return true;
        return false;
    }
    private void ifCounterIncreament()
    {
        ifCounter++;
    }
    private void ifCounterDecreament()
    {
        ifCounter--;
    }
    static private boolean isCalledByLoop()
    {
        return RunTime.calledByLoop;
    }
    public void ifCondition(String statment) throws FileNotFoundException
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
        String ifkey = "";
        String unparsedStatment = "";
        currentRunTime.currentIf = statment;
        for(int i = 0 ; i < unSpacedStatment.length() ; i++)
        {
            if(ifkey.equals("if"))
            {
                unparsedStatment = unSpacedStatment.substring(i);
                break;
            }
            ifkey += unSpacedStatment.charAt(i);
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
        setConditionResult("(!" + unparsedStatment + ')');
        String result = conditions.Parse(unparsedStatment);
        if(isCalledByLoop())
            ifCounterIncreament();
        ifCounterIncreament();
        Vector<String> input = new Vector<String>();
        if(isNestedIf())
            input = getlastIfInputVector();
        boolean isFirstTime = false;
        boolean isSpaced = false;
        if(input.size() == 0)
        {
            isFirstTime = true;
            String temp = currentRunTime.codeFile.nextLine();
            RunTime.NumberLine++;
            unSpacedStatment = "";
            for(int i = 0 ; i < temp.length() ; i++)
            {
                if(temp.charAt(i) == ' ' && isSpaced == false)
                    continue;
                else
                {
                    isSpaced = true;
                    unSpacedStatment += temp.charAt(i);
                }
            }
            isSpaced = false;
            if(unSpacedStatment.equals("{"))
            {
                while(currentRunTime.codeFile.hasNext())
                {
                    temp = currentRunTime.codeFile.nextLine();
                    RunTime.NumberLine++;
                    unSpacedStatment = "";
                    for(int i = 0 ; i < temp.length() ; i++)
                    {
                        if(temp.charAt(i) == ' ' && isSpaced == false)
                            continue;
                        else
                        {
                            isSpaced = true;
                            unSpacedStatment += temp.charAt(i);
                        }
                    }
                    if(unSpacedStatment.charAt(0) == '}')
                        break;
                    input.add(unSpacedStatment);
                    isSpaced = false;
                    if(result.equals("true"))
                        currentRunTime.FunctionCaller(unSpacedStatment);
                }
                //return;
            }
            else if(!temp.equals(""))
            {
                if(result.equals("true"))
                    currentRunTime.FunctionCaller(unSpacedStatment);
                input.add(unSpacedStatment);
            }
            if(isCalledByLoop() && isFirstTime == false)
            {
                for(int i = 1 ; i < input.size(); i++)
                {
                    if(result.equals("true"))
                        currentRunTime.FunctionCaller(input.get(i));
                }
            }
        }
        else
        {
            for(int i = 0 ; i < input.size(); i++)
            {
                if(result.equals("true"))
                    currentRunTime.FunctionCaller(input.get(i));
            }
        }
        isFirstTime = false;
        setLastIfInputVector(input);
        deleteVariablesDeclaredWithinACondition();
        ifCounterDecreament();
    }
    private Vector<String> lastElseInputVector = new Vector<String>();
    private void setLastElseInputVector(Vector<String> lastInput)
    {
        lastElseInputVector = lastInput;
    }
    private Vector<String> getlastElseInputVector()
    {
        return lastElseInputVector;
    }
    private int ElseCounter = 0;
    private boolean isNestedElse()
    {
        if(ElseCounter > 1)
            return true;
        return false;
    }
    private void ElseCounterIncreament()
    {
        ElseCounter++;
    }
    private void ElseCounterDecreament()
    {
        ElseCounter--;
    }
    public void elseCondition() throws FileNotFoundException
    {
        String result = conditions.Parse(getCondition());
        setConditionResult("");
        if(isCalledByLoop())
            ElseCounterIncreament();
        ElseCounterIncreament();
        Vector<String> input = new Vector<String>();
        if(isNestedElse())
            input = getlastElseInputVector();
        boolean isSpaced = false;
        boolean isFirstTime = false;
        if(input.size() == 0)
        {
            isFirstTime = true;
            String temp = currentRunTime.codeFile.nextLine();
            RunTime.NumberLine++;
            String unSpacedStatment = "";
            for(int i = 0 ; i < temp.length() ; i++)
            {
                if(temp.charAt(i) == ' ' && isSpaced == false)
                    continue;
                else
                {
                    isSpaced = true;
                    unSpacedStatment += temp.charAt(i);
                }
            }
            isSpaced = false;
            if(unSpacedStatment.equals("{"))
            {
                while(currentRunTime.codeFile.hasNext())
                {
                    temp = currentRunTime.codeFile.nextLine();
                    RunTime.NumberLine++;
                    unSpacedStatment = "";
                    for(int i = 0 ; i < temp.length() ; i++)
                    {
                        if(temp.charAt(i) == ' ' && isSpaced == false)
                            continue;
                        else
                        {
                            isSpaced = true;
                            unSpacedStatment += temp.charAt(i);
                        }
                    }
                    if(unSpacedStatment.equals("}"))
                        break;
                    isSpaced = false;
                    if(result.equals("true"))
                        currentRunTime.FunctionCaller(unSpacedStatment);
                    input.add(unSpacedStatment);
                }
            }
            else if(!temp.equals(""))
            {
                if(result.equals("true"))
                    currentRunTime.FunctionCaller(unSpacedStatment);
                input.add(unSpacedStatment);
            }
            if(isFirstTime == false)
            {
                for(int i = 1 ; i < input.size(); i++)
                {
                    if(result.equals("true"))
                        currentRunTime.FunctionCaller(input.get(i));
                }
            }
        }
        else
        {
            for(int i = 0 ; i < input.size(); i++)
            {
                if(result.equals("true"))
                    currentRunTime.FunctionCaller(input.get(i));
            }
        }
        isFirstTime = false;
        setLastElseInputVector(input);
        ElseCounterDecreament();
        deleteVariablesDeclaredWithinACondition();
    }
    private Vector<String> lastElseIfInputVector = new Vector<String>();
    private void setLastElseIfInputVector(Vector<String> lastInput)
    {
        lastElseIfInputVector = lastInput;
    }
    private Vector<String> getlastElseIfInputVector()
    {
        return lastElseIfInputVector;
    }
    private int ElseIfCounter = 0;
    private boolean isNestedElseIf()
    {
        if(ElseIfCounter > 1)
            return true;
        return false;
    }
    private void ElseIfCounterIncreament()
    {
        ElseIfCounter++;
    }
    private void ElseIfCounterDecreament()
    {
        ElseIfCounter--;
    }
    @SuppressWarnings("empty-statement")
    public void elseIfCondition(String statment) throws FileNotFoundException
    {
        String statmentWithoutSpace = "";
        for(int i = 0 ; i < statment.length() ; i++)
        {
            if(statment.charAt(i) == ' ')
                continue;
            statmentWithoutSpace += statment.charAt(i);
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
        String result = conditions.Parse("(" + getCondition() + "&&" + statment + ")");
        setConditionResult("(" + getCondition() + "&&(!" + statment + "))");
        if(isCalledByLoop())
            ElseIfCounterIncreament();
        ElseIfCounterIncreament();
        Vector<String> input = new Vector<String>();
        if(isNestedElseIf())
            input = getlastElseIfInputVector();
        boolean isSpaced = false;
        boolean isFirstTime = false;
        if(input.size() == 0)
        {
            isFirstTime = true;
            String temp = currentRunTime.codeFile.nextLine();
            RunTime.NumberLine++;
            String unSpacedStatment = "";
            for(int i = 0 ; i < temp.length() ; i++)
            {
                if(temp.charAt(i) == ' ' && isSpaced == false)
                    continue;
                else
                {
                    isSpaced = true;
                    unSpacedStatment += temp.charAt(i);
                }
            }
            isSpaced = false;
            if(unSpacedStatment.equals("{"))
            {
                while(currentRunTime.codeFile.hasNext())
                {
                    temp = currentRunTime.codeFile.nextLine();
                    RunTime.NumberLine++;
                    unSpacedStatment = "";
                    for(int i = 0 ; i < temp.length() ; i++)
                    {
                        if(temp.charAt(i) == ' ' && isSpaced == false)
                            continue;
                        else
                        {
                            isSpaced = true;
                            unSpacedStatment += temp.charAt(i);
                        }
                    }
                    if(unSpacedStatment.equals("}"))
                        break;
                    isSpaced = false;
                    if(result.equals("true"))
                        currentRunTime.FunctionCaller(unSpacedStatment);
                    input.add(unSpacedStatment);
                }
            }
            else if(!temp.equals(""))
            {
                if(result.equals("true"))
                    currentRunTime.FunctionCaller(unSpacedStatment);
                input.add(unSpacedStatment);
            }
            if(isFirstTime == false)
            {
                for(int i = 1 ; i < input.size(); i++)
                {
                    if(result.equals("true"))
                        currentRunTime.FunctionCaller(input.get(i));
                }
            }
        }
        else
        {
            for(int i = 0 ; i < input.size(); i++)
            {
                if(result.equals("true"))
                    currentRunTime.FunctionCaller(input.get(i));
            }
        }
        isFirstTime = false;
        setLastElseIfInputVector(input);
        ElseIfCounterDecreament();
        deleteVariablesDeclaredWithinACondition();
    }
    void elseOrElseIfCondition(String statment) throws FileNotFoundException
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
        String elseifkey = "";
        String unparsedStatment = "";
        for(int i = 0 ; i < unSpacedStatment.length() ; i++)
        {
            elseifkey += unSpacedStatment.charAt(i);
            if(elseifkey.equals("else if"))
            {
                unparsedStatment = unSpacedStatment.substring(i+1);
                elseIfCondition(unparsedStatment);
                break;
            }
            if(elseifkey.equals("else") && i == unSpacedStatment.length()-1)
            {
                elseCondition();
                break;
            }
        }
    }
}
