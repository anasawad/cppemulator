/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Functions extends Parsing
{
    private static ParsingEquation eq = new ParsingEquation();
    public static boolean isCalledByFunction = false;
    private int numberOfParameters = 0;
    public Map<Integer,DataTypeAndVariableNameAndValue> functionParametersMap = new HashMap<Integer, DataTypeAndVariableNameAndValue>();
    private File functionFile;
    private Scanner fileScanner;
    private String returnType = "";
    private String functionName = "";
    private ParsingEquation equation = new ParsingEquation();
    private RunTime currentRunTime = new RunTime();
    private String currentPath = "";
    public boolean isFirstTime = true;
    public void setCurrentRunTime(RunTime currentRunTime)
    {
        this.currentRunTime = currentRunTime;
    }
    public void InitializeFunction(String fileName,String path) throws FileNotFoundException
    {
        currentPath = path;
        functionFile = new File(currentPath + "\\"+fileName+".txt");
        fileScanner = new Scanner(functionFile);
        functionName = fileName;
        String functionDecleration = fileScanner.nextLine();
        if(isFirstTime)
        {
            RunTime.NumberLine+=(6*(RunTime.FunctionNumber-1));
            isFirstTime = false;
        }
        boolean dataType = false;
        boolean function = false;
        for(int i = 0 ; i < functionDecleration.length() ; i++)
        {
            if(dataType == false)
                returnType += functionDecleration.charAt(i);
            if(returnType.equals("int") || returnType.equals("float") || returnType.equals("long")
                    || returnType.equals("double") || returnType.equals("void")
                    || returnType.equals("char") || returnType.equals("string")
                    || returnType.equals("bool"))
                    dataType = true;
            if(functionDecleration.charAt(i) == '(')
                function = true;
            if(dataType == true && function == true)
            {
                declareFunctionVariables(functionDecleration.substring(i));
                break;
            }
        }
    }
    private void declareFunctionVariables(String statment)
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
        Stack<Character> brackets = new Stack<Character>();
        brackets.push(unSpacedStatment.charAt(0));
        String variableDeclaration = "";
        int currentIndex = 1;
        while(!brackets.empty())
        {
            for(int i = currentIndex ; i < unSpacedStatment.length() ; i++)
            {
                if(unSpacedStatment.charAt(i) == ',')
                {
                    declareFunctionVariable(variableDeclaration);
                    variableDeclaration = "";
                    currentIndex = i + 1;
                    break;
                }
                if(unSpacedStatment.charAt(i) == ')')
                {
                    declareFunctionVariable(variableDeclaration);
                    variableDeclaration = "";
                    brackets.pop();
                    break;
                }
                variableDeclaration += unSpacedStatment.charAt(i);
            }

        }
    }
    private void declareFunctionVariable(String statment)
    {
        String variable = "";
        String datatype = "";
        boolean dataTypeEnd = false;
        boolean varEnd = false;
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
        for(int i = 0 ; i < unSpacedStatment.length() ; i++)
        {
            if(unSpacedStatment.charAt(i)!= ' ' && dataTypeEnd == false)
            {
                datatype += unSpacedStatment.charAt(i);
                continue;
            }
            else if(unSpacedStatment.charAt(i) == ' ')
                dataTypeEnd = true;
            if((unSpacedStatment.charAt(i) != ')' && unSpacedStatment.charAt(i) != ',') && varEnd == false)
            {
                if(unSpacedStatment.charAt(i) == ' ')
                    continue;
                else if(unSpacedStatment.charAt(i) != '[')
                    variable += unSpacedStatment.charAt(i);
            }
            else if(unSpacedStatment.charAt(i) == ')' || unSpacedStatment.charAt(i) == ',' || unSpacedStatment.charAt(i) == '[')
                varEnd = true;
            if(varEnd == true && dataTypeEnd == true)
            {
               break;
            }
        }
        if(datatype.equals("int"))
        {
            functionParametersMap.put(numberOfParameters, new DataTypeAndVariableNameAndValue("int",variable, 0));
            numberOfParameters++;
        }
        else if(datatype.equals("float"))
        {
            functionParametersMap.put(numberOfParameters, new DataTypeAndVariableNameAndValue("float",variable, 0f));
            numberOfParameters++;
        }
        else if(datatype.equals("double"))
        {
            functionParametersMap.put(numberOfParameters, new DataTypeAndVariableNameAndValue("double",variable, 0d));
            numberOfParameters++;
        }
        else if(datatype.equals("long"))
        {
            functionParametersMap.put(numberOfParameters, new DataTypeAndVariableNameAndValue("long",variable, 0l));
            numberOfParameters++;
        }
        else if(datatype.equals("string"))
        {
            functionParametersMap.put(numberOfParameters, new DataTypeAndVariableNameAndValue("string",variable, ""));
            numberOfParameters++;
        }
        else if(datatype.equals("char"))
        {
            functionParametersMap.put(numberOfParameters, new DataTypeAndVariableNameAndValue("char",variable,'\u0000'));
            numberOfParameters++;
        }
    }
    @Override
    public String Parse(String statment)
    {
        isCalledByFunction = true;
        String functionkey = "";
        boolean isNotSpaced = false;
        String unSpacedStatment = "";
        String subStatment = "";
        Vector<String> arguments = new Vector<String>();
        Vector<String> dataType = new Vector<String>();
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
        for(int i = 0 ; i < unSpacedStatment.length() ; i++)
        {
            if(functionkey.equals(functionName))
            {
                subStatment = unSpacedStatment.substring(i);
                break;
            }
            functionkey += unSpacedStatment.charAt(i);
        }
        String statmentWithoutSpace = "";
        for(int i = 0 ; i < subStatment.length() ; i++)
        {
            if(subStatment.charAt(i) == ' ')
                continue;
            statmentWithoutSpace += subStatment.charAt(i);
        }
        if(statmentWithoutSpace.charAt(0) != '(')
        {
            RunTime.ErrorList.add("missing bracket \'(\'");
            RunTime.ErrorNumberLine.add(RunTime.NumberLine);
            return "";
        }
        if((statmentWithoutSpace.charAt(statmentWithoutSpace.length()-2) != ')'
             && statmentWithoutSpace.charAt(statmentWithoutSpace.length()-1) == ';')
            ||statmentWithoutSpace.charAt(statmentWithoutSpace.length()-1) != ')' && statmentWithoutSpace.charAt(statmentWithoutSpace.length()-1) != ';')
        {
            RunTime.ErrorList.add("missing bracket \')\'");
            RunTime.ErrorNumberLine.add(RunTime.NumberLine);
            return "";
        }
        if(statmentWithoutSpace.charAt(statmentWithoutSpace.length()-1) != ';')
        {
            RunTime.ErrorList.add("missing semicolon \';\'");
            RunTime.ErrorNumberLine.add(RunTime.NumberLine);
            return "";
        }
        String alpha = "";
        int i = 0;
        //el moshkela hena
        if(subStatment.charAt(i) == '(')
        {
            i++;
            while(subStatment.charAt(i) != ')')
            {
                if(Mathematics.isDigit(subStatment.charAt(i)))
                {
                    while(subStatment.charAt(i)!= ')' && subStatment.charAt(i)!= ',')
                    {
                        if(subStatment.charAt(i) == ' ')
                        {
                            i++;
                            continue;
                        }
                        alpha += subStatment.charAt(i);
                        i++;
                    }
                    dataType.add("int");
                    arguments.add(eq.Parse(alpha + ';'));
                    if(subStatment.charAt(i)!= ')')
                        i++;
                    alpha = "";
                }
                if(Mathematics.isAlpha(subStatment.charAt(i)))
                {
                    while(Mathematics.isAlpha(subStatment.charAt(i)))
                    {
                        if(subStatment.charAt(i) == ' ')
                        {
                            i++;
                            continue;
                        }
                        alpha += subStatment.charAt(i);
                        i++;
                    }
                    String expression = "";
                    while(subStatment.charAt(i) != ')' && subStatment.charAt(i) != ',')
                    {
                        expression += subStatment.charAt(i);
                        i++;
                    }
                    if(currentRunTime.variables.CheckIfExist(alpha) == -2)
                        return "error";
                    if(currentRunTime.variables.GetType(alpha).equals("int"))
                    {
                        String  value = currentRunTime.variables.intMap.get(alpha).toString();
                        if(!expression.equals(alpha))
                            value = eq.Parse(currentRunTime.variables.intMap.get(alpha).toString() + expression + ';');
                        arguments.add(value);
                        dataType.add("int");
                    }
                    else if(currentRunTime.variables.GetType(alpha).equals("float"))
                    {
                        String  value = currentRunTime.variables.floatMap.get(alpha).toString();
                        if(!expression.equals(alpha))
                            value = eq.Parse(currentRunTime.variables.floatMap.get(alpha).toString() + expression + ';');
                        arguments.add(value);
                        dataType.add("float");
                    }
                    else if(currentRunTime.variables.GetType(alpha).equals("double"))
                    {
                        String  value = currentRunTime.variables.doubleMap.get(alpha).toString();
                        if(!expression.equals(alpha))
                            value = eq.Parse(currentRunTime.variables.doubleMap.get(alpha).toString() + expression + ';');
                        arguments.add(value);
                        dataType.add("double");
                    }
                    else if(currentRunTime.variables.GetType(alpha).equals("long"))
                    {
                        String  value = currentRunTime.variables.longMap.get(alpha).toString();
                        if(!expression.equals(alpha))
                            value = eq.Parse(currentRunTime.variables.longMap.get(alpha).toString() + expression + ';');
                        arguments.add(value);
                        arguments.add(currentRunTime.variables.longMap.get(alpha).toString());
                        dataType.add("long");
                    }
                    else if(currentRunTime.variables.GetType(alpha).equals("string"))
                    {
                        String  value = currentRunTime.variables.stringMap.get(alpha);
                        if(!expression.equals(alpha))
                            value = eq.Parse(currentRunTime.variables.stringMap.get(alpha) + expression + ';');
                        arguments.add(value);
                        dataType.add("string");
                    }
                    else if(currentRunTime.variables.GetType(alpha).equals("char"))
                    {
                        if(!expression.equals(alpha))
                            currentRunTime.variables.assigningValue(alpha + '=' + expression + ';');
                        arguments.add(currentRunTime.variables.charMap.get(alpha).toString());
                        dataType.add("char");
                    }
                    if(subStatment.charAt(i)!= ')')
                        i++;
                    alpha = "";
                }
            }
        }
        String error = assignValuesToEachVariable(functionkey,arguments, dataType);
        RunTime funcRunTime = new RunTime();
        addParametersToVariables(funcRunTime);
//        RunTime.setCurrentFunction(this);
        try {
            funcRunTime.initializeRunTime(functionFile,currentPath);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            funcRunTime.CodeLoop();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
        funcRunTime.ClearRAM();
        isCalledByFunction = false;
        return "";
    }
    private void addParametersToVariables(RunTime functionRunTime)
    {
        for(int i = 0 ; i < functionParametersMap.size() ; i++)
        {
            String dataType = functionParametersMap.get(i).dataType;
            String variableName = functionParametersMap.get(i).variableName;
            String value = functionParametersMap.get(i).value.toString();
            if(dataType.equals("int"))
            {
                int Value = Integer.parseInt(value);
                functionRunTime.variables.intMap.put(variableName , Value);
            }
            else if(dataType.equals("float"))
            {
                float Value = Float.parseFloat(value);
                functionRunTime.variables.floatMap.put(variableName , Value);
            }
            else if(dataType.equals("long"))
            {
                long Value = Long.parseLong(value);
                functionRunTime.variables.longMap.put(variableName , Value);
            }
            else if(dataType.equals("double"))
            {
                double Value = Double.parseDouble(value);
                functionRunTime.variables.doubleMap.put(variableName , Value);
            }
            else if(dataType.equals("string"))
            {
                functionRunTime.variables.stringMap.put(variableName , value);
            }
            else if(dataType.equals("char"))
            {
                char Value = value.charAt(i);
                functionRunTime.variables.charMap.put(variableName , Value);
            }
        }
    }
    private String assignValuesToEachVariable(String functionName , Vector<String> arguments,Vector<String> dataType)
    {
        if(arguments.size() > numberOfParameters || arguments.size() < numberOfParameters)
        {
            RunTime.ErrorList.add(functionName + " doesn't take " + arguments.size() + " arguments");
            return "error";
        }
        else if(arguments.size() == 0)
            return "";
        else
        {
            for(int i = 0 ; i < arguments.size() ; i++)
            {
                if(dataType.get(i).equals("int"))
                {
                    DataTypeAndVariableNameAndValue temp= functionParametersMap.get(i);
                    String variableName = temp.variableName;
                    String DataType = temp.dataType;
                    if(dataType.get(i).equals(DataType))
                        functionParametersMap.put(i, new DataTypeAndVariableNameAndValue(DataType,variableName, arguments.get(i)));
                    else
                    {
                        RunTime.ErrorList.add("expected " + DataType + " but found " + dataType.get(i));
                        return "error";
                    }
                }
                else if(dataType.get(i).equals("float"))
                {
                    DataTypeAndVariableNameAndValue temp= functionParametersMap.get(i);
                    String variableName = temp.variableName;
                    String DataType = temp.dataType;
                    if(dataType.get(i).equals(DataType))
                        functionParametersMap.put(i, new DataTypeAndVariableNameAndValue(DataType,variableName, arguments.get(i)));
                    else
                    {
                        RunTime.ErrorList.add("expected " + DataType + " but found " + dataType.get(i));
                        return "error";
                    }
                }
                else if(dataType.get(i).equals("double"))
                {
                    DataTypeAndVariableNameAndValue temp= functionParametersMap.get(i);
                    String variableName = temp.variableName;
                    String DataType = temp.dataType;
                    if(dataType.get(i).equals(DataType))
                        functionParametersMap.put(i, new DataTypeAndVariableNameAndValue(DataType,variableName, arguments.get(i)));
                    else
                    {
                        RunTime.ErrorList.add("expected " + DataType + " but found " + dataType.get(i));
                        RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                        return "error";
                    }
                }
                else if(dataType.get(i).equals("long"))
                {
                    DataTypeAndVariableNameAndValue temp= functionParametersMap.get(i);
                    String variableName = temp.variableName;
                    String DataType = temp.dataType;
                    if(dataType.get(i).equals(DataType))
                        functionParametersMap.put(i, new DataTypeAndVariableNameAndValue(DataType,variableName, arguments.get(i)));
                    else
                    {
                        RunTime.ErrorList.add("expected " + DataType + " but found " + dataType.get(i));
                        RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                        return "error";
                    }
                }
                else if(dataType.get(i).equals("string"))
                {
                    DataTypeAndVariableNameAndValue temp= functionParametersMap.get(i);
                    String variableName = temp.variableName;
                    String DataType = temp.dataType;
                    if(dataType.get(i).equals(DataType))
                        functionParametersMap.put(i, new DataTypeAndVariableNameAndValue(DataType,variableName, arguments.get(i)));
                    else
                    {
                        RunTime.ErrorList.add("expected " + DataType + " but found " + dataType.get(i));
                        RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                        return "error";
                    }
                }
                else if(dataType.get(i).equals("char"))
                {
                    DataTypeAndVariableNameAndValue temp= functionParametersMap.get(i);
                    String variableName = temp.variableName;
                    String DataType = temp.dataType;
                    if(dataType.get(i).equals(DataType))
                        functionParametersMap.put(i, new DataTypeAndVariableNameAndValue(DataType,variableName, arguments.get(i)));
                    else
                    {
                        RunTime.ErrorList.add("expected " + DataType + " but found " + dataType.get(i));
                        RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                        return "error";
                    }
                }
            }
            return "";
        }
    }
    public void ReturnValue(String statment)
    {
        String StatmentWithoutSpace = "";
        for(int i = 0 ; i < statment.length() ; i++)
        {
            if(statment.charAt(i) == ' ')
                continue;
            else
                StatmentWithoutSpace += statment.charAt(i);
        }
        if(StatmentWithoutSpace.charAt(StatmentWithoutSpace.length()-1) != ';')
        {
            RunTime.ErrorList.add("missing semicolon \';\'");
            RunTime.ErrorNumberLine.add(RunTime.NumberLine);
            return;
        }
        String returnkey = "";
        String unparsedStatment = "";
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
        for(int i = 0 ; i < unSpacedStatment.length() ; i++)
        {
            if(returnkey.equals("return"))
            {
                unparsedStatment = unSpacedStatment.substring(i);
                break;
            }
            returnkey += unSpacedStatment.charAt(i);
        }
        RunTime.returnedValue.push(equation.Parse(unparsedStatment + ';'));
    }
}
