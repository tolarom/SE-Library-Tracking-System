CREATE TABLE role (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE app_user (
    id VARCHAR(20) PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE, 
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE app_user_roles (
    app_user_id VARCHAR(20) NOT NULL,
    role_id VARCHAR(20) NOT NULL,
    PRIMARY KEY (app_user_id, role_id),
    CONSTRAINT fk_app_user_roles_user
        FOREIGN KEY (app_user_id) REFERENCES app_user(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_app_user_roles_role
        FOREIGN KEY (role_id) REFERENCES role(id)
        ON DELETE CASCADE
);

CREATE TABLE book (
    id VARCHAR(20) PRIMARY KEY,
    isbn VARCHAR(20),
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    publisher VARCHAR(255),
    total_copies INT NOT NULL CHECK (total_copies >= 0),
    available_copies INT NOT NULL CHECK (available_copies >= 0),
    CHECK (available_copies <= total_copies) 
);

CREATE TABLE borrow_record (
    id VARCHAR(20) PRIMARY KEY,
    book_id VARCHAR(20) NOT NULL,
    user_id VARCHAR(20) NOT NULL, 
    borrowed_at TIMESTAMP,
    due_date TIMESTAMP NOT NULL,
    returned_at TIMESTAMP,
    status VARCHAR(20),
    is_overdue BOOLEAN,
    CONSTRAINT fk_borrow_record_book
        FOREIGN KEY (book_id) REFERENCES book(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_borrow_record_user
        FOREIGN KEY (user_id) REFERENCES app_user(id)
        ON DELETE CASCADE
);