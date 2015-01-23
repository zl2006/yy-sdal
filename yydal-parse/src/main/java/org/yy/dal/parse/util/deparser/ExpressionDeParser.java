package org.yy.dal.parse.util.deparser;

import java.util.Iterator;

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
import org.yy.dal.parse.expression.operators.relational.OldOracleJoinBinaryExpression;
import org.yy.dal.parse.expression.operators.relational.RegExpMatchOperator;
import org.yy.dal.parse.expression.operators.relational.RegExpMySQLOperator;
import org.yy.dal.parse.expression.operators.relational.SupportsOldOracleJoinSyntax;
import org.yy.dal.parse.schema.Column;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.parse.statement.select.SelectVisitor;
import org.yy.dal.parse.statement.select.SubSelect;

/**
 *  A class to de-parse (that is, tranform from JSqlParser hierarchy into a
 * string) 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class ExpressionDeParser implements ExpressionVisitor, ItemsListVisitor {
    
    private StringBuilder buffer;
    
    private SelectVisitor selectVisitor;
    
    private boolean useBracketsInExprList = true;
    
    public ExpressionDeParser() {
    }
    
    /**
     * @param selectVisitor a SelectVisitor to de-parse SubSelects. It has to
     * share the same<br> StringBuilder as this object in order to work, as:
     *
     * <pre>
     * <code>
     * StringBuilder myBuf = new StringBuilder();
     * MySelectDeparser selectDeparser = new  MySelectDeparser();
     * selectDeparser.setBuffer(myBuf);
     * ExpressionDeParser expressionDeParser = new ExpressionDeParser(selectDeparser, myBuf);
     * </code>
     * </pre>
     *
     * @param buffer the buffer that will be filled with the expression
     */
    public ExpressionDeParser(SelectVisitor selectVisitor, StringBuilder buffer) {
        this.selectVisitor = selectVisitor;
        this.buffer = buffer;
    }
    
    public StringBuilder getBuffer() {
        return buffer;
    }
    
    public void setBuffer(StringBuilder buffer) {
        this.buffer = buffer;
    }
    
    @Override
    public void visit(Addition addition) {
        visitBinaryExpression(addition, " + ");
    }
    
    @Override
    public void visit(AndExpression andExpression) {
        visitBinaryExpression(andExpression, " AND ");
    }
    
    @Override
    public void visit(Between between) {
        between.getLeftExpression().accept(this);
        if (between.isNot()) {
            buffer.append(" NOT");
        }
        
        buffer.append(" BETWEEN ");
        between.getBetweenExpressionStart().accept(this);
        buffer.append(" AND ");
        between.getBetweenExpressionEnd().accept(this);
        
    }
    
    @Override
    public void visit(EqualsTo equalsTo) {
        visitOldOracleJoinBinaryExpression(equalsTo, " = ");
    }
    
    @Override
    public void visit(Division division) {
        visitBinaryExpression(division, " / ");
        
    }
    
    @Override
    public void visit(DoubleValue doubleValue) {
        buffer.append(doubleValue.toString());
        
    }
    
    public void visitOldOracleJoinBinaryExpression(OldOracleJoinBinaryExpression expression, String operator) {
        if (expression.isNot()) {
            buffer.append(" NOT ");
        }
        expression.getLeftExpression().accept(this);
        if (expression.getOldOracleJoinSyntax() == EqualsTo.ORACLE_JOIN_RIGHT) {
            buffer.append("(+)");
        }
        buffer.append(operator);
        expression.getRightExpression().accept(this);
        if (expression.getOldOracleJoinSyntax() == EqualsTo.ORACLE_JOIN_LEFT) {
            buffer.append("(+)");
        }
    }
    
    @Override
    public void visit(GreaterThan greaterThan) {
        visitOldOracleJoinBinaryExpression(greaterThan, " > ");
    }
    
    @Override
    public void visit(GreaterThanEquals greaterThanEquals) {
        visitOldOracleJoinBinaryExpression(greaterThanEquals, " >= ");
        
    }
    
    @Override
    public void visit(InExpression inExpression) {
        if (inExpression.getLeftExpression() == null) {
            inExpression.getLeftItemsList().accept(this);
        }
        else {
            inExpression.getLeftExpression().accept(this);
            if (inExpression.getOldOracleJoinSyntax() == SupportsOldOracleJoinSyntax.ORACLE_JOIN_RIGHT) {
                buffer.append("(+)");
            }
        }
        if (inExpression.isNot()) {
            buffer.append(" NOT");
        }
        buffer.append(" IN ");
        
        inExpression.getRightItemsList().accept(this);
    }
    
    @Override
    public void visit(SignedExpression signedExpression) {
        buffer.append(signedExpression.getSign());
        signedExpression.getExpression().accept(this);
    }
    
    @Override
    public void visit(IsNullExpression isNullExpression) {
        isNullExpression.getLeftExpression().accept(this);
        if (isNullExpression.isNot()) {
            buffer.append(" IS NOT NULL");
        }
        else {
            buffer.append(" IS NULL");
        }
    }
    
    @Override
    public void visit(JdbcParameter jdbcParameter) {
        buffer.append("?");
        
    }
    
    @Override
    public void visit(LikeExpression likeExpression) {
        visitBinaryExpression(likeExpression, " LIKE ");
        String escape = likeExpression.getEscape();
        if (escape != null) {
            buffer.append(" ESCAPE '").append(escape).append('\'');
        }
    }
    
    @Override
    public void visit(ExistsExpression existsExpression) {
        if (existsExpression.isNot()) {
            buffer.append("NOT EXISTS ");
        }
        else {
            buffer.append("EXISTS ");
        }
        existsExpression.getRightExpression().accept(this);
    }
    
    @Override
    public void visit(LongValue longValue) {
        buffer.append(longValue.getStringValue());
        
    }
    
    @Override
    public void visit(MinorThan minorThan) {
        visitOldOracleJoinBinaryExpression(minorThan, " < ");
        
    }
    
    @Override
    public void visit(MinorThanEquals minorThanEquals) {
        visitOldOracleJoinBinaryExpression(minorThanEquals, " <= ");
        
    }
    
    @Override
    public void visit(Multiplication multiplication) {
        visitBinaryExpression(multiplication, " * ");
        
    }
    
    @Override
    public void visit(NotEqualsTo notEqualsTo) {
        visitOldOracleJoinBinaryExpression(notEqualsTo, " " + notEqualsTo.getStringExpression() + " ");
        
    }
    
    @Override
    public void visit(NullValue nullValue) {
        buffer.append("NULL");
        
    }
    
    @Override
    public void visit(OrExpression orExpression) {
        visitBinaryExpression(orExpression, " OR ");
        
    }
    
    @Override
    public void visit(Parenthesis parenthesis) {
        if (parenthesis.isNot()) {
            buffer.append(" NOT ");
        }
        
        buffer.append("(");
        parenthesis.getExpression().accept(this);
        buffer.append(")");
        
    }
    
    @Override
    public void visit(StringValue stringValue) {
        buffer.append("'").append(stringValue.getValue()).append("'");
        
    }
    
    @Override
    public void visit(Subtraction subtraction) {
        visitBinaryExpression(subtraction, " - ");
        
    }
    
    private void visitBinaryExpression(BinaryExpression binaryExpression, String operator) {
        if (binaryExpression.isNot()) {
            buffer.append(" NOT ");
        }
        binaryExpression.getLeftExpression().accept(this);
        buffer.append(operator);
        binaryExpression.getRightExpression().accept(this);
        
    }
    
    @Override
    public void visit(SubSelect subSelect) {
        buffer.append("(");
        subSelect.getSelectBody().accept(selectVisitor);
        buffer.append(")");
    }
    
    @Override
    public void visit(Column tableColumn) {
        final Table table = tableColumn.getTable();
        String tableName = null;
        if (table != null) {
            if (table.getAlias() != null) {
                tableName = table.getAlias().getName();
            }
            else {
                tableName = table.getFullyQualifiedName();
            }
        }
        if (tableName != null && !tableName.isEmpty()) {
            buffer.append(tableName).append(".");
        }
        
        buffer.append(tableColumn.getColumnName());
    }
    
    @Override
    public void visit(Function function) {
        if (function.isEscaped()) {
            buffer.append("{fn ");
        }
        
        buffer.append(function.getName());
        if (function.isAllColumns() && function.getParameters() == null) {
            buffer.append("(*)");
        }
        else if (function.getParameters() == null) {
            buffer.append("()");
        }
        else {
            boolean oldUseBracketsInExprList = useBracketsInExprList;
            if (function.isDistinct()) {
                useBracketsInExprList = false;
                buffer.append("(DISTINCT ");
            }
            else if (function.isAllColumns()) {
                useBracketsInExprList = false;
                buffer.append("(ALL ");
            }
            visit(function.getParameters());
            useBracketsInExprList = oldUseBracketsInExprList;
            if (function.isDistinct() || function.isAllColumns()) {
                buffer.append(")");
            }
        }
        
        if (function.isEscaped()) {
            buffer.append("}");
        }
    }
    
    @Override
    public void visit(ExpressionList expressionList) {
        if (useBracketsInExprList) {
            buffer.append("(");
        }
        for (Iterator<Expression> iter = expressionList.getExpressions().iterator(); iter.hasNext();) {
            Expression expression = iter.next();
            expression.accept(this);
            if (iter.hasNext()) {
                buffer.append(", ");
            }
        }
        if (useBracketsInExprList) {
            buffer.append(")");
        }
    }
    
    public SelectVisitor getSelectVisitor() {
        return selectVisitor;
    }
    
    public void setSelectVisitor(SelectVisitor visitor) {
        selectVisitor = visitor;
    }
    
    @Override
    public void visit(DateValue dateValue) {
        buffer.append("{d '").append(dateValue.getValue().toString()).append("'}");
    }
    
    @Override
    public void visit(TimestampValue timestampValue) {
        buffer.append("{ts '").append(timestampValue.getValue().toString()).append("'}");
    }
    
    @Override
    public void visit(TimeValue timeValue) {
        buffer.append("{t '").append(timeValue.getValue().toString()).append("'}");
    }
    
    @Override
    public void visit(CaseExpression caseExpression) {
        buffer.append("CASE ");
        Expression switchExp = caseExpression.getSwitchExpression();
        if (switchExp != null) {
            switchExp.accept(this);
            buffer.append(" ");
        }
        
        for (Expression exp : caseExpression.getWhenClauses()) {
            exp.accept(this);
        }
        
        Expression elseExp = caseExpression.getElseExpression();
        if (elseExp != null) {
            buffer.append("ELSE ");
            elseExp.accept(this);
            buffer.append(" ");
        }
        
        buffer.append("END");
    }
    
    @Override
    public void visit(WhenClause whenClause) {
        buffer.append("WHEN ");
        whenClause.getWhenExpression().accept(this);
        buffer.append(" THEN ");
        whenClause.getThenExpression().accept(this);
        buffer.append(" ");
    }
    
    @Override
    public void visit(AllComparisonExpression allComparisonExpression) {
        buffer.append(" ALL ");
        allComparisonExpression.getSubSelect().accept((ExpressionVisitor)this);
    }
    
    @Override
    public void visit(AnyComparisonExpression anyComparisonExpression) {
        buffer.append(" ANY ");
        anyComparisonExpression.getSubSelect().accept((ExpressionVisitor)this);
    }
    
    @Override
    public void visit(Concat concat) {
        visitBinaryExpression(concat, " || ");
    }
    
    @Override
    public void visit(Matches matches) {
        visitOldOracleJoinBinaryExpression(matches, " @@ ");
    }
    
    @Override
    public void visit(BitwiseAnd bitwiseAnd) {
        visitBinaryExpression(bitwiseAnd, " & ");
    }
    
    @Override
    public void visit(BitwiseOr bitwiseOr) {
        visitBinaryExpression(bitwiseOr, " | ");
    }
    
    @Override
    public void visit(BitwiseXor bitwiseXor) {
        visitBinaryExpression(bitwiseXor, " ^ ");
    }
    
    @Override
    public void visit(CastExpression cast) {
        if (cast.isUseCastKeyword()) {
            buffer.append("CAST(");
            buffer.append(cast.getLeftExpression());
            buffer.append(" AS ");
            buffer.append(cast.getType());
            buffer.append(")");
        }
        else {
            buffer.append(cast.getLeftExpression());
            buffer.append("::");
            buffer.append(cast.getType());
        }
    }
    
    @Override
    public void visit(Modulo modulo) {
        visitBinaryExpression(modulo, " % ");
    }
    
    @Override
    public void visit(AnalyticExpression aexpr) {
        buffer.append(aexpr.toString());
    }
    
    @Override
    public void visit(ExtractExpression eexpr) {
        buffer.append(eexpr.toString());
    }
    
    @Override
    public void visit(MultiExpressionList multiExprList) {
        for (Iterator<ExpressionList> it = multiExprList.getExprList().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                buffer.append(", ");
            }
        }
    }
    
    @Override
    public void visit(IntervalExpression iexpr) {
        buffer.append(iexpr.toString());
    }
    
    @Override
    public void visit(JdbcNamedParameter jdbcNamedParameter) {
        buffer.append(jdbcNamedParameter.toString());
    }
    
    @Override
    public void visit(OracleHierarchicalExpression oexpr) {
        buffer.append(oexpr.toString());
    }
    
    @Override
    public void visit(RegExpMatchOperator rexpr) {
        visitBinaryExpression(rexpr, " " + rexpr.getStringExpression() + " ");
    }
    
    @Override
    public void visit(RegExpMySQLOperator rexpr) {
        visitBinaryExpression(rexpr, " " + rexpr.getStringExpression() + " ");
    }
    
    @Override
    public void visit(JsonExpression jsonExpr) {
        buffer.append(jsonExpr.toString());
    }
    
}
