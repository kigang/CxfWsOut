import java.io.*;

public class FileSizeTest {

    public static void main(String[] args) {
        File file = new File("D:\\tmp\\11.txt");
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {

            /*FileInputStream input = new FileInputStream("D:\\tmp\\11.txt");

            byte[] buffer = new byte[1024 * 1024 * 1024];
            int len = input.read(buffer);
            System.out.println(len);

            input.close();*/
            Long ss = 2L;

            String s = "哈哈";
            System.out.println(s.getBytes().length);

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            Integer mb = 1024 * 1024;
            //System.out.println(builder.length());
            //System.out.println(builder);
        }
    }
}
