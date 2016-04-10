package com.itspeed.naidou.app.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.model.bean.UpdateInfo;
import com.panwrona.downloadprogressbar.library.DownloadProgressBar;

import org.kymjs.kjframe.utils.SystemTool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jafir on 15/6/27.
 *
 * 更新管理器
 *
 * 首先是去一个地址 下载一个xml配置文件
 * xml 文件里面有  安装包的 版本  版本名  APK地址
 * 获取xml 之后比对 如果有新的 版本则去下载
 * 然后安装
 *
 */
public class UpdateManager {
    private final static  String PATH = AppContext.HOST+"update_info.xml";
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 下载xml*/
    private static final int DOWNLOAD_XML = 3;
    /* 下载XML结束*/
    private static final int FINISH_DOWNLOAD_XML = 4;

    private static final int FAIL_TO_LOAD_XML = 5;
    /* 保存解析的XML信息 */
//    HashMap<String, String> mHashMap;
    private UpdateInfo info;
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    private Context mContext;
    /* 更新进度条 */
    private DownloadProgressBar mProgress;
    private Dialog mDownloadDialog;

    //提示对话框
//    private Dialog mShowDialog;
    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {

                case DOWNLOAD_XML:

                    break;

                case FAIL_TO_LOAD_XML:
                    Toast.makeText(mContext,"获取数据失败",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
                case FINISH_DOWNLOAD_XML:
                    checkUpdate();
                    dialog.dismiss();
                    break;
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    mProgress.setSuccessResultState();
                    mDownloadDialog.dismiss();
                    installApk();
                    break;
                default:
                    break;
            }
        }
    };
    private ProgressDialog dialog;

    public UpdateManager(Context context)
    {
        this.mContext = context;
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate()
    {
        if (isUpdate())
        {
            // 显示提示对话框
            showNoticeDialog();
        } else
        {
            Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 检查软件是否有更新版本
     *
     * @return
     */
    private boolean isUpdate()
    {

        // 获取当前软件版本
        int versionCode = getAppVersionCode(mContext);

//        info = new UpdateInfo();
//        info.setUrl("http://kibey-static.b0.upaiyun.com/download/echo_80.apk");
//        info.setVersion("2");
//        info.setVersionName("version 2.0");

        if (null != info)
        {
            int serviceCode = Integer.valueOf(info.getVersion());
            // 版本判断
            if (serviceCode > versionCode)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前应用程序的版本号
     */
    public static int getAppVersionCode(Context context) {
        int version = 0;
        try {
            version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(SystemTool.class.getName()
                    + "the application not found");
        }
        return version;
    }

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog()
    {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.soft_update_title);
        builder.setMessage(R.string.soft_update_info);
        // 更新
        builder.setPositiveButton(R.string.soft_update_updatebtn, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                // 显示下载对话框
                showDownloadDialog();
            }
        });
        // 稍后更新
        builder.setNegativeButton(R.string.soft_update_later, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog()
    {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.soft_updating);
        builder.setCancelable(false);
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (DownloadProgressBar) v.findViewById(R.id.download_view);
        mProgress.playManualProgressAnimation();
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton(R.string.soft_update_cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
                mProgress.abortDownload();

            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 现在文件
        downloadApk();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk()
    {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    /**
     * 开始从服务器获得xml
     */
    public void start(){

        dialog = new ProgressDialog(mContext);
        dialog.setMessage("正在检查更新...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        
//        mShowDialog = new AlertDialog.Builder(mContext).setTitle("正在检查更新...").create();
//        mShowDialog.show();
        new DownloadXMLThread().start();
    }

    private  class DownloadXMLThread extends Thread{
        @Override
        public void run() {

            // 把version.xml放到网络上，然后获取文件信息
            //InputStream inStream = ParseXmlService.class.getClassLoader().getResourceAsStream("version.xml");
            // 解析XML文件。 由于XML文件比较小，因此使用DOM方式进行解析
            try {
                String path = PATH;
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(5 * 1000);
                conn.setRequestMethod("POST");
                InputStream inStream = conn.getInputStream();
                info = Parser.getUpdataInfo(inStream);
                mHandler.sendEmptyMessage(FINISH_DOWNLOAD_XML);
            } catch (Exception e) {
                e.printStackTrace();
                mHandler.sendEmptyMessage(FAIL_TO_LOAD_XML);

            }
        }

    }


    /**
     * 下载文件线程
     */
    private class downloadApkThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    URL url = new URL(info.getUrl());
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    conn.setConnectTimeout(5000);
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists())
                    {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, info.getVersionName()+".apk");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do
                    {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0)
                        {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
                mProgress.setErrorResultState();
            } catch (IOException e)
            {
                e.printStackTrace();
                mProgress.setErrorResultState();
            }
        }
    }

    /**
     * 安装APK文件
     */
    private void installApk()
    {
        File apkfile = new File(mSavePath, info.getVersionName());
        if (!apkfile.exists())
        {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }
}
