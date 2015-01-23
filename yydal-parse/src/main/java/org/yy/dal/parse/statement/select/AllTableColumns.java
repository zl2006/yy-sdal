package org.yy.dal.parse.statement.select;

import org.yy.dal.parse.schema.Table;

/**
 * All the columns of a table (as in "SELECT TableName.* FROM ...")
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class AllTableColumns implements SelectItem {
    
    private Table table;
    
    public AllTableColumns() {
    }
    
    public AllTableColumns(Table tableName) {
        this.table = tableName;
    }
    
    public Table getTable() {
        return table;
    }
    
    public void setTable(Table table) {
        this.table = table;
    }
    
    @Override
    public void accept(SelectItemVisitor selectItemVisitor) {
        selectItemVisitor.visit(this);
    }
    
    @Override
    public String toString() {
        return table + ".*";
    }
}
