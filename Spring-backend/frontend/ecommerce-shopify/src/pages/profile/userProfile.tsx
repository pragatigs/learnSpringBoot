import { Box, Typography } from "@mui/material"
import type { RootState, AppDispatch } from "../../app/store";
import { useDispatch } from "react-redux";
import { getProfile } from "../../features/authentication/authThunks";
import { useEffect } from "react";
export function UserProfile(){
    const dispatch = useDispatch<AppDispatch>()
     useEffect(() => {
    dispatch(getProfile())
  }, [dispatch])
    return (
        <Box>
            <Typography variant="h6">User Details are :  </Typography>
            <Typography variant="h6">Username </Typography>
        </Box>
    )
}