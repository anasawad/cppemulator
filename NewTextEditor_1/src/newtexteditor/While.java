/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

import java.io.FileNotFoundException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


public class While extends Loops
{
    public String Statment;
    public int currentVectorIndex = 0;
    public String lastCinStatment = "";
    boolean isAddingCinDone = false;
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
        String whilekey = "";
        String unparsedStatment = "";
        for(int i = 0 ; i < unSpacedStatment.length() ; i++)
        {
            if(whilekey.equals("while"))
            {
                unparsedStatment = unSpacedStatment.substring(i);
                break;
            }
            whilekey += unSpacedStatment.charAt(i);
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
        String condition = "";
        for(int i = 0 ; i < unparsedStatment.length() ; i++)
        {
            if(unparsedStatment.charAt(i) == ' ')
                continue;
            while( unparsedStatment.charAt(i) !=  ')')
            {
                condition += unparsedStatment.charAt(i);
                i++;
            }
            condition += ')';
        }
        boolean TrueOrFalse = false;
        if(conditions.Parse(condition).equals("true"))
            TrueOrFalse = true;
        else if(conditions.Parse(condition).equals("false"))
            TrueOrFalse = false;
        if(isAddingCinDone)
        {
            currentVectorIndex++;
        }
        else
        {
            currentVectorIndex = 1;
        }
        Vector<String> input = new Vector<String>();
        if(!isAddingCinDone)
        {
            loopCounterIncreament();
            if(isCalledByLoop())
                loopCounterIncreament();
            else
                isMainLoop = true;
            if(currentRunTime.isReturnedFromCin)
            {
                input.add(lastCinStatment);
                isAddingCinDone = true;
            }
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
                            Logger.getLogger(While.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        input.add(unSpacedStatment);
                    }
                }
                else if(!unSpacedStatment.equals(""))
                {
                    try {
                        if(TrueOrFalse)
                            if(!(isBreak || isContinue))
                                currentRunTime.FunctionCaller(unSpacedStatment);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(While.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    input.add(unSpacedStatment);
                }
                if(!unSpacedStatment.equals(""))
                {
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
                    Logger.getLogger(While.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(i == input.size()-1 && isAddingCinDone)
                {
                    currentVectorIndex = 0;
                }
            }
            if(terminate)
            {
                terminate = false;
                break;
            }
            if(conditions.Parse(condition).equals("true"))
                TrueOrFalse = true;
            else if(conditions.Parse(condition).equals("false"))
                TrueOrFalse = false;
        }
        if(!currentRunTime.isReturnedFromCin)
        {
            isAddingCinDone = false;
            currentRunTime.currentLoops.remove(currentRunTime.currentLoops.size()-1);
            if(currentRunTime.currentLoops.size() != 0)
            {
                currentRunTime.variables.currentLoop = currentRunTime.currentLoops.lastElement();
            }
            setLastInputVector(input);
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
