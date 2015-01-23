package org.yy.dal.parse.statement.select;

import org.yy.dal.parse.expression.Alias;
import org.yy.dal.parse.expression.Function;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class FunctionItem {
    
    private Function function;
    
    private Alias alias;
    
    public Alias getAlias() {
        return alias;
    }
    
    public void setAlias(Alias alias) {
        this.alias = alias;
    }
    
    public Function getFunction() {
        return function;
    }
    
    public void setFunction(Function function) {
        this.function = function;
    }
    
    @Override
    public String toString() {
        return function + ((alias != null) ? alias.toString() : "");
    }
}
