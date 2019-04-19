CREATE TABLE Carts (
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    user_id int,
    FOREIGN KEY(user_id) REFERENCES Users(id)
)