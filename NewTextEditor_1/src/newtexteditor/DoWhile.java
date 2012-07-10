/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

import java.io.FileNotFoundException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoWhile extends Loops
{
    @Override
    public void loopFunction(String statment)
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
        String dokey = "";
        String unparsedStatment = "";
        for(int i = 0 ; i < unSpacedStatment.length() ; i++)
        {
            if(dokey.equals("do"))
            {
                unparsedStatment = unSpacedStatment.substring(i);
                break;
            }
            dokey += unSpacedStatment.charAt(i);
        }
        boolean TrueOrFalse = false;
        loopCounterIncreament();
        Vector<String> input = new Vector<String>();
        if(isCalledByLoop())
            loopCounterIncreament();
        if(isNestedLoop())
            input = getlastInputVector();
        setCalledByLoop(true);
        String condition = "";
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
                }
            }
            if(unSpacedStatment.equals("{"))
            {
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
                    if(unSpacedStatment.charAt(0) == '}')
                    {
                        String whilekey = "";
                        unparsedStatment = "";
                        for(int i = 0 ; i < unSpacedStatment.length() ; i++)
                        {
                            if(whilekey.equals("while"))
                            {
                                unparsedStatment = unSpacedStatment.substring(i);
                                break;
                            }
                            if(unSpacedStatment.charAt(i) != '}')
                                whilekey += unSpacedStatment.charAt(i);
                        }
                        for(int i = 0 ; i < unparsedStatment.length() ; i++)
                        {
                            if(unSpacedStatment.charAt(i) == ' ')
                                continue;
                            while( unparsedStatment.charAt(i) !=  ')' && unparsedStatment.charAt(i) != ';')
                            {
                                condition += unparsedStatment.charAt(i);
                                i++;
                            }
                            if(unparsedStatment.charAt(i) != ';')
                                condition += ')';
                        }
                        if(conditions.Parse(condition).equals("true"))
                            TrueOrFalse = true;
                        else if(conditions.Parse(condition).equals("false"))
                            TrueOrFalse = false;
                        continue;
                    }
                    isSpaced = false;
                    try {
                        if(!(isBreak || isContinue))
                            currentRunTime.FunctionCaller(unSpacedStatment);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(DoWhile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    input.add(unSpacedStatment);
                }
            }
            if(!unSpacedStatment.equals(""))
            {
                if(conditions.Parse(condition).equals("true"))
                    TrueOrFalse = true;
                else if(conditions.Parse(condition).equals("false"))
                    TrueOrFalse = false;
            }
        }
        while(TrueOrFalse)
        {
            boolean terminate = false;
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
                try {
                    currentRunTime.FunctionCaller(input.get(i));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(DoWhile.class.getName()).log(Level.SEVERE, null, ex);
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
        currentRunTime.currentLoops.remove(currentRunTime.currentLoops.size()-1);
        if(currentRunTime.currentLoops.size() != 0)
        {
            currentRunTime.variables.currentLoop = currentRunTime.currentLoops.lastElement();
        }
        setLastInputVector(input);
        loopCounterDecreament();
        setCalledByLoop(false);
        isLoopRunning = false;
        deleteVariablesDeclaredWithinALoop();
        if(isMainLoop)
        {
            currentRunTime.loops.clear();
        }
    }
}
