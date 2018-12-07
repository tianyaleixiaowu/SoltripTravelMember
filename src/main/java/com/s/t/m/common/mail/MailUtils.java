package com.s.t.m.common.mail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.s.t.m.common.fileupload.UploadActionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * 邮件工具
 * @author Bai
 *
 */
@Component
public class MailUtils{
// TODO
    private static final Logger logger = LoggerFactory.getLogger(MailUtils.class);

    @Resource
    @Qualifier("javaMailSender")
    private JavaMailSender mailSender = new JavaMailSenderImpl();

    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 发送简单邮件
     * @param mail 实体
     */
    public void sendSimpleMail(Mail mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(mail.getTo());
        message.setSubject(mail.getSubject());
        message.setText(mail.getText());
        message.setCc(mail.getCc());
        mailSender.send(message);
    }

    /**
     * 发送附件
     *@param request 接收文件的请求
     *@param mail 实体
     * @throws Exception
     */
    public void sendAttachmentsMail(Mail mail,HttpServletRequest request){
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getText());
            List<String> list = UploadActionUtil.uploadFile(request);
            for (int i = 1,length = list.size();i<=length;i++) {
                String fileName = list.get(i-1);
                String fileTyps = fileName.substring(fileName.lastIndexOf("."));
                FileSystemResource file = new FileSystemResource(new File(fileName));
                helper.addAttachment("附件-"+i+fileTyps, file);
            }
            mailSender.send(mimeMessage);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 发送静态资源  一张照片
     * @param mail 实体
     * @throws Exception
     */
    public void sendInlineMail(Mail mail){
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setText("<html><body><img src=\"cid:chuchen\" ></body></html>", true);
            //这里的文件是路径
            FileSystemResource file = new FileSystemResource(new File(mail.getStaticImgFile()));
            // addInline函数中资源名称chuchen需要与正文中cid:chuchen对应起来
            helper.addInline("chuchen", file);
            mailSender.send(mimeMessage);
        }catch (Exception e){
            logger.error("发送邮件发生异常");
        }

    }

    /**
     * 发送模板邮件
     * @param mail
     */
    public void sendTemplateMail(Mail mail){
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            //读取 html 模板
            Configuration cfg = getConfiguration();
            Template template = cfg.getTemplate(mail.getTemplateName()+".ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getTemplateModel());
            helper.setText(html, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailSender.send(message);
    }

    private static Configuration getConfiguration() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(MailConstant.TEMPLATEPATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }
}
