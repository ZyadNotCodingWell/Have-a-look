CREATE DATABASE CYBooks;
USE CYBooks;

CREATE TABLE Users (
    id VARCHAR(255) PRIMARY KEY,
    surname VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    dateOfBirth DATE,
    email VARCHAR(255),  
    status VARCHAR(50),
    currentlyBorrowedBooks INT DEFAULT 0,
    numberOfLateReturns INT DEFAULT 0,
    CHECK (currentlyBorrowedBooks >= 0),
    CHECK (numberOfLateReturns >= 0)
);

CREATE TABLE Librarians (
    id INT PRIMARY KEY,
    surname VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    login VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL  -- Ce champ doit être traité avec précaution.
);


CREATE TABLE Books (
    ISBN VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    theme VARCHAR(50),
    author VARCHAR(255) NOT NULL,
    NbOfCopies INT DEFAULT 0,
    NbOfAvailableCopies INT DEFAULT 0
);

CREATE TABLE Borrows (
    id int primary key,
    book_isbn VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    date_of_operation DATE NOT NULL,
    date_of_return DATE NOT NULL,
    status ENUM('Ongoing', 'LateReturned', 'Closed') DEFAULT 'Ongoing',
    FOREIGN KEY (book_isbn) REFERENCES Books(ISBN),
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

ALTER TABLE Users
MODIFY COLUMN status ENUM('currentlyBorrowing', 'blockedAccount', 'openToRun') NOT NULL DEFAULT 'openToRun';

-- requete qui permet d'afficher liste de retards à la demande(je suis pas sur pour * apres SELECT)
-- Le * c'est pour prendre toutes les données afin de générer des objets :)
SELECT *
FROM Borrows br
JOIN Users u ON br.user_id = u.id
JOIN Books b ON br.book_isbn = b.ISBN
WHERE br.date_of_return < CURDATE() AND br.status = 'Ongoing';
