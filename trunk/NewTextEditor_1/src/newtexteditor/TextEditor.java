/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TextEditor.java
 *
 * Created on Apr 11, 2011, 2:34:55 AM
 */

package newtexteditor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;


public final class TextEditor extends javax.swing.JFrame implements ActionListener{
    public TextEditor() throws FileNotFoundException {
        initComponents();
        InitializeStyles();
        
    LinePainter lp = new LinePainter(jTextPane1);
        
        this.setTitle("C++ emulator");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
         mainText.setText("Line: " + 1 + " Column: " + 1);
         myTextStyles();
         
         sh.setTextPane(jTextPane1);
        jTextPane1.setEditable(false);
        jButton3.setEnabled(false);
        projects = load.loadCodesOfProjects();
         UpdateProjectList();
        
        cutMenuItem.addActionListener((ActionListener) this);
        copyMenuItem.addActionListener((ActionListener) this);
        pasteMenuItem.addActionListener((ActionListener) this);

        cutpasteMenu.add(cutMenuItem);
        cutpasteMenu.add(copyMenuItem);
        cutpasteMenu.add(pasteMenuItem);

            jTextPane1.addMouseListener(new MouseAdapter() {
            @Override
                public void mousePressed(MouseEvent e)
                {
                    switch(e.getModifiers())
                    {
                        case InputEvent.BUTTON3_MASK:
                        {
                            cutpasteMenu.show(e.getComponent(), e.getX(), e.getY());
                            break;
                        }
                    }
                }
            });
            jTextPane1.addMouseListener(new MouseAdapter() {
            @Override
                public void mousePressed(MouseEvent e) {
                    switch(e.getModifiers()) {
                        case InputEvent.BUTTON3_MASK: {
                            cutpasteMenu.show(e.getComponent(), e.getX(), e.getY());
                            cutpasteMenu.setInvoker(e.getComponent());
                            break;
                        }
                    }
                }
            });
            WindowListener wl = new WindowAdapter()
            {
            @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            };
            addWindowListener(wl);
            pack();
            setResizable(true);
            setVisible(true);
    }
    Load load = new Load();
    public Vector<Projects> projects = new Vector<Projects>();
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        mainText = new java.awt.TextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        runMenu = new javax.swing.JMenu();
        run = new javax.swing.JMenuItem();
        checkError = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTextPane1.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
        jTextPane1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextPane1CaretUpdate(evt);
            }
        });
        jTextPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTextPane1MouseMoved(evt);
            }
        });
        jTextPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextPane1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextPane1KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTextPane1);

        jLabel2.setText("Code :");

        jList2.setSelectionBackground(new java.awt.Color(255, 255, 0));
        jList2.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jList2);

        jButton1.setText("Create new project");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText("Open existing project");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jButton3.setText("Delete project");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jLabel3.setText("Current projects :");

        jList1.setForeground(new java.awt.Color(204, 0, 0));
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Error", jPanel1);

        jTextPane2.setEditable(false);
        jScrollPane4.setViewportView(jTextPane2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Output", jPanel2);

        mainText.setBackground(new java.awt.Color(204, 204, 204));
        mainText.setEditable(false);
        mainText.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        fileMenu.setText("File");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("New project");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem4);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Open project");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem3);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Save");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem2);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem1);

        jMenuBar1.add(fileMenu);

        runMenu.setText("Run");

        run.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, java.awt.event.InputEvent.CTRL_MASK));
        run.setText("Run");
        run.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runActionPerformed(evt);
            }
        });
        runMenu.add(run);

        checkError.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        checkError.setText("Build");
        checkError.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkErrorActionPerformed(evt);
            }
        });
        runMenu.add(checkError);

        jMenuBar1.add(runMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 191, Short.MAX_VALUE)
                        .addComponent(mainText, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(mainText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Error");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    String inputt;
    TextEditorLexer teLexer = new TextEditorLexer();
    SyntaxHiglighter sh = new SyntaxHiglighter();
     
    private void jTextPane1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane1KeyReleased
        if(jList2.getSelectedIndex() != -1)
          projects.get(jList2.getSelectedIndex()).Code = jTextPane1.getText();
          onLexify(); 
    }//GEN-LAST:event_jTextPane1KeyReleased
    private void jTextPane1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane1KeyTyped
        if(evt.getKeyChar() == 9)
        {
            for(int i = 0 ; i < numberOfTabSpaces - 1; i++)
                jTextPane1.replaceSelection(" ");
            jTextPane1.setCaretPosition(position + 1);
            //evt.setKeyChar((char)8);
            jTextPane1.select(position,position + 1);
            jTextPane1.replaceSelection(" ");
            position += numberOfTabSpaces;
            jTextPane1.setCaretPosition(position);
        }
    }//GEN-LAST:event_jTextPane1KeyTyped
    private void runActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runActionPerformed
        RunTime.ErrorList.clear();
        RunTime.ErrorNumberLine.clear();
        RunTime MainRunTime = new RunTime();
        RunTime.isIOStreamIncluded = false;
        RunTime.isMathIncluded = false;
        RunTime.isNameSpaceSTDIncluded = false;
        RunTime.isWindowsIncluded = false;
        Save save = new Save();
        String path = projects.get(jList2.getSelectedIndex()).projectPath;
        String code = jTextPane1.getText();
        try {
            save.ParseCode(path, code);
        } catch (IOException ex) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        File MainFile = new File(path + "\\main.txt");
        if(!MainFile.exists())
           RunTime.ErrorList.add("code doesn't contain main function");
        Console c = new Console();
        IOStream.setConsole(c);
        try {
            MainRunTime.initializeRunTime(MainFile,path);
            MainRunTime.CodeLoop();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        String result = "";
        if(!RunTime.ErrorList.isEmpty())
        {
            DefaultListModel m = new DefaultListModel();
            for(int i = 0 ; i < RunTime.ErrorList.size() ; i++)
                m.addElement(RunTime.ErrorList.get(i) + "                      (Line Number : " + (RunTime.ErrorNumberLine.get(i)+3) + ")");
            jList1.setModel(m);
            result = "Building Failed";
            StyledDocument doc = jTextPane2.getStyledDocument();
            jTextPane2.setText(result);
            doc.setCharacterAttributes(0, result.length(), jTextPane2.getStyle("Red"), true);
        }
        else
        {
            DefaultListModel m = new DefaultListModel();
            for(int i = 0 ; i < RunTime.ErrorList.size() ; i++)
                m.addElement("");
            jList1.setModel(m);
            result = "Building Succeeded";
            StyledDocument doc = jTextPane2.getStyledDocument();
            jTextPane2.setText(result);
            doc.setCharacterAttributes(0, result.length(), jTextPane2.getStyle("Green"), true);
            c.setVisible(true);
        }
    }//GEN-LAST:event_runActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Save save = new Save();
        String code = jTextPane1.getText();
        String path = projects.get(jList2.getSelectedIndex()).projectPath;
        try {
            save.ParseCode(path, code);
        } catch (IOException ex) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        Project p = new Project();
        p.setCurrentMainFrame(this);
        p.setVisible(true);
    }//GEN-LAST:event_jButton1MouseClicked
    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
        DefaultListModel m = new DefaultListModel();
        for(int i = 0 ; i < RunTime.ErrorList.size() ; i++)
        m.addElement("");
        jList1.setModel(m);
        jButton3.setEnabled(true);
        if(jList2.getSelectedIndex() == -1)
            jButton3.setEnabled(false);
        jTextPane1.setEditable(true);
        if(jList2.getSelectedIndex() != -1)
        {
            jLabel2.setText("Code :  " + jList2.getSelectedValue().toString());
            jTextPane1.setText(projects.get(jList2.getSelectedIndex()).Code);
             onLexify();
        }
        else
        {
            jTextPane1.setText("");
            jTextPane1.setEditable(false);
            jLabel2.setText("Code :");
        }
        
        
    }//GEN-LAST:event_jList2ValueChanged
    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        String ProjectPath = "";
        String ProjectName = "";
        String fileName = "";
        JFileChooser fileChooser1 = new JFileChooser();
        fileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser1.setDialogTitle("Save");
        fileChooser1.showOpenDialog(this);
        try
        {
            fileName = fileChooser1.getSelectedFile().getPath();
        }
        catch(NullPointerException e)
        {
            return;
        }
        for(int i = 0 ; i < fileName.length() ; i++)
        {
            if(fileName.charAt(i) == '\\')
            {
                ProjectPath += '\\';
            }
            ProjectPath += fileName.charAt(i);
        }
        String temp1 = "";
        for(int i = ProjectPath.length()-1 ; i >= 0 ; i--)
        {
            if(ProjectPath.charAt(i) == '\\')
                break;
            temp1 += ProjectPath.charAt(i);
        }
        for(int i = temp1.length()-1 ; i >= 0 ; i--)
        {
            ProjectName += temp1.charAt(i);
        }
        File codeFile = new File(ProjectPath + "\\fullCode.txt");
        if(!codeFile.exists())
            return;
        Scanner code = null;
        try {
            code = new Scanner(codeFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        String projectCode = "";
        while(code.hasNext())
        {
            projectCode += code.nextLine() + '\n';
        }
        projects.add(new Projects(ProjectName, ProjectPath, projectCode));
        UpdateProjectList();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        if(jList2.getSelectedIndex() != -1)
        {
            String path = projects.get(jList2.getSelectedIndex()).projectPath;
            DirectoryOperations.Delete(path);
            projects.remove(jList2.getSelectedIndex());
            Save save = new Save();
            try {
                save.saveProjects(projects);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
            UpdateProjectList();
        }
    }//GEN-LAST:event_jButton3MouseClicked

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        Project p = new Project();
        p.setCurrentMainFrame(this);
        p.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        String ProjectPath = "";
        String ProjectName = "";
        String fileName = "";
        JFileChooser fileChooser1 = new JFileChooser();
        fileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser1.setDialogTitle("Save");
        fileChooser1.showOpenDialog(this);
        try
        {
            fileName = fileChooser1.getSelectedFile().getPath();
        }
        catch(NullPointerException e)
        {
            return;
        }
        for(int i = 0 ; i < fileName.length() ; i++)
        {
            if(fileName.charAt(i) == '\\')
            {
                ProjectPath += '\\';
            }
            ProjectPath += fileName.charAt(i);
        }
        String temp1 = "";
        for(int i = ProjectPath.length()-1 ; i >= 0 ; i--)
        {
            if(ProjectPath.charAt(i) == '\\')
                break;
            temp1 += ProjectPath.charAt(i);
        }
        for(int i = temp1.length()-1 ; i >= 0 ; i--)
        {
            ProjectName += temp1.charAt(i);
        }
        File codeFile = new File(ProjectPath + "\\fullCode.txt");
        if(!codeFile.exists())
            return;
        Scanner code = null;
        try {
            code = new Scanner(codeFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        String projectCode = "";
        while(code.hasNext())
        {
            projectCode += code.nextLine() + '\n';
        }
        projects.add(new Projects(ProjectName, ProjectPath, projectCode));
        UpdateProjectList();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void checkErrorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkErrorActionPerformed
        jTextPane1.setFocusable(true);
        RunTime MainRunTime = new RunTime();
        RunTime.isIOStreamIncluded = false;
        RunTime.isMathIncluded = false;
        RunTime.isNameSpaceSTDIncluded = false;
        RunTime.isWindowsIncluded = false;
        Save save = new Save();
        String path = projects.get(jList2.getSelectedIndex()).projectPath;
        String code = jTextPane1.getText();
        try {
            save.ParseCode(path, code);
        } catch (IOException ex) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        File MainFile = new File(path + "\\main.txt");
        if(!MainFile.exists())
           RunTime.ErrorList.add("code doesn't contain main function");
        Console c = new Console();
        IOStream.setConsole(c);
        try {
            MainRunTime.initializeRunTime(MainFile , path);
            MainRunTime.CodeLoop();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        String result = "";
        if(!RunTime.ErrorList.isEmpty())
        {
            DefaultListModel m = new DefaultListModel();
            for(int i = 0 ; i < RunTime.ErrorList.size() ; i++)
                m.addElement(RunTime.ErrorList.get(i) + "                      (Line Number : " + (RunTime.ErrorNumberLine.get(i)+3) + ")");
            jList1.setModel(m);
            RunTime.ErrorList.clear();
            result = "Building Failed";
            StyledDocument doc = jTextPane2.getStyledDocument();
            jTextPane2.setText(result);
            doc.setCharacterAttributes(0, result.length(), jTextPane2.getStyle("Red"), true);
        }
        else
        {
            DefaultListModel m = new DefaultListModel();
            for(int i = 0 ; i < RunTime.ErrorList.size() ; i++)
                m.addElement("");
            jList1.setModel(m);
            result = "Building Succeeded";
            StyledDocument doc = jTextPane2.getStyledDocument();
            jTextPane2.setText(result);
            doc.setCharacterAttributes(0, result.length(), jTextPane2.getStyle("Green"), true);
        }
    }//GEN-LAST:event_checkErrorActionPerformed
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Save save = new Save();
        try {
            save.saveProjects(projects);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        StyledDocument doc = jTextPane1.getStyledDocument();
        if(jList1.getSelectedIndex() == -1) {
            doc.setCharacterAttributes(0, jTextPane1.getText().length() , jTextPane1.getStyle("Black"), true);
            return;
        }
        int y = RunTime.ErrorNumberLine.get(jList1.getSelectedIndex()) + 2;
        doc.setCharacterAttributes(0, jTextPane1.getText().length() , jTextPane1.getStyle("Black"), true);
        doc.setCharacterAttributes(Save.linesNumber.get(y), Save.linesNumber.get(y+1)-Save.linesNumber.get(y), jTextPane1.getStyle("Error"), true);
        y = 0;
}//GEN-LAST:event_jList1ValueChanged

    private void jTextPane1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextPane1CaretUpdate
         try
        {
            int dot = evt.getDot();
            int line = getLineOfOffset(jTextPane1, dot);
            int positionInLine = dot - getLineStartOffset(jTextPane1, line);
            line++;
            positionInLine++;
             mainText.setText("Line: " + line + " Column: " + positionInLine);
        }
        catch (BadLocationException ex) 
        {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextPane1CaretUpdate

    private void jTextPane1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextPane1MouseMoved
        onLexify();
    }//GEN-LAST:event_jTextPane1MouseMoved
   
    /*
     * Show Line and Colmn Number
     */
    //////////////////////////////////////////////////////////////////////////////////////////
    
     static int getLineStartOffset(JTextComponent comp, int line) throws BadLocationException 
    {
        javax.swing.text.Element map = comp.getDocument().getDefaultRootElement();
        if (line < 0) 
        {
            throw new BadLocationException("Negative line", -1);
        }
        else if (line >= map.getElementCount()) 
        {
            throw new BadLocationException("No such line", comp.getDocument().getLength() + 1);
        } 
        else 
        {
            javax.swing.text.Element lineElem = map.getElement(line);
            return lineElem.getStartOffset();
        }
    }

    static int getLineOfOffset(JTextComponent comp, int offset) throws BadLocationException
    {
    
        Document doc = comp.getDocument();
    
        if (offset < 0) 
        {
            throw new BadLocationException("Can't translate offset to line", -1);
        } 
        else if (offset > doc.getLength()) 
        {
            throw new BadLocationException("Can't translate offset to line", doc.getLength() + 1);
        }
        else 
        {
            javax.swing.text.Element map = doc.getDefaultRootElement();
            return map.getElementIndex(offset);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    int numberOfTabSpaces = 7;
    String temp = "";
    String s = "";
    int position = 0;
    final JPopupMenu cutpasteMenu = new JPopupMenu();
    JMenuItem cutMenuItem = new JMenuItem("Cut");
    JMenuItem copyMenuItem = new JMenuItem("Copy");
    JMenuItem pasteMenuItem = new JMenuItem("Paste");
    private void InitializeStyles()
    {
        Style style00 = jTextPane1.addStyle("Error", null);
        StyleConstants.setUnderline(style00, true);
        StyleConstants.setItalic(style00, true);
        StyleConstants.setForeground(style00, Color.red);
        StyleConstants.setBold(style00, true);
        Style style = jTextPane1.addStyle("Blue", null);
        StyleConstants.setForeground(style, Color.blue);
        Style style1 = jTextPane1.addStyle("Red", null);
        StyleConstants.setForeground(style, Color.red);
        Style style11 = jTextPane2.addStyle("Red", null);
        StyleConstants.setForeground(style11, Color.red);
        Style style2 = jTextPane1.addStyle("Black", null);
        StyleConstants.setForeground(style2, Color.BLACK);
        Style style3 = jTextPane1.addStyle("Green", null);
        StyleConstants.setForeground(style3, Color.green);
        Style style33 = jTextPane2.addStyle("Green", null);
        StyleConstants.setForeground(style33, Color.green);
    }
    public void UpdateProjectList()
    {
        DefaultListModel m = new DefaultListModel();
        for(int i = 0 ; i < projects.size() ; i++)
            m.addElement(projects.get(i).projectName);
        jList2.setModel(m);
    }

    public void actionPerformed(ActionEvent evt)
    {
        Object source = evt.getSource();
        if (source == cutMenuItem)
        {
            jTextPane1 = (JTextPane)cutpasteMenu.getInvoker();
            jTextPane1.cut();
        }
        if (source == copyMenuItem) {
            jTextPane1 = (JTextPane)cutpasteMenu.getInvoker();
            jTextPane1.copy();
        }
        if (source == pasteMenuItem) {
            jTextPane1 = (JTextPane)cutpasteMenu.getInvoker();
            jTextPane1.paste();
        }
    }
    public static void main(String args[]) throws FileNotFoundException {
        //try {
            //Thread.sleep(3000);
        //} catch (InterruptedException ex) {
        //    Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        //}
        JFrame f = new JFrame();
        com.sun.awt.AWTUtilities.setWindowOpacity(f, 0.1f);
        //f.setUndecorated(true); //f.getContentPane.setOpaque(false);
                    SplashWindow1 w = new SplashWindow1("starting.gif", f, 10000);
                    try {
                        Thread.sleep(10000);
                        }
                    catch (InterruptedException ex) {
                        Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
                        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                try {
                    new TextEditor().setVisible(true);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
                }

//                try {
//                    new TextEditor().setVisible(true);
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                try {
//                    new TextEditor().setVisible(true);
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
        });
    }
    public void onLexify()
    {
        String input = jTextPane1.getText();
        input.replaceAll("\r\n", " ");
         teLexer.Intialize(input);
            try
            {
                 while(teLexer.hasMoreTokens())
                 {
                     Token t  =teLexer.nextToken();
                     if(t.getTokenType() == TextEditorLexer.TOKEN_KEYWORD)
                         sh.coloringKeywords(t.getOffset(), t.getTokenLexem());
                     else if(t.getTokenType() == TextEditorLexer.TOKEN_STRING_LITERAL 
                             ||t.getTokenType() == TextEditorLexer.TOKEN_CHAR_LITERAL)
                         sh.coloringCharAndStringLiterals(t.getOffset(), t.getTokenLexem());
                     else if(t.getTokenType() == TextEditorLexer.TOKEN_COMMENT)
                         sh.coloringComments(t.getOffset(),t.getTokenLexem());
                     else 
                         sh.coloringIdentfiersAndIntLiterals(t.getOffset(), t.getTokenLexem());
                    //f(t.getTokenType() == TextEditorLexer.TOKEN_IDENTIFIER
                      //      || t.getTokenType() == TextEditorLexer.TOKEN_INT_LITERAL)
                 }
            }
            catch (Exception ex) 
            {
              Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    private  void myTextStyles()
    {
        javax.swing.text.Style keywordStyle = jTextPane1.addStyle("Blue", null);
        StyleConstants.setForeground(keywordStyle, Color.blue);
        javax.swing.text.Style CommentStyle = jTextPane1.addStyle("Green", null);
        StyleConstants.setForeground(CommentStyle, Color.green);
        javax.swing.text.Style LiteralsStyle = jTextPane1.addStyle("Red", null);
        StyleConstants.setForeground(LiteralsStyle, Color.red);
        javax.swing.text.Style style2 = jTextPane1.addStyle("Black", null);
        StyleConstants.setForeground(style2, Color.BLACK);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem checkError;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private java.awt.TextField mainText;
    private javax.swing.JMenuItem run;
    private javax.swing.JMenu runMenu;
    // End of variables declaration//GEN-END:variables

}
