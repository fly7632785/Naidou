package com.itspeed.naidou.app.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by jafir on 15/6/28.
 *
 * 数据清理管理器
 */
public class DataCleanManager {

    /**
     * 获取内存中总的缓存
     *
     * @param context 上下文
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        //如果有外部内存 则加入外部内存
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
            //SD卡下面 Naidou 文件夹里面缓存
            cacheSize += getFolderSize(new File(Environment.getExternalStorageDirectory().toString() + "/Naidou"));
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 清理所有的缓存
     *
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
            deleteDir(new File(Environment.getExternalStorageDirectory().toString() + "/Naidou"));
        }
    }

    /**
     * 删除文件   递归算法
     * 因为没有办法直接删除 文件夹
     * 只有通过递归删除下面的所有文件
     * 才能达到删除文件下的效果
     *
     * @param dir
     * @return
     */
    private static boolean deleteDir(File dir) {
        //如果路径不为空且文件路径正确
        //说明路径 是目录 不能直接删除目录 必须删除下面的所有文件
        if (dir != null && dir.isDirectory()) {
            //获取路径下 下一层的所有路径或者目录
            String[] children = dir.list();
            //循环  删除
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                //递归终止条件
                if (!success) {
                    return false;
                }
            }
        }
        //否则说明是路径是文件  直接删除文件
        return dir.delete();
    }



    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据

    /**
     * 获取文件大小   递归算法
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            //获取路径下所有的文件或者目录
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                //如果是一个路径或者目录 则继续递归
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                    //否则是一个文件  获取文件大小
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            //此方法用于格式化 小数位数
            //表示 如果  2.357 = 2.36   四舍五入
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }


}
