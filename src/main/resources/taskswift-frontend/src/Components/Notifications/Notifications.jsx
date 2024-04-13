import "./Notifications.css";
import pinIcon from "../../Assets/pin-tack.svg";
import NotifyCard from "../NotifyCard/NotifyCard";
import axios from "axios";
import KeyboardArrowRightIcon from "@mui/icons-material/KeyboardArrowRight";
import React from "react";

const Notifications = ({messagesList, updateMessagesList, handleOpenNotf}) => {

    const handleMarkAsRead = (id) => {
        debugger
        axios.post("/v1/api/markAsRead", [id]).then(resp => {
            updateMessagesList("markAsRead", id);
        })
    }

    return (
        <div className="notf-parent-cont-1">
            <div className="notf-parent-cont-2">
                <div className="notf-body-1">
                    <KeyboardArrowRightIcon className="cursor-pointer close-icon-notf" onClick={handleOpenNotf}/>
                    <div className="title-font notf-title-cont">
                        <img src={pinIcon} alt="Pin Icon is missing"/>&nbsp;
                        Notifications
                    </div>
                    <div className="notf-card-cont scroll-div">
                        {messagesList.length > 0 ?
                            messagesList.map(message => {
                                return (
                                    <NotifyCard
                                        id={message.notificationId}
                                        title={message.title}
                                        subject={message.subject}
                                        body={message.body}
                                        classList={message.isRead ? "notf-card-bg-read" : ""}
                                        clickCallback={handleMarkAsRead}
                                    />
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