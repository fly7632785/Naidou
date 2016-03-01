package com.itspeed.naidou.app.activity;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by jafir on 16/3/1.
 */
public abstract class BaseActivity extends KJActivity {


    public void writeToLocal(String t,String folder,String fileName) {

        //只储存  最新一页的缓存数据
        File file = FileUtils.getSaveFile(folder, fileName);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(t.trim());
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String getFromLocal(String folder,String fileName){
        File file = FileUtils.getSaveFile( folder, fileName);
        char[] buffer = new char[1024];
        StringBuilder builder = new StringBuilder();
        String  data ;
        BufferedReader br = null;
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            br = new BufferedReader(fileReader);
            try {
                while ((data = br.readLine()) != null) {
                    builder.append(data);
                }
                return builder.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



}
