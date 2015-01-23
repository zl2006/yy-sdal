package org.yy.dal.parse.parser;

import java.io.Reader;

import org.yy.dal.parse.JSQLParserException;
import org.yy.dal.parse.statement.Statement;

/**
 *  Every parser must implements this interface
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public interface JSqlParser {
    
    Statement parse(Reader statementReader)
        throws JSQLParserException;
}
