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

**Result:** Borrow listing is now stable, readable, and production-ready with proper entity relationships and error handling.
