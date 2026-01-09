**Progress Report**

* Managed the **GitHub repository** and overall project structure.
* Created and maintained **branches** for team members.
* Reviewed and **merged pull requests**.
* Resolved **merge conflicts** and ensured code consistency.
* Enforced commit message and coding standards.
* Integrated major features into the **main branch**.
* Monitored progress and coordinated tasks among team members.

**Week5**
**Progress Report (Commit Summary)**

* **AuthenticationController**

  * Added role support using `Role` and `ERole`
  * Integrated `RoleRepository`
  * Auto-assigns **MEMBER** role during registration
  * Improved registration flow and security (password encoding, JWT)

* **RoleRepository**

  * New JPA repository to fetch roles by enum (`ERole`)
  * Enables role-based access control (RBAC)

* **application.properties**

  * Fixed MySQL connection URL (SSL, timezone, key retrieval)
  * Improves database connectivity reliability

* **login.html**

  * Updated login endpoint (`/login` → `/doLogin`)
  * Minor UI/UX text and styling improvements

**Overall:**
This make the user register into the app get the role of member.

**Progress Report (Borrow Module Update)**

* **BorrowController**

  * Updated list view to load borrow records **with book and user details**
  * Prevents lazy-loading / null reference issues

* **BorrowRecord Entity**

  * Added `@ManyToOne` relationships to **Book** and **User**
  * Enabled eager fetching for reliable display in views

* **BorrowRepository**

  * Added custom query and `@EntityGraph` to fetch book + user efficiently
  * Improves performance and avoids N+1 query problems

* **BorrowService**

  * Added `findAllWithBooksAndUsers()` for clean service-layer access

* **UI (borrows/list.html)**

  * Updated to display **book title** and **member name** instead of IDs
  * Safer null handling in Thymeleaf

* **Error Pages (404, 500, error)**

  * Improved navigation using `history.back()` for better UX


* **User Management (Admin)**

  * Users now **display roles** in the user list
  * Admin can **add new members with roles**
  * Admin can **edit existing users and update roles**
  * Role selection enforced (at least one role required)

* **UserController**

  * Integrated `RoleService`
  * Added role handling in create/edit flows
  * Validation for roles and passwords

* **Role System**

  * Added `RoleService` for clean role retrieval
  * Improved `Role` entity (`equals` / `hashCode`) for proper Set behavior
  * `User ↔ Role` many-to-many relationship stabilized

 **Result:**
The system now fully supports **role visibility, and editing**, enabling proper role-based user management from the librarian panel.

**Progress Report – Borrow Return Feature**

* **BorrowController**

  * Added **Return Book** action (`POST /admin/borrows/return/{id}`)
  * Handles success and error cases with user feedback
  * Enables marking a borrow record as returned

* **Borrow List UI**

  * Added **Return** button for active borrows
  * Integrated return action directly in the borrow table
  * Keeps delete action unchanged

* **Admin Dashboard**

  * Simplified dashboard layout
  * Cleaned unused “Recent Activity” section
  * Minor fixes to displayed book data bindings

* **AdminController**

  * Small adjustment to dashboard statistics handling

**Result:**
Librarian can now **return borrowed books directly from the borrow list**, improving borrow lifecycle management and overall usability.
