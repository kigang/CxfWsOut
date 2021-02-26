import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import cxf.dto.FtpClient;
import cxf.dto.Input;
import cxf.util.SHA1Util;
import cxf.util.SftpClient;
import cxf.util.Md5Util;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Pattern;


public class FTPTest {

    public static void main(String[] args) {
        /*try {
            FtpClient ftpClient = new FtpClient("192.168.246.128",21,"mftuser","bing950411");
            InputStream inputStream = new FileInputStream(new File("D:\\tmp\\test\\idoc\\application.yml"));
            ftpClient.uploadFile("/file/test","testFtp.xml",1,inputStream);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            SftpClient sftpClient = new SftpClient("192.168.246.128",22,"root","950411");
            InputStream inputStream = new FileInputStream(new File("D:\\tmp\\test\\idoc\\application.yml"));
            sftpClient.uploadFile("/file/test","test.xml",1,inputStream);

        } catch (Exception e) {

        }*/
        //String file = "/file/test/111.xml";
        //System.out.println(file.substring(0,file.lastIndexOf("/")));

        //SftpClient client = new SftpClient();

        Input input = new Input();
        input.setName("haha");
        Input input1 = new Input();
        input1.setName("haha");
        //client.initSftpClient();
       // ChannelSftp sftp = client.getSftp();
        FtpClient ftp = new FtpClient();
        ftp.initFtpClient();
        FTPClient client = ftp.getClient();
        //9a8e34d83d82b0d959bbc9da1aa422608df8d575
        //da39a3ee5e6b4b0d3255bfef95601890afd80709
        SftpClient sftpClient = new SftpClient();

        try {

            String s = "ddd.ss";
            System.out.println(s.substring(0,s.lastIndexOf(".")));
            System.out.println(s.substring(s.lastIndexOf(".")));

            sftpClient.initSftpClient();
            sftpClient.createDirectory("/file/sftp/mftb/success");

            //System.out.println(s.lastIndexOf("."));
            /*boolean f = client.rename("/file/test/test/testftp/2.txt", "/file/sftp/mftb/success/2.txt");
            System.out.println("---------------------:" + f);*/
            //sftp.get("/file/share/application3",new FileOutputStream(new File("D:/tmp/test/idoc/download/dir1/11.xml")));
            //InputStream is = clientFtp.retrieveFileStream("/file/sftp/mftb/success/1.txt");
            //sftp.setFilenameEncoding("GBK");
            /*FTPFile[] ftpFileArr = clientFtp.listFiles("/file/share");
            InputStream inputStream = sftp.get("/file/test/test/dir1/ftp1");
            System.out.println("---------");
            String code =  DigestUtils.md5DigestAsHex(IOUtils.toByteArray(inputStream));
            System.out.println(code);
            String code2 =  DigestUtils.md5DigestAsHex(IOUtils.toByteArray(inputStream));
            System.out.println(code2 + inputStream.toString());*/
            /*File file = new File("D:\\tmp\\test\\idoc\\test\\dir1\\application");
            InputStream inputStream1 = new FileInputStream(file);
            String code1 =  DigestUtils.md5DigestAsHex(IOUtils.toByteArray(inputStream1));
            System.out.println(code1);
            String code3 =  DigestUtils.md5DigestAsHex(IOUtils.toByteArray(inputStream1));
            System.out.println(code3);*/
            /*File file = new File("D:\\tmp\\test\\idoc\\download\\dir");
            if (!file.exists()) {
                file.mkdirs();
            }*/
            //FTPFile[] listFiles = clientFtp.listFiles("/file/sftp/mftb/success");

            //sftp.cd("/file/test");
            //System.out.println(sftp.stat("/file/test/fileName111.xml"));

            //InputStream inputStream = sftp.get("/file/test/test/downloaftpyml");
            //sftp.rmdir("/file/dede");
            //sftp.rm("/file/1.txt");
            /*try {
                sftp.cd(path);
            } catch (SftpException e) {
                //目录不存在，则创建文件夹
                String[] dirs = path.split("/");
                StringBuilder tempPath = new StringBuilder();
                for (String dir : dirs) {
                    tempPath.append("/").append(dir);
                    try {
                        sftp.cd(String.valueOf(tempPath));
                    } catch (SftpException ex) {
                        sftp.mkdir(String.valueOf(tempPath));
                        sftp.cd(String.valueOf(tempPath));
                    }
                }
            }*/
            /*ChannelSftp sftp = client.getSftp();
            SftpATTRS attrs = sftp.stat("/file/test");
            SftpATTRS attrs1 = sftp.stat("/file/test/ftp1.xml");
            System.out.println(attrs1.isDir());
            System.out.println(attrs.isDir());
            File file = new File("D:\\tmp\\test\\idoc\\application.yml");
            InputStream inputStream = new FileInputStream(file);
            String pathname = "/file/test";
            Integer cover = 1;*/
            //client.uploadFile(pathname, "fileName.xml", cover, inputStream);
            //byte[] dou = client.downloadFile("/file/test","fileName.xml");

            //System.out.println(Md5Util.md5DigestAsHex(file));
            //System.out.println(DigestUtils.md5DigestAsHex(dou));
            //System.out.println(vector.toArray());

            /*System.out.println(!Pattern.compile("").matcher("11.txt").matches());

            File file1 = new File("D:\\tmp\\test\\idoc");
            File[] files = file1.listFiles();*/
            /*for (File i : files) {
                System.out.println(i.getAbsolutePath());
            }*/
            /*String sss = "/a/v/s/";
            String ss[] = sss.substring(1).split("/");
            System.out.println(ss.length);
            for (String i : ss) {
                System.out.println("------------:" + i);
            }*/

        } catch (Exception e) {
            e.printStackTrace();

            /*try {
                sftp.mkdir("/file/test/test/dir");
            } catch (SftpException e1) {
                e.printStackTrace();
            }*/


        }

    }
}
