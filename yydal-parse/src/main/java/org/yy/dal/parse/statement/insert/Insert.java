package org.yy.dal.parse.statement.insert;

import java.util.List;

import org.yy.dal.parse.expression.operators.relational.ItemsList;
import org.yy.dal.parse.schema.Column;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.parse.statement.Statement;
import org.yy.dal.parse.statement.StatementVisitor;
import org.yy.dal.parse.statement.select.PlainSelect;
import org.yy.dal.parse.statement.select.Select;
import org.yy.dal.parse.statement.select.SelectExpressionItem;

/**
 * The insert statement. Every column name in <code>columnNames</code> matches
 * an item in <code>itemsList</code>
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class Insert implements Statement {
    
    private Table table;
    
    private List<Column> columns;
    
    private ItemsList itemsList;
    
    private boolean useValues = true;
    
    private Select select;
    
    private boolean useSelectBrackets = true;
    
    private boolean returningAllColumns = false;
    
    private List<SelectExpressionItem> returningExpressionList = null;
    
    @Override
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }
    
    public Table getTable() {
        return table;
    }
    
    public void setTable(Table name) {
        table = name;
    }
    
    /**
     * Get the columns (found in "INSERT INTO (col1,col2..) [...]" )
     *
     * @return a list of {@link net.sf.jsqlparser.schema.Column}
     */
    public List<Column> getColumns() {
        return columns;
    }
    
    public void setColumns(List<Column> list) {
        columns = list;
    }
    
    /**
     * Get the values (as VALUES (...) or SELECT)
     *
     * @return the values of the insert
     */
    public ItemsList getItemsList() {
        return itemsList;
    }
    
    public void setItemsList(ItemsList list) {
        itemsList = list;
    }
    
    public boolean isUseValues() {
        return useValues;
    }
    
    public void setUseValues(boolean useValues) {
        this.useValues = useValues;
    }
    
    public boolean isReturningAllColumns() {
        return returningAllColumns;
    }
    
    public void setReturningAllColumns(boolean returningAllColumns) {
        this.returningAllColumns = returningAllColumns;
    }
    
    public List<SelectExpressionItem> getReturningExpressionList() {
        return returningExpressionList;
    }
    
    public void setReturningExpressionList(List<SelectExpressionItem> returningExpressionList) {
        this.returningExpressionList = returningExpressionList;
    }
    
    public Select getSelect() {
        return select;
    }
    
    public void setSelect(Select select) {
        this.select = select;
    }
    
    public boolean isUseSelectBrackets() {
        return useSelectBrackets;
    }
    
    public void setUseSelectBrackets(boolean useSelectBrackets) {
        this.useSelectBrackets = useSelectBrackets;
    }
    
    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder();
        
        sql.append("INSERT INTO ");
        sql.append(table).append(" ");
        if (columns != null) {
            sql.append(PlainSelect.getStringList(columns, true, true)).append(" ");
        }
        
        if (useValues) {
            sql.append("VALUES ");
        }
        
        if (itemsList != null) {
            sql.append(itemsList);
        }
        
        if (useSelectBrackets) {
            sql.append("(");
        }
        if (select != null) {
            sql.append(select);
        }
        if (useSelectBrackets) {
            sql.append(")");
        }
        
        if (isReturningAllColumns()) {
            sql.append(" RETURNING *");
        }
        else if (getReturningExpressionList() != null) {
            sql.append(" RETURNING ").append(PlainSelect.getStringList(getReturningExpressionList(), true, false));
        }
        
        return sql.toString();
    }
}
