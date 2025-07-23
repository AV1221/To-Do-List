import { TaskStatus } from "../Types/TaskStatus";
import type { PartialUser } from "../Types/User";
import type { Task } from "../Types/Task";

// Sample PartialUsers
const user1: PartialUser = {
  id: 1,
  firstName: "Alice",
  lastName: "Smith",
  email: "alice@example.com",
};

const user2: PartialUser = {
  id: 2,
  firstName: "Bob",
  lastName: "Johnson",
  email: "bob@example.com",
};

const user3: PartialUser = {
  id: 3,
  firstName: "Charlie",
  lastName: "Lee",
  email: "charlie@example.com",
};

// Sample tasks
export const demoTasks: Task[] = [
  {
    id: 101,
    title: "Buy groceries",
    body: "Milk, eggs, bread, and fruits",
    owner: user1,
    accomplishedBy: null,
    assignedUsers: [user2],
    status: TaskStatus.TODO,
    createdAt: "2025-07-21T08:30:00.000Z",
    accomplishedAt: null,
  },
  {
    id: 102,
    title: "Finish project report",
    body: "Compile results and send to manager",
    owner: user2,
    accomplishedBy: user2,
    assignedUsers: [user1, user3],
    status: TaskStatus.DONE,
    createdAt: "2025-07-20T10:00:00.000Z",
    accomplishedAt: "2025-07-22T14:00:00.000Z",
  },
  {
    id: 103,
    title: "Team meeting",
    body: "Discuss quarterly roadmap and priorities",
    owner: user3,
    accomplishedBy: null,
    assignedUsers: [user1, user2],
    status: TaskStatus.TODO,
    createdAt: "2025-07-19T09:00:00.000Z",
    accomplishedAt: null,
  },
  {
    id: 104,
    title: "Refactor authentication module",
    body: "Improve JWT handling and error boundaries",
    owner: user1,
    accomplishedBy: null,
    assignedUsers: [],
    status: TaskStatus.TODO,
    createdAt: "2025-07-18T11:45:00.000Z",
    accomplishedAt: null,
  },
];
