package org.yy.dal.parse.util.deparser;

import java.util.Iterator;

import org.yy.dal.parse.statement.create.index.CreateIndex;
import org.yy.dal.parse.statement.create.table.Index;

/**
 *  A class to de-parse (that is, tranform from JSqlParser hierarchy into a string)
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class CreateIndexDeParser {
    
    private StringBuilder buffer;
    
    /**
     * @param buffer the buffer that will be filled with the create
     */
    public CreateIndexDeParser(StringBuilder buffer) {
        this.buffer = buffer;
    }
    
    public void deParse(CreateIndex createIndex) {
        Index index = createIndex.getIndex();
        
        buffer.append("CREATE ");
        
        if (index.getType() != null) {
            buffer.append(index.getType());
            buffer.append(" ");
        }
        
        buffer.append("INDEX ");
        buffer.append(index.getName());
        buffer.append(" ON ");
        buffer.append(createIndex.getTable().getFullyQualifiedName());
        
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
    }
    
    public StringBuilder getBuffer() {
        return buffer;
    }
    
    public void setBuffer(StringBuilder buffer) {
        this.buffer = buffer;
    }
    
}
