package edu.kh.project.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.project.email.model.service.EmailService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("email")
@RequiredArgsConstructor  // final이 붙은 필드 / @NotNull 필드에 자동으로 의존성 주입 (@Autowired 생성자 방식 코드 자동완성)   (보이지는 않음)
public class EmailController {

	/*이메일
		- 이메일 양식 유효성 검사
		- 중복 검사 (ajax)
		- 인증번호 발송 (난수)
	 * */



	
	
	
	
	private final EmailService service;
	// private final EX ex;
	// private final Test test;
			// => 자동으로 @Autowired (final 붙이기)
	
	
	
	@PostMapping("signup")
	@ResponseBody
	public int signup(@RequestBody String email) { // body에 값이 실려옴
		
		String authKey = service.sendEmail("signup", email);
		
		return 0;
	}
	

	
}

/* @Autowired를 이용한 의존성 주입 방법은 3가지 존재
 * 1) 필드
 * 2) setter
 * 3) 생성자 (권장)
 * 
 * Lombok 라이브러리에서 제공하는 
 * 
 * @RequiredArgsConstructor 를 이용하면
 * 
 * 필드 중
 * 1) 초기화되지 않은 final이 붙은 필드
 * 2) 초기화되지 않은 @NotNull 이 붙은 필드
 * 
 * 1,2에 해당하는 필드에 대한 
 * @Autowired 생성자 구문을 자동 완성
 * 
 * */

// 1) 필드에 의존성 주입하는 방법 (권장 X)
// @Autowired // 의존성 주입(DI)
// private EmailService service;


// 2) setter 이용

// private EmailService service;

// @Autowired
// public void setService(EmailService service) {
//	this.service = service;
// }


// 3) 생성자
// private EmailService service;
// private MemberService service2;

// @Autowired
// public EmailController(EmailService service, MemberService service2) {
// 	this.service = service;
//  this.service2 = service2;
// }

