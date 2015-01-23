package org.yy.dal.parse.util.deparser;

import java.util.Iterator;

import org.yy.dal.parse.statement.create.table.ColumnDefinition;
import org.yy.dal.parse.statement.create.table.CreateTable;
import org.yy.dal.parse.statement.create.table.Index;

/**
 * A class to de-parse (that is, tranform from JSqlParser hierarchy into a
 * string)
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class CreateTableDeParser {
    
    private StringBuilder buffer;
    
    /**
     * @param buffer the buffer that will be filled with the select
     */
    public CreateTableDeParser(StringBuilder buffer) {
        this.buffer = buffer;
    }
    
    public void deParse(CreateTable createTable) {
        buffer.append("CREATE ");
        if (createTable.isUnlogged()) {
            buffer.append("UNLOGGED ");
        }
        buffer.append("TABLE ").append(createTable.getTable().getFullyQualifiedName());
        if (createTable.getSelect() != null) {
            buffer.append(" AS ").append(createTable.getSelect().toString());
        }
        else {
            if (createTable.getColumnDefinitions() != null) {
                buffer.append(" (");
                for (Iterator<ColumnDefinition> iter = createTable.getColumnDefinitions().iterator(); iter.hasNext();) {
                    ColumnDefinition columnDefinition = iter.next();
                    buffer.append(columnDefinition.getColumnName());
                    buffer.append(" ");
                    buffer.append(columnDefinition.getColDataType().toString());
                    if (columnDefinition.getColumnSpecStrings() != null) {
                        for (String s : columnDefinition.getColumnSpecStrings()) {
                            buffer.append(" ");
                            buffer.append(s);
                        }
                    }
                    
                    if (iter.hasNext()) {
                        buffer.append(", ");
                    }
                }
                
                if (createTable.getIndexes() != null) {
                    for (Iterator<Index> iter = createTable.getIndexes().iterator(); iter.hasNext();) {
                        buffer.append(", ");
                        Index index = iter.next();
                        buffer.append(index.toString());
                    }
                }
                
                buffer.append(")");
            }
        }
    }
    
    public StringBuilder getBuffer() {
        return buffer;
    }
    
    public void setBuffer(StringBuilder buffer) {
        this.buffer = buffer;
    }
}
