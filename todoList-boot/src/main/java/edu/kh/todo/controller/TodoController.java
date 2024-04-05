package edu.kh.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;



@Controller
@RequestMapping("todo")  // "/todo"로 시작하는 모든 요청 매핑
public class TodoController {
	
	@Autowired // 같은 타입 / 상속 관계인 Bean을 자동으로 연결해서 주입 (DI)
	private TodoService service;
	
	
	@PostMapping("add") //   "/todo/add" Post 방식 요청 매핑
	public String addTodo(
			@RequestParam("todoTitle")String todoTitle,
			@RequestParam("todoContent") String todoContent,
			RedirectAttributes ra
			) {
		
		// RedirectAttributes : 리다이렉트 시 값을 1회성으로 전달하는 객체
		// RedirectAttributes.addFlashAttribute("key", value) 형식으로 잠깐 세션에 속성을 추가
		
		// [원리]
		// 응답 전 : ra는 request scope
		// redirect 중 : session scope로 이동
		// 응답 후 (redirect 마친 후) : request scope로 복귀
		
		
		// 서비스 메서드 호출 후 결과 반환 받기 
		int result = service.addTodo(todoTitle, todoContent);
		
		// 삽입 결과에 따라 message 값 지정
		String message = null;
		
		if(result > 0) message = "할 일 추가 성공!";
		else 		   message = "할 일 추가 실패..ㅠ";
		
		// redirect 시 request scope 소멸
		// 리다이렉트 후 1회성으로 사용할 데이터를 속성으로 추가
		ra.addFlashAttribute("message", message); // 리다이렉트 시 사용하는 객체이다
		
		return "redirect:/"; // 메인페이지 재요청
		// '/'라는 요청을 재요청 (HTML주소가 아닌 요청 주소임! '/'라는 주소를 매핑해주는 곳으로 감)
		
	}
	
	// 상세 조회
	@GetMapping("detail")
	public String todoDetail(
			@RequestParam("todoNo") int todoNo,
			Model model,
			RedirectAttributes ra) {
		
		Todo todo = service.todoDetail(todoNo);
		
		String path = null;
		
		if(todo != null) { // 조회 결과가 있을 경우
			
			// forward : templates/todo/detail.html
			path = "todo/detail";
			
			// request scope 값 세팅
			model.addAttribute("todo", todo);
			
			
		} else { // 조회 결과 없을 경우
			
			path = "redirect:/"; // 메인 페이지로 리다이렉트
			
			// RedirectAttributes :
			// - 리다이렉트 시 데이터를 request scope -> (잠시) session scope로 
			// 전달할 수 있는 객체 (응답 후 request scope로 복귀)
			ra.addFlashAttribute("message", "해당 할 일이 존재하지 않습니다");
		}
		
		
		return path;
	}
	
	
	
	/** 완료 여부 변경
	 * @param todo : 커맨드 객체 (@ModelAttribute 생략)
	 * 				- todoNo, complete 두 필드가 세팅된 상태
	 * @param ra
	 * @return redirect:detail?todoNo=할 일 번호(상대경로)
	 */
	@GetMapping("changeComplete")   // JS에서 location통해서 보낸 요청은 Get 요청
	public String changeComplete(Todo todo, RedirectAttributes ra) {  // @ModelAttribute 생략 가능
		
		// 변경 서비스 호출
		int result = service.changeComplete(todo);
		
		// 변경 성공 시 : "변경 성공 !!"
		// 변경 실패 시 : "변경 실패 !!"
		
		String message = null;
		
		if(result > 0) message="변경 성공!!";
		else		message="변경 실패..";
		
		ra.addFlashAttribute("message", message);
		
		// 현재 요청 주소 : /todo/changeComplete
		// 응답 주소 	  : /todo/detail
		
		return "redirect:detail?todoNo=" + todo.getTodoNo(); // 상대경로 
	}
	
	
	/** 수정 화면 전환
	 * @return 
	 */
	@GetMapping("update")
	public String todoUpdate(@RequestParam("todoNo") int todoNo,
						Model model) {
		
		// 이전에 작성한 내용이 들어있어야 함
			// => 상세 조회 서비스 호출해야함
		// 상세 조회 서비스 호출 -> 수정화면에 출력할 이전 내용으로 사용
		Todo todo = service.todoDetail(todoNo);
		
		model.addAttribute("todo", todo);
		
		
		return "todo/update";
	}
	
	
	/** 할 일 수정
	 * @param todo : 커맨드 객체 (전달 받은 파라미터가 자동으로 DTO의 필드에 세팅된 객체)
	 * @param ra
	 * @return
	 */
	@PostMapping("update")
	public String todoUpdate(Todo todo, RedirectAttributes ra) {
		
		// 수정 서비스 호출
		int result = service.todoUpdate(todo);
		
		String path = "redirect:";
		String message = null;
		
		if(result > 0) {
			// 상세 조회로 리다이렉트
			path += "/todo/detail?todoNo=" + todo.getTodoNo();
			message = "수정 성공!!";
		} else {
			// 다시 수정 화면으로 리다이렉트
			path += "/todo/update/todoNo=" + todo.getTodoNo();
			message = "수정 실패..";
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
	}
	
	
	/** 할 일 삭제
	 * @param todoNo : 삭제할 할 일 번호
	 * @param ra
	 * @return 성공 : 메인페이지 / 실패 : 상세페이지
	 */
	@GetMapping("delete")
	public String todoDelete(@RequestParam("todoNo") int todoNo,
							 RedirectAttributes ra) {
		
		int result = service.todoDelete(todoNo);
		String path=null;
		String message = null;
		
		if(result > 0 ) { // 성공
			
			path = "/";
			message = "삭제 성공";
			
		} else { // 실패
			
			message = "삭제 실패";
			path = "/todo/detail?todoNo=" + todoNo;
			
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
}
