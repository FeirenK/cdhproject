package com.cloudera.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.File;
import java.io.IOException;

/**
 * package: com.cloudera.hbase
 * describe: TODO
 * creat_user: Feiren
 * email: feirenkuang@gmail.com
 * creat_date: 2017/11/30
 * creat_time: 上午12:49

 */
public class KBHBaseTest {

    private static String confPath = System.getProperty("user.dir") + File.separator + "hbasedemo" + File.separator + "config";

    public static void main(String[] args) {

        System.setProperty("java.security.krb5.conf", "/Users/xxxx/Desktop/hbase-test/conf/krb5.conf");

        Configuration configuration = getConfiguration();
        System.out.println(configuration.get("hbase.rootdir"));
        configuration.set("hadoop.security.authentication", "Kerberos");

        UserGroupInformation.setConfiguration(configuration);
        try {
            UserGroupInformation.loginUserFromKeytab("Feiren@Feiren.COM", "/Users/xxxx/Desktop/hbase-test/conf/Feiren.keytab");

            Connection connection = ConnectionFactory.createConnection(configuration);
            Table table = connection.getTable(TableName.valueOf("picHbase"));
            System.out.println(table.getName());
            Scan scan = new Scan();
            ResultScanner rs = table.getScanner(scan);
            for (Result r : rs) {
                System.out.println(r.toString());
            }

            //释放连接
            table.close();
            connection.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载Hbase环境变量
     * @return
     */
    public static Configuration getConfiguration() {

        Configuration configuration = HBaseConfiguration.create();
        configuration.addResource(new Path(confPath + File.separator + "core-site.xml"));
        configuration.addResource(new Path(confPath + File.separator + "hdfs-site.xml"));
        configuration.addResource(new Path(confPath + File.separator + "hbase-site.xml"));

        return  configuration;
    }
}
