CREATE TABLE Users (
    userId INT IDENTITY PRIMARY KEY,
    name TEXT NOT NULL,
    age TINYINT NOT NULL,
    gender CHAR,
    budget SMALLMONEY NOT NULL,
    description TEXT,
    ON DELETE CASCADE
);

CREATE TABLE Postings (
    postingId INT IDENTITY PRIMARY KEY,
    userId INT NOT NULL,
    title VARCHAR(100),
    price SMALLMONEY NOT NULL,
    location TEXT NOT NULL,
    type VARCHAR(50) NOT NULL,
    description TEXT,
    PRIMARY KEY(postingId),
    FOREIGN KEY(userId) REFERENCES Users(userId)
    ON DELETE CASCADE
);

CREATE TABLE UserPostings (
    userId INT NOT NULL,
    postingId INT NOT NULL,
    PRIMARY KEY(userId, postingID),
    FOREIGN KEY(userId) REFERENCES Users(userId),
    FOREIGN KEY(postingId) REFERENCES Postings(postingId)
);

CREATE TABLE AttachedUsers (
    postingId INT NOT NULL,
    userId INT NOT NULL,
    PRIMARY KEY(postingId, userId),
    FOREIGN KEY(postingId) REFERENCES Postings(postingId),
    FOREIGN KEY(userId) REFERENCES Users(userId)
);

CREATE TABLE Requests (
    userId INT NOT NULL,
    postingId INT NOT NULL,
    PRIMARY KEY(userId, postingId),
    FOREIGN KEY(userId) REFERENCES Users(userId),
    FOREIGN KEY(postingId) REFERENCES Postings(postingId)
);

CREATE TABLE Matches (
   userId INT NOT NULL,
       postingId INT NOT NULL,
       PRIMARY KEY(userId, postingId),
       FOREIGN KEY(userId) REFERENCES Users(userId),
       FOREIGN KEY(postingId) REFERENCES Postings(postingId)
);


