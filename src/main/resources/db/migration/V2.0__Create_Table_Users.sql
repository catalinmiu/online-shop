CREATE TABLE Users (
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    username varchar(40),
    email varchar(100),
    user_password varchar(256),
	user_role int,
	enabled boolean default 1,
    FOREIGN KEY(user_role) REFERENCES Roles(id)
)