/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendle.courses.wp.finalexam_wp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author lendle
 */
public class TaskFrame extends JInternalFrame {

    private JTextField textTitle = new JTextField();
    private JTextArea textContent = new JTextArea();
    private boolean modified = false;

    public TaskFrame() {
        this.setSize(500, 500);
        //Q4: layout 出如圖所示的樣子，
        //記得 JTextArea 要放在捲軸裡面 (30%)
        
        JLabel lable = new JLabel("Title:");
        textTitle.setColumns(10);
        lable.setLabelFor(textTitle);        
        this.add(lable, "North");

        //scroll
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(textContent);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(textContent,"Center");


        ////////////////////////////
        this.setClosable(true);
        this.setResizable(true);
        this.setVisible(true);

        JPanel southPanel = new JPanel();
        this.add(southPanel, "South");
        JButton saveButton = new JButton("Save");
        southPanel.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TaskDB.save(getNoteTitle(), getNoteContent());
                modified = false;
                setTitle("");
            }
        });

        KeyListener keyListener = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                modified = true;
                setTitle("*");
            }

        };
        textContent.addKeyListener(keyListener);
        this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {

                if (modified) {
                    //Q5: 發現變更，顯示 confirm dialog 詢問是否要儲存 (20%)
                    JDialog confirm = new JDialog();
                    confirm.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    confirm.setVisible(true);
                    int ret = JOptionPane.showConfirmDialog(this, "是否要儲存?", JOptionPane.YES_NO_CANCEL_OPTION);
                    //int result = JOptionPane.showConfirmDialog(this, "是否要儲存?",JOptionPane.YES_NO_CANCEL_OPTION);
                    /////////////////////////////////////////////
                    if (ret == JOptionPane.YES_OPTION) {
                        TaskDB.save(getNoteTitle(), getNoteContent());
                        modified = false;
                        setTitle("");
                    }
                }
            }

        });
    }

    public String getNoteTitle() {
        return textTitle.getText();
    }

    public void setNoteTitle(String title) {
        this.textTitle.setText(title);
    }

    public String getNoteContent() {
        return textContent.getText();
    }

    public void setNoteContent(String content) {
        this.textContent.setText(content);
    }
}
