
package newtexteditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class RunTime
{
    public static boolean isIOStreamIncluded= false;
    public static boolean isNameSpaceSTDIncluded= false;
    public static boolean isWindowsIncluded= false;
    public static boolean isMathIncluded= false;
    public Variables variables = new Variables();
    static public Stack<String> returnedValue = new Stack<String>();
    public Map<String,Conditions> ifConditions = new HashMap<String,Conditions>();
    public Map<String,Loops> loops = new HashMap<String, Loops>();
    public Map<String,Switch> switchs = new HashMap<String, Switch>();
    public Map<String,Functions> functions = new HashMap<String,Functions>();
    public Scanner codeFile;
    public File f;
    public Vector<Loops> currentLoops = new Vector<Loops>();
    public boolean isReturnedFromCin = false;
    static public Conditions currentCondition;
    static private File funcFile;
    static private Scanner functionFile;
    static public boolean calledByLoop;
    public Integer currentValueOfLoop = 0;
    public boolean isFunctionCalled = false;
    public boolean isLoopStoped = false;
    static public Functions currentFunction = new Functions();
    static public Vector<String> ErrorList = new Vector<String>();
    static public Vector<Integer> ErrorNumberLine = new Vector<Integer>();
    static public String path;
    static public int NumberLine = 3;
    static public int FunctionNumber;
    static public void setCurrentFunction(Functions currentFunction)
    {
        RunTime.currentFunction = currentFunction;
    }
    public void initializeRunTime(File file ,String Path) throws FileNotFoundException
    {
        path = Path;
        this.f = file;
        this.codeFile = new  Scanner(f);
        codeFile.nextLine();
        Mathematics.currentRunTime = this;
        ParsingStrings.currentRunTime = this;
        ParsingEquation.currentRunTime = this;
        ParsingConditions.currentRunTime = this;
    }
    public void CodeLoop() throws FileNotFoundException
    {
        if(isReturnedFromCin)
        {
            if(currentLoops.size() != 0)
            {
                if(currentLoops.lastElement() instanceof For)
                    loops.get(((For)currentLoops.lastElement()).Statment).loopFunction(((For)currentLoops.lastElement()).Statment);
                else if(currentLoops.lastElement() instanceof While)
                    loops.get(((While)currentLoops.lastElement()).Statment).loopFunction(((While)currentLoops.lastElement()).Statment);
            }
        }
        while(codeFile.hasNext())
        {
            if(IOStream.waitingForCin)
                break;
            else
            {
                FunctionCaller(codeFile.nextLine());
                NumberLine++;
            }
        }
    }
    public void ClearRAM()
    {
        loops.clear();
        ifConditions.clear();
        switchs.clear();
        variables.ClearRAM();
    }
    public String currentIf = "";
    public void FunctionCaller(String statment) throws FileNotFoundException
    {
        boolean isFunctionCalled1 = false;
        if(IOStream.waitingForCin)
        {
            return;
        }
        String functionCaller = "";
        String functionReader = "";
        funcFile = new File(path + "\\functions.txt");
        ParsingEquation.functionFile = funcFile;
        ParsingConditions.functionFile = funcFile;
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
            if(unSpacedStatment.charAt(i) == ' ' && unSpacedStatment.charAt(i+1) != '+'
                    && unSpacedStatment.charAt(i+1) != '-' && unSpacedStatment.charAt(i+1) != '*'
                    && unSpacedStatment.charAt(i+1) != '/' && unSpacedStatment.charAt(i+1) != '=')
                break;
            functionCaller += unSpacedStatment.charAt(i);
            if(functionCaller.equals("{") || functionCaller.equals("}"))
            {
                isFunctionCalled1 = true;
                continue;
            }
            if(functionCaller.equals("cin") || functionCaller.equals("std::cin"))
            {
                isFunctionCalled1 = true;
                if(isIOStreamIncluded)
                {
                    if(isNameSpaceSTDIncluded)
                    {
                        IOStream.setCurrentRunTime(this);
                        IOStream.cinFuntion(statment);
                    }
                    else if(functionCaller.equals("std::cin"))
                    {
                        IOStream.setCurrentRunTime(this);
                        IOStream.cinFuntion(statment);
                    }
                    else
                        ErrorList.add("Undeclared identifier cin");
                }
                else
                    ErrorList.add("Undeclared identifier cin");
                functionCaller = "";
                break;
            }
            else if(functionCaller.equals("cout") || functionCaller.equals("std::cout"))
            {
                isFunctionCalled1 = true;
                if(isIOStreamIncluded)
                {
                    if(isNameSpaceSTDIncluded)
                    {
                        IOStream.setCurrentRunTime(this);
                        IOStream.coutFunction(statment);
                    }
                    else if(functionCaller.equals("std::cout"))
                    {
                        IOStream.setCurrentRunTime(this);
                        IOStream.coutFunction(statment);
                    }
                    else
                    {
                        ErrorList.add("Undeclared identifier cout");
                        ErrorNumberLine.add(NumberLine);
                    }
                }
                else
                    {
                        ErrorList.add("Undeclared identifier cout");
                        ErrorNumberLine.add(NumberLine);
                    }
                functionCaller = "";
                break;
            }
            else if(functionCaller.equals("int") ||functionCaller.equals("float") ||functionCaller.equals("double") ||
                    functionCaller.equals("long") || functionCaller.equals("string") || functionCaller.equals("char")
                    || functionCaller.endsWith("bool"))
            {
                isFunctionCalled1 = true;
                variables.VariableAndExpressionCompiler(statment);
                functionCaller = "";
                break;
            }
            else if(functionCaller.equals("if"))
            {
                isFunctionCalled1 = true;
                if(!ifConditions.containsKey(statment))
                {
                    Conditions ifcondition = new Conditions();
                    ifcondition.setCurrentRunTime(this);
                    ifConditions.put(statment, ifcondition);
                }
                currentCondition = ifConditions.get(statment);
                ifConditions.get(statment).isConditionRunning = true;
                variables.isConditionRunning = true;
                variables.currentCondition = currentCondition;
                ifConditions.get(statment).ifCondition(statment);
                functionCaller = "";
                break;
            }
            else if(unSpacedStatment.charAt(i) == '=' || unSpacedStatment.charAt(i) == '+' || unSpacedStatment.charAt(i) == '-'
                    || unSpacedStatment.charAt(i) == '*' ||((unSpacedStatment.charAt(i) == '/' && unSpacedStatment.charAt(i+1) != '/')
                    &&(i != 0 && unSpacedStatment.charAt(i-1) == '/' && unSpacedStatment.charAt(i) != '/')))
            {
                isFunctionCalled1 = true;
                variables.assigningValue(statment);
                functionCaller = "";
                break;
            }
            else if(functionCaller.equals("else"))
            {
                isFunctionCalled1 = true;
                ifConditions.get(currentIf).elseOrElseIfCondition(statment);
                functionCaller = "";
                break;
            }
            else if(functionCaller.equals("for"))
            {
                isFunctionCalled1 = true;
                if(!loops.containsKey(statment))
                {
                    Loops loop = new For();
                    loop.setCurrentRunTime(this);
                    loops.put(statment, loop);
                }
                currentLoops.add((For)loops.get(statment));
                loops.get(statment).isLoopRunning = true;
                variables.isLoopRunning = true;
                variables.currentLoop = currentLoops.lastElement();
                ((For)loops.get(statment)).loopFunction(statment);
                functionCaller = "";
                break;
            }
            else if(functionCaller.equals("while"))
            {
                isFunctionCalled1 = true;
                if(!loops.containsKey(statment))
                {
                    Loops loop = new While();
                    loop.setCurrentRunTime(this);
                    loops.put(statment, loop);
                }
                currentLoops.add((While)loops.get(statment));
                loops.get(statment).isLoopRunning = true;
                variables.isLoopRunning = true;
                variables.currentLoop = currentLoops.lastElement();
                ((While)loops.get(statment)).loopFunction(statment);
                functionCaller = "";
                break;
            }
            else if(functionCaller.equals("do") && i == unSpacedStatment.length()-1 )
            {
                isFunctionCalled1 = true;
                Loops loop = new DoWhile();
                loop.setCurrentRunTime(this);
                currentLoops.add(loop);
                //loops.get(statment).isLoopRunning = true;
                loop.isLoopRunning = true;
                variables.isLoopRunning = true;
                variables.currentLoop = currentLoops.lastElement();
                ((DoWhile)loop).loopFunction(statment);
                functionCaller = "";
                break;
            }
            else if(functionCaller.equals("switch"))
            {
                isFunctionCalled1 = true;
                if(!switchs.containsKey(statment))
                {
                    Switch temp = new Switch();
                    temp.setCurrentRunTime(this);
                    switchs.put(statment, temp);
                }
                switchs.get(statment).switchFunction(statment);
                functionCaller = "";
                break;
            }
            else if(functionCaller.equals("return"))
            {
                isFunctionCalled1 = true;
                currentFunction.ReturnValue(statment);
                functionCaller = "";
                while(codeFile.hasNext())
                {
                    codeFile.nextLine();
                }
                break;
            }
            else if(functionCaller.equals("//"))
            {
                isFunctionCalled1 = true;
                functionCaller = "";
                break;
            }
            else if(functionCaller.equals("break"))
            {
                isFunctionCalled1 = true;
                if(currentLoops.size() != 0)
                    currentLoops.lastElement().Break();
                String statmentWithoutSpace = "";
                for(int j = 0 ; j < statment.length() ; j++)
                {
                    if(statment.charAt(i) == ' ')
                        continue;
                    statmentWithoutSpace += statment.charAt(i);
                }
                if(statmentWithoutSpace.charAt(statmentWithoutSpace.length()-1) != ';')
                {
                    ErrorList.add("missing semicolon \';\'");
                    ErrorNumberLine.add(NumberLine);
                    return ;
                }
            }
            else if(functionCaller.equals("continue"))
            {
                isFunctionCalled1 = true;
                if(currentLoops.size() != 0)
                    currentLoops.lastElement().Continue();
                String statmentWithoutSpace = "";
                for(int j = 0 ; j < statment.length() ; j++)
                {
                    if(statment.charAt(i) == ' ')
                        continue;
                    statmentWithoutSpace += statment.charAt(i);
                }
                if(statmentWithoutSpace.charAt(statmentWithoutSpace.length()-1) != ';')
                {
                    ErrorList.add("missing semicolon \';\'");
                    ErrorNumberLine.add(NumberLine);
                    return ;
                }
            }
            functionFile = new Scanner(funcFile);
            FunctionNumber = 0;
            boolean isFunctionFinished = false;
            while(functionFile.hasNext())
            {
                if(functionFile.hasNext())
                {
                    functionReader = functionFile.nextLine();
                    FunctionNumber++;
                }
                if(functionCaller.equals(functionReader))
                {
                    isFunctionCalled1 = true;
                    if(!functions.containsKey(functionReader))
                    {
                        Functions function = new Functions();
                        function.setCurrentRunTime(this);
                        function.InitializeFunction(functionReader,path);
                        functions.put(functionReader, function);
                        isFunctionCalled1 = true;
                    }
                    functions.get(functionReader).Parse(statment);
                    Mathematics.currentRunTime = this;
                    ParsingStrings.currentRunTime = this;
                    ParsingEquation.currentRunTime = this;
                    ParsingConditions.currentRunTime = this;
                    functionCaller = "";
                    NumberLine-=(5*(RunTime.FunctionNumber))+1;
                    isFunctionFinished = true;
                    break;
                }
            }
            if(isFunctionFinished)
                break;
        }
        if(isFunctionCalled1 == false)
        {
            ErrorList.add("Undeclared identifier " + functionCaller);
            RunTime.ErrorNumberLine.add(RunTime.NumberLine);
        }
    }
}
