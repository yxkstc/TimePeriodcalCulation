package com.yk.business;

import com.yk.JavaBean.XmlTableModel;
import com.yk.JavaBean.execlBean;
import com.yk.JavaBean.Column;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class BusinessLogic {
    /**
     * 查询表格
     *
     * @param table
     */
    public static DefaultTableModel queryTableModel(List table) {
        Object[][] data=null;
        List<execlBean> list=table;
        String head[]=new String[]{"公司","用户名","流程名称","流程编码","审核节点","开始时间","结束时间","超时小时(小时)"};
        data=new Object[list.size()][9];
        for(int i=0;i<list.size();i++){
            data[i][0]=list.get(i).getCompany();
            data[i][1]=list.get(i).getUsername();
            data[i][2]=list.get(i).getProcessname();
            data[i][3]=list.get(i).getProcesscoding();
            data[i][4]=list.get(i).getAuditnode();
            data[i][5]=list.get(i).getStarttime();
            data[i][6]=list.get(i).getEndtime();
            data[i][7]=list.get(i).getOvertime();
        }
        DefaultTableModel tableModel=new DefaultTableModel(data,head);
        return tableModel;
    }

    /**
     * 将表格的数据模型保存到xml文件当中
     *
     * @param model
     * @param file
     */
    public void saveModel(TableModel model, String file) {
        Document doc = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement("table");
        doc.setRootElement(root);
        Element columns = DocumentHelper.createElement("columns");
        for (int i = 0; i < model.getColumnCount(); i++) {
            Element column = DocumentHelper.createElement("column");
            column.addAttribute("name", model.getColumnName(i));
            column.addAttribute("type", model.getColumnClass(i).getName());
            columns.add(column);
        }
        root.add(columns);
        for (int i = 0; i < model.getRowCount(); i++) {
            Element tr = DocumentHelper.createElement("tr");
            for (int j = 0; j < model.getColumnCount(); j++) {
                Element td = DocumentHelper.createElement("td");
                td.setText(model.getValueAt(i, j).toString());
                tr.add(td);
            }
            root.add(tr);
        }
        saveDocument(doc, file);
    }

    /**
     * 保存xml Dom到指定的文件当中
     *
     * @param doc
     * @param name
     */
    public void saveDocument(Document doc, String name) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        try {
            XMLWriter writer = new XMLWriter(format);
            FileOutputStream fos = new FileOutputStream(name);
            writer.setOutputStream(fos);
            writer.write(doc);
            fos.close();
        } catch (UnsupportedEncodingException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析指定的xml文件，生成表格数据模型
     *
     * @param name
     *            xml文件路径名称
     * @return XmlTableModel
     */
    public XmlTableModel getModel(String name) {
        Document doc = getDocument(name);
        List<Column> columns = getComumns(doc);
        Vector<Object[]> vector = getRows(doc, columns);
        Object[] names = columns.toArray(new Column[columns.size()]);
        return new XmlTableModel(names, vector);

    }

    /**
     * 解析指定的xml文件，返回其文档对象
     *
     * @param file
     * @return
     */
    public Document getDocument(String file) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new File(file));
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return document;
    }

    /**
     * 解析xml Dom中的列
     *
     * @param doc
     * @return
     */
    private List<Column> getComumns(Document doc) {
        ArrayList<Column> list = new ArrayList<Column>();
        Element root = doc.getRootElement();
        Iterator<Element> el = root.element("columns")
                .elementIterator("column");
        while (el.hasNext()) {
            Element e = (Element) el.next();
            Column c = new Column(e.attributeValue("name"), e
                    .attributeValue("type"));
            list.add(c);
        }
        return list;
    }

    /**
     * 解析xml Dom中的行
     *
     * @param doc
     * @param columns
     * @return
     */
    private Vector<Object[]> getRows(Document doc, List<Column> columns) {
        Vector<Object[]> vector = new Vector<Object[]>();
        Element root = doc.getRootElement();
        List<Node> trList = root.elements("tr");
        for (int i = 0; i < trList.size(); i++) {
            Element tr = (Element) trList.get(i);
            List<Object> list2 = new ArrayList<Object>();
            List<Node> tdList = tr.elements("td");
            for (int j = 0; j < tdList.size(); j++) {
                Element td = (Element) tdList.get(j);
                list2
                        .add(getTDValue(columns.get(j).getType(), td
                                .getTextTrim()));
            }
            vector.add(list2.toArray(new Object[list2.size()]));
        }
        return vector;
    }

    /**
     * 根据列定义的类型，对单元格中的数据进行类型转换
     *
     * @param type
     * @param value
     * @return
     */
    private Object getTDValue(String type, String value) {
        if ("java.lang.Integer".equals(type)) {
            return Integer.parseInt(value);
        }
        if ("java.lang.Boolean".equals(type)) {
            return Boolean.parseBoolean(value);
        }
        return value;
    }

    /**
     * 根据开始，结束时间计算两个时间段相差多少小时
     *
     * @param nowDate
     * @param endDate
     * @return
     */
    public  long getDatePoor(Date nowDate, Date endDate) {
        long nh = 1000 * 60 * 60;
        long diff =0;
        //判断开始，结束时间相差多少秒
        if (nowDate.getTime()>endDate.getTime()){
            diff=nowDate.getTime()-endDate.getTime();
        }else {
            diff=endDate.getTime()-nowDate.getTime();
        }

        //相差多少小时
        return diff/nh;
    }

    /**
     * 计算相差时间是否超过48小时，是，返回timecha-48，否，返回0
     *
     * @param timecha
     * @return
     */
    public long getisChaoshi(long timecha){
        return timecha>48?timecha-48:0;
    }
    /**
     * 计算是日期是本年第几天
     *
     * @param time
     * @return
     */
    public int DayNoftheyear(Date time){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(time);
        return gc.get(Calendar.DAY_OF_YEAR);
    }
    /**
     * 计算是假期为多少小时
     *
     * @param nowDate
     * @param endDate
     * @return
     */
    public int vacationTime(Date nowDate, Date endDate){
        //获取系统日期，加工数据为格式XX月XX日
        int kaishi=DayNoftheyear(nowDate);
        int jieshu=DayNoftheyear(endDate);
        int jiari=0;
        int rows=getModel("lib/jiaqibiao.xml").getRowCount();
        //遍历数据,判断当日是否假期
        for (int i=kaishi;i<=jieshu;i++){
            if (getModel("lib/jiaqibiao.xml").getValueAt(i,1).equals("是")){
                jiari++;
            }
        }
        return jiari*24;
    }




}
