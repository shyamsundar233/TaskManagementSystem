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

const ListFilter = ({openDrawer, handleClose}) => {

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [dueDate, setDueDate] = useState('');
    const [status, setStatus] = useState('To Do');
    const [priority, setPriority] = useState('Low Priority');
    const [category, setCategory] = useState('');
    const [recurring, setRecurring] = useState('Daily');

    const [categoryList, setCategoryList] = useState([]);

    useEffect(() => {
        if(openDrawer){
            axios.get("/v1/api/taskCategory").then(resp => {
                let categoryList = getCategoryList(resp.data.TaskCategory);
                setCategory(categoryList[0]);
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

    const handleSubmit = () => {
        handleClose()
    }

    return (
        <Drawer
            open={openDrawer}
            PaperProps={{
                className: "drawer-dim"
            }}
        >
            <div className="margin-10 display-right">
                <img src={CloseIcon} alt="Close Icon not found" className="cursor-pointer" onClick={handleClose}/>
            </div>
            <div className="font-heading display-center">
                Filter Records
            </div>
            <div className="filter-form-container padd-20">
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='title'>Title</label>
                    </div>
                    <input className="input-field" type='text' id='title' value={title} onChange={event => setTitle(event.target.value)}/>
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='description'>Description</label>
                    </div>
                    <input className="input-field" type='text' id='description' value={description} onChange={event => setDescription(event.target.value)}/>
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='dueDate'>Due Date</label>
                    </div>
                    <input className="input-field title-font due-field" type='date' id='dueDate' value={dueDate} onChange={event => setDueDate(event.target.value)}/>
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='status'>Status</label>
                    </div>
                    {filterDropDown("status", ["To Do", "In Progress", "On Hold", "Blocked", "Completed", "Cancelled", "Reinitiated"], status, dropDownUpdate)}
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='priority'>Priority</label>
                    </div>
                    {filterDropDown("priority", ["Low Priority", "Medium Priority", "High Priority"], priority, dropDownUpdate)}
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='category'>Category</label>
                    </div>
                    {filterDropDown("category", categoryList, category, dropDownUpdate)}
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='recurring'>Recurring</label>
                    </div>
                    {filterDropDown("recurring", ["Daily", "Weekly", "Yearly"], recurring, dropDownUpdate)}
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

export default ListFilter;