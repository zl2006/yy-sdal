package org.yy.dal.parse.schema;

/**
 * 数据库中的对象由多部分组成， 例如"数据库.表名",   "表名.字段名"
 * @author  zhouliang
 * @version  [0.1, 2015年1月22日]
 * @since  [yydal-parse/0.1]
 */
public interface MultiPartName {
    
    /**
     * 获取完整的名称
     */
    String getFullyQualifiedName();
}
