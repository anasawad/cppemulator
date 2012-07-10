
package newtexteditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParsingEquation extends Parsing
{
    static public int currentIntValue = 0;
    static public float currentFloatValue = 0;
    static public long currentLongValue = 0;
    static public double currentDoubleValue = 0;
    static public Scanner functionScanner;
    static public File functionFile;
    static public void setCurrentVariableValue(int value)
    {
        currentIntValue = value;
    }
    static public void setCurrentVariableValue(float value)
    {
        currentFloatValue = value;
    }
    static public void setCurrentVariableValue(double value)
    {
        currentDoubleValue = value;
    }
    static public void setCurrentVariableValue(long value)
    {
        currentLongValue = value;
    }
    static private Integer getCurrentIntVariableValue()
    {
        int temp = currentIntValue;
        currentIntValue = 0;
        return temp;
    }
    static private Float getCurrentFloatVariableValue()
    {
        float temp = currentFloatValue;
        currentFloatValue = 0;
        return temp;
    }
    static private Long getCurrentLongVariableValue()
    {
        long temp = currentLongValue;
        currentLongValue = 0;
        return temp;
    }
    static private Double getCurrentDoubleVariableValue()
    {
        double temp = currentDoubleValue;
        currentDoubleValue = 0;
        return temp;
    }
    static public RunTime currentRunTime = new RunTime();
    boolean isIndexOutOfRange = false;
    public int CalculateArrayIndex(String indexInString)
    {
        String temp = "";
        int i = 1;
        while(indexInString.charAt(i) != ']')
        {
            if(indexInString.charAt(i) != ' ')
                temp += indexInString.charAt(i);
            i++;
        }
        if(Mathematics.isAlpha(temp.charAt(0)))
            return Integer.parseInt(Parse(temp + ';'));
        else if(Mathematics.isDigit(temp.charAt(0)))
            return Integer.parseInt(temp);
        return -1;
    }
    @Override
    public String Parse(String statment)
    {
        //if 1st char is '=' calculate the eqn and aassing the res to the variable
        String Equation = statment;
        //erase '=' from the eqn
        if(Equation.charAt(0) == '=')
            Equation = Equation.substring(1);
        String unSpacedStatment = "";
        for(int i = 0 ; i < Equation.length() ; i++)
        {
            if(Equation.charAt(i) == ' ')
                continue;
            unSpacedStatment += Equation.charAt(i);
        }
        if(unSpacedStatment.equals("true;"))
        {
            return "true";
        }
        else if(unSpacedStatment.equals("false;"))
        {
            return "false";
        }
        String ResultOperator = "";
        // for *= , -=  , /= , +=;
        if((Equation.charAt(0) == '*' ||Equation.charAt(0) == '/' ||Equation.charAt(0) == '+' ||Equation.charAt(0) == '-') && Equation.charAt(1) == '=')
        {
            ResultOperator += Equation.charAt(0);
            Equation = Equation.substring(2);  //remove operator from eqn
        }
        if(Equation.equals(";") || Equation.equals(";;"))
            return "";

        String alpha = "";
        Stack<String> Result = new Stack<String>();
        Stack <Character> operators = new Stack<Character>();
        Stack <Character> doubleQuotes = new Stack<Character>();
        Stack <Character> singleQuotes = new Stack<Character>();
        for(int i = 0 ; i < Equation.length();i++)
        {
            if(!doubleQuotes.empty())
            {
                if(Equation.charAt(i) == '\"')
                    return alpha;
                alpha += Equation.charAt(i);
                continue;
            }
            if(!singleQuotes.empty())
            {
                if(Equation.charAt(i) == '\'')
                    return alpha;
                alpha += Equation.charAt(i);
                continue;
            }
            if(Equation.charAt(i) == ' ')
                continue;
            if(Equation.charAt(i) == '\"')
                doubleQuotes.push(Equation.charAt(i));
            if(Equation.charAt(i) == '\'')
                singleQuotes.push(Equation.charAt(i));
            if(Equation.charAt(i) == ')')
            {
                while(operators.peek() != '(')
                {
                    String q = "";
                    q += operators.peek();
                    String v1 = Result.peek();
                    Result.pop();
                    String v2 = Result.peek();
                    Result.pop();
                    if(q.equals("*"))
                        Result.push(Mathematics.ValueTimesValue(v1,v2));
                    else if(q.equals("+"))
                        Result.push(Mathematics.ValuePlusValue(v1,v2));
                    else if(q.equals("-"))
                        Result.push(Mathematics.ValueMinesValue(v1,v2));
                    else if(q.equals("/"))
                        Result.push(Mathematics.ValueDividedValue(v1,v2));
                    if(Result.peek().equals("error") || Result.peek().equals("error"))
                        return "";
                    operators.pop();
                }
                operators.pop();
            }
            // for Num Literals
            if(Mathematics.isDigit(Equation.charAt(i)))
            {
                while(Mathematics.isDigit(Equation.charAt(i)) || Equation.charAt(i) == '.')
                {
                    alpha +=Equation.charAt(i);
                    i++;
                }
                i--;
                Result.push(alpha);
                alpha = "";
                continue;
            }
            if(Mathematics.isAlpha(Equation.charAt(i)))
            {
                while(Mathematics.isAlpha(Equation.charAt(i)) || Mathematics.isDigit(Equation.charAt(i)))
                {
                    alpha +=Equation.charAt(i);
                    i++;
                }
                if(Equation.charAt(i) == '[')
                {
                    int index;
                    String temp = Equation.substring(i);
                    while(Equation.charAt(i) != ']')
                        i++;
                    index = CalculateArrayIndex(temp);
                    i++;
                    temp = alpha;
                    temp += index;
                    if(currentRunTime.variables.CheckIfExist(temp) == -1)
                        Result.push(temp);
                    else
                    {
                        if(!isIndexOutOfRange)
                        {
                            RunTime.ErrorList.add("index is out of range");
                            RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                        }
                        isIndexOutOfRange = true;
                        return "";
                    }
                }
                else
                {
                    try {
                        functionScanner = new Scanner(functionFile);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ParsingEquation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    RunTime.FunctionNumber = 0;
                    while(functionScanner.hasNext())
                    {
                        RunTime.FunctionNumber++;
                        if(alpha.equals(functionScanner.nextLine()))
                        {
                            RunTime.NumberLine--;
                            alpha += Equation.charAt(i);
                            while(Equation.charAt(i) != ')')
                            {
                                i++;
                                alpha += Equation.charAt(i);
                            }
                            i++;
                            try {
                                currentRunTime.FunctionCaller(alpha + ';');
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(ParsingEquation.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            if(RunTime.returnedValue.size() == 0)
                            {
                                return "-1000";
                            }
                            alpha = RunTime.returnedValue.peek();
                            RunTime.returnedValue.pop();
                        }
                    }
                    if(RunTime.isMathIncluded)
                    {
                        if(alpha.equals("pow"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                        else if(alpha.equals("cos"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                        else if(alpha.equals("sin"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                        else if(alpha.equals("tan"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                        else if(alpha.equals("asin"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                        else if(alpha.equals("acos"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                        else if(alpha.equals("atan"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                        else if(alpha.equals("abs"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                        else if(alpha.equals("log"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                        else if(alpha.equals("log10"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                        else if(alpha.equals("exp"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                        else if(alpha.equals("sinh"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                        else if(alpha.equals("cosh"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                        else if(alpha.equals("tanh"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                        else if(alpha.equals("ceil"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                        else if(alpha.equals("floor"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                        else if(alpha.equals("sqrt"))
                        {
                            i++;
                            Result.push(Mathematics.MathFunctions(Equation,alpha,i).toString());
                            while(Equation.charAt(i) != ')')
                                i++;
                            continue;
                        }
                    }
                    if(!Mathematics.isDigit(alpha.charAt(0)))
                    {
                        if(currentRunTime.variables.CheckIfExist(alpha) == -2)
                        {
                            RunTime.ErrorList.add("Undeclared Identifier "+alpha);
                            RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                            return "error";
                        }
                    }
                    Result.push(alpha);
                }
                alpha = "";
                i--;
                continue;
            }
            if(Equation.charAt(i) == '+' || Equation.charAt(i) == '-' ||
                Equation.charAt(i) == '*' || Equation.charAt(i) == '/' || Equation.charAt(i) == '(')
            {
                if(!operators.empty())
                {
                    if(operators.peek() != '*' && operators.peek() != '/')
                        operators.push(Equation.charAt(i));
                    else
                    {
                        if(Equation.charAt(i) == '(')
                        {
                            operators.push(Equation.charAt(i));
                            continue;
                        }
                        while(!operators.empty())
                        {
                            if(operators.peek() == '(')
                                break;
                            String q = "";
                            q += operators.peek();
                            String v1 = Result.peek();
                            Result.pop();
                            String v2 = Result.peek();
                            Result.pop();
                            if(q.equals("*"))
                                Result.push(Mathematics.ValueTimesValue(v1,v2));
                            else if(q.equals("+"))
                                Result.push(Mathematics.ValuePlusValue(v1,v2));
                            else if(q.equals("-"))
                                Result.push(Mathematics.ValueMinesValue(v1,v2));
                            else if(q.equals("/"))
                                Result.push(Mathematics.ValueDividedValue(v1,v2));
                            if(Result.peek().equals("error"))
                                return "";
                            operators.pop();
                        }
                        operators.push(Equation.charAt(i));
                    }
                }
                if(operators.empty())
                {
                    if(Equation.charAt(i) == '+' && Equation.charAt(i+1) == '+')
                    {
                        if(getCurrentVariableType().equals("int"))
                            Result.push(Mathematics.ValuePlusValue(getCurrentIntVariableValue().toString(),"1"));
                        if(getCurrentVariableType().equals("float"))
                            Result.push(Mathematics.ValuePlusValue(getCurrentFloatVariableValue().toString(),"1"));
                        if(getCurrentVariableType().equals("long"))
                            Result.push(Mathematics.ValuePlusValue(getCurrentLongVariableValue().toString(),"1"));
                        if(getCurrentVariableType().equals("double"))
                            Result.push(Mathematics.ValuePlusValue(getCurrentDoubleVariableValue().toString(),"1"));
                        return Result.peek();
                    }
                    else if(Equation.charAt(i) == '-' && Equation.charAt(i+1) == '-')
                    {
                        if(getCurrentVariableType().equals("int"))
                            Result.push(Mathematics.ValueMinesValue(getCurrentIntVariableValue().toString(),"1"));
                        if(getCurrentVariableType().equals("float"))
                            Result.push(Mathematics.ValueMinesValue(getCurrentFloatVariableValue().toString(),"1"));
                        if(getCurrentVariableType().equals("long"))
                            Result.push(Mathematics.ValueMinesValue(getCurrentLongVariableValue().toString(),"1"));
                        if(getCurrentVariableType().equals("double"))
                            Result.push(Mathematics.ValueMinesValue(getCurrentDoubleVariableValue().toString(),"1"));
                        return Result.peek();
                    }
                    operators.push(Equation.charAt(i));
                }
            }
            if(Equation.charAt(i) == ';')
            {
                while(!operators.empty())
                {
                    String q = "";
                    q += operators.peek();
                    String v1 = Result.peek();
                    Result.pop();
                    String v2 = Result.peek();
                    Result.pop();
                    if(q.equals("*"))
                        Result.push(Mathematics.ValueTimesValue(v1,v2));
                    else if(q.equals("+"))
                        Result.push(Mathematics.ValuePlusValue(v1,v2));
                    else if(q.equals("-"))
                        Result.push(Mathematics.ValueMinesValue(v1,v2));
                    else if(q.equals("/"))
                        Result.push(Mathematics.ValueDividedValue(v1,v2));
                    if(Result.peek().equals("error"))
                        return "";
                    operators.pop();
                }
            }
        }
        if(!(Mathematics.isDigit(Result.peek().charAt(0))))
        {
            if(currentRunTime.variables.CheckIfExist(Result.peek()) == -2)
            {
                RunTime.ErrorList.add("Undeclared identifier " + Result.peek());
                RunTime.ErrorNumberLine.add(RunTime.NumberLine);
            }
            if(currentRunTime.variables.GetType(Result.peek()).equals("int"))
                Result.add(currentRunTime.variables.intMap.get(Result.peek()).toString());
            else if(currentRunTime.variables.GetType(Result.peek()).equals("float"))
                Result.add(currentRunTime.variables.floatMap.get(Result.peek()).toString());
            else if(currentRunTime.variables.GetType(Result.peek()).equals("double"))
                Result.add(currentRunTime.variables.doubleMap.get(Result.peek()).toString());
            else if(currentRunTime.variables.GetType(Result.peek()).equals("long"))
                Result.add(currentRunTime.variables.longMap.get(Result.peek()).toString());
            else if(currentRunTime.variables.GetType(Result.peek()).equals("string"))
                Result.add(currentRunTime.variables.stringMap.get(Result.peek()));
            else if(currentRunTime.variables.GetType(Result.peek()).equals("char"))
            {
                String temp ="";
                temp += currentRunTime.variables.charMap.get(Result.peek());
                Result.add(temp);
            }
        }
        if(ResultOperator.equals("+"))
        {
            if(getCurrentVariableType().equals("int"))
               Result.push(Mathematics.ValuePlusValue(getCurrentIntVariableValue().toString(),Result.peek()));
            if(getCurrentVariableType().equals("float"))
               Result.push(Mathematics.ValuePlusValue(getCurrentFloatVariableValue().toString(),Result.peek()));
            if(getCurrentVariableType().equals("long"))
                Result.push(Mathematics.ValuePlusValue(getCurrentLongVariableValue().toString(),Result.peek()));
            if(getCurrentVariableType().equals("double"))
                Result.push(Mathematics.ValuePlusValue(getCurrentDoubleVariableValue().toString(),Result.peek()));
        }
        if(ResultOperator.equals("-"))
        {
            if(getCurrentVariableType().equals("int"))
               Result.push(Mathematics.ValueMinesValue(getCurrentIntVariableValue().toString(),Result.peek()));
            if(getCurrentVariableType().equals("float"))
               Result.push(Mathematics.ValueMinesValue(getCurrentFloatVariableValue().toString(),Result.peek()));
            if(getCurrentVariableType().equals("long"))
                Result.push(Mathematics.ValueMinesValue(getCurrentLongVariableValue().toString(),Result.peek()));
            if(getCurrentVariableType().equals("double"))
                Result.push(Mathematics.ValueMinesValue(getCurrentDoubleVariableValue().toString(),Result.peek()));
        }
        if(ResultOperator.equals("*"))
        {
            if(getCurrentVariableType().equals("int"))
               Result.push(Mathematics.ValueTimesValue(getCurrentIntVariableValue().toString(),Result.peek()));
            if(getCurrentVariableType().equals("float"))
               Result.push(Mathematics.ValueTimesValue(getCurrentFloatVariableValue().toString(),Result.peek()));
            if(getCurrentVariableType().equals("long"))
                Result.push(Mathematics.ValueTimesValue(getCurrentLongVariableValue().toString(),Result.peek()));
            if(getCurrentVariableType().equals("double"))
                Result.push(Mathematics.ValueTimesValue(getCurrentDoubleVariableValue().toString(),Result.peek()));
        }
        if(ResultOperator.equals("/"))
        {
            if(getCurrentVariableType().equals("int"))
               Result.push(Mathematics.ValueDividedValue(getCurrentIntVariableValue().toString(),Result.peek()));
            if(getCurrentVariableType().equals("float"))
               Result.push(Mathematics.ValueDividedValue(getCurrentFloatVariableValue().toString(),Result.peek()));
            if(getCurrentVariableType().equals("long"))
                Result.push(Mathematics.ValueDividedValue(getCurrentLongVariableValue().toString(),Result.peek()));
            if(getCurrentVariableType().equals("double"))
                Result.push(Mathematics.ValueDividedValue(getCurrentDoubleVariableValue().toString(),Result.peek()));
        }
        return Result.peek();
    }
}
