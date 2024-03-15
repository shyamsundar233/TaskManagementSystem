import "./GraphComponent.css";
import {VictoryAxis, VictoryChart, VictoryLine, VictoryTheme} from "victory";

const GraphComponent = ({data, options, classList}) => {

    classList = classList + " graph-comp-cont-1"

    data = [
        { x: 1, y: 0 },
        { x: 2, y: 3 },
        { x: 3, y: 1 },
        { x: 4, y: 2 },
        { x: 5, y: 0 }
    ];

    return (
        <div className={classList} id="graph-comp-cont">
            <VictoryChart
                theme={VictoryTheme.material}
                animate={{ duration: 1000 }}
                width={200}
                height={200}
                style={{
                    parent: {padding: "0px", height: "100px", width: "100px"}
                }}
            >
                <VictoryLine
                    style={{
                        data: { stroke: "transparent", fill: "#00BDD6FF", opacity: 0.9 },
                        grid: {stroke: "transparent"}
                    }}
                    data={data}
                    interpolation="natural"
                />
                <VictoryAxis style={{
                    axis: {stroke: "transparent"},
                    ticks: {stroke: "transparent"},
                    tickLabels: { fill:"transparent"}
                }} />
            </VictoryChart>
        </div>
    );
}

export default GraphComponent;