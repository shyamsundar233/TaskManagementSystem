import { Link } from "react-router-dom";
import "./Home.css";

import React from 'react'

const Home = () => {
  return (
      <div>
          <Link to="/ts/create">Create</Link><br/>
          <Link to="/ts/list">List</Link><br/>
          <Link to="/ts/addUser">Add User</Link><br/>
          <Link to="/logout">Logout</Link>
      </div>
  )
}

export default Home;