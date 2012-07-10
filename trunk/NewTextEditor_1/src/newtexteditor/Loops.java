/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

import java.util.Vector;

abstract public class Loops
{
    static private Functions function = new Functions();
    static public void setCurrentFunction(Functions currentFunction)
    {
        function = currentFunction;
    }
    public boolean isBreak = false;
    public boolean isContinue = false;
    public void Break()
    {
        isBreak = true;
    }
    public void Continue()
    {
        isContinue = true;
    }
    public boolean isLoopRunning = false;
    public Vector<String> variablesDeclaredWithinLoop = new Vector<String>();
    public Conditions condtitionVariable = new Conditions();
    public ParsingConditions conditions = new ParsingConditions();
    public RunTime currentRunTime = new RunTime();
    public void setCurrentRunTime(RunTime current)
    {
        currentRunTime = current;
    }
    protected boolean isMainLoop = false;
    protected boolean isCalledByLoop()
    {
        return RunTime.calledByLoop;
    }
    protected Vector<String> lastInputVector = new Vector<String>();
    protected void setLastInputVector(Vector<String> lastInput)
    {
        lastInputVector = lastInput;
    }
    protected Vector<String> getlastInputVector()
    {
        return lastInputVector;
    }
    int loopCounter = 0;
    protected boolean isNestedLoop()
    {
        if(loopCounter > 1)
            return true;
        return false;
    }
    protected void loopCounterIncreament()
    {
        loopCounter++;
    }
    protected void loopCounterDecreament()
    {
        loopCounter--;
    }
    protected void setCalledByLoop(boolean called)
    {
        RunTime.calledByLoop = called;
    }
    public void deleteVariablesDeclaredWithinALoop()
    {
        for(int i = 0 ; i < variablesDeclaredWithinLoop.size() ; i++)
        {
            if(currentRunTime.variables.intMap.containsKey(variablesDeclaredWithinLoop.get(i)))
                currentRunTime.variables.intMap.remove(variablesDeclaredWithinLoop.get(i));
            if(currentRunTime.variables.floatMap.containsKey(variablesDeclaredWithinLoop.get(i)))
                currentRunTime.variables.floatMap.remove(variablesDeclaredWithinLoop.get(i));
            if(currentRunTime.variables.longMap.containsKey(variablesDeclaredWithinLoop.get(i)))
                currentRunTime.variables.longMap.remove(variablesDeclaredWithinLoop.get(i));
            if(currentRunTime.variables.doubleMap.containsKey(variablesDeclaredWithinLoop.get(i)))
                currentRunTime.variables.doubleMap.remove(variablesDeclaredWithinLoop.get(i));
            if(currentRunTime.variables.stringMap.containsKey(variablesDeclaredWithinLoop.get(i)))
                currentRunTime.variables.stringMap.remove(variablesDeclaredWithinLoop.get(i));
            if(currentRunTime.variables.charMap.containsKey(variablesDeclaredWithinLoop.get(i)))
                currentRunTime.variables.charMap.remove(variablesDeclaredWithinLoop.get(i));
        }
    }
    abstract public void loopFunction(String statment);
}