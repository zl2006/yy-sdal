package org.yy.dal.parse.expression;

import java.util.ArrayList;
import java.util.List;

import org.yy.dal.parse.schema.Column;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class JsonExpression implements Expression {
    
    private Column column;
    
    private List<String> idents = new ArrayList<String>();
    
    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    public Column getColumn() {
        return column;
    }
    
    public void setColumn(Column column) {
        this.column = column;
    }
    
    public List<String> getIdents() {
        return idents;
    }
    
    public void setIdents(List<String> idents) {
        this.idents = idents;
    }
    
    public void addIdent(String ident) {
        idents.add(ident);
    }
    
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(column.toString());
        for (String ident : idents) {
            b.append("->").append(ident);
        }
        return b.toString();
    }
}
