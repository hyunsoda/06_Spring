package edu.kh.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.mapper.MemberMapper;

@Transactional(rollbackFor=Exception.class) // 해당 클래스 메서드 종료 시까지 
				// - 예외(Runtime Exception)가 발생하지 않으면 commit
				//- 예외(Runtime Exception)가 발생하면 rollback
				// rollbackFor=Exception.class = 모든 예외로 변경
@Service // 비즈니스 로직 처리 역할 명시 + Bean 등록
public class MemberServiceImpl implements MemberService{
	
	// 등록된 bean중에서 같은 타입 또는 상속관계인 bean을
	// 자동으로 의존성 주입 (DI)
	@Autowired
	private MemberMapper mapper;

	// BCrypt 암호화 객체 의존성 주입 (SecurityConfig 참고)
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	// 로그인 서비스
	@Override
	public Member login(Member inputMember) {
		
		// memberEmail : user01@kh.or.kr
		// memberPw : pass01!
		
		
		return null;
	}
	
	
}

/* Bcrypt 암호화 (Spring Security 제공)
 * 
 * - 입력된 문자열 (비밀번호)에 salt를 추가한 후 암호화
 * 
 * ex) A 회원 : 1234   -> $12!asdfg
 * ex) B 회원 : 1234   -> $12!dfghj
 *  		똑같은 평문이어도 암호화된 게 다르다
 *  
 *  - 비밀번호 확인 방법
 *  	-> BcryptPasswordEncoder.matches(평문 비밀번호, 암호화된 비밀번호)
 *  	-> 평문 비밀번호와 암호화된 비밀번호가 같은 경우 true 아니면 false 반환
 *  
 *  * 로그인 / 비밀번호 변경 / 탈퇴 등 비밀번호가 입력되는 경우 
 *    DB에 저장된 암호화된 비밀번호를 조회해서
 *    matches() 메서드로 비교해야 한다!
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  (참고)
 *  sha 방식 암호화
 * ex) A 회원 : 1234   -> 암호화 : abcd
 * ex) B 회원 : 1234   -> 암호화 : abcd (암호화 시 변경된 내용이 같음)
 * */

