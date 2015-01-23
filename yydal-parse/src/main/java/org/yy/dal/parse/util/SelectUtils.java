package org.yy.dal.parse.util;

import java.util.ArrayList;
import java.util.List;

import org.yy.dal.parse.JSQLParserException;
import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.parser.CCJSqlParserUtil;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.parse.statement.select.AllColumns;
import org.yy.dal.parse.statement.select.Join;
import org.yy.dal.parse.statement.select.PlainSelect;
import org.yy.dal.parse.statement.select.Select;
import org.yy.dal.parse.statement.select.SelectExpressionItem;
import org.yy.dal.parse.statement.select.SelectItem;
import org.yy.dal.parse.statement.select.SelectVisitor;
import org.yy.dal.parse.statement.select.SetOperationList;
import org.yy.dal.parse.statement.select.WithItem;

/**
 * Utility function for select statements.
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public final class SelectUtils {
    
    private SelectUtils() {
    }
    
    /**
     * Builds select expr1, expr2 from table.
     * @param table
     * @param expr
     * @return 
     */
    public static Select buildSelectFromTableAndExpressions(Table table, Expression... expr) {
        SelectItem[] list = new SelectItem[expr.length];
        for (int i = 0; i < expr.length; i++) {
            list[i] = new SelectExpressionItem(expr[i]);
        }
        return buildSelectFromTableAndSelectItems(table, list);
    }
    
    /**
     * Builds select expr1, expr2 from table.
     * @param table
     * @param expr
     * @return 
     * @throws net.sf.jsqlparser.JSQLParserException 
     */
    public static Select buildSelectFromTableAndExpressions(Table table, String... expr)
        throws JSQLParserException {
        SelectItem[] list = new SelectItem[expr.length];
        for (int i = 0; i < expr.length; i++) {
            list[i] = new SelectExpressionItem(CCJSqlParserUtil.parseExpression(expr[i]));
        }
        return buildSelectFromTableAndSelectItems(table, list);
    }
    
    public static Select buildSelectFromTableAndSelectItems(Table table, SelectItem... selectItems) {
        Select select = new Select();
        PlainSelect body = new PlainSelect();
        body.addSelectItems(selectItems);
        body.setFromItem(table);
        select.setSelectBody(body);
        return select;
    }
    
    /**
     * Builds select * from table.
     * @param table
     * @return 
     */
    public static Select buildSelectFromTable(Table table) {
        return buildSelectFromTableAndSelectItems(table, new AllColumns());
    }
    
    /**
     * Adds an expression to select statements. E.g. a simple column is an
     * expression.
     *
     * @param select
     * @param expr
     */
    public static void addExpression(Select select, final Expression expr) {
        select.getSelectBody().accept(new SelectVisitor() {
            
            @Override
            public void visit(PlainSelect plainSelect) {
                plainSelect.getSelectItems().add(new SelectExpressionItem(expr));
            }
            
            @Override
            public void visit(SetOperationList setOpList) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
            @Override
            public void visit(WithItem withItem) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }
    
    /**
     * Adds a simple join to a select statement. The introduced join is returned for
     * more configuration settings on it (e.g. left join, right join).
     * @param select
     * @param table
     * @param onExpression
     * @return 
     */
    public static Join addJoin(Select select, final Table table, final Expression onExpression) {
        if (select.getSelectBody() instanceof PlainSelect) {
            PlainSelect plainSelect = (PlainSelect)select.getSelectBody();
            List<Join> joins = plainSelect.getJoins();
            if (joins == null) {
                joins = new ArrayList<Join>();
                plainSelect.setJoins(joins);
            }
            Join join = new Join();
            join.setRightItem(table);
            join.setOnExpression(onExpression);
            joins.add(join);
            return join;
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
