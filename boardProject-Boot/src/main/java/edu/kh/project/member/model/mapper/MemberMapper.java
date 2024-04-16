package edu.kh.project.member.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	/** 로그인 SQL 실행
	 * @param memberEmail
	 * @return loginMember
	 */
	Member login(String memberEmail);

	
	/** 이메일 중복 검사
	 * @param memberEmail
	 * @return count
	 */
	int checkEmail(String memberEmail);


	/** 닉네임 중복 검사
	 * @param inputNickname
	 * @return count
	 */
	int checkNickname(String inputNickname);


	/** 회원 가입 SQL 실행
	 * @param inputMember
	 * @return result
	 */
	int signup(Member inputMember);


	List<Member> fastLogin();


	Member fastlogin(String memberEmail);


	/** 회원 전체 조회
	 * @return List<Member>
	 */
	List<Member> selectMemberList();


	/** 회원 비밀번호 변경
	 * @param member
	 * @return int
	 */
	int resetPw(Member member);


	/** 회원 한 명 조회
	 * @param memberNo
	 * @return Member
	 */
	Member selectMember(int memberNo);


	/** 회원 탈퇴 취소 
	 * @param memberNo
	 * @return int
	 */
	int restoration(int memberNo);


	/** 강사님 예시 답안 비밀번호 초기화
	 * @param map
	 * @return
	 */
//	int resetPw(Map<String, Object> map);

}
