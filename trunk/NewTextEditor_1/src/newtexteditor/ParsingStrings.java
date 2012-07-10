/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

import java.util.Stack;

/**
 *
 * @author Mahmoud
 */
public class ParsingStrings extends Parsing
{
    static public String currentStringValue = "";
    static public RunTime currentRunTime = new RunTime();
    static public void setCurrentVariableValue(String value)
    {
        currentStringValue = value;
    }
    static private String getCurrentStringVariableValue()
    {
        String temp = currentStringValue;
        currentStringValue = "";
        return temp;
    }
    @Override
    public String Parse(String statment)
    {
        if(statment.charAt(0) == '=')
            statment = statment.substring(1);
        String ResultOperator = "";
        if(statment.charAt(0) == '+' && statment.charAt(1) == '=')
        {
            ResultOperator += statment.charAt(0);
            statment = statment.substring(2);
        }
        if(statment.equals(";"))
            return "";
        String alpha= "";
        Stack<String> Result = new Stack<String>();
        Stack <Character> operators = new Stack<Character>();
        Stack <Character> doubleQuotes = new Stack<Character>();
        Stack <Character> singleQuotes = new Stack<Character>();
        for(int i = 0 ; i < statment.length();i++)
        {
            if(!doubleQuotes.empty())
            {
                if(statment.charAt(i) == '\"')
                {
                    doubleQuotes.pop();
                    Result.push(alpha);
                    alpha = "";
                    continue;
                }
                alpha += statment.charAt(i);
                continue;
            }
            if(!singleQuotes.empty())
            {
                if(statment.charAt(i) == '\'')
                {
                    singleQuotes.pop();
                    Result.push(alpha);
                    continue;
                }
                alpha += statment.charAt(i);
                continue;
            }
            if(statment.charAt(i) == ' ')
                continue;
            if(statment.charAt(i) == '\"')
                doubleQuotes.push(statment.charAt(i));
            if(statment.charAt(i) == '\'')
                singleQuotes.push(statment.charAt(i));
            if(statment.charAt(i) == ')')
            {
                while(operators.peek() != '(')
                {
                    String q = "";
                    q += operators.peek();
                    String v1 = Result.peek();
                    Result.pop();
                    String v2 = Result.peek();
                    Result.pop();
                    if(q.equals("+"))
                        Result.push(v2+v1);
                    if(Result.peek().equals("error"))
                        return "";
                    operators.pop();
                }
                operators.pop();
            }
            if(Mathematics.isDigit(statment.charAt(i)))
            {
                while(Mathematics.isDigit(statment.charAt(i)))
                {
                    alpha +=statment.charAt(i);
                    i++;
                }
                i--;
                Result.push(alpha);
                alpha = "";
                continue;
            }
            if(statment.charAt(i) == '+' || statment.charAt(i) == '(')
            {
                if(!operators.empty())
                {
                    if(statment.charAt(i) == '(')
                    {
                        operators.push(statment.charAt(i));
                        continue;
                    }
                    operators.push(statment.charAt(i));
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
                        if(q.equals("+"))
                            Result.push(v2+v1);
                        if(Result.peek().equals("error"))
                            return "";
                        operators.pop();
                    }
                    operators.push(statment.charAt(i));
                }
                if(operators.empty())
                {
                    operators.push(statment.charAt(i));
                }
            }
            if(statment.charAt(i) == ';')
            {
                while(!operators.empty())
                {
                    String q = "";
                    q += operators.peek();
                    String v1 = Result.peek();
                    Result.pop();
                    String v2 = Result.peek();
                    Result.pop();
                    if(q.equals("+"))
                        Result.push(v2+v1);
                    if(Result.peek().equals("error"))
                        return "";
                    operators.pop();
                }
            }
        }
        if(ResultOperator.equals("+"))
        {
            if(getCurrentVariableType().equals("string"))
                Result.push(getCurrentStringVariableValue() + Result.peek());
        }
        return Result.peek();
    }
}
