// Request DTOs
export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  phoneNumber?: string;
}

// Response DTOs
export interface LoginResponse {
  id: number;
  username: string;
}

export interface RegisterResponse {
  id: number;
  username: string;
  email: string;
  phoneNumber?: string;
  message: string;
}

export interface ErrorResponse {
  message: string;
}

// User Model
export interface User {
  id: number;
  username: string;
  email: string;
  phoneNumber?: string;
}
