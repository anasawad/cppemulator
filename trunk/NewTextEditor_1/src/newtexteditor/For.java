/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

import java.io.FileNotFoundException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class For extends Loops
{

    public String Statment = "";
    public Integer currentIndex = 0;
    public int currentVectorIndex = 0;
    public String lastCinStatment = "";
    boolean isAddingCinDone = false;
    public String loopVariable = "Unknown";
    @Override
    public void loopFunction(String statment)
    {
        Statment = statment;
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
        String forkey = "";
        String unparsedStatment = "";
        for(int i = 0 ; i < unSpacedStatment.length() ; i++)
        {
            if(forkey.equals("for"))
            {
                unparsedStatment = unSpacedStatment.substring(i);
                break;
            }
            forkey += unSpacedStatment.charAt(i);
        }
        int semicolonCounter = 0;
        for(int i = 0 ; i < unparsedStatment.length() ; i++)
        {
            if(unparsedStatment.charAt(i) == ';')
                semicolonCounter++;
        }
        if(semicolonCounter != 2)
        {
            RunTime.ErrorList.add("invalid loop formate");
            RunTime.ErrorNumberLine.add(RunTime.NumberLine);
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
        }
        if(statmentWithoutSpace.charAt(statmentWithoutSpace.length()-1) != ')')
        {
            RunTime.ErrorList.add("missing bracket \')\'");
            RunTime.ErrorNumberLine.add(RunTime.NumberLine);
            return;
        }
        String variableDeclaration ="";
        String condition = "";
        String iteration = "";
        int counter = 0;
        for(int i = 0 ; i < unparsedStatment.length() ; i++)
        {
            if(unparsedStatment.charAt(i) == ' ')
                continue;
            if(counter == 0)
            {
                while( unparsedStatment.charAt(i) !=  ';')
                {
                    i++;
                    variableDeclaration += unparsedStatment.charAt(i);
                }
                counter++;
            }
            i++;
            if(counter == 1)
            {
                condition += '(';
                while( unparsedStatment.charAt(i) !=  ';')
                {
                    condition += unparsedStatment.charAt(i);
                    i++;
                }
                condition += ')';
                counter++;
            }
            if(counter == 2)
            {
                while( unparsedStatment.charAt(i) !=  ')')
                {
                    i++;
                    if(unparsedStatment.charAt(i) == ' ' || unparsedStatment.charAt(i) == ')')
                        continue;
                    iteration += unparsedStatment.charAt(i);
                }
                break;
            }
        }
        boolean TrueOrFalse = false;
        Vector<String> input = new Vector<String>();
        if(!currentRunTime.variables.intMap.containsKey(loopVariable))
        {
            String variable = currentRunTime.variables.VariableAndExpressionCompiler(variableDeclaration);
            loopVariable = variable;
        }
        if(isAddingCinDone)
        {
            currentVectorIndex++;
        }
        else
        {
            currentVectorIndex = 1;
        }
        currentRunTime.variables.assigningValue(loopVariable + "=" + currentIndex.toString() + ';');
        if(conditions.Parse(condition).equals("true"))
            TrueOrFalse = true;
        else if(conditions.Parse(condition).equals("false"))
        {
            TrueOrFalse = false;
            currentIndex = 0;
            currentRunTime.isReturnedFromCin = false;
        }
        if(!isAddingCinDone)
        {
            loopCounterIncreament();
            if(currentRunTime.isReturnedFromCin)
            {
                input.add(lastCinStatment);
                isAddingCinDone = true;
            }
            if(isCalledByLoop())
                loopCounterIncreament();
            else
                isMainLoop = true;
            if(isNestedLoop())
                input = getlastInputVector();
            setCalledByLoop(true);
            if(input.size() == 0)
            {
                boolean isSpaced = false;
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
                        if(unSpacedStatment.equals("{"))
                            break;
                    }
                }
                if(unSpacedStatment.equals("{"))
                {
                    isSpaced = false;
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
                        try {
                            if(TrueOrFalse)
                                if(!(isBreak || isContinue))
                                    currentRunTime.FunctionCaller(unSpacedStatment);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(For.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        input.add(unSpacedStatment);
                    }
                }
                else if(!unSpacedStatment.equals(""))
                {
                    try {
                        if(TrueOrFalse)
                            if(!(isBreak || isContinue))
                            {
                                currentIndex++;
                                currentRunTime.FunctionCaller(unSpacedStatment);
                            }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(For.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    input.add(unSpacedStatment);
                }
                if(!unSpacedStatment.equals(""))
                {
                    currentRunTime.variables.assigningValue(iteration + ';');
                    if(conditions.Parse(condition).equals("true"))
                        TrueOrFalse = true;
                    else if(conditions.Parse(condition).equals("false"))
                        TrueOrFalse = false;
                }
                setLastInputVector(input);
            }
        }
        else
        {
            input = lastInputVector;
        }
        boolean isIndexIncreamented = true;
        while(TrueOrFalse)
        {
            boolean terminate = false;
            setCalledByLoop(true);
            for(int i = 0 ; i < input.size() ; i++)
            {
                if(isBreak)
                {
                    isBreak = false;
                    terminate = true;
                    break;
                }
                if(isContinue)
                {
                    isContinue = false;
                    break;
                }
                if(currentVectorIndex < input.size())
                {
                    currentVectorIndex++;
                }
                if(isAddingCinDone)
                    i = currentVectorIndex -1;
                try {
                        currentRunTime.FunctionCaller(input.get(i));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(For.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(i == input.size()-1 && isAddingCinDone)
                {
                    currentVectorIndex = 0;
                    if(isIndexIncreamented)
                    {
                        currentIndex++;
                        isIndexIncreamented = false;
                    }
                }
            }
            if(terminate)
            {
                terminate = false;
                break;
            }
            currentRunTime.variables.assigningValue(iteration +';');
            if(conditions.Parse(condition).equals("true"))
                TrueOrFalse = true;
            else if(conditions.Parse(condition).equals("false"))
            {
                TrueOrFalse = false;
            }
        }
        if(!currentRunTime.isReturnedFromCin)
        {
            isAddingCinDone = false;
            currentRunTime.currentLoops.remove(currentRunTime.currentLoops.size()-1);
            if(currentRunTime.currentLoops.size() != 0)
            {
                currentRunTime.variables.currentLoop = currentRunTime.currentLoops.lastElement();
            }
            loopCounterDecreament();
            setCalledByLoop(false);
            deleteVariablesDeclaredWithinALoop();
            isLoopRunning = false;
            if(isMainLoop)
            {
                currentRunTime.loops.clear();
            }
        }
    }
}
