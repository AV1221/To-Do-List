import { Box, Tab, Typography, CircularProgress } from "@mui/material";
import TabContext from "@mui/lab/TabContext";
import TabList from "@mui/lab/TabList";
import TabPanel from "@mui/lab/TabPanel";
import AssignmentIcon from "@mui/icons-material/Assignment";
import { useEffect, useState, type FC, type SyntheticEvent } from "react";
import { TaskList } from "../TaskListComponent/TaskList";
import type { Task } from "../../Types/Task";
import axiosInstance from "../../api/axios";
import { TaskStatus } from "../../Types/TaskStatus";
import { CompletedTask } from "../AccordionTaskComponent/AccordionTask";

export const TaskFilter: FC = () => {
  const [value, setValue] = useState("all-tasks");
  const [tasks, setTasks] = useState<Task[]>();

  const [userId] = useState<number>(1); //TODO:

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleChange = (_: SyntheticEvent, newValue: string) => {
    setValue(newValue);
  };

  useEffect(() => {
    const getTasks = async () => {
      setLoading(true);
      setError(null);

      try {
        const response = await axiosInstance.get<Task[]>(
          `/users/${userId}/${value}`
        );
        setTasks(response.data);
      } catch (err) {
        console.error("Error fetching tasks:", err);
        setError("Failed to load tasks.");
      } finally {
        setLoading(false);
      }
    };

    getTasks();
  }, [userId, value]);

  const renderContent = (taskStatus: TaskStatus = TaskStatus.TODO) => {
    if (loading) {
      return (
        <Box sx={{ display: "flex", justifyContent: "center", mt: 4 }}>
          <CircularProgress />
        </Box>
      );
    }

    if (error) {
      return (
        <Typography color="error" align="center" mt={2}>
          {error}
        </Typography>
      );
    }

    return tasks && <TaskList tasks={tasks} status={taskStatus} />;
  };

  return (
    <Box sx={{ width: "100%", maxWidth: 600, mx: "auto", mt: 4 }}>
      <TabContext value={value}>
        <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
          <TabList onChange={handleChange} variant="fullWidth">
            <Tab label="All" value="all-tasks" icon={<AssignmentIcon />} />
            <Tab label="Owned" value="owned-tasks" icon={<AssignmentIcon />} />
            <Tab
              label="Shared"
              value="shared-tasks"
              icon={<AssignmentIcon />}
            />
          </TabList>
        </Box>

        <TabPanel value="all-tasks">
          <Typography variant="h6" gutterBottom>
            All Tasks
          </Typography>
          {renderContent()}
        </TabPanel>

        <TabPanel value="owned-tasks">
          <Typography variant="h6" gutterBottom>
            Owned Tasks
          </Typography>
          {renderContent()}
        </TabPanel>

        <TabPanel value="shared-tasks">
          <Typography variant="h6" gutterBottom>
            Shared With Me
          </Typography>
          {renderContent()}
        </TabPanel>
      </TabContext>
      <CompletedTask renderContent={renderContent}/>
    </Box>
  );
};
