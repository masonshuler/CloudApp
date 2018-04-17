DROP TABLE IF EXISTS UserPhoto, ItemPhoto, Item, User;

CREATE TABLE User
(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    password_hash VARCHAR (64) NOT NULL,
    first_name VARCHAR (32) NOT NULL,
    middle_name VARCHAR (32),
    last_name VARCHAR (32) NOT NULL,
    address1 VARCHAR (128) NOT NULL,
    address2 VARCHAR (128),
    city VARCHAR (64) NOT NULL,
    state VARCHAR (2) NOT NULL,
    zipcode VARCHAR (10) NOT NULL, /* e.g., 24060-1804 */
    security_question INT NOT NULL, /* Refers to the number of the selected security question */
    security_answer VARCHAR (128) NOT NULL,
    email VARCHAR (128) NOT NULL,
    phone_number VARCHAR (10),      
    PRIMARY KEY (id),
    isAdmin BIT NOT NULL
);

CREATE TABLE UserPhoto
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    extension ENUM('jpeg', 'jpg', 'png', 'gif') NOT NULL,
    user_id INT UNSIGNED,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);

CREATE TABLE Item
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    reserved_user INT UNSIGNED,
    title VARCHAR (64) NOT NULL,
    price FLOAT (7,2) NOT NULL,
    rating FLOAT (3,2) NOT NULL,
    category VARCHAR (32) NOT NULL,
    description VARCHAR (512),
    date_published DATE NOT NULL,
    FOREIGN KEY (reserved_user) REFERENCES User(id) ON DELETE CASCADE
);

CREATE TABLE ItemPhoto
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    extension ENUM('jpeg', 'jpg', 'png', 'gif') NOT NULL,
    item_id INT UNSIGNED,
    FOREIGN KEY (item_id) REFERENCES Item(id) ON DELETE CASCADE
);
