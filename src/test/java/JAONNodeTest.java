import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cxf.dto.tree.ConditionTree;
import cxf.dto.tree.ConditionTreeNode;

public class JAONNodeTest {


    public static void main(String[] args) {
        String text = "{\"AND\":[{\"value\":\"{\\\"definitionCode\\\":\\\"ORC20201200200\\\",\\\"definitionName\\\":\\\"1223任务名称参数传递\\\",\\\"dateTimeRange\\\":[\\\"2020-12-24 00:00:00\\\",\\\"2020-12-25 00:00:00\\\"],\\\"taskName\\\":\\\"all\\\",\\\"definitionId\\\":\\\"=a1ta_fDssvbin3A7Uh0JOUWRbtYjvIPBZBvzBG4Nn-4==\\\",\\\"startDate\\\":\\\"2020-12-24 00:00:00\\\",\\\"endDate\\\":\\\"2020-12-25 00:00:00\\\"}\"},{\"value\":\"{\\\"definitionCode\\\":\\\"ORC20201200054\\\",\\\"definitionId\\\":\\\"=KbWyvcScwpwEMciH-pTqkHWcE0SkUOvkiNRMnx5W1a0==\\\",\\\"definitionName\\\":\\\"1130-MQ\\\",\\\"taskName\\\":\\\"mq-consuer\\\",\\\"startDate\\\":\\\"2020-12-23 00:00:00\\\",\\\"endDate\\\":\\\"2020-12-25 00:00:00\\\"}\"},{\"OR\":[{\"value\":\"{\\\"definitionCode\\\":\\\"ORC20201200408\\\",\\\"definitionId\\\":\\\"=EccLz84bY3AbrW7jUmWeuJp7LfmkHK0wBM3_Gp947z0==\\\",\\\"definitionName\\\":\\\"fusion-test\\\",\\\"taskName\\\":\\\"mq\\\",\\\"startDate\\\":\\\"2020-12-24 00:00:00\\\",\\\"endDate\\\":\\\"2020-12-25 00:00:00\\\"}\"},{\"value\":\"{\\\"definitionCode\\\":\\\"ORC20201200099\\\",\\\"definitionId\\\":\\\"=8qH1fNH8Xomu7-xtHZuGDZp7LfmkHK0wBM3_Gp947z0==\\\",\\\"definitionName\\\":\\\"PO_FLOW\\\",\\\"taskName\\\":\\\"PO_RECEIVE\\\"}\"}]}]}";
        try {
            String text1 = "{\"OR\":[{\"value\":\"{\\\"definitionCode\\\":\\\"ORC20201200443\\\",\\\"definitionName\\\":\\\"依赖子定义-001\\\",\\\"taskName\\\":\\\"1221\\\"}\"},{\"value\":\"{\\\"definitionCode\\\":\\\"ORC20201200445\\\",\\\"definitionName\\\":\\\"依赖子定义-003\\\",\\\"taskName\\\":\\\"all\\\"}\"},{\"OR\":[{\"value\":\"{\\\"definitionCode\\\":\\\"ORC20201200446\\\",\\\"definitionName\\\":\\\"依赖子定义-004\\\",\\\"taskName\\\":\\\"all\\\"}\"},{\"value\":\"{\\\"definitionCode\\\":\\\"ORC20201200444\\\",\\\"definitionName\\\":\\\"依赖子定义-002\\\",\\\"taskName\\\":\\\"1>0\\\"}\"}]}]}";
            //ObjectMapper objectMapper = new ObjectMapper();
            //JsonNode jsonNode = objectMapper.readTree(text1);
            ConditionTree tree = new  ConditionTree();
            System.out.println();
            ConditionTreeNode node = tree.buildTree(text1);
            tree.recursive(node);
            System.out.println(node.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
