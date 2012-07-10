/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserialization
{
    public void ReadFromFile(String filePath,Object o) throws IOException, ClassNotFoundException
    {
        FileInputStream fis   = new FileInputStream(filePath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        o = (Object)ois.readObject();
        ois.close();
    }
}