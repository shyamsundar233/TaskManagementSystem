import "./NotifyCard.css";

const NotifyCard = ({id, title, subject, body, classList, clickCallback}) => {

    return (
        <div className={`cursor-pointer notify-card-cont-1 ${classList}`} onClick={e => clickCallback(id)}>
            <div className="notify-card-cont-2">
                <div className="notify-card-user-cont">
                    <span className="notify-card-user-text"> {title} </span> &nbsp;
                </div>
                <div className="notify-card-title-cont">
                    <span className="notify-card-user-text">{subject}</span>
                </div>
                <div className="notify-card-title-cont">
                    <span>{body}</span>
                </div>
            </div>
        </div>
    );
}

export default NotifyCard;
