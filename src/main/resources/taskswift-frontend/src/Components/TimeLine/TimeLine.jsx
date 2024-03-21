import "./TimeLine.css";
import {useEffect, useState} from "react";
import axios from "axios";

const TimeLine = ({taskId}) => {

    const [timelineList, setTimelineList] = useState([]);

    useEffect(() => {
        axios.get("/v1/api/timeline/" + taskId).then(resp => {
            setTimelineList(resp.data.Timeline);
        })
    }, []);

  return(
      <div className="tl-parent-cont padd-20">
          <div className="font-heading comm-head">
              <span>Timeline</span>
          </div>
      </div>
  );
}

export default TimeLine;