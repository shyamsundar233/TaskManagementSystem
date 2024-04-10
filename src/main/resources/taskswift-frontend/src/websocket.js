import StockJS from "sockjs-client";
import Stomp from "stompjs";

let publicMessage = "";

let privateMessage = "";

export let stompClient = null;

export let privateStompClient = null;

const initWebSocket = () => {
    return new Promise((resolve, reject) => {
        let socket = new StockJS('/ws');
        stompClient = Stomp.over(socket);
        let privateSocket = new StockJS('/ws');
        privateStompClient = Stomp.over(privateSocket);

        let stompPromise = new Promise((resolveStomp, rejectStomp) => {
            stompClient.connect({}, function(frame) {
                stompClient.subscribe('/all/messages', function(result) {
                    publicMessage = result;
                });
                resolveStomp();
            });
        });

        let privatePromise = new Promise((resolvePrivate, rejectPrivate) => {
            privateStompClient.connect({}, function(frame) {
                privateStompClient.subscribe('/user/specific', function(result) {
                    privateMessage = result;
                });
                resolvePrivate();
            });
        });

        Promise.all([stompPromise, privatePromise]).then(() => {
            resolve();
        }).catch((error) => {
            reject(error);
        });
    });
}

export default initWebSocket;