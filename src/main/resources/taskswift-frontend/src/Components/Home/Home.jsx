import { Link } from "react-router-dom";
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
          <Link to="/ts/create">Create</Link><br/>
          <Link to="/ts/list">List</Link><br/>
          <Link to="/ts/addUser">Add User</Link><br/>
          <Link to="/ts/listUser">List User</Link><br/>
          <Button to="/logout" onClick={handleLogout}>Logout</Button>
      </div>
  )
}

export default Home;