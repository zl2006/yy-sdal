package org.yy.dal.parse.statement.select;

import org.yy.dal.parse.schema.Table;

/**
 * 
 * into table 访问器
 * 
 * @author  zhouliang
 * @version  [0.1, 2015年1月22日]
 * @since  [yydal-parse/0.1]
 */
public interface IntoTableVisitor {
    void visit(Table tableName);
}
