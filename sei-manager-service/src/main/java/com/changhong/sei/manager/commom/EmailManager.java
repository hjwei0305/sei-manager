package com.changhong.sei.manager.commom;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.manager.service.UserService;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-13 17:28
 */
@Component
public class EmailManager {
    private static final Logger LOG = LoggerFactory.getLogger(EmailManager.class);

    @Value("${spring.mail.username}")
    private String senderUsername;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    public ResultData<Void> sendMailByAccount(String subject, String context, String... accounts) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(User.FIELD_ACCOUNT, accounts, SearchFilter.Operator.IN));
        List<User> users = userService.findByFilters(search);
        if (CollectionUtils.isNotEmpty(users)) {
            User[] userArr = users.toArray(new User[0]);

            return sendMail(subject, context, userArr);
        } else {
            return ResultData.fail("未找到用户.");
        }
    }

    public ResultData<Void> sendMail(String subject, String context, User... users) {
        Set<InternetAddress> toSet = new HashSet<>();
        for (User user : users) {
            try {
                toSet.add(new InternetAddress(user.getEmail(), user.getNickname()));
            } catch (UnsupportedEncodingException e) {
                LOG.error("构建邮件发送地址异常", e);
            }
        }
        return sendMail(subject, context, toSet);
    }

    public ResultData<Void> sendMail(String subject, String context, String email) {
        Set<InternetAddress> toSet = null;
        try {
            toSet = Sets.newHashSet(new InternetAddress(email));
            return sendMail(subject, context, toSet);
        } catch (AddressException e) {
            LOG.error("构建邮件发送地址异常", e);
            return ResultData.fail("构建邮件发送地址异常");
        }
    }

    public ResultData<Void> sendMail(String subject, String context, Set<InternetAddress> toSet) {
        try {
            //构造MimeMessage并设置相关属性值,MimeMessage就是实际的电子邮件对象.
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            //设置发件人
            mimeMessage.setFrom(new InternetAddress(senderUsername, "sei-manager"));
            //设置收件人,为数组,可输入多个地址.

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // 设置收件人
            helper.setTo(toSet.toArray(new InternetAddress[0]));
            //ContentBody.RecipientType==>TO(主要接收人),CC(抄送),BCC(密件抄送);
            //设置邮件主题,如果不是UTF-8就要转换下
            helper.setSubject(subject);
            //设置邮件内容
            helper.setText(context, true);
            //发送时间
            helper.setSentDate(new Date());

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            LOG.error("邮件发送异常", e);
        }
        return ResultData.success();
    }
}
