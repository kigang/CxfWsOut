import cxf.util.SpelExtractUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class renameTest {


    public void testString (String key, Object... args) {
        String s = String.format(key, args);
        System.out.println(s);
    }

    public static void main(String[] args) {


        String sp = ",\"global.source\":\"${global.value[0].source}\",\"global.target\":@[#global[value][0][target]],\"ftp01.source\":@[#ftp01[value][0][source]],\"ftp01.target\":@[#ftp01[value][0][target]],";
        List<String> sl = SpelExtractUtil.findSpel(sp);
        for (String i : sl) {
            boolean start = false;
            List<String> keys = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            for (int j=0;j<i.length();j++) {

                if (i.charAt(j) == '[') {
                    start = true;
                } else {
                    if (start && i.charAt(j) != '[') {

                        if (i.charAt(j) == ']') {
                            start = false;
                            String b1 = builder.toString();
                            keys.add(b1);
                            builder = new StringBuilder();
                        } else {
                            builder.append(i.charAt(j));
                        }
                    } else {
                        continue;
                    }
                }
            }
            StringBuilder end = new StringBuilder();
            for (int i1=0;i1<keys.size();i1++) {
                String s = keys.get(i1);
                try {
                    Integer.parseInt(s);
                } catch (Exception e) {
                    end.append(s);
                    if (i1 < keys.size()-1) {
                        end.append(".");
                    }
                }
            }
            System.out.println(end);

        }

        /*for (int i = 0; i < 4; i++) {
            try {
                System.out.println("-----------:" + i);
                if (i == 2) {
                    continue;
                }
            } catch (Exception e) {

            } finally {
                System.out.println(i);
            }

        }*/


    }
}
