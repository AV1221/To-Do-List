import { List, ListItem, ListItemIcon, Checkbox, ListItemText } from "@mui/material";
import type { FC } from "react";
import type { Task } from "../../Types/Task";
import { TaskStatus } from "../../Types/TaskStatus";

interface props {
    tasks: Task[];
    status: TaskStatus;
}

export const TaskList: FC<props> = ({tasks, status}) => {
  return (
    <List dense>
      {tasks.map((task) => task.status == status && (
        <ListItem key={task.id} disablePadding>
          <ListItemIcon>
            <Checkbox edge="start" checked={task.status == TaskStatus.DONE} tabIndex={-1} />
          </ListItemIcon>
          <ListItemText
            primary={task.title}
            sx={{
              textDecoration: task.status == TaskStatus.DONE ? "line-through" : "none",
              color: task.status == TaskStatus.DONE ? "text.secondary" : "text.primary",
            }}
          />
        </ListItem>
      ))}
    </List>
  );
};
