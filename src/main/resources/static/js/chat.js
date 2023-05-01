const url = 'http://localhost:8080';
let stompClient;
let selectedUser;
let newMessages = new Map();

let OLD_USERS = "";

function connectToChat(userName) {
    console.log("connecting to chat...")
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + userName, function (response) {
            let data = JSON.parse(response.body);
            if (selectedUser === data.fromLogin) {
                render(data.message, data.fromLogin, data.image);
            } else {
                newMessages.set(data.fromLogin, data.message);
                $('#userNameAppender_' + data.fromLogin).append('<span id="newMessage_' + data.fromLogin + '" style="color: red">+1</span>');
            }
        });
    });
}

function sendMsg(from, text, image) {
    stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
        fromLogin: from,
        message: text,
        image: FILE
    }));
}

function registration(userName) {
    $.get(url + "/registration/" + userName, function (response) {
        connectToChat(userName);
    }).fail(function (error) {
        if (error.status === 400) {
            alert("Login is already busy!")
        }
    })
}

function selectUser(userName) {
    console.log("selecting users: " + userName);
    $('#friendAvatar').attr('src', $('#userNameAvatar_' + userName).attr('src'));
    selectedUser = userName;
    let isNew = document.getElementById("newMessage_" + userName) !== null;
    if (isNew) {
        let element = document.getElementById("newMessage_" + userName);
        element.parentNode.removeChild(element);
        render(newMessages.get(userName), userName, null);
    }
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with ' + userName);
}

function fetchAll() {
    $.get(url + "/fetchAllUsers", function (response) {
        let users = response;
        let usersTemplateHTML = "";
        console.log(users);

        if (JSON.stringify(users) === JSON.stringify(OLD_USERS)) {
            return;
        }

        OLD_USERS = users;
        for (let i = 0; i < users.length; i++) {
            if (users[i].userName == userName) {
                continue;
            }

            let imgSrc = "/png/user-image.png"
            if (users[i].avatar != null) {
                imgSrc = users[i].avatar;
            }

            usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\'' + users[i].userName + '\')"><li class="clearfix">\n' +
                '                <img src="' + imgSrc + '" width="55px" height="55px" alt="avatar" id="userNameAvatar_'+ users[i].userName + '" />\n' +
                '                <div class="about">\n' +
                '                    <div id="userNameAppender_' + users[i].userName + '" class="name">' + users[i].userName + '</div>\n' +
                '                    <div class="status">\n' +
                '                        <i class="fa fa-circle offline"></i>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </li></a>';
        }
        $('#usersList').html(usersTemplateHTML);
    });
}