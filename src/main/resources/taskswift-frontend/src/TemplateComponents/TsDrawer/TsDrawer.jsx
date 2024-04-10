import "./TsDrawer.css";
import {Drawer} from "@mui/material";

const TsDrawer = ({open, anchor, body, drawerClass, paperProps}) => {
    let paperPropsClass = "ts-drawer-root " + paperProps;
  return (
      <Drawer
          open={open}
          anchor={anchor}
          className={drawerClass}
          PaperProps={{className : paperPropsClass}}
      >
          {body}
      </Drawer>
  );
}

export default TsDrawer;