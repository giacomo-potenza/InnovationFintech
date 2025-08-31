export interface User {
  id: number;
  matricola: string;
  nome: string;
  cognome: string;
  email?: string;
  ruolo: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  user: User;
}
