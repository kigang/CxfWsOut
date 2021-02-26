package cxf.util;

import org.springframework.util.DigestUtils;

import java.io.*;

public class Md5Util {


    private Md5Util() {
        throw new IllegalStateException();
    }

    public static String md5DigestAsHex(File file) {

        Throwable var2 = null;

        String var3 = null;
        try {
            byte[] bytes = null;
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
            if (length > Integer.MAX_VALUE) {
                System.out.println("this file is max ");
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            //如果得到的字节长度和file实际的长度不一致就可能出错了
            if (offset < bytes.length) {
                System.out.println("file length is error");
                return null;
            }
            is.close();


            var3 = DigestUtils.md5DigestAsHex(bytes);
        } catch (Throwable var13) {
            var2 = var13;
        } finally {
        }
        return var3;
    }
}
