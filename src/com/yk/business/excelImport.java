package com.yk.business;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yk.JavaBean.execlBean;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import javax.swing.table.TableModel;

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
    public static  List<execlBean> readExcel(String str) throws FileNotFoundException, IOException, ParseException {
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
            eb.setStarttime(row.getCell(5).toString());
            eb.setEndtime(row.getCell(6).toString());
            eb.setOvertime(row.getCell(7).toString());
            //5,6依次为开始，结束时间
            if(row.getCell(5).toString().length()>0&&row.getCell(6).toString().length()>0){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date kaishi=sdf.parse(row.getCell(5).toString());
                Date jieshu=sdf.parse(row.getCell(6).toString());
                eb.setSystem_shijiancha(String.valueOf(new BusinessLogic().getDatePoor(kaishi,jieshu)));
                //获得相差时间
                long time=Long.valueOf(eb.getSystem_shijiancha());
                //计算超时时间，可能含假期
                long chaoshi=new BusinessLogic().getisChaoshi(time);
                if (chaoshi>0){
                    //获取假日时间
                    int jiari=new BusinessLogic().getVacationTime(kaishi,jieshu);
                    long result=chaoshi-jiari;
                    eb.setSystem_chaoshi(String.valueOf(result>0?result:0));
                    if (result>0){
                        eb.setSystem_sfchaoshi("已超时");
                    }else {
                        eb.setSystem_sfchaoshi("未超时");
                    }
                }else {
                    //开始结束相差不超时2天的
                    eb.setSystem_chaoshi("0");
                    eb.setSystem_sfchaoshi("未超时");
                }

            }else {
                //开始时间和结束时间任意一个字段为空的不计算是否超时
                eb.setSystem_shijiancha("0");
                eb.setSystem_chaoshi("0");
                eb.setSystem_sfchaoshi("不在计算范围");
            }
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
    @SuppressWarnings({ "resource", "rawtypes", "unchecked" })
    public  void writeExcel(String str,TableModel table) throws FileNotFoundException, IOException{
        //File file=new File(str);
        // HSSFWorkbook 2003的excel .xls,XSSFWorkbook导入2007的excel   .xlsx
        //HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(new File(file)));
        //XSSFWorkbook workbook=new XSSFWorkbook(new FileInputStream(file));
        //InputStream is = new FileInputStream(file);
        HSSFWorkbook workbook=new HSSFWorkbook();
        TableModel savetable =table;

        Sheet sheet1 = workbook.createSheet();//创建 sheet 对象
        Row row = sheet1.createRow(0);//第一行，标题
        row.createCell(0).setCellValue("公司");
        row.createCell(1).setCellValue("用户名");
        row.createCell(2).setCellValue("流程名称");
        row.createCell(3).setCellValue("流程编码");
        row.createCell(4).setCellValue("审核节点");
        row.createCell(5).setCellValue("开始时间");
        row.createCell(6).setCellValue("结束时间");
        row.createCell(7).setCellValue("超时时间(小时)");
        row.createCell(8).setCellValue("时间差（系统）");
        row.createCell(9).setCellValue("超时小时（系统）");
        row.createCell(10).setCellValue("是否超时（系统）");

        Row row1;
        for (int i = 1;i <savetable.getRowCount(); i++) {//循环创建数据行
            //因为第一行已经设置了，所以从第二行开始
            row1 = sheet1.createRow(i);
            row1.createCell(0).setCellValue(savetable.getValueAt(i,0).toString());
            row1.createCell(1).setCellValue(savetable.getValueAt(i,1).toString());
            row1.createCell(2).setCellValue(savetable.getValueAt(i,2).toString());
            row1.createCell(3).setCellValue(savetable.getValueAt(i,3).toString());
            row1.createCell(4).setCellValue(savetable.getValueAt(i,4).toString());
            row1.createCell(5).setCellValue(savetable.getValueAt(i,5).toString());
            row1.createCell(6).setCellValue(savetable.getValueAt(i,6).toString());
            row1.createCell(7).setCellValue(savetable.getValueAt(i,7).toString());
            row1.createCell(8).setCellValue(savetable.getValueAt(i,8).toString());
            row1.createCell(9).setCellValue(savetable.getValueAt(i,9).toString());
            row1.createCell(10).setCellValue(savetable.getValueAt(i,10).toString());
        }
        FileOutputStream fos = new FileOutputStream(str);
        workbook.write(fos);//写文件
        fos.close();
    }
}