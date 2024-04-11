package edu.kh.project.common.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:/config.properties")  // 괄호 속 파일의 내용을 읽어드리겠다
public class EmailConfig {
	
	// @Value : properties에 작성된 내용 중 키가 일치하는 값을 얻어와 필드에 대입
	@Value("${spring.mail.username}")
	private String userName;
	
	@Value("${spring.mail.password}")
	private String password;
	
	@Bean
	public JavaMailSender javaMailSender() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		Properties prop = new Properties();
		prop.setProperty("mail.transport.protocol", "smtp"); 		// 전송 프로토콜 설정
		prop.setProperty("mail.smtp.auth", "true"); 				// smtp에 대한 서버인증을 사용할지 말지에 대한 여부
		prop.setProperty("mail.smtp.starttls.enable", "true");	    // 안전한 연결 활성화 여부
		prop.setProperty("mail.debug", "true"); 					// mail보낼 때 debug 모드 사용 여부
		prop.setProperty("mail.smtp.ssl.trust","smtp.gmail.com"); 	// 신뢰할 수 있는 smtp 서버 호스트 지정  / 뒤에는 서버 주소
		prop.setProperty("mail.smtp.ssl.protocols","TLSv1.2"); 		// TLSv1.2버전 사용
																    // 구글에서도 해당 내용들 검색 가능
		
		mailSender.setUsername(userName);
		mailSender.setPassword(password);
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setDefaultEncoding("UTF-8");
		mailSender.setJavaMailProperties(prop);  // Java 메일에 설정이 있으면 앞서 정의한 property들이 추가됨

		
		return mailSender;
	}
	
}
