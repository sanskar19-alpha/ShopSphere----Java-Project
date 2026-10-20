package com.shopsphere.swing.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/** MVC "Model" for JTable: holds the rows (entities) and maps each entity to table cells. */
public class GenericTableModel<T> extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private final String[] columns;
    private final transient Function<T, Object[]> rowMapper;
    private transient List<T> rows = new ArrayList<>();

    public GenericTableModel(String[] columns, Function<T, Object[]> rowMapper) {
        this.columns = columns;
        this.rowMapper = rowMapper;
    }

    public void setRows(List<T> newRows) {
        this.rows = new ArrayList<>(newRows);
        fireTableDataChanged();
    }

    public T getAt(int modelRow) { return modelRow >= 0 && modelRow < rows.size() ? rows.get(modelRow) : null; }

    @Override public int getRowCount() { return rows.size(); }
    @Override public int getColumnCount() { return columns.length; }
    @Override public String getColumnName(int c) { return columns[c]; }
    @Override public Object getValueAt(int r, int c) { return rowMapper.apply(rows.get(r))[c]; }
    @Override public boolean isCellEditable(int r, int c) { return false; }
}
