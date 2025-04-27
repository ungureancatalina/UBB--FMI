export type Employee = {
    _id: string;
    name: string;
    lastCheckIn?: string;
};

export type Task = {
    _id: string;
    description: string;
    status: 'new' | 'in_progress' | 'done';
    employeeName: string;
};
