CREATE TABLE Carts (
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    user_id int,
    paid_date datetime,
    FOREIGN KEY(user_id) REFERENCES Users(id)
    ON DELETE CASCADE
)