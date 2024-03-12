import "./SideBar.css";
import tasksIcon from "../../Assets/tasks-2.svg";
import homeIcon from "../../Assets/house-2.svg";
import chartIcon from "../../Assets/chart-bar-trend-up.svg";
import usersIcon from "../../Assets/users.svg";
import connectIcon from "../../Assets/nodes.svg";
import settingsIcon from "../../Assets/gear-2.svg";
import {Link} from "react-router-dom";

const SideBar = () => {

    return (
        <div className="side-bar-container-1">
            <div className="side-bar-container-2">
                <Link to="/ts">
                    <img className="side-bar-icons-dim" src={homeIcon} alt="Home Icon is missing" />
                </Link>
                <Link to="/ts/listUser">
                    <img className="side-bar-icons-dim" src={usersIcon} alt="Tasks Icon is missing" />
                </Link>
                <Link to="#">
                    <img className="side-bar-icons-dim" src={chartIcon} alt="Chart Icon is missing"/>
                </Link>
                <Link to="#">
                    <img className="side-bar-icons-dim" src={connectIcon} alt="Connect Icon is missing"/>
                </Link>
                <Link to="/ts/list">
                    <img className="side-bar-icons-dim" src={tasksIcon} alt="Tasks Icon is missing" />
                </Link>
                <Link to="#">
                    <img className="side-bar-icons-dim" src={settingsIcon} alt="Settings Icon is missing"/>
                </Link>
            </div>
        </div>
    );
}

export default SideBar;