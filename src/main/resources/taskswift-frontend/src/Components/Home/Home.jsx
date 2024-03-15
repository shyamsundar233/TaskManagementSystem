import "./Home.css";

import React, {useEffect, useState} from 'react'
import Notifications from "../Notifications/Notifications";
import StatsComponent from "../StatsComponent/StatsComponent";
import TableComponent from "../TableComponent/TableComponent";
import axios from "axios";

const requestsUrl = [
    "/v1/api/todayTask",
    "/v1/api/currentWeekTask"
]

const Home = () => {

    const [todayTask, setTodayTask] = useState([]);
    const [weeklyTasksCount, setWeeklyTasksCount] = useState(0);
    const [weeklyTasks, setWeeklyTasks] = useState([]);

    const requests = requestsUrl.map(url => axios.get(url));

    useEffect(() => {

        Promise.all(requests).then(responses => {
            if(responses[0].data.todayTask){
                constructTodayTask(responses[0].data.todayTask);
            }
            if(responses[1].data.weeklyTasks){
                setWeeklyTasksCount(responses[1].data.weeklyTasks.length);
                constructWeeklyTask(responses[1].data.weeklyTasks);
            }
        }).catch(
            error => {
                console.error('Error:', error);
            });
    }, []);

    const constructTodayTask = (tasksList) => {
        let tasksArr = [];
        tasksList.forEach(task => {
            let tempArr = [];
            tempArr.push(task.taskTitle);
            tempArr.push(task.taskDesc);
            tempArr.push(task.taskPriority);
            tempArr.push(task.taskCategory);
            tempArr.push(task.taskRecurring);
            tasksArr.push(tempArr);
        })
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
        setWeeklyTasks(data);
    }

  return (
      <div className="display-flex">
          <Notifications/>
          <div className="display-center-col">
              <div className="height-width-auto">
                  <div className="title-font padd-20 margin-top-left-20 font-heading">Statistics</div>
                  <div className="display-center home-graph-cont-1">
                      <StatsComponent title="Total tasks solved" value={430} className="home-stats-comp-col-1"/>
                      <StatsComponent title="This week tasks" value={weeklyTasksCount} graphData={weeklyTasks} className="home-stats-comp-col-2"/>
                      <StatsComponent title="Unsolved tasks" value={430} className="home-stats-comp-col-3"/>
                  </div>
              </div>
              <div className="height-width-auto">
                  <div className="title-font padd-20 margin-top-left-20 font-heading">
                      <div className="padd-20"> My Today's Tasks </div>
                      <TableComponent
                          headerRow={["Task Title", "Description", "Priority", "Category", "Recurring"]}
                          bodyRow={todayTask}
                      />
                  </div>
              </div>
          </div>
      </div>
  )
}

export default Home;