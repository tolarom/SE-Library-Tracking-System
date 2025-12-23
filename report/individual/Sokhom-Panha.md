# Group Members
1. Prave Vinuth  
2. Rom Tola  
3. Sokhom Panha  
4. Sreng Panha  

## Project Progression

### Project Name: Library Tracking System

### Week 2 Progress

#### 1. Database Schema Update
- Updated table schema  
- Changed ID data type from `VARCHAR` to `BIGINT` for better performance  

#### 2. JwtFilter
- Implemented JWT authentication filter  
- Extracted token from request header  
- Validated JWT and set authentication in `SecurityContext`  

#### 3. JwtProperties
- Configured JWT settings (secret key and expiration time)  
- Loaded values from `application.yml` / `application.properties`  

#### 4. JwtUtil
- Implemented JWT token generation and validation  
- Extracted username from token  
- Used by `AuthController` and `JwtFilter`  

#### 5. SecurityConfig
- Configured Spring Security rules and filters  
- Disabled CSRF for REST APIs  
- Defined public and protected endpoints  
- Integrated JWT-based authentication  
