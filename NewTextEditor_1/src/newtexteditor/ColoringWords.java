/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newtexteditor;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

/**
 *
 * @author A.A Ismail
 */
public class ColoringWords {
      public void coloringReservedWords(JTextPane mainText ,  String s , int currentOffset , int nextOffset) 
    {
     //   int j = 0 , count = 0;
        //String input = mainText.getText();
        //String []splittingInput = input.split("\r\n");
          StyledDocument doc = mainText.getStyledDocument();
//       int pos;
//       
//        for(int i = 0 ; i < splittingInput.length ;i++)
//        {
//            while (j < i)
//            {
//                count += splittingInput[j].length()+2;
//                j++;
//            }
//            pos  = splittingInput[i].indexOf(s) - count;
//            doc.setCharacterAttributes(pos, s.length(), mainText.getStyle("Blue"), true);
//        }
        int pos = currentOffset;
        doc.setCharacterAttributes(pos, s.length(), mainText.getStyle("Blue"), true);
        
        
    }
    
     public void coloringUnreservedWords(JTextPane mainText, String s , int currentOffset , int nextOffset)
    {
//         int j = 0 , count = 0;
//        String input = mainText.getText();
           //String []splittingInput = input.split("\r\n");
          StyledDocument doc = mainText.getStyledDocument();
//        int pos = 0;
//        for(int i = 0 ; i < splittingInput.length ;i++)
//        {
//            while (j < i)
//            {
//                count += splittingInput[j].length();
//                j++;
//            }
//            pos  = splittingInput[i].indexOf(s) +count;
//            doc.setCharacterAttributes(pos, s.length() , mainText.getStyle("Black"), true);
//            mainText.doLayout();
//        }
          String temp = "";
          for(int i = 0 ; i < (nextOffset-currentOffset)-1;i++)
          {
              temp += mainText.getText().charAt(i+currentOffset);
              if (temp.charAt(i) == '\n' || temp.charAt(i) == '\r')
                  break;
          }
          String []arrTemp = temp.split(" ");
          int pos = temp.length()-arrTemp[0].length();
            doc.setCharacterAttributes(pos, s.length() , mainText.getStyle("Black"), true);
    }
    
}
