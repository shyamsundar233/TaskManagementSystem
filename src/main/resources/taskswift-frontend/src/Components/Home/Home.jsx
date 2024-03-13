import "./Home.css";

import React from 'react'
import Notifications from "../Notifications/Notifications";
import StatsComponent from "../StatsComponent/StatsComponent";

const Home = () => {

  return (
      <div className="display-flex">
          <Notifications/>
          <div className="height-width-100">
              <div className="title-font padd-20 margin-top-left-20 font-heading">Statistics</div>
              <div className="display-center home-graph-cont-1">
                  <StatsComponent title="Total tasks solved" className="home-stats-comp-col-1"/>
                  <StatsComponent title="This week tasks" className="home-stats-comp-col-2"/>
                  <StatsComponent title="Unsolved tasks" className="home-stats-comp-col-3"/>
              </div>
          </div>
      </div>
  )
}

export default Home;