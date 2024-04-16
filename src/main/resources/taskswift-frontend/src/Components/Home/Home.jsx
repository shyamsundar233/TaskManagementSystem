import "./Home.css";

import React, {useEffect, useState} from 'react'
import StatsComponent from "../StatsComponent/StatsComponent";
import TableComponent from "../TableComponent/TableComponent";
import axios from "axios";
import {Link} from "react-router-dom";
import { PieChart } from '@mui/x-charts/PieChart';

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

const Home = () => {

    const [todayTask, setTodayTask] = useState([]);
    const [toDoTasksCount, setToDoTasksCount] = useState(0);
    const [toDoTask, setToDoTask] = useState([]);
    const [weeklyTasksCount, setWeeklyTasksCount] = useState(0);
    const [weeklyTasks, setWeeklyTasks] = useState([]);
    const [highPriorityTasksCount, setHighPriorityTasksCount] = useState(0);
    const [highPriorityTasks, setHighPriorityTasks] = useState([]);
    const [usersList, setUsersList] = useState([]);

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
    }, []);

    const constructTodayTask = (tasksList) => {
        let tasksArr = [];
        for (let index in tasksList) {
            let task = tasksList[index];
            let tempArr = [];
            tempArr.push(<Link to={`/ts/view/${task.taskId}`} className="link">{task.taskTitle}</Link>);
            tempArr.push(task.taskStatus);
            tempArr.push(task.taskPriority);
            tempArr.push(task.taskCategory);
            tempArr.push(task.taskRecurring);
            tasksArr.push(tempArr);
        }
        setTodayTask(tasksArr);
    }

    const constructUsersList = (usersList) => {
        let usersArr = [];
        for (let index in usersList) {
            usersArr.push([usersList[index].username]);
        }
        setUsersList(usersArr);
    }

  return (
      <div className="display-center">
          <div className="display-center-col scroll-div home-section-1">
              <div className="height-width-auto stats-cont">
                  <div className="title-font font-heading padd-20">Statistics</div>
                  <div className="display-center home-graph-cont-1">
                      <StatsComponent title="To Do Tasks" value={toDoTasksCount} graphData={toDoTask} className="home-stats-comp-col-1"/>
                      <StatsComponent title="This Week Tasks" value={weeklyTasksCount} graphData={weeklyTasks} className="home-stats-comp-col-2"/>
                      <StatsComponent title="High Priorities" value={highPriorityTasksCount} graphData={highPriorityTasks} className="home-stats-comp-col-3"/>
                  </div>
              </div>
              <div className="height-width-auto task-cont">
                  <div className="title-font font-heading task-tab-cont">
                      <div className="padd-20"> My Today's Tasks </div>
                      {todayTask.length > 0 ?
                          <TableComponent
                              headerRow={["Task Title", "Status", "Priority", "Category", "Recurring"]}
                              bodyRow={todayTask}
                              classList="height-width-auto scroll-div home-tab-dim"
                              height="calc(100% - 5%)"
                          />
                          :
                          <div className="padd-20 font-sub-heading display-center">No Tasks today !!!</div>
                      }
                  </div>
              </div>
          </div>
          <div className="home-section-2">
              <div className="users-list-cont">
                  <div className="title-font font-heading padd-20">Team Members</div>
                  {usersList.length > 0 ?
                      <TableComponent
                          headerRow={["Username"]}
                          bodyRow={usersList}
                          classList="scroll-div home-users-tab-dim"
                          height="calc(100% - 20%)"
                      />
                      :
                      <div className="padd-20 font-sub-heading display-center">No team members !!!</div>
                  }
              </div>
              <div className="leading-list-cont">
                  <div className="title-font font-heading padd-20">Leading Contributors</div>
                  <PieChart
                      series={[
                          {
                              data: [
                                  { id: 0, value: 20, label: 'Shyam' },
                                  { id: 1, value: 40, label: 'Archana' },
                                  { id: 2, value: 15, label: 'Dinesh' },
                                  { id: 3, value: 20, label: 'Kumar' },
                                  { id: 4, value: 5, label: 'Nakul' },
                              ],
                              innerRadius: 20,
                              outerRadius: 100,
                              paddingAngle: 5,
                              cornerRadius: 5
                          },
                      ]}
                      width={400}
                      height={200}
                  />
              </div>
          </div>
      </div>
  )
}

export default Home;