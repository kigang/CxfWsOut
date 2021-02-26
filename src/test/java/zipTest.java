import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class zipTest {

    public static void main(String[] args) {
        String ss = "大大家啊搭建大红大单号";
        try {
            String zipFile = "D:\\tmp\\11.zip";
            File ff = new File(zipFile);
            if (!ff.exists()) {
                ff.createNewFile();
            }
            //BufferedInputStream fis = new BufferedInputStream(new ByteArrayInputStream(ss.getBytes()));
            //byte[] buffer = new byte[fis.available()];
            byte[] buffer = buffer = ss.getBytes();
            FileOutputStream out = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(out);
            ZipEntry zipEntry = new ZipEntry("11.txt");
            zos.putNextEntry(zipEntry);
            zos.write(buffer);
            zos.closeEntry();
            //toClient.write(buffer);
            zos.flush();
            //fis.close();
            zos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
