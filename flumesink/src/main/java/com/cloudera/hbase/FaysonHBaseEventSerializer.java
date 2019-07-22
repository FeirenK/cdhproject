package com.cloudera.hbase;

import org.apache.flume.Event;
import org.apache.flume.sink.hbase.HbaseEventSerializer;

/**
 * package: com.cloudera.hbase
 * describe: 继承HBaseSink的HbaseEventSerializer接口类，增加initialize(Event var1, byte[] var2, String var3)
 * 用于处理指定rowkey
 * creat_user: Feiren
 * email: feirenkuang@gmail.com
 * creat_date: 2018/6/3
 * creat_time: 下午11:54

 */
public interface FeirenHBaseEventSerializer extends HbaseEventSerializer {
    void initialize(Event var1, byte[] var2, String var3);
}