package com.zhixinhuixue.querykylin.datasource;

import com.zhixinhuixue.querykylin.entity.KylinProperties;
import org.slf4j.LoggerFactory;


import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 创建数据源
 *
 * @author hsh
 * @create 2020年12月17日
 */

public class KylinDataSource implements DataSource {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(KylinDataSource.class);

    private LinkedList<Connection> connectionPoolList = new LinkedList<>();

    private long maxWaitTime;


    /**
     * kylin datasource
     *
     * @param kylinProperties
     */
    public KylinDataSource(KylinProperties kylinProperties) {
        try {
            this.maxWaitTime = kylinProperties.getMaxWaitTime();
            Driver driverManager = (Driver) Class.forName(kylinProperties.getDriverClassName()).newInstance();
            Properties info = new Properties();
            info.put("user", kylinProperties.getUsername());
            info.put("password", kylinProperties.getPassword());
            for (int i = 0; i < kylinProperties.getPoolSize(); i++) {
                Connection connection = driverManager.connect(kylinProperties.getJdbcUrl(), info);

                // 将代理对象放入到连接池中
                connectionPoolList.add(ConnectionProxy.getProxy(connection, connectionPoolList));
            }
            log.info("PrestoDataSource has initialized {} size connection pool", connectionPoolList.size());
        } catch (Exception e) {
            log.error("kylinDataSource initialize error, ex: ", e);
        }
    }



    @Override
    public Connection getConnection() throws SQLException {
        synchronized (connectionPoolList) {
            if (connectionPoolList.size() <= 0) {
                try {
                    // 线程有开启,就绪，运行，阻塞，销毁过程，wait() 方法使该线程让出cpu,进入到阻塞阻塞状态,停止该线程
                    connectionPoolList.wait(maxWaitTime);
                } catch (InterruptedException e) {
                    throw new SQLException("getConnection timeout..." + e.getMessage());
                }
            }

            return connectionPoolList.removeFirst();
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }


    /**
     * 动态代理对象执行方法时，会调用该类中invoke方法
     *
     */
    static class ConnectionProxy implements InvocationHandler {

        private Object obj;
        private LinkedList<Connection> pool;
        private String DEFAULT_CLOSE_METHOD = "close";


        /**
         * 构造方法
         *
         * @param obj  connection
         * @param pool   连接池
         */
        private ConnectionProxy(Object obj, LinkedList<Connection> pool) {
            this.obj = obj;
            this.pool = pool;
        }

        /**
         * 返回connection 的代理对象
         *
         * @param o
         * @param pool
         * @return
         */
        public static Connection getProxy(Object o, LinkedList<Connection> pool) {

            /**
             * 类加载器:  connection 的类加载器
             * 实现的接口:  connection 接口
             * InvocationHandler 的子类 :  实现invoke方法
             *
             * 返回结果 代理类
             */
            Object proxed = Proxy.newProxyInstance(o.getClass().getClassLoader(), new Class[]{Connection.class}, new ConnectionProxy(o, pool));
            return (Connection) proxed;
        }


        /**
         * 代理对象执行某个方法时，会调用该方法
         *
         * @param proxy  代理对象
         * @param method  方法
         * @param args 方法参数
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals(DEFAULT_CLOSE_METHOD)) {
                synchronized (pool) {
                    pool.add((Connection) proxy);

                    // notify 方法唤醒一个阻塞状态的线程(另一个线程)，与wait(),sleep() 等方法一起用
                    pool.notify();
                }
                return null;
            } else {
                return method.invoke(obj, args);
            }
        }
    }
}
