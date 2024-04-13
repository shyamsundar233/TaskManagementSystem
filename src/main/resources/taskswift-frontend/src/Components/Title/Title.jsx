import React, {useEffect, useState} from 'react';
import "./Title.css";
import noUser from "../../Assets/user.svg";
import {Link, useNavigate} from "react-router-dom";
import {Menu, MenuItem} from "@mui/material";
import axios from "axios";
import Notifications from "../Notifications/Notifications";
import initWebSocket, {privateStompClient, stompClient} from "../../websocket";
import TsPopover from "../../TemplateComponents/TsPopover/TsPopover";
import NotificationsIcon from '@mui/icons-material/Notifications';
import NotificationsActiveIcon from '@mui/icons-material/NotificationsActive';
import SearchIcon from '@mui/icons-material/Search';
import CallIcon from '@mui/icons-material/Call';
import PersonIcon from '@mui/icons-material/Person';

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
    const [hasUnread, setHasUnread] = useState(false);
    const [popoverEl, setPopoverEl] = useState(null);

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

    useEffect(() => {
        let hasUnread = false;
        for (let index = 0; index < messagesList.length; index++) {
            let message = messagesList[index];
            if(!message.isRead){
                setHasUnread(true);
                hasUnread = true;
                break;
            }
        }
        if(!hasUnread) {
            setHasUnread(false);
        }
    }, [messagesList]);

    const updateMessageList = (operation, id) => {
        if(operation === "markAsRead"){
            debugger
            let updatedMessagesList = [...messagesList];
            let index = updatedMessagesList.findIndex(msg => msg.notificationId === id);
            updatedMessagesList[index].isRead = true;
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

    const handleOpenNotf = (event) => {
        setOpenNotf(!openNotf);
        setPopoverEl(event.currentTarget);
    }

    return (
        <div className="title-container-1">
            <Link to="/ts" className="link"><div className="title-text title-font">Task Swift</div></Link>
            <div className="title-actions-div">
                <button className="btn-1 cursor-pointer" onClick={handleCreate}> + CREATE NEW</button>
                <div className="title-options-div">
                    <SearchIcon className="title-icon-pos cursor-pointer"/>
                    <CallIcon className="title-icon-pos cursor-pointer"/>
                    {hasUnread ?
                        <NotificationsActiveIcon className="title-icon-pos cursor-pointer"  onClick={e => handleOpenNotf(e)}/>
                        :
                        <NotificationsIcon className="title-icon-pos cursor-pointer"  onClick={e => handleOpenNotf(e)}/>
                    }
                    <PersonIcon className="title-icon-pos cursor-pointer" onClick={handleUserMenuOpen}/>
                </div>
            </div>
            <UserMenu open={userMenuOpen} handleClose={handleUserMenuClose} anchorEl={anchorEl}/>
            <TsPopover
                open={openNotf}
                anchorEl={popoverEl}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'left'
                }}
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
                paperProps={{
                    style: {
                        width: '30%',
                        maxHeight: '500px',
                        marginTop: '15px',
                        marginLeft: '90px'
                    },
                }}
                body={
                    <div>
                        <Notifications messagesList={messagesList} updateMessagesList={updateMessageList} handleOpenNotf={handleOpenNotf}/>
                    </div>
                }
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
