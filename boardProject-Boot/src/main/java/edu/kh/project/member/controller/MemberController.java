package edu.kh.project.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

@Slf4j
@Controller
@RequestMapping("member")
public class MemberController {
				
				// 의존성 주입(DI)
	@Autowired // 타입이 같거나 상속관계인 것을 찾아서 주입
	private MemberService service;
	
	
	/* [로그인]
	 * - 특정 사이트에 아이디 / 비밀번호 등을 입력해서
	 *   해당 정보가 있으면 조회/서비스 이용
	 *   
	 * - 로그인 한 정보를 session에 기록하여 
	 *   로그아웃 또는 브라우저 종료 시까지
	 *   해당 정보를 계속 이용할 수 있게 함
	 * */
	
	
	/** 로그인
	 * @param inputMember : 커맨드 객체 (@ModelAttribute 생략)
	 * 							(memberEmail, memberPw 세팅된 상태)
	 * @param ra : 리다이렉트 시 request scope로 데이터를 전달하는 객체
	 * @param model : 데이터 전달용 객체 (기본 request scope)
	 * @return "redirect:/"
	 */
	@PostMapping("login")
	public String login(Member inputMember, // ModelAttribute는 생략 가능  // value값이 넘어와서 세팅
					RedirectAttributes ra,
					Model model  // 기본 request scope, sessionAttribute와 사용하면 session scope
			) {
		
		// 로그인 서비스 호출
		Member loginMember = service.login(inputMember); // Email이랑 password 세팅돼있음
		
		
		
		return "redirect:/"; // 메인페이지 재요청
	}
	
	
	
	
	
	
	
	
	
	
	
}
