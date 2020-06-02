package com.zjg.monitor.util;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * @author zjg
 * <p> 2020/4/3 20:10 </p>
 */
@Data
@Slf4j
@Component
public class EmailUtil {

    /**
     * fromEmail是发件人的邮箱
     */
    @Value("${email.fromEmail}")
    private String fromEmail;
    /**
     * password是邮箱授权码
     */
    @Value("${email.password}")
    private String password;
    /**
     * 发件人昵称
     */
    @Value("${email.fromNick}")
    private String fromNick;
    /**
     * 发件主题前缀
     */
    @Value("${email.mailTitlePrefix}")
    private String mailTitlePrefix;

    public boolean audio (String emails, String system, String audioType, String audioKey, double nowValue, double threshold) {
        if (StringUtils.isEmpty(emails)) {
            log.error("发生报警，系统：{}未配置邮件负责人", system);
            return false;
        }
        String mailTitle = mailTitlePrefix + "-" + system + audioType + audioKey + "报警";
        String content = "系统：<span style='color:red;'>" + system + "</span>发生报警<br>" +
                "时间： " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "<br>" +
                "监控类型：<span style='color:red;'>" + audioType +"</span><br>" +
                "监控属性：<span style='color:red;'>" + audioKey +"</span><br>" +
                "当前值：<span style='color:red;'>" + nowValue +"</span><br>" +
                "阈值：<span style='color:red;'>" + threshold +"</span><br>";
        String[] emailsSplit = emails.split(",");
        List<String> emailsList = new ArrayList<>(Arrays.asList(emailsSplit));
        try {
            sendMail(emailsList, mailTitle, content);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("邮件发送错误，发送人：{}，系统：{}, 报错内容：{}, fromEmail" + fromEmail + " password " + password, JSON.toJSONString(emails), system ,content, e);
            return false;
        }
        return true;
    }

    private void sendMail(List<String> emails, String
            mailTitle, String mailContent) throws
            MessagingException, UnsupportedEncodingException {
        Properties props = new Properties();//加载一个配置文件
        //存储发送邮件服务器的信息,qq为例，如果是163则是smtp.163.com
        props.put("mail.smtp.host", "smtp.qq.com");
        //使用smtp简单邮件传输协议
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");//是否需要身份验证
        props.put("mail.smtp.ssl.enable", "true");//QQ邮箱的SSL加密
        Session session = Session.getInstance(props);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //由邮件会话创建一个扩展信息对象
        MimeMessage msg = new MimeMessage(session);
        //自定义昵称
        String nick = MimeUtility.encodeText(fromNick);//防止乱码
        msg.setFrom(new InternetAddress(nick + "<" + fromEmail + ">"));

        InternetAddress[] addresses = new InternetAddress[emails.size()];
        for (int i = 0; i < emails.size(); i++) {
            addresses[i] = new InternetAddress(emails.get(i));
        }
        msg.setRecipients(Message.RecipientType.TO, addresses);

        msg.setSubject(mailTitle);//设置标题
        //设置为html格式，可以发送多种样式
        msg.setContent(mailContent, "text/html;charset=UTF-8");
        //msg.setSentDate(new Date());  //设置发信时间
        msg.saveChanges(); //存储邮件信息
        //使用smtp协议获取session环境的Transprot对象来发送邮件 javamial使用
        //Transport对象来管理发送邮件服务
        Transport tran = session.getTransport("smtp");
        tran.connect(props.getProperty("mail.smtp.host"), fromEmail,
                password);//链接邮箱服务器，发送邮件的邮箱，以及授权码
        //发送邮件，getAllRecipients()是所有已设好的收件人地址
        tran.sendMessage(msg, msg.getAllRecipients());
        tran.close();
    }

    public static void main(String[] args) throws MessagingException,
            UnsupportedEncodingException {
        //"自定义昵称"，可以自己定义想要使用的发送昵称
        //toEMail,是接收方的email
        //title,是邮件的标题
        //再下一个参数是邮件的内容，支持html
        /*sendMail("自定义昵称", "toEmail",
                "title",
                "<span style='color:red;'>下雨了_简</span>的博客，欢迎交流");*/
    }
}
