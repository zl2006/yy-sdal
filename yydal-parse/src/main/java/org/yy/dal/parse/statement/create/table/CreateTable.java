package org.yy.dal.parse.statement.create.table;

import java.util.List;

import org.yy.dal.parse.schema.Table;
import org.yy.dal.parse.statement.Statement;
import org.yy.dal.parse.statement.StatementVisitor;
import org.yy.dal.parse.statement.select.PlainSelect;
import org.yy.dal.parse.statement.select.Select;

/**
 * A "CREATE TABLE" statement
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class CreateTable implements Statement {
    
    private Table table;
    
    private boolean unlogged = false;
    
    private List<String> tableOptionsStrings;
    
    private List<ColumnDefinition> columnDefinitions;
    
    private List<Index> indexes;
    
    private Select select;
    
    @Override
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }
    
    /**
     * The name of the table to be created
     */
    public Table getTable() {
        return table;
    }
    
    public void setTable(Table table) {
        this.table = table;
    }
    
    /**
     * Whether the table is unlogged or not (PostgreSQL 9.1+ feature)
     * @return 
     */
    public boolean isUnlogged() {
        return unlogged;
    }
    
    public void setUnlogged(boolean unlogged) {
        this.unlogged = unlogged;
    }
    
    /**
     * A list of {@link ColumnDefinition}s of this table.
     */
    public List<ColumnDefinition> getColumnDefinitions() {
        return columnDefinitions;
    }
    
    public void setColumnDefinitions(List<ColumnDefinition> list) {
        columnDefinitions = list;
    }
    
    /**
     * A list of options (as simple strings) of this table definition, as
     * ("TYPE", "=", "MYISAM")
     */
    public List<?> getTableOptionsStrings() {
        return tableOptionsStrings;
    }
    
    public void setTableOptionsStrings(List<String> list) {
        tableOptionsStrings = list;
    }
    
    /**
     * A list of {@link Index}es (for example "PRIMARY KEY") of this table.<br>
     * Indexes created with column definitions (as in mycol INT PRIMARY KEY) are
     * not inserted into this list.
     */
    public List<Index> getIndexes() {
        return indexes;
    }
    
    public void setIndexes(List<Index> list) {
        indexes = list;
    }
    
    public Select getSelect() {
        return select;
    }
    
    public void setSelect(Select select) {
        this.select = select;
    }
    
    @Override
    public String toString() {
        String sql = "";
        
        sql = "CREATE " + (unlogged ? "UNLOGGED " : "") + "TABLE " + table;
        
        if (select != null) {
            sql += " AS " + select.toString();
        }
        else {
            sql += " (";
            
            sql += PlainSelect.getStringList(columnDefinitions, true, false);
            if (indexes != null && indexes.size() != 0) {
                sql += ", ";
                sql += PlainSelect.getStringList(indexes);
            }
            sql += ")";
            String options = PlainSelect.getStringList(tableOptionsStrings, false, false);
            if (options != null && options.length() > 0) {
                sql += " " + options;
            }
        }
        
        return sql;
    }
}
