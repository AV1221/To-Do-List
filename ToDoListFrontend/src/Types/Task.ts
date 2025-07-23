import type { TaskStatus } from "./TaskStatus";
import type { PartialUser } from "./User";

export interface Task {
  id: number;
  title: string;
  status: TaskStatus;
}

export interface FullTask extends Task {
  body: string;
  owner?: PartialUser;
  accomplishedBy: PartialUser | null;
  assignedUsers: PartialUser[];
  createdAt: string;
  accomplishedAt: string | null;
}
