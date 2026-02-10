import { createSlice } from "@reduxjs/toolkit";
import { registerUser } from "./authThunks";

interface RegisterState {
  isSuccess: boolean;
  isLoading: boolean;
  error: string | null;
}

const initialRegisterState: RegisterState = {
  isSuccess: false,
  isLoading: false,
  error: null,
};

export const registerSlice = createSlice({
  name: "register",
  initialState: initialRegisterState,
  reducers: {
    resetRegisterState: (state) => {
      state.isSuccess = false;
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(registerUser.pending, (state) => {
        state.isLoading = true;
        state.error = null;
        state.isSuccess = false;
      })
      .addCase(registerUser.fulfilled, (state) => {
        state.isLoading = false;
        state.error = null;
        state.isSuccess = true;
      })
      .addCase(registerUser.rejected, (state, action) => {
        state.isLoading = false;
        state.error = action.payload || "Registration failed";
        state.isSuccess = false;
      });
  },
});

export const { resetRegisterState } = registerSlice.actions;

export default registerSlice.reducer;


