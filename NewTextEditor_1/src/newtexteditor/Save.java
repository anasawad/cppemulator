/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Save
{
    public String Code = "";
    private Formatter file;
    private Formatter functionFile;
    private File functionsFile;
    static public Vector<Integer> linesNumber = new Vector<Integer>();
    private void SaveCode(String Path,String Code) throws FileNotFoundException, IOException
    {
        this.Code = Code;
        File fileChecker = new File(Path + "\\fullCode.txt");
        if(!fileChecker.exists())
            fileChecker.createNewFile();
        file = new Formatter(fileChecker);
        file.format("%s", Code);
        file.close();
    }
    public String ParseCode(String Path,String statment) throws IOException
    {
        boolean isFirst = true;
        functionsFile = new File(Path +"\\functions.txt");
        if(!functionsFile.exists())
            functionsFile.createNewFile();
        try {
            functionFile = new Formatter(functionsFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        }
        String blocks[] = statment.split("\n|\r");
        linesNumber.clear();
        constructLineNumbers(blocks);
        String line = "";
        for(int i = 0 ; i < blocks.length ; i++)
        {
            line = "";
            for(int j = 0 ; j < blocks[i].length() ; j++)
            {
                if(blocks[i].charAt(j) == ' ')
                    continue;
                line += blocks[i].charAt(j);
                if(line.equals("#include<iostream>"))
                {
                    RunTime.isIOStreamIncluded = true;
                }
                if(line.equals("#include<windows>"))
                {
                    RunTime.isWindowsIncluded = true;
                }
                if(line.equals("#include<math>"))
                {
                    RunTime.isMathIncluded = true;
                }
                if(line.equals("usingnamespacestd;"))
                {
                    RunTime.isNameSpaceSTDIncluded = true;
                }
                if((line.equals("int")||line.equals("float")||line.equals("double")||line.equals("long")|| line.equals("string")
                        ||line.equals("char")||line.equals("bool")||line.equals("void")) && blocks[i].charAt(blocks[i].length()-1) == ')')
                {
                    if(isFirst)
                    {
                        RunTime.NumberLine = i;
                        isFirst = false;
                    }
                    String FunctionCode[] = new String[blocks.length];
                    FunctionCode[0] = blocks[i];
                    String statmentWithoutSpace = "";
                    for(int k = 0 ; k < FunctionCode[0].length() ; k++)
                    {
                        if(FunctionCode[0].charAt(k) == ' ')
                            continue;
                        statmentWithoutSpace += FunctionCode[0].charAt(k);
                    }
                    boolean dataType = false;
                    String returnType = "";
                    boolean functionName = false;
                    String FunctionName = "";
                    for(int k = 0 ; k < statmentWithoutSpace.length() ; k++)
                    {
                        if(dataType == false)
                            returnType += FunctionCode[0].charAt(k);
                        if((returnType.equals("int") || returnType.equals("float") || returnType.equals("long")
                                || returnType.equals("double") || returnType.equals("void")
                                || returnType.equals("char") || returnType.equals("string")
                                || returnType.equals("bool")) && dataType == false)
                        {
                            dataType = true;
                            continue;
                        }
                        if(statmentWithoutSpace.charAt(k) == '(')
                            functionName = true;
                        if(dataType && !functionName)
                            FunctionName += statmentWithoutSpace.charAt(k);
                        if(dataType && functionName)
                            break;
                    }
                    i++;
                    for(int k = 1 ; k < blocks.length ; k++)
                    {
                        i++;
                        if(i == blocks.length)
                            break;
                        if(blocks[i-1].equals("{"))
                            continue;
                        if(blocks[i-1].equals("}"))
                            break;
                        FunctionCode[k] = blocks[i-1];
                    }
                    i--;
                    FunctionCode[FunctionCode.length-1] = "}";
                    File Main = new File(Path + "\\" +FunctionName + ".txt");
                    if(!Main.exists())
                        Main.createNewFile();
                    functionFile.format("%s", FunctionName + "\n");
                    if(!Main.exists())
                        try {
                        Main.createNewFile();
                    } catch (IOException ex) {
                        Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        file = new Formatter(Main);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //file.format("%s", "");
                    for(int k = 0 ; k < FunctionCode.length ; k++)
                        if(FunctionCode[k]!= null && !FunctionCode[k].equals(""))
                            file.format("%s%s", FunctionCode[k],"\n");
                    file.close();
                }
            }
        }
        functionFile.close();
        try {
            SaveCode(Path,statment);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Save.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Completed";
    }
    public void saveProjects(Vector<Projects> v) throws FileNotFoundException
    {
        File projectsFile = new File("ProjectsFile.txt");
        Formatter writer = new Formatter(projectsFile);
        for(int i = 0 ; i < v.size() ; i++)
        {
            writer.format("%s%s", v.get(i).projectName + '\n',v.get(i).projectPath + '\n');
        }
        writer.close();
    }
    private void constructLineNumbers(String block[])
    {
        linesNumber.add(0);
        for(int i = 1 ; i < block.length ; i++)
        {
            int tempLength = block[i-1].length();
                linesNumber.add(tempLength+linesNumber.get(i-1)+1);
        }
    }
}
