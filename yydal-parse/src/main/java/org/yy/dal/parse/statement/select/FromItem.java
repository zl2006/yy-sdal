package org.yy.dal.parse.statement.select;

import org.yy.dal.parse.expression.Alias;

/**
 * sql中from关键字后的数据库对象
 * An item in a "SELECT [...] FROM item1" statement. (for example a table or a
 * sub-select)
 */
public interface FromItem {
    
    /**
     * 对象访问器
     */
    void accept(FromItemVisitor fromItemVisitor);
    
    /**
     * 获取别名
     */
    Alias getAlias();
    
    /**
     * 设置别名
     */
    void setAlias(Alias alias);
    
    Pivot getPivot();
    
    void setPivot(Pivot pivot);
    
}
