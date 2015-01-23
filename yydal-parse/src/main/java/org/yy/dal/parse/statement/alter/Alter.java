package org.yy.dal.parse.statement.alter;

import org.yy.dal.parse.schema.Table;
import org.yy.dal.parse.statement.Statement;
import org.yy.dal.parse.statement.StatementVisitor;
import org.yy.dal.parse.statement.create.table.ColDataType;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class Alter implements Statement {
    private Table table;
    
    private String columnName;
    
    private ColDataType dataType;
    
    public Table getTable() {
        return table;
    }
    
    public void setTable(Table table) {
        this.table = table;
    }
    
    public String getColumnName() {
        return columnName;
    }
    
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    public ColDataType getDataType() {
        return dataType;
    }
    
    public void setDataType(ColDataType dataType) {
        this.dataType = dataType;
    }
    
    @Override
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }
    
    @Override
    public String toString() {
        return "ALTER TABLE " + table.getFullyQualifiedName() + " ADD COLUMN " + columnName + " " + dataType.toString();
    }
}
