import "./List.css";
import axios from "axios";
import React, {useEffect, useState} from "react";
import ListFilter from "../ListFilter/ListFilter";
import FilterIcon from "../../Assets/filter.svg";
import TableComponent from "../TableComponent/TableComponent";
import {Link, useNavigate} from "react-router-dom";
import {Button} from "@mui/material";
import NavigateBeforeIcon from '@mui/icons-material/NavigateBefore';
import NavigateNextIcon from '@mui/icons-material/NavigateNext';
import FilterAltOutlinedIcon from '@mui/icons-material/FilterAltOutlined';

const createData = (id, title, desc, status, dueDate, priority, category, recurring, user) => {
    return { id, title, desc, status, dueDate, priority, category, recurring, user };
}

const constructTasksList = (tasksList) => {
    let taskList = [];

    tasksList.forEach(task => {
        let taskData = createData(task.taskId, task.taskTitle, task.taskDesc, task.taskStatus, task.dueDate, task.taskPriority, task.taskCategory, task.taskRecurring, task.user.username)
        taskList.push(taskData)
    })
    return taskList;
}

const constructTaskDataForTable = (taskList) => {
    const resultArr = [];
    taskList.forEach(task => {
        const taskData = [];
        taskData.push(<Link to={`/ts/view/${task.id}`} className="link">{task.title}</Link>);
        taskData.push(task.desc);
        taskData.push(task.status);
        taskData.push(task.dueDate);
        taskData.push(task.priority);
        taskData.push(task.category);
        taskData.push(task.recurring);
        taskData.push(task.user);
        resultArr.push(taskData);
    })
    return resultArr;
}

const headerRow = ["Task Title", "Description", "Status" ,"Due Date", "Priority", "Category", "Recurring", "User"];

const getRecordsCount = (page, count) => {
    let startCount = page * 20 + 1;
    let endCount = startCount + count - 1;
    return [startCount, endCount];
}

const List = () => {

    const [tasksList, setTasksList] = useState([]);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(20);
    const [startCount, setStartCount] = useState(1);
    const [endCount, setEndCount] = useState(20);
    const [hasMoreRecords, setHasMoreRecords] = useState(true);
    const [openFilter, setOpenFilter] = useState(false);
    const [filterApplied, setFilterApplied] = useState(false);

    const nav = useNavigate();

    useEffect(() => {
        fetchRecords();
    }, []);
    useEffect(() => {
        fetchRecords();
    }, [page]);

    const fetchRecords = () => {
        axios.get(`/v1/api/tasks?page=${page}&size=${size}`).then((resp) => {
            initRecords(resp.data.Task);
            let recordsCount = getRecordsCount(resp.data.metaData.page, resp.data.metaData.count);
            setStartCount(recordsCount[0]);
            setEndCount(recordsCount[1]);
            setHasMoreRecords(resp.data.metaData.hasMoreRecords);
            setFilterApplied(false);
        })
    }

    const handleFilterClose = () => {
        setOpenFilter(false);
    }

    const filterRecords = (payLoad) => {
        if(Object.keys(payLoad).length > 0){
            axios.post("/v1/api/taskFilter", payLoad).then(resp => {
                initRecords(resp.data.Task);
                setFilterApplied(true);
            })
        }
    }

    const initRecords = (allTasks) => {
        setTasksList(constructTaskDataForTable(constructTasksList(allTasks)));
    }

    const resetRecords = () => {
        fetchRecords();
    }

    const handlePageNavigation = (dir) => {
        let nextPage;
        if(dir === "left" && page > 0){
            nextPage = page - 1;
            setPage(nextPage);
        }else if(dir === "right" && hasMoreRecords){
            nextPage = page + 1;
            setPage(nextPage);
        }
    }

    const handleCreateNav = () => {
        nav("/ts/create");
    }

    return (
        <div>
            <ListFilter openDrawer={openFilter} handleClose={handleFilterClose}
                        handleFilterRecords={filterRecords}></ListFilter>
            <div className="padd-20 display-flex list-header align-items-center">
                <span className="font-heading">Task Records &nbsp;&nbsp;&nbsp;</span>
                <FilterAltOutlinedIcon className="cursor-pointer nav-list-button" onClick={e => setOpenFilter(true)}/>
                {filterApplied &&
                    <Button variant="outlined" className="btn-1-outlined" onClick={resetRecords}>Clear Filter</Button>}
                <button className="btn-1 cursor-pointer list-btn-create" onClick={handleCreateNav}> + CREATE NEW</button>
                <div className="display-flex pagination-div align-items-center">
                    <NavigateBeforeIcon
                        className={page === 0 ? "cursor-pointer disabled-button nav-list-button" : "cursor-pointer nav-list-button"}
                        onClick={e => handlePageNavigation("left")}/> &nbsp;
                    <NavigateNextIcon
                        className={hasMoreRecords ? "cursor-pointer nav-list-button" : "cursor-pointer disabled-button nav-list-button"}
                        onClick={e => handlePageNavigation("right")}/> &nbsp;&nbsp;&nbsp;&nbsp;
                    <div className="font-bold">
                        {startCount} &nbsp; - &nbsp; {endCount}
                    </div>
                </div>
            </div>
            <div className="display-center">
                {tasksList.length > 0 ? (
                    <TableComponent
                        headerRow={headerRow}
                        bodyRow={tasksList}
                        classList="list-tab-dim"
                    />
                ) : (
                    <div className="padd-20 font-bold">No Data Found</div>
                )}
            </div>
        </div>
    );
}

export default List;