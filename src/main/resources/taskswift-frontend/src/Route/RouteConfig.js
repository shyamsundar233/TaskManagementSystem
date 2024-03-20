import React from 'react'
import {BrowserRouter, Routes, Route, Navigate} from 'react-router-dom'
import App from '../App'
import Home from '../Components/Home/Home'
import Create from '../Components/Create/Create'
import List from "../Components/List/List";
import AddUser from "../Components/AddUser/AddUser";
import ListUser from "../Components/ListUser/ListUser";
import View from "../Components/View/View";

const RouteConfig = () => {
  return (
    <BrowserRouter>
        <Routes>
            <Route path="/ts" element={<App/>}>
                <Route index element={<Home/>}/>
                <Route path="create" element={<Create/>}/>
                <Route path="list" element={<List/>}/>
                <Route path="view" element={<Navigate to="/ts/list"/>}/>
                <Route path="view/:taskId" element={<View/>}/>
                <Route path="listUser" element={<ListUser/>}/>
            </Route>
        </Routes>
    </BrowserRouter>
  )
}

export default RouteConfig