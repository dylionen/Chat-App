let $chatHistory;
let $button;
let $textarea;
let $chatHistoryList;

function init() {
    cacheDOM();
    bindEvents();
}

function bindEvents() {
    $button.on('click', addMessage.bind(this));
    $textarea.on('keyup', addMessageEnter.bind(this));
}

function cacheDOM() {
    $chatHistory = $('.chat-history');
    $button = $('#sendBtn');
    $textarea = $('#message-to-send');
    $chatHistoryList = $chatHistory.find('ul');
}

function render(message, userName, image) {
    scrollToBottom();
    var templateResponse = Handlebars.compile($("#message-response-template").html());
    if(image != null){
        templateResponse = Handlebars.compile($("#message-response-template_image").html());
        var img = new Image();
        img.src = image;
       // document.body.appendChild(img);
    }
    // responses

    var contextResponse = {
        response: message,
        time: getCurrentTime(),
        userName: userName,
        image: image
    };

    setTimeout(function () {
        $chatHistoryList.append(templateResponse(contextResponse));
        scrollToBottom();
    }.bind(this), 1500);
}

function sendMessage(message) {
    let username = userName;
    console.log(username)
    sendMsg(username, message);
    scrollToBottom();
    if (message.trim() !== '') {
        var template = Handlebars.compile($("#message-template").html());
        if(FILE != null){
            template = Handlebars.compile($("#message-template_image").html());
        }
        var context = {
            messageOutput: message,
            time: getCurrentTime(),
            toUserName: selectedUser,
            image: FILE
        };
        FILE = null;

        $chatHistoryList.append(template(context));
        scrollToBottom();
        $textarea.val('');
    }
}

function scrollToBottom() {
    $chatHistory.scrollTop($chatHistory[0].scrollHeight);
}

function getCurrentTime() {
    return new Date().toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
}

function addMessage() {
    sendMessage($textarea.val());
}

function addMessageEnter(event) {
    // enter was pressed
    if (event.keyCode === 13) {
        addMessage();
    }
}

init();