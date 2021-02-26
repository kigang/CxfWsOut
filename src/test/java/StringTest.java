
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class StringTest {




    public static void main(String[] args) {

        String result = "1==1";
        String pp = "node.daca";
        String result1 = "'15'='1'";
        //System.out.println("S"=="S");
        Expression expression = new SpelExpressionParser().parseExpression(result);
        System.out.println(Optional.ofNullable(expression.getValue(Boolean.class)).orElse(false));
        Map<String, String> map = new HashMap<>();
        map.put("哈哈","哈哈");

        String nodeStr = pp.substring(0, pp.indexOf("."));
        pp = pp.replace("node.","");

        System.out.println(pp);
        if (nodeStr.equalsIgnoreCase("node")) {
            pp = pp.substring(pp.indexOf(".") + 1);
            System.out.println(pp);
        }


    }
}

