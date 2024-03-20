import "./View.css";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import TimeLine from "../TimeLine/TimeLine";

const priorityCss = {
  "High Priority" : {
    background : "#8B0000",
    color : "white"
  },
  "Medium Priority" : {
    background: "#FFA500"
  },
  "Low Priority" : {
    background : "#ADD8E6"
  }
}

const View = ({data}) => {

  const { taskId } = useParams();
  const [taskTitle, setTaskTitle] = useState('');
  const [taskDesc, setTaskDesc] = useState('');
  const [dueDate, setDueDate] = useState('');
  const [taskStatus, setTaskStatus] = useState('');
  const [taskPriority, setTaskPriority] = useState('');
  const [taskCategory, setTaskCategory] = useState('');
  const [taskAttachment, setTaskAttachment] = useState('');
  const [taskRecurring, setTaskRecurring] = useState('');
  const [prioritySx, setPrioritySx] = useState(priorityCss.low);

  useEffect(() => {
    axios.get("/v1/api/tasks/" + taskId).then(resp => {
      if(resp.data.Task.length > 0){
        constructInitialData(resp.data.Task[0]);
      }
    })
  }, []);

  const constructInitialData = (taskDetails) => {
    setTaskTitle(taskDetails.taskTitle);
    setTaskDesc(taskDetails.taskDesc);
    setDueDate(taskDetails.dueDate);
    setTaskStatus(taskDetails.taskStatus);
    setTaskPriority(taskDetails.taskPriority);
    setPrioritySx(priorityCss[taskDetails.taskPriority]);
    setTaskCategory(taskDetails.taskCategory);
    setTaskAttachment(taskDetails.taskAttachment);
    setTaskRecurring(taskDetails.taskRecurring);
  }

  return (
      <div className="display-flex view-par-cont-1">
        <div className="padd-20 view-cont-1">
          <div className="width-100 font-heading view-title-1">
            <span>{taskTitle}</span>
          </div>

          <div className="view-cont-2">
            <div className="display-flex cont-2-row-1">
              <div className="font-bold cont-2-col-1">Due Date</div>
              <div className="cont-2-col-2">{dueDate}</div>
            </div>
            <div className="display-flex cont-2-row-1">
              <div className="font-bold cont-2-col-1">Status</div>
              <div className="cont-2-col-2">{taskStatus}</div>
            </div>
            <div className="display-flex cont-2-row-1">
              <div className="font-bold cont-2-col-1">Category</div>
              <div className="cont-2-col-2">{taskCategory}</div>
            </div>
            <div className="display-flex cont-2-row-1 priority-div-cont">
              <div className="font-bold cont-2-col-1">Priority</div>
              <div className="cont-2-col-2 priority-div" style={prioritySx}>{taskPriority}</div>
            </div>
            <div className="display-flex cont-2-row-1">
              <div className="font-bold cont-2-col-1">Assignee</div>
              <div className="cont-2-col-2">{"Thomas Shelby"}</div>
            </div>
            <div className="display-flex cont-2-row-1">
              <div className="font-bold cont-2-col-1">Description</div>
              <div className="cont-2-col-2">{taskDesc}</div>
            </div>
            <div className="display-flex cont-2-row-1">
              <div className="font-bold cont-2-col-1">Recurring</div>
              <div className="cont-2-col-2">{taskRecurring}</div>
            </div>
            <div className="display-flex cont-2-row-1">
              <div className="font-bold cont-2-col-1">Attachments</div>
              <div className="cont-2-col-2">{"No Attachments"}</div>
            </div>
          </div>

          <div className="view-cont-3">
            <div className="font-heading comm-head">
              <span>Comments</span>
            </div>
          </div>
        </div>

        <div className="time-line-parent">
          <TimeLine/>
        </div>

      </div>
  );
}

export default View;