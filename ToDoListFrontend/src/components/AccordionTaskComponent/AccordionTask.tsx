import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { type FC } from "react";
import { TaskStatus } from "../../Types/TaskStatus";
import type { JSX } from "@emotion/react/jsx-runtime";

interface props {
  renderContent: (taskStatus: TaskStatus) => JSX.Element | undefined;
}
export const CompletedTask: FC<props> = ({renderContent}) => {

  return (
    <div>
      <Accordion>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel1-content"
          id="panel1-header"
        >
          <Typography component="span">Accomplished Tasks</Typography>
        </AccordionSummary>
        <AccordionDetails>
          {renderContent(TaskStatus.DONE)}
          </AccordionDetails>
      </Accordion>
    </div>
  );
};
