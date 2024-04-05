import React, {useEffect, useState} from 'react';
import "./Create.css";
import axios from 'axios';
import $ from 'jquery';
import { useAlert } from '../CustomAlert/CustomAlert';
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    MenuItem,
    Select, Slide
} from "@mui/material";
import {useNavigate, useParams} from "react-router-dom";

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="down" ref={ref} {...props} />;
});


const getTodayDate = () => {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0'); // January is 0
    const day = String(today.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

const Create = () => {

    const {showAlert} = useAlert();
    const {taskId} = useParams();
    const navigate = useNavigate();
    let taskData;

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [dueDate, setDueDate] = useState(getTodayDate());
    const [statusList, setStatusList] = useState(["To Do", "In Progress", "On Hold", "Blocked", "Completed", "Cancelled", "Reinitiated"]);
    const [status, setStatus] = useState('');
    const [priority, setPriority] = useState('Low Priority');
    const [category, setCategory] = useState('');
    const [attachment, setAttachment] = useState(null);
    const [recurring, setRecurring] = useState('Daily');
    const [categoryTitle, setCategoryTitle] = useState('');
    const [categoryDesc, setCategoryDesc] = useState('');
    const [dialogOpen, setDialogOpen] = useState(false);
    const [categoryList, setCategoryList] = useState([]);
    const [userId, setUserId] = useState(null);
    const [usersList, setUsersList] = useState([]);

    useEffect(() => {
        axios.get("/v1/api/taskCategory").then(resp => {
            setCategoryList(resp.data.TaskCategory);
            if(resp.data.TaskCategory && resp.data.TaskCategory.length > 0){
                setCategory(resp.data.TaskCategory[0].categoryTitle);
            }
        })
        axios.get("/v1/api/users").then(resp => {
            setUsersList(resp.data.Users);
            if(resp.data.Users.length > 0){
                setUserId(resp.data.Users[0].userid);
            }
        })
        if(taskId){
            axios.get("/v1/api/tasks/" + taskId).then(resp => {
                taskData = resp.data.Task[0];
                setTitle(taskData.taskTitle);
                setDescription(taskData.taskDesc);
                setDueDate(taskData.dueDate);
                setStatus(taskData.taskStatus);
                setTimeout(() => {
                    document.getElementById("status_" + taskData.taskStatus).style.backgroundColor = "#00BDD6FF";
                },100)
                setPriority(taskData.taskPriority);
                setAttachment(taskData.taskAttachment);
                setRecurring(taskData.taskRecurring);
                setCategory(taskData.taskCategory);
                setUserId(taskData.userId);
            })
        }
    }, []);

    const handleSubmit = () => {
        if(!validateInputs()){
            return false;
        }
        let payLoad = {
            "taskTitle" : title,
            "taskDesc" : description,
            "dueDate" : dueDate,
            "taskPriority" : priority,
            "taskCategory" : category,
            "taskAttachment" : attachment,
            "taskRecurring" : recurring,
            "taskStatus" : status,
            "taskStatusList" : statusList,
            "userId" : userId
        };
        if(taskId){
            payLoad.taskId = taskId;
        }
        axios.post("/v1/api/tasks", payLoad, {withCredentials: true}).then((resp) => {
            let [message, severity] = constructMsg(resp.data.Task);
            showAlert(message, severity);
            if(!taskId){
                resetData();
            }
        })
    }

    const handleDelete = () => {
        axios.delete("/v1/api/tasks/" + taskId).then(resp => {
            showAlert(resp.data.Task, 'success');
            navigate("/ts/list");
        })
    }

    const constructMsg = (response) => {
        let errorIndex = response.lastIndexOf(":");
        if(errorIndex > -1){
            return [response.substring(errorIndex + 1).trim(), 'error'];
        }else{
            return [response, 'success'];
        }
    }

    const validateInputs = () => {
        if(title.length < 1 || title.length > 200){
            showAlert("Please check the Title length", "error");
            return false;
        }else if(description.length < 1 || description.length > 200){
            showAlert("Please check the Description Length", "error");
            return false;
        }else if(dueDate.length < 1){
            showAlert("Please set the Due Date","error")
            return false;
        }else if(status.length < 1){
            showAlert("Please add status for task","error")
            return false;
        }else if(!category){
            showAlert("Please add category for task","error")
            return false;
        }else{
            var titleRegex = /^[^\s]+[a-zA-Z0-9\s]*[a-zA-Z0-9]$/;
            if(!titleRegex.test(title)){
                showAlert("Please enter a valid title","error")
                return false;
            }
        }
        return true;
    }

    const handleStatusClick = (event, tempStatus) => {
        if(status === tempStatus){
            setStatus('');
            event.target.style.backgroundColor = "";
        }else{
            if(status !== ''){
                document.getElementById("status_" + status).style.backgroundColor = "";
            }
            setStatus(tempStatus);
            event.target.style.backgroundColor = "#00BDD6FF";
        }
    }

    const saveCategory = (event) => {
        event.preventDefault();
        let data = {
            "categoryTitle" : categoryTitle,
            "categoryDesc" : categoryDesc
        }
        axios.post("/v1/api/taskCategory", data).then(resp => {
            axios.get("/v1/api/taskCategory").then(resp => {
                setCategoryList(resp.data.TaskCategory);
                handleCatgDialog();
            })
        })
    }

    const handleCatgDialog = () => {
        setDialogOpen(!dialogOpen);
    }

    const resetData = () => {
        setTitle('');
        setDescription('');
        setDueDate(getTodayDate());
        setPriority('Low Priority');
        setCategory('Food');
        setAttachment(null);
        setRecurring('Daily');
        setStatusList(["To Do", "In Progress", "On Hold", "Blocked", "Completed", "Cancelled", "Reinitiated"]);
        setStatus('');
    }

    return (
        <div className="container">
            <h1 className="heading">Create Task</h1>
            <div className="input-group">
                <div className="label-spac">
                    <label className="font-sub-heading" htmlFor='title'>Title</label>
                </div>
                <input className="input-field" type='text' id='title' value={title}
                       onChange={(e) => setTitle(e.target.value)}/>
            </div>

            <div className="input-group">
                <div className="label-spac">
                    <label className="font-sub-heading" htmlFor='description'>Description</label>
                </div>
                <input className="input-field" type='text' id='description' value={description}
                       onChange={(e) => setDescription(e.target.value)}/>
            </div>

            <div className="input-group">
                <div className="label-spac">
                    <label className="font-sub-heading" htmlFor='dueDate'>Due Date</label>
                </div>
                <input className="input-field title-font" type='date' id='dueDate' value={dueDate}
                       onChange={(e) => setDueDate(e.target.value)}/>
            </div>

            <div className="input-group">
                <div className="label-spac">
                    <label className="font-sub-heading" htmlFor='status'>Status</label>
                </div>
                <div className="display-flex ">
                    {statusList.length > 0 && statusList.map((tempStatus, index) => {
                        return (
                            <div style={{position: "relative"}} className="cursor-pointer">
                                <div className="margin-10 status-span-cont" id={`status_${tempStatus}`}
                                     onClick={e => handleStatusClick(e, tempStatus)}
                                >{tempStatus}</div>
                            </div>
                        );
                    })}
                </div>
            </div>

            <div className="input-group">
                <div className="label-spac">
                    <label className="font-sub-heading" htmlFor='priority'>Priority</label>
                </div>
                <Select
                    labelId="demo-simple-select-label"
                    id="priority"
                    value={priority}
                    className="dropdown-field MuiSelect-filled"
                    onChange={(e) => setPriority(e.target.value)}
                >
                    <MenuItem value="Low Priority">Low Priority</MenuItem>
                    <MenuItem value="Medium Priority">Medium Priority</MenuItem>
                    <MenuItem value="High Priority">High Priority</MenuItem>
                </Select>
            </div>

            <div className="input-group">
                <div className="label-spac">
                    <label className="font-sub-heading" htmlFor='category'>Category</label>
                </div>
                <Select
                    labelId="demo-simple-select-label"
                    id="category"
                    value={category}
                    className="dropdown-field MuiSelect-filled"
                    onChange={(e) => {
                        if (e.target.value) {
                            setCategory(e.target.value)
                        }
                    }}
                >
                    {categoryList.length > 0 && categoryList.map((category, index) => {
                        return (
                            <MenuItem value={category.categoryTitle}>{category.categoryTitle}</MenuItem>
                        );
                    })}
                    <Button className="catg-create-btn margin-10 btn-1" onClick={handleCatgDialog}>Create New</Button>
                </Select>
            </div>

            <div className="input-group file-field">
                <div className="label-spac">
                    <label className="font-sub-heading" htmlFor="attachment">Attach</label>
                </div>
                <input className="input-field" type='file' id="attachment" value={attachment}
                       onChange={(e) => setAttachment(e.target.files[0])}/>
            </div>

            <div className="input-group">
                <div className="label-spac">
                    <label className="font-sub-heading" htmlFor='recurring'>Recurring</label>
                </div>
                <Select
                    labelId="demo-simple-select-label"
                    id="recurring"
                    value={recurring}
                    className="dropdown-field MuiSelect-filled"
                    onChange={(e) => setRecurring(e.target.value)}
                >
                    <MenuItem value="Daily">Daily</MenuItem>
                    <MenuItem value="Weekly">Weekly</MenuItem>
                    <MenuItem value="Yearly">Yearly</MenuItem>
                </Select>
            </div>
            <div className="input-group">
                <div className="label-spac">
                    <label className="font-sub-heading" htmlFor='user'>Task For</label>
                </div>
                <Select
                    labelId="demo-simple-select-label"
                    id="user"
                    value={userId}
                    className="dropdown-field MuiSelect-filled"
                    onChange={(e) => setUserId(e.target.value)}
                >
                    {usersList.length > 0 && usersList.map(user => {
                        return (
                            <MenuItem value={user.userid}>{user.username}</MenuItem>
                        );
                    })}
                </Select>
            </div>

            <div className="sub-btn-1">
                <Button variant="contained" className="btn-1" onClick={handleSubmit}> Submit </Button> &nbsp;&nbsp;&nbsp;
                {taskId &&
                    <Button variant="outlined" color="error" onClick={handleDelete}> Delete </Button>
                }
            </div>

            <Dialog
                open={dialogOpen}
                TransitionComponent={Transition}
                PaperProps={{
                    component: 'form'
                }}
            >
                <DialogTitle className="font-heading">Create New Category</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        <div className="input-group">
                            <label className="font-sub-heading" htmlFor='cat_title'>Category Title</label>
                            <input className="input-field" type='text' id='cat_title' value={categoryTitle}
                                   onChange={(e) => setCategoryTitle(e.target.value)}/>
                        </div>
                        <div className="input-group">
                            <label className="font-sub-heading" htmlFor='cat_desc'>Category Description</label>
                            <input className="input-field" type='text' id='cat_desc' value={categoryDesc}
                                   onChange={(e) => setCategoryDesc(e.target.value)}/>
                        </div>
                    </DialogContentText>
                    <DialogActions>
                        <Button className="btn-1" onClick={handleCatgDialog}>Cancel</Button>
                        <Button className="btn-1" type="submit" onClick={event => saveCategory(event)}>Save</Button>
                    </DialogActions>
                </DialogContent>
            </Dialog>

        </div>
    );
}

export default Create;
