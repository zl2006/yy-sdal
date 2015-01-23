package org.yy.dal.parse.statement.create.table;

import java.util.List;

import org.yy.dal.parse.statement.select.PlainSelect;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class ColDataType {
    
    private String dataType;
    
    private List<String> argumentsStringList;
    
    private String characterSet;
    
    public List<String> getArgumentsStringList() {
        return argumentsStringList;
    }
    
    public String getDataType() {
        return dataType;
    }
    
    public void setArgumentsStringList(List<String> list) {
        argumentsStringList = list;
    }
    
    public void setDataType(String string) {
        dataType = string;
    }
    
    public String getCharacterSet() {
        return characterSet;
    }
    
    public void setCharacterSet(String characterSet) {
        this.characterSet = characterSet;
    }
    
    @Override
    public String toString() {
        return dataType
            + (argumentsStringList != null ? " " + PlainSelect.getStringList(argumentsStringList, true, true) : "")
            + (characterSet != null ? " CHARACTER SET " + characterSet : "");
    }
}
