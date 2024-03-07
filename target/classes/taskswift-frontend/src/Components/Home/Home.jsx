import { Link } from "react-router-dom";
import "./Home.css";

import React from 'react'

const Home = () => {
  return (
    <div>
        <Link to="/create">Create</Link><br/>
        <Link to="/list">List</Link>
    </div>
  )
}

export default Home;