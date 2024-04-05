import "./Home.css";

import React, {useEffect, useState} from 'react'
import Notifications from "../Notifications/Notifications";
import StatsComponent from "../StatsComponent/StatsComponent";
import TableComponent from "../TableComponent/TableComponent";
import axios from "axios";
import {Link} from "react-router-dom";

const Home = () => {

    const [todayTask, setTodayTask] = useState([]);
    const [toDoTasks, setToDoTasks] = useState(0);
    const [weeklyTasksCount, setWeeklyTasksCount] = useState(0);
    const [weeklyTasks, setWeeklyTasks] = useState([]);
    const [highPriorityTasks, setHighPriorityTasks] = useState(0);

    useEffect(() => {

        axios.get("/v1/api/homePage").then(resp => {
            debugger
            if(resp.data.todayTask){
                constructTodayTask(resp.data.todayTask);
            }
            if(resp.data.weeklyTasks){
                setWeeklyTasksCount(resp.data.weeklyTasks.length);
                constructWeeklyTask(resp.data.weeklyTasks);
            }
            if(resp.data.toDoTasks){
                setToDoTasks(resp.data.toDoTasks.length > 0 ? resp.data.toDoTasks.length : 0);
            }

            if(resp.data.highPriorityTasks){
                setHighPriorityTasks(resp.data.highPriorityTasks.length > 0 ? resp.data.highPriorityTasks.length : 0);
            }
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

    const constructWeeklyTask = (tasksList) => {
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
        setWeeklyTasks(res);
    }

  return (
      <div className="display-flex pos-fixed">
          <Notifications/>
          <div className="display-center-col scroll-div">
              <div className="height-width-auto">
                  <div className="title-font padd-20 margin-top-left-20 font-heading">Statistics</div>
                  <div className="display-center home-graph-cont-1">
                      <StatsComponent title="To Do Tasks" value={toDoTasks} className="home-stats-comp-col-1"/>
                      <StatsComponent title="This Week Tasks" value={weeklyTasksCount} graphData={weeklyTasks} className="home-stats-comp-col-2"/>
                      <StatsComponent title="High Priorities" value={highPriorityTasks} className="home-stats-comp-col-3"/>
                  </div>
              </div>
              <div className="height-width-auto">
                  <div className="title-font padd-20 margin-top-left-20 font-heading">
                      <div className="padd-20"> My Today's Tasks </div>
                      {todayTask.length > 0 ?
                          <TableComponent
                              headerRow={["Task Title", "Status", "Priority", "Category", "Recurring"]}
                              bodyRow={todayTask}
                              classList="home-tab-dim"
                          />
                          :
                          <div className="padd-20 font-sub-heading display-center">No Tasks today !!!</div>
                      }
                  </div>
              </div>
          </div>
      </div>
  )
}

export default Home;