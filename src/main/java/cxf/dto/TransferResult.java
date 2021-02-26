package cxf.dto;

/**
 * 传输结果对象
 *
 * @author qigang.qin@hand-china.com 2020/10/26 14:42
 */
public class TransferResult {

    private String source;

    private String target;

    private  String type;



    private Boolean result;

    private String shaCode;

    private String message;

    private String batchId;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }



    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getShaCode() {
        return shaCode;
    }

    public void setShaCode(String shaCode) {
        this.shaCode = shaCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
