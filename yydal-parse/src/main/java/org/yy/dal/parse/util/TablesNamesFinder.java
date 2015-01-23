package org.yy.dal.parse.util;

import java.util.ArrayList;
import java.util.List;

import org.yy.dal.parse.expression.AllComparisonExpression;
import org.yy.dal.parse.expression.AnalyticExpression;
import org.yy.dal.parse.expression.AnyComparisonExpression;
import org.yy.dal.parse.expression.BinaryExpression;
import org.yy.dal.parse.expression.CaseExpression;
import org.yy.dal.parse.expression.CastExpression;
import org.yy.dal.parse.expression.DateValue;
import org.yy.dal.parse.expression.DoubleValue;
import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.expression.ExpressionVisitor;
import org.yy.dal.parse.expression.ExtractExpression;
import org.yy.dal.parse.expression.Function;
import org.yy.dal.parse.expression.IntervalExpression;
import org.yy.dal.parse.expression.JdbcNamedParameter;
import org.yy.dal.parse.expression.JdbcParameter;
import org.yy.dal.parse.expression.JsonExpression;
import org.yy.dal.parse.expression.LongValue;
import org.yy.dal.parse.expression.NullValue;
import org.yy.dal.parse.expression.OracleHierarchicalExpression;
import org.yy.dal.parse.expression.Parenthesis;
import org.yy.dal.parse.expression.SignedExpression;
import org.yy.dal.parse.expression.StringValue;
import org.yy.dal.parse.expression.TimeValue;
import org.yy.dal.parse.expression.TimestampValue;
import org.yy.dal.parse.expression.WhenClause;
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
import org.yy.dal.parse.schema.Table;
import org.yy.dal.parse.statement.delete.Delete;
import org.yy.dal.parse.statement.insert.Insert;
import org.yy.dal.parse.statement.replace.Replace;
import org.yy.dal.parse.statement.select.FromItemVisitor;
import org.yy.dal.parse.statement.select.Join;
import org.yy.dal.parse.statement.select.LateralSubSelect;
import org.yy.dal.parse.statement.select.PlainSelect;
import org.yy.dal.parse.statement.select.Select;
import org.yy.dal.parse.statement.select.SelectVisitor;
import org.yy.dal.parse.statement.select.SetOperationList;
import org.yy.dal.parse.statement.select.SubJoin;
import org.yy.dal.parse.statement.select.SubSelect;
import org.yy.dal.parse.statement.select.ValuesList;
import org.yy.dal.parse.statement.select.WithItem;
import org.yy.dal.parse.statement.update.Update;

/*
 *   <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class TablesNamesFinder implements SelectVisitor, FromItemVisitor, ExpressionVisitor, ItemsListVisitor {
    
    private List<String> tables;
    
    /**
     * There are special names, that are not table names but are parsed as
     * tables. These names are collected here and are not included in the tables
     * - names anymore.
     */
    private List<String> otherItemNames;
    
    /**
     * Main entry for this Tool class. A list of found tables is returned.
     *
     * @param delete
     * @return
     */
    public List<String> getTableList(Delete delete) {
        init();
        tables.add(delete.getTable().getName());
        if (delete.getWhere() != null) {
            delete.getWhere().accept(this);
        }
        
        return tables;
    }
    
    /**
     * Main entry for this Tool class. A list of found tables is returned.
     *
     * @param insert
     * @return
     */
    public List<String> getTableList(Insert insert) {
        init();
        tables.add(insert.getTable().getName());
        if (insert.getItemsList() != null) {
            insert.getItemsList().accept(this);
        }
        
        return tables;
    }
    
    /**
     * Main entry for this Tool class. A list of found tables is returned.
     *
     * @param replace
     * @return
     */
    public List<String> getTableList(Replace replace) {
        init();
        tables.add(replace.getTable().getName());
        if (replace.getExpressions() != null) {
            for (Expression expression : replace.getExpressions()) {
                expression.accept(this);
            }
        }
        if (replace.getItemsList() != null) {
            replace.getItemsList().accept(this);
        }
        
        return tables;
    }
    
    /**
     * Main entry for this Tool class. A list of found tables is returned.
     *
     * @param select
     * @return
     */
    public List<String> getTableList(Select select) {
        init();
        if (select.getWithItemsList() != null) {
            for (WithItem withItem : select.getWithItemsList()) {
                withItem.accept(this);
            }
        }
        select.getSelectBody().accept(this);
        
        return tables;
    }
    
    /**
     * Main entry for this Tool class. A list of found tables is returned.
     *
     * @param update
     * @return
     */
    public List<String> getTableList(Update update) {
        init();
        for (Table table : update.getTables()) {
            tables.add(table.getName());
        }
        if (update.getExpressions() != null) {
            for (Expression expression : update.getExpressions()) {
                expression.accept(this);
            }
        }
        
        if (update.getFromItem() != null) {
            update.getFromItem().accept(this);
        }
        
        if (update.getJoins() != null) {
            for (Join join : update.getJoins()) {
                join.getRightItem().accept(this);
            }
        }
        
        if (update.getWhere() != null) {
            update.getWhere().accept(this);
        }
        
        return tables;
    }
    
    @Override
    public void visit(WithItem withItem) {
        otherItemNames.add(withItem.getName().toLowerCase());
        withItem.getSelectBody().accept(this);
    }
    
    @Override
    public void visit(PlainSelect plainSelect) {
        plainSelect.getFromItem().accept(this);
        
        if (plainSelect.getJoins() != null) {
            for (Join join : plainSelect.getJoins()) {
                join.getRightItem().accept(this);
            }
        }
        if (plainSelect.getWhere() != null) {
            plainSelect.getWhere().accept(this);
        }
        
    }
    
    @Override
    public void visit(Table tableName) {
        String tableWholeName = tableName.getFullyQualifiedName();
        if (!otherItemNames.contains(tableWholeName.toLowerCase()) && !tables.contains(tableWholeName)) {
            tables.add(tableWholeName);
        }
    }
    
    @Override
    public void visit(SubSelect subSelect) {
        subSelect.getSelectBody().accept(this);
    }
    
    @Override
    public void visit(Addition addition) {
        visitBinaryExpression(addition);
    }
    
    @Override
    public void visit(AndExpression andExpression) {
        visitBinaryExpression(andExpression);
    }
    
    @Override
    public void visit(Between between) {
        between.getLeftExpression().accept(this);
        between.getBetweenExpressionStart().accept(this);
        between.getBetweenExpressionEnd().accept(this);
    }
    
    @Override
    public void visit(Column tableColumn) {
    }
    
    @Override
    public void visit(Division division) {
        visitBinaryExpression(division);
    }
    
    @Override
    public void visit(DoubleValue doubleValue) {
    }
    
    @Override
    public void visit(EqualsTo equalsTo) {
        visitBinaryExpression(equalsTo);
    }
    
    @Override
    public void visit(Function function) {
    }
    
    @Override
    public void visit(GreaterThan greaterThan) {
        visitBinaryExpression(greaterThan);
    }
    
    @Override
    public void visit(GreaterThanEquals greaterThanEquals) {
        visitBinaryExpression(greaterThanEquals);
    }
    
    @Override
    public void visit(InExpression inExpression) {
        inExpression.getLeftExpression().accept(this);
        inExpression.getRightItemsList().accept(this);
    }
    
    @Override
    public void visit(SignedExpression signedExpression) {
        signedExpression.getExpression().accept(this);
    }
    
    @Override
    public void visit(IsNullExpression isNullExpression) {
    }
    
    @Override
    public void visit(JdbcParameter jdbcParameter) {
    }
    
    @Override
    public void visit(LikeExpression likeExpression) {
        visitBinaryExpression(likeExpression);
    }
    
    @Override
    public void visit(ExistsExpression existsExpression) {
        existsExpression.getRightExpression().accept(this);
    }
    
    @Override
    public void visit(LongValue longValue) {
    }
    
    @Override
    public void visit(MinorThan minorThan) {
        visitBinaryExpression(minorThan);
    }
    
    @Override
    public void visit(MinorThanEquals minorThanEquals) {
        visitBinaryExpression(minorThanEquals);
    }
    
    @Override
    public void visit(Multiplication multiplication) {
        visitBinaryExpression(multiplication);
    }
    
    @Override
    public void visit(NotEqualsTo notEqualsTo) {
        visitBinaryExpression(notEqualsTo);
    }
    
    @Override
    public void visit(NullValue nullValue) {
    }
    
    @Override
    public void visit(OrExpression orExpression) {
        visitBinaryExpression(orExpression);
    }
    
    @Override
    public void visit(Parenthesis parenthesis) {
        parenthesis.getExpression().accept(this);
    }
    
    @Override
    public void visit(StringValue stringValue) {
    }
    
    @Override
    public void visit(Subtraction subtraction) {
        visitBinaryExpression(subtraction);
    }
    
    public void visitBinaryExpression(BinaryExpression binaryExpression) {
        binaryExpression.getLeftExpression().accept(this);
        binaryExpression.getRightExpression().accept(this);
    }
    
    @Override
    public void visit(ExpressionList expressionList) {
        for (Expression expression : expressionList.getExpressions()) {
            expression.accept(this);
        }
        
    }
    
    @Override
    public void visit(DateValue dateValue) {
    }
    
    @Override
    public void visit(TimestampValue timestampValue) {
    }
    
    @Override
    public void visit(TimeValue timeValue) {
    }
    
    /*
     * (non-Javadoc)
     *
     * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.CaseExpression)
     */
    @Override
    public void visit(CaseExpression caseExpression) {
    }
    
    /*
     * (non-Javadoc)
     *
     * @see net.sf.jsqlparser.expression.ExpressionVisitor#visit(net.sf.jsqlparser.expression.WhenClause)
     */
    @Override
    public void visit(WhenClause whenClause) {
    }
    
    @Override
    public void visit(AllComparisonExpression allComparisonExpression) {
        allComparisonExpression.getSubSelect().getSelectBody().accept(this);
    }
    
    @Override
    public void visit(AnyComparisonExpression anyComparisonExpression) {
        anyComparisonExpression.getSubSelect().getSelectBody().accept(this);
    }
    
    @Override
    public void visit(SubJoin subjoin) {
        subjoin.getLeft().accept(this);
        subjoin.getJoin().getRightItem().accept(this);
    }
    
    @Override
    public void visit(Concat concat) {
        visitBinaryExpression(concat);
    }
    
    @Override
    public void visit(Matches matches) {
        visitBinaryExpression(matches);
    }
    
    @Override
    public void visit(BitwiseAnd bitwiseAnd) {
        visitBinaryExpression(bitwiseAnd);
    }
    
    @Override
    public void visit(BitwiseOr bitwiseOr) {
        visitBinaryExpression(bitwiseOr);
    }
    
    @Override
    public void visit(BitwiseXor bitwiseXor) {
        visitBinaryExpression(bitwiseXor);
    }
    
    @Override
    public void visit(CastExpression cast) {
        cast.getLeftExpression().accept(this);
    }
    
    @Override
    public void visit(Modulo modulo) {
        visitBinaryExpression(modulo);
    }
    
    @Override
    public void visit(AnalyticExpression analytic) {
    }
    
    @Override
    public void visit(SetOperationList list) {
        for (PlainSelect plainSelect : list.getPlainSelects()) {
            visit(plainSelect);
        }
    }
    
    @Override
    public void visit(ExtractExpression eexpr) {
    }
    
    @Override
    public void visit(LateralSubSelect lateralSubSelect) {
        lateralSubSelect.getSubSelect().getSelectBody().accept(this);
    }
    
    @Override
    public void visit(MultiExpressionList multiExprList) {
        for (ExpressionList exprList : multiExprList.getExprList()) {
            exprList.accept(this);
        }
    }
    
    @Override
    public void visit(ValuesList valuesList) {
    }
    
    private void init() {
        otherItemNames = new ArrayList<String>();
        tables = new ArrayList<String>();
    }
    
    @Override
    public void visit(IntervalExpression iexpr) {
    }
    
    @Override
    public void visit(JdbcNamedParameter jdbcNamedParameter) {
    }
    
    @Override
    public void visit(OracleHierarchicalExpression oexpr) {
    }
    
    @Override
    public void visit(RegExpMatchOperator rexpr) {
        visitBinaryExpression(rexpr);
    }
    
    @Override
    public void visit(RegExpMySQLOperator rexpr) {
        visitBinaryExpression(rexpr);
    }
    
    @Override
    public void visit(JsonExpression jsonExpr) {
    }
    
}
