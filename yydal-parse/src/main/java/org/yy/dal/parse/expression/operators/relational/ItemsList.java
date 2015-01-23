package org.yy.dal.parse.expression.operators.relational;

/**
 * 
 * Values of an "INSERT" statement (for example a SELECT or a list of
 * expressions)
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public interface ItemsList {
    
    void accept(ItemsListVisitor itemsListVisitor);
}
