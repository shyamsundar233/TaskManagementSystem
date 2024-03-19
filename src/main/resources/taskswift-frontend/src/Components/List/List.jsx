import "./List.css";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import axios from "axios";
import {useEffect, useState} from "react";
import ListFilter from "../ListFilter/ListFilter";
import {Link} from "react-router-dom";

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

const List = () => {

    const [pastData, setPastData] = useState([]);
    const [todayData, setTodayData] = useState([]);
    const [futureData, setFutureData] = useState([]);

    useEffect(() => {
        axios.get("/v1/api/tasks").then((resp) => {
            let allTasks = resp.data.Task;
            let taskList = constructTasksList(allTasks);
            setPastData(taskList[0]);
            setTodayData(taskList[1]);
            setFutureData(taskList[2]);
        })
    }, []);

    return (
        <div style={parentDivSx}>
            <ListFilter></ListFilter><br/>
            <TableContainer component={Paper} sx={tableContainerSx}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Task Title</TableCell>
                            <TableCell align="right">Description</TableCell>
                            <TableCell align="right">Due Date</TableCell>
                            <TableCell align="right">Priority</TableCell>
                            <TableCell align="right">Category</TableCell>
                            <TableCell align="right">Recurring</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        <TableRow
                            key="Past Tasks"
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row" colSpan={6} sx={TableInnerHeadingSx}>Past Tasks</TableCell>
                        </TableRow>
                        {pastData.map((row) => (
                            <TableRow
                                key={row.title}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell component="th" scope="row">
                                    <Link className="link" to={`/ts/view/${row.id}`} >{row.title}</Link>
                                </TableCell>
                                <TableCell align="right">{row.desc}</TableCell>
                                <TableCell align="right">{row.dueDate}</TableCell>
                                <TableCell align="right">{row.priority}</TableCell>
                                <TableCell align="right">{row.category}</TableCell>
                                <TableCell align="right">{row.recurring}</TableCell>
                            </TableRow>
                        ))}
                        <TableRow
                            key="Past Tasks"
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row" colSpan={6} sx={TableInnerHeadingSx}>Today's Tasks</TableCell>
                        </TableRow>
                        {todayData.map((row) => (
                            <TableRow
                                key={row.title}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell component="th" scope="row">
                                    <Link className="link" to={`/ts/view/${row.id}`} >{row.title}</Link>
                                </TableCell>
                                <TableCell align="right">{row.desc}</TableCell>
                                <TableCell align="right">{row.dueDate}</TableCell>
                                <TableCell align="right">{row.priority}</TableCell>
                                <TableCell align="right">{row.category}</TableCell>
                                <TableCell align="right">{row.recurring}</TableCell>
                            </TableRow>
                        ))}
                        <TableRow
                            key="Past Tasks"
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row" colSpan={6} sx={TableInnerHeadingSx}>Future Tasks</TableCell>
                        </TableRow>
                        {futureData.map((row) => (
                            <TableRow
                                key={row.title}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell component="th" scope="row">
                                    {row.title}
                                </TableCell>
                                <TableCell align="right">{row.desc}</TableCell>
                                <TableCell align="right">{row.dueDate}</TableCell>
                                <TableCell align="right">{row.priority}</TableCell>
                                <TableCell align="right">{row.category}</TableCell>
                                <TableCell align="right">{row.recurring}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
}

const parentDivSx = {
    display : "flex",
    justifyContent : "center"
}

const tableContainerSx = {
    width : "1000px",
    marginLeft: "400px"
}

const TableInnerHeadingSx = {
    fontWeight : "bolder",
    fontSize : "20px",
    textAlign : "center"
}

export default List;