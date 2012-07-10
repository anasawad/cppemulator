/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newtexteditor;

import java.awt.Color;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 *
 * @author A.A Ismail
 */
public class LineHighlihgter {
     final Color DEFAULT_COLOR = new Color(230, 230, 210);

public Highlighter.HighlightPainter painter;
public Object highlight;

public LineHighlihgter()
{
this(null);
}

public LineHighlihgter(Color highlightColor)
{
Color c = highlightColor != null ? highlightColor : DEFAULT_COLOR;
painter = new DefaultHighlighter.DefaultHighlightPainter(c);
}
           
    
}
