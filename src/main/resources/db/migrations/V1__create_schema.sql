CREATE TABLE role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE app_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    email VARCHAR(255) UNIQUE
);

CREATE TABLE app_user_roles (
    app_user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (app_user_id, role_id),
    CONSTRAINT fk_app_user_roles_user
        FOREIGN KEY (app_user_id) REFERENCES app_user(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_app_user_roles_role
        FOREIGN KEY (role_id) REFERENCES role(id)
        ON DELETE CASCADE
);

CREATE TABLE book (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    isbn VARCHAR(20) UNIQUE,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    publisher VARCHAR(255),
    total_copies INT NOT NULL CHECK (total_copies >= 0),
    available_copies INT NOT NULL CHECK (available_copies >= 0),
    CHECK (available_copies <= total_copies)
);

CREATE TABLE borrow_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    book_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    borrowed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date TIMESTAMP NOT NULL,
    returned_at TIMESTAMP,
    status VARCHAR(20) NOT NULL,
    is_overdue BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_borrow_record_book
        FOREIGN KEY (book_id) REFERENCES book(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_borrow_record_user
        FOREIGN KEY (user_id) REFERENCES app_user(id)
        ON DELETE CASCADE,
    CHECK (returned_at IS NULL OR returned_at >= borrowed_at)
);
