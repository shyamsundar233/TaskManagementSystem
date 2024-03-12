import "./Home.css";

import React from 'react'
import {Button} from "@mui/material";
import axios from "axios";

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
          <Button to="/logout" onClick={handleLogout}>Logout</Button>
      </div>
  )
}

export default Home;