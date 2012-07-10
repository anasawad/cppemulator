/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Load
{
    private File projectsFile;
    private Scanner reader;
    public Vector<Projects> loadExistingProjects() throws FileNotFoundException
    {
        projectsFile = new File("ProjectsFile.txt");
        reader = new Scanner(projectsFile);
        Vector<Projects> existingProjects = new Vector<Projects>();
        while(reader.hasNext())
        {
            existingProjects.add(new Projects(reader.nextLine(), reader.nextLine()));
        }
        return existingProjects;
    }
    public Vector<Projects> loadCodesOfProjects() throws FileNotFoundException
    {
        Vector<Projects> temp = loadExistingProjects();
        for(int i = 0 ; i < temp.size() ; i++)
        {
            projectsFile = new File(temp.get(i).projectPath+"\\fullCode.txt");
            reader = new Scanner(projectsFile);
            String code = "";
            while(reader.hasNext())
            {
                code += reader.nextLine() + '\n';
            }
            temp.get(i).Code = code;
        }
        return temp;
    }
}
