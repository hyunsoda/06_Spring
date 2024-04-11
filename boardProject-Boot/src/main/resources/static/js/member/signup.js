// **** 회원 가입 유효성 검사

// 필수 입력 항목의 유효성 검사 여부를 체크하기 위한 객체
// - true  == 해당 항목은 유효한 형식으로 작성됨
// - false == 해당 항목은 유효하지 않은 형식으로 작성됨

const checkObj = {
    "memberEmail" : false,
    "memberPw" : false,
    "memberPwConfirm" : false,
    "memberNickname" : false,
    "memberTel" : false,
    "authKey" : false
};

// --------------------------------------

/* 이메일 유효성 검사 */

// 1) 이메일 유효성 검사에 사용될 요소 얻어오기
const memberEmail = document.querySelector("#memberEmail");
const emailMessage = document.querySelector("#emailMessage");

// 2) 이메일이 입력(input , keyup..) 될 때마다 유효성 검사 수행
memberEmail.addEventListener("input",(e) => {

    // 이메일 인증 후 이메일이 변경된 경우
    //  나중에 처리
    
    // 작성된 이메일 값 얻어오기
    const inputEmail = e.target.value;

    // console.log(inputEmail);

    // 3) 입력된 이메일이 없을 경우
    if(inputEmail.trim().length == 0){
        emailMessage.innerText = "메일을 받을 수 있는 이메일을 입력해주세요.";

        // 메시지에 색상을 추가하는 클래스 모두 제거
        emailMessage.classList.remove('confirm','error');

        // 이메일 유효성 검사 여부를 false 변경
        checkObj.memberEmail = false;

        // 잘못 입력한 띄어쓰기가 있을 경우 없앰
        memberEmail.value ="";

        return;
    }

    // 4) 입력된 이메일이 있을 경우 정규식 검사
    //  (알맞은 형태로 작성했는지 검사)
    const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    // 입력 받은 이메일이 정규식과 일치하지 않는 경우
    // (알맞은 이메일 형태가 아닌 경우)
    if( !regExp.test(inputEmail)){
        emailMessage.innerText="알맞은 이메일 형식으로 작성해주세요.";
        emailMessage.classList.add('error'); // 글자를 빨간색으로 변경
        emailMessage.classList.remove('confirm'); // 초록색 글자 제거
        checkObj.memberEmail = false; // 유효하지 않은 이메일임을 기록
        return;
    }

    // 5) 유효한 이메일 형식인 경우 중복 검사 수행
    // 비동기 (ajax)
    fetch("/member/checkEmail?memberEmail=" + inputEmail)





});















