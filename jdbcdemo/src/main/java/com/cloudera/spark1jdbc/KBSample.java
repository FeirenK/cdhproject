package com.cloudera.spark1jdbc;

import com.cloudera.utils.JDBCUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * package: com.cloudera.sparkjdbc
 * describe: 使用JDBC的方式访问Kerberos环境下Spark1.6 Thrift Server
 * creat_user: Feiren
 * email: feirenkuang@gmail.com
 * creat_date: 2018/6/1
 * creat_time: 上午10:21

 */
public class KBSample {

    private static String JDBC_DRIVER = "org.apache.hive.jdbc.HiveDriver";
    private static String CONNECTION_URL ="jdbc:hive2://cdh04.Feiren.com:10001/;principal=hive/cdh04.Feiren.com@Feiren.COM";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("通过JDBC连接Kerberos环境下的Spark1.6 Thrift Server");
        //登录Kerberos账号
        System.setProperty("java.security.krb5.conf", "/Users/Feiren/Documents/develop/kerberos/krb5.conf");
        Configuration configuration = new Configuration();
        configuration.set("hadoop.security.authentication" , "Kerberos" );
        UserGroupInformation. setConfiguration(configuration);
        UserGroupInformation.loginUserFromKeytab("Feiren@Feiren.COM", "/Users/Feiren/Documents/develop/kerberos/Feiren.keytab");
        System.out.println(UserGroupInformation.getLoginUser());

        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_URL);
            ps = connection.prepareStatement("select * from test");
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "----" + rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.disconnect(connection, rs, ps);
        }
    }
}
