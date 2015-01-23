package org.yy.dal.parse.statement.create.table;

import java.util.List;

import org.yy.dal.parse.statement.select.PlainSelect;

/**
 * An index (unique, primary etc.) in a CREATE TABLE statement
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class Index {
    
    private String type;
    
    private List<String> columnsNames;
    
    private String name;
    
    /**
     * A list of strings of all the columns regarding this index
     */
    public List<String> getColumnsNames() {
        return columnsNames;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * The type of this index: "PRIMARY KEY", "UNIQUE", "INDEX"
     */
    public String getType() {
        return type;
    }
    
    public void setColumnsNames(List<String> list) {
        columnsNames = list;
    }
    
    public void setName(String string) {
        name = string;
    }
    
    public void setType(String string) {
        type = string;
    }
    
    @Override
    public String toString() {
        return type + " " + PlainSelect.getStringList(columnsNames, true, true) + (name != null ? " " + name : "");
    }
}
