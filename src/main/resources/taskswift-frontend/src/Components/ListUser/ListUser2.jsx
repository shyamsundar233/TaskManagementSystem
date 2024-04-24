import "./ListUser.css";
import React, {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import TsCard from "../../TemplateComponents/TsCard/TsCard";
import {Avatar} from "@mui/material";
import TsDrawer from "../../TemplateComponents/TsDrawer/TsDrawer";
import AddUser from "../AddUser/AddUser";

const ListUser2 = () =>{

    const [users, setUsers] = useState([]);
    const [openAddUser, setOpenAddUser] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        updateUsers();
    }, []);

    const handleCreate = () => {
        setOpenAddUser(true);
    }

    const handleCloseCreate = () => {
        setOpenAddUser(false);
    }

    const updateUsers = () => {
        axios.get("/v1/api/users").then(resp => {
            setUsers(resp.data.Users);
            handleCloseCreate();
        })
    }

    return (
        <div className="parent-div-table">
            <div className="font-heading display-flex pa-1">
                Team Members
                <button className="create-new-button add-new-button-pos cursor-pointer" onClick={handleCreate}> + ADD TEAM MEMBER</button>
            </div>
            <div className="padd-20 display-flex">
                {users.length > 0 && users.map(user => {
                    return (
                        <TsCard
                            header={
                                <div className="font-sub-heading">{user.username}</div>
                            }
                            headerAvatar={
                                <Avatar sx={{bgcolor : "#00BDD6FF", marginLeft: "15px"}} aria-label="recipe">
                                    {user.username.charAt(0)}
                                </Avatar>
                            }
                            content={
                                <div>
                                    <div className="margin-10 display-flex left-row-1">
                                        <div className="left-col-1">
                                            <label className="font-bold" htmlFor="userEmail">Email: </label>
                                        </div>
                                        <div id="userEmail" className="left-col-2"> {user.email} </div>
                                    </div>
                                    <div className="margin-10 display-flex left-row-1">
                                        <div className="left-col-1">
                                            <label className="font-bold" htmlFor="userAuth">Authority: </label>
                                        </div>
                                        <div id="userAuth" className="left-col-2"> {user.authority} </div>
                                    </div>
                                    <div className="margin-10 display-flex left-row-1">
                                        <div className="left-col-1">
                                            <label className="font-bold" htmlFor="userPhone">Phone: </label>
                                        </div>
                                        <div id="userPhone" className="left-col-2"> +91-99988-22233</div>
                                    </div>
                                    <div className="margin-10 display-flex left-row-1">
                                        <div className="left-col-1">
                                            <label className="font-bold" htmlFor="userLoc">Location: </label>
                                        </div>
                                        <div id="userLoc" className="left-col-2"> Chennai</div>
                                    </div>
                                    <div className="margin-10 display-flex left-row-1">
                                        <div className="left-col-1">
                                            <label className="font-bold" htmlFor="userCreatedOn">Created On: </label>
                                        </div>
                                        <div id="userCreatedOn" className="left-col-2"> 20-03-2024 07:23:09 AM</div>
                                    </div>
                                </div>
                            }

                            cardClass="user-card-dim"
                            headerClass="user-card-header-1"
                            contentClass="user-card-cont-1"
                        />
                    );
                })}
            </div>
            <TsDrawer
                open={openAddUser}
                anchor="top"
                body={
                    <AddUser closeAddUser={handleCloseCreate} updateUsersList={updateUsers}/>
                }
                drawerClass="add-user-drawer"
            />
        </div>
    );

}

export default ListUser2;