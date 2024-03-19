import "./TsDrawer.css";
import {Drawer} from "@mui/material";

const TsDrawer = ({open, anchor, body}) => {
  return (
      <Drawer
          open={open}
          anchor={anchor}
      >
          {body}
      </Drawer>
  );
}

export default TsDrawer;