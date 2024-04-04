package edu.kh.todo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그 객체 자동 생성
@Controller  // 요청 / 응답 제어 역할 명시 + Bean 등록
public class MainController {

	@Autowired // DI (의존성 주입)  // 같은 타입이거나 상속받은 걸 가져옴 //== new TodoServiceImpl();
	private TodoService service; /* = new TodoServiceImpl()*/
	// Spring에서 객체는 Spring이 만들고 관리한다
	
	
	@RequestMapping("/")  // 메인페이지로 이동하는 요청 '/' 사용 가능
	public String mainPage(Model model) {
		
		// 의존성 주입(DI) 확인 (진짜 service 객체 들어옴!)
		log.debug("service : "+ service);
		
		// Service 메서드 호출 후 결과 반환 받기
		Map<String, Object> map = service.selectAll();
		
		// map에 담긴 내용 추출
		List<Todo> todoList= (List<Todo>)map.get("todoList"); 
			// 다운캐스팅 해줘야 한다
			// Map에다 Value를 Object로 넣어놨음
		int completeCount = (int)map.get("completeCount");
		
		// Model : 값 전달용 객체 (request scope 기본) + session 변환 가능
		model.addAttribute("todoList",todoList);
		model.addAttribute("completeCount",completeCount);
		
		
		
		
		// classpath:/templates/
		// common/main
		// .html
		// -> 이쪽으로 forward
		// classpath:/templates/common/main.html
		return "common/main";
	}
}
