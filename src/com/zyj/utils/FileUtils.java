package com.zyj.utils;

import java.io.*;

/**
 * @author zyj
 */
public class FileUtils {


    public static final String CONF_FILE_NAME = "fishing.conf";
    /**
     * 读配置文件
     * @param
     * @return
     */
    public static String readFileContent() {
        File file = new File(CONF_FILE_NAME);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
//            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    /**
     * 写配置文件
     * @param
     * @return
     */
    public static void writeFileContent(String content) {
        File file = new File(CONF_FILE_NAME);
        isExists(file);
        try {
            FileOutputStream fos = new FileOutputStream(file,false);
            fos.write(content.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 判断文件是否存在
     * @param file
     * @return
     */
    private static void isExists(File file){
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
