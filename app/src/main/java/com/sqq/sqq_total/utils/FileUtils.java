package com.sqq.sqq_total.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sqq on 2016/8/16.
 */
public class FileUtils {

    /**
     * 二进制写入文件
     * @param file
     * @param bytes
     */
    public void writeByteArrayToFile(File file ,byte[] bytes){
        FileOutputStream fos=null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bytes);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos!=null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
