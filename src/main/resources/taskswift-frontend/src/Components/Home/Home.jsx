import "./Home.css";

import React, {useEffect, useState} from 'react'
import Notifications from "../Notifications/Notifications";
import StatsComponent from "../StatsComponent/StatsComponent";
import TableComponent from "../TableComponent/TableComponent";
import axios from "axios";

const Home = () => {

    const [todayTask, setTodayTask] = useState([]);

    useEffect(() => {
        axios.get("/v1/api/todayTask").then(resp => {
            if(resp.data.todayTask){
                constructTodayTask(resp.data.todayTask);
            }
        })
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

  return (
      <div className="display-flex">
          <Notifications/>
          <div className="display-center-col">
              <div className="height-width-auto">
                  <div className="title-font padd-20 margin-top-left-20 font-heading">Statistics</div>
                  <div className="display-center home-graph-cont-1">
                      <StatsComponent title="Total tasks solved" className="home-stats-comp-col-1"/>
                      <StatsComponent title="This week tasks" className="home-stats-comp-col-2"/>
                      <StatsComponent title="Unsolved tasks" className="home-stats-comp-col-3"/>
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