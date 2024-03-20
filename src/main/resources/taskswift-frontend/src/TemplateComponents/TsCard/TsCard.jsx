import "./TsCard.css";
import {Card, CardActions, CardContent, CardHeader, CardMedia} from "@mui/material";

const TsCard = ({header, headerAvatar, content, actions, cardClass, headerClass, contentClass, actionClass}) => {
  return(
      <Card className={`card-cont-1 ${cardClass}`}>
          <CardHeader
              avatar={headerAvatar}
              title={header}
              className={headerClass}
          />
          <CardContent className={contentClass}>
              {content}
          </CardContent>
          <CardActions className={actionClass}>
              {actions}
          </CardActions>
      </Card>
  );
}

export default TsCard;