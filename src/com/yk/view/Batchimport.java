/*
 * Created by JFormDesigner on Wed Nov 21 15:11:30 CST 2018
 */

package com.yk.view;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import com.yk.business.*;


public class Batchimport extends JFrame {


    public Batchimport(){
        initComponents();
        filesTable.setModel(new BusinessLogic().getModel("lib/database.xml"));
    }

    /**
     * @UploadingfilesActionPerformed 上传事件
     */
    private void UploadingfilesActionPerformed(ActionEvent e) throws IOException, ParseException {
        // TODO add your code here
        initUploadingfiles();
        if(textField1.getText().length()>0){
            filesTable.setModel( BusinessLogic.queryTableModel(excelImport.readExcel(textField1.getText())));
        }else {
            return;
        }
    }
    /**
     * @SaveActionPerformed 保存事件
     */
    private void SaveActionPerformed(ActionEvent e) throws IOException {
        // TODO add your code here

            if (filesTable.getRowCount()>0){
                //写入本地xml保存数据
                new BusinessLogic().saveModel(filesTable.getModel(),"lib/database.xml");
                //写入本地'wenjian'生成xls
                new excelImport().writeExcel("wenjian/流程审批超时统计表"+new BusinessLogic().getSystemriqi(new Date())+".xls",filesTable.getModel());
                JOptionPane.showMessageDialog(null, "保存成功");
            }else {
                JOptionPane.showMessageDialog(null, "保存失败", "失败", JOptionPane.ERROR_MESSAGE);

            }
    }

    public void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        Uploadingfiles = new JButton();
        textField1 = new JTextField();
        Save = new JButton();
        scrollPane1 = new JScrollPane();
        filesTable = new JTable();
        chooser=new JFileChooser();

        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths =  new int[] {112, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights =  new int[] {41, 38, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};

        Uploadingfiles.setText("\u9009\u62e9\u4e0a\u4f20\u6587\u4ef6");
        Uploadingfiles.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 20));
        Uploadingfiles.addActionListener(e -> {
            try {
                UploadingfilesActionPerformed(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ParseException e1) {
                e1.printStackTrace( );
            }
        });
        contentPane.add(Uploadingfiles, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        contentPane.add(textField1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));


        Save.setText("计算超时小时并导入到本地'wenjian'目录"); //NON-NLS
        Save.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 20)); //NON-NLS
        Save.addActionListener(e -> {
            try {
                SaveActionPerformed(e);
            } catch (IOException e1) {
                e1.printStackTrace( );
            }
        });
        contentPane.add(Save, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        scrollPane1.setViewportView(filesTable);
        contentPane.add(scrollPane1, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        contentPane.setName("EXECL批量导入");
        pack();
        setLocationRelativeTo(getOwner());
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton Uploadingfiles;
    private JTextField textField1;
    private JButton Save;
    private JScrollPane scrollPane1;
    private JTable filesTable;
    private JFileChooser chooser;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    /**
     * @initUploadingfiles 初始化上传事件，获取选择文件路径
     */
    private void  initUploadingfiles() {
        String path=null;
        chooser.setDialogTitle("请选择要上传的文件...");
        chooser.setApproveButtonText("确定");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(this)) {
            path=chooser.getSelectedFile().getPath();
            textField1.setText(path);
        }
    }
}
