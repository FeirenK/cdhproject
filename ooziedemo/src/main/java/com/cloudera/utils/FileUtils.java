package com.cloudera.utils;

import java.io.*;

/**
 * package: com.cloudera.utils
 * describe: TODO
 * creat_user: Feiren
 * email: feirenkuang@gmail.com
 * creat_date: 2018/2/12
 * creat_time: 下午11:12

 */
public class FileUtils {

    public static String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }
}
