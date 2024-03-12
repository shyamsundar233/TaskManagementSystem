import "./Home.css";

import React from 'react'
import {Button} from "@mui/material";
import axios from "axios";
import Notifications from "../Notifications/Notifications";

const Home = () => {

    const handleLogout = () =>{
        axios.post("/logout").then(resp => {
            if(resp.status === 200){
                window.location.href = "/login";
            }
        })
    }

  return (
      <div>
          <Notifications/>
      </div>
  )
}

export default Home;