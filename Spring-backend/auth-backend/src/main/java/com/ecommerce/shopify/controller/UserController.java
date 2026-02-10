package com.ecommerce.shopify.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.ecommerce.shopify.service.UserService;
import com.ecommerce.shopify.model.Orders;
import com.ecommerce.shopify.model.OrderRequest;
import com.ecommerce.shopify.model.User;
import com.ecommerce.shopify.security.JwtUtil;

@RestController // ← Singleton (ONE instance of UserController in the app context)
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final JwtUtil jwtUtil;

    public UserController(UserService service, JwtUtil jwtUtil){
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    //Serialization: Converting an object into a format that can be easily transmitted (like JSON).
    //Deserialization: Converting the transmitted format back into an object.
    @PostMapping("/create")
    // When a POST request arrives with JSON, Spring's @RequestBody annotation automatically:
    public ResponseEntity<?> create(@RequestBody User user){
        try {
            // passing the deserialized User object to this method.
            User savedUser = service.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/getAll")
    public List<User> getAll(){
        return service.findAll();
    }

    // @PostMapping("/login")
    // public boolean login(@RequestBody User user){
    //     return service.authLoginByDb(user.getUsername(), user.getPassword());
    // }

    @PostMapping("/jwtLogin")
    public ResponseEntity<?> jwtLogin(@RequestBody User user, HttpServletResponse resp){
        if (service.authLoginByDb(user.getUsername(), user.getPassword())){
            // if (service.jwtExists(user.getUsername())){
            //     return "User already logged in, please use existing JWT token.";
            // }
            // return service.JwtLoginAuth(user.getUsername(), user.getPassword());
            String jwtToken = service.JwtLoginAuth(user.getUsername(), user.getPassword());
            Cookie cookie = new Cookie("jwt", jwtToken);
            cookie.setMaxAge(3600);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");

            resp.addCookie(cookie);

            return ResponseEntity.ok("Login successful");
        }
        else{
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("New user! Please create an account first!"));
        }
    }
         
    
//     Login attempt
//     ↓
// Is password correct?
//     ├─ Yes → Does JWT exist?
//     │         ├─ Yes → Return "already logged in" message
//     │         └─ No → Generate & return new JWT
//     └─ No → Return "create account first" error message

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
            // Extract JWT from cookie
            System.out.println("------------------------------------------- fetching profile -------------------------------------------");
            Cookie[] cookies = request.getCookies();
            System.out.println("Cookies received: ");
            if (cookies == null) {
                System.out.println("No cookies found");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("No authentication token found"));
                }
            else {
            String jwtToken = null;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    System.out.println("*****************Cookie Name: " + cookie.getName() + ", Cookie Value: " + cookie.getValue());
                    if ("jwt".equals(cookie.getName())) {
                        jwtToken = cookie.getValue();
                        break;
                    }
                }
            }

            if (jwtToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("No authentication token found"));
            }

            String username = jwtUtil.extractUsernameFromToken(jwtToken);

            boolean userExists = service.userExists(username);
            if (userExists) {
                int expireTime = jwtUtil.getExpiration(jwtToken);
                if (expireTime > 0){
                return ResponseEntity.ok(username);
                }
                else{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Session expired"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("User not found"));
            }   
        }        
    }

    @PostMapping("/putOrders")
    public ResponseEntity<?> putOrders(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody OrderRequest request) {

    try {
        String username = jwtUtil.extractUsernameFromToken(authHeader);
        if(service.userExists(username)){
        Orders order = new Orders();
        order.setUsername(username); // from JWT
        order.setProduct(request.getProduct());
        order.setPrice(request.getPrice());
        order.setOrderId(request.getOrderId());

        Orders savedOrder = service.addOrderToUser(order);
        return ResponseEntity.ok(savedOrder);
        }
        else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("User not found"));
            }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("Invalid or expired token, please login again"));
    }
    }

    @GetMapping("/getOrdersByUser")
    public ResponseEntity<?> getOrders(@RequestHeader("Authorization") String authHeader){
        try {
            String username = jwtUtil.extractUsernameFromToken(authHeader);
            if(service.userExists(username)){
                List<Orders> orders = service.getOrdersByUser(username);
                return ResponseEntity.ok(orders);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("User not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("Invalid or expired token, please login again"));
        }
    }
    
    // Inner class for error response
    private static class ErrorResponse {
        private String error;
        
        public ErrorResponse(String error) {
            this.error = error;
        }
        
        public String getError() {
            return error;
        }
    }
}