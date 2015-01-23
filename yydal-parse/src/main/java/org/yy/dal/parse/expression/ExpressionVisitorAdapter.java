package org.yy.dal.parse.expression;

import org.yy.dal.parse.expression.operators.arithmetic.Addition;
import org.yy.dal.parse.expression.operators.arithmetic.BitwiseAnd;
import org.yy.dal.parse.expression.operators.arithmetic.BitwiseOr;
import org.yy.dal.parse.expression.operators.arithmetic.BitwiseXor;
import org.yy.dal.parse.expression.operators.arithmetic.Concat;
import org.yy.dal.parse.expression.operators.arithmetic.Division;
import org.yy.dal.parse.expression.operators.arithmetic.Modulo;
import org.yy.dal.parse.expression.operators.arithmetic.Multiplication;
import org.yy.dal.parse.expression.operators.arithmetic.Subtraction;
import org.yy.dal.parse.expression.operators.conditional.AndExpression;
import org.yy.dal.parse.expression.operators.conditional.OrExpression;
import org.yy.dal.parse.expression.operators.relational.Between;
import org.yy.dal.parse.expression.operators.relational.EqualsTo;
import org.yy.dal.parse.expression.operators.relational.ExistsExpression;
import org.yy.dal.parse.expression.operators.relational.ExpressionList;
import org.yy.dal.parse.expression.operators.relational.GreaterThan;
import org.yy.dal.parse.expression.operators.relational.GreaterThanEquals;
import org.yy.dal.parse.expression.operators.relational.InExpression;
import org.yy.dal.parse.expression.operators.relational.IsNullExpression;
import org.yy.dal.parse.expression.operators.relational.ItemsListVisitor;
import org.yy.dal.parse.expression.operators.relational.LikeExpression;
import org.yy.dal.parse.expression.operators.relational.Matches;
import org.yy.dal.parse.expression.operators.relational.MinorThan;
import org.yy.dal.parse.expression.operators.relational.MinorThanEquals;
import org.yy.dal.parse.expression.operators.relational.MultiExpressionList;
import org.yy.dal.parse.expression.operators.relational.NotEqualsTo;
import org.yy.dal.parse.expression.operators.relational.RegExpMatchOperator;
import org.yy.dal.parse.expression.operators.relational.RegExpMySQLOperator;
import org.yy.dal.parse.schema.Column;
import org.yy.dal.parse.statement.select.OrderByElement;
import org.yy.dal.parse.statement.select.SubSelect;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class ExpressionVisitorAdapter implements ExpressionVisitor, ItemsListVisitor {
    @Override
    public void visit(NullValue value) {
        
    }
    
    @Override
    public void visit(Function function) {
        
    }
    
    @Override
    public void visit(SignedExpression expr) {
        expr.getExpression().accept(this);
    }
    
    @Override
    public void visit(JdbcParameter parameter) {
        
    }
    
    @Override
    public void visit(JdbcNamedParameter parameter) {
        
    }
    
    @Override
    public void visit(DoubleValue value) {
        
    }
    
    @Override
    public void visit(LongValue value) {
        
    }
    
    @Override
    public void visit(DateValue value) {
        
    }
    
    @Override
    public void visit(TimeValue value) {
        
    }
    
    @Override
    public void visit(TimestampValue value) {
        
    }
    
    @Override
    public void visit(Parenthesis parenthesis) {
        parenthesis.getExpression().accept(this);
    }
    
    @Override
    public void visit(StringValue value) {
        
    }
    
    @Override
    public void visit(Addition expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(Division expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(Multiplication expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(Subtraction expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(AndExpression expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(OrExpression expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(Between expr) {
        expr.getLeftExpression().accept(this);
        expr.getBetweenExpressionStart().accept(this);
        expr.getBetweenExpressionEnd().accept(this);
    }
    
    @Override
    public void visit(EqualsTo expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(GreaterThan expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(GreaterThanEquals expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(InExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getLeftItemsList().accept(this);
        expr.getRightItemsList().accept(this);
    }
    
    @Override
    public void visit(IsNullExpression expr) {
        expr.getLeftExpression().accept(this);
    }
    
    @Override
    public void visit(LikeExpression expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(MinorThan expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(MinorThanEquals expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(NotEqualsTo expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(Column column) {
        
    }
    
    @Override
    public void visit(SubSelect subSelect) {
        
    }
    
    @Override
    public void visit(CaseExpression expr) {
        expr.getSwitchExpression().accept(this);
        for (Expression x : expr.getWhenClauses()) {
            x.accept(this);
        }
        expr.getElseExpression().accept(this);
    }
    
    @Override
    public void visit(WhenClause expr) {
        expr.getWhenExpression().accept(this);
        expr.getThenExpression().accept(this);
    }
    
    @Override
    public void visit(ExistsExpression expr) {
        expr.getRightExpression().accept(this);
    }
    
    @Override
    public void visit(AllComparisonExpression expr) {
        
    }
    
    @Override
    public void visit(AnyComparisonExpression expr) {
        
    }
    
    @Override
    public void visit(Concat expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(Matches expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(BitwiseAnd expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(BitwiseOr expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(BitwiseXor expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(CastExpression expr) {
        expr.getLeftExpression().accept(this);
    }
    
    @Override
    public void visit(Modulo expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(AnalyticExpression expr) {
        expr.getExpression().accept(this);
        expr.getDefaultValue().accept(this);
        expr.getOffset().accept(this);
        for (OrderByElement element : expr.getOrderByElements()) {
            element.getExpression().accept(this);
        }
        
        expr.getWindowElement().getRange().getStart().getExpression().accept(this);
        expr.getWindowElement().getRange().getEnd().getExpression().accept(this);
        expr.getWindowElement().getOffset().getExpression().accept(this);
    }
    
    @Override
    public void visit(ExtractExpression expr) {
        expr.getExpression().accept(this);
    }
    
    @Override
    public void visit(IntervalExpression expr) {
        
    }
    
    @Override
    public void visit(OracleHierarchicalExpression expr) {
        expr.getConnectExpression().accept(this);
        expr.getStartExpression().accept(this);
    }
    
    @Override
    public void visit(RegExpMatchOperator expr) {
        visitBinaryExpression(expr);
    }
    
    @Override
    public void visit(ExpressionList expressionList) {
        for (Expression expr : expressionList.getExpressions()) {
            expr.accept(this);
        }
    }
    
    @Override
    public void visit(MultiExpressionList multiExprList) {
        for (ExpressionList list : multiExprList.getExprList()) {
            visit(list);
        }
    }
    
    protected void visitBinaryExpression(BinaryExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
    }
    
    @Override
    public void visit(JsonExpression jsonExpr) {
        visit(jsonExpr.getColumn());
    }
    
    @Override
    public void visit(RegExpMySQLOperator expr) {
        visitBinaryExpression(expr);
    }
}
