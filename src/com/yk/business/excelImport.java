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
        for (int i = 1; i <rowCount; i++) {
            row=sheet.getRow(i);
            execlBean eb=new execlBean();
            eb.setCompany(row.getCell(0).toString());
            eb.setUsername(row.getCell(1).toString());
            eb.setProcessname(row.getCell(2).toString());
            eb.setProcesscoding(row.getCell(3).toString());
            eb.setAuditnode(row.getCell(4).toString());
            eb.setStarttime(row.getCell(5).toString());
            eb.setEndtime(row.getCell(6).toString());
            String overtime=row.getCell(7).toString( ).length()==0?"0":row.getCell(7).toString();
            String subOvertime=overtime.substring(0,overtime.indexOf("."));
            eb.setOvertime(subOvertime);
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
        // HSSFWorkbook 2003的excel .xls,XSSFWorkbook导入2007的excel   .xlsx
        //HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(new File(file)));
        //XSSFWorkbook workbook=new XSSFWorkbook(new FileInputStream(file));
        //InputStream is = new FileInputStream(file);
        HSSFWorkbook workbook=new HSSFWorkbook();//定义输出方式为xls2003
        TableModel savetable =table;//输出数据源（流程超时表）
        TableModel savetable1 =new BusinessLogic().getModel("lib/jiaqibiao.xml");//输出数据源（假期表）
        Sheet sheet1 = workbook.createSheet("晏堃统计("+new BusinessLogic().monthNoftheyear(new Date())+"月)");//创建 sheet1 对象
        Sheet sheet2 = workbook.createSheet("流程超时统计表（原表）");//创建 sheet2 对象
        Sheet sheet3 = workbook.createSheet("假日表");//创建 sheet3 对象
        Row row = sheet1.createRow(0);//创建sheet1第一行标题对象
        Row row2 = sheet2.createRow(0);//创建sheet2第一行标题对象
        Row row4 = sheet3.createRow(0);//创建sheet3第一行标题对象
        //shee1标题赋值
        row.createCell(0).setCellValue(savetable.getColumnName(0));
        row.createCell(1).setCellValue(savetable.getColumnName(1));
        row.createCell(2).setCellValue(savetable.getColumnName(2));
        row.createCell(3).setCellValue(savetable.getColumnName(3));
        row.createCell(4).setCellValue(savetable.getColumnName(4));
        row.createCell(5).setCellValue(savetable.getColumnName(5));
        row.createCell(6).setCellValue(savetable.getColumnName(6));
        row.createCell(7).setCellValue(savetable.getColumnName(7));
        row.createCell(8).setCellValue(savetable.getColumnName(8));
        row.createCell(9).setCellValue(savetable.getColumnName(9));
        row.createCell(10).setCellValue(savetable.getColumnName(10));
        //sheet2标题赋值
        row2.createCell(0).setCellValue(savetable.getColumnName(0));
        row2.createCell(1).setCellValue(savetable.getColumnName(1));
        row2.createCell(2).setCellValue(savetable.getColumnName(2));
        row2.createCell(3).setCellValue(savetable.getColumnName(3));
        row2.createCell(4).setCellValue(savetable.getColumnName(4));
        row2.createCell(5).setCellValue(savetable.getColumnName(5));
        row2.createCell(6).setCellValue(savetable.getColumnName(6));
        row2.createCell(7).setCellValue(savetable.getColumnName(7));
        //sheet3标题赋值
        row4.createCell(0).setCellValue(savetable1.getColumnName(0));
        row4.createCell(1).setCellValue(savetable1.getColumnName(1));

        //数据源（流程超时表）遍历
        for (int i = 0; i < savetable.getRowCount( ); i++) {
            //因为sheet1第一行已经设置了，所以从第二行开始
            row = sheet1.createRow(i + 1);
            //因为sheet2第一行已经设置了，所以从第二行开始
            row2 = sheet2.createRow(i + 1);
            //写入sheet1行数据
            row.createCell(0).setCellValue(savetable.getValueAt(i, 0).toString( ));
            row.createCell(1).setCellValue(savetable.getValueAt(i, 1).toString( ));
            row.createCell(2).setCellValue(savetable.getValueAt(i, 2).toString( ));
            row.createCell(3).setCellValue(savetable.getValueAt(i, 3).toString( ));
            row.createCell(4).setCellValue(savetable.getValueAt(i, 4).toString( ));
            row.createCell(5).setCellValue(savetable.getValueAt(i, 5).toString( ));
            row.createCell(6).setCellValue(savetable.getValueAt(i, 6).toString( ));
            row.createCell(7).setCellValue(savetable.getValueAt(i, 7).toString( ));
            row.createCell(8).setCellValue(savetable.getValueAt(i, 8).toString( ));
            row.createCell(9).setCellValue(savetable.getValueAt(i, 9).toString( ));
            row.createCell(10).setCellValue(savetable.getValueAt(i, 10).toString( ));
            //写入sheet2行数据
            row2.createCell(0).setCellValue(savetable.getValueAt(i, 0).toString( ));
            row2.createCell(1).setCellValue(savetable.getValueAt(i, 1).toString( ));
            row2.createCell(2).setCellValue(savetable.getValueAt(i, 2).toString( ));
            row2.createCell(3).setCellValue(savetable.getValueAt(i, 3).toString( ));
            row2.createCell(4).setCellValue(savetable.getValueAt(i, 4).toString( ));
            row2.createCell(5).setCellValue(savetable.getValueAt(i, 5).toString( ));
            row2.createCell(6).setCellValue(savetable.getValueAt(i, 6).toString( ));
            row2.createCell(7).setCellValue(savetable.getValueAt(i, 7).toString( ));
        }
        //数据源（假期表）遍历
        for (int i = 0; i < savetable1.getRowCount( ); i++) {
            //因为sheet3第一行已经设置了，所以从第二行开始
            row4 = sheet3.createRow(i+1);
            //写入sheet3行数据
            row4.createCell(0).setCellValue(savetable1.getValueAt(i, 0).toString( ));
            row4.createCell(1).setCellValue(savetable1.getValueAt(i, 1).toString( ));

        }
        FileOutputStream fos = new FileOutputStream(str);
        workbook.write(fos);//写文件
        fos.close();
    }
}