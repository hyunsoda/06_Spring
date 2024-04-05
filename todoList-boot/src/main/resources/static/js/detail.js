// 목록으로 버튼 동작
const goToList = document.querySelector("#goToList");

goToList.addEventListener("click",()=> {
    location.href = "/"; // 메인 페이지로 요청
    // location.href 요청 보내기
});


// 완료 여부 변경 버튼 동작
const completeBtn = document.querySelector(".complete-btn");

completeBtn.addEventListener("click", (e)=>{
    // e : event 객체
    // 클릭했을 때 일어날 정보들에 대한 객체
    const todoNo = e.target.dataset.todoNo;   //요소.dataset을 이용해서 해당 값을 얻어옴
                                             // todoNo안에 우리가 가져온 todoNo가 들어옴
    //console.log(todoNo);

    // Y <-> N 변경

    let complete = e.target.innerText;  // complete는 변경될 수 있으니 let으로 얻어옴
         // 기존 완료 여부 값 얻어오기
    complete = (complete === 'Y') ? 'N' : 'Y';
           // == : 같다 === : 일치연산자 (타입까지 같은 지)

    // 완료 여부 수정 요청하기
    location.href = `/todo/changeComplete?todoNo=${todoNo}&complete=${complete}`;
            // 백틱사용 (${}사용해야하기 때문에)
    
});


// --------------------------------------------------------------------------

// 수정 버튼 클릭 시 
const updateBtn = document.querySelector("#updateBtn");

updateBtn.addEventListener("click",e =>{
                // 매개변수 하나만 있으면 화살표 함수 쓸 때 괄호 없어도 됨
    
    // data-todo-no 얻어오기
    const todoNo = e.target.dataset.todoNo;

    location.href = `/todo/update?todoNo=${todoNo}`;
})


// 삭제 버튼 클릭 시 
const deleteBtn = document.querySelector("#deleteBtn");

deleteBtn.addEventListener("click",e => {

    // confirm 창 확인 취소 떠서 확인 누르면 삭제
    if(confirm("삭제하시겠습니까?")){ // 확인 누르면 true반환
        location.href = `/todo/delete?todoNo=${e.target.dataset.todoNo}`;
    }

    
})

