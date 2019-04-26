CREATE TABLE Products (
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title varchar(40),
    price float,
    description varchar(256),
	stock int,
	created_date datetime,
	category_id int,
	FOREIGN KEY(category_id) REFERENCES Categories(id)
)