import { combineEpics } from "redux-observable";
import { authEpic } from "../features/authentication/authEpic";

export const rootEpic = combineEpics(authEpic);
