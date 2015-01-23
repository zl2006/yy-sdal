package org.yy.dal.parse;

import java.util.ArrayList;
import java.util.List;

import org.yy.dal.parse.expression.Alias;
import org.yy.dal.parse.expression.AllComparisonExpression;
import org.yy.dal.parse.expression.AnalyticExpression;
import org.yy.dal.parse.expression.AnyComparisonExpression;
import org.yy.dal.parse.expression.BinaryExpression;
import org.yy.dal.parse.expression.CaseExpression;
import org.yy.dal.parse.expression.CastExpression;
import org.yy.dal.parse.expression.DateValue;
import org.yy.dal.parse.expression.DoubleValue;
import org.yy.dal.parse.expression.Expression;
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
import org.yy.dal.parse.expression.WindowElement;
import org.yy.dal.parse.expression.WindowOffset;
import org.yy.dal.parse.expression.WindowRange;
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
import org.yy.dal.parse.expression.operators.relational.ItemsList;
import org.yy.dal.parse.expression.operators.relational.LikeExpression;
import org.yy.dal.parse.expression.operators.relational.Matches;
import org.yy.dal.parse.expression.operators.relational.MinorThan;
import org.yy.dal.parse.expression.operators.relational.MinorThanEquals;
import org.yy.dal.parse.expression.operators.relational.MultiExpressionList;
import org.yy.dal.parse.expression.operators.relational.NotEqualsTo;
import org.yy.dal.parse.expression.operators.relational.RegExpMatchOperator;
import org.yy.dal.parse.expression.operators.relational.RegExpMatchOperatorType;
import org.yy.dal.parse.expression.operators.relational.RegExpMySQLOperator;
import org.yy.dal.parse.expression.operators.relational.SupportsOldOracleJoinSyntax;
import org.yy.dal.parse.schema.Column;
import org.yy.dal.parse.schema.Database;
import org.yy.dal.parse.schema.Server;
import org.yy.dal.parse.schema.Table;
import org.yy.dal.parse.statement.Statement;
import org.yy.dal.parse.statement.Statements;
import org.yy.dal.parse.statement.alter.Alter;
import org.yy.dal.parse.statement.create.index.CreateIndex;
import org.yy.dal.parse.statement.create.table.ColDataType;
import org.yy.dal.parse.statement.create.table.ColumnDefinition;
import org.yy.dal.parse.statement.create.table.CreateTable;
import org.yy.dal.parse.statement.create.table.ForeignKeyIndex;
import org.yy.dal.parse.statement.create.table.Index;
import org.yy.dal.parse.statement.create.table.NamedConstraint;
import org.yy.dal.parse.statement.create.view.CreateView;
import org.yy.dal.parse.statement.delete.Delete;
import org.yy.dal.parse.statement.drop.Drop;
import org.yy.dal.parse.statement.execute.Execute;
import org.yy.dal.parse.statement.insert.Insert;
import org.yy.dal.parse.statement.replace.Replace;
import org.yy.dal.parse.statement.select.AllColumns;
import org.yy.dal.parse.statement.select.AllTableColumns;
import org.yy.dal.parse.statement.select.Distinct;
import org.yy.dal.parse.statement.select.ExceptOp;
import org.yy.dal.parse.statement.select.ExpressionListItem;
import org.yy.dal.parse.statement.select.FromItem;
import org.yy.dal.parse.statement.select.FunctionItem;
import org.yy.dal.parse.statement.select.IntersectOp;
import org.yy.dal.parse.statement.select.Join;
import org.yy.dal.parse.statement.select.LateralSubSelect;
import org.yy.dal.parse.statement.select.Limit;
import org.yy.dal.parse.statement.select.MinusOp;
import org.yy.dal.parse.statement.select.OrderByElement;
import org.yy.dal.parse.statement.select.Pivot;
import org.yy.dal.parse.statement.select.PivotXml;
import org.yy.dal.parse.statement.select.PlainSelect;
import org.yy.dal.parse.statement.select.Select;
import org.yy.dal.parse.statement.select.SelectBody;
import org.yy.dal.parse.statement.select.SelectExpressionItem;
import org.yy.dal.parse.statement.select.SelectItem;
import org.yy.dal.parse.statement.select.SetOperation;
import org.yy.dal.parse.statement.select.SetOperationList;
import org.yy.dal.parse.statement.select.SubJoin;
import org.yy.dal.parse.statement.select.SubSelect;
import org.yy.dal.parse.statement.select.Top;
import org.yy.dal.parse.statement.select.UnionOp;
import org.yy.dal.parse.statement.select.ValuesList;
import org.yy.dal.parse.statement.select.WithItem;
import org.yy.dal.parse.statement.truncate.Truncate;
import org.yy.dal.parse.statement.update.Update;

/**
 *  The parser generated by JavaCC
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhouliang
 * @version  [版本号, 2015年1月22日]
 * @since  [产品/模块版本]
 */
public class CCJSqlParser implements CCJSqlParserConstants {
    private boolean allowOraclePrior = false;
    
    final public Statement Statement()
        throws ParseException {
        Statement stm;
        stm = SingleStatement();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case 122: {
                jj_consume_token(122);
                break;
            }
            default:
                jj_la1[0] = jj_gen;
                ;
        }
        jj_consume_token(0);
        {
            if ("" != null)
                return stm;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Statement SingleStatement()
        throws ParseException {
        Statement stm;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_WITH:
            case K_SELECT:
            case 125: {
                stm = Select();
                break;
            }
            case K_UPDATE: {
                stm = Update();
                break;
            }
            case K_INSERT: {
                stm = Insert();
                break;
            }
            case K_DELETE: {
                stm = Delete();
                break;
            }
            case K_REPLACE: {
                stm = Replace();
                break;
            }
            case K_ALTER: {
                stm = Alter();
                break;
            }
            default:
                jj_la1[1] = jj_gen;
                if (jj_2_1(3)) {
                    stm = CreateIndex();
                }
                else if (jj_2_2(2)) {
                    stm = CreateTable();
                }
                else if (jj_2_3(2)) {
                    stm = CreateView();
                }
                else {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case K_DROP: {
                            stm = Drop();
                            break;
                        }
                        case K_TRUNCATE: {
                            stm = Truncate();
                            break;
                        }
                        case K_EXEC:
                        case K_EXECUTE: {
                            stm = Execute();
                            break;
                        }
                        default:
                            jj_la1[2] = jj_gen;
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                }
        }
        {
            if ("" != null)
                return stm;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Statements Statements()
        throws ParseException {
        Statements stmts = new Statements();
        List<Statement> list = new ArrayList<Statement>();
        Statement stm;
        stm = SingleStatement();
        list.add(stm);
        label_1: while (true) {
            if (jj_2_4(2)) {
                ;
            }
            else {
                break label_1;
            }
            jj_consume_token(122);
            stm = SingleStatement();
            list.add(stm);
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case 122: {
                jj_consume_token(122);
                break;
            }
            default:
                jj_la1[3] = jj_gen;
                ;
        }
        jj_consume_token(0);
        stmts.setStatements(list);
        {
            if ("" != null)
                return stmts;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Update Update()
        throws ParseException {
        Update update = new Update();
        Table table = null;
        List<Table> tables = new ArrayList<Table>();
        Expression where = null;
        Column tableColumn = null;
        List<Expression> expList = new ArrayList<Expression>();
        List<Column> columns = new ArrayList<Column>();
        Expression value = null;
        FromItem fromItem = null;
        List<Join> joins = null;
        jj_consume_token(K_UPDATE);
        table = TableWithAlias();
        tables.add(table);
        label_2: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 123: {
                    ;
                    break;
                }
                default:
                    jj_la1[4] = jj_gen;
                    break label_2;
            }
            jj_consume_token(123);
            table = TableWithAlias();
            tables.add(table);
        }
        jj_consume_token(K_SET);
        tableColumn = Column();
        jj_consume_token(124);
        value = SimpleExpression();
        columns.add(tableColumn);
        expList.add(value);
        label_3: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 123: {
                    ;
                    break;
                }
                default:
                    jj_la1[5] = jj_gen;
                    break label_3;
            }
            jj_consume_token(123);
            tableColumn = Column();
            jj_consume_token(124);
            value = SimpleExpression();
            columns.add(tableColumn);
            expList.add(value);
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_FROM: {
                jj_consume_token(K_FROM);
                fromItem = FromItem();
                joins = JoinsList();
                break;
            }
            default:
                jj_la1[6] = jj_gen;
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_WHERE: {
                where = WhereClause();
                update.setWhere(where);
                break;
            }
            default:
                jj_la1[7] = jj_gen;
                ;
        }
        update.setColumns(columns);
        update.setExpressions(expList);
        update.setTables(tables);
        update.setFromItem(fromItem);
        update.setJoins(joins);
        {
            if ("" != null)
                return update;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Replace Replace()
        throws ParseException {
        Replace replace = new Replace();
        Table table = null;
        Column tableColumn = null;
        Expression value = null;
        
        List<Column> columns = new ArrayList<Column>();
        List<Expression> expList = new ArrayList<Expression>();
        ItemsList itemsList = null;
        Expression exp = null;
        jj_consume_token(K_REPLACE);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_INTO: {
                jj_consume_token(K_INTO);
                break;
            }
            default:
                jj_la1[8] = jj_gen;
                ;
        }
        table = Table();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_SET: {
                jj_consume_token(K_SET);
                tableColumn = Column();
                jj_consume_token(124);
                value = SimpleExpression();
                columns.add(tableColumn);
                expList.add(value);
                label_4: while (true) {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case 123: {
                            ;
                            break;
                        }
                        default:
                            jj_la1[9] = jj_gen;
                            break label_4;
                    }
                    jj_consume_token(123);
                    tableColumn = Column();
                    jj_consume_token(124);
                    value = SimpleExpression();
                    columns.add(tableColumn);
                    expList.add(value);
                }
                replace.setExpressions(expList);
                break;
            }
            case K_VALUE:
            case K_SELECT:
            case K_VALUES:
            case 125: {
                if (jj_2_5(2)) {
                    jj_consume_token(125);
                    tableColumn = Column();
                    columns.add(tableColumn);
                    label_5: while (true) {
                        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                            case 123: {
                                ;
                                break;
                            }
                            default:
                                jj_la1[10] = jj_gen;
                                break label_5;
                        }
                        jj_consume_token(123);
                        tableColumn = Column();
                        columns.add(tableColumn);
                    }
                    jj_consume_token(126);
                }
                else {
                    ;
                }
                if (jj_2_6(2)) {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case K_VALUE:
                        case K_VALUES: {
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case K_VALUES: {
                                    jj_consume_token(K_VALUES);
                                    break;
                                }
                                case K_VALUE: {
                                    jj_consume_token(K_VALUE);
                                    break;
                                }
                                default:
                                    jj_la1[11] = jj_gen;
                                    jj_consume_token(-1);
                                    throw new ParseException();
                            }
                            break;
                        }
                        default:
                            jj_la1[12] = jj_gen;
                            ;
                    }
                    jj_consume_token(125);
                    exp = PrimaryExpression();
                    expList.add(exp);
                    label_6: while (true) {
                        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                            case 123: {
                                ;
                                break;
                            }
                            default:
                                jj_la1[13] = jj_gen;
                                break label_6;
                        }
                        jj_consume_token(123);
                        exp = PrimaryExpression();
                        expList.add(exp);
                    }
                    jj_consume_token(126);
                    itemsList = new ExpressionList(expList);
                }
                else {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case K_SELECT:
                        case 125: {
                            replace.setUseValues(false);
                            itemsList = SubSelect();
                            ((SubSelect)itemsList).setUseBrackets(false);
                            break;
                        }
                        default:
                            jj_la1[14] = jj_gen;
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                }
                replace.setItemsList(itemsList);
                break;
            }
            default:
                jj_la1[15] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        if (columns.size() > 0)
            replace.setColumns(columns);
        replace.setTable(table);
        {
            if ("" != null)
                return replace;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public List<SelectExpressionItem> ListExpressionItem()
        throws ParseException {
        List<SelectExpressionItem> retval = new ArrayList<SelectExpressionItem>();
        SelectExpressionItem item;
        item = SelectExpressionItem();
        retval.add(item);
        label_7: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 123: {
                    ;
                    break;
                }
                default:
                    jj_la1[16] = jj_gen;
                    break label_7;
            }
            jj_consume_token(123);
            item = SelectExpressionItem();
            retval.add(item);
        }
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Insert Insert()
        throws ParseException {
        Insert insert = new Insert();
        Table table = null;
        Column tableColumn = null;
        List<Column> columns = new ArrayList<Column>();
        List<Expression> primaryExpList = new ArrayList<Expression>();
        ItemsList itemsList = null;
        Expression exp = null;
        MultiExpressionList multiExpr = null;
        List<SelectExpressionItem> returning = null;
        Select select = null;
        boolean useSelectBrackets = false;
        jj_consume_token(K_INSERT);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_INTO: {
                jj_consume_token(K_INTO);
                break;
            }
            default:
                jj_la1[17] = jj_gen;
                ;
        }
        table = Table();
        if (jj_2_7(2)) {
            jj_consume_token(125);
            tableColumn = Column();
            columns.add(tableColumn);
            label_8: while (true) {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case 123: {
                        ;
                        break;
                    }
                    default:
                        jj_la1[18] = jj_gen;
                        break label_8;
                }
                jj_consume_token(123);
                tableColumn = Column();
                columns.add(tableColumn);
            }
            jj_consume_token(126);
        }
        else {
            ;
        }
        if (jj_2_9(2)) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_VALUE:
                case K_VALUES: {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case K_VALUES: {
                            jj_consume_token(K_VALUES);
                            break;
                        }
                        case K_VALUE: {
                            jj_consume_token(K_VALUE);
                            break;
                        }
                        default:
                            jj_la1[19] = jj_gen;
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                    break;
                }
                default:
                    jj_la1[20] = jj_gen;
                    ;
            }
            jj_consume_token(125);
            exp = SimpleExpression();
            primaryExpList.add(exp);
            label_9: while (true) {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case 123: {
                        ;
                        break;
                    }
                    default:
                        jj_la1[21] = jj_gen;
                        break label_9;
                }
                jj_consume_token(123);
                exp = SimpleExpression();
                primaryExpList.add(exp);
            }
            jj_consume_token(126);
            itemsList = new ExpressionList(primaryExpList);
            label_10: while (true) {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case 123: {
                        ;
                        break;
                    }
                    default:
                        jj_la1[22] = jj_gen;
                        break label_10;
                }
                jj_consume_token(123);
                jj_consume_token(125);
                exp = SimpleExpression();
                if (multiExpr == null) {
                    multiExpr = new MultiExpressionList();
                    multiExpr.addExpressionList((ExpressionList)itemsList);
                    itemsList = multiExpr;
                }
                primaryExpList = new ArrayList<Expression>();
                primaryExpList.add(exp);
                label_11: while (true) {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case 123: {
                            ;
                            break;
                        }
                        default:
                            jj_la1[23] = jj_gen;
                            break label_11;
                    }
                    jj_consume_token(123);
                    exp = SimpleExpression();
                    primaryExpList.add(exp);
                }
                jj_consume_token(126);
                multiExpr.addExpressionList(primaryExpList);
            }
        }
        else {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_WITH:
                case K_SELECT:
                case 125: {
                    if (jj_2_8(2)) {
                        jj_consume_token(125);
                        useSelectBrackets = true;
                    }
                    else {
                        ;
                    }
                    insert.setUseValues(false);
                    select = Select();
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case 126: {
                            jj_consume_token(126);
                            break;
                        }
                        default:
                            jj_la1[24] = jj_gen;
                            ;
                    }
                    break;
                }
                default:
                    jj_la1[25] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_RETURNING: {
                jj_consume_token(K_RETURNING);
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case 127: {
                        jj_consume_token(127);
                        insert.setReturningAllColumns(true);
                        break;
                    }
                    case K_DO:
                    case K_NULL:
                    case K_CASE:
                    case K_XML:
                    case K_VALUE:
                    case K_REPLACE:
                    case K_TRUNCATE:
                    case K_CAST:
                    case K_PARTITION:
                    case K_EXTRACT:
                    case K_MATERIALIZED:
                    case K_INTERVAL:
                    case K_SIBLINGS:
                    case K_COLUMN:
                    case K_NULLS:
                    case K_FIRST:
                    case K_LAST:
                    case K_ROWS:
                    case K_RANGE:
                    case K_FOLLOWING:
                    case K_ROW:
                    case S_DOUBLE:
                    case S_LONG:
                    case S_IDENTIFIER:
                    case S_CHAR_LITERAL:
                    case S_QUOTED_IDENTIFIER:
                    case 125:
                    case 129:
                    case 145:
                    case 146:
                    case 150:
                    case 152:
                    case 153:
                    case 155:
                    case 157: {
                        returning = ListExpressionItem();
                        break;
                    }
                    default:
                        jj_la1[26] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                break;
            }
            default:
                jj_la1[27] = jj_gen;
                ;
        }
        insert.setItemsList(itemsList);
        insert.setUseSelectBrackets(useSelectBrackets);
        insert.setSelect(select);
        insert.setTable(table);
        if (columns.size() > 0)
            insert.setColumns(columns);
        insert.setReturningExpressionList(returning);
        {
            if ("" != null)
                return insert;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Delete Delete()
        throws ParseException {
        Delete delete = new Delete();
        Table table = null;
        Expression where = null;
        jj_consume_token(K_DELETE);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_FROM: {
                jj_consume_token(K_FROM);
                break;
            }
            default:
                jj_la1[28] = jj_gen;
                ;
        }
        table = TableWithAlias();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_WHERE: {
                where = WhereClause();
                delete.setWhere(where);
                break;
            }
            default:
                jj_la1[29] = jj_gen;
                ;
        }
        delete.setTable(table);
        {
            if ("" != null)
                return delete;
        }
        throw new Error("Missing return statement in function");
    }
    
    // See: http://technet.microsoft.com/en-us/library/ms187879%28v=sql.105%29.aspx
    final public Column Column()
        throws ParseException {
        String databaseName = null, schemaName = null, tableName = null, columnName = null;
        if (jj_2_10(7)) {
            databaseName = RelObjectName();
            jj_consume_token(128);
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_DO:
                case K_XML:
                case K_VALUE:
                case K_REPLACE:
                case K_TRUNCATE:
                case K_CAST:
                case K_PARTITION:
                case K_EXTRACT:
                case K_MATERIALIZED:
                case K_SIBLINGS:
                case K_COLUMN:
                case K_NULLS:
                case K_FIRST:
                case K_LAST:
                case K_ROWS:
                case K_RANGE:
                case K_FOLLOWING:
                case K_ROW:
                case S_IDENTIFIER:
                case S_QUOTED_IDENTIFIER: {
                    schemaName = RelObjectName();
                    break;
                }
                default:
                    jj_la1[30] = jj_gen;
                    ;
            }
            jj_consume_token(128);
            tableName = RelObjectName();
            jj_consume_token(128);
            columnName = RelObjectName();
        }
        else if (jj_2_11(5)) {
            schemaName = RelObjectName();
            jj_consume_token(128);
            tableName = RelObjectName();
            jj_consume_token(128);
            columnName = RelObjectName();
        }
        else if (jj_2_12(3)) {
            tableName = RelObjectName();
            jj_consume_token(128);
            columnName = RelObjectName();
        }
        else {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_DO:
                case K_XML:
                case K_VALUE:
                case K_REPLACE:
                case K_TRUNCATE:
                case K_CAST:
                case K_PARTITION:
                case K_EXTRACT:
                case K_MATERIALIZED:
                case K_SIBLINGS:
                case K_COLUMN:
                case K_NULLS:
                case K_FIRST:
                case K_LAST:
                case K_ROWS:
                case K_RANGE:
                case K_FOLLOWING:
                case K_ROW:
                case S_IDENTIFIER:
                case S_QUOTED_IDENTIFIER: {
                    columnName = RelObjectName();
                    break;
                }
                default:
                    jj_la1[31] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        final Database database = new Database(databaseName);
        final Table table = new Table(database, schemaName, tableName);
        {
            if ("" != null)
                return new Column(table, columnName);
        }
        throw new Error("Missing return statement in function");
    }
    
    final public String RelObjectName()
        throws ParseException {
        Token tk = null;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case S_IDENTIFIER: {
                tk = jj_consume_token(S_IDENTIFIER);
                break;
            }
            case S_QUOTED_IDENTIFIER: {
                tk = jj_consume_token(S_QUOTED_IDENTIFIER);
                break;
            }
            case K_CAST: {
                tk = jj_consume_token(K_CAST);
                break;
            }
            case K_DO: {
                tk = jj_consume_token(K_DO);
                break;
            }
            case K_EXTRACT: {
                tk = jj_consume_token(K_EXTRACT);
                break;
            }
            case K_FIRST: {
                tk = jj_consume_token(K_FIRST);
                break;
            }
            case K_FOLLOWING: {
                tk = jj_consume_token(K_FOLLOWING);
                break;
            }
            case K_LAST: {
                tk = jj_consume_token(K_LAST);
                break;
            }
            case K_MATERIALIZED: {
                tk = jj_consume_token(K_MATERIALIZED);
                break;
            }
            case K_NULLS: {
                tk = jj_consume_token(K_NULLS);
                break;
            }
            case K_PARTITION: {
                tk = jj_consume_token(K_PARTITION);
                break;
            }
            case K_RANGE: {
                tk = jj_consume_token(K_RANGE);
                break;
            }
            case K_ROW: {
                tk = jj_consume_token(K_ROW);
                break;
            }
            case K_ROWS: {
                tk = jj_consume_token(K_ROWS);
                break;
            }
            case K_SIBLINGS: {
                tk = jj_consume_token(K_SIBLINGS);
                break;
            }
            case K_VALUE: {
                tk = jj_consume_token(K_VALUE);
                break;
            }
            case K_XML: {
                tk = jj_consume_token(K_XML);
                break;
            }
            case K_COLUMN: {
                tk = jj_consume_token(K_COLUMN);
                break;
            }
            case K_REPLACE: {
                tk = jj_consume_token(K_REPLACE);
                break;
            }
            case K_TRUNCATE: {
                tk = jj_consume_token(K_TRUNCATE);
                break;
            }
            default:
                jj_la1[32] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        {
            if ("" != null)
                return tk.image;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Table Table()
        throws ParseException {
        String serverName = null, databaseName = null, schemaName = null, tableName = null;
        if (jj_2_13(7)) {
            serverName = RelObjectName();
            jj_consume_token(128);
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_DO:
                case K_XML:
                case K_VALUE:
                case K_REPLACE:
                case K_TRUNCATE:
                case K_CAST:
                case K_PARTITION:
                case K_EXTRACT:
                case K_MATERIALIZED:
                case K_SIBLINGS:
                case K_COLUMN:
                case K_NULLS:
                case K_FIRST:
                case K_LAST:
                case K_ROWS:
                case K_RANGE:
                case K_FOLLOWING:
                case K_ROW:
                case S_IDENTIFIER:
                case S_QUOTED_IDENTIFIER: {
                    databaseName = RelObjectName();
                    break;
                }
                default:
                    jj_la1[33] = jj_gen;
                    ;
            }
            jj_consume_token(128);
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_DO:
                case K_XML:
                case K_VALUE:
                case K_REPLACE:
                case K_TRUNCATE:
                case K_CAST:
                case K_PARTITION:
                case K_EXTRACT:
                case K_MATERIALIZED:
                case K_SIBLINGS:
                case K_COLUMN:
                case K_NULLS:
                case K_FIRST:
                case K_LAST:
                case K_ROWS:
                case K_RANGE:
                case K_FOLLOWING:
                case K_ROW:
                case S_IDENTIFIER:
                case S_QUOTED_IDENTIFIER: {
                    schemaName = RelObjectName();
                    break;
                }
                default:
                    jj_la1[34] = jj_gen;
                    ;
            }
            jj_consume_token(128);
            tableName = RelObjectName();
        }
        else if (jj_2_14(5)) {
            databaseName = RelObjectName();
            jj_consume_token(128);
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_DO:
                case K_XML:
                case K_VALUE:
                case K_REPLACE:
                case K_TRUNCATE:
                case K_CAST:
                case K_PARTITION:
                case K_EXTRACT:
                case K_MATERIALIZED:
                case K_SIBLINGS:
                case K_COLUMN:
                case K_NULLS:
                case K_FIRST:
                case K_LAST:
                case K_ROWS:
                case K_RANGE:
                case K_FOLLOWING:
                case K_ROW:
                case S_IDENTIFIER:
                case S_QUOTED_IDENTIFIER: {
                    schemaName = RelObjectName();
                    break;
                }
                default:
                    jj_la1[35] = jj_gen;
                    ;
            }
            jj_consume_token(128);
            tableName = RelObjectName();
        }
        else if (jj_2_15(3)) {
            schemaName = RelObjectName();
            jj_consume_token(128);
            tableName = RelObjectName();
        }
        else {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_DO:
                case K_XML:
                case K_VALUE:
                case K_REPLACE:
                case K_TRUNCATE:
                case K_CAST:
                case K_PARTITION:
                case K_EXTRACT:
                case K_MATERIALIZED:
                case K_SIBLINGS:
                case K_COLUMN:
                case K_NULLS:
                case K_FIRST:
                case K_LAST:
                case K_ROWS:
                case K_RANGE:
                case K_FOLLOWING:
                case K_ROW:
                case S_IDENTIFIER:
                case S_QUOTED_IDENTIFIER: {
                    tableName = RelObjectName();
                    break;
                }
                default:
                    jj_la1[36] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        final Server server = new Server(serverName);
        final Database database = new Database(server, databaseName);
        {
            if ("" != null)
                return new Table(database, schemaName, tableName);
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Table TableWithAlias()
        throws ParseException {
        Table table = null;
        Alias alias = null;
        table = Table();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_AS:
            case K_DO:
            case K_XML:
            case K_VALUE:
            case K_REPLACE:
            case K_TRUNCATE:
            case K_CAST:
            case K_PARTITION:
            case K_EXTRACT:
            case K_MATERIALIZED:
            case K_SIBLINGS:
            case K_COLUMN:
            case K_NULLS:
            case K_FIRST:
            case K_LAST:
            case K_ROWS:
            case K_RANGE:
            case K_FOLLOWING:
            case K_ROW:
            case S_IDENTIFIER:
            case S_QUOTED_IDENTIFIER: {
                alias = Alias();
                table.setAlias(alias);
                break;
            }
            default:
                jj_la1[37] = jj_gen;
                ;
        }
        {
            if ("" != null)
                return table;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Select Select()
        throws ParseException {
        Select select = new Select();
        SelectBody selectBody = null;
        List<WithItem> with = null;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_WITH: {
                with = WithList();
                select.setWithItemsList(with);
                break;
            }
            default:
                jj_la1[38] = jj_gen;
                ;
        }
        selectBody = SelectBody();
        select.setSelectBody(selectBody);
        {
            if ("" != null)
                return select;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public SelectBody SelectBody()
        throws ParseException {
        SelectBody selectBody = null;
        if (jj_2_16(2147483647)) {
            selectBody = SetOperationList();
        }
        else {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_SELECT: {
                    selectBody = PlainSelect();
                    break;
                }
                default:
                    jj_la1[39] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        {
            if ("" != null)
                return selectBody;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public PlainSelect PlainSelect()
        throws ParseException {
        PlainSelect plainSelect = new PlainSelect();
        List<SelectItem> selectItems = null;
        FromItem fromItem = null;
        List<Join> joins = null;
        List<SelectItem> distinctOn = null;
        Expression where = null;
        List<OrderByElement> orderByElements;
        List<Expression> groupByColumnReferences = null;
        Expression having = null;
        Limit limit = null;
        Top top = null;
        OracleHierarchicalExpression oracleHierarchicalQueryClause = null;
        List<Table> intoTables = null;
        jj_consume_token(K_SELECT);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_ALL:
            case K_DISTINCT: {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_ALL: {
                        jj_consume_token(K_ALL);
                        break;
                    }
                    case K_DISTINCT: {
                        jj_consume_token(K_DISTINCT);
                        Distinct distinct = new Distinct();
                        plainSelect.setDistinct(distinct);
                        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                            case K_ON: {
                                jj_consume_token(K_ON);
                                jj_consume_token(125);
                                distinctOn = SelectItemsList();
                                plainSelect.getDistinct().setOnSelectItems(distinctOn);
                                jj_consume_token(126);
                                break;
                            }
                            default:
                                jj_la1[40] = jj_gen;
                                ;
                        }
                        break;
                    }
                    default:
                        jj_la1[41] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                break;
            }
            default:
                jj_la1[42] = jj_gen;
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_TOP: {
                top = Top();
                plainSelect.setTop(top);
                break;
            }
            default:
                jj_la1[43] = jj_gen;
                ;
        }
        selectItems = SelectItemsList();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_INTO: {
                intoTables = IntoClause();
                plainSelect.setIntoTables(intoTables);
                break;
            }
            default:
                jj_la1[44] = jj_gen;
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_FROM: {
                jj_consume_token(K_FROM);
                fromItem = FromItem();
                joins = JoinsList();
                break;
            }
            default:
                jj_la1[45] = jj_gen;
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_WHERE: {
                where = WhereClause();
                plainSelect.setWhere(where);
                break;
            }
            default:
                jj_la1[46] = jj_gen;
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_START:
            case K_CONNECT: {
                oracleHierarchicalQueryClause = OracleHierarchicalQueryClause();
                plainSelect.setOracleHierarchical(oracleHierarchicalQueryClause);
                break;
            }
            default:
                jj_la1[47] = jj_gen;
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_GROUP: {
                groupByColumnReferences = GroupByColumnReferences();
                plainSelect.setGroupByColumnReferences(groupByColumnReferences);
                break;
            }
            default:
                jj_la1[48] = jj_gen;
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_HAVING: {
                having = Having();
                plainSelect.setHaving(having);
                break;
            }
            default:
                jj_la1[49] = jj_gen;
                ;
        }
        if (jj_2_17(2147483647)) {
            orderByElements = OrderByElements();
            plainSelect.setOracleSiblings(true);
            plainSelect.setOrderByElements(orderByElements);
        }
        else {
            ;
        }
        if (jj_2_18(2147483647)) {
            orderByElements = OrderByElements();
            plainSelect.setOrderByElements(orderByElements);
        }
        else {
            ;
        }
        if (jj_2_19(2)) {
            limit = Limit();
            plainSelect.setLimit(limit);
        }
        else {
            ;
        }
        plainSelect.setSelectItems(selectItems);
        plainSelect.setFromItem(fromItem);
        if (joins != null && joins.size() > 0)
            plainSelect.setJoins(joins);
        {
            if ("" != null)
                return plainSelect;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public SetOperationList SetOperationList()
        throws ParseException {
        SetOperationList list = new SetOperationList();
        List<OrderByElement> orderByElements = null;
        Limit limit = null;
        PlainSelect select = null;
        List<PlainSelect> selects = new ArrayList<PlainSelect>();
        List<SetOperation> operations = new ArrayList<SetOperation>();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case 125: {
                jj_consume_token(125);
                select = PlainSelect();
                jj_consume_token(126);
                break;
            }
            case K_SELECT: {
                select = PlainSelect();
                break;
            }
            default:
                jj_la1[50] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        selects.add(select);
        label_12: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_UNION: {
                    jj_consume_token(K_UNION);
                    UnionOp union = new UnionOp();
                    operations.add(union);
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case K_ALL:
                        case K_DISTINCT: {
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case K_ALL: {
                                    jj_consume_token(K_ALL);
                                    union.setAll(true);
                                    break;
                                }
                                case K_DISTINCT: {
                                    jj_consume_token(K_DISTINCT);
                                    union.setDistinct(true);
                                    break;
                                }
                                default:
                                    jj_la1[51] = jj_gen;
                                    jj_consume_token(-1);
                                    throw new ParseException();
                            }
                            break;
                        }
                        default:
                            jj_la1[52] = jj_gen;
                            ;
                    }
                    break;
                }
                case K_INTERSECT: {
                    jj_consume_token(K_INTERSECT);
                    operations.add(new IntersectOp());
                    break;
                }
                case K_MINUS: {
                    jj_consume_token(K_MINUS);
                    operations.add(new MinusOp());
                    break;
                }
                case K_EXCEPT: {
                    jj_consume_token(K_EXCEPT);
                    operations.add(new ExceptOp());
                    break;
                }
                default:
                    jj_la1[53] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 125: {
                    jj_consume_token(125);
                    select = PlainSelect();
                    jj_consume_token(126);
                    break;
                }
                case K_SELECT: {
                    select = PlainSelect();
                    break;
                }
                default:
                    jj_la1[54] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            selects.add(select);
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_UNION:
                case K_INTERSECT:
                case K_EXCEPT:
                case K_MINUS: {
                    ;
                    break;
                }
                default:
                    jj_la1[55] = jj_gen;
                    break label_12;
            }
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_ORDER: {
                orderByElements = OrderByElements();
                list.setOrderByElements(orderByElements);
                break;
            }
            default:
                jj_la1[56] = jj_gen;
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_LIMIT:
            case K_OFFSET: {
                limit = Limit();
                list.setLimit(limit);
                break;
            }
            default:
                jj_la1[57] = jj_gen;
                ;
        }
        list.setOpsAndSelects(selects, operations);
        {
            if ("" != null)
                return list;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public List<WithItem> WithList()
        throws ParseException {
        List<WithItem> withItemsList = new ArrayList<WithItem>();
        WithItem with = null;
        jj_consume_token(K_WITH);
        with = WithItem();
        withItemsList.add(with);
        label_13: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 123: {
                    ;
                    break;
                }
                default:
                    jj_la1[58] = jj_gen;
                    break label_13;
            }
            jj_consume_token(123);
            with = WithItem();
            withItemsList.add(with);
        }
        {
            if ("" != null)
                return withItemsList;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public WithItem WithItem()
        throws ParseException {
        WithItem with = new WithItem();
        String name = null;
        List<SelectItem> selectItems = null;
        SelectBody selectBody = null;
        name = RelObjectName();
        with.setName(name);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case 125: {
                jj_consume_token(125);
                selectItems = SelectItemsList();
                jj_consume_token(126);
                with.setWithItemList(selectItems);
                break;
            }
            default:
                jj_la1[59] = jj_gen;
                ;
        }
        jj_consume_token(K_AS);
        jj_consume_token(125);
        selectBody = SelectBody();
        with.setSelectBody(selectBody);
        jj_consume_token(126);
        {
            if ("" != null)
                return with;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public List<SelectItem> SelectItemsList()
        throws ParseException {
        List<SelectItem> selectItemsList = new ArrayList<SelectItem>();
        SelectItem selectItem = null;
        selectItem = SelectItem();
        selectItemsList.add(selectItem);
        label_14: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 123: {
                    ;
                    break;
                }
                default:
                    jj_la1[60] = jj_gen;
                    break label_14;
            }
            jj_consume_token(123);
            selectItem = SelectItem();
            selectItemsList.add(selectItem);
        }
        {
            if ("" != null)
                return selectItemsList;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public SelectExpressionItem SelectExpressionItem()
        throws ParseException {
        SelectExpressionItem selectExpressionItem = null;
        Expression expression = null;
        Alias alias = null;
        expression = SimpleExpression();
        selectExpressionItem = new SelectExpressionItem();
        selectExpressionItem.setExpression(expression);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_AS:
            case K_DO:
            case K_XML:
            case K_VALUE:
            case K_REPLACE:
            case K_TRUNCATE:
            case K_CAST:
            case K_PARTITION:
            case K_EXTRACT:
            case K_MATERIALIZED:
            case K_SIBLINGS:
            case K_COLUMN:
            case K_NULLS:
            case K_FIRST:
            case K_LAST:
            case K_ROWS:
            case K_RANGE:
            case K_FOLLOWING:
            case K_ROW:
            case S_IDENTIFIER:
            case S_QUOTED_IDENTIFIER: {
                alias = Alias();
                selectExpressionItem.setAlias(alias);
                break;
            }
            default:
                jj_la1[61] = jj_gen;
                ;
        }
        {
            if ("" != null)
                return selectExpressionItem;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public SelectItem SelectItem()
        throws ParseException {
        SelectItem selectItem = null;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case 127: {
                jj_consume_token(127);
                selectItem = new AllColumns();
                break;
            }
            default:
                jj_la1[62] = jj_gen;
                if (jj_2_20(2147483647)) {
                    selectItem = AllTableColumns();
                }
                else if (jj_2_21(2147483647)) {
                    selectItem = SelectExpressionItem();
                }
                else {
                    jj_consume_token(-1);
                    throw new ParseException();
                }
        }
        {
            if ("" != null)
                return selectItem;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public AllTableColumns AllTableColumns()
        throws ParseException {
        Table table = null;
        table = Table();
        jj_consume_token(128);
        jj_consume_token(127);
        {
            if ("" != null)
                return new AllTableColumns(table);
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Alias Alias()
        throws ParseException {
        String name = null;
        boolean useAs = false;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_AS: {
                jj_consume_token(K_AS);
                useAs = true;
                break;
            }
            default:
                jj_la1[63] = jj_gen;
                ;
        }
        name = RelObjectName();
        {
            if ("" != null)
                return new Alias(name, useAs);
        }
        throw new Error("Missing return statement in function");
    }
    
    final public FunctionItem FunctionItem()
        throws ParseException {
        Alias alias = null;
        Function function;
        FunctionItem functionItem;
        function = Function();
        functionItem = new FunctionItem();
        functionItem.setFunction(function);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_AS:
            case K_DO:
            case K_XML:
            case K_VALUE:
            case K_REPLACE:
            case K_TRUNCATE:
            case K_CAST:
            case K_PARTITION:
            case K_EXTRACT:
            case K_MATERIALIZED:
            case K_SIBLINGS:
            case K_COLUMN:
            case K_NULLS:
            case K_FIRST:
            case K_LAST:
            case K_ROWS:
            case K_RANGE:
            case K_FOLLOWING:
            case K_ROW:
            case S_IDENTIFIER:
            case S_QUOTED_IDENTIFIER: {
                alias = Alias();
                functionItem.setAlias(alias);
                break;
            }
            default:
                jj_la1[64] = jj_gen;
                ;
        }
        {
            if ("" != null)
                return functionItem;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public List<Column> PivotForColumns()
        throws ParseException {
        List<Column> columns = new ArrayList<Column>();
        Column column;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case 125: {
                jj_consume_token(125);
                column = Column();
                columns.add(column);
                label_15: while (true) {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case 123: {
                            ;
                            break;
                        }
                        default:
                            jj_la1[65] = jj_gen;
                            break label_15;
                    }
                    jj_consume_token(123);
                    column = Column();
                    columns.add(column);
                }
                jj_consume_token(126);
                break;
            }
            case K_DO:
            case K_XML:
            case K_VALUE:
            case K_REPLACE:
            case K_TRUNCATE:
            case K_CAST:
            case K_PARTITION:
            case K_EXTRACT:
            case K_MATERIALIZED:
            case K_SIBLINGS:
            case K_COLUMN:
            case K_NULLS:
            case K_FIRST:
            case K_LAST:
            case K_ROWS:
            case K_RANGE:
            case K_FOLLOWING:
            case K_ROW:
            case S_IDENTIFIER:
            case S_QUOTED_IDENTIFIER: {
                column = Column();
                columns.add(column);
                break;
            }
            default:
                jj_la1[66] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        {
            if ("" != null)
                return columns;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public List<FunctionItem> PivotFunctionItems()
        throws ParseException {
        List<FunctionItem> functionItems = new ArrayList<FunctionItem>();
        FunctionItem item;
        item = FunctionItem();
        functionItems.add(item);
        label_16: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 123: {
                    ;
                    break;
                }
                default:
                    jj_la1[67] = jj_gen;
                    break label_16;
            }
            jj_consume_token(123);
            item = FunctionItem();
            functionItems.add(item);
        }
        {
            if ("" != null)
                return functionItems;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public List<SelectExpressionItem> PivotSingleInItems()
        throws ParseException {
        List<SelectExpressionItem> retval = new ArrayList<SelectExpressionItem>();
        SelectExpressionItem item;
        item = SelectExpressionItem();
        retval.add(item);
        label_17: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 123: {
                    ;
                    break;
                }
                default:
                    jj_la1[68] = jj_gen;
                    break label_17;
            }
            jj_consume_token(123);
            item = SelectExpressionItem();
            retval.add(item);
        }
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public ExpressionListItem ExpressionListItem()
        throws ParseException {
        ExpressionListItem expressionListItem = null;
        ExpressionList expressionList = null;
        Alias alias = null;
        jj_consume_token(125);
        expressionList = SimpleExpressionList();
        expressionListItem = new ExpressionListItem();
        expressionListItem.setExpressionList(expressionList);
        jj_consume_token(126);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_AS:
            case K_DO:
            case K_XML:
            case K_VALUE:
            case K_REPLACE:
            case K_TRUNCATE:
            case K_CAST:
            case K_PARTITION:
            case K_EXTRACT:
            case K_MATERIALIZED:
            case K_SIBLINGS:
            case K_COLUMN:
            case K_NULLS:
            case K_FIRST:
            case K_LAST:
            case K_ROWS:
            case K_RANGE:
            case K_FOLLOWING:
            case K_ROW:
            case S_IDENTIFIER:
            case S_QUOTED_IDENTIFIER: {
                alias = Alias();
                expressionListItem.setAlias(alias);
                break;
            }
            default:
                jj_la1[69] = jj_gen;
                ;
        }
        {
            if ("" != null)
                return expressionListItem;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public List<ExpressionListItem> PivotMultiInItems()
        throws ParseException {
        List<ExpressionListItem> retval = new ArrayList<ExpressionListItem>();
        ExpressionListItem item;
        item = ExpressionListItem();
        retval.add(item);
        label_18: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 123: {
                    ;
                    break;
                }
                default:
                    jj_la1[70] = jj_gen;
                    break label_18;
            }
            jj_consume_token(123);
            item = ExpressionListItem();
            retval.add(item);
        }
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Pivot Pivot()
        throws ParseException {
        Pivot retval = new Pivot();
        List<FunctionItem> functionItems;
        List<Column> forColumns;
        List<SelectExpressionItem> singleInItems = null;
        List<ExpressionListItem> multiInItems = null;
        jj_consume_token(K_PIVOT);
        jj_consume_token(125);
        functionItems = PivotFunctionItems();
        jj_consume_token(K_FOR);
        forColumns = PivotForColumns();
        jj_consume_token(K_IN);
        jj_consume_token(125);
        if (jj_2_22(3)) {
            singleInItems = PivotSingleInItems();
        }
        else {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 125: {
                    multiInItems = PivotMultiInItems();
                    break;
                }
                default:
                    jj_la1[71] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        jj_consume_token(126);
        jj_consume_token(126);
        retval.setFunctionItems(functionItems);
        retval.setForColumns(forColumns);
        retval.setSingleInItems(singleInItems);
        retval.setMultiInItems(multiInItems);
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public PivotXml PivotXml()
        throws ParseException {
        PivotXml retval = new PivotXml();
        List<FunctionItem> functionItems;
        List<Column> forColumns;
        List<SelectExpressionItem> singleInItems = null;
        List<ExpressionListItem> multiInItems = null;
        SelectBody inSelect = null;
        jj_consume_token(K_PIVOT);
        jj_consume_token(K_XML);
        jj_consume_token(125);
        functionItems = PivotFunctionItems();
        jj_consume_token(K_FOR);
        forColumns = PivotForColumns();
        jj_consume_token(K_IN);
        jj_consume_token(125);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_ANY: {
                jj_consume_token(K_ANY);
                retval.setInAny(true);
                break;
            }
            case K_SELECT:
            case 125: {
                inSelect = SelectBody();
                break;
            }
            default:
                jj_la1[72] = jj_gen;
                if (jj_2_23(2)) {
                    singleInItems = PivotSingleInItems();
                }
                else {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case 125: {
                            multiInItems = PivotMultiInItems();
                            break;
                        }
                        default:
                            jj_la1[73] = jj_gen;
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                }
        }
        jj_consume_token(126);
        jj_consume_token(126);
        retval.setFunctionItems(functionItems);
        retval.setForColumns(forColumns);
        retval.setSingleInItems(singleInItems);
        retval.setMultiInItems(multiInItems);
        retval.setInSelect(inSelect);
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public List<Table> IntoClause()
        throws ParseException {
        List<Table> tables = new ArrayList<Table>();
        Table table;
        jj_consume_token(K_INTO);
        table = Table();
        tables.add(table);
        label_19: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 123: {
                    ;
                    break;
                }
                default:
                    jj_la1[74] = jj_gen;
                    break label_19;
            }
            jj_consume_token(123);
            table = Table();
            tables.add(table);
        }
        {
            if ("" != null)
                return tables;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public FromItem FromItem()
        throws ParseException {
        FromItem fromItem = null;
        Pivot pivot = null;
        Alias alias = null;
        if (jj_2_26(2147483647)) {
            fromItem = ValuesList();
        }
        else {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_DO:
                case K_XML:
                case K_VALUE:
                case K_REPLACE:
                case K_TRUNCATE:
                case K_CAST:
                case K_PARTITION:
                case K_EXTRACT:
                case K_LATERAL:
                case K_MATERIALIZED:
                case K_SIBLINGS:
                case K_COLUMN:
                case K_NULLS:
                case K_FIRST:
                case K_LAST:
                case K_ROWS:
                case K_RANGE:
                case K_FOLLOWING:
                case K_ROW:
                case S_IDENTIFIER:
                case S_QUOTED_IDENTIFIER:
                case 125: {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case 125: {
                            jj_consume_token(125);
                            if (jj_2_24(2147483647)) {
                                fromItem = SubJoin();
                            }
                            else {
                                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                    case K_SELECT:
                                    case 125: {
                                        fromItem = SubSelect();
                                        break;
                                    }
                                    default:
                                        jj_la1[75] = jj_gen;
                                        jj_consume_token(-1);
                                        throw new ParseException();
                                }
                            }
                            jj_consume_token(126);
                            break;
                        }
                        case K_DO:
                        case K_XML:
                        case K_VALUE:
                        case K_REPLACE:
                        case K_TRUNCATE:
                        case K_CAST:
                        case K_PARTITION:
                        case K_EXTRACT:
                        case K_MATERIALIZED:
                        case K_SIBLINGS:
                        case K_COLUMN:
                        case K_NULLS:
                        case K_FIRST:
                        case K_LAST:
                        case K_ROWS:
                        case K_RANGE:
                        case K_FOLLOWING:
                        case K_ROW:
                        case S_IDENTIFIER:
                        case S_QUOTED_IDENTIFIER: {
                            fromItem = Table();
                            break;
                        }
                        case K_LATERAL: {
                            fromItem = LateralSubSelect();
                            break;
                        }
                        default:
                            jj_la1[76] = jj_gen;
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case K_PIVOT: {
                            if (jj_2_25(2)) {
                                pivot = PivotXml();
                            }
                            else {
                                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                    case K_PIVOT: {
                                        pivot = Pivot();
                                        break;
                                    }
                                    default:
                                        jj_la1[77] = jj_gen;
                                        jj_consume_token(-1);
                                        throw new ParseException();
                                }
                            }
                            fromItem.setPivot(pivot);
                            break;
                        }
                        default:
                            jj_la1[78] = jj_gen;
                            ;
                    }
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case K_AS:
                        case K_DO:
                        case K_XML:
                        case K_VALUE:
                        case K_REPLACE:
                        case K_TRUNCATE:
                        case K_CAST:
                        case K_PARTITION:
                        case K_EXTRACT:
                        case K_MATERIALIZED:
                        case K_SIBLINGS:
                        case K_COLUMN:
                        case K_NULLS:
                        case K_FIRST:
                        case K_LAST:
                        case K_ROWS:
                        case K_RANGE:
                        case K_FOLLOWING:
                        case K_ROW:
                        case S_IDENTIFIER:
                        case S_QUOTED_IDENTIFIER: {
                            alias = Alias();
                            fromItem.setAlias(alias);
                            break;
                        }
                        default:
                            jj_la1[79] = jj_gen;
                            ;
                    }
                    break;
                }
                default:
                    jj_la1[80] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        {
            if ("" != null)
                return fromItem;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public FromItem ValuesList()
        throws ParseException {
        MultiExpressionList exprList = new MultiExpressionList();
        List<Expression> primaryExpList = new ArrayList<Expression>();
        ValuesList valuesList = new ValuesList();
        Expression exp = null;
        List<String> colNames = null;
        String colName;
        Alias alias;
        jj_consume_token(125);
        jj_consume_token(K_VALUES);
        if (jj_2_27(3)) {
            jj_consume_token(125);
            exp = SimpleExpression();
            primaryExpList.add(exp);
            label_20: while (true) {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case 123: {
                        ;
                        break;
                    }
                    default:
                        jj_la1[81] = jj_gen;
                        break label_20;
                }
                jj_consume_token(123);
                exp = SimpleExpression();
                primaryExpList.add(exp);
            }
            jj_consume_token(126);
            exprList.addExpressionList(primaryExpList);
            label_21: while (true) {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case 123: {
                        ;
                        break;
                    }
                    default:
                        jj_la1[82] = jj_gen;
                        break label_21;
                }
                jj_consume_token(123);
                jj_consume_token(125);
                exp = SimpleExpression();
                primaryExpList = new ArrayList<Expression>();
                primaryExpList.add(exp);
                label_22: while (true) {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case 123: {
                            ;
                            break;
                        }
                        default:
                            jj_la1[83] = jj_gen;
                            break label_22;
                    }
                    jj_consume_token(123);
                    exp = SimpleExpression();
                    primaryExpList.add(exp);
                }
                jj_consume_token(126);
                exprList.addExpressionList(primaryExpList);
            }
        }
        else {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_DO:
                case K_NULL:
                case K_CASE:
                case K_XML:
                case K_VALUE:
                case K_REPLACE:
                case K_TRUNCATE:
                case K_CAST:
                case K_PARTITION:
                case K_EXTRACT:
                case K_MATERIALIZED:
                case K_INTERVAL:
                case K_SIBLINGS:
                case K_COLUMN:
                case K_NULLS:
                case K_FIRST:
                case K_LAST:
                case K_ROWS:
                case K_RANGE:
                case K_FOLLOWING:
                case K_ROW:
                case S_DOUBLE:
                case S_LONG:
                case S_IDENTIFIER:
                case S_CHAR_LITERAL:
                case S_QUOTED_IDENTIFIER:
                case 125:
                case 129:
                case 145:
                case 146:
                case 150:
                case 152:
                case 153:
                case 155:
                case 157: {
                    exp = SimpleExpression();
                    exprList.addExpressionList(exp);
                    valuesList.setNoBrackets(true);
                    label_23: while (true) {
                        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                            case 123: {
                                ;
                                break;
                            }
                            default:
                                jj_la1[84] = jj_gen;
                                break label_23;
                        }
                        jj_consume_token(123);
                        exp = SimpleExpression();
                        exprList.addExpressionList(exp);
                    }
                    break;
                }
                default:
                    jj_la1[85] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        jj_consume_token(126);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_AS:
            case K_DO:
            case K_XML:
            case K_VALUE:
            case K_REPLACE:
            case K_TRUNCATE:
            case K_CAST:
            case K_PARTITION:
            case K_EXTRACT:
            case K_MATERIALIZED:
            case K_SIBLINGS:
            case K_COLUMN:
            case K_NULLS:
            case K_FIRST:
            case K_LAST:
            case K_ROWS:
            case K_RANGE:
            case K_FOLLOWING:
            case K_ROW:
            case S_IDENTIFIER:
            case S_QUOTED_IDENTIFIER: {
                alias = Alias();
                valuesList.setAlias(alias);
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case 125: {
                        jj_consume_token(125);
                        colName = RelObjectName();
                        colNames = new ArrayList<String>();
                        colNames.add(colName);
                        label_24: while (true) {
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case 123: {
                                    ;
                                    break;
                                }
                                default:
                                    jj_la1[86] = jj_gen;
                                    break label_24;
                            }
                            jj_consume_token(123);
                            colName = RelObjectName();
                            colNames.add(colName);
                        }
                        jj_consume_token(126);
                        valuesList.setColumnNames(colNames);
                        break;
                    }
                    default:
                        jj_la1[87] = jj_gen;
                        ;
                }
                break;
            }
            default:
                jj_la1[88] = jj_gen;
                ;
        }
        valuesList.setMultiExpressionList(exprList);
        {
            if ("" != null)
                return valuesList;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public LateralSubSelect LateralSubSelect()
        throws ParseException {
        LateralSubSelect lateralSubSelect = new LateralSubSelect();
        SubSelect subSelect = null;
        jj_consume_token(K_LATERAL);
        jj_consume_token(125);
        subSelect = SubSelect();
        jj_consume_token(126);
        lateralSubSelect.setSubSelect(subSelect);
        {
            if ("" != null)
                return lateralSubSelect;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public FromItem SubJoin()
        throws ParseException {
        FromItem fromItem = null;
        Join join = null;
        SubJoin subJoin = new SubJoin();
        fromItem = FromItem();
        subJoin.setLeft(fromItem);
        join = JoinerExpression();
        subJoin.setJoin(join);
        {
            if ("" != null)
                return subJoin;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public List JoinsList()
        throws ParseException {
        List<Join> joinsList = new ArrayList<Join>();
        Join join = null;
        label_25: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_JOIN:
                case K_LEFT:
                case K_CROSS:
                case K_FULL:
                case K_INNER:
                case K_RIGHT:
                case K_NATURAL:
                case 123: {
                    ;
                    break;
                }
                default:
                    jj_la1[89] = jj_gen;
                    break label_25;
            }
            join = JoinerExpression();
            joinsList.add(join);
        }
        {
            if ("" != null)
                return joinsList;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Join JoinerExpression()
        throws ParseException {
        Join join = new Join();
        FromItem right = null;
        Expression onExpression = null;
        Column tableColumn;
        List<Column> columns = null;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_LEFT:
            case K_CROSS:
            case K_FULL:
            case K_INNER:
            case K_RIGHT:
            case K_NATURAL: {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_LEFT:
                    case K_FULL:
                    case K_RIGHT: {
                        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                            case K_LEFT: {
                                jj_consume_token(K_LEFT);
                                join.setLeft(true);
                                break;
                            }
                            case K_RIGHT: {
                                jj_consume_token(K_RIGHT);
                                join.setRight(true);
                                break;
                            }
                            case K_FULL: {
                                jj_consume_token(K_FULL);
                                join.setFull(true);
                                break;
                            }
                            default:
                                jj_la1[90] = jj_gen;
                                jj_consume_token(-1);
                                throw new ParseException();
                        }
                        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                            case K_OUTER: {
                                jj_consume_token(K_OUTER);
                                join.setOuter(true);
                                break;
                            }
                            default:
                                jj_la1[91] = jj_gen;
                                ;
                        }
                        break;
                    }
                    case K_INNER: {
                        jj_consume_token(K_INNER);
                        join.setInner(true);
                        break;
                    }
                    case K_NATURAL: {
                        jj_consume_token(K_NATURAL);
                        join.setNatural(true);
                        break;
                    }
                    case K_CROSS: {
                        jj_consume_token(K_CROSS);
                        join.setCross(true);
                        break;
                    }
                    default:
                        jj_la1[92] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                break;
            }
            default:
                jj_la1[93] = jj_gen;
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_JOIN: {
                jj_consume_token(K_JOIN);
                break;
            }
            case 123: {
                jj_consume_token(123);
                join.setSimple(true);
                break;
            }
            default:
                jj_la1[94] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        right = FromItem();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_ON:
            case K_USING: {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_ON: {
                        jj_consume_token(K_ON);
                        onExpression = Expression();
                        join.setOnExpression(onExpression);
                        break;
                    }
                    case K_USING: {
                        jj_consume_token(K_USING);
                        jj_consume_token(125);
                        tableColumn = Column();
                        columns = new ArrayList();
                        columns.add(tableColumn);
                        label_26: while (true) {
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case 123: {
                                    ;
                                    break;
                                }
                                default:
                                    jj_la1[95] = jj_gen;
                                    break label_26;
                            }
                            jj_consume_token(123);
                            tableColumn = Column();
                            columns.add(tableColumn);
                        }
                        jj_consume_token(126);
                        join.setUsingColumns(columns);
                        break;
                    }
                    default:
                        jj_la1[96] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                break;
            }
            default:
                jj_la1[97] = jj_gen;
                ;
        }
        join.setRightItem(right);
        {
            if ("" != null)
                return join;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression WhereClause()
        throws ParseException {
        Expression retval = null;
        jj_consume_token(K_WHERE);
        retval = Expression();
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public OracleHierarchicalExpression OracleHierarchicalQueryClause()
        throws ParseException {
        OracleHierarchicalExpression result = new OracleHierarchicalExpression();
        Expression expr;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_START: {
                jj_consume_token(K_START);
                jj_consume_token(K_WITH);
                expr = AndExpression();
                result.setStartExpression(expr);
                break;
            }
            default:
                jj_la1[98] = jj_gen;
                ;
        }
        jj_consume_token(K_CONNECT);
        jj_consume_token(K_BY);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_NOCYCLE: {
                jj_consume_token(K_NOCYCLE);
                result.setNoCycle(true);
                break;
            }
            default:
                jj_la1[99] = jj_gen;
                ;
        }
        allowOraclePrior = true;
        expr = AndExpression();
        result.setConnectExpression(expr);
        allowOraclePrior = false;
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public List<Expression> GroupByColumnReferences()
        throws ParseException {
        Expression columnReference = null;
        List<Expression> columnReferences = new ArrayList<Expression>();
        jj_consume_token(K_GROUP);
        jj_consume_token(K_BY);
        columnReference = SimpleExpression();
        columnReferences.add(columnReference);
        label_27: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 123: {
                    ;
                    break;
                }
                default:
                    jj_la1[100] = jj_gen;
                    break label_27;
            }
            jj_consume_token(123);
            columnReference = SimpleExpression();
            columnReferences.add(columnReference);
        }
        {
            if ("" != null)
                return columnReferences;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression Having()
        throws ParseException {
        Expression having = null;
        jj_consume_token(K_HAVING);
        having = Expression();
        {
            if ("" != null)
                return having;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public List<OrderByElement> OrderByElements()
        throws ParseException {
        List<OrderByElement> orderByList = new ArrayList<OrderByElement>();
        OrderByElement orderByElement = null;
        jj_consume_token(K_ORDER);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_SIBLINGS: {
                jj_consume_token(K_SIBLINGS);
                break;
            }
            default:
                jj_la1[101] = jj_gen;
                ;
        }
        jj_consume_token(K_BY);
        orderByElement = OrderByElement();
        orderByList.add(orderByElement);
        label_28: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 123: {
                    ;
                    break;
                }
                default:
                    jj_la1[102] = jj_gen;
                    break label_28;
            }
            jj_consume_token(123);
            orderByElement = OrderByElement();
            orderByList.add(orderByElement);
        }
        {
            if ("" != null)
                return orderByList;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public OrderByElement OrderByElement()
        throws ParseException {
        OrderByElement orderByElement = new OrderByElement();
        Expression columnReference = null;
        columnReference = SimpleExpression();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_ASC:
            case K_DESC: {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_ASC: {
                        jj_consume_token(K_ASC);
                        break;
                    }
                    case K_DESC: {
                        jj_consume_token(K_DESC);
                        orderByElement.setAsc(false);
                        break;
                    }
                    default:
                        jj_la1[103] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                orderByElement.setAscDescPresent(true);
                break;
            }
            default:
                jj_la1[104] = jj_gen;
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_NULLS: {
                jj_consume_token(K_NULLS);
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_FIRST:
                    case K_LAST: {
                        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                            case K_FIRST: {
                                jj_consume_token(K_FIRST);
                                orderByElement.setNullOrdering(OrderByElement.NullOrdering.NULLS_FIRST);
                                break;
                            }
                            case K_LAST: {
                                jj_consume_token(K_LAST);
                                orderByElement.setNullOrdering(OrderByElement.NullOrdering.NULLS_LAST);
                                break;
                            }
                            default:
                                jj_la1[105] = jj_gen;
                                jj_consume_token(-1);
                                throw new ParseException();
                        }
                        break;
                    }
                    default:
                        jj_la1[106] = jj_gen;
                        ;
                }
                break;
            }
            default:
                jj_la1[107] = jj_gen;
                ;
        }
        orderByElement.setExpression(columnReference);
        {
            if ("" != null)
                return orderByElement;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Limit Limit()
        throws ParseException {
        Limit limit = new Limit();
        limit.setRowCount(-1l);
        Token token = null;
        if (jj_2_29(3)) {
            jj_consume_token(K_LIMIT);
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case S_LONG: {
                    token = jj_consume_token(S_LONG);
                    limit.setOffset(Long.parseLong(token.image));
                    break;
                }
                case 129: {
                    jj_consume_token(129);
                    limit.setOffsetJdbcParameter(true);
                    break;
                }
                default:
                    jj_la1[108] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            jj_consume_token(123);
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case S_LONG: {
                    token = jj_consume_token(S_LONG);
                    limit.setRowCount(Long.parseLong(token.image));
                    break;
                }
                case 129: {
                    jj_consume_token(129);
                    limit.setRowCountJdbcParameter(true);
                    break;
                }
                default:
                    jj_la1[109] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        else {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_OFFSET: {
                    jj_consume_token(K_OFFSET);
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case S_LONG: {
                            token = jj_consume_token(S_LONG);
                            limit.setOffset(Long.parseLong(token.image));
                            break;
                        }
                        case 129: {
                            jj_consume_token(129);
                            limit.setOffsetJdbcParameter(true);
                            break;
                        }
                        default:
                            jj_la1[110] = jj_gen;
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                    break;
                }
                case K_LIMIT: {
                    jj_consume_token(K_LIMIT);
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case S_LONG: {
                            token = jj_consume_token(S_LONG);
                            limit.setRowCount(Long.parseLong(token.image));
                            break;
                        }
                        case 129: {
                            jj_consume_token(129);
                            limit.setRowCountJdbcParameter(true);
                            break;
                        }
                        case K_ALL: {
                            jj_consume_token(K_ALL);
                            limit.setLimitAll(true);
                            break;
                        }
                        case K_NULL: {
                            jj_consume_token(K_NULL);
                            limit.setLimitNull(true);
                            break;
                        }
                        default:
                            jj_la1[111] = jj_gen;
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                    if (jj_2_28(2)) {
                        jj_consume_token(K_OFFSET);
                        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                            case S_LONG: {
                                token = jj_consume_token(S_LONG);
                                limit.setOffset(Long.parseLong(token.image));
                                break;
                            }
                            case 129: {
                                jj_consume_token(129);
                                limit.setOffsetJdbcParameter(true);
                                break;
                            }
                            default:
                                jj_la1[112] = jj_gen;
                                jj_consume_token(-1);
                                throw new ParseException();
                        }
                    }
                    else {
                        ;
                    }
                    break;
                }
                default:
                    jj_la1[113] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        {
            if ("" != null)
                return limit;
        }
        throw new Error("Missing return statement in function");
    }
    
    // according to http://technet.microsoft.com/en-us/library/ms189463.aspx
    final public Top Top()
        throws ParseException {
        Top top = new Top();
        Token token = null;
        jj_consume_token(K_TOP);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case S_LONG: {
                token = jj_consume_token(S_LONG);
                top.setRowCount(Long.parseLong(token.image));
                break;
            }
            case 129: {
                jj_consume_token(129);
                top.setRowCountJdbcParameter(true);
                break;
            }
            case 125: {
                jj_consume_token(125);
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case S_LONG: {
                        token = jj_consume_token(S_LONG);
                        top.setRowCount(Long.parseLong(token.image));
                        break;
                    }
                    case 129: {
                        jj_consume_token(129);
                        top.setRowCountJdbcParameter(true);
                        break;
                    }
                    default:
                        jj_la1[114] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                top.setParenthesis(true);
                jj_consume_token(126);
                break;
            }
            default:
                jj_la1[115] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_PERCENT: {
                jj_consume_token(K_PERCENT);
                top.setPercentage(true);
                break;
            }
            default:
                jj_la1[116] = jj_gen;
                ;
        }
        {
            if ("" != null)
                return top;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression Expression()
        throws ParseException {
        Expression retval = null;
        if (jj_2_30(2147483647)) {
            retval = OrExpression();
        }
        else {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 125: {
                    jj_consume_token(125);
                    retval = Expression();
                    jj_consume_token(126);
                    retval = new Parenthesis(retval);
                    break;
                }
                default:
                    jj_la1[117] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression OrExpression()
        throws ParseException {
        Expression left, right, result;
        left = AndExpression();
        result = left;
        label_29: while (true) {
            if (jj_2_31(2147483647)) {
                ;
            }
            else {
                break label_29;
            }
            jj_consume_token(K_OR);
            right = AndExpression();
            result = new OrExpression(left, right);
            left = result;
        }
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression AndExpression()
        throws ParseException {
        Expression left, right, result;
        boolean not = false;
        if (jj_2_32(2147483647)) {
            left = Condition();
        }
        else {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_NOT:
                case 125: {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case K_NOT: {
                            jj_consume_token(K_NOT);
                            not = true;
                            break;
                        }
                        default:
                            jj_la1[118] = jj_gen;
                            ;
                    }
                    jj_consume_token(125);
                    left = OrExpression();
                    jj_consume_token(126);
                    left = new Parenthesis(left);
                    if (not) {
                        ((Parenthesis)left).setNot();
                        not = false;
                    }
                    break;
                }
                default:
                    jj_la1[119] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        result = left;
        label_30: while (true) {
            if (jj_2_33(2147483647)) {
                ;
            }
            else {
                break label_30;
            }
            jj_consume_token(K_AND);
            if (jj_2_34(2147483647)) {
                right = Condition();
            }
            else {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_NOT:
                    case 125: {
                        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                            case K_NOT: {
                                jj_consume_token(K_NOT);
                                not = true;
                                break;
                            }
                            default:
                                jj_la1[120] = jj_gen;
                                ;
                        }
                        jj_consume_token(125);
                        right = OrExpression();
                        jj_consume_token(126);
                        right = new Parenthesis(right);
                        if (not) {
                            ((Parenthesis)right).setNot();
                            not = false;
                        }
                        break;
                    }
                    default:
                        jj_la1[121] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
            }
            result = new AndExpression(left, right);
            left = result;
        }
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression Condition()
        throws ParseException {
        Expression result;
        if (jj_2_35(2147483647)) {
            result = SQLCondition();
        }
        else if (jj_2_36(2147483647)) {
            result = RegularCondition();
        }
        else {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_DO:
                case K_XML:
                case K_VALUE:
                case K_REPLACE:
                case K_TRUNCATE:
                case K_CAST:
                case K_PARTITION:
                case K_EXTRACT:
                case K_MATERIALIZED:
                case K_SIBLINGS:
                case K_COLUMN:
                case K_NULLS:
                case K_FIRST:
                case K_LAST:
                case K_ROWS:
                case K_RANGE:
                case K_FOLLOWING:
                case K_ROW:
                case S_IDENTIFIER:
                case S_QUOTED_IDENTIFIER:
                case 157: {
                    result = Function();
                    break;
                }
                default:
                    jj_la1[122] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression RegularCondition()
        throws ParseException {
        Expression result = null;
        Expression leftExpression;
        Expression rightExpression;
        boolean not = false;
        int oracleJoin = EqualsTo.NO_ORACLE_JOIN;
        int oraclePrior = EqualsTo.NO_ORACLE_PRIOR;
        boolean binary = false;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_PRIOR: {
                jj_consume_token(K_PRIOR);
                oraclePrior = EqualsTo.ORACLE_PRIOR_START;
                break;
            }
            default:
                jj_la1[123] = jj_gen;
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_NOT: {
                jj_consume_token(K_NOT);
                not = true;
                break;
            }
            default:
                jj_la1[124] = jj_gen;
                ;
        }
        leftExpression = ComparisonItem();
        result = leftExpression;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case 130: {
                jj_consume_token(130);
                oracleJoin = EqualsTo.ORACLE_JOIN_RIGHT;
                break;
            }
            default:
                jj_la1[125] = jj_gen;
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case 131: {
                jj_consume_token(131);
                result = new GreaterThan();
                break;
            }
            case 132: {
                jj_consume_token(132);
                result = new MinorThan();
                break;
            }
            case 124: {
                jj_consume_token(124);
                result = new EqualsTo();
                break;
            }
            case 133: {
                jj_consume_token(133);
                result = new GreaterThanEquals();
                break;
            }
            case 134: {
                jj_consume_token(134);
                result = new MinorThanEquals();
                break;
            }
            case 135: {
                jj_consume_token(135);
                result = new NotEqualsTo();
                break;
            }
            case 136: {
                jj_consume_token(136);
                result = new NotEqualsTo("!=");
                break;
            }
            case 137: {
                jj_consume_token(137);
                result = new Matches();
                break;
            }
            case 138: {
                jj_consume_token(138);
                result = new RegExpMatchOperator(RegExpMatchOperatorType.MATCH_CASESENSITIVE);
                break;
            }
            case K_REGEXP: {
                jj_consume_token(K_REGEXP);
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_BINARY: {
                        jj_consume_token(K_BINARY);
                        binary = true;
                        break;
                    }
                    default:
                        jj_la1[126] = jj_gen;
                        ;
                }
                result =
                    new RegExpMySQLOperator(binary ? RegExpMatchOperatorType.MATCH_CASESENSITIVE
                        : RegExpMatchOperatorType.MATCH_CASEINSENSITIVE);
                break;
            }
            case 139: {
                jj_consume_token(139);
                result = new RegExpMatchOperator(RegExpMatchOperatorType.MATCH_CASEINSENSITIVE);
                break;
            }
            case 140: {
                jj_consume_token(140);
                result = new RegExpMatchOperator(RegExpMatchOperatorType.NOT_MATCH_CASESENSITIVE);
                break;
            }
            case 141: {
                jj_consume_token(141);
                result = new RegExpMatchOperator(RegExpMatchOperatorType.NOT_MATCH_CASEINSENSITIVE);
                break;
            }
            default:
                jj_la1[127] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        rightExpression = ComparisonItem();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_PRIOR: {
                jj_consume_token(K_PRIOR);
                oraclePrior = EqualsTo.ORACLE_PRIOR_END;
                break;
            }
            default:
                jj_la1[128] = jj_gen;
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case 130: {
                jj_consume_token(130);
                oracleJoin = EqualsTo.ORACLE_JOIN_LEFT;
                break;
            }
            default:
                jj_la1[129] = jj_gen;
                ;
        }
        BinaryExpression regCond = (BinaryExpression)result;
        regCond.setLeftExpression(leftExpression);
        regCond.setRightExpression(rightExpression);
        if (not)
            regCond.setNot();
        
        if (oracleJoin > 0)
            ((SupportsOldOracleJoinSyntax)result).setOldOracleJoinSyntax(oracleJoin);
        
        if (oraclePrior != EqualsTo.NO_ORACLE_PRIOR)
            ((SupportsOldOracleJoinSyntax)result).setOraclePriorPosition(oraclePrior);
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression SQLCondition()
        throws ParseException {
        Expression result;
        if (jj_2_37(2147483647)) {
            result = InExpression();
        }
        else if (jj_2_38(2147483647)) {
            result = Between();
        }
        else if (jj_2_39(2147483647)) {
            result = IsNullExpression();
        }
        else if (jj_2_40(2147483647)) {
            result = ExistsExpression();
        }
        else {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_DO:
                case K_NULL:
                case K_CASE:
                case K_XML:
                case K_VALUE:
                case K_REPLACE:
                case K_TRUNCATE:
                case K_CAST:
                case K_PARTITION:
                case K_EXTRACT:
                case K_MATERIALIZED:
                case K_INTERVAL:
                case K_SIBLINGS:
                case K_COLUMN:
                case K_NULLS:
                case K_FIRST:
                case K_LAST:
                case K_ROWS:
                case K_RANGE:
                case K_FOLLOWING:
                case K_ROW:
                case S_DOUBLE:
                case S_LONG:
                case S_IDENTIFIER:
                case S_CHAR_LITERAL:
                case S_QUOTED_IDENTIFIER:
                case 125:
                case 129:
                case 145:
                case 146:
                case 150:
                case 152:
                case 153:
                case 155:
                case 157: {
                    result = LikeExpression();
                    break;
                }
                default:
                    jj_la1[130] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression InExpression()
        throws ParseException {
        InExpression result = new InExpression();
        ItemsList leftItemsList = null;
        ItemsList rightItemsList = null;
        Expression leftExpression = null;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case 125: {
                jj_consume_token(125);
                if (jj_2_41(2147483647)) {
                    leftItemsList = SimpleExpressionList();
                    result.setLeftItemsList(leftItemsList);
                }
                else {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case K_DO:
                        case K_NULL:
                        case K_CASE:
                        case K_XML:
                        case K_VALUE:
                        case K_REPLACE:
                        case K_TRUNCATE:
                        case K_CAST:
                        case K_PARTITION:
                        case K_EXTRACT:
                        case K_MATERIALIZED:
                        case K_INTERVAL:
                        case K_SIBLINGS:
                        case K_COLUMN:
                        case K_NULLS:
                        case K_FIRST:
                        case K_LAST:
                        case K_ROWS:
                        case K_RANGE:
                        case K_FOLLOWING:
                        case K_ROW:
                        case S_DOUBLE:
                        case S_LONG:
                        case S_IDENTIFIER:
                        case S_CHAR_LITERAL:
                        case S_QUOTED_IDENTIFIER:
                        case 125:
                        case 129:
                        case 145:
                        case 146:
                        case 150:
                        case 152:
                        case 153:
                        case 155:
                        case 157: {
                            leftExpression = SimpleExpression();
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case 130: {
                                    jj_consume_token(130);
                                    result.setOldOracleJoinSyntax(EqualsTo.ORACLE_JOIN_RIGHT);
                                    break;
                                }
                                default:
                                    jj_la1[131] = jj_gen;
                                    ;
                            }
                            break;
                        }
                        default:
                            jj_la1[132] = jj_gen;
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                }
                jj_consume_token(126);
                break;
            }
            case K_DO:
            case K_NULL:
            case K_CASE:
            case K_XML:
            case K_VALUE:
            case K_REPLACE:
            case K_TRUNCATE:
            case K_CAST:
            case K_PARTITION:
            case K_EXTRACT:
            case K_MATERIALIZED:
            case K_INTERVAL:
            case K_SIBLINGS:
            case K_COLUMN:
            case K_NULLS:
            case K_FIRST:
            case K_LAST:
            case K_ROWS:
            case K_RANGE:
            case K_FOLLOWING:
            case K_ROW:
            case S_DOUBLE:
            case S_LONG:
            case S_IDENTIFIER:
            case S_CHAR_LITERAL:
            case S_QUOTED_IDENTIFIER:
            case 129:
            case 145:
            case 146:
            case 150:
            case 152:
            case 153:
            case 155:
            case 157: {
                leftExpression = SimpleExpression();
                result.setLeftExpression(leftExpression);
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case 130: {
                        jj_consume_token(130);
                        result.setOldOracleJoinSyntax(EqualsTo.ORACLE_JOIN_RIGHT);
                        break;
                    }
                    default:
                        jj_la1[133] = jj_gen;
                        ;
                }
                break;
            }
            default:
                jj_la1[134] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_NOT: {
                jj_consume_token(K_NOT);
                result.setNot(true);
                break;
            }
            default:
                jj_la1[135] = jj_gen;
                ;
        }
        jj_consume_token(K_IN);
        jj_consume_token(125);
        if (jj_2_42(2147483647)) {
            rightItemsList = SubSelect();
        }
        else {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_DO:
                case K_NULL:
                case K_CASE:
                case K_XML:
                case K_VALUE:
                case K_REPLACE:
                case K_TRUNCATE:
                case K_CAST:
                case K_PARTITION:
                case K_EXTRACT:
                case K_MATERIALIZED:
                case K_INTERVAL:
                case K_SIBLINGS:
                case K_COLUMN:
                case K_NULLS:
                case K_FIRST:
                case K_LAST:
                case K_ROWS:
                case K_RANGE:
                case K_FOLLOWING:
                case K_ROW:
                case S_DOUBLE:
                case S_LONG:
                case S_IDENTIFIER:
                case S_CHAR_LITERAL:
                case S_QUOTED_IDENTIFIER:
                case 125:
                case 129:
                case 145:
                case 146:
                case 150:
                case 152:
                case 153:
                case 155:
                case 157: {
                    rightItemsList = SimpleExpressionList();
                    break;
                }
                default:
                    jj_la1[136] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        jj_consume_token(126);
        result.setRightItemsList(rightItemsList);
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression Between()
        throws ParseException {
        Between result = new Between();
        Expression leftExpression = null;
        Expression betweenExpressionStart = null;
        Expression betweenExpressionEnd = null;
        leftExpression = SimpleExpression();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_NOT: {
                jj_consume_token(K_NOT);
                result.setNot(true);
                break;
            }
            default:
                jj_la1[137] = jj_gen;
                ;
        }
        jj_consume_token(K_BETWEEN);
        betweenExpressionStart = SimpleExpression();
        jj_consume_token(K_AND);
        betweenExpressionEnd = SimpleExpression();
        result.setLeftExpression(leftExpression);
        result.setBetweenExpressionStart(betweenExpressionStart);
        result.setBetweenExpressionEnd(betweenExpressionEnd);
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression LikeExpression()
        throws ParseException {
        LikeExpression result = new LikeExpression();
        Expression leftExpression = null;
        Expression rightExpression = null;
        leftExpression = SimpleExpression();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_NOT: {
                jj_consume_token(K_NOT);
                result.setNot(true);
                break;
            }
            default:
                jj_la1[138] = jj_gen;
                ;
        }
        jj_consume_token(K_LIKE);
        rightExpression = SimpleExpression();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_ESCAPE: {
                jj_consume_token(K_ESCAPE);
                token = jj_consume_token(S_CHAR_LITERAL);
                result.setEscape((new StringValue(token.image)).getValue());
                break;
            }
            default:
                jj_la1[139] = jj_gen;
                ;
        }
        result.setLeftExpression(leftExpression);
        result.setRightExpression(rightExpression);
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression IsNullExpression()
        throws ParseException {
        IsNullExpression result = new IsNullExpression();
        Expression leftExpression = null;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_NOT: {
                jj_consume_token(K_NOT);
                result.setNot(true);
                leftExpression = SimpleExpression();
                jj_consume_token(K_IS);
                jj_consume_token(K_NULL);
                break;
            }
            case K_DO:
            case K_NULL:
            case K_CASE:
            case K_XML:
            case K_VALUE:
            case K_REPLACE:
            case K_TRUNCATE:
            case K_CAST:
            case K_PARTITION:
            case K_EXTRACT:
            case K_MATERIALIZED:
            case K_INTERVAL:
            case K_SIBLINGS:
            case K_COLUMN:
            case K_NULLS:
            case K_FIRST:
            case K_LAST:
            case K_ROWS:
            case K_RANGE:
            case K_FOLLOWING:
            case K_ROW:
            case S_DOUBLE:
            case S_LONG:
            case S_IDENTIFIER:
            case S_CHAR_LITERAL:
            case S_QUOTED_IDENTIFIER:
            case 125:
            case 129:
            case 145:
            case 146:
            case 150:
            case 152:
            case 153:
            case 155:
            case 157: {
                leftExpression = SimpleExpression();
                jj_consume_token(K_IS);
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_NOT: {
                        jj_consume_token(K_NOT);
                        result.setNot(true);
                        break;
                    }
                    default:
                        jj_la1[140] = jj_gen;
                        ;
                }
                jj_consume_token(K_NULL);
                break;
            }
            default:
                jj_la1[141] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        result.setLeftExpression(leftExpression);
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression ExistsExpression()
        throws ParseException {
        ExistsExpression result = new ExistsExpression();
        Expression rightExpression = null;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_NOT: {
                jj_consume_token(K_NOT);
                result.setNot(true);
                break;
            }
            default:
                jj_la1[142] = jj_gen;
                ;
        }
        jj_consume_token(K_EXISTS);
        rightExpression = SimpleExpression();
        result.setRightExpression(rightExpression);
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public ExpressionList SQLExpressionList()
        throws ParseException {
        ExpressionList retval = new ExpressionList();
        List<Expression> expressions = new ArrayList<Expression>();
        Expression expr = null;
        expr = Expression();
        expressions.add(expr);
        label_31: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 123: {
                    ;
                    break;
                }
                default:
                    jj_la1[143] = jj_gen;
                    break label_31;
            }
            jj_consume_token(123);
            expr = Expression();
            expressions.add(expr);
        }
        retval.setExpressions(expressions);
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public ExpressionList SimpleExpressionList()
        throws ParseException {
        ExpressionList retval = new ExpressionList();
        List<Expression> expressions = new ArrayList<Expression>();
        Expression expr = null;
        expr = SimpleExpression();
        expressions.add(expr);
        label_32: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 123: {
                    ;
                    break;
                }
                default:
                    jj_la1[144] = jj_gen;
                    break label_32;
            }
            jj_consume_token(123);
            expr = SimpleExpression();
            expressions.add(expr);
        }
        retval.setExpressions(expressions);
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression ComparisonItem()
        throws ParseException {
        Expression retval = null;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_ALL: {
                retval = AllComparisonExpression();
                break;
            }
            case K_ANY:
            case K_SOME: {
                retval = AnyComparisonExpression();
                break;
            }
            case K_DO:
            case K_NULL:
            case K_CASE:
            case K_XML:
            case K_VALUE:
            case K_REPLACE:
            case K_TRUNCATE:
            case K_CAST:
            case K_PARTITION:
            case K_EXTRACT:
            case K_MATERIALIZED:
            case K_INTERVAL:
            case K_SIBLINGS:
            case K_COLUMN:
            case K_NULLS:
            case K_FIRST:
            case K_LAST:
            case K_ROWS:
            case K_RANGE:
            case K_FOLLOWING:
            case K_ROW:
            case S_DOUBLE:
            case S_LONG:
            case S_IDENTIFIER:
            case S_CHAR_LITERAL:
            case S_QUOTED_IDENTIFIER:
            case 125:
            case 129:
            case 145:
            case 146:
            case 150:
            case 152:
            case 153:
            case 155:
            case 157: {
                retval = SimpleExpression();
                break;
            }
            default:
                jj_la1[145] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression AllComparisonExpression()
        throws ParseException {
        AllComparisonExpression retval = null;
        SubSelect subselect = null;
        jj_consume_token(K_ALL);
        jj_consume_token(125);
        subselect = SubSelect();
        jj_consume_token(126);
        retval = new AllComparisonExpression(subselect);
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression AnyComparisonExpression()
        throws ParseException {
        AnyComparisonExpression retval = null;
        SubSelect subselect = null;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_ANY: {
                jj_consume_token(K_ANY);
                break;
            }
            case K_SOME: {
                jj_consume_token(K_SOME);
                break;
            }
            default:
                jj_la1[146] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        jj_consume_token(125);
        subselect = SubSelect();
        jj_consume_token(126);
        retval = new AnyComparisonExpression(subselect);
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression SimpleExpression()
        throws ParseException {
        Expression retval = null;
        if (jj_2_43(2147483647)) {
            retval = BitwiseAndOr();
        }
        else {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 125: {
                    jj_consume_token(125);
                    retval = BitwiseAndOr();
                    jj_consume_token(126);
                    retval = new Parenthesis(retval);
                    break;
                }
                default:
                    jj_la1[147] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression ConcatExpression()
        throws ParseException {
        Expression result = null;
        Expression leftExpression = null;
        Expression rightExpression = null;
        leftExpression = AdditiveExpression();
        result = leftExpression;
        label_33: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 142: {
                    ;
                    break;
                }
                default:
                    jj_la1[148] = jj_gen;
                    break label_33;
            }
            jj_consume_token(142);
            rightExpression = AdditiveExpression();
            Concat binExp = new Concat();
            binExp.setLeftExpression(leftExpression);
            binExp.setRightExpression(rightExpression);
            result = binExp;
            leftExpression = result;
        }
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression BitwiseAndOr()
        throws ParseException {
        Expression result = null;
        Expression leftExpression = null;
        Expression rightExpression = null;
        leftExpression = ConcatExpression();
        result = leftExpression;
        label_34: while (true) {
            if (jj_2_44(2)) {
                ;
            }
            else {
                break label_34;
            }
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 143: {
                    jj_consume_token(143);
                    result = new BitwiseOr();
                    break;
                }
                case 144: {
                    jj_consume_token(144);
                    result = new BitwiseAnd();
                    break;
                }
                default:
                    jj_la1[149] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            rightExpression = ConcatExpression();
            BinaryExpression binExp = (BinaryExpression)result;
            binExp.setLeftExpression(leftExpression);
            binExp.setRightExpression(rightExpression);
            leftExpression = result;
        }
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression AdditiveExpression()
        throws ParseException {
        Expression result = null;
        Expression leftExpression = null;
        Expression rightExpression = null;
        leftExpression = MultiplicativeExpression();
        result = leftExpression;
        label_35: while (true) {
            if (jj_2_45(2)) {
                ;
            }
            else {
                break label_35;
            }
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 145: {
                    jj_consume_token(145);
                    result = new Addition();
                    break;
                }
                case 146: {
                    jj_consume_token(146);
                    result = new Subtraction();
                    break;
                }
                default:
                    jj_la1[150] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            rightExpression = MultiplicativeExpression();
            BinaryExpression binExp = (BinaryExpression)result;
            binExp.setLeftExpression(leftExpression);
            binExp.setRightExpression(rightExpression);
            leftExpression = result;
        }
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression MultiplicativeExpression()
        throws ParseException {
        Expression result = null;
        Expression leftExpression = null;
        Expression rightExpression = null;
        if (jj_2_46(2147483647)) {
            leftExpression = BitwiseXor();
        }
        else {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 125: {
                    jj_consume_token(125);
                    leftExpression = ConcatExpression();
                    jj_consume_token(126);
                    leftExpression = new Parenthesis(leftExpression);
                    break;
                }
                default:
                    jj_la1[151] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        result = leftExpression;
        label_36: while (true) {
            if (jj_2_47(2)) {
                ;
            }
            else {
                break label_36;
            }
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 127: {
                    jj_consume_token(127);
                    result = new Multiplication();
                    break;
                }
                case 147: {
                    jj_consume_token(147);
                    result = new Division();
                    break;
                }
                case 148: {
                    jj_consume_token(148);
                    result = new Modulo();
                    break;
                }
                default:
                    jj_la1[152] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            if (jj_2_48(2147483647)) {
                rightExpression = BitwiseXor();
            }
            else {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case 125: {
                        jj_consume_token(125);
                        rightExpression = ConcatExpression();
                        jj_consume_token(126);
                        rightExpression = new Parenthesis(rightExpression);
                        break;
                    }
                    default:
                        jj_la1[153] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
            }
            BinaryExpression binExp = (BinaryExpression)result;
            binExp.setLeftExpression(leftExpression);
            binExp.setRightExpression(rightExpression);
            leftExpression = result;
        }
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression BitwiseXor()
        throws ParseException {
        Expression result = null;
        Expression leftExpression = null;
        Expression rightExpression = null;
        leftExpression = PrimaryExpression();
        result = leftExpression;
        label_37: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 149: {
                    ;
                    break;
                }
                default:
                    jj_la1[154] = jj_gen;
                    break label_37;
            }
            jj_consume_token(149);
            rightExpression = PrimaryExpression();
            BitwiseXor binExp = new BitwiseXor();
            binExp.setLeftExpression(leftExpression);
            binExp.setRightExpression(rightExpression);
            result = binExp;
            leftExpression = result;
        }
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression PrimaryExpression()
        throws ParseException {
        Expression retval = null;
        CastExpression castExpr = null;
        Token token = null;
        Token sign = null;
        String tmp = "";
        ColDataType type = null;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_NULL: {
                jj_consume_token(K_NULL);
                retval = new NullValue();
                break;
            }
            case K_CASE: {
                retval = CaseWhenExpression();
                break;
            }
            case 129: {
                jj_consume_token(129);
                retval = new JdbcParameter();
                break;
            }
            case 155: {
                retval = JdbcNamedParameter();
                break;
            }
            default:
                jj_la1[171] = jj_gen;
                if (jj_2_49(2147483647)) {
                    retval = AnalyticExpression();
                }
                else if (jj_2_50(2147483647)) {
                    retval = ExtractExpression();
                }
                else if (jj_2_51(2147483647)) {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case 145:
                        case 146: {
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case 145: {
                                    sign = jj_consume_token(145);
                                    break;
                                }
                                case 146: {
                                    sign = jj_consume_token(146);
                                    break;
                                }
                                default:
                                    jj_la1[155] = jj_gen;
                                    jj_consume_token(-1);
                                    throw new ParseException();
                            }
                            break;
                        }
                        default:
                            jj_la1[156] = jj_gen;
                            ;
                    }
                    retval = JsonExpression();
                }
                else if (jj_2_52(2147483647)) {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case 145:
                        case 146: {
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case 145: {
                                    sign = jj_consume_token(145);
                                    break;
                                }
                                case 146: {
                                    sign = jj_consume_token(146);
                                    break;
                                }
                                default:
                                    jj_la1[157] = jj_gen;
                                    jj_consume_token(-1);
                                    throw new ParseException();
                            }
                            break;
                        }
                        default:
                            jj_la1[158] = jj_gen;
                            ;
                    }
                    retval = Function();
                }
                else if (jj_2_53(2147483647)) {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case 145:
                        case 146: {
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case 145: {
                                    sign = jj_consume_token(145);
                                    break;
                                }
                                case 146: {
                                    sign = jj_consume_token(146);
                                    break;
                                }
                                default:
                                    jj_la1[159] = jj_gen;
                                    jj_consume_token(-1);
                                    throw new ParseException();
                            }
                            break;
                        }
                        default:
                            jj_la1[160] = jj_gen;
                            ;
                    }
                    token = jj_consume_token(S_DOUBLE);
                    retval = new DoubleValue(token.image);
                }
                else if (jj_2_54(2147483647)) {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case 145:
                        case 146: {
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case 145: {
                                    sign = jj_consume_token(145);
                                    break;
                                }
                                case 146: {
                                    sign = jj_consume_token(146);
                                    break;
                                }
                                default:
                                    jj_la1[161] = jj_gen;
                                    jj_consume_token(-1);
                                    throw new ParseException();
                            }
                            break;
                        }
                        default:
                            jj_la1[162] = jj_gen;
                            ;
                    }
                    token = jj_consume_token(S_LONG);
                    retval = new LongValue(token.image);
                }
                else if (jj_2_55(2147483647)) {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case 145:
                        case 146: {
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case 145: {
                                    sign = jj_consume_token(145);
                                    break;
                                }
                                case 146: {
                                    sign = jj_consume_token(146);
                                    break;
                                }
                                default:
                                    jj_la1[163] = jj_gen;
                                    jj_consume_token(-1);
                                    throw new ParseException();
                            }
                            break;
                        }
                        default:
                            jj_la1[164] = jj_gen;
                            ;
                    }
                    retval = CastExpression();
                }
                else if (jj_2_56(2147483647)) {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case 145:
                        case 146: {
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case 145: {
                                    sign = jj_consume_token(145);
                                    break;
                                }
                                case 146: {
                                    sign = jj_consume_token(146);
                                    break;
                                }
                                default:
                                    jj_la1[165] = jj_gen;
                                    jj_consume_token(-1);
                                    throw new ParseException();
                            }
                            break;
                        }
                        default:
                            jj_la1[166] = jj_gen;
                            ;
                    }
                    retval = Column();
                }
                else if (jj_2_57(2)) {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case 145:
                        case 146: {
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case 145: {
                                    sign = jj_consume_token(145);
                                    break;
                                }
                                case 146: {
                                    sign = jj_consume_token(146);
                                    break;
                                }
                                default:
                                    jj_la1[167] = jj_gen;
                                    jj_consume_token(-1);
                                    throw new ParseException();
                            }
                            break;
                        }
                        default:
                            jj_la1[168] = jj_gen;
                            ;
                    }
                    jj_consume_token(125);
                    retval = PrimaryExpression();
                    jj_consume_token(126);
                    retval = new Parenthesis(retval);
                }
                else {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case 125:
                        case 145:
                        case 146: {
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case 145:
                                case 146: {
                                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                        case 145: {
                                            sign = jj_consume_token(145);
                                            break;
                                        }
                                        case 146: {
                                            sign = jj_consume_token(146);
                                            break;
                                        }
                                        default:
                                            jj_la1[169] = jj_gen;
                                            jj_consume_token(-1);
                                            throw new ParseException();
                                    }
                                    break;
                                }
                                default:
                                    jj_la1[170] = jj_gen;
                                    ;
                            }
                            jj_consume_token(125);
                            retval = SubSelect();
                            jj_consume_token(126);
                            break;
                        }
                        case S_CHAR_LITERAL: {
                            token = jj_consume_token(S_CHAR_LITERAL);
                            retval = new StringValue(token.image);
                            break;
                        }
                        case 150: {
                            jj_consume_token(150);
                            token = jj_consume_token(S_CHAR_LITERAL);
                            jj_consume_token(151);
                            retval = new DateValue(token.image);
                            break;
                        }
                        case 152: {
                            jj_consume_token(152);
                            token = jj_consume_token(S_CHAR_LITERAL);
                            jj_consume_token(151);
                            retval = new TimeValue(token.image);
                            break;
                        }
                        case 153: {
                            jj_consume_token(153);
                            token = jj_consume_token(S_CHAR_LITERAL);
                            jj_consume_token(151);
                            retval = new TimestampValue(token.image);
                            break;
                        }
                        case K_INTERVAL: {
                            retval = IntervalExpression();
                            break;
                        }
                        default:
                            jj_la1[172] = jj_gen;
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                }
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case 154: {
                jj_consume_token(154);
                type = ColDataType();
                castExpr = new CastExpression();
                castExpr.setUseCastKeyword(false);
                castExpr.setLeftExpression(retval);
                castExpr.setType(type);
                retval = castExpr;
                break;
            }
            default:
                jj_la1[173] = jj_gen;
                ;
        }
        if (sign != null) {
            retval = new SignedExpression(sign.image.charAt(0), retval);
        }
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public JdbcNamedParameter JdbcNamedParameter()
        throws ParseException {
        JdbcNamedParameter parameter = new JdbcNamedParameter();
        Token token;
        jj_consume_token(155);
        token = jj_consume_token(S_IDENTIFIER);
        parameter.setName(token.image);
        {
            if ("" != null)
                return parameter;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public JsonExpression JsonExpression()
        throws ParseException {
        JsonExpression result = new JsonExpression();
        Column column;
        Token token;
        column = Column();
        label_38: while (true) {
            jj_consume_token(156);
            token = jj_consume_token(S_CHAR_LITERAL);
            result.addIdent(token.image);
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 156: {
                    ;
                    break;
                }
                default:
                    jj_la1[174] = jj_gen;
                    break label_38;
            }
        }
        result.setColumn(column);
        {
            if ("" != null)
                return result;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public IntervalExpression IntervalExpression()
        throws ParseException {
        IntervalExpression interval = new IntervalExpression();
        Token token;
        jj_consume_token(K_INTERVAL);
        token = jj_consume_token(S_CHAR_LITERAL);
        interval.setParameter(token.image);
        {
            if ("" != null)
                return interval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public AnalyticExpression AnalyticExpression()
        throws ParseException {
        AnalyticExpression retval = new AnalyticExpression();
        ExpressionList expressionList = null;
        List<OrderByElement> olist = null;
        Token token = null;
        Expression expr = null;
        Expression offset = null;
        Expression defaultValue = null;
        WindowElement windowElement = null;
        token = jj_consume_token(S_IDENTIFIER);
        retval.setName(token.image);
        jj_consume_token(125);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_DO:
            case K_NULL:
            case K_CASE:
            case K_XML:
            case K_VALUE:
            case K_REPLACE:
            case K_TRUNCATE:
            case K_CAST:
            case K_PARTITION:
            case K_EXTRACT:
            case K_MATERIALIZED:
            case K_INTERVAL:
            case K_SIBLINGS:
            case K_COLUMN:
            case K_NULLS:
            case K_FIRST:
            case K_LAST:
            case K_ROWS:
            case K_RANGE:
            case K_FOLLOWING:
            case K_ROW:
            case S_DOUBLE:
            case S_LONG:
            case S_IDENTIFIER:
            case S_CHAR_LITERAL:
            case S_QUOTED_IDENTIFIER:
            case 125:
            case 127:
            case 129:
            case 145:
            case 146:
            case 150:
            case 152:
            case 153:
            case 155:
            case 157: {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_DO:
                    case K_NULL:
                    case K_CASE:
                    case K_XML:
                    case K_VALUE:
                    case K_REPLACE:
                    case K_TRUNCATE:
                    case K_CAST:
                    case K_PARTITION:
                    case K_EXTRACT:
                    case K_MATERIALIZED:
                    case K_INTERVAL:
                    case K_SIBLINGS:
                    case K_COLUMN:
                    case K_NULLS:
                    case K_FIRST:
                    case K_LAST:
                    case K_ROWS:
                    case K_RANGE:
                    case K_FOLLOWING:
                    case K_ROW:
                    case S_DOUBLE:
                    case S_LONG:
                    case S_IDENTIFIER:
                    case S_CHAR_LITERAL:
                    case S_QUOTED_IDENTIFIER:
                    case 125:
                    case 129:
                    case 145:
                    case 146:
                    case 150:
                    case 152:
                    case 153:
                    case 155:
                    case 157: {
                        expr = SimpleExpression();
                        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                            case 123: {
                                jj_consume_token(123);
                                offset = SimpleExpression();
                                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                    case 123: {
                                        jj_consume_token(123);
                                        defaultValue = SimpleExpression();
                                        break;
                                    }
                                    default:
                                        jj_la1[175] = jj_gen;
                                        ;
                                }
                                break;
                            }
                            default:
                                jj_la1[176] = jj_gen;
                                ;
                        }
                        break;
                    }
                    case 127: {
                        jj_consume_token(127);
                        retval.setAllColumns(true);
                        break;
                    }
                    default:
                        jj_la1[177] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                break;
            }
            default:
                jj_la1[178] = jj_gen;
                ;
        }
        jj_consume_token(126);
        jj_consume_token(K_OVER);
        jj_consume_token(125);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_PARTITION: {
                jj_consume_token(K_PARTITION);
                jj_consume_token(K_BY);
                expressionList = SimpleExpressionList();
                break;
            }
            default:
                jj_la1[179] = jj_gen;
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_ORDER: {
                olist = OrderByElements();
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_ROWS:
                    case K_RANGE: {
                        windowElement = WindowElement();
                        break;
                    }
                    default:
                        jj_la1[180] = jj_gen;
                        ;
                }
                break;
            }
            default:
                jj_la1[181] = jj_gen;
                ;
        }
        retval.setExpression(expr);
        retval.setOffset(offset);
        retval.setDefaultValue(defaultValue);
        retval.setPartitionExpressionList(expressionList);
        retval.setOrderByElements(olist);
        retval.setWindowElement(windowElement);
        jj_consume_token(126);
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public WindowElement WindowElement()
        throws ParseException {
        WindowElement windowElement = new WindowElement();
        WindowRange range = new WindowRange();
        WindowOffset offset = null;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_ROWS: {
                jj_consume_token(K_ROWS);
                windowElement.setType(WindowElement.Type.ROWS);
                break;
            }
            case K_RANGE: {
                jj_consume_token(K_RANGE);
                windowElement.setType(WindowElement.Type.RANGE);
                break;
            }
            default:
                jj_la1[182] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_BETWEEN: {
                jj_consume_token(K_BETWEEN);
                windowElement.setRange(range);
                offset = WindowOffset();
                range.setStart(offset);
                jj_consume_token(K_AND);
                offset = WindowOffset();
                range.setEnd(offset);
                break;
            }
            case K_DO:
            case K_NULL:
            case K_CASE:
            case K_XML:
            case K_VALUE:
            case K_REPLACE:
            case K_TRUNCATE:
            case K_CAST:
            case K_PARTITION:
            case K_EXTRACT:
            case K_MATERIALIZED:
            case K_INTERVAL:
            case K_SIBLINGS:
            case K_COLUMN:
            case K_NULLS:
            case K_FIRST:
            case K_LAST:
            case K_ROWS:
            case K_RANGE:
            case K_UNBOUNDED:
            case K_FOLLOWING:
            case K_CURRENT:
            case K_ROW:
            case S_DOUBLE:
            case S_LONG:
            case S_IDENTIFIER:
            case S_CHAR_LITERAL:
            case S_QUOTED_IDENTIFIER:
            case 125:
            case 129:
            case 145:
            case 146:
            case 150:
            case 152:
            case 153:
            case 155:
            case 157: {
                offset = WindowOffset();
                windowElement.setOffset(offset);
                break;
            }
            default:
                jj_la1[183] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        {
            if ("" != null)
                return windowElement;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public WindowOffset WindowOffset()
        throws ParseException {
        WindowOffset offset = new WindowOffset();
        Expression expr = null;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_UNBOUNDED: {
                jj_consume_token(K_UNBOUNDED);
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_PRECEDING: {
                        jj_consume_token(K_PRECEDING);
                        offset.setType(WindowOffset.Type.PRECEDING);
                        {
                            if ("" != null)
                                return offset;
                        }
                        break;
                    }
                    case K_FOLLOWING: {
                        jj_consume_token(K_FOLLOWING);
                        offset.setType(WindowOffset.Type.FOLLOWING);
                        {
                            if ("" != null)
                                return offset;
                        }
                        break;
                    }
                    default:
                        jj_la1[184] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                break;
            }
            case K_CURRENT: {
                jj_consume_token(K_CURRENT);
                jj_consume_token(K_ROW);
                offset.setType(WindowOffset.Type.CURRENT);
                {
                    if ("" != null)
                        return offset;
                }
                break;
            }
            case K_DO:
            case K_NULL:
            case K_CASE:
            case K_XML:
            case K_VALUE:
            case K_REPLACE:
            case K_TRUNCATE:
            case K_CAST:
            case K_PARTITION:
            case K_EXTRACT:
            case K_MATERIALIZED:
            case K_INTERVAL:
            case K_SIBLINGS:
            case K_COLUMN:
            case K_NULLS:
            case K_FIRST:
            case K_LAST:
            case K_ROWS:
            case K_RANGE:
            case K_FOLLOWING:
            case K_ROW:
            case S_DOUBLE:
            case S_LONG:
            case S_IDENTIFIER:
            case S_CHAR_LITERAL:
            case S_QUOTED_IDENTIFIER:
            case 125:
            case 129:
            case 145:
            case 146:
            case 150:
            case 152:
            case 153:
            case 155:
            case 157: {
                expr = SimpleExpression();
                offset.setType(WindowOffset.Type.EXPR);
                offset.setExpression(expr);
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_PRECEDING: {
                        jj_consume_token(K_PRECEDING);
                        offset.setType(WindowOffset.Type.PRECEDING);
                        break;
                    }
                    case K_FOLLOWING: {
                        jj_consume_token(K_FOLLOWING);
                        offset.setType(WindowOffset.Type.FOLLOWING);
                        break;
                    }
                    default:
                        jj_la1[185] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                {
                    if ("" != null)
                        return offset;
                }
                break;
            }
            default:
                jj_la1[186] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }
    
    final public ExtractExpression ExtractExpression()
        throws ParseException {
        ExtractExpression retval = new ExtractExpression();
        Token token = null;
        Expression expr = null;
        jj_consume_token(K_EXTRACT);
        jj_consume_token(125);
        token = jj_consume_token(S_IDENTIFIER);
        retval.setName(token.image);
        jj_consume_token(K_FROM);
        expr = SimpleExpression();
        retval.setExpression(expr);
        jj_consume_token(126);
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public CastExpression CastExpression()
        throws ParseException {
        CastExpression retval = new CastExpression();
        ColDataType type = null;
        Expression expression = null;
        boolean useCastKeyword;
        jj_consume_token(K_CAST);
        jj_consume_token(125);
        expression = SimpleExpression();
        jj_consume_token(K_AS);
        type = ColDataType();
        jj_consume_token(126);
        retval.setUseCastKeyword(true);
        retval.setLeftExpression(expression);
        retval.setType(type);
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Expression CaseWhenExpression()
        throws ParseException {
        CaseExpression caseExp = new CaseExpression();
        Expression switchExp = null;
        WhenClause clause;
        List whenClauses = new ArrayList();
        Expression elseExp = null;
        jj_consume_token(K_CASE);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_WHEN: {
                label_39: while (true) {
                    clause = WhenThenSearchCondition();
                    whenClauses.add(clause);
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case K_WHEN: {
                            ;
                            break;
                        }
                        default:
                            jj_la1[187] = jj_gen;
                            break label_39;
                    }
                }
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_ELSE: {
                        jj_consume_token(K_ELSE);
                        elseExp = SimpleExpression();
                        break;
                    }
                    default:
                        jj_la1[188] = jj_gen;
                        ;
                }
                break;
            }
            case K_DO:
            case K_NULL:
            case K_CASE:
            case K_XML:
            case K_VALUE:
            case K_REPLACE:
            case K_TRUNCATE:
            case K_CAST:
            case K_PARTITION:
            case K_EXTRACT:
            case K_MATERIALIZED:
            case K_INTERVAL:
            case K_SIBLINGS:
            case K_COLUMN:
            case K_NULLS:
            case K_FIRST:
            case K_LAST:
            case K_ROWS:
            case K_RANGE:
            case K_FOLLOWING:
            case K_ROW:
            case S_DOUBLE:
            case S_LONG:
            case S_IDENTIFIER:
            case S_CHAR_LITERAL:
            case S_QUOTED_IDENTIFIER:
            case 125:
            case 129:
            case 145:
            case 146:
            case 150:
            case 152:
            case 153:
            case 155:
            case 157: {
                switchExp = PrimaryExpression();
                label_40: while (true) {
                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                        case K_WHEN: {
                            ;
                            break;
                        }
                        default:
                            jj_la1[189] = jj_gen;
                            break label_40;
                    }
                    clause = WhenThenValue();
                    whenClauses.add(clause);
                }
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_ELSE: {
                        jj_consume_token(K_ELSE);
                        elseExp = SimpleExpression();
                        break;
                    }
                    default:
                        jj_la1[190] = jj_gen;
                        ;
                }
                break;
            }
            default:
                jj_la1[191] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        jj_consume_token(K_END);
        caseExp.setSwitchExpression(switchExp);
        caseExp.setWhenClauses(whenClauses);
        caseExp.setElseExpression(elseExp);
        {
            if ("" != null)
                return caseExp;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public WhenClause WhenThenSearchCondition()
        throws ParseException {
        WhenClause whenThen = new WhenClause();
        Expression whenExp = null;
        Expression thenExp = null;
        jj_consume_token(K_WHEN);
        whenExp = Expression();
        jj_consume_token(K_THEN);
        thenExp = SimpleExpression();
        whenThen.setWhenExpression(whenExp);
        whenThen.setThenExpression(thenExp);
        {
            if ("" != null)
                return whenThen;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public WhenClause WhenThenValue()
        throws ParseException {
        WhenClause whenThen = new WhenClause();
        Expression whenExp = null;
        Expression thenExp = null;
        jj_consume_token(K_WHEN);
        whenExp = PrimaryExpression();
        jj_consume_token(K_THEN);
        thenExp = SimpleExpression();
        whenThen.setWhenExpression(whenExp);
        whenThen.setThenExpression(thenExp);
        {
            if ("" != null)
                return whenThen;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Execute Execute()
        throws ParseException {
        String funcName = null;
        ExpressionList expressionList = null;
        Execute execute = new Execute();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_EXEC: {
                jj_consume_token(K_EXEC);
                break;
            }
            case K_EXECUTE: {
                jj_consume_token(K_EXECUTE);
                break;
            }
            default:
                jj_la1[192] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        funcName = RelObjectName();
        execute.setName(funcName);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_DO:
            case K_NULL:
            case K_CASE:
            case K_XML:
            case K_VALUE:
            case K_REPLACE:
            case K_TRUNCATE:
            case K_CAST:
            case K_PARTITION:
            case K_EXTRACT:
            case K_MATERIALIZED:
            case K_INTERVAL:
            case K_SIBLINGS:
            case K_COLUMN:
            case K_NULLS:
            case K_FIRST:
            case K_LAST:
            case K_ROWS:
            case K_RANGE:
            case K_FOLLOWING:
            case K_ROW:
            case S_DOUBLE:
            case S_LONG:
            case S_IDENTIFIER:
            case S_CHAR_LITERAL:
            case S_QUOTED_IDENTIFIER:
            case 125:
            case 129:
            case 145:
            case 146:
            case 150:
            case 152:
            case 153:
            case 155:
            case 157: {
                expressionList = SimpleExpressionList();
                break;
            }
            default:
                jj_la1[193] = jj_gen;
                ;
        }
        execute.setExprList(expressionList);
        {
            if ("" != null)
                return execute;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Function Function()
        throws ParseException {
        Function retval = new Function();
        String funcName = null;
        String tmp = null;
        ExpressionList expressionList = null;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case 157: {
                jj_consume_token(157);
                retval.setEscaped(true);
                break;
            }
            default:
                jj_la1[194] = jj_gen;
                ;
        }
        funcName = RelObjectName();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case 128: {
                jj_consume_token(128);
                tmp = RelObjectName();
                funcName += "." + tmp;
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case 128: {
                        jj_consume_token(128);
                        tmp = RelObjectName();
                        funcName += "." + tmp;
                        break;
                    }
                    default:
                        jj_la1[195] = jj_gen;
                        ;
                }
                break;
            }
            default:
                jj_la1[196] = jj_gen;
                ;
        }
        jj_consume_token(125);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_DO:
            case K_ALL:
            case K_NULL:
            case K_CASE:
            case K_XML:
            case K_VALUE:
            case K_REPLACE:
            case K_TRUNCATE:
            case K_DISTINCT:
            case K_CAST:
            case K_PARTITION:
            case K_EXTRACT:
            case K_MATERIALIZED:
            case K_INTERVAL:
            case K_SIBLINGS:
            case K_COLUMN:
            case K_NULLS:
            case K_FIRST:
            case K_LAST:
            case K_ROWS:
            case K_RANGE:
            case K_FOLLOWING:
            case K_ROW:
            case S_DOUBLE:
            case S_LONG:
            case S_IDENTIFIER:
            case S_CHAR_LITERAL:
            case S_QUOTED_IDENTIFIER:
            case 125:
            case 127:
            case 129:
            case 145:
            case 146:
            case 150:
            case 152:
            case 153:
            case 155:
            case 157: {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_ALL:
                    case K_DISTINCT: {
                        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                            case K_DISTINCT: {
                                jj_consume_token(K_DISTINCT);
                                retval.setDistinct(true);
                                break;
                            }
                            case K_ALL: {
                                jj_consume_token(K_ALL);
                                retval.setAllColumns(true);
                                break;
                            }
                            default:
                                jj_la1[197] = jj_gen;
                                jj_consume_token(-1);
                                throw new ParseException();
                        }
                        break;
                    }
                    default:
                        jj_la1[198] = jj_gen;
                        ;
                }
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_DO:
                    case K_NULL:
                    case K_CASE:
                    case K_XML:
                    case K_VALUE:
                    case K_REPLACE:
                    case K_TRUNCATE:
                    case K_CAST:
                    case K_PARTITION:
                    case K_EXTRACT:
                    case K_MATERIALIZED:
                    case K_INTERVAL:
                    case K_SIBLINGS:
                    case K_COLUMN:
                    case K_NULLS:
                    case K_FIRST:
                    case K_LAST:
                    case K_ROWS:
                    case K_RANGE:
                    case K_FOLLOWING:
                    case K_ROW:
                    case S_DOUBLE:
                    case S_LONG:
                    case S_IDENTIFIER:
                    case S_CHAR_LITERAL:
                    case S_QUOTED_IDENTIFIER:
                    case 125:
                    case 129:
                    case 145:
                    case 146:
                    case 150:
                    case 152:
                    case 153:
                    case 155:
                    case 157: {
                        expressionList = SimpleExpressionList();
                        break;
                    }
                    case 127: {
                        jj_consume_token(127);
                        retval.setAllColumns(true);
                        break;
                    }
                    default:
                        jj_la1[199] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                break;
            }
            default:
                jj_la1[200] = jj_gen;
                ;
        }
        jj_consume_token(126);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case 151: {
                jj_consume_token(151);
                break;
            }
            default:
                jj_la1[201] = jj_gen;
                ;
        }
        retval.setParameters(expressionList);
        retval.setName(funcName);
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public SubSelect SubSelect()
        throws ParseException {
        SelectBody selectBody = null;
        selectBody = SelectBody();
        SubSelect subSelect = new SubSelect();
        subSelect.setSelectBody(selectBody);
        {
            if ("" != null)
                return subSelect;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public CreateIndex CreateIndex()
        throws ParseException {
        CreateIndex createIndex = new CreateIndex();
        Table table = null;
        List<String> colNames = new ArrayList<String>();
        Token columnName;
        Index index = null;
        String name = null;
        String parameter = null;
        jj_consume_token(K_CREATE);
        label_41: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_KEY:
                case K_NOT:
                case K_NULL:
                case K_PRIMARY:
                case S_DOUBLE:
                case S_LONG:
                case S_IDENTIFIER:
                case S_CHAR_LITERAL:
                case 124:
                case 125: {
                    ;
                    break;
                }
                default:
                    jj_la1[202] = jj_gen;
                    break label_41;
            }
            parameter = CreateParameter();
        }
        jj_consume_token(K_INDEX);
        name = RelObjectName();
        index = new Index();
        index.setName(name);
        index.setType(parameter);
        jj_consume_token(K_ON);
        table = Table();
        jj_consume_token(125);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case S_IDENTIFIER: {
                columnName = jj_consume_token(S_IDENTIFIER);
                break;
            }
            case S_QUOTED_IDENTIFIER: {
                columnName = jj_consume_token(S_QUOTED_IDENTIFIER);
                break;
            }
            default:
                jj_la1[203] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        label_42: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_KEY:
                case K_NOT:
                case K_ASC:
                case K_DESC:
                case K_NULL:
                case K_PRIMARY:
                case S_DOUBLE:
                case S_LONG:
                case S_IDENTIFIER:
                case S_CHAR_LITERAL:
                case 124:
                case 125: {
                    ;
                    break;
                }
                default:
                    jj_la1[204] = jj_gen;
                    break label_42;
            }
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_KEY:
                case K_NOT:
                case K_NULL:
                case K_PRIMARY:
                case S_DOUBLE:
                case S_LONG:
                case S_IDENTIFIER:
                case S_CHAR_LITERAL:
                case 124:
                case 125: {
                    CreateParameter();
                    break;
                }
                case K_ASC: {
                    jj_consume_token(K_ASC);
                    break;
                }
                case K_DESC: {
                    jj_consume_token(K_DESC);
                    break;
                }
                default:
                    jj_la1[205] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        colNames.add(columnName.image);
        label_43: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 123: {
                    ;
                    break;
                }
                default:
                    jj_la1[206] = jj_gen;
                    break label_43;
            }
            jj_consume_token(123);
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case S_IDENTIFIER: {
                    columnName = jj_consume_token(S_IDENTIFIER);
                    break;
                }
                case S_QUOTED_IDENTIFIER: {
                    columnName = jj_consume_token(S_QUOTED_IDENTIFIER);
                    break;
                }
                default:
                    jj_la1[207] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            label_44: while (true) {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_KEY:
                    case K_NOT:
                    case K_ASC:
                    case K_DESC:
                    case K_NULL:
                    case K_PRIMARY:
                    case S_DOUBLE:
                    case S_LONG:
                    case S_IDENTIFIER:
                    case S_CHAR_LITERAL:
                    case 124:
                    case 125: {
                        ;
                        break;
                    }
                    default:
                        jj_la1[208] = jj_gen;
                        break label_44;
                }
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case K_KEY:
                    case K_NOT:
                    case K_NULL:
                    case K_PRIMARY:
                    case S_DOUBLE:
                    case S_LONG:
                    case S_IDENTIFIER:
                    case S_CHAR_LITERAL:
                    case 124:
                    case 125: {
                        CreateParameter();
                        break;
                    }
                    case K_ASC: {
                        jj_consume_token(K_ASC);
                        break;
                    }
                    case K_DESC: {
                        jj_consume_token(K_DESC);
                        break;
                    }
                    default:
                        jj_la1[209] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
            }
            colNames.add(columnName.image);
        }
        jj_consume_token(126);
        label_45: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_KEY:
                case K_NOT:
                case K_NULL:
                case K_PRIMARY:
                case S_DOUBLE:
                case S_LONG:
                case S_IDENTIFIER:
                case S_CHAR_LITERAL:
                case 124:
                case 125: {
                    ;
                    break;
                }
                default:
                    jj_la1[210] = jj_gen;
                    break label_45;
            }
            CreateParameter();
            
        }
        index.setColumnsNames(colNames);
        createIndex.setIndex(index);
        createIndex.setTable(table);
        {
            if ("" != null)
                return createIndex;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public CreateTable CreateTable()
        throws ParseException {
        CreateTable createTable = new CreateTable();
        Table table = null;
        List columnDefinitions = new ArrayList();
        List columnSpecs = null;
        List tableOptions = new ArrayList();
        Token columnName;
        Token tk = null;
        Token tk2 = null;
        Token tk3 = null;
        ColDataType colDataType = null;
        String stringList = null;
        ColumnDefinition coldef = null;
        List indexes = new ArrayList();
        List colNames = null;
        Index index = null;
        ForeignKeyIndex fkIndex = null;
        String parameter = null;
        Table fkTable = null;
        Select select = null;
        jj_consume_token(K_CREATE);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_UNLOGGED: {
                jj_consume_token(K_UNLOGGED);
                createTable.setUnlogged(true);
                break;
            }
            default:
                jj_la1[211] = jj_gen;
                ;
        }
        label_46: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case K_KEY:
                case K_NOT:
                case K_NULL:
                case K_PRIMARY:
                case S_DOUBLE:
                case S_LONG:
                case S_IDENTIFIER:
                case S_CHAR_LITERAL:
                case 124:
                case 125: {
                    ;
                    break;
                }
                default:
                    jj_la1[212] = jj_gen;
                    break label_46;
            }
            CreateParameter();
        }
        jj_consume_token(K_TABLE);
        table = Table();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_AS:
            case 125: {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case 125: {
                        jj_consume_token(125);
                        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                            case S_IDENTIFIER: {
                                columnName = jj_consume_token(S_IDENTIFIER);
                                break;
                            }
                            case S_QUOTED_IDENTIFIER: {
                                columnName = jj_consume_token(S_QUOTED_IDENTIFIER);
                                break;
                            }
                            default:
                                jj_la1[213] = jj_gen;
                                jj_consume_token(-1);
                                throw new ParseException();
                        }
                        colDataType = ColDataType();
                        columnSpecs = new ArrayList();
                        label_47: while (true) {
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case K_KEY:
                                case K_NOT:
                                case K_NULL:
                                case K_PRIMARY:
                                case S_DOUBLE:
                                case S_LONG:
                                case S_IDENTIFIER:
                                case S_CHAR_LITERAL:
                                case 124:
                                case 125: {
                                    ;
                                    break;
                                }
                                default:
                                    jj_la1[214] = jj_gen;
                                    break label_47;
                            }
                            parameter = CreateParameter();
                            columnSpecs.add(parameter);
                        }
                        coldef = new ColumnDefinition();
                        coldef.setColumnName(columnName.image);
                        coldef.setColDataType(colDataType);
                        if (columnSpecs.size() > 0)
                            coldef.setColumnSpecStrings(columnSpecs);
                        columnDefinitions.add(coldef);
                        label_48: while (true) {
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case 123: {
                                    ;
                                    break;
                                }
                                default:
                                    jj_la1[215] = jj_gen;
                                    break label_48;
                            }
                            jj_consume_token(123);
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case K_INDEX: {
                                    tk = jj_consume_token(K_INDEX);
                                    tk3 = jj_consume_token(S_IDENTIFIER);
                                    colNames = ColumnsNamesList();
                                    index = new Index();
                                    index.setType(tk.image);
                                    index.setName(tk3.image);
                                    index.setColumnsNames(colNames);
                                    indexes.add(index);
                                    break;
                                }
                                default:
                                    jj_la1[220] = jj_gen;
                                    if (jj_2_58(3)) {
                                        index = new NamedConstraint();
                                        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                            case K_CONSTRAINT: {
                                                jj_consume_token(K_CONSTRAINT);
                                                tk3 = jj_consume_token(S_IDENTIFIER);
                                                index.setName(tk3.image);
                                                break;
                                            }
                                            default:
                                                jj_la1[216] = jj_gen;
                                                ;
                                        }
                                        tk = jj_consume_token(K_PRIMARY);
                                        tk2 = jj_consume_token(K_KEY);
                                        colNames = ColumnsNamesList();
                                        index.setType(tk.image + " " + tk2.image);
                                        index.setColumnsNames(colNames);
                                        indexes.add(index);
                                    }
                                    else {
                                        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                            case K_KEY: {
                                                tk = jj_consume_token(K_KEY);
                                                tk3 = jj_consume_token(S_IDENTIFIER);
                                                colNames = ColumnsNamesList();
                                                index = new Index();
                                                index.setType(tk.image);
                                                index.setName(tk3.image);
                                                index.setColumnsNames(colNames);
                                                indexes.add(index);
                                                break;
                                            }
                                            case K_FOREIGN:
                                            case K_CONSTRAINT: {
                                                fkIndex = new ForeignKeyIndex();
                                                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                                    case K_CONSTRAINT: {
                                                        jj_consume_token(K_CONSTRAINT);
                                                        tk3 = jj_consume_token(S_IDENTIFIER);
                                                        fkIndex.setName(tk3.image);
                                                        break;
                                                    }
                                                    default:
                                                        jj_la1[217] = jj_gen;
                                                        ;
                                                }
                                                tk = jj_consume_token(K_FOREIGN);
                                                tk2 = jj_consume_token(K_KEY);
                                                colNames = ColumnsNamesList();
                                                fkIndex.setType(tk.image + " " + tk2.image);
                                                fkIndex.setColumnsNames(colNames);
                                                jj_consume_token(K_REFERENCES);
                                                fkTable = Table();
                                                colNames = ColumnsNamesList();
                                                fkIndex.setTable(fkTable);
                                                fkIndex.setReferencedColumnNames(colNames);
                                                indexes.add(fkIndex);
                                                break;
                                            }
                                            case S_IDENTIFIER:
                                            case S_QUOTED_IDENTIFIER: {
                                                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                                    case S_IDENTIFIER: {
                                                        columnName = jj_consume_token(S_IDENTIFIER);
                                                        break;
                                                    }
                                                    case S_QUOTED_IDENTIFIER: {
                                                        columnName = jj_consume_token(S_QUOTED_IDENTIFIER);
                                                        break;
                                                    }
                                                    default:
                                                        jj_la1[218] = jj_gen;
                                                        jj_consume_token(-1);
                                                        throw new ParseException();
                                                }
                                                colDataType = ColDataType();
                                                columnSpecs = new ArrayList();
                                                label_49: while (true) {
                                                    switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                                        case K_KEY:
                                                        case K_NOT:
                                                        case K_NULL:
                                                        case K_PRIMARY:
                                                        case S_DOUBLE:
                                                        case S_LONG:
                                                        case S_IDENTIFIER:
                                                        case S_CHAR_LITERAL:
                                                        case 124:
                                                        case 125: {
                                                            ;
                                                            break;
                                                        }
                                                        default:
                                                            jj_la1[219] = jj_gen;
                                                            break label_49;
                                                    }
                                                    parameter = CreateParameter();
                                                    columnSpecs.add(parameter);
                                                }
                                                coldef = new ColumnDefinition();
                                                coldef.setColumnName(columnName.image);
                                                coldef.setColDataType(colDataType);
                                                if (columnSpecs.size() > 0)
                                                    coldef.setColumnSpecStrings(columnSpecs);
                                                columnDefinitions.add(coldef);
                                                break;
                                            }
                                            default:
                                                jj_la1[221] = jj_gen;
                                                jj_consume_token(-1);
                                                throw new ParseException();
                                        }
                                    }
                            }
                        }
                        jj_consume_token(126);
                        label_50: while (true) {
                            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                                case K_KEY:
                                case K_NOT:
                                case K_NULL:
                                case K_PRIMARY:
                                case S_DOUBLE:
                                case S_LONG:
                                case S_IDENTIFIER:
                                case S_CHAR_LITERAL:
                                case 124:
                                case 125: {
                                    ;
                                    break;
                                }
                                default:
                                    jj_la1[222] = jj_gen;
                                    break label_50;
                            }
                            parameter = CreateParameter();
                            tableOptions.add(parameter);
                        }
                        break;
                    }
                    case K_AS: {
                        jj_consume_token(K_AS);
                        select = Select();
                        createTable.setSelect(select);
                        break;
                    }
                    default:
                        jj_la1[223] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                break;
            }
            default:
                jj_la1[224] = jj_gen;
                ;
        }
        createTable.setTable(table);
        if (indexes.size() > 0)
            createTable.setIndexes(indexes);
        if (tableOptions.size() > 0)
            createTable.setTableOptionsStrings(tableOptions);
        if (columnDefinitions.size() > 0)
            createTable.setColumnDefinitions(columnDefinitions);
        {
            if ("" != null)
                return createTable;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public ColDataType ColDataType()
        throws ParseException {
        ColDataType colDataType = new ColDataType();
        Token tk = null;
        Token tk2 = null;
        ArrayList argumentsStringList = new ArrayList();
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_CHARACTER: {
                tk = jj_consume_token(K_CHARACTER);
                tk2 = jj_consume_token(K_VARYING);
                colDataType.setDataType(tk.image + " " + tk2.image);
                break;
            }
            case S_IDENTIFIER: {
                tk = jj_consume_token(S_IDENTIFIER);
                colDataType.setDataType(tk.image);
                break;
            }
            default:
                jj_la1[225] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        if (jj_2_59(2)) {
            jj_consume_token(125);
            label_51: while (true) {
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case S_LONG:
                    case S_CHAR_LITERAL: {
                        ;
                        break;
                    }
                    default:
                        jj_la1[226] = jj_gen;
                        break label_51;
                }
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case S_LONG: {
                        tk = jj_consume_token(S_LONG);
                        break;
                    }
                    case S_CHAR_LITERAL: {
                        tk = jj_consume_token(S_CHAR_LITERAL);
                        break;
                    }
                    default:
                        jj_la1[227] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                argumentsStringList.add(tk.image);
                switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                    case 123: {
                        jj_consume_token(123);
                        
                        break;
                    }
                    default:
                        jj_la1[228] = jj_gen;
                        ;
                }
            }
            jj_consume_token(126);
        }
        else {
            ;
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_CHARACTER: {
                jj_consume_token(K_CHARACTER);
                jj_consume_token(K_SET);
                tk = jj_consume_token(S_IDENTIFIER);
                colDataType.setCharacterSet(tk.image);
                break;
            }
            default:
                jj_la1[229] = jj_gen;
                ;
        }
        if (argumentsStringList.size() > 0)
            colDataType.setArgumentsStringList(argumentsStringList);
        {
            if ("" != null)
                return colDataType;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public CreateView CreateView()
        throws ParseException {
        CreateView createView = new CreateView();
        Table view = null;
        SelectBody select = null;
        List<String> columnNames = null;
        jj_consume_token(K_CREATE);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_OR: {
                jj_consume_token(K_OR);
                jj_consume_token(K_REPLACE);
                createView.setOrReplace(true);
                break;
            }
            default:
                jj_la1[230] = jj_gen;
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case K_MATERIALIZED: {
                jj_consume_token(K_MATERIALIZED);
                createView.setMaterialized(true);
                break;
            }
            default:
                jj_la1[231] = jj_gen;
                ;
        }
        jj_consume_token(K_VIEW);
        view = Table();
        createView.setView(view);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case 125: {
                columnNames = ColumnsNamesList();
                createView.setColumnNames(columnNames);
                break;
            }
            default:
                jj_la1[232] = jj_gen;
                ;
        }
        jj_consume_token(K_AS);
        if (jj_2_60(2147483647)) {
            select = SelectBody();
            createView.setSelectBody(select);
        }
        else {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 125: {
                    jj_consume_token(125);
                    select = SelectBody();
                    jj_consume_token(126);
                    createView.setSelectBody(select);
                    break;
                }
                default:
                    jj_la1[233] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        {
            if ("" != null)
                return createView;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public String CreateParameter()
        throws ParseException {
        String retval = null;
        Token tk = null;
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case S_IDENTIFIER: {
                tk = jj_consume_token(S_IDENTIFIER);
                retval = tk.image;
                break;
            }
            case K_NULL: {
                tk = jj_consume_token(K_NULL);
                retval = tk.image;
                break;
            }
            case K_NOT: {
                tk = jj_consume_token(K_NOT);
                retval = tk.image;
                break;
            }
            case K_PRIMARY: {
                tk = jj_consume_token(K_PRIMARY);
                retval = tk.image;
                break;
            }
            case K_KEY: {
                tk = jj_consume_token(K_KEY);
                retval = tk.image;
                break;
            }
            case S_CHAR_LITERAL: {
                tk = jj_consume_token(S_CHAR_LITERAL);
                retval = tk.image;
                break;
            }
            case S_LONG: {
                tk = jj_consume_token(S_LONG);
                retval = tk.image;
                break;
            }
            case S_DOUBLE: {
                tk = jj_consume_token(S_DOUBLE);
                retval = tk.image;
                break;
            }
            case 124: {
                jj_consume_token(124);
                retval = "=";
                break;
            }
            case 125: {
                retval = AList();
                break;
            }
            default:
                jj_la1[234] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public String AList()
        throws ParseException {
        StringBuilder retval = new StringBuilder("(");
        Token tk = null;
        jj_consume_token(125);
        label_52: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case S_DOUBLE:
                case S_LONG:
                case S_IDENTIFIER:
                case S_CHAR_LITERAL: {
                    ;
                    break;
                }
                default:
                    jj_la1[235] = jj_gen;
                    break label_52;
            }
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case S_LONG: {
                    tk = jj_consume_token(S_LONG);
                    break;
                }
                case S_DOUBLE: {
                    tk = jj_consume_token(S_DOUBLE);
                    break;
                }
                case S_CHAR_LITERAL: {
                    tk = jj_consume_token(S_CHAR_LITERAL);
                    break;
                }
                case S_IDENTIFIER: {
                    tk = jj_consume_token(S_IDENTIFIER);
                    break;
                }
                default:
                    jj_la1[236] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            retval.append(tk.image);
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 123: {
                    jj_consume_token(123);
                    retval.append(",");
                    break;
                }
                default:
                    jj_la1[237] = jj_gen;
                    ;
            }
        }
        jj_consume_token(126);
        retval.append(")");
        {
            if ("" != null)
                return retval.toString();
        }
        throw new Error("Missing return statement in function");
    }
    
    final public List<String> ColumnsNamesList()
        throws ParseException {
        List<String> retval = new ArrayList<String>();
        Token tk = null;
        jj_consume_token(125);
        tk = jj_consume_token(S_IDENTIFIER);
        retval.add(tk.image);
        label_53: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case 123: {
                    ;
                    break;
                }
                default:
                    jj_la1[238] = jj_gen;
                    break label_53;
            }
            jj_consume_token(123);
            tk = jj_consume_token(S_IDENTIFIER);
            retval.add(tk.image);
        }
        jj_consume_token(126);
        {
            if ("" != null)
                return retval;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Drop Drop()
        throws ParseException {
        Drop drop = new Drop();
        Token tk = null;
        List<String> dropArgs = new ArrayList<String>();
        jj_consume_token(K_DROP);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case S_IDENTIFIER: {
                tk = jj_consume_token(S_IDENTIFIER);
                break;
            }
            case K_TABLE: {
                tk = jj_consume_token(K_TABLE);
                break;
            }
            case K_INDEX: {
                tk = jj_consume_token(K_INDEX);
                break;
            }
            default:
                jj_la1[239] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        drop.setType(tk.image);
        tk = jj_consume_token(S_IDENTIFIER);
        drop.setName(tk.image);
        label_54: while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case S_IDENTIFIER: {
                    ;
                    break;
                }
                default:
                    jj_la1[240] = jj_gen;
                    break label_54;
            }
            tk = jj_consume_token(S_IDENTIFIER);
            dropArgs.add(tk.image);
        }
        if (dropArgs.size() > 0)
            drop.setParameters(dropArgs);
        {
            if ("" != null)
                return drop;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Truncate Truncate()
        throws ParseException {
        Truncate truncate = new Truncate();
        Table table;
        jj_consume_token(K_TRUNCATE);
        jj_consume_token(K_TABLE);
        table = Table();
        truncate.setTable(table);
        {
            if ("" != null)
                return truncate;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public Alter Alter()
        throws ParseException {
        Alter alter = new Alter();
        Table table;
        Token tk;
        ColDataType dataType;
        jj_consume_token(K_ALTER);
        jj_consume_token(K_TABLE);
        table = Table();
        jj_consume_token(K_ADD);
        jj_consume_token(K_COLUMN);
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case S_IDENTIFIER: {
                tk = jj_consume_token(S_IDENTIFIER);
                break;
            }
            case S_QUOTED_IDENTIFIER: {
                tk = jj_consume_token(S_QUOTED_IDENTIFIER);
                break;
            }
            default:
                jj_la1[241] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        dataType = ColDataType();
        alter.setTable(table);
        alter.setColumnName(tk.image);
        alter.setDataType(dataType);
        {
            if ("" != null)
                return alter;
        }
        throw new Error("Missing return statement in function");
    }
    
    private boolean jj_2_1(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_1();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(0, xla);
        }
    }
    
    private boolean jj_2_2(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_2();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(1, xla);
        }
    }
    
    private boolean jj_2_3(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_3();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(2, xla);
        }
    }
    
    private boolean jj_2_4(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_4();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(3, xla);
        }
    }
    
    private boolean jj_2_5(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_5();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(4, xla);
        }
    }
    
    private boolean jj_2_6(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_6();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(5, xla);
        }
    }
    
    private boolean jj_2_7(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_7();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(6, xla);
        }
    }
    
    private boolean jj_2_8(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_8();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(7, xla);
        }
    }
    
    private boolean jj_2_9(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_9();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(8, xla);
        }
    }
    
    private boolean jj_2_10(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_10();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(9, xla);
        }
    }
    
    private boolean jj_2_11(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_11();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(10, xla);
        }
    }
    
    private boolean jj_2_12(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_12();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(11, xla);
        }
    }
    
    private boolean jj_2_13(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_13();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(12, xla);
        }
    }
    
    private boolean jj_2_14(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_14();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(13, xla);
        }
    }
    
    private boolean jj_2_15(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_15();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(14, xla);
        }
    }
    
    private boolean jj_2_16(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_16();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(15, xla);
        }
    }
    
    private boolean jj_2_17(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_17();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(16, xla);
        }
    }
    
    private boolean jj_2_18(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_18();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(17, xla);
        }
    }
    
    private boolean jj_2_19(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_19();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(18, xla);
        }
    }
    
    private boolean jj_2_20(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_20();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(19, xla);
        }
    }
    
    private boolean jj_2_21(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_21();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(20, xla);
        }
    }
    
    private boolean jj_2_22(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_22();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(21, xla);
        }
    }
    
    private boolean jj_2_23(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_23();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(22, xla);
        }
    }
    
    private boolean jj_2_24(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_24();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(23, xla);
        }
    }
    
    private boolean jj_2_25(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_25();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(24, xla);
        }
    }
    
    private boolean jj_2_26(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_26();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(25, xla);
        }
    }
    
    private boolean jj_2_27(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_27();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(26, xla);
        }
    }
    
    private boolean jj_2_28(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_28();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(27, xla);
        }
    }
    
    private boolean jj_2_29(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_29();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(28, xla);
        }
    }
    
    private boolean jj_2_30(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_30();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(29, xla);
        }
    }
    
    private boolean jj_2_31(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_31();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(30, xla);
        }
    }
    
    private boolean jj_2_32(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_32();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(31, xla);
        }
    }
    
    private boolean jj_2_33(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_33();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(32, xla);
        }
    }
    
    private boolean jj_2_34(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_34();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(33, xla);
        }
    }
    
    private boolean jj_2_35(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_35();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(34, xla);
        }
    }
    
    private boolean jj_2_36(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_36();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(35, xla);
        }
    }
    
    private boolean jj_2_37(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_37();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(36, xla);
        }
    }
    
    private boolean jj_2_38(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_38();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(37, xla);
        }
    }
    
    private boolean jj_2_39(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_39();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(38, xla);
        }
    }
    
    private boolean jj_2_40(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_40();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(39, xla);
        }
    }
    
    private boolean jj_2_41(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_41();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(40, xla);
        }
    }
    
    private boolean jj_2_42(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_42();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(41, xla);
        }
    }
    
    private boolean jj_2_43(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_43();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(42, xla);
        }
    }
    
    private boolean jj_2_44(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_44();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(43, xla);
        }
    }
    
    private boolean jj_2_45(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_45();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(44, xla);
        }
    }
    
    private boolean jj_2_46(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_46();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(45, xla);
        }
    }
    
    private boolean jj_2_47(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_47();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(46, xla);
        }
    }
    
    private boolean jj_2_48(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_48();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(47, xla);
        }
    }
    
    private boolean jj_2_49(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_49();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(48, xla);
        }
    }
    
    private boolean jj_2_50(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_50();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(49, xla);
        }
    }
    
    private boolean jj_2_51(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_51();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(50, xla);
        }
    }
    
    private boolean jj_2_52(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_52();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(51, xla);
        }
    }
    
    private boolean jj_2_53(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_53();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(52, xla);
        }
    }
    
    private boolean jj_2_54(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_54();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(53, xla);
        }
    }
    
    private boolean jj_2_55(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_55();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(54, xla);
        }
    }
    
    private boolean jj_2_56(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_56();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(55, xla);
        }
    }
    
    private boolean jj_2_57(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_57();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(56, xla);
        }
    }
    
    private boolean jj_2_58(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_58();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(57, xla);
        }
    }
    
    private boolean jj_2_59(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_59();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(58, xla);
        }
    }
    
    private boolean jj_2_60(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_60();
        }
        catch (LookaheadSuccess ls) {
            return true;
        }
        finally {
            jj_save(59, xla);
        }
    }
    
    private boolean jj_3R_420() {
        if (jj_scan_token(125))
            return true;
        if (jj_3R_59())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_428()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_299() {
        if (jj_scan_token(124))
            return true;
        return false;
    }
    
    private boolean jj_3R_427() {
        if (jj_3R_258())
            return true;
        return false;
    }
    
    private boolean jj_3R_355() {
        if (jj_scan_token(K_DISTINCT))
            return true;
        return false;
    }
    
    private boolean jj_3R_342() {
        if (jj_scan_token(125))
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_379()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_298() {
        if (jj_scan_token(S_DOUBLE))
            return true;
        return false;
    }
    
    private boolean jj_3R_421() {
        if (jj_3R_59())
            return true;
        return false;
    }
    
    private boolean jj_3R_297() {
        if (jj_scan_token(S_LONG))
            return true;
        return false;
    }
    
    private boolean jj_3R_428() {
        if (jj_scan_token(123))
            return true;
        if (jj_3R_59())
            return true;
        return false;
    }
    
    private boolean jj_3R_296() {
        if (jj_scan_token(S_CHAR_LITERAL))
            return true;
        return false;
    }
    
    private boolean jj_3R_295() {
        if (jj_scan_token(K_KEY))
            return true;
        return false;
    }
    
    private boolean jj_3R_208() {
        if (jj_scan_token(123))
            return true;
        if (jj_3R_63())
            return true;
        return false;
    }
    
    private boolean jj_3R_294() {
        if (jj_scan_token(K_PRIMARY))
            return true;
        return false;
    }
    
    private boolean jj_3R_63() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_154()) {
            jj_scanpos = xsp;
            if (jj_3R_155())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_293() {
        if (jj_scan_token(K_NOT))
            return true;
        return false;
    }
    
    private boolean jj_3R_408() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_420()) {
            jj_scanpos = xsp;
            if (jj_3R_421())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_292() {
        if (jj_scan_token(K_NULL))
            return true;
        return false;
    }
    
    private boolean jj_3R_291() {
        if (jj_scan_token(S_IDENTIFIER))
            return true;
        return false;
    }
    
    private boolean jj_3R_347() {
        if (jj_scan_token(123))
            return true;
        if (jj_3R_346())
            return true;
        return false;
    }
    
    private boolean jj_3R_336() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(14)) {
            jj_scanpos = xsp;
            if (jj_scan_token(36))
                return true;
        }
        if (jj_scan_token(125))
            return true;
        if (jj_3R_91())
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_418() {
        if (jj_3R_110())
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_427())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_223() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_291()) {
            jj_scanpos = xsp;
            if (jj_3R_292()) {
                jj_scanpos = xsp;
                if (jj_3R_293()) {
                    jj_scanpos = xsp;
                    if (jj_3R_294()) {
                        jj_scanpos = xsp;
                        if (jj_3R_295()) {
                            jj_scanpos = xsp;
                            if (jj_3R_296()) {
                                jj_scanpos = xsp;
                                if (jj_3R_297()) {
                                    jj_scanpos = xsp;
                                    if (jj_3R_298()) {
                                        jj_scanpos = xsp;
                                        if (jj_3R_299()) {
                                            jj_scanpos = xsp;
                                            if (jj_3R_300())
                                                return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean jj_3R_316() {
        if (jj_scan_token(K_AS))
            return true;
        return false;
    }
    
    private boolean jj_3R_258() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_316())
            jj_scanpos = xsp;
        if (jj_3R_64())
            return true;
        return false;
    }
    
    private boolean jj_3R_354() {
        if (jj_scan_token(K_ALL))
            return true;
        return false;
    }
    
    private boolean jj_3R_335() {
        if (jj_scan_token(K_ALL))
            return true;
        if (jj_scan_token(125))
            return true;
        if (jj_3R_91())
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_313() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_354()) {
            jj_scanpos = xsp;
            if (jj_3R_355())
                return true;
        }
        return false;
    }
    
    private boolean jj_3_60() {
        if (jj_3R_120())
            return true;
        return false;
    }
    
    private boolean jj_3_21() {
        if (jj_3R_72())
            return true;
        return false;
    }
    
    private boolean jj_3R_273() {
        if (jj_3R_63())
            return true;
        return false;
    }
    
    private boolean jj_3R_71() {
        if (jj_3R_163())
            return true;
        if (jj_scan_token(128))
            return true;
        if (jj_scan_token(127))
            return true;
        return false;
    }
    
    private boolean jj_3R_272() {
        if (jj_3R_336())
            return true;
        return false;
    }
    
    private boolean jj_3_20() {
        if (jj_3R_71())
            return true;
        return false;
    }
    
    private boolean jj_3R_164() {
        if (jj_3R_258())
            return true;
        return false;
    }
    
    private boolean jj_3R_271() {
        if (jj_3R_335())
            return true;
        return false;
    }
    
    private boolean jj_3R_125() {
        if (jj_scan_token(K_MATERIALIZED))
            return true;
        return false;
    }
    
    private boolean jj_3R_124() {
        if (jj_scan_token(K_OR))
            return true;
        return false;
    }
    
    private boolean jj_3R_182() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_271()) {
            jj_scanpos = xsp;
            if (jj_3R_272()) {
                jj_scanpos = xsp;
                if (jj_3R_273())
                    return true;
            }
        }
        return false;
    }
    
    private boolean jj_3R_119() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(112)) {
            jj_scanpos = xsp;
            if (jj_scan_token(119))
                return true;
        }
        xsp = jj_scanpos;
        if (jj_3R_378())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_57() {
        if (jj_scan_token(K_CREATE))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_124())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_125())
            jj_scanpos = xsp;
        if (jj_scan_token(K_VIEW))
            return true;
        return false;
    }
    
    private boolean jj_3R_387() {
        if (jj_3R_72())
            return true;
        return false;
    }
    
    private boolean jj_3R_386() {
        if (jj_3R_71())
            return true;
        return false;
    }
    
    private boolean jj_3R_203() {
        if (jj_3R_90())
            return true;
        return false;
    }
    
    private boolean jj_3R_278() {
        if (jj_scan_token(K_NOT))
            return true;
        return false;
    }
    
    private boolean jj_3R_385() {
        if (jj_scan_token(127))
            return true;
        return false;
    }
    
    private boolean jj_3R_90() {
        if (jj_3R_63())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_208()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_346() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_385()) {
            jj_scanpos = xsp;
            if (jj_3R_386()) {
                jj_scanpos = xsp;
                if (jj_3R_387())
                    return true;
            }
        }
        return false;
    }
    
    private boolean jj_3R_72() {
        if (jj_3R_63())
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_164())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_249() {
        if (jj_3R_243())
            return true;
        return false;
    }
    
    private boolean jj_3_59() {
        if (jj_scan_token(125))
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_119()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_289() {
        if (jj_scan_token(S_IDENTIFIER))
            return true;
        return false;
    }
    
    private boolean jj_3R_288() {
        if (jj_scan_token(K_CHARACTER))
            return true;
        if (jj_scan_token(K_VARYING))
            return true;
        return false;
    }
    
    private boolean jj_3R_290() {
        if (jj_scan_token(K_CHARACTER))
            return true;
        if (jj_scan_token(K_SET))
            return true;
        if (jj_scan_token(S_IDENTIFIER))
            return true;
        return false;
    }
    
    private boolean jj_3R_220() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_288()) {
            jj_scanpos = xsp;
            if (jj_3R_289())
                return true;
        }
        xsp = jj_scanpos;
        if (jj_3_59())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_290())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_304() {
        if (jj_3R_346())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_347()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_157() {
        if (jj_3R_243())
            return true;
        return false;
    }
    
    private boolean jj_3R_207() {
        if (jj_scan_token(K_NOT))
            return true;
        return false;
    }
    
    private boolean jj_3R_89() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_207())
            jj_scanpos = xsp;
        if (jj_scan_token(K_EXISTS))
            return true;
        if (jj_3R_63())
            return true;
        return false;
    }
    
    private boolean jj_3R_248() {
        if (jj_scan_token(125))
            return true;
        if (jj_3R_243())
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3_42() {
        if (jj_3R_91())
            return true;
        return false;
    }
    
    private boolean jj_3R_206() {
        if (jj_3R_63())
            return true;
        if (jj_scan_token(K_IS))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_278())
            jj_scanpos = xsp;
        if (jj_scan_token(K_NULL))
            return true;
        return false;
    }
    
    private boolean jj_3R_247() {
        if (jj_scan_token(K_EXCEPT))
            return true;
        return false;
    }
    
    private boolean jj_3R_246() {
        if (jj_scan_token(K_MINUS))
            return true;
        return false;
    }
    
    private boolean jj_3R_205() {
        if (jj_scan_token(K_NOT))
            return true;
        if (jj_3R_63())
            return true;
        if (jj_scan_token(K_IS))
            return true;
        if (jj_scan_token(K_NULL))
            return true;
        return false;
    }
    
    private boolean jj_3R_245() {
        if (jj_scan_token(K_INTERSECT))
            return true;
        return false;
    }
    
    private boolean jj_3R_88() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_205()) {
            jj_scanpos = xsp;
            if (jj_3R_206())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_343() {
        if (jj_scan_token(K_WITH))
            return true;
        return false;
    }
    
    private boolean jj_3R_244() {
        if (jj_scan_token(K_UNION))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_313())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_158() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_244()) {
            jj_scanpos = xsp;
            if (jj_3R_245()) {
                jj_scanpos = xsp;
                if (jj_3R_246()) {
                    jj_scanpos = xsp;
                    if (jj_3R_247())
                        return true;
                }
            }
        }
        xsp = jj_scanpos;
        if (jj_3R_248()) {
            jj_scanpos = xsp;
            if (jj_3R_249())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_202() {
        if (jj_3R_91())
            return true;
        return false;
    }
    
    private boolean jj_3R_160() {
        if (jj_3R_70())
            return true;
        return false;
    }
    
    private boolean jj_3R_159() {
        if (jj_3R_250())
            return true;
        return false;
    }
    
    private boolean jj_3R_156() {
        if (jj_scan_token(125))
            return true;
        if (jj_3R_243())
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_270() {
        if (jj_3R_63())
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_333())
            jj_scanpos = xsp;
        if (jj_scan_token(K_LIKE))
            return true;
        if (jj_3R_63())
            return true;
        xsp = jj_scanpos;
        if (jj_3R_334())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_334() {
        if (jj_scan_token(K_ESCAPE))
            return true;
        if (jj_scan_token(S_CHAR_LITERAL))
            return true;
        return false;
    }
    
    private boolean jj_3R_333() {
        if (jj_scan_token(K_NOT))
            return true;
        return false;
    }
    
    private boolean jj_3R_204() {
        if (jj_scan_token(K_NOT))
            return true;
        return false;
    }
    
    private boolean jj_3R_69() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_156()) {
            jj_scanpos = xsp;
            if (jj_3R_157())
                return true;
        }
        if (jj_3R_158())
            return true;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_158()) {
                jj_scanpos = xsp;
                break;
            }
        }
        xsp = jj_scanpos;
        if (jj_3R_159())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_160())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3_41() {
        if (jj_3R_90())
            return true;
        return false;
    }
    
    private boolean jj_3R_87() {
        if (jj_3R_63())
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_204())
            jj_scanpos = xsp;
        if (jj_scan_token(K_BETWEEN))
            return true;
        if (jj_3R_63())
            return true;
        if (jj_scan_token(K_AND))
            return true;
        if (jj_3R_63())
            return true;
        return false;
    }
    
    private boolean jj_3R_276() {
        if (jj_3R_63())
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_337())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3_18() {
        if (jj_scan_token(K_ORDER))
            return true;
        if (jj_scan_token(K_BY))
            return true;
        return false;
    }
    
    private boolean jj_3_17() {
        if (jj_scan_token(K_ORDER))
            return true;
        if (jj_scan_token(K_SIBLINGS))
            return true;
        if (jj_scan_token(K_BY))
            return true;
        return false;
    }
    
    private boolean jj_3R_275() {
        if (jj_3R_90())
            return true;
        return false;
    }
    
    private boolean jj_3R_200() {
        if (jj_3R_63())
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_277())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3_58() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_117())
            jj_scanpos = xsp;
        if (jj_scan_token(K_PRIMARY))
            return true;
        if (jj_scan_token(K_KEY))
            return true;
        if (jj_3R_118())
            return true;
        return false;
    }
    
    private boolean jj_3R_380() {
        if (jj_scan_token(K_ON))
            return true;
        if (jj_scan_token(125))
            return true;
        if (jj_3R_304())
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_337() {
        if (jj_scan_token(130))
            return true;
        return false;
    }
    
    private boolean jj_3R_277() {
        if (jj_scan_token(130))
            return true;
        return false;
    }
    
    private boolean jj_3R_117() {
        if (jj_scan_token(K_CONSTRAINT))
            return true;
        if (jj_scan_token(S_IDENTIFIER))
            return true;
        return false;
    }
    
    private boolean jj_3R_312() {
        if (jj_3R_250())
            return true;
        return false;
    }
    
    private boolean jj_3R_201() {
        if (jj_scan_token(K_NOT))
            return true;
        return false;
    }
    
    private boolean jj_3R_311() {
        if (jj_3R_250())
            return true;
        return false;
    }
    
    private boolean jj_3R_308() {
        if (jj_3R_351())
            return true;
        return false;
    }
    
    private boolean jj_3_19() {
        if (jj_3R_70())
            return true;
        return false;
    }
    
    private boolean jj_3R_310() {
        if (jj_3R_353())
            return true;
        return false;
    }
    
    private boolean jj_3R_309() {
        if (jj_3R_352())
            return true;
        return false;
    }
    
    private boolean jj_3R_199() {
        if (jj_scan_token(125))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_275()) {
            jj_scanpos = xsp;
            if (jj_3R_276())
                return true;
        }
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_307() {
        if (jj_3R_350())
            return true;
        return false;
    }
    
    private boolean jj_3R_86() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_199()) {
            jj_scanpos = xsp;
            if (jj_3R_200())
                return true;
        }
        xsp = jj_scanpos;
        if (jj_3R_201())
            jj_scanpos = xsp;
        if (jj_scan_token(K_IN))
            return true;
        if (jj_scan_token(125))
            return true;
        xsp = jj_scanpos;
        if (jj_3R_202()) {
            jj_scanpos = xsp;
            if (jj_3R_203())
                return true;
        }
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3_40() {
        if (jj_3R_89())
            return true;
        return false;
    }
    
    private boolean jj_3R_67() {
        if (jj_3R_64())
            return true;
        return false;
    }
    
    private boolean jj_3_39() {
        if (jj_3R_88())
            return true;
        return false;
    }
    
    private boolean jj_3R_306() {
        if (jj_scan_token(K_FROM))
            return true;
        if (jj_3R_166())
            return true;
        if (jj_3R_349())
            return true;
        return false;
    }
    
    private boolean jj_3_38() {
        if (jj_3R_87())
            return true;
        return false;
    }
    
    private boolean jj_3R_344() {
        if (jj_scan_token(K_DISTINCT))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_380())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_305() {
        if (jj_3R_348())
            return true;
        return false;
    }
    
    private boolean jj_3_37() {
        if (jj_3R_86())
            return true;
        return false;
    }
    
    private boolean jj_3R_179() {
        if (jj_3R_270())
            return true;
        return false;
    }
    
    private boolean jj_3R_303() {
        if (jj_3R_345())
            return true;
        return false;
    }
    
    private boolean jj_3R_178() {
        if (jj_3R_89())
            return true;
        return false;
    }
    
    private boolean jj_3R_177() {
        if (jj_3R_88())
            return true;
        return false;
    }
    
    private boolean jj_3R_176() {
        if (jj_3R_87())
            return true;
        return false;
    }
    
    private boolean jj_3R_302() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(12)) {
            jj_scanpos = xsp;
            if (jj_3R_344())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_175() {
        if (jj_3R_86())
            return true;
        return false;
    }
    
    private boolean jj_3R_84() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_175()) {
            jj_scanpos = xsp;
            if (jj_3R_176()) {
                jj_scanpos = xsp;
                if (jj_3R_177()) {
                    jj_scanpos = xsp;
                    if (jj_3R_178()) {
                        jj_scanpos = xsp;
                        if (jj_3R_179())
                            return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean jj_3R_243() {
        if (jj_scan_token(K_SELECT))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_302())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_303())
            jj_scanpos = xsp;
        if (jj_3R_304())
            return true;
        xsp = jj_scanpos;
        if (jj_3R_305())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_306())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_307())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_308())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_309())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_310())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_311())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_312())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3_19())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_287() {
        if (jj_scan_token(127))
            return true;
        return false;
    }
    
    private boolean jj_3_16() {
        if (jj_3R_69())
            return true;
        return false;
    }
    
    private boolean jj_3R_68() {
        if (jj_3R_64())
            return true;
        return false;
    }
    
    private boolean jj_3R_274() {
        if (jj_scan_token(K_BINARY))
            return true;
        return false;
    }
    
    private boolean jj_3R_221() {
        if (jj_3R_69())
            return true;
        return false;
    }
    
    private boolean jj_3R_222() {
        if (jj_3R_243())
            return true;
        return false;
    }
    
    private boolean jj_3R_66() {
        if (jj_3R_64())
            return true;
        return false;
    }
    
    private boolean jj_3R_198() {
        if (jj_scan_token(130))
            return true;
        return false;
    }
    
    private boolean jj_3R_197() {
        if (jj_scan_token(K_PRIOR))
            return true;
        return false;
    }
    
    private boolean jj_3R_120() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_221()) {
            jj_scanpos = xsp;
            if (jj_3R_222())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_196() {
        if (jj_scan_token(141))
            return true;
        return false;
    }
    
    private boolean jj_3R_195() {
        if (jj_scan_token(140))
            return true;
        return false;
    }
    
    private boolean jj_3R_194() {
        if (jj_scan_token(139))
            return true;
        return false;
    }
    
    private boolean jj_3R_193() {
        if (jj_scan_token(K_REGEXP))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_274())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_192() {
        if (jj_scan_token(138))
            return true;
        return false;
    }
    
    private boolean jj_3R_123() {
        if (jj_3R_223())
            return true;
        return false;
    }
    
    private boolean jj_3R_191() {
        if (jj_scan_token(137))
            return true;
        return false;
    }
    
    private boolean jj_3R_189() {
        if (jj_scan_token(135))
            return true;
        return false;
    }
    
    private boolean jj_3R_188() {
        if (jj_scan_token(134))
            return true;
        return false;
    }
    
    private boolean jj_3R_122() {
        if (jj_scan_token(K_UNLOGGED))
            return true;
        return false;
    }
    
    private boolean jj_3R_301() {
        if (jj_3R_343())
            return true;
        return false;
    }
    
    private boolean jj_3R_187() {
        if (jj_scan_token(133))
            return true;
        return false;
    }
    
    private boolean jj_3R_56() {
        if (jj_scan_token(K_CREATE))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_122())
            jj_scanpos = xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_123()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(K_TABLE))
            return true;
        return false;
    }
    
    private boolean jj_3R_186() {
        if (jj_scan_token(124))
            return true;
        return false;
    }
    
    private boolean jj_3R_190() {
        if (jj_scan_token(136))
            return true;
        return false;
    }
    
    private boolean jj_3R_224() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_301())
            jj_scanpos = xsp;
        if (jj_3R_120())
            return true;
        return false;
    }
    
    private boolean jj_3R_185() {
        if (jj_scan_token(132))
            return true;
        return false;
    }
    
    private boolean jj_3R_184() {
        if (jj_scan_token(131))
            return true;
        return false;
    }
    
    private boolean jj_3R_183() {
        if (jj_scan_token(130))
            return true;
        return false;
    }
    
    private boolean jj_3R_181() {
        if (jj_scan_token(K_NOT))
            return true;
        return false;
    }
    
    private boolean jj_3R_180() {
        if (jj_scan_token(K_PRIOR))
            return true;
        return false;
    }
    
    private boolean jj_3R_286() {
        if (jj_3R_90())
            return true;
        return false;
    }
    
    private boolean jj_3R_65() {
        if (jj_3R_64())
            return true;
        return false;
    }
    
    private boolean jj_3R_85() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_180())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_181())
            jj_scanpos = xsp;
        if (jj_3R_182())
            return true;
        xsp = jj_scanpos;
        if (jj_3R_183())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_184()) {
            jj_scanpos = xsp;
            if (jj_3R_185()) {
                jj_scanpos = xsp;
                if (jj_3R_186()) {
                    jj_scanpos = xsp;
                    if (jj_3R_187()) {
                        jj_scanpos = xsp;
                        if (jj_3R_188()) {
                            jj_scanpos = xsp;
                            if (jj_3R_189()) {
                                jj_scanpos = xsp;
                                if (jj_3R_190()) {
                                    jj_scanpos = xsp;
                                    if (jj_3R_191()) {
                                        jj_scanpos = xsp;
                                        if (jj_3R_192()) {
                                            jj_scanpos = xsp;
                                            if (jj_3R_193()) {
                                                jj_scanpos = xsp;
                                                if (jj_3R_194()) {
                                                    jj_scanpos = xsp;
                                                    if (jj_3R_195()) {
                                                        jj_scanpos = xsp;
                                                        if (jj_3R_196())
                                                            return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (jj_3R_182())
            return true;
        xsp = jj_scanpos;
        if (jj_3R_197())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_198())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3_36() {
        if (jj_3R_85())
            return true;
        return false;
    }
    
    private boolean jj_3_35() {
        if (jj_3R_84())
            return true;
        return false;
    }
    
    private boolean jj_3R_257() {
        if (jj_3R_64())
            return true;
        return false;
    }
    
    private boolean jj_3_15() {
        if (jj_3R_64())
            return true;
        if (jj_scan_token(128))
            return true;
        if (jj_3R_64())
            return true;
        return false;
    }
    
    private boolean jj_3R_174() {
        if (jj_3R_110())
            return true;
        return false;
    }
    
    private boolean jj_3_14() {
        if (jj_3R_64())
            return true;
        if (jj_scan_token(128))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_68())
            jj_scanpos = xsp;
        if (jj_scan_token(128))
            return true;
        if (jj_3R_64())
            return true;
        return false;
    }
    
    private boolean jj_3R_173() {
        if (jj_3R_85())
            return true;
        return false;
    }
    
    private boolean jj_3_13() {
        if (jj_3R_64())
            return true;
        if (jj_scan_token(128))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_66())
            jj_scanpos = xsp;
        if (jj_scan_token(128))
            return true;
        xsp = jj_scanpos;
        if (jj_3R_67())
            jj_scanpos = xsp;
        if (jj_scan_token(128))
            return true;
        if (jj_3R_64())
            return true;
        return false;
    }
    
    private boolean jj_3R_172() {
        if (jj_3R_84())
            return true;
        return false;
    }
    
    private boolean jj_3R_83() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_172()) {
            jj_scanpos = xsp;
            if (jj_3R_173()) {
                jj_scanpos = xsp;
                if (jj_3R_174())
                    return true;
            }
        }
        return false;
    }
    
    private boolean jj_3_34() {
        if (jj_3R_83())
            return true;
        return false;
    }
    
    private boolean jj_3R_370() {
        if (jj_scan_token(K_NOT))
            return true;
        return false;
    }
    
    private boolean jj_3R_163() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_13()) {
            jj_scanpos = xsp;
            if (jj_3_14()) {
                jj_scanpos = xsp;
                if (jj_3_15()) {
                    jj_scanpos = xsp;
                    if (jj_3R_257())
                        return true;
                }
            }
        }
        return false;
    }
    
    private boolean jj_3R_332() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_370())
            jj_scanpos = xsp;
        if (jj_scan_token(125))
            return true;
        if (jj_3R_82())
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3_33() {
        if (jj_scan_token(K_AND))
            return true;
        return false;
    }
    
    private boolean jj_3R_79() {
        if (jj_scan_token(129))
            return true;
        return false;
    }
    
    private boolean jj_3R_331() {
        if (jj_3R_83())
            return true;
        return false;
    }
    
    private boolean jj_3R_64() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(116)) {
            jj_scanpos = xsp;
            if (jj_scan_token(120)) {
                jj_scanpos = xsp;
                if (jj_scan_token(73)) {
                    jj_scanpos = xsp;
                    if (jj_scan_token(7)) {
                        jj_scanpos = xsp;
                        if (jj_scan_token(78)) {
                            jj_scanpos = xsp;
                            if (jj_scan_token(96)) {
                                jj_scanpos = xsp;
                                if (jj_scan_token(102)) {
                                    jj_scanpos = xsp;
                                    if (jj_scan_token(97)) {
                                        jj_scanpos = xsp;
                                        if (jj_scan_token(80)) {
                                            jj_scanpos = xsp;
                                            if (jj_scan_token(95)) {
                                                jj_scanpos = xsp;
                                                if (jj_scan_token(77)) {
                                                    jj_scanpos = xsp;
                                                    if (jj_scan_token(99)) {
                                                        jj_scanpos = xsp;
                                                        if (jj_scan_token(104)) {
                                                            jj_scanpos = xsp;
                                                            if (jj_scan_token(98)) {
                                                                jj_scanpos = xsp;
                                                                if (jj_scan_token(91)) {
                                                                    jj_scanpos = xsp;
                                                                    if (jj_scan_token(55)) {
                                                                        jj_scanpos = xsp;
                                                                        if (jj_scan_token(44)) {
                                                                            jj_scanpos = xsp;
                                                                            if (jj_scan_token(94)) {
                                                                                jj_scanpos = xsp;
                                                                                if (jj_scan_token(68)) {
                                                                                    jj_scanpos = xsp;
                                                                                    if (jj_scan_token(70))
                                                                                        return true;
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean jj_3R_269() {
        if (jj_scan_token(K_AND))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_331()) {
            jj_scanpos = xsp;
            if (jj_3R_332())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_341() {
        if (jj_scan_token(K_ALL))
            return true;
        return false;
    }
    
    private boolean jj_3R_284() {
        if (jj_scan_token(128))
            return true;
        if (jj_3R_64())
            return true;
        return false;
    }
    
    private boolean jj_3_32() {
        if (jj_3R_83())
            return true;
        return false;
    }
    
    private boolean jj_3R_330() {
        if (jj_scan_token(K_NOT))
            return true;
        return false;
    }
    
    private boolean jj_3R_135() {
        if (jj_3R_64())
            return true;
        return false;
    }
    
    private boolean jj_3_12() {
        if (jj_3R_64())
            return true;
        if (jj_scan_token(128))
            return true;
        if (jj_3R_64())
            return true;
        return false;
    }
    
    private boolean jj_3R_268() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_330())
            jj_scanpos = xsp;
        if (jj_scan_token(125))
            return true;
        if (jj_3R_82())
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3_11() {
        if (jj_3R_64())
            return true;
        if (jj_scan_token(128))
            return true;
        if (jj_3R_64())
            return true;
        if (jj_scan_token(128))
            return true;
        if (jj_3R_64())
            return true;
        return false;
    }
    
    private boolean jj_3_10() {
        if (jj_3R_64())
            return true;
        if (jj_scan_token(128))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_65())
            jj_scanpos = xsp;
        if (jj_scan_token(128))
            return true;
        if (jj_3R_64())
            return true;
        if (jj_scan_token(128))
            return true;
        if (jj_3R_64())
            return true;
        return false;
    }
    
    private boolean jj_3R_252() {
        if (jj_scan_token(129))
            return true;
        return false;
    }
    
    private boolean jj_3R_267() {
        if (jj_3R_83())
            return true;
        return false;
    }
    
    private boolean jj_3R_170() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_267()) {
            jj_scanpos = xsp;
            if (jj_3R_268())
                return true;
        }
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_269()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_121() {
        if (jj_3R_223())
            return true;
        return false;
    }
    
    private boolean jj_3R_59() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_10()) {
            jj_scanpos = xsp;
            if (jj_3_11()) {
                jj_scanpos = xsp;
                if (jj_3_12()) {
                    jj_scanpos = xsp;
                    if (jj_3R_135())
                        return true;
                }
            }
        }
        return false;
    }
    
    private boolean jj_3R_359() {
        if (jj_scan_token(129))
            return true;
        return false;
    }
    
    private boolean jj_3_31() {
        if (jj_scan_token(K_OR))
            return true;
        return false;
    }
    
    private boolean jj_3R_55() {
        if (jj_scan_token(K_CREATE))
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_121()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(K_INDEX))
            return true;
        if (jj_3R_64())
            return true;
        return false;
    }
    
    private boolean jj_3R_171() {
        if (jj_scan_token(K_OR))
            return true;
        if (jj_3R_170())
            return true;
        return false;
    }
    
    private boolean jj_3R_227() {
        if (jj_scan_token(K_DELETE))
            return true;
        return false;
    }
    
    private boolean jj_3R_82() {
        if (jj_3R_170())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_171()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_397() {
        if (jj_scan_token(125))
            return true;
        if (jj_3R_368())
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_91() {
        if (jj_3R_120())
            return true;
        return false;
    }
    
    private boolean jj_3_30() {
        if (jj_3R_82())
            return true;
        return false;
    }
    
    private boolean jj_3R_340() {
        if (jj_scan_token(K_DISTINCT))
            return true;
        return false;
    }
    
    private boolean jj_3R_285() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_340()) {
            jj_scanpos = xsp;
            if (jj_3R_341())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_219() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_285())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_286()) {
            jj_scanpos = xsp;
            if (jj_3R_287())
                return true;
        }
        return false;
    }
    
    private boolean jj_3_8() {
        if (jj_scan_token(125))
            return true;
        return false;
    }
    
    private boolean jj_3R_396() {
        if (jj_3R_82())
            return true;
        return false;
    }
    
    private boolean jj_3R_62() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(64)) {
            jj_scanpos = xsp;
            if (jj_scan_token(55))
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_368() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_396()) {
            jj_scanpos = xsp;
            if (jj_3R_397())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_218() {
        if (jj_scan_token(128))
            return true;
        if (jj_3R_64())
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_284())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_78() {
        if (jj_scan_token(S_LONG))
            return true;
        return false;
    }
    
    private boolean jj_3R_217() {
        if (jj_scan_token(157))
            return true;
        return false;
    }
    
    private boolean jj_3R_110() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_217())
            jj_scanpos = xsp;
        if (jj_3R_64())
            return true;
        xsp = jj_scanpos;
        if (jj_3R_218())
            jj_scanpos = xsp;
        if (jj_scan_token(125))
            return true;
        xsp = jj_scanpos;
        if (jj_3R_219())
            jj_scanpos = xsp;
        if (jj_scan_token(126))
            return true;
        xsp = jj_scanpos;
        if (jj_scan_token(151))
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_404() {
        if (jj_scan_token(129))
            return true;
        return false;
    }
    
    private boolean jj_3R_384() {
        if (jj_scan_token(K_PERCENT))
            return true;
        return false;
    }
    
    private boolean jj_3R_403() {
        if (jj_scan_token(S_LONG))
            return true;
        return false;
    }
    
    private boolean jj_3R_256() {
        if (jj_scan_token(K_NULL))
            return true;
        return false;
    }
    
    private boolean jj_3R_382() {
        if (jj_scan_token(129))
            return true;
        return false;
    }
    
    private boolean jj_3R_255() {
        if (jj_scan_token(K_ALL))
            return true;
        return false;
    }
    
    private boolean jj_3R_381() {
        if (jj_scan_token(S_LONG))
            return true;
        return false;
    }
    
    private boolean jj_3_28() {
        if (jj_scan_token(K_OFFSET))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_78()) {
            jj_scanpos = xsp;
            if (jj_3R_79())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_383() {
        if (jj_scan_token(125))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_403()) {
            jj_scanpos = xsp;
            if (jj_3R_404())
                return true;
        }
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3_9() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_62())
            jj_scanpos = xsp;
        if (jj_scan_token(125))
            return true;
        if (jj_3R_63())
            return true;
        return false;
    }
    
    private boolean jj_3R_254() {
        if (jj_scan_token(129))
            return true;
        return false;
    }
    
    private boolean jj_3R_253() {
        if (jj_scan_token(S_LONG))
            return true;
        return false;
    }
    
    private boolean jj_3R_345() {
        if (jj_scan_token(K_TOP))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_381()) {
            jj_scanpos = xsp;
            if (jj_3R_382()) {
                jj_scanpos = xsp;
                if (jj_3R_383())
                    return true;
            }
        }
        xsp = jj_scanpos;
        if (jj_3R_384())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_81() {
        if (jj_scan_token(129))
            return true;
        return false;
    }
    
    private boolean jj_3_7() {
        if (jj_scan_token(125))
            return true;
        if (jj_3R_59())
            return true;
        return false;
    }
    
    private boolean jj_3R_80() {
        if (jj_scan_token(S_LONG))
            return true;
        return false;
    }
    
    private boolean jj_3R_162() {
        if (jj_scan_token(K_LIMIT))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_253()) {
            jj_scanpos = xsp;
            if (jj_3R_254()) {
                jj_scanpos = xsp;
                if (jj_3R_255()) {
                    jj_scanpos = xsp;
                    if (jj_3R_256())
                        return true;
                }
            }
        }
        xsp = jj_scanpos;
        if (jj_3_28())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_251() {
        if (jj_scan_token(S_LONG))
            return true;
        return false;
    }
    
    private boolean jj_3R_232() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(109)) {
            jj_scanpos = xsp;
            if (jj_scan_token(110))
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_226() {
        if (jj_scan_token(K_INSERT))
            return true;
        return false;
    }
    
    private boolean jj_3R_358() {
        if (jj_scan_token(S_LONG))
            return true;
        return false;
    }
    
    private boolean jj_3R_60() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(64)) {
            jj_scanpos = xsp;
            if (jj_scan_token(55))
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_161() {
        if (jj_scan_token(K_OFFSET))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_251()) {
            jj_scanpos = xsp;
            if (jj_3R_252())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_414() {
        if (jj_scan_token(K_WHEN))
            return true;
        if (jj_3R_61())
            return true;
        if (jj_scan_token(K_THEN))
            return true;
        if (jj_3R_63())
            return true;
        return false;
    }
    
    private boolean jj_3_6() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_60())
            jj_scanpos = xsp;
        if (jj_scan_token(125))
            return true;
        if (jj_3R_61())
            return true;
        return false;
    }
    
    private boolean jj_3R_413() {
        if (jj_scan_token(K_WHEN))
            return true;
        if (jj_3R_368())
            return true;
        if (jj_scan_token(K_THEN))
            return true;
        if (jj_3R_63())
            return true;
        return false;
    }
    
    private boolean jj_3_29() {
        if (jj_scan_token(K_LIMIT))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_80()) {
            jj_scanpos = xsp;
            if (jj_3R_81())
                return true;
        }
        if (jj_scan_token(123))
            return true;
        xsp = jj_scanpos;
        if (jj_3R_358()) {
            jj_scanpos = xsp;
            if (jj_3R_359())
                return true;
        }
        return false;
    }
    
    private boolean jj_3_5() {
        if (jj_scan_token(125))
            return true;
        if (jj_3R_59())
            return true;
        return false;
    }
    
    private boolean jj_3R_372() {
        if (jj_3R_61())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_400()) {
                jj_scanpos = xsp;
                break;
            }
        }
        xsp = jj_scanpos;
        if (jj_3R_401())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_426() {
        if (jj_scan_token(K_FOLLOWING))
            return true;
        return false;
    }
    
    private boolean jj_3R_401() {
        if (jj_scan_token(K_ELSE))
            return true;
        if (jj_3R_63())
            return true;
        return false;
    }
    
    private boolean jj_3R_400() {
        if (jj_3R_414())
            return true;
        return false;
    }
    
    private boolean jj_3R_399() {
        if (jj_scan_token(K_ELSE))
            return true;
        if (jj_3R_63())
            return true;
        return false;
    }
    
    private boolean jj_3R_398() {
        if (jj_3R_413())
            return true;
        return false;
    }
    
    private boolean jj_3R_70() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_29()) {
            jj_scanpos = xsp;
            if (jj_3R_161()) {
                jj_scanpos = xsp;
                if (jj_3R_162())
                    return true;
            }
        }
        return false;
    }
    
    private boolean jj_3R_371() {
        Token xsp;
        if (jj_3R_398())
            return true;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_398()) {
                jj_scanpos = xsp;
                break;
            }
        }
        xsp = jj_scanpos;
        if (jj_3R_399())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_424() {
        if (jj_scan_token(K_FOLLOWING))
            return true;
        return false;
    }
    
    private boolean jj_3R_233() {
        if (jj_scan_token(K_CASE))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_371()) {
            jj_scanpos = xsp;
            if (jj_3R_372())
                return true;
        }
        if (jj_scan_token(K_END))
            return true;
        return false;
    }
    
    private boolean jj_3R_393() {
        if (jj_scan_token(K_DESC))
            return true;
        return false;
    }
    
    private boolean jj_3R_282() {
        if (jj_scan_token(127))
            return true;
        return false;
    }
    
    private boolean jj_3R_406() {
        if (jj_scan_token(K_LAST))
            return true;
        return false;
    }
    
    private boolean jj_3R_405() {
        if (jj_scan_token(K_FIRST))
            return true;
        return false;
    }
    
    private boolean jj_3R_394() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_405()) {
            jj_scanpos = xsp;
            if (jj_3R_406())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_314() {
        if (jj_3R_63())
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_356())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_357())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_357() {
        if (jj_scan_token(K_NULLS))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_394())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_356() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(18)) {
            jj_scanpos = xsp;
            if (jj_3R_393())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_228() {
        if (jj_scan_token(K_REPLACE))
            return true;
        return false;
    }
    
    private boolean jj_3R_114() {
        if (jj_scan_token(K_CAST))
            return true;
        if (jj_scan_token(125))
            return true;
        if (jj_3R_63())
            return true;
        if (jj_scan_token(K_AS))
            return true;
        if (jj_3R_220())
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_375() {
        if (jj_scan_token(K_RANGE))
            return true;
        return false;
    }
    
    private boolean jj_3R_315() {
        if (jj_scan_token(123))
            return true;
        if (jj_3R_314())
            return true;
        return false;
    }
    
    private boolean jj_3R_250() {
        if (jj_scan_token(K_ORDER))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(91))
            jj_scanpos = xsp;
        if (jj_scan_token(K_BY))
            return true;
        if (jj_3R_314())
            return true;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_315()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_106() {
        if (jj_scan_token(K_EXTRACT))
            return true;
        if (jj_scan_token(125))
            return true;
        if (jj_scan_token(S_IDENTIFIER))
            return true;
        if (jj_scan_token(K_FROM))
            return true;
        if (jj_3R_63())
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_391() {
        if (jj_scan_token(K_NOCYCLE))
            return true;
        return false;
    }
    
    private boolean jj_3R_353() {
        if (jj_scan_token(K_HAVING))
            return true;
        if (jj_3R_368())
            return true;
        return false;
    }
    
    private boolean jj_3R_373() {
        if (jj_scan_token(123))
            return true;
        if (jj_3R_63())
            return true;
        return false;
    }
    
    private boolean jj_3R_225() {
        if (jj_scan_token(K_UPDATE))
            return true;
        return false;
    }
    
    private boolean jj_3R_423() {
        if (jj_scan_token(K_PRECEDING))
            return true;
        return false;
    }
    
    private boolean jj_3R_425() {
        if (jj_scan_token(K_PRECEDING))
            return true;
        return false;
    }
    
    private boolean jj_3R_392() {
        if (jj_scan_token(123))
            return true;
        if (jj_3R_63())
            return true;
        return false;
    }
    
    private boolean jj_3R_367() {
        if (jj_scan_token(K_OUTER))
            return true;
        return false;
    }
    
    private boolean jj_3R_352() {
        if (jj_scan_token(K_GROUP))
            return true;
        if (jj_scan_token(K_BY))
            return true;
        if (jj_3R_63())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_392()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_417() {
        if (jj_3R_63())
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_425()) {
            jj_scanpos = xsp;
            if (jj_3R_426())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_390() {
        if (jj_scan_token(K_START))
            return true;
        if (jj_scan_token(K_WITH))
            return true;
        if (jj_3R_170())
            return true;
        return false;
    }
    
    private boolean jj_3R_416() {
        if (jj_scan_token(K_CURRENT))
            return true;
        if (jj_scan_token(K_ROW))
            return true;
        return false;
    }
    
    private boolean jj_3R_351() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_390())
            jj_scanpos = xsp;
        if (jj_scan_token(K_CONNECT))
            return true;
        if (jj_scan_token(K_BY))
            return true;
        xsp = jj_scanpos;
        if (jj_3R_391())
            jj_scanpos = xsp;
        if (jj_3R_170())
            return true;
        return false;
    }
    
    private boolean jj_3R_369() {
        if (jj_scan_token(123))
            return true;
        if (jj_3R_59())
            return true;
        return false;
    }
    
    private boolean jj_3R_415() {
        if (jj_scan_token(K_UNBOUNDED))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_423()) {
            jj_scanpos = xsp;
            if (jj_3R_424())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_402() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_415()) {
            jj_scanpos = xsp;
            if (jj_3R_416()) {
                jj_scanpos = xsp;
                if (jj_3R_417())
                    return true;
            }
        }
        return false;
    }
    
    private boolean jj_3_4() {
        if (jj_scan_token(122))
            return true;
        if (jj_3R_58())
            return true;
        return false;
    }
    
    private boolean jj_3R_338() {
        if (jj_scan_token(123))
            return true;
        if (jj_3R_63())
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_373())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_350() {
        if (jj_scan_token(K_WHERE))
            return true;
        if (jj_3R_368())
            return true;
        return false;
    }
    
    private boolean jj_3R_262() {
        if (jj_scan_token(123))
            return true;
        return false;
    }
    
    private boolean jj_3R_377() {
        if (jj_3R_402())
            return true;
        return false;
    }
    
    private boolean jj_3R_327() {
        if (jj_scan_token(K_USING))
            return true;
        if (jj_scan_token(125))
            return true;
        if (jj_3R_59())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_369()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_283() {
        if (jj_3R_339())
            return true;
        return false;
    }
    
    private boolean jj_3R_134() {
        if (jj_3R_232())
            return true;
        return false;
    }
    
    private boolean jj_3R_326() {
        if (jj_scan_token(K_ON))
            return true;
        if (jj_3R_368())
            return true;
        return false;
    }
    
    private boolean jj_3R_133() {
        if (jj_3R_231())
            return true;
        return false;
    }
    
    private boolean jj_3R_263() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_326()) {
            jj_scanpos = xsp;
            if (jj_3R_327())
                return true;
        }
        return false;
    }
    
    private boolean jj_3_3() {
        if (jj_3R_57())
            return true;
        return false;
    }
    
    private boolean jj_3R_376() {
        if (jj_scan_token(K_BETWEEN))
            return true;
        if (jj_3R_402())
            return true;
        if (jj_scan_token(K_AND))
            return true;
        if (jj_3R_402())
            return true;
        return false;
    }
    
    private boolean jj_3R_325() {
        if (jj_scan_token(K_CROSS))
            return true;
        return false;
    }
    
    private boolean jj_3R_132() {
        if (jj_3R_230())
            return true;
        return false;
    }
    
    private boolean jj_3R_374() {
        if (jj_scan_token(K_ROWS))
            return true;
        return false;
    }
    
    private boolean jj_3_2() {
        if (jj_3R_56())
            return true;
        return false;
    }
    
    private boolean jj_3R_366() {
        if (jj_scan_token(K_FULL))
            return true;
        return false;
    }
    
    private boolean jj_3R_339() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_374()) {
            jj_scanpos = xsp;
            if (jj_3R_375())
                return true;
        }
        xsp = jj_scanpos;
        if (jj_3R_376()) {
            jj_scanpos = xsp;
            if (jj_3R_377())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_365() {
        if (jj_scan_token(K_RIGHT))
            return true;
        return false;
    }
    
    private boolean jj_3R_131() {
        if (jj_3R_229())
            return true;
        return false;
    }
    
    private boolean jj_3R_324() {
        if (jj_scan_token(K_NATURAL))
            return true;
        return false;
    }
    
    private boolean jj_3_1() {
        if (jj_3R_55())
            return true;
        return false;
    }
    
    private boolean jj_3R_323() {
        if (jj_scan_token(K_INNER))
            return true;
        return false;
    }
    
    private boolean jj_3R_364() {
        if (jj_scan_token(K_LEFT))
            return true;
        return false;
    }
    
    private boolean jj_3R_130() {
        if (jj_3R_228())
            return true;
        return false;
    }
    
    private boolean jj_3R_322() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_364()) {
            jj_scanpos = xsp;
            if (jj_3R_365()) {
                jj_scanpos = xsp;
                if (jj_3R_366())
                    return true;
            }
        }
        xsp = jj_scanpos;
        if (jj_3R_367())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_261() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_322()) {
            jj_scanpos = xsp;
            if (jj_3R_323()) {
                jj_scanpos = xsp;
                if (jj_3R_324()) {
                    jj_scanpos = xsp;
                    if (jj_3R_325())
                        return true;
                }
            }
        }
        return false;
    }
    
    private boolean jj_3R_213() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_281()) {
            jj_scanpos = xsp;
            if (jj_3R_282())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_281() {
        if (jj_3R_63())
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_338())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_129() {
        if (jj_3R_227())
            return true;
        return false;
    }
    
    private boolean jj_3R_167() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_261())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(27)) {
            jj_scanpos = xsp;
            if (jj_3R_262())
                return true;
        }
        if (jj_3R_166())
            return true;
        xsp = jj_scanpos;
        if (jj_3R_263())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_128() {
        if (jj_3R_226())
            return true;
        return false;
    }
    
    private boolean jj_3R_58() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_126()) {
            jj_scanpos = xsp;
            if (jj_3R_127()) {
                jj_scanpos = xsp;
                if (jj_3R_128()) {
                    jj_scanpos = xsp;
                    if (jj_3R_129()) {
                        jj_scanpos = xsp;
                        if (jj_3R_130()) {
                            jj_scanpos = xsp;
                            if (jj_3R_131()) {
                                jj_scanpos = xsp;
                                if (jj_3_1()) {
                                    jj_scanpos = xsp;
                                    if (jj_3_2()) {
                                        jj_scanpos = xsp;
                                        if (jj_3_3()) {
                                            jj_scanpos = xsp;
                                            if (jj_3R_132()) {
                                                jj_scanpos = xsp;
                                                if (jj_3R_133()) {
                                                    jj_scanpos = xsp;
                                                    if (jj_3R_134())
                                                        return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean jj_3R_127() {
        if (jj_3R_225())
            return true;
        return false;
    }
    
    private boolean jj_3R_126() {
        if (jj_3R_224())
            return true;
        return false;
    }
    
    private boolean jj_3R_215() {
        if (jj_3R_250())
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_283())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_214() {
        if (jj_scan_token(K_PARTITION))
            return true;
        if (jj_scan_token(K_BY))
            return true;
        if (jj_3R_90())
            return true;
        return false;
    }
    
    private boolean jj_3R_105() {
        if (jj_scan_token(S_IDENTIFIER))
            return true;
        if (jj_scan_token(125))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_213())
            jj_scanpos = xsp;
        if (jj_scan_token(126))
            return true;
        if (jj_scan_token(K_OVER))
            return true;
        if (jj_scan_token(125))
            return true;
        xsp = jj_scanpos;
        if (jj_3R_214())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_215())
            jj_scanpos = xsp;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_216() {
        if (jj_scan_token(156))
            return true;
        if (jj_scan_token(S_CHAR_LITERAL))
            return true;
        return false;
    }
    
    private boolean jj_3R_242() {
        if (jj_scan_token(K_INTERVAL))
            return true;
        if (jj_scan_token(S_CHAR_LITERAL))
            return true;
        return false;
    }
    
    private boolean jj_3R_389() {
        if (jj_3R_167())
            return true;
        return false;
    }
    
    private boolean jj_3R_349() {
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_389()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_108() {
        if (jj_3R_59())
            return true;
        Token xsp;
        if (jj_3R_216())
            return true;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_216()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_241() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(145)) {
            jj_scanpos = xsp;
            if (jj_scan_token(146))
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_148() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_241())
            jj_scanpos = xsp;
        if (jj_scan_token(125))
            return true;
        if (jj_3R_91())
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_116() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(145)) {
            jj_scanpos = xsp;
            if (jj_scan_token(146))
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_240() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(145)) {
            jj_scanpos = xsp;
            if (jj_scan_token(146))
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_239() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(145)) {
            jj_scanpos = xsp;
            if (jj_scan_token(146))
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_234() {
        if (jj_scan_token(155))
            return true;
        if (jj_scan_token(S_IDENTIFIER))
            return true;
        return false;
    }
    
    private boolean jj_3R_235() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(145)) {
            jj_scanpos = xsp;
            if (jj_scan_token(146))
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_238() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(145)) {
            jj_scanpos = xsp;
            if (jj_scan_token(146))
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_74() {
        if (jj_3R_166())
            return true;
        if (jj_3R_167())
            return true;
        return false;
    }
    
    private boolean jj_3R_237() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(145)) {
            jj_scanpos = xsp;
            if (jj_scan_token(146))
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_236() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(145)) {
            jj_scanpos = xsp;
            if (jj_scan_token(146))
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_329() {
        if (jj_scan_token(123))
            return true;
        if (jj_3R_64())
            return true;
        return false;
    }
    
    private boolean jj_3R_362() {
        if (jj_scan_token(K_LATERAL))
            return true;
        if (jj_scan_token(125))
            return true;
        if (jj_3R_91())
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_328() {
        if (jj_scan_token(123))
            return true;
        if (jj_3R_63())
            return true;
        return false;
    }
    
    private boolean jj_3R_153() {
        if (jj_3R_242())
            return true;
        return false;
    }
    
    private boolean jj_3R_152() {
        if (jj_scan_token(153))
            return true;
        if (jj_scan_token(S_CHAR_LITERAL))
            return true;
        if (jj_scan_token(151))
            return true;
        return false;
    }
    
    private boolean jj_3R_363() {
        if (jj_3R_395())
            return true;
        return false;
    }
    
    private boolean jj_3R_266() {
        if (jj_scan_token(125))
            return true;
        if (jj_3R_64())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_329()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_115() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(145)) {
            jj_scanpos = xsp;
            if (jj_scan_token(146))
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_151() {
        if (jj_scan_token(152))
            return true;
        if (jj_scan_token(S_CHAR_LITERAL))
            return true;
        if (jj_scan_token(151))
            return true;
        return false;
    }
    
    private boolean jj_3_56() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_115())
            jj_scanpos = xsp;
        if (jj_3R_59())
            return true;
        return false;
    }
    
    private boolean jj_3R_280() {
        if (jj_scan_token(154))
            return true;
        if (jj_3R_220())
            return true;
        return false;
    }
    
    private boolean jj_3R_113() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(145)) {
            jj_scanpos = xsp;
            if (jj_scan_token(146))
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_150() {
        if (jj_scan_token(150))
            return true;
        if (jj_scan_token(S_CHAR_LITERAL))
            return true;
        if (jj_scan_token(151))
            return true;
        return false;
    }
    
    private boolean jj_3_55() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_113())
            jj_scanpos = xsp;
        if (jj_3R_114())
            return true;
        return false;
    }
    
    private boolean jj_3R_112() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(145)) {
            jj_scanpos = xsp;
            if (jj_scan_token(146))
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_264() {
        if (jj_scan_token(123))
            return true;
        if (jj_scan_token(125))
            return true;
        if (jj_3R_63())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_328()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3_54() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_112())
            jj_scanpos = xsp;
        if (jj_scan_token(S_LONG))
            return true;
        return false;
    }
    
    private boolean jj_3R_149() {
        if (jj_scan_token(S_CHAR_LITERAL))
            return true;
        return false;
    }
    
    private boolean jj_3R_111() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(145)) {
            jj_scanpos = xsp;
            if (jj_scan_token(146))
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_77() {
        if (jj_scan_token(123))
            return true;
        if (jj_3R_63())
            return true;
        return false;
    }
    
    private boolean jj_3R_265() {
        if (jj_scan_token(123))
            return true;
        if (jj_3R_63())
            return true;
        return false;
    }
    
    private boolean jj_3_53() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_111())
            jj_scanpos = xsp;
        if (jj_scan_token(S_DOUBLE))
            return true;
        return false;
    }
    
    private boolean jj_3R_109() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(145)) {
            jj_scanpos = xsp;
            if (jj_scan_token(146))
                return true;
        }
        return false;
    }
    
    private boolean jj_3_24() {
        if (jj_3R_74())
            return true;
        return false;
    }
    
    private boolean jj_3_52() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_109())
            jj_scanpos = xsp;
        if (jj_3R_110())
            return true;
        return false;
    }
    
    private boolean jj_3_57() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_116())
            jj_scanpos = xsp;
        if (jj_scan_token(125))
            return true;
        if (jj_3R_61())
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_169() {
        if (jj_3R_258())
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_266())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_147() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_240())
            jj_scanpos = xsp;
        if (jj_3R_59())
            return true;
        return false;
    }
    
    private boolean jj_3_50() {
        if (jj_3R_106())
            return true;
        return false;
    }
    
    private boolean jj_3R_146() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_239())
            jj_scanpos = xsp;
        if (jj_3R_114())
            return true;
        return false;
    }
    
    private boolean jj_3R_107() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(145)) {
            jj_scanpos = xsp;
            if (jj_scan_token(146))
                return true;
        }
        return false;
    }
    
    private boolean jj_3_49() {
        if (jj_3R_105())
            return true;
        return false;
    }
    
    private boolean jj_3_51() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_107())
            jj_scanpos = xsp;
        if (jj_3R_108())
            return true;
        return false;
    }
    
    private boolean jj_3R_145() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_238())
            jj_scanpos = xsp;
        if (jj_scan_token(S_LONG))
            return true;
        return false;
    }
    
    private boolean jj_3R_361() {
        if (jj_3R_91())
            return true;
        return false;
    }
    
    private boolean jj_3R_168() {
        if (jj_3R_63())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_265()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_144() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_237())
            jj_scanpos = xsp;
        if (jj_scan_token(S_DOUBLE))
            return true;
        return false;
    }
    
    private boolean jj_3R_360() {
        if (jj_3R_74())
            return true;
        return false;
    }
    
    private boolean jj_3R_143() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_236())
            jj_scanpos = xsp;
        if (jj_3R_110())
            return true;
        return false;
    }
    
    private boolean jj_3R_141() {
        if (jj_3R_106())
            return true;
        return false;
    }
    
    private boolean jj_3_27() {
        if (jj_scan_token(125))
            return true;
        if (jj_3R_63())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_77()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(126))
            return true;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_264()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_319() {
        if (jj_3R_362())
            return true;
        return false;
    }
    
    private boolean jj_3R_140() {
        if (jj_3R_105())
            return true;
        return false;
    }
    
    private boolean jj_3R_142() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_235())
            jj_scanpos = xsp;
        if (jj_3R_108())
            return true;
        return false;
    }
    
    private boolean jj_3R_318() {
        if (jj_3R_163())
            return true;
        return false;
    }
    
    private boolean jj_3R_76() {
        if (jj_scan_token(125))
            return true;
        if (jj_scan_token(K_VALUES))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_27()) {
            jj_scanpos = xsp;
            if (jj_3R_168())
                return true;
        }
        if (jj_scan_token(126))
            return true;
        xsp = jj_scanpos;
        if (jj_3R_169())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_321() {
        if (jj_3R_258())
            return true;
        return false;
    }
    
    private boolean jj_3_25() {
        if (jj_3R_75())
            return true;
        return false;
    }
    
    private boolean jj_3R_138() {
        if (jj_scan_token(129))
            return true;
        return false;
    }
    
    private boolean jj_3R_320() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_25()) {
            jj_scanpos = xsp;
            if (jj_3R_363())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_137() {
        if (jj_3R_233())
            return true;
        return false;
    }
    
    private boolean jj_3R_102() {
        if (jj_scan_token(148))
            return true;
        return false;
    }
    
    private boolean jj_3R_139() {
        if (jj_3R_234())
            return true;
        return false;
    }
    
    private boolean jj_3R_101() {
        if (jj_scan_token(147))
            return true;
        return false;
    }
    
    private boolean jj_3R_136() {
        if (jj_scan_token(K_NULL))
            return true;
        return false;
    }
    
    private boolean jj_3R_388() {
        if (jj_scan_token(123))
            return true;
        if (jj_3R_163())
            return true;
        return false;
    }
    
    private boolean jj_3R_317() {
        if (jj_scan_token(125))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_360()) {
            jj_scanpos = xsp;
            if (jj_3R_361())
                return true;
        }
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_104() {
        if (jj_scan_token(125))
            return true;
        if (jj_3R_95())
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_61() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_136()) {
            jj_scanpos = xsp;
            if (jj_3R_137()) {
                jj_scanpos = xsp;
                if (jj_3R_138()) {
                    jj_scanpos = xsp;
                    if (jj_3R_139()) {
                        jj_scanpos = xsp;
                        if (jj_3R_140()) {
                            jj_scanpos = xsp;
                            if (jj_3R_141()) {
                                jj_scanpos = xsp;
                                if (jj_3R_142()) {
                                    jj_scanpos = xsp;
                                    if (jj_3R_143()) {
                                        jj_scanpos = xsp;
                                        if (jj_3R_144()) {
                                            jj_scanpos = xsp;
                                            if (jj_3R_145()) {
                                                jj_scanpos = xsp;
                                                if (jj_3R_146()) {
                                                    jj_scanpos = xsp;
                                                    if (jj_3R_147()) {
                                                        jj_scanpos = xsp;
                                                        if (jj_3_57()) {
                                                            jj_scanpos = xsp;
                                                            if (jj_3R_148()) {
                                                                jj_scanpos = xsp;
                                                                if (jj_3R_149()) {
                                                                    jj_scanpos = xsp;
                                                                    if (jj_3R_150()) {
                                                                        jj_scanpos = xsp;
                                                                        if (jj_3R_151()) {
                                                                            jj_scanpos = xsp;
                                                                            if (jj_3R_152()) {
                                                                                jj_scanpos = xsp;
                                                                                if (jj_3R_153())
                                                                                    return true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        xsp = jj_scanpos;
        if (jj_3R_280())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3_26() {
        if (jj_3R_76())
            return true;
        return false;
    }
    
    private boolean jj_3_48() {
        if (jj_3R_99())
            return true;
        return false;
    }
    
    private boolean jj_3R_260() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_317()) {
            jj_scanpos = xsp;
            if (jj_3R_318()) {
                jj_scanpos = xsp;
                if (jj_3R_319())
                    return true;
            }
        }
        xsp = jj_scanpos;
        if (jj_3R_320())
            jj_scanpos = xsp;
        xsp = jj_scanpos;
        if (jj_3R_321())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_259() {
        if (jj_3R_76())
            return true;
        return false;
    }
    
    private boolean jj_3R_212() {
        if (jj_scan_token(149))
            return true;
        if (jj_3R_61())
            return true;
        return false;
    }
    
    private boolean jj_3R_103() {
        if (jj_3R_99())
            return true;
        return false;
    }
    
    private boolean jj_3R_97() {
        if (jj_scan_token(146))
            return true;
        return false;
    }
    
    private boolean jj_3R_99() {
        if (jj_3R_61())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_212()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_166() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_259()) {
            jj_scanpos = xsp;
            if (jj_3R_260())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_100() {
        if (jj_scan_token(127))
            return true;
        return false;
    }
    
    private boolean jj_3R_348() {
        if (jj_scan_token(K_INTO))
            return true;
        if (jj_3R_163())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_388()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3_46() {
        if (jj_3R_99())
            return true;
        return false;
    }
    
    private boolean jj_3_47() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_100()) {
            jj_scanpos = xsp;
            if (jj_3R_101()) {
                jj_scanpos = xsp;
                if (jj_3R_102())
                    return true;
            }
        }
        xsp = jj_scanpos;
        if (jj_3R_103()) {
            jj_scanpos = xsp;
            if (jj_3R_104())
                return true;
        }
        return false;
    }
    
    private boolean jj_3R_211() {
        if (jj_scan_token(125))
            return true;
        if (jj_3R_95())
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_411() {
        if (jj_3R_422())
            return true;
        return false;
    }
    
    private boolean jj_3_23() {
        if (jj_3R_73())
            return true;
        return false;
    }
    
    private boolean jj_3R_410() {
        if (jj_3R_120())
            return true;
        return false;
    }
    
    private boolean jj_3R_210() {
        if (jj_3R_99())
            return true;
        return false;
    }
    
    private boolean jj_3R_409() {
        if (jj_scan_token(K_ANY))
            return true;
        return false;
    }
    
    private boolean jj_3R_98() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_210()) {
            jj_scanpos = xsp;
            if (jj_3R_211())
                return true;
        }
        while (true) {
            xsp = jj_scanpos;
            if (jj_3_47()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_75() {
        if (jj_scan_token(K_PIVOT))
            return true;
        if (jj_scan_token(K_XML))
            return true;
        if (jj_scan_token(125))
            return true;
        if (jj_3R_407())
            return true;
        if (jj_scan_token(K_FOR))
            return true;
        if (jj_3R_408())
            return true;
        if (jj_scan_token(K_IN))
            return true;
        if (jj_scan_token(125))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_409()) {
            jj_scanpos = xsp;
            if (jj_3R_410()) {
                jj_scanpos = xsp;
                if (jj_3_23()) {
                    jj_scanpos = xsp;
                    if (jj_3R_411())
                        return true;
                }
            }
        }
        if (jj_scan_token(126))
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_96() {
        if (jj_scan_token(145))
            return true;
        return false;
    }
    
    private boolean jj_3R_94() {
        if (jj_scan_token(144))
            return true;
        return false;
    }
    
    private boolean jj_3R_93() {
        if (jj_scan_token(143))
            return true;
        return false;
    }
    
    private boolean jj_3R_229() {
        if (jj_scan_token(K_ALTER))
            return true;
        return false;
    }
    
    private boolean jj_3_45() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_96()) {
            jj_scanpos = xsp;
            if (jj_3R_97())
                return true;
        }
        if (jj_3R_98())
            return true;
        return false;
    }
    
    private boolean jj_3R_412() {
        if (jj_3R_422())
            return true;
        return false;
    }
    
    private boolean jj_3R_209() {
        if (jj_3R_98())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3_45()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3_22() {
        if (jj_3R_73())
            return true;
        return false;
    }
    
    private boolean jj_3R_231() {
        if (jj_scan_token(K_TRUNCATE))
            return true;
        return false;
    }
    
    private boolean jj_3R_395() {
        if (jj_scan_token(K_PIVOT))
            return true;
        if (jj_scan_token(125))
            return true;
        if (jj_3R_407())
            return true;
        if (jj_scan_token(K_FOR))
            return true;
        if (jj_3R_408())
            return true;
        if (jj_scan_token(K_IN))
            return true;
        if (jj_scan_token(125))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_22()) {
            jj_scanpos = xsp;
            if (jj_3R_412())
                return true;
        }
        if (jj_scan_token(126))
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3_44() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_93()) {
            jj_scanpos = xsp;
            if (jj_3R_94())
                return true;
        }
        if (jj_3R_95())
            return true;
        return false;
    }
    
    private boolean jj_3R_430() {
        if (jj_scan_token(123))
            return true;
        if (jj_3R_429())
            return true;
        return false;
    }
    
    private boolean jj_3R_422() {
        if (jj_3R_429())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_430()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_92() {
        if (jj_3R_95())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3_44()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_431() {
        if (jj_3R_258())
            return true;
        return false;
    }
    
    private boolean jj_3R_230() {
        if (jj_scan_token(K_DROP))
            return true;
        return false;
    }
    
    private boolean jj_3R_429() {
        if (jj_scan_token(125))
            return true;
        if (jj_3R_90())
            return true;
        if (jj_scan_token(126))
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_431())
            jj_scanpos = xsp;
        return false;
    }
    
    private boolean jj_3R_378() {
        if (jj_scan_token(123))
            return true;
        return false;
    }
    
    private boolean jj_3R_279() {
        if (jj_scan_token(142))
            return true;
        if (jj_3R_209())
            return true;
        return false;
    }
    
    private boolean jj_3R_118() {
        if (jj_scan_token(125))
            return true;
        return false;
    }
    
    private boolean jj_3R_165() {
        if (jj_scan_token(123))
            return true;
        if (jj_3R_72())
            return true;
        return false;
    }
    
    private boolean jj_3_43() {
        if (jj_3R_92())
            return true;
        return false;
    }
    
    private boolean jj_3R_73() {
        if (jj_3R_72())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_165()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_95() {
        if (jj_3R_209())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_279()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_155() {
        if (jj_scan_token(125))
            return true;
        if (jj_3R_92())
            return true;
        if (jj_scan_token(126))
            return true;
        return false;
    }
    
    private boolean jj_3R_419() {
        if (jj_scan_token(123))
            return true;
        if (jj_3R_418())
            return true;
        return false;
    }
    
    private boolean jj_3R_154() {
        if (jj_3R_92())
            return true;
        return false;
    }
    
    private boolean jj_3R_407() {
        if (jj_3R_418())
            return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_419()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }
    
    private boolean jj_3R_379() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(112)) {
            jj_scanpos = xsp;
            if (jj_scan_token(111)) {
                jj_scanpos = xsp;
                if (jj_scan_token(119)) {
                    jj_scanpos = xsp;
                    if (jj_scan_token(116))
                        return true;
                }
            }
        }
        return false;
    }
    
    private boolean jj_3R_300() {
        if (jj_3R_342())
            return true;
        return false;
    }
    
    /** Generated Token Manager. */
    public CCJSqlParserTokenManager token_source;
    
    SimpleCharStream jj_input_stream;
    
    /** Current token. */
    public Token token;
    
    /** Next token. */
    public Token jj_nt;
    
    private int jj_ntk;
    
    private Token jj_scanpos, jj_lastpos;
    
    private int jj_la;
    
    private int jj_gen;
    
    final private int[] jj_la1 = new int[242];
    
    static private int[] jj_la1_0;
    
    static private int[] jj_la1_1;
    
    static private int[] jj_la1_2;
    
    static private int[] jj_la1_3;
    
    static private int[] jj_la1_4;
    static {
        jj_la1_init_0();
        jj_la1_init_1();
        jj_la1_init_2();
        jj_la1_init_3();
        jj_la1_init_4();
    }
    
    private static void jj_la1_init_0() {
        jj_la1_0 =
            new int[] {0x0, 0x0, 0x4000000, 0x0, 0x0, 0x0, 0x40000000, 0x0, 0x800000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x20000, 0x0, 0x800000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x1000080, 0x0, 0x40000000, 0x0, 0x80,
                0x80, 0x80, 0x80, 0x80, 0x80, 0x80, 0xa0, 0x0, 0x0, 0x800, 0x1000, 0x1000, 0x80000, 0x800000,
                0x40000000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x1000, 0x1000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0xa0, 0x0,
                0x20, 0xa0, 0x0, 0x80, 0x0, 0x0, 0xa0, 0x0, 0x0, 0x4000, 0x0, 0x0, 0x0, 0x80, 0x0, 0x0, 0xa0, 0x80,
                0x0, 0x0, 0x0, 0x0, 0x1000080, 0x0, 0x0, 0xa0, 0x38000000, 0x10000000, 0x0, 0x30000000, 0x30000000,
                0x8000000, 0x0, 0x800, 0x800, 0x0, 0x0, 0x0, 0x0, 0x0, 0x440000, 0x440000, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x1001000, 0x0, 0x0, 0x0, 0x0, 0x100000, 0x0, 0x10000, 0x10000, 0x10000, 0x10000, 0x80, 0x0,
                0x10000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x1000080, 0x0, 0x1000080, 0x0, 0x1000080, 0x10000, 0x1000080,
                0x10000, 0x10000, 0x0, 0x10000, 0x1010080, 0x10000, 0x0, 0x0, 0x1005080, 0x4000, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x1000000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x1000080, 0x1000080, 0x0, 0x0, 0x0, 0x0, 0x1000080, 0x0, 0x0,
                0x1000080, 0x0, 0x0, 0x0, 0x0, 0x1000080, 0x0, 0x1000080, 0x0, 0x0, 0x0, 0x1000, 0x1000, 0x1000080,
                0x1001080, 0x0, 0x1018000, 0x0, 0x1458000, 0x1458000, 0x0, 0x0, 0x1458000, 0x1458000, 0x1018000, 0x0,
                0x1018000, 0x0, 0x1018000, 0x0, 0x0, 0x0, 0x0, 0x1018000, 0x0, 0x8000, 0x1018000, 0x20, 0x20, 0x0, 0x0,
                0x0, 0x0, 0x0, 0x400, 0x0, 0x0, 0x0, 0x1018000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,};
    }
    
    private static void jj_la1_init_1() {
        jj_la1_1 =
            new int[] {0x0, 0xc5000040, 0x0, 0x0, 0x0, 0x0, 0x0, 0x200, 0x0, 0x0, 0x0, 0x800000, 0x800000, 0x0,
                0x4000000, 0x4800000, 0x0, 0x0, 0x0, 0x800000, 0x800000, 0x0, 0x0, 0x0, 0x0, 0x4000040, 0x801001, 0x0,
                0x0, 0x200, 0x801000, 0x801000, 0x801000, 0x801000, 0x801000, 0x801000, 0x801000, 0x801000, 0x40,
                0x4000000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x200, 0x0, 0x8000, 0x20000000, 0x4000000, 0x0, 0x0, 0x4000,
                0x4000000, 0x4000, 0x200000, 0x8080000, 0x0, 0x0, 0x0, 0x801000, 0x0, 0x0, 0x801000, 0x0, 0x801000,
                0x0, 0x0, 0x801000, 0x0, 0x0, 0x4000000, 0x0, 0x0, 0x4000000, 0x801000, 0x800, 0x800, 0x801000,
                0x801000, 0x0, 0x0, 0x0, 0x0, 0x801001, 0x0, 0x0, 0x801000, 0x440020, 0x400020, 0x100000, 0x440020,
                0x440020, 0x0, 0x0, 0x2000, 0x2000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x0, 0x8080000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x801000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x801001, 0x0, 0x801001, 0x0, 0x801001, 0x0, 0x801001, 0x0, 0x0, 0x0, 0x0, 0x801001, 0x0, 0x0,
                0x0, 0x801011, 0x10, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x1, 0x0, 0x0, 0x0, 0x0, 0x0, 0x801001, 0x801001, 0x0, 0x0,
                0x200000, 0x0, 0x801001, 0x0, 0x0, 0x801001, 0x2, 0x8, 0x2, 0x8, 0x801003, 0x0, 0x801001, 0x0, 0x0,
                0x0, 0x0, 0x0, 0x801001, 0x801001, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x20000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x20080, 0x0, 0x0,};
    }
    
    private static void jj_la1_init_2() {
        jj_la1_2 =
            new int[] {0x0, 0x10000010, 0x40, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x1, 0x1, 0x0, 0x0, 0x1, 0x0,
                0x0, 0x0, 0x1, 0x1, 0x0, 0x0, 0x0, 0x0, 0x0, 0xc8036250, 0x0, 0x0, 0x0, 0xc8016250, 0xc8016250,
                0xc8016250, 0xc8016250, 0xc8016250, 0xc8016250, 0xc8016250, 0xc8016250, 0x0, 0x0, 0x0, 0x80, 0x80, 0x0,
                0x0, 0x0, 0x0, 0x1800000, 0x0, 0x0, 0x0, 0x80, 0x80, 0xd00, 0x0, 0xd00, 0x0, 0x0, 0x0, 0x0, 0x0,
                0xc8016250, 0x0, 0x0, 0xc8016250, 0x0, 0xc8016250, 0x0, 0x0, 0xc8016250, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0xc801e250, 0x0, 0x0, 0xc8016250, 0xc801e250, 0x0, 0x0, 0x0, 0x0, 0xc8036250, 0x0, 0x0, 0xc8016250,
                0x8, 0x0, 0x0, 0x8, 0x8, 0x0, 0x0, 0x0, 0x0, 0x800000, 0x4000000, 0x0, 0x8000000, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x80000000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0xc8016250,
                0x2000000, 0x0, 0x0, 0x0, 0x0, 0x2000000, 0x0, 0xc8036250, 0x0, 0xc8036250, 0x0, 0xc8036250, 0x0,
                0xc8036250, 0x0, 0x0, 0x2, 0x0, 0xc8036250, 0x0, 0x0, 0x0, 0xc8036250, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x20000, 0x0, 0x0, 0x0, 0x0, 0xc8036250, 0xc8036250, 0x2000, 0x0, 0x0, 0x0, 0xc8036270, 0x0, 0x0,
                0xc8036250, 0x0, 0x0, 0x0, 0x0, 0xc8036250, 0x0, 0xc8036250, 0x0, 0x0, 0x0, 0x80, 0x80, 0xc8036250,
                0xc80362d0, 0x0, 0x4, 0x0, 0x4, 0x4, 0x0, 0x0, 0x4, 0x4, 0x4, 0x0, 0x4, 0x0, 0x4, 0x0, 0x80000,
                0x80000, 0x0, 0x4, 0x0, 0xc0000, 0x4, 0x0, 0x0, 0x200000, 0x0, 0x0, 0x0, 0x200000, 0x0, 0x10000, 0x0,
                0x0, 0x4, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,};
    }
    
    private static void jj_la1_init_3() {
        jj_la1_3 =
            new int[] {0x4000000, 0x20000000, 0x6000, 0x4000000, 0x8000000, 0x8000000, 0x0, 0x0, 0x0, 0x8000000,
                0x8000000, 0x0, 0x0, 0x8000000, 0x20000000, 0x20000000, 0x8000000, 0x0, 0x8000000, 0x0, 0x0, 0x8000000,
                0x8000000, 0x8000000, 0x40000000, 0x20000000, 0xa191814f, 0x200, 0x0, 0x0, 0x110014f, 0x110014f,
                0x110014f, 0x110014f, 0x110014f, 0x110014f, 0x110014f, 0x110014f, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x20000000, 0x0, 0x0, 0x0, 0x20000000, 0x0, 0x0, 0x0, 0x8000000, 0x20000000,
                0x8000000, 0x110014f, 0x80000000, 0x0, 0x110014f, 0x8000000, 0x2110014f, 0x8000000, 0x8000000,
                0x110014f, 0x8000000, 0x20000000, 0x20000000, 0x20000000, 0x8000000, 0x20000000, 0x2110014f, 0x0, 0x0,
                0x110014f, 0x2110014f, 0x8000000, 0x8000000, 0x8000000, 0x8000000, 0x2191814f, 0x8000000, 0x20000000,
                0x110014f, 0x8000000, 0x0, 0x0, 0x0, 0x0, 0x8000000, 0x8000000, 0x0, 0x0, 0x0, 0x0, 0x8000000, 0x0,
                0x8000000, 0x0, 0x0, 0x3, 0x3, 0x0, 0x10000, 0x10000, 0x10000, 0x10000, 0x10000, 0x0, 0x10000,
                0x20010000, 0x0, 0x20000000, 0x0, 0x20000000, 0x0, 0x20000000, 0x110014f, 0x0, 0x0, 0x0, 0x400,
                0x10000800, 0x0, 0x0, 0x2191814f, 0x0, 0x2191814f, 0x0, 0x2191814f, 0x0, 0x2191814f, 0x0, 0x0, 0x0,
                0x0, 0x2191814f, 0x0, 0x8000000, 0x8000000, 0x2191814f, 0x0, 0x20000000, 0x0, 0x0, 0x0, 0x20000000,
                0x80000000, 0x20000000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x0, 0x20800000, 0x0, 0x0, 0x8000000, 0x8000000, 0xa191814f, 0xa191814f, 0x0, 0xc, 0x0, 0xc,
                0x219181df, 0x60, 0x60, 0x219181df, 0x0, 0x0, 0x0, 0x0, 0x2191814f, 0x6000, 0x2191814f, 0x0, 0x0, 0x0,
                0x0, 0x0, 0xa191814f, 0xa191814f, 0x0, 0x30918000, 0x1100000, 0x30918000, 0x30918000, 0x8000000,
                0x1100000, 0x30918000, 0x30918000, 0x30918000, 0x1000, 0x30918000, 0x1100000, 0x30918000, 0x8000000,
                0x0, 0x0, 0x1100000, 0x30918000, 0x0, 0x1100000, 0x30918000, 0x20000000, 0x20000000, 0x100000,
                0x810000, 0x810000, 0x8000000, 0x0, 0x0, 0x0, 0x20000000, 0x20000000, 0x30918000, 0x918000, 0x918000,
                0x8000000, 0x8000000, 0x100000, 0x100000, 0x1100000,};
    }
    
    private static void jj_la1_init_4() {
        jj_la1_4 =
            new int[] {0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x2b460002, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x2b460002, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x2, 0x2, 0x2, 0x2, 0x2, 0x0, 0x2, 0x2, 0x0,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x20000000, 0x0, 0x0, 0x4, 0x0, 0x3ff8, 0x0, 0x4, 0x2b460002, 0x4, 0x2b460002,
                0x4, 0x2b460002, 0x0, 0x2b460002, 0x0, 0x0, 0x0, 0x0, 0x2b460002, 0x0, 0x0, 0x0, 0x2b460002, 0x0, 0x0,
                0x4000, 0x18000, 0x60000, 0x0, 0x180000, 0x0, 0x200000, 0x60000, 0x60000, 0x60000, 0x60000, 0x60000,
                0x60000, 0x60000, 0x60000, 0x60000, 0x60000, 0x60000, 0x60000, 0x60000, 0x60000, 0x60000, 0x60000,
                0x8000002, 0x3460000, 0x4000000, 0x10000000, 0x0, 0x0, 0x2b460002, 0x2b460002, 0x0, 0x0, 0x0, 0x0,
                0x2b460002, 0x0, 0x0, 0x2b460002, 0x0, 0x0, 0x0, 0x0, 0x2b460002, 0x0, 0x2b460002, 0x20000000, 0x1,
                0x1, 0x0, 0x0, 0x2b460002, 0x2b460002, 0x800000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,};
    }
    
    final private JJCalls[] jj_2_rtns = new JJCalls[60];
    
    private boolean jj_rescan = false;
    
    private int jj_gc = 0;
    
    /** Constructor with InputStream. */
    public CCJSqlParser(java.io.InputStream stream) {
        this(stream, null);
    }
    
    /** Constructor with InputStream and supplied encoding */
    public CCJSqlParser(java.io.InputStream stream, String encoding) {
        try {
            jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
        }
        catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        token_source = new CCJSqlParserTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 242; i++)
            jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++)
            jj_2_rtns[i] = new JJCalls();
    }
    
    /** Reinitialise. */
    public void ReInit(java.io.InputStream stream) {
        ReInit(stream, null);
    }
    
    /** Reinitialise. */
    public void ReInit(java.io.InputStream stream, String encoding) {
        try {
            jj_input_stream.ReInit(stream, encoding, 1, 1);
        }
        catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 242; i++)
            jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++)
            jj_2_rtns[i] = new JJCalls();
    }
    
    /** Constructor. */
    public CCJSqlParser(java.io.Reader stream) {
        jj_input_stream = new SimpleCharStream(stream, 1, 1);
        token_source = new CCJSqlParserTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 242; i++)
            jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++)
            jj_2_rtns[i] = new JJCalls();
    }
    
    /** Reinitialise. */
    public void ReInit(java.io.Reader stream) {
        if (jj_input_stream == null) {
            jj_input_stream = new SimpleCharStream(stream, 1, 1);
        }
        else {
            jj_input_stream.ReInit(stream, 1, 1);
        }
        if (token_source == null) {
            token_source = new CCJSqlParserTokenManager(jj_input_stream);
        }
        
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 242; i++)
            jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++)
            jj_2_rtns[i] = new JJCalls();
    }
    
    /** Constructor with generated Token Manager. */
    public CCJSqlParser(CCJSqlParserTokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 242; i++)
            jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++)
            jj_2_rtns[i] = new JJCalls();
    }
    
    /** Reinitialise. */
    public void ReInit(CCJSqlParserTokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 242; i++)
            jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++)
            jj_2_rtns[i] = new JJCalls();
    }
    
    private Token jj_consume_token(int kind)
        throws ParseException {
        Token oldToken;
        if ((oldToken = token).next != null)
            token = token.next;
        else
            token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        if (token.kind == kind) {
            jj_gen++;
            if (++jj_gc > 100) {
                jj_gc = 0;
                for (int i = 0; i < jj_2_rtns.length; i++) {
                    JJCalls c = jj_2_rtns[i];
                    while (c != null) {
                        if (c.gen < jj_gen)
                            c.first = null;
                        c = c.next;
                    }
                }
            }
            return token;
        }
        token = oldToken;
        jj_kind = kind;
        throw generateParseException();
    }
    
    @SuppressWarnings("serial")
    static private final class LookaheadSuccess extends java.lang.Error {
    }
    
    final private LookaheadSuccess jj_ls = new LookaheadSuccess();
    
    private boolean jj_scan_token(int kind) {
        if (jj_scanpos == jj_lastpos) {
            jj_la--;
            if (jj_scanpos.next == null) {
                jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
            }
            else {
                jj_lastpos = jj_scanpos = jj_scanpos.next;
            }
        }
        else {
            jj_scanpos = jj_scanpos.next;
        }
        if (jj_rescan) {
            int i = 0;
            Token tok = token;
            while (tok != null && tok != jj_scanpos) {
                i++;
                tok = tok.next;
            }
            if (tok != null)
                jj_add_error_token(kind, i);
        }
        if (jj_scanpos.kind != kind)
            return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos)
            throw jj_ls;
        return false;
    }
    
    /** Get the next Token. */
    final public Token getNextToken() {
        if (token.next != null)
            token = token.next;
        else
            token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        jj_gen++;
        return token;
    }
    
    /** Get the specific Token. */
    final public Token getToken(int index) {
        Token t = token;
        for (int i = 0; i < index; i++) {
            if (t.next != null)
                t = t.next;
            else
                t = t.next = token_source.getNextToken();
        }
        return t;
    }
    
    private int jj_ntk_f() {
        if ((jj_nt = token.next) == null)
            return (jj_ntk = (token.next = token_source.getNextToken()).kind);
        else
            return (jj_ntk = jj_nt.kind);
    }
    
    private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
    
    private int[] jj_expentry;
    
    private int jj_kind = -1;
    
    private int[] jj_lasttokens = new int[100];
    
    private int jj_endpos;
    
    private void jj_add_error_token(int kind, int pos) {
        if (pos >= 100) {
            return;
        }
        
        if (pos == jj_endpos + 1) {
            jj_lasttokens[jj_endpos++] = kind;
        }
        else if (jj_endpos != 0) {
            jj_expentry = new int[jj_endpos];
            
            for (int i = 0; i < jj_endpos; i++) {
                jj_expentry[i] = jj_lasttokens[i];
            }
            
            for (int[] oldentry : jj_expentries) {
                if (oldentry.length == jj_expentry.length) {
                    boolean isMatched = true;
                    
                    for (int i = 0; i < jj_expentry.length; i++) {
                        if (oldentry[i] != jj_expentry[i]) {
                            isMatched = false;
                            break;
                        }
                        
                    }
                    if (isMatched) {
                        jj_expentries.add(jj_expentry);
                        break;
                    }
                }
            }
            
            if (pos != 0) {
                jj_lasttokens[(jj_endpos = pos) - 1] = kind;
            }
        }
    }
    
    /** Generate ParseException. */
    public ParseException generateParseException() {
        jj_expentries.clear();
        boolean[] la1tokens = new boolean[158];
        if (jj_kind >= 0) {
            la1tokens[jj_kind] = true;
            jj_kind = -1;
        }
        for (int i = 0; i < 242; i++) {
            if (jj_la1[i] == jj_gen) {
                for (int j = 0; j < 32; j++) {
                    if ((jj_la1_0[i] & (1 << j)) != 0) {
                        la1tokens[j] = true;
                    }
                    if ((jj_la1_1[i] & (1 << j)) != 0) {
                        la1tokens[32 + j] = true;
                    }
                    if ((jj_la1_2[i] & (1 << j)) != 0) {
                        la1tokens[64 + j] = true;
                    }
                    if ((jj_la1_3[i] & (1 << j)) != 0) {
                        la1tokens[96 + j] = true;
                    }
                    if ((jj_la1_4[i] & (1 << j)) != 0) {
                        la1tokens[128 + j] = true;
                    }
                }
            }
        }
        for (int i = 0; i < 158; i++) {
            if (la1tokens[i]) {
                jj_expentry = new int[1];
                jj_expentry[0] = i;
                jj_expentries.add(jj_expentry);
            }
        }
        jj_endpos = 0;
        jj_rescan_token();
        jj_add_error_token(0, 0);
        int[][] exptokseq = new int[jj_expentries.size()][];
        for (int i = 0; i < jj_expentries.size(); i++) {
            exptokseq[i] = jj_expentries.get(i);
        }
        return new ParseException(token, exptokseq, tokenImage);
    }
    
    /** Enable tracing. */
    final public void enable_tracing() {
    }
    
    /** Disable tracing. */
    final public void disable_tracing() {
    }
    
    private void jj_rescan_token() {
        jj_rescan = true;
        for (int i = 0; i < 60; i++) {
            try {
                JJCalls p = jj_2_rtns[i];
                
                do {
                    if (p.gen > jj_gen) {
                        jj_la = p.arg;
                        jj_lastpos = jj_scanpos = p.first;
                        switch (i) {
                            case 0:
                                jj_3_1();
                                break;
                            case 1:
                                jj_3_2();
                                break;
                            case 2:
                                jj_3_3();
                                break;
                            case 3:
                                jj_3_4();
                                break;
                            case 4:
                                jj_3_5();
                                break;
                            case 5:
                                jj_3_6();
                                break;
                            case 6:
                                jj_3_7();
                                break;
                            case 7:
                                jj_3_8();
                                break;
                            case 8:
                                jj_3_9();
                                break;
                            case 9:
                                jj_3_10();
                                break;
                            case 10:
                                jj_3_11();
                                break;
                            case 11:
                                jj_3_12();
                                break;
                            case 12:
                                jj_3_13();
                                break;
                            case 13:
                                jj_3_14();
                                break;
                            case 14:
                                jj_3_15();
                                break;
                            case 15:
                                jj_3_16();
                                break;
                            case 16:
                                jj_3_17();
                                break;
                            case 17:
                                jj_3_18();
                                break;
                            case 18:
                                jj_3_19();
                                break;
                            case 19:
                                jj_3_20();
                                break;
                            case 20:
                                jj_3_21();
                                break;
                            case 21:
                                jj_3_22();
                                break;
                            case 22:
                                jj_3_23();
                                break;
                            case 23:
                                jj_3_24();
                                break;
                            case 24:
                                jj_3_25();
                                break;
                            case 25:
                                jj_3_26();
                                break;
                            case 26:
                                jj_3_27();
                                break;
                            case 27:
                                jj_3_28();
                                break;
                            case 28:
                                jj_3_29();
                                break;
                            case 29:
                                jj_3_30();
                                break;
                            case 30:
                                jj_3_31();
                                break;
                            case 31:
                                jj_3_32();
                                break;
                            case 32:
                                jj_3_33();
                                break;
                            case 33:
                                jj_3_34();
                                break;
                            case 34:
                                jj_3_35();
                                break;
                            case 35:
                                jj_3_36();
                                break;
                            case 36:
                                jj_3_37();
                                break;
                            case 37:
                                jj_3_38();
                                break;
                            case 38:
                                jj_3_39();
                                break;
                            case 39:
                                jj_3_40();
                                break;
                            case 40:
                                jj_3_41();
                                break;
                            case 41:
                                jj_3_42();
                                break;
                            case 42:
                                jj_3_43();
                                break;
                            case 43:
                                jj_3_44();
                                break;
                            case 44:
                                jj_3_45();
                                break;
                            case 45:
                                jj_3_46();
                                break;
                            case 46:
                                jj_3_47();
                                break;
                            case 47:
                                jj_3_48();
                                break;
                            case 48:
                                jj_3_49();
                                break;
                            case 49:
                                jj_3_50();
                                break;
                            case 50:
                                jj_3_51();
                                break;
                            case 51:
                                jj_3_52();
                                break;
                            case 52:
                                jj_3_53();
                                break;
                            case 53:
                                jj_3_54();
                                break;
                            case 54:
                                jj_3_55();
                                break;
                            case 55:
                                jj_3_56();
                                break;
                            case 56:
                                jj_3_57();
                                break;
                            case 57:
                                jj_3_58();
                                break;
                            case 58:
                                jj_3_59();
                                break;
                            case 59:
                                jj_3_60();
                                break;
                        }
                    }
                    p = p.next;
                } while (p != null);
                
            }
            catch (LookaheadSuccess ls) {
            }
        }
        jj_rescan = false;
    }
    
    private void jj_save(int index, int xla) {
        JJCalls p = jj_2_rtns[index];
        while (p.gen > jj_gen) {
            if (p.next == null) {
                p = p.next = new JJCalls();
                break;
            }
            p = p.next;
        }
        
        p.gen = jj_gen + xla - jj_la;
        p.first = token;
        p.arg = xla;
    }
    
    static final class JJCalls {
        int gen;
        
        Token first;
        
        int arg;
        
        JJCalls next;
    }
    
}
