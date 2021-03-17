package com.changhong.sei.ge.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.ge.dao.MessageContentDao;
import com.changhong.sei.ge.entity.MessageContent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 消息内容(MessageContent)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Service
public class MessageContentService extends BaseEntityService<MessageContent> {
    @Autowired
    private MessageContentDao dao;

    @Override
    protected BaseEntityDao<MessageContent> getDao() {
        return dao;
    }

    /**
     * 获取内容
     *
     * @param messageId 内容id
     * @return 返回内容
     */
    public String getContent(String messageId) {
        String content = null;
        if (StringUtils.isNotBlank(messageId)) {
            MessageContent messageContent = dao.findOne(messageId);
            if (Objects.nonNull(messageContent)) {
                content = messageContent.getContent();
            }
        }
        return content;
    }
}