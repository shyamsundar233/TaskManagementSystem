import "./List.css";
import axios from "axios";
import {useEffect, useState} from "react";
import ListFilter from "../ListFilter/ListFilter";
import FilterIcon from "../../Assets/filter.svg";
import TableComponent from "../TableComponent/TableComponent";

const createData = (id, title, desc, dueDate, priority, category, recurring) => {
    return { id, title, desc, dueDate, priority, category, recurring };
}

const constructTasksList = (tasksList) => {
    let currentDate = new Date().toJSON().slice(0, 10);
    let pastTasks = [];
    let todayTasks = [];
    let futureTasks = [];

    tasksList.forEach(task => {
        let taskData = createData(task.taskId, task.taskTitle, task.taskDesc, task.dueDate, task.taskPriority, task.taskCategory, task.taskRecurring)

        if(currentDate > taskData.dueDate){
            pastTasks.push(taskData);
        }else if(currentDate < taskData.dueDate){
            futureTasks.push(taskData);
        }else{
            todayTasks.push(taskData);
        }
    })
    return [pastTasks, todayTasks, futureTasks];
}

const constructTaskDataForTable = (taskList) => {
    const resultArr = [];
    taskList.forEach(task => {
        const taskData = [];
        taskData.push(task.title);
        taskData.push(task.desc);
        taskData.push(task.dueDate);
        taskData.push(task.priority);
        taskData.push(task.category);
        taskData.push(task.recurring);
        resultArr.push(taskData);
    })
    return resultArr;
}

const List = () => {

    const [pastData, setPastData] = useState([]);
    const [todayData, setTodayData] = useState([]);
    const [futureData, setFutureData] = useState([]);
    const [openFilter, setOpenFilter] = useState(false);

    useEffect(() => {
        axios.get("/v1/api/tasks").then((resp) => {
            let allTasks = resp.data.Task;
            let taskList = constructTasksList(allTasks);
            setPastData(constructTaskDataForTable(taskList[0]));
            setTodayData(constructTaskDataForTable(taskList[1]));
            setFutureData(constructTaskDataForTable(taskList[2]));
        })
    }, []);

    const handleFilterClose = () => {
        setOpenFilter(false);
    }

    return (
        <div>
            <ListFilter openDrawer={openFilter} handleClose={handleFilterClose}></ListFilter>
            <div className="font-heading padd-20 display-flex">
                Past Tasks &nbsp;&nbsp;&nbsp;
                <img src={FilterIcon} alt="Filter Icon not found" className="cursor-pointer" onClick={e => setOpenFilter(true)}/>
            </div>
            {pastData.length > 0 ? (
                <TableComponent
                    headerRow={["Task Title", "Description", "Due Date", "Priority", "Category", "Recurring"]}
                    bodyRow={pastData}
                    classList="list-tab-dim"
                />
            ) : (
                <div className="padd-20 font-bold">No Data Found</div>
            )}
            <div className="font-heading padd-20 display-flex">
                Today Tasks &nbsp;&nbsp;&nbsp;
                <img src={FilterIcon} alt="Filter Icon not found" className="cursor-pointer" onClick={e => setOpenFilter(!openFilter)}/>
            </div>
            {todayData.length > 0 ? (
                <TableComponent
                    headerRow={["Task Title", "Description", "Due Date", "Priority", "Category", "Recurring"]}
                    bodyRow={todayData}
                    classList="list-tab-dim"
                />
            ) : (
                <div className="padd-20 font-bold">No Data Found</div>
            )}
            <div className="font-heading padd-20 display-flex">
                Future Tasks &nbsp;&nbsp;&nbsp;
                <img src={FilterIcon} alt="Filter Icon not found" className="cursor-pointer" onClick={e => setOpenFilter(!openFilter)}/>
            </div>
            {futureData.length > 0 ? (
                <TableComponent
                    headerRow={["Task Title", "Description", "Due Date", "Priority", "Category", "Recurring"]}
                    bodyRow={futureData}
                    classList="list-tab-dim"
                />
            ) : (
                <div className="padd-20 font-bold">No Data Found</div>
            )}
        </div>
    );
}

export default List;