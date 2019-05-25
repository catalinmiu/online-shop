CREATE TABLE Cart_Products (
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	product_id int,
    cart_id int,
	units int,
	FOREIGN KEY(product_id) REFERENCES Products(id) ON DELETE CASCADE,
	FOREIGN KEY(cart_id) REFERENCES Carts(id) ON DELETE CASCADE
)