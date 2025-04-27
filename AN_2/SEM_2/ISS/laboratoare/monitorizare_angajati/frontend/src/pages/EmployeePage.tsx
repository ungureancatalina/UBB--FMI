import { wrapper, card, title, section, button, input, label } from '../styles/employeeStyles';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { Task } from '../types';
import EmployeeTaskTable from '../components/EmployeeTaskForm';
import { useNavigate } from 'react-router-dom';

const EmployeePage = () => {
    const [tasks, setTasks] = useState<Task[]>([]);
    const [selectedTaskId, setSelectedTaskId] = useState('');
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    const navigate = useNavigate();

    const fetchTasks = async () => {
        const res = await axios.get(`http://localhost:5000/api/task/employee/${user.id}`);
        setTasks(res.data);
    };

    const handleComplete = async () => {
        if (!selectedTaskId) return;
        await axios.delete(`http://localhost:5000/api/task/${selectedTaskId}`);
        setSelectedTaskId('');
        fetchTasks();
    };

    const handleInProgress = async () => {
        if (!selectedTaskId) return;
        await axios.put(`http://localhost:5000/api/task/${selectedTaskId}/in-progress`);
        fetchTasks();
    };

    const handleCheckOut = () => {
        navigate('/checkout');
    };

    useEffect(() => {
        fetchTasks();
    }, []);

    return (
        <div className={wrapper}>
            <div className={card}>
                <h1 className={title}>EMPLOYEE PAGE </h1>

                <h2 className={section}>My Tasks</h2>
                <EmployeeTaskTable
                    tasks={tasks}
                    selectedTaskId={selectedTaskId}
                    onSelect={setSelectedTaskId}
                />

                <div className="mt-12 w-full flex flex-col items-center gap-8">
                    <div className="w-full max-w-lg">
                        <label className={label}>Selected Task:</label>
                        <select
                            className={input + ' text-center'}
                            value={selectedTaskId}
                            onChange={(e) => setSelectedTaskId(e.target.value)}
                        >
                            <option value="">-- Select task --</option>
                            {tasks.map((task) => (
                                <option key={task._id} value={task._id}>
                                    {task.description}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="flex flex-col md:flex-row gap-6 w-full max-w-2xl justify-center">
                        <button onClick={handleComplete} className={button + ' w-full md:w-1/2 bg-green-700 hover:bg-green-800'}>
                            Set Task As Finished
                        </button>
                        <button onClick={handleInProgress} className={button + ' w-full md:w-1/2 bg-orange-700 hover:bg-orange-800'}>
                            Set Task As In Progress
                        </button>
                    </div>

                    <div className="mt-6">
                        <button onClick={handleCheckOut} className={button + ' px-12'}>
                            Check Out
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default EmployeePage;
