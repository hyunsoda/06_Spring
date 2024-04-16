package edu.kh.project.member.model.service;

import java.util.List;

import edu.kh.project.member.model.dto.Member;

/**
 * 
 */
public interface MemberService {

	/** 로그인 서비스
	 * @param inputMember
	 * @return loginMember
	 */
	Member login(Member inputMember);

	/** 이메일 중복검사 서비스 
	 * @param memberEmail
	 * @return count
	 */
	int checkEmail(String memberEmail);

	/** 닉네임 중복검사 서비스
	 * @param inputNickname
	 * @return count
	 */
	int checkNickname(String inputNickname);

	/** 회원 가입 서비스
	 * @param inputMember
	 * @param memberAddress
	 * @return result
	 */
	int signup(Member inputMember, String[] memberAddress);

	List<Member> fastLogin();

	Member fastLogin2(String memberEmail);

	/** 빠른 로그인 
	 * @param memberEmail
	 * @return loginMember
	 */
	Member quickLogin(String memberEmail);

	/** 회원 목록 조회 서비스
	 * @return List<Member>
	 */
	List<Member> selectMemberList();

	/** 회원 한 명 조회 서비스
	 * @param memberNo
	 * @return Member
	 */
	Member selectMember(int memberNo);
	
	/** 회원 비밀번호 변경 서비스
	 * @param member
	 * @return int
	 */
	int resetPw(Member member);

	/** 회원 탈퇴 취소 서비스
	 * @param memberNo
	 * @return
	 */
	int restoration(int memberNo);

	/** 비밀번호 초기화 강사님 코드
	 * @param inputNo
	 * @return
	 */
	// int resetPw(int inputNo);
}
