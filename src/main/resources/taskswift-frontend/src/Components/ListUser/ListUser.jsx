import "./ListUser.css";
import {useEffect, useState} from "react";
import axios from "axios";
import {TableContainer, Paper} from "@mui/material";
import Table from "@mui/material/Table";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import TableBody from "@mui/material/TableBody";

const ListUser = () =>{

    const [users, setUsers] = useState([]);

    useEffect(() => {
        axios.get("/v1/api/users").then(resp => {
            setUsers(resp.data.Users);
        })
    }, []);

    return (
        <div className="parent-div-table">
            <TableContainer component={Paper} className="list-user-container">
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell align="center" className="user-table-header">Username</TableCell>
                            <TableCell align="center" className="user-table-header">Email</TableCell>
                            <TableCell align="center" className="user-table-header">Authority</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {users.length > 0 && users.map(user => {
                            return (
                                <TableRow>
                                    <TableCell align="center">{user.username}</TableCell>
                                    <TableCell align="center">{user.email}</TableCell>
                                    <TableCell align="center">{user.authority}</TableCell>
                                </TableRow>
                            );
                        })}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );

}

export default ListUser;