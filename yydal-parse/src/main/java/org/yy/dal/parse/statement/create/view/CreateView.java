package org.yy.dal.parse.statement.create.view;

import java.util.List;

import org.yy.dal.parse.schema.Table;
import org.yy.dal.parse.statement.Statement;
import org.yy.dal.parse.statement.StatementVisitor;
import org.yy.dal.parse.statement.select.PlainSelect;
import org.yy.dal.parse.statement.select.SelectBody;

/**
 *  A "CREATE VIEW" statement
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class CreateView implements Statement {
    
    private Table view;
    
    private SelectBody selectBody;
    
    private boolean orReplace = false;
    
    private List<String> columnNames = null;
    
    private boolean materialized = false;
    
    @Override
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }
    
    /**
     * In the syntax tree, a view looks and acts just like a Table.
     *
     * @return The name of the view to be created.
     */
    public Table getView() {
        return view;
    }
    
    public void setView(Table view) {
        this.view = view;
    }
    
    /**
     * @return was "OR REPLACE" specified?
     */
    public boolean isOrReplace() {
        return orReplace;
    }
    
    /**
     * @param orReplace was "OR REPLACE" specified?
     */
    public void setOrReplace(boolean orReplace) {
        this.orReplace = orReplace;
    }
    
    /**
     * @return the SelectBody
     */
    public SelectBody getSelectBody() {
        return selectBody;
    }
    
    public void setSelectBody(SelectBody selectBody) {
        this.selectBody = selectBody;
    }
    
    public List<String> getColumnNames() {
        return columnNames;
    }
    
    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }
    
    public boolean isMaterialized() {
        return materialized;
    }
    
    public void setMaterialized(boolean materialized) {
        this.materialized = materialized;
    }
    
    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder("CREATE ");
        if (isOrReplace()) {
            sql.append("OR REPLACE ");
        }
        if (isMaterialized()) {
            sql.append("MATERIALIZED ");
        }
        sql.append("VIEW ");
        sql.append(view);
        if (columnNames != null) {
            sql.append(PlainSelect.getStringList(columnNames, true, true));
        }
        sql.append(" AS ").append(selectBody);
        return sql.toString();
    }
}
