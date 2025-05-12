import { Employee, Task } from '../types';
import { input, tableBase, th, td, button } from '../styles/dashboardStyles';
import {errorText} from "../styles/checkinStyles.ts";

export const EmployeeTable = ({
                                  employees,
                                  onEmployeeClick
                              }: {
    employees: Employee[];
    onEmployeeClick: (id: string) => void;
}) => (
    <div>
        <h2 className="text-3xl font-bold mb-6 text-gray-800">Active Employees</h2>
        <table className={tableBase}>
            <thead className="bg-rose-300 font-bold text-black">
            <tr>
                <th className={th}>Name</th>
                <th className={th}>Time of Arrival</th>
            </tr>
            </thead>
            <tbody>
            {employees.map((emp) => (
                <tr
                    key={emp._id}
                    className="border-b hover:bg-rose-50 transition cursor-pointer"
                    onClick={() => onEmployeeClick(emp._id)}
                >
                    <td className={td}>{emp.name}</td>
                    <td className={td}>{emp.lastCheckIn || '—'}</td>
                </tr>
            ))}
            </tbody>
        </table>
    </div>
);



export const FilterSelect = ({ employees, filterEmployeeId, setFilterEmployeeId }: {
    employees: Employee[];
    filterEmployeeId: string;
    setFilterEmployeeId: (id: string) => void;
}) => (
    <div className="mt-10">
        <label className="block text-lg font-semibold mb-2 text-gray-700">Filtered after:</label>
        <select
            value={filterEmployeeId}
            onChange={(e) => setFilterEmployeeId(e.target.value)}
            className={input}
        >
            <option value="all">See All</option>
            {employees.map((emp) => (
                <option key={emp._id} value={emp._id}>{emp.name}</option>
            ))}
        </select>
    </div>
);


export const TaskTable = ({ tasks, onModify, onDelete }: {
    tasks: Task[];
    onModify: (task: Task) => void;
    onDelete: (id: string) => void;
}) => (
    <div>
        <h2 className="text-3xl font-bold mb-6 text-gray-800">Employee's Tasks</h2>
        <table className={tableBase}>
            <thead className="bg-rose-300 font-bold text-black">
            <tr>
                <th className={th}>Name</th>
                <th className={th}>Description</th>
                <th className={th}>Status</th>
                <th className={th + ' text-center'}>Actions</th>
            </tr>
            </thead>
            <tbody>
            {tasks.map((task) => (
                <tr key={task._id} className="border-b hover:bg-rose-50 transition">
                    <td className={td}>{task.employeeName}</td>
                    <td className={td}>{task.description}</td>
                    <td className={td + ' capitalize'}>{task.status}</td>
                    <td className={td + ' flex justify-center gap-4'}>
                        <button
                            onClick={() => onModify(task)}
                            className="px-6 py-2 bg-purple-800 hover:bg-purple-900 text-white rounded-xl text-lg font-medium"
                        >Modify</button>
                        <button
                            onClick={() => onDelete(task._id)}
                            className="px-6 py-2 bg-indigo-800 hover:bg-indigo-900  text-white rounded-xl text-lg font-medium"
                        >Delete</button>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    </div>
);

export const ModifyTaskForm = ({
                                   editingTask,
                                   employees,
                                   selectedEmployeeId,
                                   setEditingTask,
                                   setSelectedEmployeeId,
                                   onSubmit,
                                   error,
                               }: {
    editingTask: Task;
    employees: Employee[];
    selectedEmployeeId: string;
    setEditingTask: (task: Task | null) => void;
    setSelectedEmployeeId: (id: string) => void;
    onSubmit: () => void;
    error: string;
}) => (
    <div className="mt-10 p-6 bg-rose-100 rounded-2xl space-y-4 shadow-inner">
        <h3 className="text-2xl font-bold text-gray-800">Modify Task</h3>
        <input
            type="text"
            className={input}
            placeholder="Task description"
            value={editingTask.description}
            onChange={(e) => setEditingTask({ ...editingTask, description: e.target.value })}
        />
        <select
            className={input}
            value={selectedEmployeeId || editingTask._id}
            onChange={(e) => setSelectedEmployeeId(e.target.value)}
        >
            <option value="">-- Select Employee --</option>
            {employees.map((emp) => (
                <option key={emp._id} value={emp._id}>{emp.name}</option>
            ))}
        </select>
        <button className={button} onClick={onSubmit}>Done</button>
        {error && <p className={errorText}>{error}</p>}
    </div>
);
