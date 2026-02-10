import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Link from "@mui/material/Link";
import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import Divider from "@mui/material/Divider";
import FacebookIcon from '@mui/icons-material/Facebook';
import TwitterIcon from '@mui/icons-material/Twitter';
import InstagramIcon from '@mui/icons-material/Instagram';

export default function Footer() {
  return (
    <Box component="footer" className="bg-gray-100 mt-12">
      <Container maxWidth="lg" className="py-10">
        <Grid container spacing={4}>

          {/* BRAND */}
          <Grid  size={{ xs: 12, md: 4 }}>
            <Typography variant="h6" fontWeight="bold" gutterBottom>
              ShopNow
            </Typography>
            <Typography variant="body2" color="text.secondary">
              Your trusted destination for quality products,
              fast delivery, and secure payments.
            </Typography>
          </Grid>

          {/* CUSTOMER SERVICE */}
          <Grid  size={{ xs: 6, md: 2 }}>
            <Typography variant="subtitle1" fontWeight="bold" gutterBottom>
              Support
            </Typography>
            <Box className="flex flex-col gap-1">
              <Link href="#" underline="hover">Help Center</Link>
              <Link href="#" underline="hover">Returns</Link>
              <Link href="#" underline="hover">Shipping</Link>
              <Link href="#" underline="hover">Track Order</Link>
            </Box>
          </Grid>

          {/* COMPANY */}
          <Grid  size={{ xs: 6, md: 2 }}>
            <Typography variant="subtitle1" fontWeight="bold" gutterBottom>
              Company
            </Typography>
            <Box className="flex flex-col gap-1">
              <Link href="#" underline="hover">About Us</Link>
              <Link href="#" underline="hover">Careers</Link>
              <Link href="#" underline="hover">Contact</Link>
            </Box>
          </Grid>
          {/* LEGAL */}
          <Grid  size={{ xs: 6, md: 2 }}>
            <Typography variant="subtitle1" fontWeight="bold" gutterBottom>
              Legal
            </Typography>
            <Box className="flex flex-col gap-1">
              <Link href="#" underline="hover">Privacy Policy</Link>
              <Link href="#" underline="hover">Terms of Service</Link>
              <Link href="#" underline="hover">Refund Policy</Link>
            </Box>
          </Grid>

          {/* SOCIAL */}
          <Grid  size={{ xs: 6, md: 2 }}>
            <Typography variant="subtitle1" fontWeight="bold" gutterBottom>
              Follow Us
            </Typography>
            <Box>
              <IconButton><FacebookIcon /></IconButton>
              <IconButton><TwitterIcon /></IconButton>
              <IconButton><InstagramIcon /></IconButton>
            </Box>
          </Grid>
        </Grid>

        <Divider className="my-6" />

        {/* COPYRIGHT */}
        <Typography
          variant="body2"
          color="text.secondary"
          align="center"
        >
          © {new Date().getFullYear()} ShopNow. All rights reserved.
        </Typography>
      </Container>
    </Box>
  );
}
