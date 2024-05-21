package edu.kh.project.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component  // bean으로 등록
// @Aspect 	// 공통 관심사가 작성된 클래스임을 명시 (AOP 동작용 클래스)
@Slf4j		// log를 찍을 수 있는 객체(Logger) 생성 코드를 추가 (Lombok 제공)
public class TestAspect {

	/* AOP -> 횡단 관점의 기술
	   공통되는 부분 : 관심사 = Aspect*/
	
	
	
	// advice : 끼워 넣을 코드 (메서드)
	// Pointcut : 실제로 Advice를 적용할 JoinPoint (지점)
	
	// <Pointcut 작성 방법>
	// execution( [접근제한자] 리턴타입 클래스명 메서드명 ([파라미터]) )   []는 생략 가능
	// + 클래스명은 패키지명부터 모두 작성
	
	// 주요 어노테이션
	// - @Aspect : Aspect를 정의하는데 사용되는 어노테이션으로, 클래스 상단에 작성함.
	// - @Before : 대상 메서드 실행 전에 Advice를 실행함
	// - @After : 대상 메서드 실행 후에 Advice를 실행함
	// - @Around : 대상 메서드 실행 전/후로 Advice를 실행함 (@Before + @After)
	
	// 실행하는 것 / 반환 X
	
				// 접근제한자 생략   *부터는 return type  
				// 모든 리턴타입   edu.kh.project랑 하위 패키지에 속하는 모든 대상
				//  .. : 0개 이상 하위 패키지
				// *Controller*  : Controller라는 문자열을 포함한 모든 클래스 대상
				// .*  : 모든 메서드    클래스명.* ( *Controller*.* )
				//(..) : 0개 이상의 파라미터를 가질 수 있음 => 앞의 메서드의 파라미터가 0개 이상일 수 있음
	
	
	// * : 모든
	// execution(* edu.kh.project..*Controller*.*(..))
	// execution : 메서드 실행 지점을 가리키는 키워드
	// * : 모든 리턴 타입을 나타냄
	// edu.kh.project : 패키지명을 나타냄. edu.kh.project패키지와 하위 패키지에 속하는 것을 대상으로 함
	// .. : 0개 이상의 하위 패키지를 나타냄
	// *Controller* : 이름에 "Controller"라는 문자열을 포함하는 모든 클래스를 대상으로 함
	// .* : 모든 메서드를 나타냄
	// (..) : 0개 이상의 파라미터를 나타냄
	
	// @Before(포인트컷)
	@Before("execution(* edu.kh.project..*Controller*.*(..))")
	public void testAdvice() {
		log.info("=======testAdvice() 수행됨 =========");
	}
	
	
	@After("execution(* edu.kh.project..*Controller*.*(..))")
	public void controllerEnd(JoinPoint jp) {
		// JoinPoint : AOP 기능이 적용된 대상
		
		// AOP가 적용된 클래스 이름 얻어오기
		String className = jp.getTarget().getClass().getSimpleName();  // ex) MainController
		
		// 실행된 컨트롤러 메서드 이름을 얻어오기
		String methodName = jp.getSignature().getName(); // 메서드 명이 나옴
		
		log.info("===================={}.{} 수행 완료===============",className, methodName);
						// log.info에 {}넣으면 뒤에 값이 넣어져서 나옴
		
		
		
	}
	
	
	
}
