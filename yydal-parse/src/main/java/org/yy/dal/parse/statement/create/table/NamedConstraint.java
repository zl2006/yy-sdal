package org.yy.dal.parse.statement.create.table;

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
public class NamedConstraint extends Index {
    @Override
    public String toString() {
        return (getName() != null ? "CONSTRAINT " + getName() + " " : "") + getType() + " "
            + PlainSelect.getStringList(getColumnsNames(), true, true);
    }
}
