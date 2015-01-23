package org.yy.dal.parse.statement.create.table;

import java.util.List;

import org.yy.dal.parse.schema.Table;
import org.yy.dal.parse.statement.select.PlainSelect;

/**
 *  Foreign Key Index
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class ForeignKeyIndex extends NamedConstraint {
    private Table table;
    
    private List<String> referencedColumnNames;
    
    public Table getTable() {
        return table;
    }
    
    public void setTable(Table table) {
        this.table = table;
    }
    
    public List<String> getReferencedColumnNames() {
        return referencedColumnNames;
    }
    
    public void setReferencedColumnNames(List<String> referencedColumnNames) {
        this.referencedColumnNames = referencedColumnNames;
    }
    
    @Override
    public String toString() {
        return super.toString() + " REFERENCES " + table
            + PlainSelect.getStringList(getReferencedColumnNames(), true, true);
    }
}
