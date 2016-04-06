/*
* 文 件 名:  AbsYYDalStatement.java
* 版    权:  YY Technologies Co., Ltd. Copyright 2012-2013,  All rights reserved
* 描    述:  <描述>
* 修 改 人:  zhouliang
* 修改时间:  2016年4月6日
* 修改内容:  <修改内容>
*/
package org.yy.dal.ds;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.yy.dal.ds.holder.ConnectionHolder;
import org.yy.dal.ds.support.YYDalStatementNotSupport;
import org.yy.dal.route.Partition;

/**
* <一句话功能简述>
* <功能详细描述>
* 
* @author  zhouliang
* @version  [版本号, 2016年4月6日]
* @since  [产品/模块版本]
*/
public abstract class AbsYYDalStatement extends YYDalStatementNotSupport {
    
    //默认连接key
    private static String DEFAULT_CONNECT = "DEFAULT";
    
    /**
     * 根据分区获取连接信息 
     * 处理三种情况，返回null时使用默认实例，返回[-1,-1]时使用分库所有实例，返回其它值时使用其中一个实例
     * 
     * @param partitions 
     * @param datasource
     * @return
     */
    public List<Connection> fetchConnection(Partition partition, YYDalConnection connection)
        throws SQLException {
        
        List<Connection> conns = new ArrayList<Connection>();
        YYDalDatasource ds = connection.getDatasource();
        
        //处理三种情况，返回null时使用默认实例，，
        ConnectionHolder temp = null;
        if (partition == null) {
            temp = connection.get(DEFAULT_CONNECT);
            if (temp == null) {
                temp = new ConnectionHolder(ds.getDefaultDataSource().getConnection(), connection.getStatus());
                connection.put(DEFAULT_CONNECT, temp);
            }
            conns.add(temp);
        }
        else if (partition.getInstNumber() == -1) {//返回[-1,-1]时使用分库所有实例
            for (int i = 0; i < ds.getDatasource().length; ++i) {
                temp = connection.get(i + "");
                if (temp == null) {
                    temp = new ConnectionHolder(ds.getDatasource()[i].getConnection(), connection.getStatus());
                    connection.put(i + "", temp);
                }
                conns.add(temp);
            }
        }
        else {//返回其它值时使用其中一个实例
            temp = connection.get(partition.getInstNumber() + "");
            if (temp == null) {
                temp =
                    new ConnectionHolder(ds.getDatasource()[partition.getInstNumber()].getConnection(),
                        connection.getStatus());
                connection.put(partition.getInstNumber() + "", temp);
            }
            conns.add(temp);
        }
        return conns;
    }
}
