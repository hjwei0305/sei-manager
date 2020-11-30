package com.changhong.sei.manager.commom;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.manager.entity.User;
import com.changhong.sei.manager.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public ResultData<String> sendMail(String subject, String context, String... accounts) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(User.FIELD_ACCOUNT, accounts, SearchFilter.Operator.IN));
        List<User> users = userService.findByFilters(search);
        if (CollectionUtils.isNotEmpty(users)) {
            User[] userArr = users.toArray(new User[0]);

            sendMail(subject, context, userArr);
        }

        return ResultData.success();
    }

    public ResultData<String> sendMail(String subject, String context, User... users) {
        try {
            //构造MimeMessage并设置相关属性值,MimeMessage就是实际的电子邮件对象.
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            //设置发件人
            mimeMessage.setFrom(new InternetAddress(senderUsername, "sei-manager"));
            //设置收件人,为数组,可输入多个地址.
            List<InternetAddress> to = new ArrayList<>();
            for (User user : users) {
                to.add(new InternetAddress(user.getEmail(), user.getNickname()));
            }

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // 设置收件人
            helper.setTo(to.toArray(new InternetAddress[0]));
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
