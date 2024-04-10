import "./Notifications.css";
import pinIcon from "../../Assets/pin-tack.svg";
import updateIcon from  "../../Assets/notification.svg";
import NotifyCard from "../NotifyCard/NotifyCard";
import {useEffect, useState} from "react";
import initWebSocket, {privateStompClient, stompClient} from "../../websocket";
import axios from "axios";

const constructMessage = (notfList) => {
    let resultList = [];
    notfList.forEach(notf => {
        let messageObj = JSON.parse(notf.message)
        let message = {
            title : messageObj.title,
            subject : messageObj.subject,
            body : messageObj.body
        }
        resultList.push(message);
    })
    return resultList;
}

const Notifications = () => {

    const [messagesList, setMessagesList] = useState([]);

    useEffect(() => {
        axios.get("/v1/api/notification").then(resp => {
            setMessagesList(constructMessage(resp.data.Notification))
        })
        initWebSocket().then(() => {
            stompClient.subscribe('/all/messages', function(result) {
                let messageObj = JSON.parse(result.body);
                let message = {
                    title : messageObj.title,
                    subject: messageObj.subject,
                    body: messageObj.body
                }
                setMessagesList([...messagesList].push(message))
            });
            privateStompClient.subscribe('/user/specific', function(result) {
                let messageObj = JSON.parse(result.body);
                let message = {
                    title : messageObj.title,
                    subject: messageObj.subject,
                    body: messageObj.body
                }
                setMessagesList([...messagesList].push(message))
            });
        })
    }, []);

    return (
        <div className="notf-parent-cont-1">
            <div className="notf-parent-cont-2 scroll-div">
                <div className="notf-body-1">
                    <div className="title-font notf-title-cont">
                        <img src={pinIcon} alt="Pin Icon is missing"/>&nbsp;
                        Notifications
                    </div>
                    <div className="notf-card-cont">
                        {messagesList.length > 0 ?
                            messagesList.map(message => {
                                return (
                                    <NotifyCard
                                        title={message.title}
                                        subject={message.subject}
                                        body={message.body}/>
                                );
                            })
                            :
                            <div>No Messages Found</div>}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Notifications;