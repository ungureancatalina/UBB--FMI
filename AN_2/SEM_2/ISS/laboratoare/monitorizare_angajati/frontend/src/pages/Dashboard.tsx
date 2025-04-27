import { useEffect, useState } from 'react';
import axios from 'axios';
import {
    EmployeeTable,
    TaskTable,
    ModifyTaskForm,
    FilterSelect
} from '../components/DashboardForm';
import { Employee, Task } from '../types';
import {
    wrapper,
    container,
    heading,
    input,
    button
} from '../styles/dashboardStyles';

const Dashboard = () => {
    const [employees, setEmployees] = useState<Employee[]>([]);
    const [tasks, setTasks] = useState<Task[]>([]);
    const [taskDescription, setTaskDescription] = useState('');
    const [selectedEmployeeId, setSelectedEmployeeId] = useState('');
    const [editingTask, setEditingTask] = useState<Task | null>(null);
    const [filterEmployeeId, setFilterEmployeeId] = useState('all');

    useEffect(() => {
        axios.get('http://localhost:5000/api/user/employees')
            .then(res => setEmployees(res.data));
    }, []);

    useEffect(() => {
        const fetchTasks = async () => {
            const url = filterEmployeeId === 'all'
                ? 'http://localhost:5000/api/task/all'
                : `http://localhost:5000/api/task/employee/${filterEmployeeId}`;
            const res = await axios.get(url);
            setTasks(res.data);
        };
        fetchTasks();
    }, [filterEmployeeId]);

    const handleAssign = async () => {
        if (!taskDescription || !selectedEmployeeId) return;
        await axios.post('http://localhost:5000/api/task', {
            employeeId: selectedEmployeeId,
            description: taskDescription
        });
        const res = await axios.get(`http://localhost:5000/api/task/employee/${selectedEmployeeId}`);
        setTasks(res.data);
        setTaskDescription('');
        setSelectedEmployeeId('');
    };

    const handleDelete = async (taskId: string) => {
        await axios.delete(`http://localhost:5000/api/task/${taskId}`);
        const res = await axios.get(
            filterEmployeeId === 'all'
                ? 'http://localhost:5000/api/task/all'
                : `http://localhost:5000/api/task/employee/${filterEmployeeId}`
        );
        setTasks(res.data);
    };

    return (
        <div className={wrapper}>
            <div className={container}>
                <h1 className={heading}>EMPLOYER PAGE</h1>

                <EmployeeTable
                    employees={employees}
                    onEmployeeClick={(id) => setSelectedEmployeeId(id)}
                />

                <div className="grid grid-cols-1 md:grid-cols-2 gap-10 items-end">
                    <div>
                        <label className="block text-xl font-bold mb-3 text-gray-700">Task Description:</label>
                        <input
                            type="text"
                            value={taskDescription}
                            onChange={(e) => setTaskDescription(e.target.value)}
                            className={input}
                            placeholder="e.g. Prepare monthly report"
                        />
                    </div>
                    <div>
                        <label className="block text-xl font-bold mb-3 text-gray-700">Select Employee:</label>
                        <select
                            value={selectedEmployeeId}
                            onChange={(e) => setSelectedEmployeeId(e.target.value)}
                            className={input}
                        >
                            <option value="">-- Select --</option>
                            {employees.map((emp) => (
                                <option key={emp._id} value={emp._id}>{emp.name}</option>
                            ))}
                        </select>
                    </div>
                </div>

                <div className="text-center">
                    <button onClick={handleAssign} className={button}>Assign Task To Employee</button>
                </div>

                <FilterSelect
                    employees={employees}
                    filterEmployeeId={filterEmployeeId}
                    setFilterEmployeeId={setFilterEmployeeId}
                />

                <TaskTable
                    tasks={tasks}
                    onModify={setEditingTask}
                    onDelete={handleDelete}
                />

                {editingTask && (
                    <ModifyTaskForm
                        editingTask={editingTask}
                        employees={employees}
                        selectedEmployeeId={selectedEmployeeId}
                        setEditingTask={setEditingTask}
                        setSelectedEmployeeId={setSelectedEmployeeId}
                        onSubmit={async () => {
                            await axios.put(`http://localhost:5000/api/task/${editingTask._id}`, {
                                description: editingTask.description,
                                employeeId: selectedEmployeeId,
                            });
                            const res = await axios.get(`http://localhost:5000/api/task/employee/${selectedEmployeeId}`);
                            setTasks(res.data);
                            setEditingTask(null);
                            setSelectedEmployeeId('');
                        }}
                    />
                )}
            </div>
        </div>
    );
};

export default Dashboard;