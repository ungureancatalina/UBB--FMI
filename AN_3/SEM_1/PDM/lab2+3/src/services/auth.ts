const TOKEN_KEY = 'auth_token';
const USERNAME_KEY = 'auth_user';

export function saveToken(token: string, username: string) {
    localStorage.setItem(TOKEN_KEY, token);
    localStorage.setItem(USERNAME_KEY, username);
}

export function getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
}

export function getUsername(): string | null {
    return localStorage.getItem(USERNAME_KEY);
}

export function isAuthenticated(): boolean {
    return !!getToken();
}

export function logout() {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USERNAME_KEY);
    window.location.href = '/login';
}

export async function login(username: string, password: string) {
    const res = await fetch('http://localhost:4000/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
    });
    if (!res.ok) {
        const msg = (await res.json().catch(() => ({})))?.error || 'Login failed';
        throw new Error(msg);
    }
    const data = await res.json();
    saveToken(data.token, username);
    localStorage.setItem('token', data.token);

}
