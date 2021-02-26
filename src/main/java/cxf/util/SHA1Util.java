package cxf.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class SHA1Util {
    public static String getFileSha1(FileInputStream in) {
        //FileInputStream in = null;
        try {
            //in = new FileInputStream(file); TRANSFER_TYPE
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            byte[] buffer = new byte[1022];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                digest.update(buffer, 0, len);
            }
            String sha1 = new BigInteger(1, digest.digest()).toString(16);
            int length = 40 - sha1.length();
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    sha1 = "0" + sha1;
                }
            }
            return sha1;
        } catch (IOException e) {
            System.out.println(e);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        } finally {
            /*try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }*/
        }
        return null;
    }

    public static Map<String, Object> json2Properties(String json){

        Map<String, String> map = JSON.parseObject(json, new TypeReference<HashMap<String, String>>(){});
        System.out.println(map.toString());
        return null;
    }


}

