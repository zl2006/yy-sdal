package org.yy.dal.parse.statement.select;

import org.yy.dal.parse.schema.Table;

/**
 * sql中from关键字后面的数据库对象访问器
 * 
 * @author  zhouliang
 * @version  [0.1, 2015年1月22日]
 * @since  [yydal-parse/0.1]
 */
public interface FromItemVisitor {
    
    /**
     * 表访问器
     */
    void visit(Table tableName);
    
    /**
     * 子查询访问器
     */
    void visit(SubSelect subSelect);
    
    /**
     * 子连接访问器
     */
    void visit(SubJoin subjoin);
    
    void visit(LateralSubSelect lateralSubSelect);
    
    void visit(ValuesList valuesList);
}
