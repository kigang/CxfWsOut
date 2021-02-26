import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnicodeToJavaTest {

    public static void main(String[] args) {
        String str = "\"city\":\"\\u5e7f\\u5dde\",\"cityEn\":\"guangzhou\",\"country\":\"\\u4e2d\\u56fd\",\"countryEn\":\"China\",";
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            // group 6728
            String group = matcher.group(2);
            // ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            // group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        System.out.println(str);

    }
}
