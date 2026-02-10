import axios from "axios";
import type { LoginRequest, LoginResponse, RegisterRequest, RegisterResponse } from "./types";

const API_BASE = "http://localhost:8080";

const authApiClient = axios.create({
  baseURL: API_BASE,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true, // Enable sending cookies
});

export const authApi = {
  login: async (credentials: LoginRequest): Promise<LoginResponse> => {
    const response = await authApiClient.post<LoginResponse>("/users/jwtLogin", credentials);
    return response.data; 
  },
  
  register: async (data: RegisterRequest): Promise<RegisterResponse> => {
    const response = await authApiClient.post<RegisterResponse>("/users/create", data);
    return response.data;
  },
  
  getProfile: async (): Promise<{ username: string }> => {
    console.log("Fetching profile from API");
    const response = await authApiClient.get("/users/profile");
    return response.data;
  },
};
