import Footer from "../components/footer";
import { Header } from "../components/header";
import { Box } from "@mui/material";

export function AppLayout({ children }: { children: React.ReactNode }) {
  return (
    <Box 
      sx={{ 
        display: 'flex', 
        flexDirection: 'column', 
        minHeight: '100vh' 
      }}
    >
      <Header />
      <Box 
        component="main" 
        sx={{ 
          flex: 1, 
          maxWidth: '1280px', 
          width: '100%',
          mx: 'auto', 
          px: 2,
          py: 3
        }}
      >
        {children}
      </Box>
      <Footer />
    </Box>
  );
}