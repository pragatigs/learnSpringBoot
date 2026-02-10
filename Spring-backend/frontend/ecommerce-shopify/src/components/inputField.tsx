import { TextField } from '@mui/material';
interface InputFieldProps {
    name: string;
    type?: string;
    placeholder?: string;
    label?: string;
    variant?: 'outlined' | 'filled' | 'standard';
    size?: 'small' | 'medium';
    color?: 'primary' | 'secondary' | 'error' | 'info' | 'success' | 'warning';
}

export function InputField({name,...rest}: InputFieldProps) {
    return (
        <TextField name={name} {...rest} />
    )
}