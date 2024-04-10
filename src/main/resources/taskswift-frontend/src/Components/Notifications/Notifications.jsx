import "./Notifications.css";
import pinIcon from "../../Assets/pin-tack.svg";
import NotifyCard from "../NotifyCard/NotifyCard";

const Notifications = ({messagesList}) => {

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