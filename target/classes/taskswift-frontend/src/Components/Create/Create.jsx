import React, { useState } from 'react';
import "./Create.css";
import axios from 'axios';
import { useAlert } from '../CustomAlert/CustomAlert';

const Create = () => {

    const {showAlert} = useAlert();

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [dueDate, setDueDate] = useState('');
    const [priority, setPriority] = useState('Low Priority');
    const [category, setCategory] = useState('Food');
    const [attachment, setAttachment] = useState(null);
    const [recurring, setRecurring] = useState('Daily');

    const handleSubmit = () => {
        if(!validateInputs()){
            return false;
        }
        let payLoad = {
            "taskTitle" : title,
            "taskDesc" : description,
            "dueDate" : dueDate,
            "taskPriority" : priority,
            "taskCategory" : category,
            "taskAttachment" : attachment,
            "taskRecurring" : recurring
        };
        axios.post("http://localhost:8080/v1/api/tasks", payLoad).then((resp) => {
            let [message, severity] = constructMsg(resp.data.Task);
            showAlert(message, severity);
            resetData();
        })
    }

    const constructMsg = (response) => {
        let errorIndex = response.lastIndexOf(":");
        if(errorIndex > -1){
            return [response.substring(errorIndex + 1).trim(), 'error'];
        }else{
            return [response, 'success'];
        }
    }

    const validateInputs = () => {
        if(title.length < 1 || title.length > 20){
            showAlert("Please check the Title length", "error");
            return false;
        }else if(description.length < 1 || description.length > 200){
            showAlert("Please check the Description Length", "error");
            return false;
        }else if(dueDate.length < 1){
            showAlert("Please set the Due Date","error")
            return false;
        }else{
            var titleRegex = /^[^\s]+[a-zA-Z0-9\s]*[a-zA-Z0-9]$/;
            if(!titleRegex.test(title)){
                showAlert("Please enter a valid title","error")
                return false;
            }
        }
        return true;
    }

    const resetData = () => {
        setTitle('');
        setDescription('');
        setDueDate('');
        setPriority('Low Priority');
        setCategory('Food');
        setAttachment(null);
        setRecurring('Daily');
    }

    return (
        <div className="container">
            <h1 className="heading">Create Task</h1>
            <div className="input-group">
                <label className="label" htmlFor='title'>Title</label>
                <input className="input-field" type='text' id='title' value={title}
                       onChange={(e) => setTitle(e.target.value)}/>
            </div>

            <div className="input-group">
                <label className="label" htmlFor='description'>Description</label>
                <input className="input-field" type='text' id='description' value={description}
                       onChange={(e) => setDescription(e.target.value)}/>
            </div>

            <div className="input-group">
                <label className="label" htmlFor='dueDate'>Due Date</label>
                <input className="input-field" type='date' id='dueDate' value={dueDate}
                       onChange={(e) => setDueDate(e.target.value)}/>
            </div>

            <div className="input-group">
                <label className="label" htmlFor='priority'>Priority</label>
                <select className="select-field" id='priority' value={priority}
                        onChange={(e) => setPriority(e.target.value)}>
                    <option value="Low Priority">Low Priority</option>
                    <option value="Medium Priority">Medium Priority</option>
                    <option value="High Priority">High Priority</option>
                </select>
            </div>

            <div className="input-group">
                <label className="label" htmlFor='category'>Category</label>
                <select className="select-field" id='category' value={category}
                        onChange={(e) => setCategory(e.target.value)}>
                    <option value="Food">Food</option>
                    <option value="Fuel">Fuel</option>
                    <option value="Party">Party</option>
                </select>
            </div>

            <div className="input-group file-field">
                <label className="label" htmlFor="attachment">Attach</label>
                <input className="input-field" type='file' id="attachment" value={attachment}
                       onChange={(e) => setAttachment(e.target.files[0])}/>
            </div>

            <div className="input-group">
                <label className="label" htmlFor='recurring'>Recurring</label>
                <select className="select-field" id='recurring' value={recurring}
                        onChange={(e) => setRecurring(e.target.value)}>
                    <option value="Daily">Daily</option>
                    <option value="Weekly">Weekly</option>
                    <option value="Yearly">Yearly</option>
                </select>
            </div>

            <div className="input-group">
                <button className="button" onClick={handleSubmit}>Submit</button>
            </div>
        </div>
    );
}

export default Create;
