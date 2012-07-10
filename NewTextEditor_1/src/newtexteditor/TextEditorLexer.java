/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newtexteditor;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author A.A Ismail
 */
public class TextEditorLexer extends AbstractLexer {
        
    public static final int TOKEN_IDENTIFIER = 0;
    public static final int TOKEN_KEYWORD = 1;
    public static final int TOKEN_STRING_LITERAL = 2;
    public static final int TOKEN_CHAR_LITERAL = 3;
    public static final int TOKEN_SEMICOLON  = 4;
    public static final int TOKEN_LPAREN = 5;
    public static final int TOKEN_RPAREN  = 6;
    public static final int TOKEN_LBRACE = 7;
    public static final int TOKEN_RBRACE = 8;
    public static final int TOKEN_COMMA = 9;
    public static final int TOKEN_ASSIGN_OP = 10;
    public static final int TOKEN_GREATER_THAN = 11;
    public static final int TOKEN_LESS_THAN = 12;
    public static final int TOKEN_ARITHMATIC_OP = 13;
    public static final int TOKEN_LOGICAL_OP = 14;
    public static final int TOKEN_INT_LITERAL = 15;
    public static final int TOKEN_LBRACKET = 16;
    public static final int TOKEN_RBRACKET = 17;
    public static final int TOKEN_COMMENT = 18;
  
    public TextEditorLexer()
    {
        super();
    }

    @Override
    protected Token switchState(char c, int _state, int typeToAccept) {
        Token t = new Token();
        try
        {
           switch(_state)
           {
               case STATE_READY:
                  t = doStateReady(c, t.getStateOfAcceptedType(), t.getTokenType());
                   break;
               case 1:
                  t =  doState1(c, t.getStateOfAcceptedType(), t.getTokenType());
                   break;
               case 2:
                  t = doState2(c,t.getStateOfAcceptedType(), t.getTokenType());
                   break;
               case 3:
                  t = doState3(c, t.getStateOfAcceptedType(), t.getTokenType());
                   break;
               case 4:
                  t = doState4(c,t.getStateOfAcceptedType(), t.getTokenType());
                   break;
               case 5:
                  t = doState5(c,t.getStateOfAcceptedType(), t.getTokenType());
                   break;
               case 6:
                  t = doState6(c,t.getStateOfAcceptedType(), t.getTokenType());
                   break;
               case 7:
                    t = doState7(c,t.getStateOfAcceptedType(), t.getTokenType());
                     break;
                   default:
                       throw new Exception("UnKnown State");
           }
        }
        catch (Exception ex) 
        {
            Logger.getLogger(TextEditorLexer.class.getName()).log(Level.SEVERE, null, ex);
        }
           return t;
    }
    public Token doStateReady(char c , int _state , int typeToAccept)
    {
        Token t = new Token();
        if(isLetter(c))
        {
            // for TOKEN_IDENTIFIERS
           _state = 1;      
        }
        else if(c == '"')
        {
            //YOKEN_STRING_LITERAL
            _state =  2;
        }
        else if(c =='\'')
        {
            //YOKEN_CHAR_LITERAL
            _state = 3;
        }
        else if(c == '/')
        {
            //TOKEN_COMMENT
            _state  =4;
        }
        else if(c == '|')
        {
            //TOKEN_LOGICAL_OPERATOR
            _state = 5;
        }
        else if(c == '&')
        {
            //TOKEN_LOGICAL_OPERATOR
            _state = 6;
        }
        else if(c == '!')
        {
            _state = STATE_ACCEPT;
            typeToAccept = TOKEN_LOGICAL_OP;
        }
        else if(isDigit(c))
        {
            //TOKEN_INT_LITERAL
            _state = 7;
        }
        else if(c == '+' ||c == '-' ||c == '*' || c == '%')
        {
            _state = STATE_ACCEPT;
            typeToAccept = TOKEN_ARITHMATIC_OP;
        }
        else if(c == '=')
        {
            _state = STATE_ACCEPT;
            typeToAccept = TOKEN_ASSIGN_OP;
        }
        else if(c == '>')
        {
            _state = STATE_ACCEPT;
            typeToAccept = TOKEN_GREATER_THAN;
        }
        else if(c == '<')
        {
            _state = STATE_ACCEPT;
            typeToAccept = TOKEN_LESS_THAN;
        }     
        else if(c == ' ' || c == '\n' || c == '\t' || c== '\r' ||
                c == '#' || c == '.' || c == ':' || c == '?' || c == '@')
        {
             //Skippen Chars
           _state = STATE_ACCEPT;
           typeToAccept = TOKEN_SKIP;
        }
        else if(c == ';')
        {
            _state = STATE_ACCEPT;
            typeToAccept = TOKEN_SEMICOLON;
        }
        else if(c == '{')
        {
            _state = STATE_ACCEPT;
            typeToAccept = TOKEN_LBRACE;
        }
        else if(c == '}')
        {
            _state = STATE_ACCEPT;
            typeToAccept = TOKEN_RBRACE;
        }
        else if(c == '(')
        {
            _state = STATE_ACCEPT;
            typeToAccept = TOKEN_LPAREN;
        }
        else if(c == ')')
        {
            _state = STATE_ACCEPT;
            typeToAccept = TOKEN_RPAREN;
        }
        else if(c == ',')
        {
            _state = STATE_ACCEPT;
            typeToAccept = TOKEN_COMMA;
        }
        else if(c == '[')
        {
            _state = STATE_ACCEPT;
            typeToAccept = TOKEN_LBRACKET;
        }
        else if(c == ']')
        {
            _state = STATE_ACCEPT;
            typeToAccept = TOKEN_RBRACKET;
        }
        t.setStateOfAcceptedType(_state);
        t.setTokenType(typeToAccept);
        return t;
    }

    /* 
     * Identifiers' State
     */
     public Token doState1(char c , int _state , int typeToAccept)
     {
         Token t = new Token();
         if(isDigit(c) || isLetter(c))
         {
             _state = 1;
             t.setStateOfAcceptedType(_state);
         }
         else
         {
             this.retract();
             _state = STATE_ACCEPT;
             typeToAccept = TOKEN_IDENTIFIER;
             t.setStateOfAcceptedType(_state);
             t.setTokenType(typeToAccept);
         }
         return t;
     }
     /*
      * String literals' state
      */
     public Token doState2(char c , int _state , int typeToAccept) throws Exception
     {
         Token t = new Token();
         if(c == '"')
         {
             if( typeToAccept == TOKEN_STRING_LITERAL)
             {
                 _state = STATE_ACCEPT;
                 typeToAccept = TOKEN_STRING_LITERAL;
             }
             
             t.setStateOfAcceptedType(_state);
             t.setTokenType(typeToAccept);
         }
         else if(c == '\n' || c == '\r')
         {
             throw new Exception("Newline in string literal");
         }
         else
         {
             _state = 2;
             t.setStateOfAcceptedType(_state);
         }
         return t;
     }
     /*
      * Char Literals' State
      */
     public Token doState3(char c , int _state , int typeToAccept) throws Exception
     {
         Token t = new Token();
         if(c == '\'')
         {
             _state = STATE_ACCEPT;
             typeToAccept = TOKEN_CHAR_LITERAL;
             t.setStateOfAcceptedType(_state);
             t.setTokenType(typeToAccept);
         }
         else if(c == '\n' || c == '\r')
         {
             throw new Exception("Newline in string literal");
         }
         else
         {
             _state = 3; 
             t.setStateOfAcceptedType(_state);
         } 
         return t;
     }
   
     /*
      *for comments
      */
     public Token doState4(char c , int _state , int typeToAccept) throws Exception
     {
         Token t = new Token();
         if(c =='/')
         {
             _state = 4;
             t.setStateOfAcceptedType(_state);
         }
         else  // if new line
         {
                   this.retract();
                 _state = STATE_ACCEPT;
                 typeToAccept = TOKEN_COMMENT; // it's for comments
                 t.setStateOfAcceptedType(_state);
                 t.setTokenType(typeToAccept);
             
         }
        return t;
     }
     
     public Token doState5(char c , int _state , int typeToAccept) throws Exception
     {
         Token t = new Token();
         if(c == '|')
         {
             _state = STATE_ACCEPT;
             typeToAccept = TOKEN_LOGICAL_OP;
              t.setStateOfAcceptedType(_state);
             t.setTokenType(typeToAccept);
         }
         else
         {
             throw new Exception("Expected '&' after previous '&'");
         }
         return t;
     }
       public Token doState6(char c , int _state , int typeToAccept) throws Exception
     {
         Token t = new Token();
         if(c == '&')
         {
             _state = STATE_ACCEPT;
             typeToAccept = TOKEN_LOGICAL_OP;
              t.setStateOfAcceptedType(_state);
             t.setTokenType(typeToAccept);
         }
         else
         {
             throw new Exception("Expected '&' after previous '&'");
         }
         return t;
     }
        public Token doState7(char c , int _state , int typeToAccept) 
     {
         Token t = new Token();
         if(isDigit(c))
         {
             _state = 7;
             t.setStateOfAcceptedType(_state);
         }
         else
         {
             this.retract();
             _state = STATE_ACCEPT;
             typeToAccept = TOKEN_INT_LITERAL;
             t.setStateOfAcceptedType(_state);
             t.setTokenType(typeToAccept);
         }
       
         return t;
     }
    
}
