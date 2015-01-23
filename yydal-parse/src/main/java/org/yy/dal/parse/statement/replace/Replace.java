package org.yy.dal.parse.statement.replace;

import java.util.List;

import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.expression.operators.relational.ItemsList;
import org.yy.dal.parse.schema.Column;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.parse.statement.Statement;
import org.yy.dal.parse.statement.StatementVisitor;
import org.yy.dal.parse.statement.select.PlainSelect;

/**
 * The replace statement.
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class Replace implements Statement {
    
    private Table table;
    
    private List<Column> columns;
    
    private ItemsList itemsList;
    
    private List<Expression> expressions;
    
    private boolean useValues = true;
    
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
     * A list of {@link net.sf.jsqlparser.schema.Column}s either from a "REPLACE
     * mytab (col1, col2) [...]" or a "REPLACE mytab SET col1=exp1, col2=exp2".
     *
     * @return a list of {@link net.sf.jsqlparser.schema.Column}s
     */
    public List<Column> getColumns() {
        return columns;
    }
    
    /**
     * An {@link ItemsList} (either from a "REPLACE mytab VALUES (exp1,exp2)" or
     * a "REPLACE mytab SELECT * FROM mytab2") it is null in case of a "REPLACE
     * mytab SET col1=exp1, col2=exp2"
     */
    public ItemsList getItemsList() {
        return itemsList;
    }
    
    public void setColumns(List<Column> list) {
        columns = list;
    }
    
    public void setItemsList(ItemsList list) {
        itemsList = list;
    }
    
    /**
     * A list of {@link net.sf.jsqlparser.expression.Expression}s (from a
     * "REPLACE mytab SET col1=exp1, col2=exp2"). <br>
     * it is null in case of a "REPLACE mytab (col1, col2) [...]"
     */
    public List<Expression> getExpressions() {
        return expressions;
    }
    
    public void setExpressions(List<Expression> list) {
        expressions = list;
    }
    
    public boolean isUseValues() {
        return useValues;
    }
    
    public void setUseValues(boolean useValues) {
        this.useValues = useValues;
    }
    
    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder();
        sql.append("REPLACE ").append(table);
        
        if (expressions != null && columns != null) {
            // the SET col1=exp1, col2=exp2 case
            sql.append(" SET ");
            // each element from expressions match up with a column from columns.
            for (int i = 0, s = columns.size(); i < s; i++) {
                sql.append(columns.get(i)).append("=").append(expressions.get(i));
                sql.append((i < s - 1) ? ", " : "");
            }
        }
        else if (columns != null) {
            // the REPLACE mytab (col1, col2) [...] case
            sql.append(" ").append(PlainSelect.getStringList(columns, true, true));
        }
        
        if (itemsList != null) {
            // REPLACE mytab SELECT * FROM mytab2
            // or VALUES ('as', ?, 565)
            
            if (useValues) {
                sql.append(" VALUES");
            }
            
            sql.append(" ").append(itemsList);
        }
        
        return sql.toString();
    }
}
