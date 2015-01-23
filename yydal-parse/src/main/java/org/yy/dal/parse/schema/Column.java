package org.yy.dal.parse.schema;

import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.expression.ExpressionVisitor;

/**
 * SQL中的列名， 例如SELECT tableName.columnName FROM tableName
 * @author  zhouliang
 * @version  [0.1, 2015年1月22日]
 * @since  [yydal-parse/0.1]
 */
public final class Column implements Expression, MultiPartName {
    
    /**
     * 表名
     */
    private Table table;
    
    /**
     * 列名
     */
    private String columnName;
    
    public Column() {
    }
    
    public Column(Table table, String columnName) {
        setTable(table);
        setColumnName(columnName);
    }
    
    public Column(String columnName) {
        this(null, columnName);
    }
    
    public Table getTable() {
        return table;
    }
    
    public void setTable(Table table) {
        this.table = table;
    }
    
    public String getColumnName() {
        return columnName;
    }
    
    public void setColumnName(String string) {
        columnName = string;
    }
    
    @Override
    public String getFullyQualifiedName() {
        StringBuilder fqn = new StringBuilder();
        
        if (table != null) {
            fqn.append(table.getFullyQualifiedName());
        }
        if (fqn.length() > 0) {
            fqn.append('.');
        }
        if (columnName != null) {
            fqn.append(columnName);
        }
        return fqn.toString();
    }
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    @Override
    public String toString() {
        return getFullyQualifiedName();
    }
}