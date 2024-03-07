import React from 'react'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import App from '../App'
import Home from '../Components/Home/Home'
import Create from '../Components/Create/Create'
import List from "../Components/List/List";

const RouteConfig = () => {
  return (
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<App/>}>
                <Route index element={<Home/>}/>
                <Route path="create" element={<Create/>}/>
                <Route path="list" element={<List/>}/>
            </Route>
        </Routes>
    </BrowserRouter>
  )
}

export default RouteConfig