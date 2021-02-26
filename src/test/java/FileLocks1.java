import com.alibaba.fastjson.JSONArray;
import cxf.dto.TransferResult;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

public class FileLocks1 {
    public static void main(String[] args) throws Exception {
        /*RandomAccessFile raf = new RandomAccessFile("D:\\tmp\\test\\idoc\\application.yml", "rw");
        FileChannel fileChannel = raf.getChannel();
        FileLock lock = fileChannel.lock();*/

       // File file = new File("D:\\tmp\\test\\idoc\\application.ymlservice rpcbind start");

        //System.out.println(file.getAbsolutePath());

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("D:\\tmp\\11.txt"));

            List<TransferResult> list = new ArrayList<>();
            for (int i=0;i<5000 ;i++) {
                TransferResult result = new TransferResult();
                result.setMessage("大数据测试大数据测试大数据测试大数据测试");
                result.setShaCode("大数据测试大数据测试");
                result.setBatchId("大数据测试大数据测试大数据测试大数据测试");
                list.add(result);
            }
            JSONArray array = new JSONArray();
            array.addAll(list);
            out.write(array.toJSONString());
            out.close();
            System.out.println("文件创建成功！");
        } catch (IOException e) {
        }

    }
}
