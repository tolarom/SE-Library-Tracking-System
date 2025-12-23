# Task 1: Create Login / Logout

## Module: Security & Login  
**Status:** ⏳ In Progress (backend mostly completed, frontend not connected)

---

## 1. Folder: `authorize`

### AuthResponse
**Purpose:**  
- Represents the response sent to the client after a successful login use test on postman.  
- Contains user information and authentication token.

**Function:**  
- Sends authentication results and user role data back to the frontend.

---

### LoginRequest
**Purpose:**  
- Captures user input for login from the frontend.  

**Function:**  
- Used by the backend to verify credentials during login.

---

### LoginResponse
**Purpose:**  
- Encapsulates the result of a login attempt.  

**Function:**  
- Contains login status, token, and user info or error messages.

---

### RegisterRequest
**Purpose:**  
- Captures user input during registration.  

**Function:**  
- Used to create a new user in the system.

---

## 2. Task Updates

### RoleEntity
**Purpose:**  
- Represents user roles in the system (e.g., MEMBER, ADMIN).  

**Function:**  
- Defines roles and permissions for access control.

---

### UserEntity
**Purpose:**  
- Represents a user in the system.  

**Function:**  
- Maps user data to the database.  
- Supports role-based authentication.

---

### UserRepository
 
- Queries users by username for login .

---

## 3. Frontend Integration

### Login
**Status:**  
- Added but not yet connected to backend.

**Function:**  
- Provides the user interface for login.  
- Sends login data to the backend and receives authentication results.  
- Stores authentication token for session management.  
- Handles login errors and redirects users based on roles.

 

---

## 4. Summary of Completion

| Component       | Status                                    |
|-----------------|------------------------------------------|
| AuthResponse     | Completed                                |
| LoginRequest     | Completed                                |
| LoginResponse    | Completed                                |
| RegisterRequest  | Completed                                |
| RoleEntity       | Updated                                  |
| UserEntity       | Updated                                  |
| UserRepository   | Updated                                  |
| Frontend Login   | Added but not connected                  |
