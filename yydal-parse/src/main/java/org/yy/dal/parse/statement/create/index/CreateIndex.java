package org.yy.dal.parse.statement.create.index;

import java.util.Iterator;

import org.yy.dal.parse.schema.Table;
import org.yy.dal.parse.statement.Statement;
import org.yy.dal.parse.statement.StatementVisitor;
import org.yy.dal.parse.statement.create.table.Index;

/**
 * A "CREATE INDEX" statement
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class CreateIndex implements Statement {
    
    private Table table;
    
    private Index index;
    
    @Override
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }
    
    /**
     * The index to be created
     */
    public Index getIndex() {
        return index;
    }
    
    public void setIndex(Index index) {
        this.index = index;
    }
    
    /**
     * The table on which the index is to be created
     */
    public Table getTable() {
        return table;
    }
    
    public void setTable(Table table) {
        this.table = table;
    }
    
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("CREATE ");
        
        if (index.getType() != null) {
            buffer.append(index.getType());
            buffer.append(" ");
        }
        
        buffer.append("INDEX ");
        buffer.append(index.getName());
        buffer.append(" ON ");
        buffer.append(table.getFullyQualifiedName());
        
        if (index.getColumnsNames() != null) {
            buffer.append(" (");
            
            for (Iterator iter = index.getColumnsNames().iterator(); iter.hasNext();) {
                String columnName = (String)iter.next();
                
                buffer.append(columnName);
                
                if (iter.hasNext()) {
                    buffer.append(", ");
                }
            }
            
            buffer.append(")");
        }
        
        return buffer.toString();
    }
    
}
