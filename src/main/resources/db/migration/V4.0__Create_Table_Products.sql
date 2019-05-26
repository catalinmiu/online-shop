CREATE TABLE Products (
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title varchar(100),
    price float,
    description longtext,
	stock int,
	created_date datetime,
	category_id int,
	FOREIGN KEY(category_id) REFERENCES Categories(id)
	ON DELETE CASCADE
)