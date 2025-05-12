import { Task } from '../types';

interface Props {
    tasks: Task[];
    selectedTaskId: string;
    onSelect: (taskId: string) => void;
}

const EmployeeTaskTable = ({ tasks, selectedTaskId, onSelect }: Props) => {
    return (
        <table className="w-full border rounded-xl overflow-hidden text-xl shadow-md">
            <thead className="bg-rose-300 font-bold text-black">
            <tr>
                <th className="p-4">Description</th>
                <th className="p-4">Status</th>
            </tr>
            </thead>
            <tbody>
            {tasks.map((task) => (
                <tr
                    key={task._id}
                    className={`border-b cursor-pointer ${selectedTaskId === task._id ? 'bg-rose-100' : ''}`}
                    onClick={() => onSelect(task._id)}
                >
                    <td className="p-4">{task.description}</td>
                    <td className="p-4 capitalize">{task.status}</td>
                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default EmployeeTaskTable;