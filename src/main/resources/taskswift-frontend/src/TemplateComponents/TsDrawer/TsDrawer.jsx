import "./TsDrawer.css";
import {Drawer} from "@mui/material";

const TsDrawer = ({open, anchor, body, drawerClass}) => {
  return (
      <Drawer
          open={open}
          anchor={anchor}
          className={drawerClass}
          PaperProps={{className : "ts-drawer-root"}}
      >
          {body}
      </Drawer>
  );
}

export default TsDrawer;