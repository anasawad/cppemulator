
package newtexteditor;
import java.lang.Math;

public class Mathematics
{
    static public RunTime currentRunTime = new RunTime();
    static public ParsingEquation eq = new ParsingEquation();
    static public void setCurrentRunTime(RunTime currentRunTime)
    {
        Mathematics.currentRunTime = currentRunTime;
    }
    static public boolean isDigit(char character)
    {
        if(character >= '0' && character <= '9')
            return true;
        return false;
    }
    static public boolean isAlpha(char character)
    {
        if((character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z'))
            return true;
        return false;
    }
    static public Double MathFunctions(String Equation,String function ,int i)
    {
        double value = 0;
        String alpha = "";
        int index = i;
        i--;
        if(Equation.charAt(i) == '(')
        {
            i++;
            if(isDigit(Equation.charAt(i)))
            {
                while(isDigit(Equation.charAt(i)) || Equation.charAt(i) == '.')
                {
                    alpha += Equation.charAt(i);
                    i++;
                }
                value = Double.parseDouble(alpha);
                if(Equation.charAt(i)!= ')')
                    i++;
                alpha = "";
            }
            if(isAlpha(Equation.charAt(i)))
            {
                while(Equation.charAt(i) != ')')
                {
                    alpha += Equation.charAt(i);
                    i++;
                }
                value = Double.parseDouble(eq.Parse(alpha + ';'));
                if(Equation.charAt(i)!= ')')
                    i++;
                alpha = "";
            }
        }
        if(function.equals("cos"))
            return Math.cos(value*3.14159265/180);
        else if(function.equals("sin"))
            return Math.sin(value*3.14159265/180);
        else if(function.equals("tan"))
            return Math.tan(value*3.14159265/180);
        else if(function.equals("asin"))
            return (Math.asin(value)/3.14159265)*180;
        else if(function.equals("acos"))
            return (Math.acos(value)/3.14159265)*180;
        else if(function.equals("atan"))
            return (Math.atan(value)/3.14159265)*180;
        else if(function.equals("sinh"))
            return Math.sinh(value);
        else if(function.equals("cosh"))
            return Math.cosh(value);
        else if(function.equals("tanh"))
            return Math.tanh(value);
        else if(function.equals("exp"))
            return Math.exp(value);
        else if(function.equals("log"))
            return Math.log(value);
        else if(function.equals("log10"))
            return Math.log10(value);
        else if(function.equals("sqrt"))
            return Math.sqrt(value);
        else if(function.equals("ceil"))
            return Math.ceil(value);
        else if(function.equals("floor"))
            return Math.floor(value);
        else if(function.equals("abs"))
            return Math.abs(value);
        else if(function.equals("pow"))
            return powFunction(Equation, index);
        else
            return -1.0;
    }
    static private double powFunction(String Equation ,int i)
    {
        double powValues[] = new double[2];
        int counter = 0;
        String alpha = "";
        i--;
        if(Equation.charAt(i) == '(')
        {
            i++;
            while(Equation.charAt(i) != ')' && Equation.charAt(i) != ';')
            {
                if(isDigit(Equation.charAt(i)))
                {
                    while(isDigit(Equation.charAt(i)) || Equation.charAt(i) == '.')
                    {
                        alpha += Equation.charAt(i);
                        i++;
                    }
                    powValues[counter] = Double.parseDouble(alpha);
                    counter++;
                    alpha = "";
                    i++;
                }
                if(isAlpha(Equation.charAt(i)))
                {
                    while(Equation.charAt(i) != ',' && Equation.charAt(i) != ')')
                    {
                        alpha += Equation.charAt(i);
                        i++;
                    }
                    powValues[counter] = Double.parseDouble(eq.Parse(alpha+';'));
                    counter++;
                    alpha = "";
                }
            }
        }
        return Math.pow(powValues[0],powValues[1]);
    }
    static public String ValueTimesValue(String v1,String v2)
    {
        if(isAlpha(v1.charAt(0)) && isAlpha(v2.charAt(0)))
        {
            if(currentRunTime.variables.CheckIfExist(v1) == -1 && currentRunTime.variables.CheckIfExist(v2) == -1)
            {
                if(currentRunTime.variables.GetType(v1).equals("int") && currentRunTime.variables.GetType(v2).equals("int"))
                {
                    Integer result = currentRunTime.variables.intMap.get(v1) * currentRunTime.variables.intMap.get(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("float") && currentRunTime.variables.GetType(v2).equals("float"))
                {
                    Float result = currentRunTime.variables.floatMap.get(v1) * currentRunTime.variables.floatMap.get(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("double") && currentRunTime.variables.GetType(v2).equals("double"))
                {
                    Double result = currentRunTime.variables.doubleMap.get(v1) * currentRunTime.variables.doubleMap.get(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("long") && currentRunTime.variables.GetType(v2).equals("long"))
                {
                    Long result = currentRunTime.variables.longMap.get(v1) * currentRunTime.variables.longMap.get(v2);
                    return result.toString();
                }
            }
            else if (currentRunTime.variables.CheckIfExist(v1) == -1 && currentRunTime.variables.CheckIfExist(v2) >= 0)
            {
                if(currentRunTime.variables.GetType(v1).equals("int") && currentRunTime.variables.GetType(v2).equals("int"))
                {
                    Integer result = currentRunTime.variables.intMap.get(v1) * currentRunTime.variables.intMap.get(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("float") && currentRunTime.variables.GetType(v2).equals("float"))
                {
                    Float result = currentRunTime.variables.floatMap.get(v1) * currentRunTime.variables.floatMap.get(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("double") && currentRunTime.variables.GetType(v2).equals("double"))
                {
                    Double result = currentRunTime.variables.doubleMap.get(v1) * currentRunTime.variables.doubleMap.get(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("long") && currentRunTime.variables.GetType(v2).equals("long"))
                {
                    Long result = currentRunTime.variables.longMap.get(v1) * currentRunTime.variables.longMap.get(v2);
                    return result.toString();
                }
            }
            else if(currentRunTime.variables.CheckIfExist(v1) == -2 || currentRunTime.variables.CheckIfExist(v2) == -2)
            {
                if(currentRunTime.variables.CheckIfExist(v1) == -2)
                {
                    RunTime.ErrorList.add("Undeclared Identifier "+v1);
                    RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                    return "error";
                }
                if(currentRunTime.variables.CheckIfExist(v2) == -2)
                {
                    RunTime.ErrorList.add("Undeclared Identifier "+v2);
                    RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                    return "error";
                }
            }
        }
        else if(isAlpha(v1.charAt(0)) && !isAlpha(v2.charAt(0)))
        {
            if(currentRunTime.variables.CheckIfExist(v1) == -1)
            {
                if(currentRunTime.variables.GetType(v1).equals("int"))
                {
                    Integer result = currentRunTime.variables.intMap.get(v1) * Integer.parseInt(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("float"))
                {
                    Float result = currentRunTime.variables.floatMap.get(v1) * Float.parseFloat(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("double"))
                {
                    Double result = currentRunTime.variables.doubleMap.get(v1) * Double.parseDouble(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("long"))
                {
                    Long result = currentRunTime.variables.longMap.get(v1) * Long.parseLong(v2);
                    return result.toString();
                }
            }
            else
            {
                RunTime.ErrorList.add("Undeclared Identifier "+v1);
                RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                return "error";
            }
        }
        else if(!isAlpha(v1.charAt(0)) && isAlpha(v2.charAt(0)))
        {
            if(currentRunTime.variables.CheckIfExist(v2) == -1)
            {
                if(currentRunTime.variables.GetType(v2).equals("int"))
                {
                    Integer result = currentRunTime.variables.intMap.get(v2) * Integer.parseInt(v1);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v2).equals("float"))
                {
                    Float result = currentRunTime.variables.floatMap.get(v2) * Float.parseFloat(v1);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v2).equals("double"))
                {
                    Double result = currentRunTime.variables.doubleMap.get(v2) * Double.parseDouble(v1);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v2).equals("long"))
                {
                    Long result = currentRunTime.variables.longMap.get(v2) * Long.parseLong(v1);
                    return result.toString();
                }
            }
            else
            {
                RunTime.ErrorList.add("Undeclared Identifier "+v2);
                RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                return "error";
            }
        }
        else
        {
            boolean isInteger = false;
            Integer result = 0;
            Float result1 = 0f;
            try
            {
                result = Integer.parseInt(v1) * Integer.parseInt(v2);
                isInteger = true;
            }
            catch(NumberFormatException e)
            {
                result1 = Float.parseFloat(v1) * Float.parseFloat(v2);
                isInteger = false;
            }
            if(isInteger)
                return result.toString();
            else
                return result1.toString();
        }
        return "error";
    }
    static public String ValuePlusValue(String v1,String v2)
    {
        if(isAlpha(v1.charAt(0)) && isAlpha(v2.charAt(0)))
        {
            if(currentRunTime.variables.CheckIfExist(v1)!= -2 && currentRunTime.variables.CheckIfExist(v2) != -2)
            {
                if(currentRunTime.variables.GetType(v1).equals("int") && currentRunTime.variables.GetType(v2).equals("int"))
                {
                    Integer result = currentRunTime.variables.intMap.get(v1) + currentRunTime.variables.intMap.get(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("float") && currentRunTime.variables.GetType(v2).equals("float"))
                {
                    Float result = currentRunTime.variables.floatMap.get(v1) + currentRunTime.variables.floatMap.get(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("double") && currentRunTime.variables.GetType(v2).equals("double"))
                {
                    Double result = currentRunTime.variables.doubleMap.get(v1) + currentRunTime.variables.doubleMap.get(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("long") && currentRunTime.variables.GetType(v2).equals("long"))
                {
                    Long result = currentRunTime.variables.longMap.get(v1) + currentRunTime.variables.longMap.get(v2);
                    return result.toString();
                }
            }
            else if(currentRunTime.variables.CheckIfExist(v1) == -2 || currentRunTime.variables.CheckIfExist(v2) == -2)
            {
                if(currentRunTime.variables.CheckIfExist(v1)==-2)
                {
                RunTime.ErrorList.add("Undeclared Identifier "+v1);
                RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                return "error";
                }
                if(currentRunTime.variables.CheckIfExist(v2)==-2)
                {
                    RunTime.ErrorList.add("Undeclared Identifier "+v2);
                    RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                    return "error";
                }
            }
        }
        else if(isAlpha(v1.charAt(0)) && !isAlpha(v2.charAt(0)))
        {
            if(currentRunTime.variables.CheckIfExist(v1) != -2)
            {
                if(currentRunTime.variables.GetType(v1).equals("int"))
                {
                    Integer result = currentRunTime.variables.intMap.get(v1) + Integer.parseInt(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("float"))
                {
                    Float result = currentRunTime.variables.floatMap.get(v1) + Float.parseFloat(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("double"))
                {
                    Double result = currentRunTime.variables.doubleMap.get(v1) + Double.parseDouble(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("long"))
                {
                    Long result = currentRunTime.variables.longMap.get(v1) + Long.parseLong(v2);
                    return result.toString();
                }
            }
            else
            {
                RunTime.ErrorList.add("Undeclared Identifier "+v1);
                RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                return "error";
            }
        }
        else if(!isAlpha(v1.charAt(0)) && isAlpha(v2.charAt(0)))
        {
            if(currentRunTime.variables.CheckIfExist(v2) != -2)
            {
                if(currentRunTime.variables.GetType(v2).equals("int"))
                {
                    Integer result = currentRunTime.variables.intMap.get(v2) + Integer.parseInt(v1);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v2).equals("float"))
                {
                    Float result = currentRunTime.variables.floatMap.get(v2) + Float.parseFloat(v1);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v2).equals("double"))
                {
                    Double result = currentRunTime.variables.doubleMap.get(v2) + Double.parseDouble(v1);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v2).equals("long"))
                {
                    Long result = currentRunTime.variables.longMap.get(v2) + Long.parseLong(v1);
                    return result.toString();
                }
            }
            else
            {
                RunTime.ErrorList.add("Undeclared Identifier "+v2);
                RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                return "error";
            }
        }
        else
        {
            boolean isInteger = false;
            Integer result = 0;
            Float result1 = 0f;
            try
            {
                result = Integer.parseInt(v1) + Integer.parseInt(v2);
                isInteger = true;
            }
            catch(NumberFormatException e)
            {
                result1 = Float.parseFloat(v1) + Float.parseFloat(v2);
                isInteger = false;
            }
            if(isInteger)
                return result.toString();
            else
                return result1.toString();
        }
        return "error";
    }
    static public String ValueMinesValue(String v2,String v1)
    {
        if(isAlpha(v1.charAt(0)) && isAlpha(v2.charAt(0)))
        {
            if(currentRunTime.variables.CheckIfExist(v1) != -2&& currentRunTime.variables.CheckIfExist(v2) != -2)
            {
                if(currentRunTime.variables.GetType(v1).equals("int") && currentRunTime.variables.GetType(v2).equals("int"))
                {
                    Integer result = currentRunTime.variables.intMap.get(v1) - currentRunTime.variables.intMap.get(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("float") && currentRunTime.variables.GetType(v2).equals("float"))
                {
                    Float result = currentRunTime.variables.floatMap.get(v1) - currentRunTime.variables.floatMap.get(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("double") && currentRunTime.variables.GetType(v2).equals("double"))
                {
                    Double result = currentRunTime.variables.doubleMap.get(v1) - currentRunTime.variables.doubleMap.get(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("long") && currentRunTime.variables.GetType(v2).equals("long"))
                {
                    Long result = currentRunTime.variables.longMap.get(v1) - currentRunTime.variables.longMap.get(v2);
                    return result.toString();
                }
            }
            else if(currentRunTime.variables.CheckIfExist(v1) == -2 || currentRunTime.variables.CheckIfExist(v2) == -2)
            {
                if(currentRunTime.variables.CheckIfExist(v1) == -2)
                {
                    RunTime.ErrorList.add("Undeclared Identifier "+v1);
                    RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                    return "error";
                }
                if(currentRunTime.variables.CheckIfExist(v2) == -2)
                {
                    RunTime.ErrorList.add("Undeclared Identifier "+v2);
                    RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                    return "error";
                }
            }
        }
        else if(isAlpha(v1.charAt(0)) && !isAlpha(v2.charAt(0)))
        {
            if(currentRunTime.variables.CheckIfExist(v1) != -2)
            {
                if(currentRunTime.variables.GetType(v1).equals("int"))
                {
                    Integer result = currentRunTime.variables.intMap.get(v1) - Integer.parseInt(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("float"))
                {
                    Float result = currentRunTime.variables.floatMap.get(v1) - Float.parseFloat(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("double"))
                {
                    Double result = currentRunTime.variables.doubleMap.get(v1) - Double.parseDouble(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("long"))
                {
                    Long result = currentRunTime.variables.longMap.get(v1) - Long.parseLong(v2);
                    return result.toString();
                }
            }
            else
            {
                RunTime.ErrorList.add("Undeclared Identifier "+v1);
                RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                return "error";
            }
        }
        else if(!isAlpha(v1.charAt(0)) && isAlpha(v2.charAt(0)))
        {
            if(currentRunTime.variables.CheckIfExist(v2) != -2)
            {
                if(currentRunTime.variables.GetType(v2).equals("int"))
                {
                    Integer result = currentRunTime.variables.intMap.get(v2) - Integer.parseInt(v1);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v2).equals("float"))
                {
                    Float result = currentRunTime.variables.floatMap.get(v2) - Float.parseFloat(v1);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v2).equals("double"))
                {
                    Double result = currentRunTime.variables.doubleMap.get(v2) - Double.parseDouble(v1);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v2).equals("long"))
                {
                    Long result = currentRunTime.variables.longMap.get(v2) - Long.parseLong(v1);
                    return result.toString();
                }
            }
            else
            {
                RunTime.ErrorList.add("Undeclared Identifier "+v2);
                RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                return "error";
            }
        }
        else
        {
            boolean isInteger = false;
            Integer result = 0;
            Float result1 = 0f;
            try
            {
                result = Integer.parseInt(v1) - Integer.parseInt(v2);
                isInteger = true;
            }
            catch(NumberFormatException e)
            {
                result1 = Float.parseFloat(v1) - Float.parseFloat(v2);
                isInteger = false;
            }
            if(isInteger)
                return result.toString();
            else
                return result1.toString();
        }
        return "error";
    }
    static public String ValueDividedValue(String v2,String v1)
    {
        if(isAlpha(v1.charAt(0)) && isAlpha(v2.charAt(0)))
        {
            if(currentRunTime.variables.CheckIfExist(v1) != -2 && currentRunTime.variables.CheckIfExist(v2) != -2)
            {
                if(currentRunTime.variables.GetType(v1).equals("int") && currentRunTime.variables.GetType(v2).equals("int"))
                {
                    Integer result = currentRunTime.variables.intMap.get(v1) / currentRunTime.variables.intMap.get(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("float") && currentRunTime.variables.GetType(v2).equals("float"))
                {
                    Float result = currentRunTime.variables.floatMap.get(v1) / currentRunTime.variables.floatMap.get(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("double") && currentRunTime.variables.GetType(v2).equals("double"))
                {
                    Double result = currentRunTime.variables.doubleMap.get(v1) / currentRunTime.variables.doubleMap.get(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("long") && currentRunTime.variables.GetType(v2).equals("long"))
                {
                    Long result = currentRunTime.variables.longMap.get(v1) / currentRunTime.variables.longMap.get(v2);
                    return result.toString();
                }
            }
            else if(currentRunTime.variables.CheckIfExist(v1) == -2 || currentRunTime.variables.CheckIfExist(v2) == -2)
            {
                if(currentRunTime.variables.CheckIfExist(v1) == -2)
                {
                    RunTime.ErrorList.add("Undeclared Identifier "+v1);
                    RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                    return "error";
                }
                if(currentRunTime.variables.CheckIfExist(v2) == -2)
                {
                    RunTime.ErrorList.add("Undeclared Identifier "+v2);
                    RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                    return "error";
                }
            }
        }
        else if(isAlpha(v1.charAt(0)) && !isAlpha(v2.charAt(0)))
        {
            if(currentRunTime.variables.CheckIfExist(v1) != -2)
            {
                if(currentRunTime.variables.GetType(v1).equals("int"))
                {
                    Integer result = currentRunTime.variables.intMap.get(v1) / Integer.parseInt(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("float"))
                {
                    Float result = currentRunTime.variables.floatMap.get(v1) / Float.parseFloat(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("double"))
                {
                    Double result = currentRunTime.variables.doubleMap.get(v1) / Double.parseDouble(v2);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v1).equals("long"))
                {
                    Long result = currentRunTime.variables.longMap.get(v1) / Long.parseLong(v2);
                    return result.toString();
                }
            }
            else
            {
                RunTime.ErrorList.add("Undeclared Identifier "+v1);
                RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                return "error";
            }
        }
        else if(!isAlpha(v1.charAt(0)) && isAlpha(v2.charAt(0)))
        {
            if(currentRunTime.variables.CheckIfExist(v2) != -2)
            {
                if(currentRunTime.variables.GetType(v2).equals("int"))
                {
                    Integer result = currentRunTime.variables.intMap.get(v2) / Integer.parseInt(v1);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v2).equals("float"))
                {
                    Float result = currentRunTime.variables.floatMap.get(v2) / Float.parseFloat(v1);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v2).equals("double"))
                {
                    Double result = currentRunTime.variables.doubleMap.get(v2) / Double.parseDouble(v1);
                    return result.toString();
                }
                else if(currentRunTime.variables.GetType(v2).equals("long"))
                {
                    Long result = currentRunTime.variables.longMap.get(v2) / Long.parseLong(v1);
                    return result.toString();
                }
            }
            else
            {
                RunTime.ErrorList.add("Undeclared Identifier "+v2);
                RunTime.ErrorNumberLine.add(RunTime.NumberLine);
                return "error";
            }
        }
        else
        {
            Float result1 = Float.parseFloat(v1) / Float.parseFloat(v2);
            return result1.toString();
        }
        return "error";
    }
}
