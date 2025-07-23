import type { Task } from "./Task";

export interface User {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    ownedTasks: Omit<Task, "owner">[];
    sharedTasks: Task[];
    allTasks: Task[];
}

export type PartialUser = Pick<User, "id" | "firstName" | "lastName" | "email">;