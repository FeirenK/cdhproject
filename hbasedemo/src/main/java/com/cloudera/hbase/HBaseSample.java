package com.cloudera.hbase;

import com.cloudera.hbase.utils.ClientUtils;
import com.cloudera.hbase.utils.HBaseUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;

/**
 * package: com.cloudera.hbase
 * describe: 访问非Kerberos环境下的HBase
 * creat_user: Feiren
 * email: feirenkuang@gmail.com
 * creat_date: 2018/11/17
 * creat_time: 下午4:55

 */
public class HBaseSample {

    public static void main(String[] args) {
        try {
            Configuration configuration = ClientUtils.initHBaseENV();
            Connection connection = HBaseUtils.initConn(configuration);
            if(connection == null) {
                System.exit(1);
            }
            //获取HBase库中所有的表
            HBaseUtils.listTables(connection);

            HBaseUtils.readTable("picHbase", connection);

            //释放连接
            connection.close();

        } catch (Exception e) {

        }
    }
}
