import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { authApi } from "./authApi";
import type { LoginRequest, LoginResponse, RegisterRequest, RegisterResponse } from "./types";

export const loginUser = createAsyncThunk<
  LoginResponse,
  LoginRequest,
  { rejectValue: string }
>("auth/login", async (credentials, { rejectWithValue }) => {
  try {
    const user = await authApi.login(credentials);
    return user;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      return rejectWithValue(
        error.response?.data?.message || "Invalid credentials"
      );
    }
    return rejectWithValue(
      error instanceof Error ? error.message : "Login failed"
    );
  }
});

export const getProfile = createAsyncThunk<
  { username: string },
  void,
  { rejectValue: string }
>("auth/profile", async (_, { rejectWithValue }) => {
  try {
    const profile = await authApi.getProfile();
    console.log(profile)
    return profile;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      return rejectWithValue(
        error.response?.data?.error || "Failed to fetch profile"
      );
    }
    return rejectWithValue(
      error instanceof Error ? error.message : "Failed to fetch profile"
    );
  }
});

export const registerUser = createAsyncThunk<
  RegisterResponse,
  RegisterRequest,
  { rejectValue: string }
>(
  "auth/register",
  async (data, { rejectWithValue }) => {
    try {
      const response = await authApi.register(data);
      return response;
    } catch (error) {
      if (axios.isAxiosError(error)) {
        return rejectWithValue(
          error.response?.data?.message || "Registration failed"
        );
      }
      return rejectWithValue(
        error instanceof Error ? error.message : "Registration failed"
      );
    }
  }
);

