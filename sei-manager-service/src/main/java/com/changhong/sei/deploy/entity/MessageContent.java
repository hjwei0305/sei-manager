package com.changhong.sei.deploy.entity;

import com.changhong.sei.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实现功能：消息内容
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-03-17 16:48
 */
@Entity
@Table(name = "ge_message_content")
public class MessageContent extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4080817677419097251L;
    /**
     * 消息内容
     */
    @Column(name = "msg_content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
