/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParsingConditions extends Parsing
{
    static private ParsingEquation equation = new ParsingEquation();
    static public RunTime currentRunTime = new RunTime();
    static public Scanner functionScanner;
    static public File functionFile;
    static public String ParsingComparison(String Comparison) throws FileNotFoundException
    {
        String statmentWithoutSpace = "";
        for(int i = 0 ; i < Comparison.length() ; i++)
        {
            if(Comparison.charAt(i) == ' ')
                continue;
            statmentWithoutSpace += Comparison.charAt(i);
            try {
                functionScanner = new Scanner(functionFile);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ParsingEquation.class.getName()).log(Level.SEVERE, null, ex);
            }
            while(functionScanner.hasNext())
            {
                if(statmentWithoutSpace.equals(functionScanner.nextLine()))
                {
                    statmentWithoutSpace += Comparison.charAt(i);
                    while(Comparison.charAt(i) != ')')
                    {
                        i++;
                        statmentWithoutSpace += Comparison.charAt(i);
                    }
                    i++;
                    try {
                        currentRunTime.FunctionCaller(statmentWithoutSpace);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ParsingEquation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    statmentWithoutSpace = RunTime.returnedValue.peek();
                    RunTime.returnedValue.pop();
                    if(statmentWithoutSpace.equals("true") || statmentWithoutSpace.equals("false"))
                        return statmentWithoutSpace;
                }
            }
        }
        if(currentRunTime.variables.boolMap.containsKey(statmentWithoutSpace))
            return currentRunTime.variables.boolMap.get(statmentWithoutSpace);
        if(Comparison.equals("true") || Comparison.equals("false"))
            return Comparison;
        String lhs = "";
        String rhs = "";
        int counter = 0;
        int operatorIndex = -1;
        boolean isIndexLocated = false;
        for(int i = 0 ; i < Comparison.length() ; i++)
        {
            if(!isIndexLocated && (Comparison.charAt(i) == '=' ||Comparison.charAt(i) == '!' ||Comparison.charAt(i) == '>' ||Comparison.charAt(i) == '<'))
            {
                if(Comparison.charAt(i-1) == ' ')
                    operatorIndex = i;
                else
                    operatorIndex = i-1;
                isIndexLocated = true;
            }
            if(Mathematics.isAlpha(Comparison.charAt(i)))
            {
                while(Comparison.charAt(i) != ')' && Comparison.charAt(i) != '!' && Comparison.charAt(i) != '>'
                        && Comparison.charAt(i) != '<' && Comparison.charAt(i) != '=' && Comparison.charAt(i) != ' ')
                {
                    if(counter == 0)
                    {
                        if(i != 0 && Comparison.charAt(i-1) == '\"')
                            lhs += '\"';
                        else if(i != 0 && Comparison.charAt(i-1) == '\'')
                            lhs += '\'';
                        lhs += Comparison.charAt(i);
                    }
                    else if (counter == 1)
                    {
                        if(i != 0 && Comparison.charAt(i-1) == '\"')
                            rhs += '\"';
                        else if(i != 0 && Comparison.charAt(i-1) == '\'')
                            rhs += '\'';
                        rhs += Comparison.charAt(i);
                    }
                    if(i == Comparison.length()-1)
                        break;
                    i++;
                }
                if(Comparison.charAt(i) == '[' && counter == 0)
                {
                    int Index = -1;
                    String temp = Comparison.substring(i);
                    while(Comparison.charAt(i) != ']')
                        i++;
                    Index = equation.CalculateArrayIndex(temp);
                    lhs += Index;
                }
                else if(Comparison.charAt(i) == '[' && counter == 1)
                {
                    int Index = -1;
                    String temp = Comparison.substring(i);
                    while(Comparison.charAt(i) != ']')
                        i++;
                    Index = equation.CalculateArrayIndex(temp);
                    rhs += Index;
                }
                counter++;
            }
            if(Mathematics.isDigit(Comparison.charAt(i)))
            {
                while(Comparison.charAt(i) != ')' && Comparison.charAt(i) != '!' && Comparison.charAt(i) != '>'
                        && Comparison.charAt(i) != '<' && Comparison.charAt(i) != '=' && Comparison.charAt(i) != ' ')
                {
                    if(counter == 0)
                        lhs += Comparison.charAt(i);
                    else if (counter == 1)
                        rhs += Comparison.charAt(i);
                    if(i == Comparison.length()-1)
                        break;
                    i++;
                }
                counter++;
            }
        }
        if(rhs.charAt(0) == '\"')
            rhs += '\"';
        else if(rhs.charAt(0) == '\'')
            rhs += '\'';
        if(lhs.charAt(0) == '\"')
            lhs += '\"';
        if(lhs.charAt(0) == '\'')
            lhs += '\'';
        lhs = equation.Parse(lhs + ';');
        rhs = equation.Parse(rhs + ';');
        if(Comparison.charAt(operatorIndex) == '=' && Comparison.charAt(operatorIndex+1) == '=')
        {
            if(Equal(lhs,rhs))
                return "true";
            return "false";
        }
        else if(Comparison.charAt(operatorIndex) == '<' && Comparison.charAt(operatorIndex+1) == '=')
        {
            if(lessThanOrEqual(lhs,rhs))
                return "true";
            return "false";
        }
        else if(Comparison.charAt(operatorIndex) == '>' && Comparison.charAt(operatorIndex+1) == '=')
        {
            if(greaterThanOrEqual(lhs,rhs))
                return "true";
            return "false";
        }
        else if(Comparison.charAt(operatorIndex) == '>' && Comparison.charAt(operatorIndex+1) != '=')
        {
            if(greaterThan(lhs,rhs))
                return "true";
            return "false";
        }
        else if(Comparison.charAt(operatorIndex) == '<' && Comparison.charAt(operatorIndex+1) != '=')
        {
            if(lessThan(lhs,rhs))
                return "true";
            return "false";
        }
        else if(Comparison.charAt(operatorIndex) == '!' && Comparison.charAt(operatorIndex+1) == '=')
        {
            if(notEqual(lhs,rhs))
                return "true";
            return "false";
        }
        return "error";
    }
    static private String orCondtion(String condition1,String condition2) throws FileNotFoundException
    {
        String boolean1 = ParsingComparison(condition1);
        String boolean2 = ParsingComparison(condition2);
        if(boolean1.equals("true") || boolean2.equals("true"))
            return "true";
        return "false";
    }
    static private String andCondtion(String condition1,String condition2) throws FileNotFoundException
    {
        String boolean1 = ParsingComparison(condition1);
        String boolean2 = ParsingComparison(condition2);
        if(boolean1.equals("true") && boolean2.equals("true"))
            return "true";
        return "false";
    }
    static private String notCondtion(String condition) throws FileNotFoundException
    {
        String boolean1 = ParsingComparison(condition);
        if(!boolean1.equals("true"))
            return "true";
        return "false";
    }
    @Override
    public String Parse(String statment)
    {
        String conditions = statment;
        String alpha= "";
        Stack<String> Result = new Stack<String>();
        Stack <Character> operators = new Stack<Character>();
        for(int i = 0 ; i < conditions.length();i++)
        {
            if(conditions.charAt(i) == ' ')
                continue;
            if(conditions.charAt(i) == ')')
            {
                String q = "";
                while(operators.peek() != '(')
                {
                    q = "";
                    q += operators.peek();
                    String condition1 = "";
                    String condition2 = "";
                    if(!q.equals("!"))
                    {
                        condition1 = Result.peek();
                        Result.pop();
                        condition2 = Result.peek();
                        Result.pop();
                    }
                    if(q.equals("&"))
                        try {
                        Result.push(andCondtion(condition1, condition2));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ParsingConditions.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    else if(q.equals("|"))
                        try {
                        Result.push(orCondtion(condition1, condition2));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ParsingConditions.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    else if(q.equals("!"))
                    {
                        if(Result.peek().equals("true"))
                        {
                            Result.pop();
                            Result.push("false");
                        }
                        else if(Result.peek().equals("false"))
                        {
                            Result.pop();
                            Result.push("true");
                        }
                    }
                    if(Result.peek().equals("Exit"))
                        return "Exit";
                    operators.pop();
                }
                operators.pop();
                if(!Result.peek().equals("true") && !Result.peek().equals("false"))
                {
                    String resultTop = Result.peek();
                    Result.pop();
                    try {
                        Result.push(ParsingComparison(resultTop));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ParsingConditions.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if((conditions.charAt(i) == '!' && conditions.charAt(i+1) == '=') ||
                    ( conditions.charAt(i) != '|' && conditions.charAt(i) != '&' && conditions.charAt(i) != '(' &&
                    conditions.charAt(i) != ')' && !(conditions.charAt(i) == '!' && conditions.charAt(i+1) != '=')))
            {
                while((conditions.charAt(i) == '!' && conditions.charAt(i+1) == '=') ||
                        (conditions.charAt(i) != '|' && conditions.charAt(i) != '&' && conditions.charAt(i) != '(' &&
                        conditions.charAt(i) != ')' && !(conditions.charAt(i) == '!' && conditions.charAt(i+1) != '=')))
                {
                    alpha += conditions.charAt(i);
                    i++;
                }
                i--;
                Result.push(alpha);
                alpha = "";
                continue;
            }
        if((conditions.charAt(i) == '|' && conditions.charAt(i+1) == '|') ||
                    (conditions.charAt(i) == '&' && conditions.charAt(i+1) == '&') ||
                    (conditions.charAt(i) == '!' && conditions.charAt(i+1) != '=') ||
                    conditions.charAt(i) == '(')
            {
                if(!operators.empty())
                {
                    if(operators.peek() == '|' || operators.peek() == '(')
                    {
                        operators.push(conditions.charAt(i));
                    }
                    else if(operators.peek() == '&' && (conditions.charAt(i) == '!' ||conditions.charAt(i) == '&'))
                    {
                        operators.push(conditions.charAt(i));
                    }
                    else if(operators.peek() == '!' && conditions.charAt(i) == '!')
                    {
                        operators.push(conditions.charAt(i));
                    }
                    else
                    {
                        if(conditions.charAt(i) == '(')
                        {
                            operators.push(conditions.charAt(i));
                            continue;
                        }
                        while(!operators.empty())
                        {
                            if(operators.peek() == '(')
                                break;
                            String q = "";
                            q += operators.peek();
                            String condition1 = Result.peek();
                            Result.pop();
                            String condition2 ="";
                            if(!q.equals("!"))
                            {
                                condition2 = Result.peek();
                                Result.pop();
                            }
                            if(q.equals("&"))
                                try {
                                Result.push(andCondtion(condition1, condition2));
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(ParsingConditions.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            else if(q.equals("|"))
                                try {
                                Result.push(orCondtion(condition1, condition2));
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(ParsingConditions.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            else if(q.equals("!"))
                                try {
                                Result.push(notCondtion(condition1));
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(ParsingConditions.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            if(Result.peek().equals("Exit"))
                                return "Exit";
                            operators.pop();
                        }
                        operators.push(conditions.charAt(i));
                    }
                }
                if(operators.empty())
                    operators.push(conditions.charAt(i));
            }
        }
        if(!Result.peek().equals("true") && !Result.peek().equals("false"))
        {
            String temp = Result.peek();
            if(currentRunTime.variables.CheckIfExist(temp) == -1)
            {
                if(currentRunTime.variables.GetType(temp).equals("bool"))
                {
                    Result.pop();
                    Result.push(currentRunTime.variables.boolMap.get(temp));
                }
            }
            
        }
        return Result.peek();
    }
    static private boolean Equal(String lhs , String rhs)
    {
        if(lhs.equals(rhs))
            return true;
        return false;
    }
    static private boolean notEqual(String lhs , String rhs)
    {
        if(!lhs.equals(rhs))
            return true;
        return false;
    }
    static private boolean greaterThanOrEqual(String lhs , String rhs)
    {
        if(Float.parseFloat(lhs) >= Float.parseFloat(rhs))
            return true;
        return false;
    }
    static private boolean lessThanOrEqual(String lhs , String rhs)
    {
        if(Float.parseFloat(lhs) <= Float.parseFloat(rhs))
            return true;
        return false;
    }
    static private boolean greaterThan(String lhs , String rhs)
    {
        if(Float.parseFloat(lhs) > Float.parseFloat(rhs))
            return true;
        return false;
    }
    static private boolean lessThan(String lhs , String rhs)
    {
        if(Float.parseFloat(lhs) < Float.parseFloat(rhs))
            return true;
        return false;
    }
}
