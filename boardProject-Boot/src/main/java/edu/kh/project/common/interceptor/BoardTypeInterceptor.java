package edu.kh.project.common.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import edu.kh.project.board.model.service.BoardService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/* Interceptor : 요청/응답 가로채는 객체 (Spring 지원)
 * 
 * Client <-> Fileter <-> Dispatcher Sevelet <-> Interceptor <-> Controller ...
 * 
 * HandlerInterceptor 인터페이스를 상속 받아서 구현 해야 한다.
 * 
 * - preHandle (전처리) : Dispatcher Servlet -> Controller 사이 수행
 * 
 * - postHanlde (후처리) : Controller -> Dispatcher Servlet 사이 수행
 * 			(응답 받고 forward할 때 가로챌 수 있음)
 * 
 * - afterCompletion (뷰 완성 (forward 코드 해석 ) 후 : View Resolver -> Dispatcher Servlet 사이 수행
 * 
 * */




@Slf4j
public class BoardTypeInterceptor implements HandlerInterceptor{
	// HandlerInterceptor 상속받는데 오버라이딩 강제화 안 뜬다 
	// => 안에 메소드가 abstract이 아닌 default로 되어있음 -> 알아서 오버라이딩해서 쓰면 됨
	
	// BoardService 의존성 주입
	@Autowired
	private BoardService service;
			// config에 기본생성자를 이용해야하는데 RequiredArgsConstructor를 사용하면 매개변수 생성자밖에 못 만들어서
			// 기본 생성자에 대한 적절한 코드 수행이 불가하다 = > @Autowired 사용하기
	
	
	
	// 전처리
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// application scope : 
		// - 서버 종료 시까지 유지되는 Servlet 내장 객체
		// - 서버 내에 딱 한 개만 존재!
		// 		--> 모든 클라이언트가 공용으로 사용
		
		
		/* interceptor가 어떤 일 할 지 정해놓음 , 언제 일 할지 설정 필요 config */
		
		
		// application scope 객체 얻어오기
		ServletContext application = request.getServletContext();  // ServletContext가 application 원래 이름
		
		
		// application scope에 "boardTypeList"가 없을 경우
		if(application.getAttribute("boardTypeList") == null) {
			
			log.info("BoardTypeInterceptor - preHandle(전처리) 동작 실행");  // 정보 (print구문 찍는 느낌)
			
			// Dispatcher Servlet에서 intercept해서 Controller를 거치는 것이 아닌 DB를 다녀올 것
			
			
			// boardTypeList 조회 서비스 호출
			List<Map<String, Object>> boardTypeList = service.selectBoardTypeList();
			
			// 조회 결과를 application scope에 추가
			application.setAttribute("boardTypeList", boardTypeList);
			
		}
		
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
			
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	
	
	
}
