package com.cloudera.nokerberos;

import net.sf.json.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * package: com.cloudera.nokerberos
 * describe: 通过读取本地交易明细text文件将文件内容解析并组装为JSON发送到Kafka
 * creat_user: Feiren
 * email: feirenkuang@gmail.com
 * creat_date: 2018/4/27
 * creat_time: 下午4:42

 */
public class ReadFileToKafka {
    public static String confPath = System.getProperty("user.dir") + File.separator + "conf";

    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.print("缺少输入参数，请指定要处理的text文件");
            System.exit(1);
        }
        String filePath = args[0];
        BufferedReader reader = null;

        try {
            Properties appProperties = new Properties();
            appProperties.load(new FileInputStream(new File(confPath + File.separator + "app.properties")));
            String brokerlist = String.valueOf(appProperties.get("bootstrap.servers"));
            String topic_name = String.valueOf(appProperties.get("topic.name"));

            Properties props = getKafkaProps();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerlist);

            Producer<String, String> producer = new KafkaProducer<String, String>(props);

            reader = new BufferedReader(new FileReader(filePath));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                String detailJson = parse_deal_daily_JSON(tempString);
                ProducerRecord record = new ProducerRecord<String, String>(topic_name, detailJson);
                producer.send(record);
                line++;
            }
            reader.close();
            producer.flush();
            producer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }

        }
    }

    /**
     * 将txt文件中的每行数据解析并组装为json字符串
     * @param tempString
     * @return
     */
    private static String parse_person_info_JSON(String tempString) {
        if(tempString != null && tempString.length() > 0) {
            Map<String, String> resultMap = null;
            String[] detail = tempString.split(",");
            resultMap = new HashMap<>();
            resultMap.put("id", detail[0]);
            resultMap.put("name", detail[1]);
            resultMap.put("sex", detail[2]);
            resultMap.put("city", detail[3]);
            resultMap.put("occupation", detail[4]);
            resultMap.put("mobile_phone_num", detail[5]);
            resultMap.put("fix_phone_num", detail[6]);
            resultMap.put("bank_name", detail[7]);
            resultMap.put("address", detail[8]);
            resultMap.put("marriage", detail[9]);
            resultMap.put("child_num", detail[10]);
            return JSONObject.fromObject(resultMap).toString();
        }
        return null;
    }


    /**
     * 将txt文件中的每行数据解析并组装为json字符串
     * @param tempString
     * @return
     */
    private static String parse_deal_daily_JSON(String tempString) {
        if(tempString != null && tempString.length() > 0) {
            Map<String, String> resultMap = null;
            String[] detail = tempString.split(",");
            resultMap = new HashMap<>();
            resultMap.put("event_ts", detail[0]);
            resultMap.put("id", detail[1]);
            resultMap.put("card_id", detail[2]);
            resultMap.put("money_type", detail[3]);
            resultMap.put("money", detail[4]);
            resultMap.put("balance", detail[5]);
            resultMap.put("punching", detail[6]);
            resultMap.put("memo", detail[7]);
            return JSONObject.fromObject(resultMap).toString();
        }
        return null;
    }

    /**
     * 初始化Kafka配置
     * @return
     */
    private static Properties getKafkaProps() {
        try{
            Properties props = new Properties();
            props.put(ProducerConfig.ACKS_CONFIG, "all");
            props.put(ProducerConfig.BATCH_SIZE_CONFIG, 1000); //批量发送消息
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

            return props;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
