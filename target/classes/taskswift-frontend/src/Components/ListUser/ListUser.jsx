import "./ListUser.css";
import React, {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {Avatar, Button} from "@mui/material";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';


const ListUser = () =>{

    const [users, setUsers] = useState([]);
    const [openAddUser, setOpenAddUser] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        updateUsers();
    }, []);

    const handleCreate = () => {
        setOpenAddUser(true);
    }

    const handleCloseCreate = () => {
        setOpenAddUser(false);
    }

    const updateUsers = () => {
        axios.get("/v1/api/users").then(resp => {
            setUsers(resp.data.Users);
            handleCloseCreate();
        })
    }

    return (
        <Container maxWidth="xl" sx={{marginTop: "50px"}}>
            <Container maxWidth="xl">
                <Box sx={{display: 'flex', alignItems: 'center'}}>
                    <Typography className="app-font font-heading">Team Members</Typography>
                    <Button className="ts-btn" sx={{height: "40px", width: "120px", marginLeft: "50px"}}>+ Add User</Button>
                </Box>
            </Container>
            <Container maxWidth="xl">
                <Box sx={{marginTop: "50px", display: {xs: "block", md: "flex"}}}>
                    {users.length > 0 ?
                        users.map((user) => {
                            {userCard(user.username, user.authority, user.email, "+91-98764-98456", "Chennai")}
                        })
                        :
                        <Box>No Users Found!!!</Box>
                    }
                </Box>
            </Container>
        </Container>
    );

}

const userCard = (username, profile, email, phone, loc) => {
    return (
        <Card sx={{ maxWidth: 500, maxHeight: 300, backgroundColor: "#B99470", color: "white", margin: "10px"}}>
            <CardContent sx={{display: 'flex', alignItems: 'center', justifyContent: 'center'}}>
                <Box>
                    <Avatar alt={username} src="/static/images/avatar/2.jpg" sx={{width: "80px", height: "80px", backgroundColor: "white", color: "#B99470"}}/>
                </Box>
                <Box sx={{marginLeft: '40px'}}>
                    <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                        {profile}
                    </Typography>
                    <Typography variant="h5" component="div">
                        {username}
                    </Typography>
                    <Typography sx={{ mb: 1.5 }} color="text.secondary">
                        {email}
                    </Typography>
                    <Typography variant="body2">
                        {phone}, {loc}
                    </Typography>
                </Box>
            </CardContent>
            <CardActions sx={{marginLeft: "15px"}}>
                <Typography sx={{ mb: 1.5 }} color="text.secondary">
                    Created On: 15/04/2024
                </Typography>
            </CardActions>
        </Card>
    )
}

export default ListUser;