// All custom JS used for Chat Web App

// Makes chat window's scroll go to bottom element
function scrollToBottom() {
    let element = document.getElementById("chat-window");
    element.scrollTop = element.scrollHeight;
}

function showModal() {
    $("#modal").modal();
}
