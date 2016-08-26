package com.sqq.sqq_total.utils;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by sqq on 2016/7/15.
 * 复制assets下的文件到sd卡下
 */
public class AssetsFileUtil {

    public static void copyFromAssets(Context con){
        String dir = con.getExternalCacheDir()+"/stafflocker";

        String assetDir = "stafflocker";

        CopyAssets(con,assetDir,dir);
    }

    private static void CopyAssets(Context con,String assetDir, String dir) {
        String[] files;
        try {
            // 获得Assets一共有几多文件
            files = con.getResources().getAssets().list(assetDir);
        } catch (IOException e1) {
            return;
        }
        File mWorkingPath = new File(dir);
        // 如果文件路径不存在
        if (!mWorkingPath.exists()) {
            // 创建文件夹
            if (!mWorkingPath.mkdirs()) {
                // 文件夹创建不成功时调用
                return;
            }
        }

        for (int i = 0; i < files.length; i++) {
            try {
                // 获得每个文件的名字
                String fileName = files[i];
                // 根据路径判断是文件夹还是文件
                if (!fileName.contains(".")) {
                    if (0 == assetDir.length()) {
                        CopyAssets(con,fileName, dir + fileName + "/");
                    } else {
                        CopyAssets(con,assetDir + "/" + fileName, dir + "/"
                                + fileName + "/");
                    }
                    continue;
                }
                File outFile = new File(mWorkingPath, fileName);
                if (outFile.exists()){
                    //如果存在就不做操作outFile.delete();
                    return;
                }
                InputStream in = null;
                if (0 != assetDir.length())
                    in = con.getAssets().open(assetDir + "/" + fileName);
                else
                    in = con.getAssets().open(fileName);
                OutputStream out = new FileOutputStream(outFile);

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                in.close();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
