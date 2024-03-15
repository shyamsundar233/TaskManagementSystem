import "./StatsComponent.css";
import GraphComponent from "../GraphComponent/GraphComponent";
import React from "react";

const StatsComponent = ({title, value, graphData, className}) => {

    let classList = "padd-20 margin-10 graph-comp-wid graph-comp-col-1 " + className;

  return (
      <div className={classList}>
          <div className="font-sub-heading"> {title} </div>
          <div className="display-flex">
              <div className="stats-comp-value-pos font-bold padd-20 stats-comp-value-font">{value}</div>
              <GraphComponent data={graphData}/>
          </div>
      </div>
  );
}

export default StatsComponent;