/* 요소 얻어와서 변수에 저장 */
const totalCount = document.querySelector("#totalCount");
const completeCount = document.querySelector("#completeCount");
const reloadBtn = document.querySelector("#reloadBtn");

// 할 일 추가 관련 요소
const todoTitle = document.querySelector("#todoTitle");
const todoContent = document.querySelector("#todoContent");
const addBtn = document.querySelector("#addBtn");

// 할 일 목록 조회 관련 요소
const tbody = document.querySelector("#tbody");

// 전체 Todo 개수 조회 및 출력하는 함수 정의
function getTotalCount(){

    // 비동기로 서버(DB)에서 전체 Todo 개수 조회하는
    // fetch() API 코드 작성
    // (fetch : 가지고 오다)


    // mapping해주는 controller로 요청이 간다
    fetch("/ajax/totalCount") // 비동기 요청 수행-> Promise 객체 반환   기본값 GET
    .then(response => { // response : 매개변수 이름 
        // response : 비동기 요청에 대한 응답이 담긴 객체

        console.log("response : ", response);
        // response.text() : 응답 데이터를 문자열/숫자 형태로 변환한
        //                   결과를 가지는 Promise 객체 반환
        return response.text();

    })
    // 두 번째 then의 매개변수 (result)
    // == 첫 번째 then에서 반환된 Promise 객체의 PromiseResult 값
    .then(result => {
        // result 매개 변수 == Controller 메서드에서 반환된 진짜 값
        console.log("result : ", result);

        // #totalCount 요소의 내용으 result로 변경
        totalCount.innerText = result;
    });


}
getTotalCount(); // 함수 호출   


// completeCount 값 비동기 통신으로 얻어와서 화면 출력
function getCompleteCount(){

    // fetch() : 비동기로 요청해서 결과 데이터 가져오기
    
    // 첫 번째 then의 response : 
    // - 응답 결과, 요청 주소, 응답 데이터 등이 담겨있음

    // response.text() : 응답 데이터를 text 형태로 변환 (단일 형태만 가능)

    // 두 번째 then의 result
    // - 첫 번째 then에서 text로 변환된 응답 데이터
    fetch("/ajax/completeCount")
    .then(response => {
        return response.text();

    })
    .then(result => {

        // completeCount 요소에 내용으로 result 값 출력
        completeCount.innerText = result;
    })

}


// 새로고침 버튼이 클릭 되었을 때
reloadBtn.addEventListener("click", ()=> {
    getTotalCount();  // 비동기로 전체 할 일 개수 조회
    getCompleteCount(); // 비동기로 완료된 할 일 개수 조회
});
// 위에 addEventListner해도 밑에 함수 호출 삭제하면 안됨 
//      페이지 리로드시 작동 안 함

// ------------------------------------

// 할 일 추가 버튼 클릭 시 동작
addBtn.addEventListener("click", ()=>{

    // 비동기로 할 일 추가하는 fetch() 코드 작성
    // - 요청 주소 : "/ajax/add"
    // - 데이터 전달 방식 (method) : "POST" 방식


    /* JS에서 Java로 값 보내기 */

    // 파라미터를 저장한 JS객체
    const param = {
        //  Key     : Value
        "todoTitle" : todoTitle.value,
        "todoContent" : todoContent.value
    };
    // JS 객체 그대로는 Java에서 사용할 수 없다
    // JS와 Java에서 모두 사용할 수 있는 JSON 형태로 변환 후 가져가야 함

    fetch("/ajax/add", {
        // key  :  value 형태로 옵션 보내야함
        method : "POST", // POST 방식 요청
        headers : {"Content-Type" : "application/json"}, // 요청 데이터의 형식을 JSON으로 지정
        body : JSON.stringify(param)  // param 객체를 JSON (String형태) 형태로 변환
    })
    .then( resp => resp.text()) // 반환된 응답값을 text로 변환
    // 매개변수 하나면 소괄호 안 써도 됨
    .then(temp => { // 첫 번째 then에서 반환된 값 중 변환된 text를 temp에 저장

        if(temp > 0){ // 성공
            alert("추가 성공 !!!");

            // 추가 성공한 제목, 내용 지우기
            todoTitle.value = "";
            todoContent.value = "";

            // 할 일이 추가되었기 때문에 전체 Todo 개수 다시 조회
            getTotalCount();

            // 할 일 목록 다시 조회
            // selectTodoList();
        } else { // 실패
            alert("추가 실패...");
        }
    }); 

});

// -------------------------------------------------------------------------

// 비동기로 할 일 목록을 조회하는 함수
const selectTodoList = () => {

    fetch("/ajax/selectList")
    .then(resp => resp.text()) // 응답결과를 text로 변환
    .then(result => {
        console.log(result);
        console.log(typeof result); // 객체가 아닌 문자열 형태

        // 문자열은 가공은 할 수 있지만 너무 힘들다.
        // => JSON.parse(JSON데이터) 이용

        // JSON.parse(JSON데이터) : string -> object
        // - string 형태의 JSON 데이터를 JS object 타입으로 변환

        // JSON.stringify(JS Object) : object -> string
        // - JS Object 타입을 string 형태의 JSON 데이터로 변환
        const todoList = JSON.parse(result);

        console.log(todoList);

        //------------------------------------

        // #tbody에 tr/td 요소를 생성해서 내용 추가
        for(let todo of todoList){ // JS에서 배열에 순차접근하는 향상된 for문
                                    // 객체 순차접근 : for in
            // tr 태그 생성
            const tr = document.createElement("tr");

            const arr = ['todoNo','todoTitle','complete','regDate'];

            for(let key of arr){
                const td = document.createElement("td");

                // 제목인 경우
                if(key === 'todoTitle'){
                    const a = document.createElement("a"); // a태그 생성
                    a.innerText = todo[key];  // key === todoTitle이니 todoTitle이 들어올 것
                    // 제목을 a태그 내용으로 대입
                    a.href = "/ajax/detail?todoNo=" + todo.todoNo;
                    td.append(a);
                    tr.append(td);

                }
            }
        }
    })
};







getTotalCount();
getCompleteCount(); // 함수 호출
selectTodoList();





