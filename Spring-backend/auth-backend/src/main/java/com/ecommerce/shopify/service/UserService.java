package com.ecommerce.shopify.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

// import org.springframework.data.domain.Example;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.shopify.model.User;
import com.ecommerce.shopify.repository.UserRepository;
import com.ecommerce.shopify.model.Orders;
import com.ecommerce.shopify.repository.OrdersRepository;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;

//@Service Manual bean instantiation & registration
@Service
public class UserService {
    private final UserRepository repo;
    private final OrdersRepository ordersRepo;
    private final PasswordEncoder passwordEncoder; //from SecurityConfig - bean already loaded
    
    @Value("${JWTsecretKey}")
    private String jwtSecretKey;

    public UserService(UserRepository repo, OrdersRepository ordersRepo, PasswordEncoder passwordEncoder){
        this.repo = repo;
        this.ordersRepo = ordersRepo;
        this.passwordEncoder = passwordEncoder; //from SecurityConfig - bean already loaded
    }

    public User save(User user){
        // Check if username already exists
        if (repo.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }
        
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //JpaRepository.save() generates and executes SQL INSERT statement
        return repo.save(user);
    }

    public List<User> findAll(){
        return repo.findAll();
    }

    public boolean authLoginByDb(String username, String pw){
        // Find user by username using database query (not in-memory filtering)
        return repo.findByUsername(username)
            .map(user -> passwordEncoder.matches(pw, user.getPassword()))
            .orElse(false);  // Return false if user not found or password doesn't match
    }
    
    public boolean userExists(String username){
        return repo.findByUsername(username).isPresent();  // Return false if user not found
    }

    public boolean jwtExists(String username){
        return repo.findByUsername(username)
            .map(user -> user.getJwt() != null)  // Check if JWT exists
            .orElse(false);  // Return false if user not found
    }
      // repo.findByUsername(username) queries the database like:
    // SELECT * FROM users WHERE username = 'alice'
    // and then returns an Optional<User>
    // .map(user -> passwordEncoder.matches(pw, user.getPassword())) user -> this is a lambda function
    //     ↑
    //     └─ This 'user' is the User object from the line above
    //        It's ALREADY the correct user for that username

    // passwordEncoder.matches(pw, user.getPassword()) does:
    // 1. Extracts the salt from user.getPassword() (the hashed pw from DB)
    // 2. Hashes the provided 'pw' with that salt
    // 3. Compares the two hashes

    // Example of BCrypt hashed password:
//     Stored in DB:  "$2a$10$N9qo8u/dV8VEWxKD$lQqNc8vI5hZA5eQo"
//                          ↑      ↑
//                        salt   hash

// Login attempt: "pass123"

// matches() does:
// 1. Extract salt: "N9qo8u/dV8VEWxKD"
// 2. Hash "pass123" with that salt → "$2a$10$N9qo8u/dV8VEWxKD$lQqNc8vI5hZA5eQo"
// 3. Compare: Both match! → returns true

public String JwtLoginAuth(String username, String pw){
    long nowMillis = System.currentTimeMillis();
    long expMillis = nowMillis + (60*60*1000);
    
    // Create a secure key from the secret
    SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    
    String jwt = Jwts.builder()
        .setSubject(username)                 // who the user is
        .setIssuedAt(new Date(nowMillis))     // issued time
        .setExpiration(new Date(expMillis))   // expiry time
        .signWith(key)                        // modern way (not deprecated)
        .compact();
    
    // Find the user and update JWT token
    // repo.findByUsername(username).ifPresent(user -> {
    //     user.setJwt(jwt);
    //     repo.save(user); 
    // });

    return jwt;

} 

// public boolean validateJwtForUser(String username, String jwtToken){
//     expiryTime = jwtToken.getExpiration();
//     if (expiryTime > 0){
//         return true;
//     }
//     else{
//         return false;
//     }
// }

public Orders addOrderToUser(Orders order){
    // Save and return the order
    return ordersRepo.save(order);
}

public List<Orders> getOrdersByUser(String username){
    return ordersRepo.findByUsername(username);
}
}
