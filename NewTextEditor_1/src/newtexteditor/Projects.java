/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

public class Projects
{
    public String projectName;
    public String projectPath;
    public String Code;
    public Projects(String projectName,String projectPath)
    {
        this.projectName = projectName;
        this.projectPath = projectPath;
    }
    public Projects(String projectName,String projectPath,String Code)
    {
        this.projectName = projectName;
        this.projectPath = projectPath;
        this.Code = Code;
    }

}
