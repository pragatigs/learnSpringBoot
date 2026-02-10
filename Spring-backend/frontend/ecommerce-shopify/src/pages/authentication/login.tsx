import { Formik, Form } from "formik";
import { useDispatch, useSelector } from "react-redux";
import {
  loginUser,
  getProfile,
} from "../../features/authentication/authThunks";
import type { RootState, AppDispatch } from "../../app/store";
import { loginSchema } from "../../validation/loginSchema";
import { useEffect } from "react";
import {
  Box,
  Button,
  IconButton,
  Paper,
  TextField,
  Typography,
} from "@mui/material";
import GoogleIcon from "@mui/icons-material/Google";
import { Link,useNavigate } from "react-router-dom";

const Login = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { isAuthenticated, error } = useSelector(
    (state: RootState) => state.auth,
  );    
  // Fetch profile after successful login
  const navigate = useNavigate()
  useEffect(() => {
    if (isAuthenticated) {
      // dispatch(getProfile());
      navigate("/userProfile")
    }
  }, [isAuthenticated, dispatch]);

  return (
    <Box sx={{ textAlign: "center", mt: 12 }}>
      <Formik
        initialValues={{ username: "", password: "" }}
        validationSchema={loginSchema}
        onSubmit={(values, { resetForm }) => {
          dispatch(loginUser(values));
          console.log(values);
          resetForm();
        }}
      >
        {({ values, errors, touched, handleChange, handleBlur }) => (
          <Paper elevation={3} sx={{ p: 4, maxWidth: 400, margin: "0 auto" }}>
            <Typography variant="h4" gutterBottom color="primary">
              Login
            </Typography>
            <Form>
              <Box
                sx={{
                  display: "flex",
                  flexDirection: "column",
                  mb: 2,
                  textAlign: "center",
                }}
              >
                <Box
                  sx={{
                    display: "flex",
                    flexDirection: "row",
                    alignItems: "center",
                  }}
                >
                  <TextField
                    fullWidth
                    name="username"
                    label="Username"
                    placeholder="Enter your username"
                    variant="outlined"
                    size="medium"
                    value={values.username}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    error={touched.username && Boolean(errors.username)}
                    helperText={touched.username && errors.username}
                  />
                </Box>
                <Box sx={{ display: "flex", flexDirection: "row", mt: 2 }}>
                  <TextField
                    fullWidth
                    name="password"
                    type="password"
                    placeholder="Password"
                    variant="outlined"
                    size="medium"
                    label="Password"
                    value={values.password}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    error={touched.password && Boolean(errors.password)}
                    helperText={touched.password && errors.password}
                  />
                </Box>
              </Box>

              <Button
                type="submit"
                color="primary"
                variant="contained"
                fullWidth
                sx={{ p: 1 }}
              >
                SUBMIT
              </Button>

              {error && (
                <Typography variant="body1" color="error">
                  {error}
                </Typography>
              )}
              {isAuthenticated && (
                <Typography variant="h6" color="success">
                  Login successful
                </Typography>
              ) }

              <IconButton
                color="error"
                aria-label="login with google"
                sx={{ mt: 2 }}
              >
                <GoogleIcon />
              </IconButton>
              <Typography variant="body1">Login with Google</Typography>
              <Typography variant="body2" sx={{ mt: 2 }}>
                Don't have an account?{" "}
                <Link
                  to="/register"
                  style={{ textDecoration: "none", color: "#1976d2" }}
                >
                  Register here.
                </Link>
              </Typography>
            </Form>
          </Paper>
        )}
      </Formik>
    </Box>
  );
};

export default Login;
