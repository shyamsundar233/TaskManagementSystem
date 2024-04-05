import "./ListFilter.css";
import {Drawer, MenuItem, Select} from "@mui/material";
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

    const [categoryList, setCategoryList] = useState([]);

    useEffect(() => {
        if(openDrawer){
            axios.get("/v1/api/taskCategory").then(resp => {
                setCategoryList(getCategoryList(resp.data.TaskCategory));
            })
        }
    }, [openDrawer]);

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
                    <input className="input-field" type='text' id='title'/>
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='description'>Description</label>
                    </div>
                    <input className="input-field" type='text' id='description'/>
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='dueDate'>Due Date</label>
                    </div>
                    <input className="input-field title-font due-field" type='date' id='dueDate'/>
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='status'>Status</label>
                    </div>
                    {filterDropDown("status", ["To Do", "In Progress", "On Hold", "Blocked", "Completed", "Cancelled", "Reinitiated"])}
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='priority'>Priority</label>
                    </div>
                    {filterDropDown("priority", ["Low Priority", "Medium Priority", "High Priority"])}
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='category'>Category</label>
                    </div>
                    {filterDropDown("category", categoryList)}
                </div>
                <div className="input-group">
                    <div className="label-spac">
                        <label className="font-sub-heading" htmlFor='recurring'>Recurring</label>
                    </div>
                    {filterDropDown("recurring", ["Daily", "Weekly", "Yearly"])}
                </div>
            </div>
        </Drawer>
    );
}

const filterDropDown = (id, options) => {
    return (
        <Select
            labelId="demo-simple-select-label"
            id={id}
            className="dropdown-field MuiSelect-filled"
            value={options[0]}
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