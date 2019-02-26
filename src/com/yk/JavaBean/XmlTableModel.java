package com.yk.JavaBean;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class XmlTableModel extends AbstractTableModel {

    private Object[] names;
    private Vector<Object[]> rows;

    public XmlTableModel(Object[] names, Vector<Object[]> rows) {
        this.rows = rows;
        this.names = names;
    }

    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return names.length;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        return rows.get(row)[col];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public String getColumnName(int column) {
        return names[column].toString();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex >= 0 && columnIndex <= getColumnCount()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        rows.get(rowIndex)[columnIndex] = aValue;
    }
}
