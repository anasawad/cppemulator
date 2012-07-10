/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

import java.io.FileNotFoundException;
import java.util.Stack;

public class IOStream
{
    static private ParsingEquation Equation = new ParsingEquation();
    static private int index = 0;
    static public boolean waitingForCin = false;
    static private String currentVariableName = "";
    static public Console c = new Console();
    static public RunTime currentRunTime = new RunTime();
    static public void setCurrentRunTime(RunTime current)
    {
        currentRunTime = current;
    }
    static public void setConsole(Console x)
    {
        c = x;
    }
    static public void coutFunction(String statment)
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
        }
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
        String coutkey = "";
        String unparsedStatment = "";
        String operation = "";
        Stack<String> temp = new Stack<String>();
        String ParsedStatment = "";
        Stack<Character> doubleQuotes = new Stack<Character>();
        Stack<Character> singleQuotes = new Stack<Character>();
        boolean isFirst = false;
        for(int i = 0 ; i < unSpacedStatment.length() ; i++)
        {
            if(coutkey.equals("cout") || coutkey.equals("std::cout"))
            {
                unparsedStatment = unSpacedStatment.substring(i);
                break;
            }
            coutkey += unSpacedStatment.charAt(i);
        }
        for(int i = 0 ; i < unparsedStatment.length() ; i++)
        {
            if(!doubleQuotes.empty())
            {
                if(unparsedStatment.charAt(i) != '\"')
                {
                    ParsedStatment += unparsedStatment.charAt(i);
                    continue;
                }
                else
                {
                    ParsedStatment += unparsedStatment.charAt(i);
                    doubleQuotes.pop();
                    continue;
                }
            }
            if(!singleQuotes.empty())
            {
                if(unparsedStatment.charAt(i) != '\'')
                {
                    ParsedStatment += unparsedStatment.charAt(i);
                    continue;
                }
                else
                {
                    ParsedStatment += unparsedStatment.charAt(i);
                    singleQuotes.pop();
                    continue;
                }
            }
            if(unparsedStatment.charAt(i) == ' ')
                continue;
            if(unparsedStatment.charAt(i) == '\"')
                doubleQuotes.push(unparsedStatment.charAt(i));
            if(unparsedStatment.charAt(i) == '\'')
                singleQuotes.push(unparsedStatment.charAt(i));
            if(unparsedStatment.charAt(i) == ';')
            {
                ParsedStatment += '@';
                temp.pop();
            }
            if(unparsedStatment.charAt(i) == '<')
            {
                while(unparsedStatment.charAt(i) == '<')
                {
                    operation +=unparsedStatment.charAt(i);
                    i++;
                }
                temp.push(operation);
                if(isFirst == true)
                {
                    ParsedStatment += '@';
                    temp.pop();
                }
                isFirst = true;
                i--;
                operation = "";
                continue;
            }
            ParsedStatment += unparsedStatment.charAt(i);
        }
        String equation = "";
        for(int i = 0 ; i < ParsedStatment.length() ; i++)
        {
            if(ParsedStatment.charAt(i) == '@')
            {
                if(equation.equals("endl"))
                    c.setConsoleText("\n");
                else
                {
                    String print = Equation.Parse(equation+';');
                    for(int j = 0 ; j < print.length() ; j++)
                    {
                        if(print.charAt(j) == '\\' && print.charAt(j+1) == 'n')
                        {
                            c.setConsoleText("\n");
                            continue;
                        }
                        if(print.charAt(j) == 'n' && j!= 0 && print.charAt(j-1) == '\\')
                            continue;
                        if(print.charAt(j) == '\\' && print.charAt(j+1) == 't')
                        {
                            c.setConsoleText("\t");
                            continue;
                        }
                        if(print.charAt(j) == 't' && j != 0 && print.charAt(j-1) == '\\')
                            continue;
                        c.setConsoleText(""+print.charAt(j));
                    }
                }
                equation = "";
                continue;
            }
            equation += ParsedStatment.charAt(i);
        }
    }
    static public void cinFuntion(String statment)
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
        String cinkey = "";
        String unparsedStatment = "";
        String operation = "";
        Stack<String> temp = new Stack<String>();
        String ParsedStatment = "";
        boolean isFirst = false;
        for(int i = 0 ; i < unSpacedStatment.length() ; i++)
        {
            if(cinkey.equals("cin") || cinkey.equals("std::cin"))
            {
                unparsedStatment = unSpacedStatment.substring(i);
                break;
            }
            cinkey += unSpacedStatment.charAt(i);
        }
        for(int i = 0 ; i < unparsedStatment.length() ; i++)
        {
            if(unparsedStatment.charAt(i) == ' ')
                continue;
            if(unparsedStatment.charAt(i) == ';')
            {
                ParsedStatment += '@';
                temp.pop();
            }
            if(unparsedStatment.charAt(i) == '>')
            {
                while(unparsedStatment.charAt(i) == '>')
                {
                    operation +=unparsedStatment.charAt(i);
                    i++;
                }
                temp.push(operation);
                if(isFirst == true)
                {
                    ParsedStatment += '@';
                    temp.pop();
                }
                isFirst = true;
                i--;
                operation = "";
                continue;
            }
            ParsedStatment += unparsedStatment.charAt(i);
        }
        String variable = "";
        for(int i = 0 ; i < ParsedStatment.length() ; i++)
        {
            if(ParsedStatment.charAt(i) == '@')
            {
                if(currentRunTime.variables.CheckIfExist(variable) == -1)
                {
                    index = c.getConsoleText().length();
                    c.setConsoleTextIndex(index);
                    c.setConsoleEditable(true);
                    waitingForCin = true;
                    if(currentRunTime.currentLoops.size() != 0)
                        if(currentRunTime.currentLoops.lastElement() instanceof For)
                           ((For)currentRunTime.currentLoops.lastElement()).lastCinStatment = statment;
                        else if(currentRunTime.currentLoops.lastElement() instanceof While)
                           ((While)currentRunTime.currentLoops.lastElement()).lastCinStatment = statment;
                    currentVariableName = variable;
                    currentRunTime.isReturnedFromCin = true;
                }
                variable = "";
                continue;
            }
            if(ParsedStatment.charAt(i) == '[')
            {
                int Index;
                String temp1 = ParsedStatment.substring(i);
                while(ParsedStatment.charAt(i) != ']')
                    i++;
                Index = Equation.CalculateArrayIndex(temp1);
                variable += Index;
                if(currentRunTime.variables.CheckIfExist(variable) == -2)
                {
                    RunTime.ErrorList.add("index out of range");
                    RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                }
                continue;
            }
            variable += ParsedStatment.charAt(i);
        }
    }
    static public void setCinValue(String Text) throws FileNotFoundException
    {
        String value = Text.substring(index);
        if(currentRunTime.variables.GetType(currentVariableName).equals("int"))
            currentRunTime.variables.intMap.put(currentVariableName,Integer.parseInt(value));
        else if(currentRunTime.variables.GetType(currentVariableName).equals("float"))
            currentRunTime.variables.floatMap.put(currentVariableName,Float.parseFloat(value));
        else if(currentRunTime.variables.GetType(currentVariableName).equals("double"))
            currentRunTime.variables.doubleMap.put(currentVariableName,Double.parseDouble(value));
        else if(currentRunTime.variables.GetType(currentVariableName).equals("long"))
            currentRunTime.variables.longMap.put(currentVariableName,Long.parseLong(value));
        else if(currentRunTime.variables.GetType(currentVariableName).equals("string"))
            currentRunTime.variables.stringMap.put(currentVariableName,value);
        else if(currentRunTime.variables.GetType(currentVariableName).equals("char"))
            currentRunTime.variables.charMap.put(currentVariableName,value.charAt(0));
        currentVariableName ="";
        waitingForCin = false;
        c.setConsoleText("\n");
        c.setConsoleEditable(false);
        currentRunTime.CodeLoop();
    }
}
