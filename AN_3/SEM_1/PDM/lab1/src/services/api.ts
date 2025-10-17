// importare axios pentru a face cereri HTTP catre server
import axios from 'axios';

// un obiect axios cu setarile implicite, setare baza URL la serverul local
const api = axios.create({
    baseURL: 'http://localhost:4000/api',
});

export default api;
