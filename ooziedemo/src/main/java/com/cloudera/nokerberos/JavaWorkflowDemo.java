package com.cloudera.nokerberos;

import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.WorkflowAction;
import org.apache.oozie.client.WorkflowJob;

import java.util.List;
import java.util.Properties;

/**
 * package: com.cloudera.nokerberos
 * describe: 使用Oozie-client的API接口向非Kerberos集群提交Java Program作业
 * creat_user: Feiren
 * email: feirenkuang@gmail.com
 * creat_date: 2018/2/13
 * creat_time: 下午9:04

 */
public class JavaWorkflowDemo {

    private static String oozieURL = "http://ip-172-31-6-148.Feiren.com:11000/oozie";

    public static void main(String[] args) {

        System.setProperty("user.name", "Feirentest");
        OozieClient oozieClient = new OozieClient(oozieURL);
        try {
            System.out.println(oozieClient.getServerBuildVersion());

            Properties properties = oozieClient.createConfiguration();
            properties.put("oozie.wf.application.path", "${nameNode}/user/Feirentest/oozie/javaaction");
            properties.put("oozie.use.system.libpath", "True");
            properties.put("nameNode", "hdfs://ip-172-31-10-118.Feiren.com:8020");
            properties.put("jobTracker", "ip-172-31-6-148.Feiren.com:8032");
            properties.put("mainClass", "org.apache.hadoop.examples.QuasiMonteCarlo");
            properties.put("arg1", "10");
            properties.put("arg2", "10");
            properties.put("javaOpts", "-Xmx1000m");
            properties.put("oozie.libpath", "${nameNode}/Feirentest/jars/");

            //运行workflow
            String jobid = oozieClient.run(properties);
            System.out.println(jobid);

            //等待10s
            new Thread(){
                public void run() {
                    try {
                        Thread.sleep(10000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            //根据workflow id获取作业运行情况
            WorkflowJob workflowJob = oozieClient.getJobInfo(jobid);
            //获取作业日志
            System.out.println(oozieClient.getJobLog(jobid));

            //获取workflow中所有ACTION
            List<WorkflowAction> list = workflowJob.getActions();
            for (WorkflowAction action : list) {
                //输出每个Action的 Appid 即Yarn的Application ID
                System.out.println(action.getExternalId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
