import React, {useEffect, useState} from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';
import './AddUser.css';
import axios from "axios";
import {useAlert} from "../CustomAlert/CustomAlert";
import ClearTwoToneIcon from '@mui/icons-material/ClearTwoTone';

const AddUser = ({updateUsersList, closeAddUser}) => {
    const {showAlert} = useAlert();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [authority, setAuthority] = useState('');

    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [authorities, setAuthorities] = useState([]);
    const [existingUsers, setExistingUsers] = useState([]);

    useEffect(() => {
        axios.get("/v1/api/getAuthorities").then(resp => {
            setAuthorities(resp.data.Authorities);
            setAuthority(resp.data.Authorities[0]);
        })
        axios.get("/v1/api/existingUsers").then(resp => {
            debugger
            setExistingUsers(resp.data.User);
        })
    }, []);

    const handleViewPassword = (type) => {
        if (type === 'password') {
            setShowPassword(!showPassword);
        } else if (type === 'confirmPassword') {
            setShowConfirmPassword(!showConfirmPassword);
        }
    };

    const saveUser = () => {
        let userDetails = {
            "username" : username,
            "password" : password,
            "email" : email,
            "authority" : authority
        }
        axios.post("/v1/api/saveUser", userDetails).then((resp) => {
            if(resp.data.status === 200){
                showAlert(resp.data.User, "success");
                updateUsersList();
                closeAddUser();
            }else{
                showAlert(resp.data.User, "error");
            }
        })
    }

    const validateForm = (event) => {
        event.preventDefault();
        const username = event.target.username.value;
        const password = event.target.password.value;
        const confirmPassword = event.target.confirmPassword.value;
        const email = event.target.email.value;

        const inputRegex = /^[^\s]+[a-zA-Z0-9]+$/;
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()-_=+\\|\[\]{};:'",.<>?]).{8,}$/;

        let isErrorPresent = false;

        if (username.length < 1 || username.length > 20) {
            setErrorMessage('Please check the Username length');
            isErrorPresent = true;
        } else if(existingUsers.indexOf(username) > -1){
            setErrorMessage('Username already taken');
            isErrorPresent = true;
        } else if (password.length < 1 || password.length > 20) {
            setErrorMessage('Please check the Password length');
            isErrorPresent = true;
        } else if (password !== confirmPassword) {
            setErrorMessage('Password mismatch');
            isErrorPresent = true;
        } else if (!inputRegex.test(username) || !inputRegex.test(password)) {
            setErrorMessage('Invalid Username');
            isErrorPresent = true;
        } else if (!passwordRegex.test(password)) {
            setErrorMessage('Invalid Password');
            isErrorPresent = true;
        } else if(email === ""){
            setErrorMessage('Please enter a valid email');
            isErrorPresent = true;
        }
        else {
            setErrorMessage('');
        }

        if(!isErrorPresent){
            saveUser();
        }
    };

    return (
        <div className="registration-container-1">
            <div className="registration-container">
                <h1>Add Team Member</h1>
                <ClearTwoToneIcon className="add-user-cancel-icon cursor-pointer" onClick={closeAddUser}/>
                <form onSubmit={validateForm}>
                    <div className={`errorDiv ${errorMessage ? '' : 'dN'}`} id="customErrorDiv">{errorMessage}</div>
                    <div>
                        <input type="text" id="username" placeholder="Username" value={username} onChange={e => setUsername(e.target.value)}/>
                    </div>
                    <div>
                        <input type="email" id="email" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)}/>
                    </div>
                    <div className="password-container">
                        <input type={showPassword ? 'text' : 'password'} id="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)}/>
                        <span className="toggle-password" onClick={() => handleViewPassword('password')}>
                        <FontAwesomeIcon icon={showPassword ? faEyeSlash : faEye}/>
                    </span>
                    </div>
                    <div className="password-container">
                        <input type={showConfirmPassword ? 'text' : 'password'} id="confirmPassword"
                               placeholder="Confirm Password"/>
                        <span className="toggle-password" onClick={() => handleViewPassword('confirmPassword')}>
                        <FontAwesomeIcon icon={showConfirmPassword ? faEyeSlash : faEye}/>
                    </span>
                    </div>
                    <div>
                        <select value={authority} onChange={e => setAuthority(e.target.value)}>
                            {authorities.map((auth) => {
                                return <option key={auth} value={auth}>{auth}</option>
                            })}
                        </select>
                    </div>
                    <input className="btn-1" type="submit" value="Add User"/>
                </form>
            </div>
        </div>
    );
};

export default AddUser;
