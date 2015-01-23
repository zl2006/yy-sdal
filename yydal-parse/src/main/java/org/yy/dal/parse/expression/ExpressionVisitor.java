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
import org.yy.dal.parse.expression.operators.relational.GreaterThan;
import org.yy.dal.parse.expression.operators.relational.GreaterThanEquals;
import org.yy.dal.parse.expression.operators.relational.InExpression;
import org.yy.dal.parse.expression.operators.relational.IsNullExpression;
import org.yy.dal.parse.expression.operators.relational.LikeExpression;
import org.yy.dal.parse.expression.operators.relational.Matches;
import org.yy.dal.parse.expression.operators.relational.MinorThan;
import org.yy.dal.parse.expression.operators.relational.MinorThanEquals;
import org.yy.dal.parse.expression.operators.relational.NotEqualsTo;
import org.yy.dal.parse.expression.operators.relational.RegExpMatchOperator;
import org.yy.dal.parse.expression.operators.relational.RegExpMySQLOperator;
import org.yy.dal.parse.schema.Column;
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
public interface ExpressionVisitor {
    
    void visit(NullValue nullValue);
    
    void visit(Function function);
    
    void visit(SignedExpression signedExpression);
    
    void visit(JdbcParameter jdbcParameter);
    
    void visit(JdbcNamedParameter jdbcNamedParameter);
    
    void visit(DoubleValue doubleValue);
    
    void visit(LongValue longValue);
    
    void visit(DateValue dateValue);
    
    void visit(TimeValue timeValue);
    
    void visit(TimestampValue timestampValue);
    
    void visit(Parenthesis parenthesis);
    
    void visit(StringValue stringValue);
    
    void visit(Addition addition);
    
    void visit(Division division);
    
    void visit(Multiplication multiplication);
    
    void visit(Subtraction subtraction);
    
    void visit(AndExpression andExpression);
    
    void visit(OrExpression orExpression);
    
    void visit(Between between);
    
    void visit(EqualsTo equalsTo);
    
    void visit(GreaterThan greaterThan);
    
    void visit(GreaterThanEquals greaterThanEquals);
    
    void visit(InExpression inExpression);
    
    void visit(IsNullExpression isNullExpression);
    
    void visit(LikeExpression likeExpression);
    
    void visit(MinorThan minorThan);
    
    void visit(MinorThanEquals minorThanEquals);
    
    void visit(NotEqualsTo notEqualsTo);
    
    void visit(Column tableColumn);
    
    void visit(SubSelect subSelect);
    
    void visit(CaseExpression caseExpression);
    
    void visit(WhenClause whenClause);
    
    void visit(ExistsExpression existsExpression);
    
    void visit(AllComparisonExpression allComparisonExpression);
    
    void visit(AnyComparisonExpression anyComparisonExpression);
    
    void visit(Concat concat);
    
    void visit(Matches matches);
    
    void visit(BitwiseAnd bitwiseAnd);
    
    void visit(BitwiseOr bitwiseOr);
    
    void visit(BitwiseXor bitwiseXor);
    
    void visit(CastExpression cast);
    
    void visit(Modulo modulo);
    
    void visit(AnalyticExpression aexpr);
    
    void visit(ExtractExpression eexpr);
    
    void visit(IntervalExpression iexpr);
    
    void visit(OracleHierarchicalExpression oexpr);
    
    void visit(RegExpMatchOperator rexpr);
    
    void visit(JsonExpression jsonExpr);
    
    void visit(RegExpMySQLOperator regExpMySQLOperator);
}
