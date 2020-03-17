package com.zbcn.demo.alarm;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 2018/7/2
 */
@Component
public class EmailAlarm extends BaseAlarm {
    private static Logger logger = LoggerFactory.getLogger(EmailAlarm.class);

    @Value("${alarm.email.interval.time:300}")
    private Long intervalTime;
    @Value("${alarm.email.receiver:null}")
    private String receiver;
    @Value("${spring.application.name:null}")
    private String applicationName;
    @Resource
    private JavaMailSenderImpl mailSender;

    final String tag = System.getenv("TAG") == null ? "dev" : System.getenv("TAG");

    @Override
    public void alarm(String receiver, String subject, Throwable e) {
        if (validReceiver(receiver)) {
            String body = ExceptionUtils.getStackTrace(e);
            cacheAlarm(receiver, subject, body, e.getClass().getName());
        }
    }

    public void alarm(String subject, Throwable e) {
        alarm(receiver, subject, e);
    }

    public void alarm(String subject, String body) {
        alarm(receiver, subject, body);
    }

    public void alarm(Throwable e) {
        alarm("", e);
    }

    @Override
    protected void sendAlarm(String receiver, String subject, String body) {
        boolean sendSuccess = false;
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(receiver.split(","));

            message.setSubject(ZbcnStringUtils.wrapStringWithBracket(applicationName) + tag + "环境 " + subject);
            message.setText(body);
            message.setFrom(mailSender.getUsername());
            mailSender.send(message);
            sendSuccess = true;
        } catch (Throwable e) {
            logger.warn(e.getMessage(), e);
        } finally {
            logger.info("发送报警邮件 {} receiver:{};subject:{}", sendSuccess, receiver, subject, body);
        }
    }


    @Override
    protected long getIntervalTime() {
        return (intervalTime == null ? DEFAULT_INTERVAL : intervalTime) * 1000;
    }

}
