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


// ===============================
const fastLogin = document.getElementById("fastLogin");

fetch("/member/fastLogin")
.then(resp =>resp.json())
.then(result => {
console.log(result);

    for(let i=0; i< result.length; i++){
            const a = document.createElement("a");
        
            a.innerText = result[i].memberEmail;
            fastLogin.append(a);
            a.classList.add("fastloginbtn");
            a.href="/member/fastlogin2?memberEmail="+result[i].memberEmail;

    } 
   
})


// ===================================== 강사님 예시

const quickLoginBtns = document.querySelectorAll(".quick-login");

quickLoginBtns.forEach( (item,index) => {
    // item : 현재 반복 시 꺼내온 객체
    // index : 현재 반복 중인 인덱스

    // quickLoginBtns 요소인 button 태그 하나씩 꺼내서 이벤트 리스너 추가
    item.addEventListener("click", ()=> {

        const email = item.innerText; // 버튼에 작성된 이메일 얻어오기
        
        location.href ="/member/quickLogin?memberEmail=" +email;

    });


})


//==================================================

/* 회원 목록 조회 (비동기) */

// 조회버튼
const selectMemberList = document.querySelector("#selectMemberList");

// tbody
const memberList = document.querySelector("#memberList");

/*
//-------------------------


    td 요소를 만들고 text 추가 후 반환
    const createTd = (text) =>{
        const td = document.createElement("td");
        td.innerText = text;
        return td;
    }

//----------------------
*/

// 조회 버튼 클릭 시
selectMemberList.addEventListener("click", ()=> {

    // 1) 비동기로 회원 목록 조회
    // (포함될 회원 정보 : 회원 번호, 회원 이메일, 회원 닉네임, 탈퇴여부)
    fetch("/member/selectMemberList")
    .then(resp => resp.json()) // JSON.parse(resp)
    .then(list => {
        // list  바로 이용 -> JS 객체 배열

        console.log(list);
        // 이전 내용 삭제
        memberList.innerHTML ="";

/*
        // tbody에 들어갈 요소를 만들고 값 세팅 후 추가
        list.forEach( (member,index)=> {
            // member : 현재 반복 접근 중인 요소
            // index : 현재 접근 중인 인덱스

            // tr 만들어서 그 안에 td 만들고, append 후 
            // tr을 tbody에 append
            const keyList = ['memberNo','memberEmail','memberNickname','memberDelFl'];
                             // list 안에 들어가 있는 key값들
            const tr = document.createElement("tr");
            
            keyList.forEach(key => tr.append(createTd(member[key])))

            // tbody에 자식으로 tr을 추가
            memberList.append(tr);
        });
*/


        for(let member of list ){
            const tr = document.createElement("tr");

            const arr = ['memberNo','memberEmail','memberNickname','memberDelFl'];

            for(let key of arr){

                const td = document.createElement("td");

                td.innerText = member[key];
                tr.append(td);

            }
            memberList.append(tr);
        }

    })

})

// =============== 특정 회원 비밀번호 초기화(Ajax)==============


const resetPw = document.getElementById("resetPw");

resetPw.addEventListener("click", ()=> {

    // 입력 받은 회원 번호 얻어오기
    const memberNo = document.getElementById("resetMemberNo").value;
   
    if(memberNo.trim().length == 0){
        alert("회원 번호를 입력해주세요");
        return;
    }


    fetch("/member/resetPw",{
        method : "POST",
        headers : {"Content-type" : "application/json"}, 
        // body에 넣는 값이 하나여도 header는 application/json작성해야함
        body: memberNo
    })
    .then(resp => resp.text())
    .then(result => {
        
        // result == 컨트롤러로부터 반환받아 TEXT로 파싱한 값
        // "1" "0"

        if(result > 0){
            alert("변경되었습니다.");
        } else {
            alert("변경 실패");
        }
    });

})


// ========================특정 회원(회원 번호) 탈퇴 복구 (Ajax)====================

const restorationBtn = document.getElementById("restorationBtn");

restorationBtn.addEventListener("click", () => {
    const memberNo = document.getElementById("restorationMemberNo").value;

    fetch("/member/restoration",{
        method : "POST",
        headers : {"Content-type" : "application/json"},
        body : memberNo
    })
    .then(resp => resp.text())
    .then(result =>{

        console.log(result);

        if(result > 0){
            alert("회원 탈퇴가 복구되었습니다.");
        } else {
            alert("탈퇴 복구 실패");
        }
    });
})









