import { createBrowserRouter } from "react-router-dom";
import { MainLayout } from "../layout/layout";
import { TaskPage } from "../pages/TaskPage/TaskPage";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <MainLayout />,
    children: [
      { path: "/", element: <TaskPage /> },
    ],
  },
]);
