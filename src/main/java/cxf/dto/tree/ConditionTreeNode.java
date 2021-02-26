package cxf.dto.tree;



import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author ycwiacb 2021/1/17
 */
public class ConditionTreeNode {
    /**
     * 名称：全局唯一
     */
    private String nodeName = UUID.randomUUID().toString();

    /**
     * 初始化WAITING
     */
    private String status = ConditionNodeStatusEnum.WAITING.name();;

    /**
     * 父节点
     */
    private String parentName;

    /**
     * 连接符 &&, ||
     */
    private String conjugation;

    /**
     * 子节点
     */
    private List<ConditionTreeNode> childNodes;

    /**
     * 叶子节点表达式
     */
    private String condition;

    public ConditionTreeNode() {

    }

    public ConditionTreeNode findNodeByName(String name) {
        if (this.nodeName.equals(name)) {
            return this;
        }
        if (CollectionUtils.isEmpty(this.getChildNodes())) {
            return null;
        }
        int num = this.childNodes.size();
        for (int i = 0; i < num; i++) {
            ConditionTreeNode child = childNodes.get(i);
            ConditionTreeNode result = child.findNodeByName(name);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /*public ConditionTreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(ConditionTreeNode parentNode) {
        this.parentNode = parentNode;
    }*/

    public String getConjugation() {
        return conjugation;
    }

    public void setConjugation(String conjugation) {
        this.conjugation = conjugation;
    }

    public List<ConditionTreeNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<ConditionTreeNode> childNodes) {
        this.childNodes = childNodes;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    private boolean isRootNode() {
        return Objects.isNull(parentName);
    }

    public boolean isLeafNode() {
        return Objects.isNull(childNodes) || childNodes.isEmpty();
    }

    public boolean isWaiting() {
        return Objects.equals(status, ConditionNodeStatusEnum.WAITING.name());
    }

    private boolean hasWaiting() {
        return !isLeafNode() && childNodes.stream().anyMatch(ConditionTreeNode::isWaiting);
    }

    public void updateStatus(boolean result) {
        if (isLeafNode() || !hasWaiting()) {
            this.status = result ? ConditionNodeStatusEnum.TRUE.name() : ConditionNodeStatusEnum.FALSE.name();
            return;
        }
        if (result && Objects.equals(conjugation, "||")) {
            this.status = ConditionNodeStatusEnum.TRUE.name();
        }
        if (!result && Objects.equals(conjugation, "&&")) {
            this.status = ConditionNodeStatusEnum.FALSE.name();
        }
    }
}
