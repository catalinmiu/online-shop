CREATE TABLE Products (
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title varchar(40),
    price int,
    description varchar(256),
	stock int,
	date_created datetime,
	category_id int,
	FOREIGN KEY(category_id) REFERENCES Category(id)
)