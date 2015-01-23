package org.yy.dal.parse.statement.create.table;

import java.util.List;

import org.yy.dal.parse.statement.select.PlainSelect;

/**
 * A column definition in a CREATE TABLE statement.<br>
 * Example: mycol VARCHAR(30) NOT NULL
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class ColumnDefinition {
    
    private String columnName;
    
    private ColDataType colDataType;
    
    private List<String> columnSpecStrings;
    
    /**
     * A list of strings of every word after the datatype of the column.<br>
     * Example ("NOT", "NULL")
     */
    public List<String> getColumnSpecStrings() {
        return columnSpecStrings;
    }
    
    public void setColumnSpecStrings(List<String> list) {
        columnSpecStrings = list;
    }
    
    /**
     * The {@link ColDataType} of this column definition
     */
    public ColDataType getColDataType() {
        return colDataType;
    }
    
    public void setColDataType(ColDataType type) {
        colDataType = type;
    }
    
    public String getColumnName() {
        return columnName;
    }
    
    public void setColumnName(String string) {
        columnName = string;
    }
    
    @Override
    public String toString() {
        return columnName + " " + colDataType
            + (columnSpecStrings != null ? " " + PlainSelect.getStringList(columnSpecStrings, false, false) : "");
    }
}
