package xyz.no21.customsview.models;


/**
 * Created by lin on 2016/12/6.
 * Email:L437943145@gmail.com
 * <p>
 * desc:弹幕消息实体
 */

public class BarrageMessageModel {

    /**
     * 文本类型
     */
    public static final int TYPE_TEXT = 1;


    private int type;
    private String content;
    private long senTime;


    public BarrageMessageModel() {
    }

    public BarrageMessageModel(String content) {
        this.type = TYPE_TEXT;
        this.content = content;
        senTime = System.currentTimeMillis();
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSenTime() {
        return senTime;
    }

    public void setSenTime(long senTime) {
        this.senTime = senTime;
    }
}
