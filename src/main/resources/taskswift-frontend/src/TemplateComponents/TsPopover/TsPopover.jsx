import {Popover} from "@mui/material";

const TsPopover = ({open, body, anchorEl, popoverClass, anchorOrigin, transformOrigin, paperProps}) => {
  return(
      <Popover
          open={open}
          anchorEl={anchorEl}
          className={popoverClass}
          anchorOrigin={anchorOrigin}
          transformOrigin={transformOrigin}
          PaperProps={paperProps}
      >
          {body}
      </Popover>
  )
}

export default TsPopover;