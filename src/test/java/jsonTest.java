import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import cxf.dto.Input;
import cxf.dto.OutPut;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class jsonTest {

    public static void main(String[] args) {
        String json = "{\n" +
                "    \"resultPropagationFlag\": true,\n" +
                "    \"globalPromotionFlag\": false,\n" +
                "    \"timeout\": {\n" +
                "        \"interval\": 30,\n" +
                "        \"alertFlag\": false,\n" +
                "        \"alertCode\": \"HORC_TASK_TIMEOUT\"\n" +
                "    },\n" +
                "    \"failAlert\": {\n" +
                "        \"failAlertCode\": \"HORC_TASK_FAIL\",\n" +
                "        \"alertFlag\": false\n" +
                "    },\n" +
                "    \"params\": {\n" +
                "        \"tenantId\": \"1607\",\n" +
                "        \"isGateway\": \"0\"\n" +
                "    }\n" +
                "}";
        Map<String, Object> jsonMap = JSON.parseObject(json, new TypeReference<HashMap<String, Object>>(){});
        //System.out.println(jsonMap.toString());

        Map<String, Object> propertiesMap = new HashMap<>();
        //transfer(null, jsonMap, propertiesMap);

       // System.out.println(propertiesMap.toString());

        List<OutPut> outPuts = new ArrayList<>();
        OutPut outPut1 = new OutPut();
        outPut1.setId(1L);
        outPut1.setName("out 1");
        OutPut outPut2 = new OutPut();
        outPut2.setId(2L);
        outPut2.setName("out 2");

        outPuts.add(outPut1);
        outPuts.add(outPut2);

        Input input = new Input();
        input.setId(1111L);
        input.setName("input");
        input.setOutPuts(outPuts);

        Map<String, Object> jsonMap1 = JSON.parseObject(JSON.toJSONString(input), new TypeReference<HashMap<String, Object>>(){});
        Map<String, Object> propertiesMap1 = new HashMap<>();
        transfer(null, jsonMap1, propertiesMap1);

        System.out.println(propertiesMap1.toString());

    }

    private static void transfer(String parentKey, Map<String, Object> jsonMap, Map<String, Object> propertiesMap) {
        if (CollectionUtils.isEmpty(jsonMap)) {
            return;
        }
        String prefix = parentKey == null ? "" : parentKey + ".";
        for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
            String key = prefix + entry.getKey();
            doTransfer(key, entry.getValue(), propertiesMap);
        }
    }

    private static void transferList(String parentKey, List<Object> jsonList, Map<String, Object> propertiesMap) {
        if (CollectionUtils.isEmpty(jsonList)) {
            return;
        }
        String prefix = parentKey == null ? "" : parentKey;
        for (int i = 0; i < jsonList.size(); i++) {
            String key = prefix + "[" + i + "]";
            doTransfer(key, jsonList.get(i), propertiesMap);
        }
    }

    @SuppressWarnings("unchecked")
    private static void doTransfer(String key, Object value, Map<String, Object> propertiesMap) {
        if (value instanceof Map) {
            transfer(key, (Map<String, Object>) value, propertiesMap);
        } else if (value instanceof String || value instanceof Integer || value instanceof Long || value instanceof Boolean) {
            propertiesMap.put(key, value);
        } else if (value instanceof List) {
            transferList(key, (List<Object>) value, propertiesMap);
        }
    }

}
