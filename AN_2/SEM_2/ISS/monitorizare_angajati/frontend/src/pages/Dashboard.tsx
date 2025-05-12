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
import { useNotifications } from '../hooks/useNotifications';
import NotificationPanel from '../components/NotificationPanel';
import { notificationCenter } from '../utils/NotificationCenter';

const Dashboard = () => {
    JSON.parse(localStorage.getItem('user') || '{}');
    useNotifications("employer");

    const [employees, setEmployees] = useState<Employee[]>([]);
    const [tasks, setTasks] = useState<Task[]>([]);
    const [taskDescription, setTaskDescription] = useState('');
    const [selectedEmployeeId, setSelectedEmployeeId] = useState('');
    const [editingTask, setEditingTask] = useState<Task | null>(null);
    const [filterEmployeeId, setFilterEmployeeId] = useState('all');
    const [error, setError] = useState('');

    const fetchEmployees = async () => {
        const res = await axios.get('http://localhost:5000/api/user/employees');
        setEmployees(res.data);
    };

    const fetchTasks = async () => {
        const url = filterEmployeeId === 'all'
            ? 'http://localhost:5000/api/task/all'
            : `http://localhost:5000/api/task/employee/${filterEmployeeId}`;
        const res = await axios.get(url);
        setTasks(res.data);
    };

    useEffect(() => {
        fetchEmployees();
    }, []);

    useEffect(() => {
        fetchTasks();
    }, [filterEmployeeId]);

    useEffect(() => {
        const handle = (msg: string) => {
            console.log("EMPLOYER got:", msg);
            if (msg.includes('task')) fetchTasks();
            if (msg.includes('checked')) fetchEmployees();
        };
        notificationCenter.subscribe("employer", handle);
        return () => notificationCenter.unsubscribe("employer", handle);
    }, []);

    const handleAssign = async () => {
        if (!taskDescription.trim()) {
            setError("Task description is required.");
            return;
        }
        if (!selectedEmployeeId) {
            setError("Please select an employee.");
            return;
        }
        await axios.post('http://localhost:5000/api/task', {
            employeeId: selectedEmployeeId,
            description: taskDescription
        });

        await fetchTasks();
        setTaskDescription('');
        setSelectedEmployeeId('');
    };

    const handleDelete = async (taskId: string) => {
        const task = tasks.find(t => t._id === taskId);
        if (task) {
            await axios.post('http://localhost:5000/api/notify', {
                userId: task.employeeName,
                message: `A task was deleted`
            });
        }
        await axios.delete(`http://localhost:5000/api/task/${taskId}`);
        await fetchTasks();
    };

    const handleSubmitUpdate = async () => {
        if (!editingTask || !selectedEmployeeId) {
            setError("Please select a task and employee.");
            return;
        }

        await axios.put(`http://localhost:5000/api/task/${editingTask._id}`, {
            description: editingTask.description,
            employeeId: selectedEmployeeId,
        });

        await axios.post('http://localhost:5000/api/notify', {
            userId: selectedEmployeeId,
            message: `Task updated: "${editingTask.description}"`
        });

        await fetchTasks();
        setEditingTask(null);
        setSelectedEmployeeId('');
    };

    return (
        <div className={wrapper}>
            <div className={container}>
                <h1 className={heading}>EMPLOYER PAGE</h1>

                <NotificationPanel userId="employer" />

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
                        <label className="block text-xl font-bold mb-3 text-gray-700" htmlFor="employee-select">Select Employee:</label>
                        <select id="employee-select"
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
                        onSubmit={handleSubmitUpdate}
                        error={error}
                    />
                )}

                {error && (
                    <p className="text-red-600 font-bold text-center mt-4" data-testid="error-message">
                        {error}
                    </p>
                )}

            </div>
        </div>
    );
};

export default Dashboard;
