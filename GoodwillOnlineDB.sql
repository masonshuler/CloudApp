DROP TABLE IF EXISTS UserPhoto, ItemPhoto, Item, User;

CREATE TABLE User
(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    password_hash VARCHAR (256) NOT NULL,
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
    auth_code VARCHAR (15), 
    isAdmin BIT NOT NULL,
    username VARCHAR (32) NOT NULL,
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
    rating FLOAT (4,2) NOT NULL,
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
('Polka Dot Slim Fit Dress Shirt', 129.00, 0.00, 'T-Shirts', 'M', 'Material is cotton/silk, slim fit.', '2018-04-18'),
('Diesel Brothers Shirt', 16.98, 0.00, 'T-Shirts', 'S', 'Shirt with orange and skull logo from diesel brothers', '2018-04-18'),
('Aston Martin Racing Shirt', 44.99, 0.00, 'T-Shirts', 'L', 'Black shirt with logos', '2018-04-18'),
('PK Subban Playoff Hockey Shirt', 19.99, 0.00, 'T-Shirts', 'L', 'Black shirt with small hockey logo in the middle', '2018-04-18'),
('Biker Denim Jeans', 13.99, 0.00, 'Pants', 'XL', 'Short cut, mens ripped denim jeans', '2018-04-18'),
('Long Gym Sweatpants', 7.99, 0.00, 'Pants', 'XXL', 'Sporty sweatpants with white stripe on the side', '2018-04-18'),
('Military Tactical Combat Pants', 28.00, 0.00, 'Pants', 'S', 'Khaki colored military pants with many pockets', '2018-04-18'),
('Dark Navy Blue Khaki Pants', 11.99, 0.00, 'Pants', 'XS', 'Traditional dark navy blue khaki pants', '2018-04-18'),
('Hooded Color Block Corduroy Jacket', 39.99, 0.00, 'Coats & Jackets', 'XXXL', 'Multi-colored coat with green base and yellow/red accents', '2018-04-18'),
('North Face Cinder Fleece Jacket', 58.45, 0.00, 'Coats & Jackets', 'M', 'Black fleece jacket made by north face', '2018-05-01'),
('Alpine Swiss Mens Zach Knee Length Jacket', 29.99, 0.00, 'Coats & Jackets', 'XL', 'Long, knee-length, overcoat jacket made by Apline Swiss', '2018-05-01'),
('Under Armor UA Storm Jacket', 39.99, 0.00, 'Coats & Jackets', 'S', 'Lightweight waterproof jacket made by Under Armor', '2018-05-01'),
('Floral Print Smocked Tie Back', 42.99, 0.00, 'Dresses', 'XL', 'Sleevless, back tie blue floral dress from Juniors', '2018-05-01'),
('Floral Lace Bridesmain Dress', 26.99, 0.00, 'Dresses', 'XS', 'A short, blue, floral bridesmaid dress with v-neck', '2018-05-01'),
('Short Sleeve Solid V-Neck Dress', 13.83, 0.00, 'Dresses', 'M', 'Green, 100% cotton short sleeve solid v-neck tshirt dress', '2018-05-01'),
('Summer Loose Sleevless Sundress', 8.99, 0.00, 'Dresses', 'S', 'Summer cotton loose vest sleeveless beach sundress', '2018-05-01'),
('Crown and Ivy Floral High Blouse', 24.99, 0.00, 'Tops & Blouses', 'S', 'Black floral high low blouse with white background', '2018-05-01'),
('Blue and White Floral Spaghetti Strap', 12.00, 0.00, 'Tops & Blouses', 'L', 'Torrid womens top blue and white floral spaghetti strap tank top', '2018-05-01'),
('Live and Let Live Blouse', 12.71, 0.00, 'Tops & Blouses', 'XL', 'One world live and let live womens shirt multi-colored', '2018-05-01'),
('Black and Gray Shirt', 6.97, 0.00, 'Tops & Blouses', 'M', 'Medium GIOIA black and gray shirt', '2018-05-01'),
('V Neck Playsuit', 12.99, 0.00, 'Jumpsuits & Rompers', 'XXL', 'Summer v-neck playsuit casual long pant jumpsuit', '2018-05-01'),
('Boutique Cold Shoulder Jumpsuit', 42.00, 0.00, 'Jumpsuits & Rompers', 'S', 'NWT Boutique black small cold shoulder jumpsuit', '2018-05-01'),
('Strapless Casual Loose Black Jumpsuit', 11.57, 0.00, 'Jumpsuits & Rompers', 'S', 'Lady strapless casual loose black harem v playsuit', '2018-05-01'),
('Sequin Clubwear Sleeveless Jumpsuit', 18.79, 0.00, 'Jumpsuits & Rompers', 'M', 'Party overalls sleeveless jumpsuit', '2018-05-01'),
('Body Shaper High Waisted Leggings', 7.99, 0.00, 'Leggings', 'L', 'Alta womens seamless body shaper high waisted premium stretch leggings', '2018-05-01'),
('Columbia Womens Glacial Leggings', 19.99, 0.00, 'Leggings', 'L', 'Columbia womens glacial leggings', '2018-05-01'),
('Skinny High Waist Pencil Leggings', 9.99, 0.00, 'Leggings', 'XL', 'New skinny floral high waist stretchy penicl leggings', '2018-05-01'),
('Reebok Womens Sports Leggings', 12.99, 0.00, 'Leggings', 'S', 'Reebok sports leggings for athletic activities', '2018-05-01');

INSERT INTO ItemPhoto (extension, item_id) VALUES
('jpeg', 1),
('jpeg', 2),
('jpeg', 3),
('jpeg', 4),
('jpeg', 5),
('jpeg', 6),
('jpeg', 7),
('jpeg', 8),
('jpg', 9),
('jpeg', 10),
('jpeg', 11),
('jpeg', 12),
('jpeg', 13),
('jpeg', 14),
('jpeg', 15),
('jpeg', 16),
('jpeg', 17),
('jpeg', 18),
('jpeg', 19),
('jpeg', 20),
('jpeg', 21),
('jpeg', 22),
('jpeg', 23),
('jpeg', 24),
('jpeg', 25),
('jpeg', 26),
('jpeg', 27),
('jpeg', 28);

INSERT INTO User (password_hash, first_name, last_name , address1, city, state, zipcode , security_question, security_answer, email, phone_number, isAdmin, username) VALUES
('cd916028a2d8a1b901e831246dd5b9b4d3832786ddc63bbf5af4b50d9fc98f50', 'Good', 'Will', '1411 N Main St', 'Blacksburg', 'VA', '24060', 0, 'Blacksburg', 'admin@gmail.com', '8045162266', 1, 'admin');
/* FOR ADMIN TESTING user:admin password: password */

INSERT INTO User (password_hash, first_name, middle_name, last_name, address1, address2, city, `state`, zipcode, security_question, security_answer, email, phone_number, auth_code, isAdmin, username) VALUES 
('ba1c933e549aaf3869599deffd8a526e2f5c8e11dcd15c0da6fb7b14bd61b1cd', 'Scott', '', 'McGhee', '1411 N Main St', '', 'Blacksburg', 'VA', '24060', 0, 'Blacksburg', 'emcghee@vt.edu', '8045162266', NULL, true, 'McGhee'),
/* FOR SUBMISSION user:McGhee password:CSD@VaTech-1872 */
('6a182cf567519bd04b62299e00604fb38b266563e3e1651eec90cf6f24b71ece', 'Shuvo', '', 'Rahman', '1411 N Main St', '', 'Blacksburg', 'VA', '24060', 0, 'Blacksburg', 'shuvesta@vt.edu', '8045162266', NULL, true, 'Rahman'),
/* FOR SUBMISSION user:Rahman password:CSD@VaTech-1872 */
('977ed29985e6291eebd361519be9a17e7e384a298ed6ed006f1fcebe38e8a370', 'Mason', '', 'Shuler', '1411 N Main St', '', 'Blacksburg', 'VA', '24060', 0, 'Blacksburg', 'mshuler@vt.edu', '8045162266', NULL, true, 'Shuler'),
/* FOR SUBMISSION user:Shuler password:CSD@VaTech-1872 */
('b2f6aba1026a95d33fb216934bb7dbdbc94e78bc0d3d2940ae0a7cf7aa3650d2', 'Matt', '', 'Tuckman', '1411 N Main St', '', 'Blacksburg', 'VA', '24060', 0, 'Blacksburg', 'matt4@vt.edu', '8045162266', NULL, true, 'Tuckman'),
/* FOR SUBMISSION user:Tuckman password:CSD@VaTech-1872 */
('e051a9d36a1deb07a8a07fc0bb7e98852566651d77223004e4db5011e3b43359', 'Jordan', '', 'Kuhn', '1411 N Main St', '', 'Blacksburg', 'VA', '24060', 0, 'Blacksburg', 'jordan01@vt.edu', '8045162266', NULL, true, 'JordanKuhn');
/* FOR SUBMISSION user:JordanKuhn password:CSD@VaTech-1872 */

INSERT INTO UserPhoto (extension, user_id) VALUES
('png', 2),
('jpeg', 3),
('png', 4),
('png', 5),
('png', 6);