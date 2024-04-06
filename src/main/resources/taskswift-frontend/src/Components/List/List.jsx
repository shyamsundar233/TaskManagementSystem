import "./List.css";
import axios from "axios";
import {useEffect, useState} from "react";
import ListFilter from "../ListFilter/ListFilter";
import FilterIcon from "../../Assets/filter.svg";
import TableComponent from "../TableComponent/TableComponent";
import {Link} from "react-router-dom";

const createData = (id, title, desc, dueDate, priority, category, recurring) => {
    return { id, title, desc, dueDate, priority, category, recurring };
}

const constructTasksList = (tasksList) => {
    let taskList = [];

    tasksList.forEach(task => {
        let taskData = createData(task.taskId, task.taskTitle, task.taskDesc, task.dueDate, task.taskPriority, task.taskCategory, task.taskRecurring)
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
        taskData.push(task.dueDate);
        taskData.push(task.priority);
        taskData.push(task.category);
        taskData.push(task.recurring);
        resultArr.push(taskData);
    })
    return resultArr;
}

const headerRow = ["Task Title", "Description", "Due Date", "Priority", "Category", "Recurring"];

const List = () => {

    const [tasksList, setTasksList] = useState([]);
    const [openFilter, setOpenFilter] = useState(false);

    useEffect(() => {
        axios.get("/v1/api/tasks").then((resp) => {
            initRecords(resp.data.Task);
        })
    }, []);

    const handleFilterClose = () => {
        setOpenFilter(false);
    }

    const filterRecords = (payLoad) => {
        if(Object.keys(payLoad).length > 0){
            axios.post("/v1/api/taskFilter", payLoad).then(resp => {
                initRecords(resp.data.Task);
            })
        }
    }

    const initRecords = (allTasks) => {
        setTasksList(constructTaskDataForTable(constructTasksList(allTasks)).reverse());
    }

    return (
        <div>
            <ListFilter openDrawer={openFilter} handleClose={handleFilterClose} handleFilterRecords={filterRecords}></ListFilter>
            <div className="font-heading padd-20 display-flex">
                Task Records &nbsp;&nbsp;&nbsp;
                <img src={FilterIcon} alt="Filter Icon not found" className="cursor-pointer" onClick={e => setOpenFilter(true)}/>
            </div>
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
    );
}

export default List;