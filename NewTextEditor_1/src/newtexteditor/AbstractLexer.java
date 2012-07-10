/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newtexteditor;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author A.A Ismail
 */
public abstract class AbstractLexer {
    public static final int  STATE_READY = -1;
    public static final int STATE_ACCEPT = -2;
    public static final int STATE_FAIL = -3;
    public static final int TOKEN_SKIP = -1;
    public static final int TOKEN_EOF = -2;
    public static final int EOF = -1;
    
    
    
    
   // Map<Integer , Vector<Token> >allTokens;
    //Vector <Token>allTokenPerLine;
    //Vector<Token> tempTokens;
    //Vector< Vector < Token> > allTokens = new Vector<Vector<Token>>();
    String _input;
    int _pointer1 , _pointer2;
    int _state;
    int _lineNumber;

    public int getLineNumber() {
        return _lineNumber;
    }
  
    public AbstractLexer()
    {
       // allTokens = new HashMap<Integer, Token>();
       // allTokenPerLine = new Vector<Token>();
        //allTokens = new HashMap<Integer, Vector<Token> >();
       //tempTokens = new Vector<Token>();
    }
    protected abstract Token switchState(char c , int _state , int typeToAccept);
    
     public Boolean isLetter(char c)
      {
        return (c >='a' && c <= 'z') ||
                (c >='A' && c <= 'Z')||
                (c == '_');
      }
      public Boolean isDigit(char c)
      {
            return (c >= '0' && c <= '9');
      }

      public void Intialize(String input)
      {
          this._lineNumber = 0;
         // allTokenPerLine.clear();
         //allTokens.clear();
          this._input = input;
          this._state = STATE_READY;
          this._pointer1 = this._pointer2 = 0;
      }
      
      public Token nextToken() throws Exception 
        {
            char c ;
            Token returnedToken = new Token();
            Token t = new Token();
            int typeToAccept = 0;
            while(this._pointer2 < this._input.length())
            {
               c = read(); 
               returnedToken = switchState(c,this._state, typeToAccept);
               typeToAccept = returnedToken.getTokenType();
               this._state = returnedToken.getStateOfAcceptedType();
               if(this._state == STATE_ACCEPT)
               {
                   t = accept(typeToAccept);
                   if(t.getTokenType() == TextEditorLexer.TOKEN_IDENTIFIER)
                   {
                      if (t.getTokenLexem().equals("int") || t.getTokenLexem().equals("float")
                              ||t.getTokenLexem().equals("double")||t.getTokenLexem().equals("long")
                              || t.getTokenLexem().equals("return")||t.getTokenLexem().equals("using")
                              ||t.getTokenLexem().equals("namespace")||t.getTokenLexem().equals("true")
                              ||t.getTokenLexem().equals("false")|| t.getTokenLexem().equals("bool")
                              ||t.getTokenLexem().equals("void") || t.getTokenLexem().equals("for")
                              ||t.getTokenLexem().equals("while") || t.getTokenLexem().equals("do")
                              ||t.getTokenLexem().equals("if") || t.getTokenLexem().equals("include")
                              ||t.getTokenLexem().equals("else"))
                      {
                           t.setTokenType(TextEditorLexer.TOKEN_KEYWORD);
                      }
                   }
                   if(typeToAccept != TOKEN_SKIP)
                   { 
                       int temp = this._pointer1-t.getTokenLexem().length();
                       t.setOffset(temp);
                       return t;
                   
                   }
               }
            }  
            if(this._state == STATE_READY)
            {
                t = new Token();
                t.setTokenLexem("");
                t.setTokenType(EOF);        
                return t;
            }
             else
            {
                throw new Exception("End of input reached");
            }

     }

      public char read()
      {
        char c =  this._input.charAt(this._pointer2);
        this._pointer2++;
        return c;
      }

      public void retract()
      {
        this._pointer2--;
      }
      public boolean  hasMoreTokens()
      {
        return this._pointer1 <= this._input.length()-1;
      }
      
    
    public void reset()
    {
        this._pointer1 = this._pointer2;
    }
    
    public Token accept(int _type)
    {
        String lexeme = this._input.substring(this._pointer1, this._pointer2);//- this._pointer1);
        this._pointer1 = this._pointer2;
        Token temp = new Token();
        temp.setTokenLexem(lexeme);
        temp.setTokenType(_type);
        this._state = STATE_READY;
        return temp;
    }
      
}
