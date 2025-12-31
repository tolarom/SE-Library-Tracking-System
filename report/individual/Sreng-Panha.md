### Entity Package
Located in `entity/`, it contains:
- `Book.java`
- `BorrowRecord.java`
- `User.java`
- `Role.java`

These entities represent the core data models of the library system and are mapped to database tables using JPA annotations.

---

### Repository Package
Located in `repo/`, it contains:
- `LibraryRepository.java`
- `BorrowRepository.java`
- `UserRepository.java`

These repositories extend Spring Data JPA interfaces and handle database operations such as save, find, update, and delete.

---

### Service Package
Located in `service/`, it contains:
- `LibraryService.java`
- `CustomUserDetailsService.java`

The service layer contains business logic and acts as a bridge between controllers and repositories.

---

## CRUD Implementation

I implemented **CRUD (Create, Read, Update, Delete)** operations mainly for the **library-related entities**, especially:

- **Book**
- **BorrowRecord**
- **User**

Each CRUD operation follows a layered approach:
- Entity defines data structure
- Repository handles database interaction
- Service processes business logic
- Controller exposes REST APIs

---

## CRUD Features Summary

The following operations are working:

- Add new books and users
- Retrieve book and user lists
- Retrieve records by ID
- Update existing records
- Delete records from the database
- Track borrow records for users

All operations follow REST API principles.

---

## Testing

- APIs were tested using **Postman**
- JSON request bodies were used for POST and PUT requests
- Database changes were verified after each operation
- CRUD operations worked correctly without errors

---

## Current Status

- Project structure properly organized  
- Core entities created  
- CRUD operations implemented   
- Application runs successfully  

---

## Next Steps

- Improve validation and error handling
- Add more business rules
- Enhance authorization rules
- Integrate frontend if required

---

## Conclusion

At this stage, the backend structure is complete and CRUD functionality for the main library entities is working correctly.  
This progress confirms that the project is on track and ready for further development.

