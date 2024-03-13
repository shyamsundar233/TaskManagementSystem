import "./NotifyCard.css";
import dummyUser from "../../Assets/user.svg";

const NotifyCard = ({user, status, title, body, userImage}) => {

    if(!userImage){
        userImage = dummyUser;
    }

    return (
        <div className="notify-card-cont-1">
            <div className="notify-card-cont-2">
                <div className="notify-card-user-cont">
                    <img src={userImage} alt="User Not Found"/>&nbsp;&nbsp;
                    <span className="notify-card-user-text"> {user} </span> &nbsp; {status}
                </div>
                <div className="notify-card-title-cont">
                    <span className="notify-card-user-text">{title}</span>
                </div>
                <div className="notify-card-title-cont">
                    <span>{body}</span>
                </div>
            </div>
        </div>
    );
}

export default NotifyCard;
