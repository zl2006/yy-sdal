package org.yy.dal.parse.parser;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.yy.dal.parse.CCJSqlParser;
import org.yy.dal.parse.JSQLParserException;
import org.yy.dal.parse.expression.Expression;
import org.yy.dal.parse.statement.Statement;
import org.yy.dal.parse.statement.Statements;

/**
 * Toolfunctions to start and use JSqlParser.
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public final class CCJSqlParserUtil {
    public static Statement parse(Reader statementReader)
        throws JSQLParserException {
        CCJSqlParser parser = new CCJSqlParser(statementReader);
        try {
            return parser.Statement();
        }
        catch (Exception ex) {
            throw new JSQLParserException(ex);
        }
    }
    
    public static Statement parse(String sql)
        throws JSQLParserException {
        CCJSqlParser parser = new CCJSqlParser(new StringReader(sql));
        try {
            return parser.Statement();
        }
        catch (Exception ex) {
            throw new JSQLParserException(ex);
        }
    }
    
    public static Statement parse(InputStream is)
        throws JSQLParserException {
        CCJSqlParser parser = new CCJSqlParser(is);
        try {
            return parser.Statement();
        }
        catch (Exception ex) {
            throw new JSQLParserException(ex);
        }
    }
    
    public static Statement parse(InputStream is, String encoding)
        throws JSQLParserException {
        CCJSqlParser parser = new CCJSqlParser(is, encoding);
        try {
            return parser.Statement();
        }
        catch (Exception ex) {
            throw new JSQLParserException(ex);
        }
    }
    
    /**
     * Parse an expression.
     * @param expression
     * @return
     * @throws JSQLParserException 
     */
    public static Expression parseExpression(String expression)
        throws JSQLParserException {
        CCJSqlParser parser = new CCJSqlParser(new StringReader(expression));
        try {
            return parser.SimpleExpression();
        }
        catch (Exception ex) {
            throw new JSQLParserException(ex);
        }
    }
    
    /**
     * Parse an conditional expression. This is the expression after a where clause.
     * @param condExpr
     * @return
     * @throws JSQLParserException 
     */
    public static Expression parseCondExpression(String condExpr)
        throws JSQLParserException {
        CCJSqlParser parser = new CCJSqlParser(new StringReader(condExpr));
        try {
            return parser.Expression();
        }
        catch (Exception ex) {
            throw new JSQLParserException(ex);
        }
    }
    
    /**
     * Parse a statement list.
     */
    public static Statements parseStatements(String sqls)
        throws JSQLParserException {
        CCJSqlParser parser = new CCJSqlParser(new StringReader(sqls));
        try {
            return parser.Statements();
        }
        catch (Exception ex) {
            throw new JSQLParserException(ex);
        }
    }
    
    private CCJSqlParserUtil() {
    }
}
