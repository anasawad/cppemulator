/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 */
package newtexteditor;

/**
 *
 * @author A.A Ismail
 */
public class Token {
    private String lexeme;
    private int type;
    private int stateOfAcceptedType;
    private int offset;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    public boolean  Is(String _lexme)
    {
       return  this.lexeme.equals(_lexme);
    }
    public boolean  Is(int _type)
    {
       return  this.type == _type;
    }
    
    public void setTokenLexem(String _lexeme)
    {
        this.lexeme = _lexeme;
    }
    public void setTokenType(int _type)
    {
        this.type = _type;
    }
    public String getTokenLexem()
    {
        return this.lexeme;
    }
    public int getTokenType()
    {
        return this.type;
    }
    public void setStateOfAcceptedType(int _state)
    {
        this.stateOfAcceptedType = _state;
    }
    public int getStateOfAcceptedType()
    {
        return this.stateOfAcceptedType;
    }
            
    
}
