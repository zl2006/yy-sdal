package org.yy.dal.ds;

import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.yy.dal.ds.constants.DBType;
import org.yy.dal.ds.constants.DSType;
import org.yy.dal.nm.DbInstance;
import org.yy.dal.nm.DbNodeManager;
import org.yy.dal.nm.DefaultNodeManager;
import org.yy.dal.nm.parse.MysqlDbParse;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 分库分表数据源,暂时仅支持mysql,如要支持其它可扩展Parse解析器
 * 
 * @author  zhouliang
 * @version  [1.0, 2016年2月17日]
 * @since  [yy-sdal/1.0]
 */
public class YYDalDatasource implements DataSource {
    
    /**
        数据源配置属性信息，例如：
        testMap.put("driverClass", "com.mysql.jdbc.Driver");
        testMap.put("jdbcUrl", "jdbc:mysql://localhost:3306/useradmin_inst?useUnicode=true&characterEncoding=UTF8");
        testMap.put("user", "root");
        testMap.put("password", "root");
        testMap.put("minPoolSize", "4");
        testMap.put("acquireIncrement", "8");
        testMap.put("maxPoolSize", "10");
        testMap.put("maxIdleTime", "160");
     */
    private Map<String, String> dataSourceConfig;
    
    /**
     * 数据源类，如：com.mchange.v2.c3p0.ComboPooledDataSource，org.apache.commons.dbcp.BasicDataSource等
     */
    private String dataSourceClass;
    
    /**
     * 分库数据源，每个实例一个数据源
     */
    private DataSource[] datasource;
    
    /**
     * 默认数据库源
     */
    private DataSource defaultDataSource;
    
    /**
     * 分库节点管理器
     */
    private DbNodeManager dbnodeManager;
    
    /**
     * 数据库类型， 暂时支持mysql
     */
    private String dbType;
    
    /**
     * 数据源类型, 暂时支持c3p0, dbcp
     */
    private String dsType;
    
    private PrintWriter logWriter;
    
    /**
     * 构造数据源
     * 
     * @param defaultDataSource 默认数据源，存储非分表的数据
     * @param dataSourceConfig 数据源配置
     * @param dataSourceClass 数据源类
     * @param dsType 数据源类型， 暂时支持c3p0, dbcp
     * @param dbType 数据库类型，暂时支持mysql
     * @param dbnodeDef 分库节点定义
     * @param tableRuleDefs 分表定义
     */
    public YYDalDatasource(DataSource defaultDataSource, Map<String, String> dataSourceConfig, String dataSourceClass,
        String dsType, String dbType, String dbnodeDef, List<String> tableRuleDefs) {
        this.defaultDataSource = defaultDataSource;
        this.dataSourceConfig = dataSourceConfig;
        this.dataSourceClass = dataSourceClass;
        this.dbType = dbType;
        this.dsType = dsType;
        
        if (DBType.MYSQL.value().equals(this.dbType)) { //mysql解析，TODO 要改成builder模式
            dbnodeManager = new DefaultNodeManager(new MysqlDbParse(), dbnodeDef, tableRuleDefs);
        }
        else {
            throw new RuntimeException("不支持的数据库类型:" + this.dbType);
        }
        
        //根据数据库实例数产生数据源, TODO 要改成builder模式
        try {
            datasource = new DataSource[dbnodeManager.dbInstances().size()];
            int i = 0;
            for (DbInstance dbInstance : dbnodeManager.dbInstances()) {
                DataSource ds = (DataSource)Class.forName(dataSourceClass).newInstance();
                if (DSType.C3P0.value().equals(this.dsType)) {
                    this.dataSourceConfig.remove("jdbcUrl");
                    BeanUtils.setProperty(ds, "jdbcUrl", dbInstance.getDbinstanceDesc());
                }
                else if (DSType.DBCP.value().equals(this.dsType)) {
                    this.dataSourceConfig.remove("url");
                    BeanUtils.setProperty(ds, "url", dbInstance.getDbinstanceDesc());
                }
                else {
                    throw new RuntimeException("不支持的数据源:" + this.dsType);
                }
                BeanUtils.populate(ds, dataSourceConfig); //设置值先后顺序的原因或时间原因，c3p0可能会出错，忽略掉
                datasource[i++] = ds;
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public PrintWriter getLogWriter() {
        return logWriter;
    }
    
    /** {@inheritDoc} */
    @Override
    public void setLogWriter(PrintWriter out)
        throws SQLException {
        this.logWriter = out;
    }
    
    /** {@inheritDoc} */
    @Override
    public void setLoginTimeout(int seconds) {
        DriverManager.setLoginTimeout(seconds);
    }
    
    /** {@inheritDoc} */
    @Override
    public int getLoginTimeout() {
        return DriverManager.getLoginTimeout();
    }
    
    public Logger getParentLogger()
        throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T unwrap(Class<T> iface)
        throws SQLException {
        if (iface == null) {
            return null;
        }
        
        if (iface.isInstance(this)) {
            return (T)this;
        }
        
        return null;
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isWrapperFor(Class<?> iface)
        throws SQLException {
        return iface != null && iface.isInstance(this);
    }
    
    /** {@inheritDoc} */
    @Override
    public YYDalConnection getConnection()
        throws SQLException {
        return new YYDalConnection(this);
    }
    
    /** {@inheritDoc} */
    @Override
    public YYDalConnection getConnection(String username, String password)
        throws SQLException {
        return new YYDalConnection(this);
    }
    
    /**
     * 数据源类
     */
    public String getDataSourceClass() {
        return dataSourceClass;
    }
    
    /**
     * 节点及实例描述
     */
    public String getDbnodeDef() {
        return dbnodeManager.getDbnodeDef();
    }
    
    /**
     * 分表及规则描述
     */
    public List<String> getTableRuleDefs() {
        return dbnodeManager.getTableRuleDefs();
    }
    
    /**
     * 分库分表节点管理器
     */
    public DbNodeManager getDbnodeManager() {
        return dbnodeManager;
    }
    
    /**
    * 默认数据源, 非分库分表的数据存储在此数据源中
    */
    public DataSource getDefaultDataSource() {
        return defaultDataSource;
    }
    
    /**
    * 所有分库分表数据源
    */
    public DataSource[] getDatasource() {
        return datasource;
    }
    
    /**
     * 数据源配置
     */
    public Map<String, String> getDataSourceConfig() {
        return dataSourceConfig;
    }
    
    /**
     * 数据库类型
     */
    public String getDbType() {
        return dbType;
    }
    
    /**
     * 数据源类型
     */
    public String getDsType() {
        return dsType;
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "YYDalDatasource [dataSourceConfig=" + dataSourceConfig + ", dataSourceClass=" + dataSourceClass
            + ", dbnodeManager=" + dbnodeManager + ", dbType=" + dbType + ", dsType=" + dsType + "]";
    }
    
    public static void main(String[] args)
        throws Exception {
        Map<String, String> testMap = new HashMap<String, String>();
        testMap.put("driverClass", "com.mysql.jdbc.Driver");
        testMap.put("jdbcUrl", "jdbc:mysql://localhost:3306/useradmin_inst?useUnicode=true&characterEncoding=UTF8");
        testMap.put("user", "root");
        testMap.put("password", "root");
        testMap.put("minPoolSize", "4");
        testMap.put("acquireIncrement", "8");
        testMap.put("maxPoolSize", "10");
        testMap.put("maxIdleTime", "160");
        
        //ComboPooledDataSource ds = new ComboPooledDataSource();
        //BeanUtils.populate(ds, testMap);
        //ds.getConnection();
        //ds.close();
        
        List<String> tableDescs = new ArrayList<String>();
        tableDescs.add("user_[8]:hash(user_id)");
        tableDescs.add("qrcode_[8]:customFunc(qrcode_str)");
        tableDescs.add("TB_WLJ_QRCODE_[1]:hash(QRCODE)");
        
        com.mchange.v2.c3p0.ComboPooledDataSource defaultDs = new ComboPooledDataSource();
        BeanUtils.populate(defaultDs, testMap);
        
        DataSource ds =
            new YYDalDatasource(defaultDs, testMap, "com.mchange.v2.c3p0.ComboPooledDataSource", DSType.C3P0.value(),
                DBType.MYSQL.value(), "jdbc:mysql://127.0.0.1:3306/useradmin_inst_[1-4]", tableDescs);
        
        ds.getConnection();
        
        System.out.println(ds);
    }
}
