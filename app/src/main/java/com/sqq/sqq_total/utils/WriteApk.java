package com.sqq.sqq_total.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.ZipFile;

/**
 * 摘自苹果核（天猫无线部的博客，www.pingguohe.net）
 * 一种动态为apk写入信息的方案
 * Created by sqq on 2016/6/24.
 */
public class WriteApk {

    /**
     * 正规写法
     * 写的操作不是在这里写的，是在给用户发送这个apk之前就写的所以是用的java代码
     * 把写了信息之后的apk再发送到用户手中
     * @param file
     * @param comment
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void writeApk(File file,String comment){
        ZipFile zipFile = null;
        ByteArrayOutputStream outputStream = null;
        RandomAccessFile accessFile  = null;

        try {
            zipFile = new ZipFile(file);
            String zipComment = zipFile.getComment();
            if(zipComment!=null){
                //不允许多次写入
                return;
            }

            byte[] byteComment = comment.getBytes();
            outputStream = new ByteArrayOutputStream();

            outputStream.write(byteComment);
            outputStream.write(short2Stream((short) byteComment.length));
            byte[] data = outputStream.toByteArray();

            accessFile = new RandomAccessFile(file, "rw");
            accessFile.seek(file.length() - 2);
            accessFile.write(short2Stream((short) data.length));
            accessFile.write(data);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("sqqq","写没有找到文件");
        }finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (accessFile != null) {
                    accessFile.close();
                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * 读取comment信息
     * @param file
     * @return
     */
    public static String readApk(File file) {
        byte[] bytes = null;
        try {
            RandomAccessFile accessFile = new RandomAccessFile(file, "r");
            long index = accessFile.length();

            bytes = new byte[2];
            index = index - bytes.length;
            accessFile.seek(index);
            accessFile.readFully(bytes);

            int contentLength = stream2Short(bytes, 0);

            bytes = new byte[contentLength];
            index = index - bytes.length;
            accessFile.seek(index);
            accessFile.readFully(bytes);

            return new String(bytes, "utf-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("sqqq", "读没有找到文件");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("sqqq", "io错误");
        }
        return null;
    }

    /**
     * short转换成字节数组（小端序）
     * @param data
     * @return
     */
    private static byte[] short2Stream(short data) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort(data);
        buffer.flip();
        return buffer.array();
    }

    /**
     * 字节数组转换成short（小端序）
     * @param stream
     * @param offset
     * @return
     */
    private static short stream2Short(byte[] stream, int offset) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(stream[offset]);
        buffer.put(stream[offset + 1]);
        return buffer.getShort(0);
    }

    public static String getPackagePath(Context context){
        if(context!=null){
            /*String path= Environment.getExternalStorageDirectory().getPath()+"/app-release.apk";
            Log.d("sqqq","path"+path);*/
            Log.d("sqqq","path"+context.getPackageCodePath());
            return  context.getPackageCodePath();
        }
        return null;
    }
}
