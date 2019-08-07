'use strict';

function sendObject(obj, destination) {
    let xhr = new XMLHttpRequest();

    xhr.open("POST", destination, false);
    xhr.setRequestHeader("Content-type", "application/json; charset=utf-8");
    // Костыль!!! Заменить по возможности
    xhr.onload = () => {
        if(xhr.status === 200) {
            $("html").html($("html", xhr.response).html());
            location.href = xhr.responseURL;
        }
    };
    xhr.send(JSON.stringify(obj));
}