import "./ListFilter.css";
import {Button, Drawer, MenuItem, Select} from "@mui/material";
import CloseIcon from "../../Assets/xmark.svg";
import React, {useEffect, useState} from "react";
import axios from "axios";

const getCategoryList = (categoryList) => {
    let resArr = [];
    categoryList.forEach(category => {
        resArr.push(category.categoryTitle);
    })
    return resArr;
}

const optList = ["Equals", "NotEquals"]
const  optListWithContains = ["Equals", "NotEquals", "Contains"];

const ListFilter = ({openDrawer, handleClose, handleFilterRecords}) => {

    const [title, setTitle] = useState('');
    const [titleOpt, setTitleOpt] = useState('Equals');
    const [description, setDescription] = useState('');
    const [descriptionOpt, setDescriptionOpt] = useState('Equals');
    const [dueDate, setDueDate] = useState('');
    const [dueDateOpt, setDueDateOpt] = useState('Equals');
    const [status, setStatus] = useState('-None-');
    const [statusOpt, setStatusOpt] = useState('Equals');
    const [priority, setPriority] = useState('-None-');
    const [priorityOpt, setPriorityOpt] = useState('Equals');
    const [category, setCategory] = useState('-None-');
    const [categoryOpt, setCategoryOpt] = useState('Equals');
    const [recurring, setRecurring] = useState('-None-');
    const [recurringOpt, setRecurringOpt] = useState('Equals');

    const [categoryList, setCategoryList] = useState([]);

    useEffect(() => {
        if(openDrawer){
            axios.get("/v1/api/taskCategory").then(resp => {
                let categoryList = ["-None-"].concat(getCategoryList(resp.data.TaskCategory));
                setCategoryList(categoryList);
            })
        }
    }, [openDrawer]);

    const dropDownUpdate = (id, value) => {
        switch(id) {
            case 'status':
                setStatus(value);
                break;
            case 'priority':
                setPriority(value);
                break;
            case 'category':
                setCategory(value);
                break;
            case 'recurring':
                setRecurring(value);
                break;
            default:
                break;
        }
    }

    const optDropDownUpdate = (id, value) => {
        debugger
        switch(id) {
            case 'titleOpt':
                setTitleOpt(value);
                break;
            case 'descriptionOpt':
                setDescriptionOpt(value);
                break;
            case 'dueDateOpt':
                setDueDateOpt(value);
                break;
            case 'statusOpt':
                setStatusOpt(value);
                break;
            case 'priorityOpt':
                setPriorityOpt(value);
                break;
            case 'categoryOpt':
                setCategoryOpt(value);
                break;
            case 'recurringOpt':
                setRecurringOpt(value);
                break;
            default:
                break;
        }
    }

    const handleSubmit = () => {
        let payLoad = {};
        if(title){
            payLoad.title = [titleOpt, title];
        }
        if(description){
            payLoad.description = [descriptionOpt, description];
        }
        if(dueDate){
            payLoad.dueDate = [dueDateOpt, dueDate];
        }
        if(status !== '-None-'){
            payLoad.status = [statusOpt, status];
        }
        if(priority !== '-None-'){
            payLoad.priority = [priorityOpt, priority];
        }
        if(category !== '-None-'){
            payLoad.category = [categoryOpt, category];
        }
        if(recurring !== '-None-'){
            payLoad.recurring = [recurringOpt, recurring];
        }
        handleFilterRecords(payLoad);
        resetData();
        closeDrawer();
    }

    const closeDrawer = () => {
        resetData();
        handleClose();
    }

    const resetData = () => {
        setTitle('');
        setTitleOpt('Equals');
        setDescription('');
        setDescriptionOpt('Equals');
        setDueDate('');
        setDueDateOpt('Equals');
        setStatus('-None-');
        setStatusOpt('Equals');
        setCategory('-None-');
        setCategoryOpt('Equals');
        setPriority('-None-');
        setPriorityOpt('Equals');
        setRecurring('-None-');
        setRecurringOpt('Equals');
    }

    return (
        <Drawer
            open={openDrawer}
            PaperProps={{
                className: "drawer-dim"
            }}
        >
            <div className="margin-10 display-right">
                <img src={CloseIcon} alt="Close Icon not found" className="cursor-pointer" onClick={closeDrawer}/>
            </div>
            <div className="font-heading display-center">
                Filter Records
            </div>
            <div className="filter-form-container padd-20">
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='title'>Title</label>
                    </div>
                    <div className="display-center">
                        {operatorDropDown("titleOpt", optListWithContains, titleOpt, optDropDownUpdate)}
                        <input className="input-field" type='text' id='title' value={title} onChange={event => setTitle(event.target.value)}/>
                    </div>
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='description'>Description</label>
                    </div>
                    <div className="display-center">
                        {operatorDropDown("descriptionOpt", optListWithContains, descriptionOpt, optDropDownUpdate)}
                        <input className="input-field" type='text' id='description' value={description} onChange={event => setDescription(event.target.value)}/>
                    </div>
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='dueDate'>Due Date</label>
                    </div>
                    <div className="display-center">
                        {operatorDropDown("dueDateOpt", optList, dueDateOpt, optDropDownUpdate)}
                        <input className="input-field title-font due-field" type='date' id='dueDate' value={dueDate} onChange={event => setDueDate(event.target.value)}/>
                    </div>
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='status'>Status</label>
                    </div>
                    <div className="display-center">
                        {operatorDropDown("statusOpt", optList, statusOpt, optDropDownUpdate)}
                        {filterDropDown("status", ["-None-", "To Do", "In Progress", "On Hold", "Blocked", "Completed", "Cancelled", "Reinitiated"], status, dropDownUpdate)}
                    </div>
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='priority'>Priority</label>
                    </div>
                    <div className="display-center">
                        {operatorDropDown("priorityOpt", optList, priorityOpt, optDropDownUpdate)}
                        {filterDropDown("priority", ["-None-", "Low Priority", "Medium Priority", "High Priority"], priority, dropDownUpdate)}
                    </div>
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='category'>Category</label>
                    </div>
                    <div className="display-center">
                        {operatorDropDown("categoryOpt", optList, categoryOpt, optDropDownUpdate)}
                        {filterDropDown("category", categoryList, category, dropDownUpdate)}
                    </div>
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='recurring'>Recurring</label>
                    </div>
                    <div className="display-center">
                        {operatorDropDown("recurringOpt", optList, recurringOpt, optDropDownUpdate)}
                        {filterDropDown("recurring", ["-None-", "Daily", "Weekly", "Yearly"], recurring, dropDownUpdate)}
                    </div>
                </div>
                <Button variant="contained" className="btn-1" onClick={handleSubmit}> Submit </Button>
            </div>
        </Drawer>
    );
}

const filterDropDown = (id, options, value,dropDownChange) => {
    return (
        <Select
            labelId="demo-simple-select-label"
            id={id}
            className="dropdown-field MuiSelect-filled"
            value={value}
            onChange={event => dropDownChange(id, event.target.value)}
        >
            {options.map(option => {
                return (
                    <MenuItem value={option}>{option}</MenuItem>
                );
            })}
        </Select>
    );
}

const operatorDropDown = (id, options, value,dropDownChange) => {
    return (
        <Select
            labelId="demo-simple-select-label"
            id={id}
            className="dropdown-field MuiSelect-filled opt-drop"
            value={value}
            onChange={event => dropDownChange(id, event.target.value)}
        >
            {options.map(option => {
                return (
                    <MenuItem value={option}>{option}</MenuItem>
                );
            })}
        </Select>
    );
}

export default ListFilter;