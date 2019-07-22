package com.cloudera.hdfs.kerberos;

import com.cloudera.hdfs.utils.HDFSUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.File;
import java.io.IOException;

/**
 * package: com.cloudera.hdfs.kerberos
 * describe: 访问Kerberos环境下的HDFS
 * creat_user: Feiren
 * email: feirenkuang@gmail.com
 * creat_date: 2017/12/2
 * creat_time: 下午11:54

 */
public class KBHDFSTest {

    private static String confPath = System.getProperty("user.dir") + File.separator + "hdfsdemo" + File.separator + "kb-conf";

    public static void main(String[] args) {
        //初始化HDFS Configuration 配置
        Configuration configuration = HDFSUtils.initConfiguration(confPath);

        //初始化Kerberos环境
        initKerberosENV(configuration);
        try {
            FileSystem fileSystem = FileSystem.get(configuration);

            //创建目录
            HDFSUtils.mkdir(fileSystem, "/user/kuang_feiren/kbtest");

            //上传本地文件至HDFS目录
            HDFSUtils.uploadFile(fileSystem, "C:\\Users\\Administrator\\Downloads\\README.txt", "/user/kuang_feiren/kbtest/item.csv");

            //文件重命名
            HDFSUtils.rename(fileSystem, "/user/kuang_feiren/kbtest/item.csv", "/user/kuang_feiren/kbtest/Feiren.csv");

            //查看文件
//            HDFSUtils.readFile(fileSystem, "/user/kuang_feiren/kbtest/Feiren.csv");

            //删除文件
//            HDFSUtils.delete(fileSystem, "/user/kuang_feiren/kbtest/Feiren.csv");

            fileSystem.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化Kerberos环境
     */
    public static void initKerberosENV(Configuration conf) {
        System.setProperty("java.security.krb5.conf", "krb5.conf");
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
//        System.setProperty("sun.security.krb5.debug", "true");
        try {
            UserGroupInformation.setConfiguration(conf);
            UserGroupInformation.loginUserFromKeytab("kuang_feiren@HOPERUN.COM", "C:\\Users\\Administrator\\IdeaProjects\\cdhproject\\hdfsdemo\\kb-conf\\kuang_feiren.keytab");
            System.out.println(UserGroupInformation.getCurrentUser());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
