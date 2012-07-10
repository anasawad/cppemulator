/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package newtexteditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Mahmoud
 */
public class DirectoryOperations {

 // *********************** Create Folder & File  ****************************
    static public void Folder_File (String FolderPath) throws IOException
    {

     // ***************************  Create Folder ***************************
        File f = new File(FolderPath);
        try{
            if(f.exists()==false)
            {
                f.mkdir();
           }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
  // ***************************  Create New File ****************************

        File file = new File(FolderPath + "\\\\fullCode.txt");
        file.createNewFile();
    }

// ************************ Copy From ....To ..... ****************************
    static public  void Copy(String From , String To) throws IOException
    {
        File fromFile = new File(From);
        File toFile = new File(To);
        if (!fromFile.exists())
          throw new IOException("FileCopy: " + "no such source file: "
              + From);
        if (!fromFile.isFile())
          throw new IOException("FileCopy: " + "can't copy directory: "
              + From);
        if (!fromFile.canRead())
          throw new IOException("FileCopy: " + "source file is unreadable: "
              + From);

        if (toFile.isDirectory())
          toFile = new File(toFile, fromFile.getName());

        if (toFile.exists())
        {
          if (!toFile.canWrite())
            throw new IOException("FileCopy: " + "destination file is unwriteable: " + To);
          System.out.print("Overwrite existing file " + toFile.getName()+ "? (Y/N): ");
          System.out.flush();
          BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
          String response = in.readLine();
          if (!response.equals("Y") && !response.equals("y")) throw new IOException("FileCopy: " + "existing file was not overwritten.");

        }
        else
        {
          String parent = toFile.getParent();
          if (parent == null)
            parent = System.getProperty("user.dir");
          File dir = new File(parent);
          if (!dir.exists())    throw new IOException("FileCopy: " + "destination directory doesn't exist: " + parent);
          if (dir.isFile())     throw new IOException("FileCopy: " + "destination is not a directory: " + parent);
          if (!dir.canWrite())  throw new IOException("FileCopy: "+ "destination directory is unwriteable: " + parent);
        }

        FileInputStream from = null;
        FileOutputStream to = null;
        try
        {
          from = new FileInputStream(fromFile);
          to = new FileOutputStream(toFile);
          byte[] buffer = new byte[4096];
          int bytesRead;

          while ((bytesRead = from.read(buffer)) != -1)
            to.write(buffer, 0 , bytesRead);
        }
        finally
        {
          if (from != null)
           try
           {
              from.close();
            }
           catch (IOException e)
            {
          System.err.println(e.getMessage());
            }
          if (to != null)
           try
            {
              to.close();
            }
            catch (IOException e)
            {
          System.err.println(e.getMessage());
            }
        }
     }

// ************************ Delete File  ****************************

    static public void Delete(String Path)
    {
        System.out.println("Enterr the Path of the Rquired file to be Deleted : ");
        // A File object to represent the Deletedfile_Name
        File f = new File(Path);
        // Make sure the file or directory exists and isn't write protected
        if (!f.exists())
        {
            System.exit(0);
        }
        if (!f.canWrite())
        {
            System.exit(0);
        }
        // If it is a directory, make sure it is empty
        if (f.isDirectory())
        {
            String[] files = f.list();
            for(int i = 0 ; i < files.length ; i++)
            {
                File temp = new File(Path + "\\" + files[i]);
                temp.setWritable(true);
                temp.delete();
            }
        }
        boolean success = f.delete();

        if (!success)
        {
            System.exit(0);
        }        //   throw new IllegalArgumentException("Delete: deletion failed");
    }
}