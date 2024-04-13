import React, {useEffect, useState} from 'react';
import "./Title.css";
import searchIcon from "../../Assets/magnifier.svg";
import callIcon from "../../Assets/phone.svg";
import notificationIcon from "../../Assets/bell.svg";
import messageIcon from "../../Assets/msg-writing.svg";
import noUser from "../../Assets/user.svg";
import {Link, useNavigate} from "react-router-dom";
import {Menu, MenuItem} from "@mui/material";
import axios from "axios";
import TsDrawer from "../../TemplateComponents/TsDrawer/TsDrawer";
import Notifications from "../Notifications/Notifications";
import KeyboardArrowRightIcon from '@mui/icons-material/KeyboardArrowRight';
import initWebSocket, {privateStompClient, stompClient} from "../../websocket";

const constructMessage = (notfList) => {
    let resultList = [];
    notfList.forEach(notf => {
        let messageObj = JSON.parse(notf.message)
        let message = {
            title : messageObj.title,
            subject : messageObj.subject,
            body : messageObj.body,
            isRead : notf.isRead,
            notificationId : notf.notificationId
        }
        resultList.push(message);
    })
    return resultList.reverse();
}

const Title = () => {

    const navigate = useNavigate();

    const [userImage, setUserImage] = useState(noUser);
    const [userMenuOpen, setUserMenuOpen] = useState(false);
    const [anchorEl, setAnchorEl] = useState(null);
    const [openNotf, setOpenNotf] = useState(false);
    const [messagesList, setMessagesList] = useState([]);

    useEffect(() => {
        axios.get("/v1/api/notification").then(resp => {
            setMessagesList(constructMessage(resp.data.Notification))
        })
        initWebSocket().then(() => {
            stompClient.subscribe('/all/messages', function(result) {
                let messageObj = JSON.parse(result.body).messageBody;
                let message = {
                    title : messageObj.title,
                    subject: messageObj.subject,
                    body: messageObj.body,
                    isRead: false
                }
                setMessagesList(prevMessages => [message, ...prevMessages])
            });
            privateStompClient.subscribe('/user/specific', function(result) {
                let messageObj = JSON.parse(result.body).messageBody;
                let message = {
                    title : messageObj.title,
                    subject: messageObj.subject,
                    body: messageObj.body,
                    isRead: false
                }
                setMessagesList(prevMessages => [message, ...prevMessages])
            });
        })
    }, []);

    const updateMessageList = (operation, id) => {
        if(operation === "markAsRead"){
            debugger
            const updatedMessagesList = messagesList.map(msg => {
                if (msg.notificationId === id) {
                    return { ...msg, isRead: true };
                } else {
                    return msg;
                }
            });
            
            setMessagesList(updatedMessagesList);
        }
    }

    const handleCreate = () => {
        navigate("/ts/create");
    }

    const handleUserMenuOpen = (event) => {
        setAnchorEl(event.currentTarget);
        setUserMenuOpen(true);
    }

    const handleUserMenuClose = (action) => {
        if(action === "logout") {
            axios.post("/logout").then(resp => {
                window.location.href = "/login"
            })
        }
        setAnchorEl(null);
        setUserMenuOpen(false);
    }

    const handleOpenNotf = () => {
        setOpenNotf(!openNotf);
    }

    return (
        <div className="title-container-1">
            <Link to="/ts" className="link"><div className="title-text title-font">Task Swift</div></Link>
            <button className="create-new-button create-new-button-pos cursor-pointer" onClick={handleCreate}> + CREATE NEW</button>
            <img src={searchIcon} className="search-button search-btn-pos cursor-pointer" alt="Search Icon not found"/>
            <img src={callIcon} className="search-button call-btn-pos cursor-pointer" alt="Call Icon not found"/>
            <img src={notificationIcon} className="search-button not-btn-pos cursor-pointer" alt="Notification Icon not found" onClick={handleOpenNotf}/>
            <img src={messageIcon} className="search-button mess-btn-pos cursor-pointer" alt="Message Icon not found"/>
            <img src={userImage} id="user_image" className="search-button prof-frame prof-btn-pos cursor-pointer" alt={messageIcon} onClick={handleUserMenuOpen}/>
            <UserMenu open={userMenuOpen} handleClose={handleUserMenuClose} anchorEl={anchorEl}/>
            <TsDrawer
                open={openNotf}
                anchor="right"
                body={
                    <div>
                        <Notifications messagesList={messagesList} updateMessagesList={updateMessageList} handleOpenNotf={handleOpenNotf}/>
                    </div>
                }
                paperProps="notf-drawer-cont"
            />
        </div>
    );
}

const UserMenu = ({open, handleClose, anchorEl}) => {
    return (
        <Menu
            id="basic-menu"
            anchorEl={anchorEl}
            open={open}
            onClose={handleClose}
            MenuListProps={{
                'aria-labelledby': 'basic-button',
            }}
        >
            <MenuItem onClick={e => handleClose("profile")}>Profile</MenuItem>
            <MenuItem onClick={e => handleClose("account")}>My account</MenuItem>
            <MenuItem onClick={e => handleClose("logout")}>Logout</MenuItem>
        </Menu>
    );
}

export default Title;
