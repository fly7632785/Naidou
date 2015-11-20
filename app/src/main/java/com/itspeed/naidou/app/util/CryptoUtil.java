package com.itspeed.naidou.app.util;

import com.itspeed.naidou.app.AppContext;

/**
 * Created by jafir on 15/11/20.
 */
public class CryptoUtil {

    public static String encrypto(String src){
        try {
            return Crypto.encrypt(AppContext.CryptoKey,src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String decrypto(String crypted){
        try {
            return Crypto.decrypt(AppContext.CryptoKey,crypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }










}
