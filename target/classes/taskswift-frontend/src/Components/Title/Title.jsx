import React, {useState} from 'react';
import "./Title.css";
import searchIcon from "../../Assets/magnifier.svg";
import callIcon from "../../Assets/phone.svg";
import notificationIcon from "../../Assets/bell.svg";
import messageIcon from "../../Assets/msg-writing.svg";
import noUser from "../../Assets/user.svg";
import {Link, useNavigate} from "react-router-dom";
import {Menu, MenuItem} from "@mui/material";
import axios from "axios";

const Title = () => {

    const navigate = useNavigate();

    const [userImage, setUserImage] = useState(noUser);
    const [userMenuOpen, setUserMenuOpen] = useState(false);
    const [anchorEl, setAnchorEl] = useState(null);

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

    return (
        <div className="title-container-1">
            <Link to="/ts" className="link"><div className="title-text title-font">Task Swift</div></Link>
            <button className="create-new-button create-new-button-pos cursor-pointer" onClick={handleCreate}> + CREATE NEW</button>
            <img src={searchIcon} className="search-button search-btn-pos" alt="Search Icon not found"/>
            <img src={callIcon} className="search-button call-btn-pos" alt="Call Icon not found"/>
            <img src={notificationIcon} className="search-button not-btn-pos" alt="Notification Icon not found"/>
            <img src={messageIcon} className="search-button mess-btn-pos" alt="Message Icon not found"/>
            <img src={userImage} id="user_image" className="search-button prof-frame prof-btn-pos" alt={messageIcon} onClick={handleUserMenuOpen}/>
            <UserMenu open={userMenuOpen} handleClose={handleUserMenuClose} anchorEl={anchorEl}/>
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
