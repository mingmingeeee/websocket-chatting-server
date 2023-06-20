// 임시 로그인 시스템
let username = prompt("아이디를 입력하세요");
let roomNum = prompt("채팅방 번호를 입력하세요");

document.querySelector("#username").innerHTML = username;

const eventSource = new EventSource(`http://localhost:8080/chat/roomNum/${roomNum}`);

// SSE 연결하기 
eventSource.onmessage = (event) => {
    console.log(1, event);
    const data = JSON.parse(event.data);

    if(data.sender === username) { // 로그인한 유저가 보낸 메시지
        // 파란 박스 (오른쪽)
        initMyMessage(data);
        console.log(username);
    } else {
        // 회색 박스 (왼쪽)
        initYourMessage(data);
    }

}

// 파란 박스 계속 초기화 (event 걸려있기 때문에)
// 최초 초기화될 때 1번방 3건이 있으면 3건 다 가져옴
// addMessage() 함수 호출시 DB에 insert 되고 그 데이터가 자동으로 흘러들어옴 (SSE)
function initMyMessage(data) {
    let chatBox = document.querySelector("#chat-box");


    let chatOutgoingBox = document.createElement("div");
    chatOutgoingBox.className = "outgoing_msg";

    chatOutgoingBox.innerHTML = getSendMsgBox(data);
    chatBox.append(chatOutgoingBox);

}

// 회색 박스 계속 초기화 (event 걸려있기 때문에)
function initYourMessage(data) {
    let chatBox = document.querySelector("#chat-box");


    let chatIncomingBox = document.createElement("div");
    chatIncomingBox.className = "received_msg";

    chatIncomingBox.innerHTML = getReceiveMsgBox(data);
    chatBox.append(chatIncomingBox);

}


// AJAX 채팅 메시지 전송 
// db에 insert만 하면 됨
async function addMessage() {
    let msgInput = document.querySelector("#chat-outgoing-msg");

    let chat = {
        sender: username,
        roomNum: roomNum,
        msg: msgInput.value
    };

    fetch("http://localhost:8080/chat", {
        method: "post",
        body: JSON.stringify(chat), // JS -> JSON
        headers: {
            "Content-Type":"application/json; charset=utf-8"
        }
    });

    msgInput.value = "";
}

// 파란 박스 만들기
function getSendMsgBox(data) {
    return `
    <div class="sent_msg">
    <p>${data.msg}</p>
    <span class="time_date"> ${data.createdAt} / ${data.sender} </span>
  </div>
    `;
}

// 회색 박스 만들기
function getReceiveMsgBox(data) {
    return `
    <div class="received_withd_msg">
    <p>${data.msg}</p>
    <span class="time_date"> ${data.createdAt} / ${data.sender} </span>
  </div>
    `;
}

// 버튼 클릭시 메시지 전송
document.querySelector("#chat-send").addEventListener("click", () => {
    addMessage()
});

// 엔터시 메시지 전송
document.querySelector("#chat-outgoing-msg").addEventListener("keydown", (e) => {
    if(e.keyCode == 13) {
        addMessage()
    }
});