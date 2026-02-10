import { Formik, Form} from 'formik';
import { registrationSchema } from '../../validation/registrationSchema';
import { registerUser } from '../../features/authentication/authThunks';
import { useDispatch, useSelector } from 'react-redux';
import { type AppDispatch, type RootState } from '../../app/store';
import { useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import GoogleIcon from '@mui/icons-material/Google';
import { Typography, Box, TextField, Button, Paper, Alert, IconButton } from '@mui/material';

export interface RegisterProps {
    username: string;
    email: string;
    password: string;
    confirmPassword: string;
    phoneNumber?: string;
}

const initialValues: RegisterProps = { 
    username: '', 
    email: '', 
    password: '', 
    confirmPassword: '', 
    phoneNumber: '' 
}

export function Register() {
    const dispatch = useDispatch<AppDispatch>();
    const navigate = useNavigate();
    const { isLoading, error, isSuccess } = useSelector((state: RootState) => state.register);
    
    useEffect(() => {
        if (isSuccess) {
            setTimeout(() => {
                navigate('/login');
            }, 2000);
        }
    }, [isSuccess, navigate]);
    
    return (
        <Box sx={{ 
            display: 'flex', 
            justifyContent: 'center', 
            alignItems: 'center', 
            minHeight: '80vh',
            py: 4 
        }}>
            <Paper elevation={3} sx={{ p: 4, maxWidth: 500, width: '100%' }}>
                <Typography variant="h4" gutterBottom color="primary" sx={{ textAlign: 'center', mb: 3 }}>
                    Create Account
                </Typography>
                
                <Formik<RegisterProps>
                    initialValues={initialValues}
                    validationSchema={registrationSchema}
                    onSubmit={(values, { setSubmitting }) => {
                        // eslint-disable-next-line @typescript-eslint/no-unused-vars
                        const { confirmPassword, ...registerData } = values;
                        dispatch(registerUser(registerData)).finally(() => {
                            setSubmitting(false);
                        });
                    }}
                >
                    {({ isSubmitting, values, handleChange, handleBlur, touched, errors }) => (
                        <Form>
                            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2.5 }}>
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

                                <TextField
                                    fullWidth
                                    name="email"
                                    type="email"
                                    label="Email"
                                    placeholder="Enter your email"
                                    variant="outlined"
                                    size="medium"
                                    value={values.email}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                    error={touched.email && Boolean(errors.email)}
                                    helperText={touched.email && errors.email}
                                />

                                <TextField
                                    fullWidth
                                    name="password"
                                    type="password"
                                    label="Password"
                                    placeholder="Enter your password"
                                    variant="outlined"
                                    size="medium"
                                    value={values.password}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                    error={touched.password && Boolean(errors.password)}
                                    helperText={touched.password && errors.password}
                                />

                                <TextField
                                    fullWidth
                                    name="confirmPassword"
                                    type="password"
                                    label="Confirm Password"
                                    placeholder="Confirm your password"
                                    variant="outlined"
                                    size="medium"
                                    value={values.confirmPassword}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                    error={touched.confirmPassword && Boolean(errors.confirmPassword)}
                                    helperText={touched.confirmPassword && errors.confirmPassword}
                                />

                                <TextField
                                    fullWidth
                                    name="phoneNumber"
                                    label="Phone Number (Optional)"
                                    placeholder="Enter your phone number"
                                    variant="outlined"
                                    size="medium"
                                    value={values.phoneNumber}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                    error={touched.phoneNumber && Boolean(errors.phoneNumber)}
                                    helperText={touched.phoneNumber && errors.phoneNumber}
                                />

                                {error && (
                                    <Alert severity="error" sx={{ mt: 1 }}>
                                        {error}
                                    </Alert>
                                )}
                                
                                {isSuccess && (
                                    <Alert severity="success" sx={{ mt: 1 }}>
                                        Registration successful! Redirecting to login...
                                    </Alert>
                                )}

                                <Button 
                                    type="submit" 
                                    disabled={isLoading || isSubmitting} 
                                    variant="contained" 
                                    size="large"
                                    fullWidth
                                    sx={{ mt: 2, py: 1.5 }}
                                >
                                    {isLoading ? 'Creating Account...' : 'Register'}
                                </Button>

                                <Typography variant="body2" sx={{ textAlign: 'center', mt: 2 }}>
                                    Already have an account?{' '}
                                    <Link to="/login" style={{ color: '#1976d2', textDecoration: 'none' }}>
                                        Login here
                                    </Link>
                                </Typography>
                                <IconButton color="error" aria-label="register with google">
                                    <GoogleIcon />
                                </IconButton>
                                <Typography variant="body1" sx={{textAlign: 'center' }}>
                                    Register with Google
                                </Typography>
                            </Box>
                        </Form>
                    )}
                </Formik>
            </Paper>
        </Box>
    )
}