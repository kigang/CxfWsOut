package cxf.dto;


import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


/**
 * <p>  <p>
 * @author qigang.qin@hand-china.com
 */
public class FtpClient {

    private static final Logger logger = LoggerFactory.getLogger(FtpClient.class);

    private  String hostname = "192.168.25.128";
    private  Integer port = 21;
    private  String username = "mftuser";
    private  String password = "bing950411";

    public FtpClient(){}

    public FtpClient(String hostname, Integer port, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public FTPClient client = null;

    public FTPClient getClient() {
        return client;
    }

    public void setClient(FTPClient client) {
        this.client = client;
    }

    /**
     * 初始化ftp服务器
     */
    public void initFtpClient() {
        client = new FTPClient();
        client.setControlEncoding("UTF-8");
        try {
            // 连接ftp服务器
            client.connect(hostname, port);
            // 登录ftp服务器
            client.login(username, password);
            // 是否成功登录服务器
            int replyCode = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                throw new RuntimeException("连接异常1");
            }
        } catch (Exception e) {
            logger.error("异常：" + e);
        }
    }

    /**
     * 关闭客户端连接(工具中所有public方法都要调用)
     */
    private void closeFtpClient() {
        if (client.isConnected()) {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException e) {
                logger.warn("ftpClient close failed!");
            }
        }
    }

    /**
     * 断点续传
     *
     * @param local      本地文件路径
     * @param remotePath 远程路径
     * @param filename   文件名
     */
    public void upload(String local, String remotePath, String filename) {
        try {
            client.initiateListParsing();
            // 设置PassiveMode传输
            client.enterLocalPassiveMode();
            // 设置以二进制流的方式传输
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.setControlEncoding("GBK");
            // 创建并进入目录
            createDirectory(remotePath);

            // 检查远程是否存在文件
            FTPFile[] files = client.listFiles(new String(filename.getBytes("GBK"), StandardCharsets.ISO_8859_1));
            if (files.length == 1) {
                long remoteSize = files[0].getSize();
                File f = new File(local);
                long localSize = f.length();
                if (remoteSize == localSize) {
                    return;
                }
                // 尝试移动文件内读取指针,实现断点续传
                uploadFile(filename, f, remoteSize);
            } else {
                throw new RuntimeException("BaseConstants.ErrorCode.ERROR");
            }
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    private void uploadFile(String remoteFile, File localFile, long remoteSize) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(localFile, "r");
        OutputStream out = client.appendFileStream(new String(remoteFile.getBytes("GBK"), StandardCharsets.ISO_8859_1));
        // 断点续传
        if (remoteSize > 0) {
            client.setRestartOffset(remoteSize);
            raf.seek(remoteSize);
        }
        byte[] bytes = new byte[1024];
        int c;
        while ((c = raf.read(bytes)) != -1) {
            out.write(bytes, 0, c);
        }
        out.flush();
        raf.close();
        out.close();
    }

    /**
     * 上传文件
     *
     * @param pathname    ftp服务保存地址
     * @param fileName    上传到ftp的文件名
     * @param cover       是否覆盖文件
     * @param inputStream 输入文件流
     */
    public void uploadFile(String pathname, String fileName, Integer cover, InputStream inputStream) {
        initFtpClient();
        try {
            client.setFileType(FTP.BINARY_FILE_TYPE);
            createDirectory(pathname);
            FTPFile[] ftpFileArr = client.listFiles(pathname);
            if (!Objects.equals(cover, 1)) {
                for (FTPFile item : ftpFileArr) {
                    if (Objects.equals(item.getName(), fileName)) {
                        throw new RuntimeException("文件已经存在");
                    }
                }
            }
            client.storeFile(fileName, inputStream);
            inputStream.close();
            System.out.println("传输完成---");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("-----------", e);
        } finally {
            closeFtpClient();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.warn("IO close failed!");
                }
            }
        }
    }

    /**
     * 改变目录路径
     */
    private boolean changeWorkingDirectory(String directory) throws IOException {
        boolean flag;
        flag = client.changeWorkingDirectory(directory);
        return flag;
    }

    /**
     * 创建并进入指定文件夹
     */
    private void createDirectory(String path) throws IOException {
        //Assert.isTrue(path.startsWith(HfleConstant.DIRECTORY_SEPARATOR), HfleMessageConstant.FTP_PATH);
        // 根目录无需创建
        if (Objects.equals(path, "")) {
            changeWorkingDirectory(path);
            return;
        }
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!changeWorkingDirectory(path)) {
            String[] dirs = path.split("");
            StringBuilder pathDir = new StringBuilder();
            for (String item : dirs) {
                pathDir.append("").append(item);
                String p = String.valueOf(pathDir);
                if (!existPath(String.valueOf(pathDir))) {
                    // 创建文件夹
                    makeDirectory(p);
                }
                changeWorkingDirectory(p);
            }
        }
    }

    /**
     * 判断ftp服务器文件路径是否存在
     */
    private boolean existPath(String path) throws IOException {
        FTPFile[] ftpFileArr = client.listFiles(path);
        return ftpFileArr.length > 0;
    }

    /**
     * 创建目录
     */
    private void makeDirectory(String dir) {
        try {
            client.makeDirectory(dir);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    /**
     * 下载文件
     *
     * @param path     路径
     * @param fileName 文件名
     * @return 文件流
     */
    public byte[] downloadFile(String path, String fileName) {
        InputStream is = null;
        try {
            initFtpClient();
            // 切换目录
            client.changeWorkingDirectory(path);
            FTPFile[] ftpFiles = client.listFiles();
            for (FTPFile file : ftpFiles) {
                if (fileName.equalsIgnoreCase(file.getName())) {
                    is = client.retrieveFileStream(file.getName());
                }
            }
            client.logout();
            return IOUtils.toByteArray(Objects.requireNonNull(is));
        } catch (IOException e) {
            throw new RuntimeException("", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                logger.error("error", e);
            }
            closeFtpClient();
        }
    }

    /**
     * 删除文件
     *
     * @param path     路径
     * @param fileName 文件名
     */
    public void deleteFile(String path, String fileName) {
        try {
            initFtpClient();
            //切换FTP目录
            client.changeWorkingDirectory(path);
            client.dele(fileName);
        } catch (Exception e) {
            throw new RuntimeException("", e);
        } finally {
            closeFtpClient();
        }
    }
}