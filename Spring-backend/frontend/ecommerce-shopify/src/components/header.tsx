import {AppBar, IconButton, InputBase, Toolbar, Typography} from '@mui/material';
import LocalMallIcon from '@mui/icons-material/LocalMall';
import { Box } from '@mui/system';
import SearchIcon from '@mui/icons-material/Search';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { useAppSelector } from '../app/hooks';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import { Link } from 'react-router-dom';
import Badge from "@mui/material/Badge";
export function Header() {
  const isAuthenticated = useAppSelector((state) => state.auth.isAuthenticated);
    return (
       <AppBar position="static">
         <Toolbar>
            <IconButton edge="start" color="inherit" aria-label="logo" size="large">
              <LocalMallIcon />
            </IconButton>
           <Typography variant="h6">
             E-commerce Shopify
           </Typography>
           <Box sx={{ display: 'flex', alignItems: 'center', marginLeft: 'auto' }}>
            <IconButton color="inherit">
              <SearchIcon />
              </IconButton>
             <InputBase placeholder="Search Brands Products........" sx={{ color: 'inherit' }} />
           </Box>
           <IconButton color="inherit">
            {
              isAuthenticated ? <AccountCircleIcon /> : <Typography variant="body1"><Link to="/login" style={{ textDecoration: 'none', color: 'inherit' }}>Login</Link></Typography>
            }
            {/* <AccountCircleIcon /> */}
           </IconButton>
            <IconButton color="inherit"> 
            <FavoriteBorderIcon />
            </IconButton>
           <IconButton color="inherit"> 
            <Badge badgeContent={4} color="secondary">
            <ShoppingCartIcon />
            </Badge>
            </IconButton>
         </Toolbar>
       </AppBar>
    )
}