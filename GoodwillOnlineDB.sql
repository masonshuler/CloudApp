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
    is_admin BIT NOT NULL, 
    PRIMARY KEY (id)
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
    size VARCHAR (32) NOT NULL,
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

INSERT INTO Item (title, price, rating, category, size, description, date_published) VALUES
('Polka Dot Slim Fit Dress Shirt', 129.00, 5.00, 'T-Shirts', 'M', 'Material is cotton/silk, slim fit.', '2018-04-18'),
('Diesel Brothers Shirt', 16.98, 5.00, 'T-Shirts', 'S', 'Shirt with orange and skull logo from diesel brothers', '2018-04-18'),
('Aston Martin Racing Shirt', 44.99, 5.00, 'T-Shirts', 'L', 'Black shirt with logos', '2018-04-18'),
('PK Subban Playoff Hockey Shirt', 19.99, 5.00, 'T-Shirts', 'L', 'Black shirt with small hockey logo in the middle', '2018-04-18'),
('Biker Denim Jeans', 13.99, 5.00, 'Pants', 'XL', 'Short cut, mens ripped denim jeans', '2018-04-18'),
('Long Gym Sweatpants', 7.99, 5.00, 'Pants', 'XXL', 'Sporty sweatpants with white stripe on the side', '2018-04-18'),
('Military Tactical Combat Pants', 28.00, 5.00, 'Pants', 'S', 'Khaki colored military pants with many pockets', '2018-04-18'),
('Dark Navy Blue Khaki Pants', 11.99, 5.00, 'Pants', 'XS', '', '2018-04-18'),
('Hooded Color Block Corduroy Jacket', 39.99, 5.00, 'Coats & Jackets', 'XXXL', 'Multi-colored coat with green base and yellow/red accents', '2018-04-18');

INSERT INTO ItemPhoto (extension, item_id) VALUES
('jpeg', 1),
('jpeg', 2),
('jpeg', 3),
('jpeg', 4),
('jpeg', 5),
('jpeg', 6),
('jpeg', 7),
('jpeg', 8),
('jpg', 9);