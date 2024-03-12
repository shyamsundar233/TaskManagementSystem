import React, {useState} from 'react';
import "./Title.css";
import searchIcon from "../../Assets/magnifier.svg";
import callIcon from "../../Assets/phone.svg";
import notificationIcon from "../../Assets/bell.svg";
import messageIcon from "../../Assets/msg-writing.svg";
import noUser from "../../Assets/user.svg";

const Title = () => {

    const [userImage, setUserImage] = useState(noUser);

    return (
        <div className="title-container-1">
            <div className="title-text">Task Swift</div>
            <button className="create-new-button"> + CREATE NEW</button>
            <img src={searchIcon} className="search-button search-btn-pos" alt="Search Icon not found"/>
            <img src={callIcon} className="search-button call-btn-pos" alt="Call Icon not found"/>
            <img src={notificationIcon} className="search-button not-btn-pos" alt="Notification Icon not found"/>
            <img src={messageIcon} className="search-button mess-btn-pos" alt="Message Icon not found"/>
            <img src={userImage} className="search-button prof-frame prof-btn-pos" alt={messageIcon}/>
        </div>
    );
}

export default Title;
