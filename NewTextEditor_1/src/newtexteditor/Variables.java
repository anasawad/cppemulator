/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class Variables
{
    public void ClearRAM()
    {
        intMap.clear();
        floatMap.clear();
        longMap.clear();
        doubleMap.clear();
        stringMap.clear();
        charMap.clear();
    }
    public Map<String,Integer> intMap = new HashMap<String, Integer>();
    public Map<String,Float> floatMap =  new HashMap<String, Float>();
    public Map<String,Long> longMap = new HashMap<String, Long>();
    public Map<String,Double> doubleMap = new HashMap<String, Double>();
    public Map<String,String> stringMap = new HashMap<String, String>();
    public Map<String,Character> charMap = new HashMap<String, Character>();
    public Map<String,String> boolMap = new HashMap<String, String>();
    private String currentVariableName;
    public boolean isLoopRunning = false;
    public boolean isConditionRunning = false;
    public Loops currentLoop;
    public Conditions currentCondition;
    private ParsingEquation equation = new ParsingEquation();
    private ParsingStrings string = new ParsingStrings();
    //if -1 then it's in normal map
    //if -2 then it's not in any map
    //if i then it's in function map
    public int CheckIfExist(String variableName)
    {
        if(intMap.containsKey(variableName) || floatMap.containsKey(variableName) || doubleMap.containsKey(variableName)
                || longMap.containsKey(variableName) || stringMap.containsKey(variableName) || charMap.containsKey(variableName)
                || boolMap.containsKey(variableName))
            return -1;
        return -2;
    }
    public String GetType(String variableName)
    {
        if(intMap.containsKey(variableName))
            return "int";
        else if(floatMap.containsKey(variableName))
            return "float";
        else if(doubleMap.containsKey(variableName))
            return "double";
        else if(longMap.containsKey(variableName))
            return "long";
        else if(charMap.containsKey(variableName))
            return "char";
        else if(stringMap.containsKey(variableName))
            return "string";
        else if(boolMap.containsKey(variableName))
            return "bool";
        else
            return "error";
    }
    private void setVariableName(String name)
    {
        currentVariableName = name;
    }
    public String getVariableName()
    {
        String temp = currentVariableName;
        currentVariableName = "";
        return temp;
    }
    private void createArray(String datatype,String arrayName,int arraySize)
    {
        if(datatype.equals("int"))
        {
            for(int i = 0 ; i < arraySize ; i++)
            {
                String name = arrayName;
                name += i;
                intMap.put(name, 0);
            }
        }
        else if(datatype.equals("float"))
        {
            for(int i = 0 ; i < arraySize ; i++)
            {
                String name = arrayName;
                name += i;
                floatMap.put(name,0f);
            }
        }
        else if(datatype.equals("double"))
        {
            for(int i = 0 ; i < arraySize ; i++)
            {
                String name = arrayName;
                name += i;
                doubleMap.put(name, 0.0);
            }
        }
        else if(datatype.equals("long"))
        {
            for(int i = 0 ; i < arraySize ; i++)
            {
                String name = arrayName;
                name += i;
                longMap.put(name, 0l);
            }
        }
        else if(datatype.equals("char"))
        {
            for(int i = 0 ; i < arraySize ; i++)
            {
                String name = arrayName;
                name += i;
                charMap.put(name, '\u0000');
            }
        }
        else if(datatype.equals("string"))
        {
            for(int i = 0 ; i < arraySize ; i++)
            {
                String name = arrayName;
                name += i;
                stringMap.put(name, "");
            }
        }
        else if(datatype.equals("bool"))
        {
            for(int i = 0 ; i < arraySize ; i++)
            {
                String name = arrayName;
                name += i;
                boolMap.put(name, "false");
            }
        }
    }
    public String VariableAndExpressionCompiler(String input)
    {
        String statmentWithoutSpace = "";
        for(int i = 0 ; i < input.length() ; i++)
        {
            if(input.charAt(i) == ' ')
                continue;
            statmentWithoutSpace += input.charAt(i);
        }
        if(statmentWithoutSpace.charAt(statmentWithoutSpace.length()-1) != ';')
        {
            RunTime.ErrorList.add("missing semicolon \';\'");
            RunTime.ErrorNumberLine.add(RunTime.NumberLine);
            return "";
        }
        boolean isNotSpaced = false;
        String unSpacedStatment = "";
        for(int i = 0 ; i < input.length() ; i++)
        {
            if(input.charAt(i) == ' ' && isNotSpaced == false)
                continue;
            else
            {
                isNotSpaced = true;
                unSpacedStatment += input.charAt(i);
            }
        }
        input = unSpacedStatment;
        int indexOfEquation = 0;
        String variable = "";
        String datatype = "";
        int ArraySize = 0;
        boolean isArray = false;
        boolean dataTypeEnd = false;
        boolean varEnd = false;
        for(int i = 0 ; i < input.length() ; i++)
        {
            if(input.charAt(i)!= ' ' && dataTypeEnd == false)
            {
                datatype += input.charAt(i);
                continue;
            }
            else if(input.charAt(i) == ' ')
                dataTypeEnd = true;
            if((input.charAt(i) != '=' && input.charAt(i) != ';') && varEnd == false)
            {
                if(input.charAt(i) == ' ')
                    continue;
                else if(input.charAt(i) != '[')
                    variable += input.charAt(i);
            }
            else if(input.charAt(i) == '=' || input.charAt(i) == ';' || input.charAt(i) == '[')
                varEnd = true;
            if(input.charAt(i) == '[')
            {
                String temp = input.substring(i);
                while(input.charAt(i) != ']')
                    i++;
                ArraySize = equation.CalculateArrayIndex(temp);
                isArray = true;
                i++;
            }
            if(varEnd == true && dataTypeEnd == true)
            {
                indexOfEquation = i;
                break;
            }
        }
        if(CheckIfExist(variable) == -1)
        {
            RunTime.ErrorList.add("Identifier " + variable + " is already defined");
            RunTime.ErrorNumberLine.add(RunTime.NumberLine);
        }
        if(!isArray)
        {
            setVariableName(variable);
            String newInput = input.substring(indexOfEquation);
            String outPut = "";
            if(datatype.equals("int") || datatype.equals("float") || datatype.equals("long") || datatype.equals("double") || datatype.equals("bool"))
                outPut = equation.Parse(newInput);
            else if(datatype.equals("string") || datatype.equals("char"))
                outPut = string.Parse(newInput);
            if(outPut.equals(""))
            {
                if(datatype.equals("int"))
                {
                    if(isLoopRunning)
                        if(!currentLoop.variablesDeclaredWithinLoop.contains(variable))
                            currentLoop.variablesDeclaredWithinLoop.add(variable);
                    if(isConditionRunning)
                        if(!currentCondition.variablesDeclaredWithinCondition.contains(variable))
                            currentCondition.variablesDeclaredWithinCondition.add(variable);
                    intMap.put(variable, 0);
                }
                else if(datatype.equals("float"))
                {
                    if(isLoopRunning)
                        if(!currentLoop.variablesDeclaredWithinLoop.contains(variable))
                            currentLoop.variablesDeclaredWithinLoop.add(variable);
                    if(isConditionRunning)
                        if(!currentCondition.variablesDeclaredWithinCondition.contains(variable))
                            currentCondition.variablesDeclaredWithinCondition.add(variable);
                    floatMap.put(variable, 0f);
                }
                else if(datatype.equals("double"))
                {
                    if(isLoopRunning)
                        if(!currentLoop.variablesDeclaredWithinLoop.contains(variable))
                            currentLoop.variablesDeclaredWithinLoop.add(variable);
                    if(isConditionRunning)
                        if(!currentCondition.variablesDeclaredWithinCondition.contains(variable))
                            currentCondition.variablesDeclaredWithinCondition.add(variable);
                    doubleMap.put(variable, 0d);
                }
                else if(datatype.equals("long"))
                {
                    if(isLoopRunning)
                        if(!currentLoop.variablesDeclaredWithinLoop.contains(variable))
                            currentLoop.variablesDeclaredWithinLoop.add(variable);
                    if(isConditionRunning)
                        if(!currentCondition.variablesDeclaredWithinCondition.contains(variable))
                            currentCondition.variablesDeclaredWithinCondition.add(variable);
                    longMap.put(variable, 0l);
                }
                else if(datatype.equals("string"))
                {
                    if(isLoopRunning)
                        if(!currentLoop.variablesDeclaredWithinLoop.contains(variable))
                            currentLoop.variablesDeclaredWithinLoop.add(variable);
                    if(isConditionRunning)
                        if(!currentCondition.variablesDeclaredWithinCondition.contains(variable))
                            currentCondition.variablesDeclaredWithinCondition.add(variable);
                    stringMap.put(variable, "");
                }
                else if(datatype.equals("char"))
                {
                    if(isLoopRunning)
                        if(!currentLoop.variablesDeclaredWithinLoop.contains(variable))
                            currentLoop.variablesDeclaredWithinLoop.add(variable);
                    if(isConditionRunning)
                        if(!currentCondition.variablesDeclaredWithinCondition.contains(variable))
                            currentCondition.variablesDeclaredWithinCondition.add(variable);
                    charMap.put(variable, '\u0000');
                }
                else if(datatype.equals("bool"))
                {
                    if(isLoopRunning)
                        if(!currentLoop.variablesDeclaredWithinLoop.contains(variable))
                            currentLoop.variablesDeclaredWithinLoop.add(variable);
                    if(isConditionRunning)
                        if(!currentCondition.variablesDeclaredWithinCondition.contains(variable))
                            currentCondition.variablesDeclaredWithinCondition.add(variable);
                    boolMap.put(variable, "false");
                }
            }
            else
            {
                if(datatype.equals("int"))
                {
                    if(isLoopRunning)
                        if(!currentLoop.variablesDeclaredWithinLoop.contains(variable))
                            currentLoop.variablesDeclaredWithinLoop.add(variable);
                    if(isConditionRunning)
                        if(!currentCondition.variablesDeclaredWithinCondition.contains(variable))
                            currentCondition.variablesDeclaredWithinCondition.add(variable);
                    intMap.put(variable, (int)Float.parseFloat(outPut));
                }
                else if(datatype.equals("float"))
                {
                    if(isLoopRunning)
                        if(!currentLoop.variablesDeclaredWithinLoop.contains(variable))
                            currentLoop.variablesDeclaredWithinLoop.add(variable);
                    if(isConditionRunning)
                        if(!currentCondition.variablesDeclaredWithinCondition.contains(variable))
                            currentCondition.variablesDeclaredWithinCondition.add(variable);
                    floatMap.put(variable, Float.parseFloat(outPut));
                }
                else if(datatype.equals("double"))
                {
                    if(isLoopRunning)
                        if(!currentLoop.variablesDeclaredWithinLoop.contains(variable))
                            currentLoop.variablesDeclaredWithinLoop.add(variable);
                    if(isConditionRunning)
                        if(!currentCondition.variablesDeclaredWithinCondition.contains(variable))
                            currentCondition.variablesDeclaredWithinCondition.add(variable);
                    doubleMap.put(variable, Double.parseDouble(outPut));
                }
                else if(datatype.equals("long"))
                {
                    if(isLoopRunning)
                        if(!currentLoop.variablesDeclaredWithinLoop.contains(variable))
                            currentLoop.variablesDeclaredWithinLoop.add(variable);
                    if(isConditionRunning)
                        if(!currentCondition.variablesDeclaredWithinCondition.contains(variable))
                            currentCondition.variablesDeclaredWithinCondition.add(variable);
                    longMap.put(variable, Long.parseLong(outPut));
                }
                else if(datatype.equals("string"))
                {
                    if(isLoopRunning)
                        if(!currentLoop.variablesDeclaredWithinLoop.contains(variable))
                            currentLoop.variablesDeclaredWithinLoop.add(variable);
                    if(isConditionRunning)
                        if(!currentCondition.variablesDeclaredWithinCondition.contains(variable))
                            currentCondition.variablesDeclaredWithinCondition.add(variable);
                    stringMap.put(variable, outPut);
                }
                else if(datatype.equals("char"))
                {
                    if(isLoopRunning)
                        if(!currentLoop.variablesDeclaredWithinLoop.contains(variable))
                            currentLoop.variablesDeclaredWithinLoop.add(variable);
                    if(isConditionRunning)
                        if(!currentCondition.variablesDeclaredWithinCondition.contains(variable))
                            currentCondition.variablesDeclaredWithinCondition.add(variable);
                    charMap.put(variable, outPut.charAt(0));
                }
                else if(datatype.equals("bool"))
                {
                    if(isLoopRunning)
                        if(!currentLoop.variablesDeclaredWithinLoop.contains(variable))
                            currentLoop.variablesDeclaredWithinLoop.add(variable);
                    if(isConditionRunning)
                        if(!currentCondition.variablesDeclaredWithinCondition.contains(variable))
                            currentCondition.variablesDeclaredWithinCondition.add(variable);
                        boolMap.put(variable,outPut);
                }
            }
        }
        else if(isArray)
            createArray(datatype,variable,ArraySize);
        return variable;
    }
    boolean isIndexOutOfRange = false;
    public void assigningValue(String statment)
    {
        String statmentWithoutSpace = "";
        for(int i = 0 ; i < statment.length() ; i++)
        {
            if(statment.charAt(i) == ' ')
                continue;
            statmentWithoutSpace += statment.charAt(i);
        }
        if(statmentWithoutSpace.charAt(statmentWithoutSpace.length()-1) != ';')
        {
            RunTime.ErrorList.add("missing semicolon \';\'");
            RunTime.ErrorNumberLine.add(RunTime.NumberLine);
            return;
        }
        String variable = "";
        String unparsedStatment = "";
        boolean isArray = false;
        int index = -1;
        for(int i = 0 ; i < statment.length() ; i++)
        {
            if(statment.charAt(i) == ' ')
                continue;
            if(statment.charAt(i) == '=' || statment.charAt(i) == '+' || statment.charAt(i) == '*' || statment.charAt(i) == '/' || statment.charAt(i) == '-')
            {
                unparsedStatment = statment.substring(i);
                break;
            }
            if(statment.charAt(i) == '[')
            {
                String temp = statment.substring(i);
                while(statment.charAt(i) != ']')
                    i++;
                index = equation.CalculateArrayIndex(temp);
                isArray = true;
                i++;
            }
            if(isArray == false)
                variable += statment.charAt(i);
        }
        if(isArray)
        {
            String variableWithIndex = variable;
            variableWithIndex += index;
            if(CheckIfExist(variableWithIndex) == -1)
            {
                if(GetType(variableWithIndex).equals("int"))
                {
                    ParsingEquation.setCurrentVariableType("int");
                    ParsingEquation.setCurrentVariableValue(intMap.get(variableWithIndex));
                    intMap.put(variableWithIndex,Integer.parseInt(equation.Parse(unparsedStatment)));
                }
                if(GetType(variableWithIndex).equals("float"))
                {
                    ParsingEquation.setCurrentVariableType("float");
                    ParsingEquation.setCurrentVariableValue(floatMap.get(variableWithIndex));
                    floatMap.put(variableWithIndex,Float.parseFloat(equation.Parse(unparsedStatment)));
                }
                if(GetType(variableWithIndex).equals("long"))
                {
                    ParsingEquation.setCurrentVariableType("long");
                    ParsingEquation.setCurrentVariableValue(longMap.get(variableWithIndex));
                    longMap.put(variableWithIndex,Long.parseLong(equation.Parse(unparsedStatment)));
                }
                if(GetType(variableWithIndex).equals("double"))
                {
                    ParsingEquation.setCurrentVariableType("double");
                    ParsingEquation.setCurrentVariableValue(doubleMap.get(variableWithIndex));
                    doubleMap.put(variableWithIndex,Double.parseDouble(equation.Parse(unparsedStatment)));
                }
                else if(GetType(variableWithIndex).equals("string"))
                {
                    ParsingStrings.setCurrentVariableType("string");
                    ParsingStrings.setCurrentVariableValue(stringMap.get(variableWithIndex));
                    stringMap.put(variableWithIndex,string.Parse(unparsedStatment));
                }
            }
            else
            {
                if(!isIndexOutOfRange)
                {
                    RunTime.ErrorList.add("index is out of range");
                    RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                }
                isIndexOutOfRange = true;
                return;
            }
        }
        else
        {
            if(CheckIfExist(variable) == -1)
            {
                if(GetType(variable).equals("int"))
                {
                    ParsingEquation.setCurrentVariableType("int");
                    ParsingEquation.setCurrentVariableValue(intMap.get(variable));
                    intMap.put(variable,Integer.parseInt(equation.Parse(unparsedStatment)));
                }
                if(GetType(variable).equals("float"))
                {
                    ParsingEquation.setCurrentVariableType("float");
                    ParsingEquation.setCurrentVariableValue(floatMap.get(variable));
                    floatMap.put(variable,Float.parseFloat(equation.Parse(unparsedStatment)));
                }
                if(GetType(variable).equals("long"))
                {
                    ParsingEquation.setCurrentVariableType("long");
                    ParsingEquation.setCurrentVariableValue(longMap.get(variable));
                    longMap.put(variable,Long.parseLong(equation.Parse(unparsedStatment)));
                }
                if(GetType(variable).equals("double"))
                {
                    ParsingEquation.setCurrentVariableType("double");
                    ParsingEquation.setCurrentVariableValue(doubleMap.get(variable));
                    doubleMap.put(variable,Double.parseDouble(equation.Parse(unparsedStatment)));
                }
                else if(GetType(variable).equals("string"))
                {
                    ParsingStrings.setCurrentVariableType("string");
                    ParsingStrings.setCurrentVariableValue(stringMap.get(variable));
                    stringMap.put(variable,string.Parse(unparsedStatment));
                }
            }
            else
            {
                RunTime.ErrorList.add("Undeclared identifier " + variable);
                RunTime.ErrorNumberLine.add(RunTime.NumberLine);
            }
        }
    }
}
