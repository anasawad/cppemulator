package newtexteditor;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Anas
 */
public class SyntaxHiglighter {
    
    TextEditorLexer telexer;
    JTextPane text;
    SyntaxHiglighter()
    {
    }

    public void setTextPane(JTextPane text) {
        this.text = text;
    }
    
    
    public void coloringKeywords(int offset , String s )
    {
         StyledDocument doc = this.text.getStyledDocument();
         doc.setCharacterAttributes(offset, s.length(), this.text.getStyle("Blue"), true);
    }
    public void coloringComments(int offset , String s)
    {
         StyledDocument doc = this.text.getStyledDocument();
         doc.setCharacterAttributes(offset, s.length(), this.text.getStyle("Green"), true);
    }
     public void coloringCharAndStringLiterals(int offset , String s)
    {
         StyledDocument doc = this.text.getStyledDocument();
         doc.setCharacterAttributes(offset, s.length(), this.text.getStyle("Red"), true);
    }
     public void coloringIdentfiersAndIntLiterals(int offset , String s)
    {
         StyledDocument doc = this.text.getStyledDocument();
         doc.setCharacterAttributes(offset, s.length(), this.text.getStyle("Black"), true);
    }
  
}
