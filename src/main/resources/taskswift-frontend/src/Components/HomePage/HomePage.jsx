import "./HomePage.css";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import React, {useEffect, useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";
import StatsComponent from "../StatsComponent/StatsComponent";
import Typography from "@mui/material/Typography";
import Table from "@mui/material/Table";
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import {PieChart} from "@mui/x-charts/PieChart";

const constructGraphForTask = (tasksList) => {
    tasksList.sort((task1, task2) => {
        const dateA = new Date(task1.dueDate);
        const dateB = new Date(task2.dueDate);
        return dateA - dateB;
    })

    const parseDate = dateString => new Date(dateString);
    const taskCounts = tasksList.reduce((acc, task) => {
        const dueDate = task.dueDate;
        acc[dueDate] = (acc[dueDate] || 0) + 1;
        return acc;
    }, {});

    const data = Object.entries(taskCounts).map(([date, count]) => ({
        x: parseDate(date).getDate(),
        y: count
    }));
    let res = [{x : 0, y : 0}]
    res = res.concat(data);
    return res;
}

const HomePage = () => {

    const [todayTask, setTodayTask] = useState([]);
    const [toDoTasksCount, setToDoTasksCount] = useState(0);
    const [toDoTask, setToDoTask] = useState([]);
    const [weeklyTasksCount, setWeeklyTasksCount] = useState(0);
    const [weeklyTasks, setWeeklyTasks] = useState([]);
    const [highPriorityTasksCount, setHighPriorityTasksCount] = useState(0);
    const [highPriorityTasks, setHighPriorityTasks] = useState([]);
    const [usersList, setUsersList] = useState([]);
    const [contributorsList, setContributorsList] = useState([]);

    useEffect(() => {

        axios.get("/v1/api/homePage").then(resp => {
            if(resp.data.todayTask){
                constructTodayTask(resp.data.todayTask);
            }
            if(resp.data.weeklyTasks){
                setWeeklyTasksCount(resp.data.weeklyTasks.length);
                let graphArr = constructGraphForTask(resp.data.weeklyTasks);
                setWeeklyTasks(graphArr);
            }
            if(resp.data.toDoTasks){
                setToDoTasksCount(resp.data.toDoTasks.length > 0 ? resp.data.toDoTasks.length : 0);
                let graphArr = constructGraphForTask(resp.data.toDoTasks);
                setToDoTask(graphArr)
            }

            if(resp.data.highPriorityTasks){
                setHighPriorityTasksCount(resp.data.highPriorityTasks.length > 0 ? resp.data.highPriorityTasks.length : 0);
                let graphArr = constructGraphForTask(resp.data.highPriorityTasks);
                setHighPriorityTasks(graphArr)
            }
        })

        axios.get("/v1/api/users").then(resp => {
            constructUsersList(resp.data.Users)
        })

        axios.get("/v1/api/leadingContributors").then(resp => {
            constructContributors(resp.data.topContributors);
        })
    }, []);

    const constructContributors = (usersList) => {
        let id = 0;
        let resultArr = [];
        for (let index = 0; index < usersList.length; index++) {
            let userObj = usersList[index];
            let count = userObj.count;
            let usersArr = userObj.user;
            for (let index2 = 0; index2 < usersArr.length; index2++) {
                let userDetailsObj = usersArr[index2];
                resultArr.push({
                    id : id++,
                    label : userDetailsObj.username,
                    value : count
                })
            }
        }
        setContributorsList(resultArr);
    }

    const constructTodayTask = (tasksList) => {
        let tasksArr = [];
        for (let index in tasksList) {
            let task = tasksList[index];
            tasksArr.push({
                taskTitle: <Link to={`/ts/view/${task.taskId}`} className="link">{task.taskTitle}</Link>,
                taskStatus: task.taskStatus,
                taskPriority: task.taskPriority,
                taskCategory: task.taskCategory,
                taskRecurring: task.taskRecurring
            });
        }
        setTodayTask(tasksArr);
    }

    const constructUsersList = (usersList) => {
        let usersArr = [];
        for (let index in usersList) {
            usersArr.push({username : [usersList[index].username]});
        }
        setUsersList(usersArr);
    }

    return (
        <Container maxWidth="xl" sx={{marginTop: "50px", display: 'block'}}>
            <Container maxWidth="xl">
                <Box sx={{display: {xs : 'block', md : 'flex'}, justifyContent: {xs: 'none', md: 'center'}}}>
                    <Box sx={{marginRight: {xs : '0', md: '50px'}}}>
                        <Box sx={{display : 'block'}}>
                            <Box>
                                <Typography className="stats-title">
                                    Statistics
                                </Typography>
                            </Box>
                            <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
                                <StatsComponent title="To Do Tasks" value={toDoTasksCount} graphData={toDoTask} className="stats-comp stats-comp-1"/>
                                <StatsComponent title="This Week Tasks" value={weeklyTasksCount} graphData={weeklyTasks} className="stats-comp stats-comp-2"/>
                                <StatsComponent title="High Priorities" value={highPriorityTasksCount} graphData={highPriorityTasks} className="stats-comp stats-comp-3"/>
                            </Box>
                        </Box>
                        <Box sx={{ flexGrow: 1, display: { xs: 'block', md: 'none' } }}>
                            <StatsComponent title="To Do Tasks" value={toDoTasksCount} graphData={toDoTask} className="stats-comp stats-comp-1"/>
                            <StatsComponent title="This Week Tasks" value={weeklyTasksCount} graphData={weeklyTasks} className="stats-comp stats-comp-2"/>
                            <StatsComponent title="High Priorities" value={highPriorityTasksCount} graphData={highPriorityTasks} className="stats-comp stats-comp-3"/>
                        </Box>
                    </Box>
                    <Box sx={{marginTop: {xs : '20px', md: '0'}}}>
                        <Box>
                            <Typography className="stats-title">
                                Top Contributors
                            </Typography>
                        </Box>
                        <Box>
                            {contributorsList.length > 0 ?
                                <PieChart
                                    series={[
                                        {
                                            data: contributorsList,
                                            innerRadius: 20,
                                            outerRadius: 100,
                                            paddingAngle: 5,
                                            cornerRadius: 5
                                        },
                                    ]}
                                    width={400}
                                    height={200}
                                />
                                :
                                <div>No Contributors So Far !!!</div>
                            }
                        </Box>
                    </Box>
                </Box>
            </Container>
            <Container maxWidth="xl" sx={{marginTop: {md: '50px'}}}>
                <Box sx={{display: {xs : 'block', md : 'flex'}, justifyContent: {xs: 'none', md: 'center'}}}>
                    <Box sx={{marginRight: {xs : '0', md: '50px'}, marginTop: {xs : '20px', md: '0'}, width: {md: "calc(100% - 30%)"}}}>
                        <Box>
                            <Typography className="stats-title">
                                Today Task
                            </Typography>
                        </Box>
                        <Box className="margin-10">
                            {todayTask.length > 0 ?
                                <TableContainer component={Paper} sx={{maxHeight: 400}}>
                                    <Table stickyHeader="true" sx={{minWidth: 800}} aria-label="simple table">
                                        <TableHead>
                                            <TableRow>
                                                <TableCell>Task Title</TableCell>
                                                <TableCell>Status</TableCell>
                                                <TableCell>Priority</TableCell>
                                                <TableCell>Category</TableCell>
                                                <TableCell>Recurring</TableCell>
                                            </TableRow>
                                        </TableHead>
                                        <TableBody>
                                            {todayTask.map((row) => (
                                                <TableRow
                                                    key={row.name}
                                                    sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                                >
                                                    <TableCell component="th" scope="row">
                                                        {row.taskTitle}
                                                    </TableCell>
                                                    <TableCell>{row.taskStatus}</TableCell>
                                                    <TableCell>{row.taskPriority}</TableCell>
                                                    <TableCell>{row.taskCategory}</TableCell>
                                                    <TableCell>{row.taskRecurring}</TableCell>
                                                </TableRow>
                                            ))}
                                        </TableBody>
                                    </Table>
                                </TableContainer>
                                :
                                <div className="padd-20 font-sub-heading display-center">No today tasks !!!</div>
                            }
                        </Box>
                    </Box>
                    <Box sx={{marginTop: {xs : '20px', md: '0'}, width: {md: "calc(100% - 70%)"}}}>
                        <Box>
                            <Typography className="stats-title">
                                Team Peers
                            </Typography>
                        </Box>
                        <Box className="margin-10">
                            {usersList.length > 0 ?
                                <TableContainer component={Paper}>
                                    <Table sx={{minWidth: 500}} aria-label="simple table">
                                        <TableHead>
                                            <TableRow>
                                                <TableCell>Username</TableCell>
                                            </TableRow>
                                        </TableHead>
                                        <TableBody>
                                            {usersList.map((row) => (
                                                <TableRow
                                                    key={row.name}
                                                    sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                                >
                                                    <TableCell component="th" scope="row">
                                                        {row.username}
                                                    </TableCell>
                                                </TableRow>
                                            ))}
                                        </TableBody>
                                    </Table>
                                </TableContainer>
                                :
                                <div className="padd-20 font-sub-heading display-center">No team members !!!</div>
                            }
                        </Box>
                    </Box>
                </Box>
            </Container>
        </Container>
    );

}

export default HomePage;