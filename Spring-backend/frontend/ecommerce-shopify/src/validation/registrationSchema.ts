import * as yup from 'yup';
export const registrationSchema = yup.object({
    username : yup.string().required('Username is required').min(4, 'Username must be at least 4 characters'),
    email: yup.string().email('Invalid email format').required('Email is required'),
    password: yup.string().required('Password is required').min(6, 'Password must be at least 6 characters'),
    confirmPassword: yup.string().required('Confirm Password is required').oneOf([yup.ref('password')], 'Passwords must match'),
    phoneNumber: yup.string().matches(/^\+?[1-9]\d{1,14}$/, 'Invalid phone number format').notRequired(),
})