package com.zbcn.demo.config;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Map;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
public class MailSendConfig {

	@Bean
	JavaMailSenderImpl mailSender(MailProperties properties) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		applyProperties(properties, sender);
		return sender;
	}

	private void applyProperties(MailProperties properties, JavaMailSenderImpl sender) {
		sender.setHost(properties.getHost());
		if (properties.getPort() != null) {
			sender.setPort(properties.getPort());
		}
		sender.setUsername(properties.getUsername());
		sender.setPassword(properties.getPassword());
		sender.setProtocol(properties.getProtocol());
		if (properties.getDefaultEncoding() != null) {
			sender.setDefaultEncoding(properties.getDefaultEncoding().name());
		}
		if (!properties.getProperties().isEmpty()) {
			sender.setJavaMailProperties(asProperties(properties.getProperties()));
		}
	}

	private Properties asProperties(Map<String, String> source) {
		Properties properties = new Properties();
		properties.putAll(source);
		return properties;
	}
}
