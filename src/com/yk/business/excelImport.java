package com.yk.business;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.yk.JavaBean.execlBean;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * Title: excelTest
 * Description: excel表格读取
 * 注意:引用poi 架包版本要一致
 * 如:
 * poi-4.0.0.jar
 * poi-ooxml-4.0.0.jar
 * poi-ooxml-schemas-4.0.0.jar
 * poi-scratchpad-4.0.0.jar
 * @author pancm
 */
public class excelImport {
    public static  List<execlBean> readExcel(String str) throws FileNotFoundException, IOException{
        File file=new File(str);
        // HSSFWorkbook 2003的excel .xls,XSSFWorkbook导入2007的excel   .xlsx
        // HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(new File(file)));
        InputStream is = new FileInputStream(file);
        HSSFWorkbook workbook=new HSSFWorkbook(is);
        Sheet sheet=workbook.getSheetAt(0);//读取第一个 sheet
        List<execlBean> list= new ArrayList<>();
        Row row=null;
        int rowCount=sheet.getPhysicalNumberOfRows();
        //逐行处理 excel 数据
        for (int i = 2; i <rowCount; i++) {
            row=sheet.getRow(i);
            execlBean eb=new execlBean();
            eb.setCompany(row.getCell(0).toString());
            eb.setUsername(row.getCell(1).toString());
            eb.setProcessname(row.getCell(2).toString());
            eb.setProcesscoding(row.getCell(3).toString());
            eb.setAuditnode(row.getCell(4).toString());
            //execl数字取值为float类型，截取.号之前的值
            eb.setStarttime(row.getCell(5).toString());
            eb.setEndtime(row.getCell(6).toString());
            eb.setOvertime(row.getCell(7).toString());
            list.add(eb);
        }
        workbook.close();

        return list;
    }

    /**
     * 写入Excel表格内容
     * @throws FileNotFoundException
     * @throws IOException
     */
   /* @SuppressWarnings({ "resource", "rawtypes", "unchecked" })
    private static void writeExcel(String str) throws FileNotFoundException, IOException{
        File file=new File(str);
        // HSSFWorkbook 2003的excel .xls,XSSFWorkbook导入2007的excel   .xlsx
//      HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(new File(file)));
        XSSFWorkbook workbook=new XSSFWorkbook(new FileInputStream(file));
        List resultList =new ArrayList<>();

        Sheet sheet1 = workbook.createSheet();//创建 sheet 对象
        Row row = sheet1.createRow(0);//第一行，标题
        row.createCell(0).setCellValue("A");
        row.createCell(1).setCellValue("B");
        row.createCell(2).setCellValue("C");
        row.createCell(3).setCellValue("D");
        row.createCell(4).setCellValue("E");
        //拼接数据
        for(int i=1;i<=10;i++){
            *//*JSONObject json1=new JSONObject();
            json1.put("A", i);
            json1.put("B", i*2);
            json1.put("C", i*3);
            json1.put("D", i*4);
            json1.put("E", i*5);
            resultList.add(json1);*//*
        }
        Row row1;
        for (int i = 1, len = resultList.size(); i <=len; i++) {//循环创建数据行
            //因为第一行已经设置了，所以从第二行开始
          *//*  row1 = sheet1.createRow(i);
            JSONObject json=(JSONObject) resultList.get(i-1);
            row1.createCell(0).setCellValue(json.getString("A"));
            row1.createCell(1).setCellValue(json.getString("B"));
            row1.createCell(2).setCellValue(json.getString("C"));
            row1.createCell(3).setCellValue(json.getString("D"));
            row1.createCell(4).setCellValue(json.getString("E"));*//*
        }
        *//*FileOutputStream fos = new FileOutputStream(path1);
        workbook.write(fos);//写文件
        fos.close();
        System.out.println("写入成功！");*//*
    }*/
}