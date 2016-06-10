package com.sqq.sqq_total.utils;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/1.
 */
public class FileLoader {

    /**
     * 通过后缀获取文件集合
     * @param folderPath   文件夹路径
     * @param suffix      文件后缀，如"wav"、"mp3"、"txt"等;为空时返回所有类型的文件
     * @return   符合筛选的文件名（包含完整路径）集合
     */
    public static List<String> GetFileWithSuffix(String folderPath,String[] suffix){

        List ret = new LinkedList<String>();
        if(folderPath==null) {
            return ret;
        }

        try
        {
            File file = new File(folderPath);
            if(!file.exists()) {
                return ret;
            }
            File[] files = file.listFiles();
            if(files==null){
                return ret;
            }
            if(files.length>0) {
                for(int  i=0;i<files.length;i++) {
                    if(!files[i].isDirectory()){
                        String pp = files[i].getName();
                        if(isMatch(pp, suffix)){
                            ret.add(folderPath+File.separator+pp);
                        }
                    }else{
                        List temp = GetFileWithSuffix(files[i].getPath(),suffix);
                        if(temp!=null){
                            for(int j=0;j<temp.size();j++) {
                                ret.add(temp.get(j).toString());
                            }
                        }

                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return ret;
        }
        return ret;
    }

    public static boolean isMatch(String filePath ,String[] suffix){
        if(suffix==null){
            return true;
        }
        if(suffix.length==0){
            return true;
        }
        for(int i=0;i<suffix.length;i++){
            if(getSuffix(filePath).equals(suffix[i])){
                return true;
            }
        }
        return false;
    }

    public static String getSuffix(String s){

        return s.substring(s.lastIndexOf(".")+1,s.length());
    }

}
