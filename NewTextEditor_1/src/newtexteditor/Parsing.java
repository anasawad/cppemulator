/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

/**
 *
 * */
abstract public class Parsing
{

    static public String variableType = "";
    static protected String getCurrentVariableType()
    {
        String temp = variableType;
        variableType = "";
        return temp;
    }
    static public void setCurrentVariableType(String type)
    {
        variableType = type;
    }
    abstract public String Parse(String statment);
}
