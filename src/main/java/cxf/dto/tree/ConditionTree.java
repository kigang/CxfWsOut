package cxf.dto.tree;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.*;

public class ConditionTree {


    public ConditionTreeNode buildTree(String jsonTxt) {
        ConditionTreeNode rootNode = new ConditionTreeNode();
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonTxt);
            rootNode.setNodeName("root");
            conditionJudgement(jsonNode, null, rootNode);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("buildTree error:", e);
        }
        return rootNode;
    }

    private void conditionJudgement(JsonNode jsonNode, String conjugation, ConditionTreeNode conditionNode) {
        if (jsonNode.hasNonNull(DependentRelation.AND.name())) {
            condition(jsonNode.get(DependentRelation.AND.name()), "&&", conditionNode);
        } else if (jsonNode.hasNonNull(DependentRelation.OR.name())) {
            condition(jsonNode.get(DependentRelation.OR.name()), "||", conditionNode);
        } else {
            if (conjugation == null) {
                conditionNode.setStatus(ConditionNodeStatusEnum.FALSE.name());
                return;
            }
            // 需要的结果对应的节点名
            buildLeaf(conditionNode, jsonNode);
        }
    }

    public void buildLeaf(ConditionTreeNode conditionNode, JsonNode jsonNode) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode dependentNode = jsonNode.get("value");
            ConditionParam param = objectMapper.readValue(jsonNode.get("value").asText(), ConditionParam.class);
            if (dependentNode == null) {
                conditionNode.setStatus(ConditionNodeStatusEnum.FALSE.name());
                return;
            }
            conditionNode.setCondition(param.getTaskName());
        } catch (Exception e) {

        }
    }

    private void condition(JsonNode jsonNode, String conjugation, ConditionTreeNode conditionNode) {
        if (!jsonNode.isArray()) {
            conditionNode.setStatus(ConditionNodeStatusEnum.FALSE.name());
            return;
        }
        conditionNode.setConjugation(conjugation);
        List<ConditionTreeNode> childNodes = new ArrayList<>();
        for (int i = 0; i < jsonNode.size(); i++) {
            ConditionTreeNode childNode = new ConditionTreeNode();

            childNode.setParentName(conditionNode.getNodeName());
            conditionJudgement(jsonNode.get(i), conjugation, childNode);
            childNodes.add(childNode);
        }
        conditionNode.setChildNodes(childNodes);
    }


    public void recursive(ConditionTreeNode treeNode) {
        //System.out.println(treeNode.getNodeName());
        if (!treeNode.isWaiting()) {
            return;
        }
        if (treeNode.isLeafNode()) {
            //尝试解析叶子节点表达式
            try {
                Expression expression = new SpelExpressionParser().parseExpression(treeNode.getCondition());
                Boolean result = Optional.ofNullable(expression.getValue(Boolean.class)).orElse(false);
                treeNode.setStatus(result ? ConditionNodeStatusEnum.TRUE.name() : ConditionNodeStatusEnum.FALSE.name());
            } catch (Exception e) {
                //解析失败不做操作
            }
            return;
        }
        treeNode.getChildNodes().forEach(childNode -> {
            recursive(childNode);
            if (!childNode.isWaiting()) {
                ConditionTreeNode parentNode = treeNode.findNodeByName(childNode.getParentName());
                if (Objects.nonNull(parentNode) && parentNode.isWaiting()) {
                    boolean result = childNode.getStatus().equals(ConditionNodeStatusEnum.TRUE.name());
                    parentNode.updateStatus(result);
                }
            }
        });
    }
}
