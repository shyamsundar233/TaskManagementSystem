import "./StatsComponent.css";
import GraphComponent from "../GraphComponent/GraphComponent";
import React from "react";

const StatsComponent = ({title, className}) => {

    let classList = "padd-20 margin-10 graph-comp-wid graph-comp-col-1 " + className;

  return (
      <div className={classList}>
          <div className="font-sub-heading"> {title} </div>
          <GraphComponent/>
      </div>
  );
}

export default StatsComponent;