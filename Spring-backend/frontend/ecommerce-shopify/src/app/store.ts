import { configureStore } from "@reduxjs/toolkit";
import authReducer from "../features/authentication/loginSlice";
import registerReducer from "../features/authentication/RegisterSlice";

export const store = configureStore({
  reducer: {
    auth: authReducer,
    register: registerReducer,
  },
  devTools: true,
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;