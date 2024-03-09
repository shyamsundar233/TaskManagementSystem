import "./ListFilter.css";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import {MenuItem, Select, TextField} from "@mui/material";
import TableContainer from "@mui/material/TableContainer";
import Paper from "@mui/material/Paper";
import React from "react";

const ListFilter = () => {

    const handleFilterChange = () =>{

    }

    return (
        <div style={listFilterParent}>
            <TableContainer component={Paper}>
                <Table>
                    <TableBody>
                        <TableRow>
                            <TableCell>
                                Title
                            </TableCell>
                            <TableCell>
                                {filterOptionsDropdown(handleFilterChange)}
                            </TableCell>
                            <TableCell>
                                <TextField id="standard-basic" variant="standard" />
                            </TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>
                                Description
                            </TableCell>
                            <TableCell>
                                {filterOptionsDropdown(handleFilterChange)}
                            </TableCell>
                            <TableCell>
                                <TextField id="standard-basic" variant="standard" />
                            </TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>
                                Due Date
                            </TableCell>
                            <TableCell>
                                {filterOptionsDropdown(handleFilterChange)}
                            </TableCell>
                            <TableCell>
                                <input style={dateFieldListFilter} type="date"/>
                            </TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>
                                Priority
                            </TableCell>
                            <TableCell>
                                {filterOptionsDropdown(handleFilterChange)}
                            </TableCell>
                            <TableCell>
                                <select className="select-field" id='priority' value={"Low Priority"}>
                                    <option value="Low Priority">Low Priority</option>
                                    <option value="Medium Priority">Medium Priority</option>
                                    <option value="High Priority">High Priority</option>
                                </select>
                            </TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>
                                Category
                            </TableCell>
                            <TableCell>
                                {filterOptionsDropdown(handleFilterChange)}
                            </TableCell>
                            <TableCell>
                                <select className="select-field" id='category' value="Food">
                                    <option value="Food">Food</option>
                                    <option value="Fuel">Fuel</option>
                                    <option value="Party">Party</option>
                                </select>
                            </TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>
                                Recurring
                            </TableCell>
                            <TableCell>
                                {filterOptionsDropdown(handleFilterChange)}
                            </TableCell>
                            <TableCell>
                                <select className="select-field" id='recurring' value="Daily">
                                    <option value="Daily">Daily</option>
                                    <option value="Weekly">Weekly</option>
                                    <option value="Yearly">Yearly</option>
                                </select>
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
}

const listFilterParent = {
    marginRight: "1020px",
    position: "fixed"
}

const filterOptionsDropdown = ({handleChange}) => {
    return (
        <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            value={"is"}
            sx={filterOptionsSx}
            onChange={handleChange}
        >
            <MenuItem value={"is"}>Is</MenuItem>
            <MenuItem value={"isnot"}>Is not</MenuItem>
            <MenuItem value={"contains"}>Contains</MenuItem>
        </Select>
    );
}

const filterOptionsSx = {
    height: "30px",
    fontSize: "15px"
}

const dateFieldListFilter = {
    width: "145px",
    padding: "10px",
    border: "1px solid #ccc",
    borderRadius: "5px"
}

export default ListFilter;