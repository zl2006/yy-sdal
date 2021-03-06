/*
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2013 JSQLParser
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 2.1 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */
package org.yy.dal.parse.statement.select;

import org.yy.dal.parse.expression.Alias;
import org.yy.dal.parse.expression.operators.relational.ExpressionList;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class ExpressionListItem {
    
    private ExpressionList expressionList;
    
    private Alias alias;
    
    public ExpressionList getExpressionList() {
        return expressionList;
    }
    
    public void setExpressionList(ExpressionList expressionList) {
        this.expressionList = expressionList;
    }
    
    public Alias getAlias() {
        return alias;
    }
    
    public void setAlias(Alias alias) {
        this.alias = alias;
    }
    
    @Override
    public String toString() {
        return expressionList + ((alias != null) ? alias.toString() : "");
    }
}
