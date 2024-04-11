// console.log("main.js loaded.");

// 쿠키에서 key가 일치하는 value 얻어오기 함수

// 쿠키는 "K=V; K=V; ..." 형식
            // 쿠키도 하나의 공간

// 배열.map(함수) : 배열의 각 요소를 이용해 함수 수행 후 
//                  결과 값으로 새로운 배열을 만들어서 반환


const getCookie = (key) => {

    //  document.cookie="test"+ "=" + "유저일";  // JS로 쿠키 세팅 테스트

    const cookies = document.cookie; // cookie 꺼내오기
    // "K=V; K=V;"

    // console.log(cookies);  // 세팅한 값만 넘어옴

    // cookies 문자열을 배열 형태로 변환
    const cookieList = cookies.split("; ")  // ["K=V", "K=V"]   세미콜론 +space까지 넣어줘야 함
                        .map(el => el.split("="));   // ["K","V"] .. 배열로 반환  "K=V"가 하나의 el (el은 임의로 지정한 이름)

    // console.log(cookieList);

    // 배열 -> 객체로 변환 (그래야 다루기 쉽다)


    const obj = {}; // 비어있는 객체 선언
    // cookieList for문 돌면서  채워주기
    
    for(let i=0; i<cookieList.length; i++) {
        const k = cookieList[i][0]; // key 값
        const v = cookieList[i][1]; // value 값
        obj[k] = v; // 객체에 추가   없는 거는 만들어지고 있는 거는 덮어쓰기 됨
    }
    // console.log(obj);

    return obj[key]; // 매개변수로 전달받은 key와
                    // obj 객체에 저장된 key가 일치하는 요소의 value 값 반환
}

 // console.log(getCookie("saveId")); 

const loginEmail = document.querySelector("#loginForm input[name='memberEmail']");
                      // 아이디가 loginForm인 element의 자식 중 input태그의 name 속성 값이 memberEmail인 것

// 로그인 안된 상태인 경우에 수행
if(loginEmail != null){ // 로그인 창의 이메일 입력 부분이 화면에 있을 때

    // 쿠키 중 key 값이 "saveId"인 요소의 value 얻어오기
    const saveId = getCookie("saveId"); // undefined 또는 이메일 

    // saveID값이 있을 경우
    if(saveId != undefined) {
        loginEmail.value = saveId; // 쿠키에서 얻어온 값을 input에 value로 세팅

        // 아이디 저장 체크박스에 체크 해두기
        document.querySelector("input[name='saveId']").checked = true;
    }
};


// 이메일, 비밀번호 미작성 시 로그인 막기
const loginForm = document.querySelector("#loginForm");

const loginPw = document.querySelector("#loginForm input[name='memberPw']");

// #loginForm이 화면에 존재할 때 (== 로그인 상태 아닐 때)
if(loginForm != null) {

    // 제출 이벤트 발생 시 
    loginForm.addEventListener("submit",e => {

        // 이메일 미작성
        if(loginEmail.value.trim().length === 0 ){
            alert("이메일을 작성해주세요!");
            e.preventDefault();  // 기본 이벤트(제출)을 막음
            loginEmail.focus();  // 초점 이동
            return;
        }

        // 비밀번호 미작성
        if(loginPw.value.trim().length === 0 ){
            alert("비밀번호를 작성해주세요!");
            e.preventDefault();  // 기본 이벤트(제출)을 막음
            loginPw.focus();  // 초점 이동
            return;
        }        

    });
}



