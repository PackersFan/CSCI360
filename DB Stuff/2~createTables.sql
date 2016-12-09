CREATE TABLE Voter(firstName varchar(128), lastName varchar(128), voterID int PRIMARY KEY auto_increment,  
pass varchar(255), registrationStatus int DEFAULT 1); 

CREATE TABLE Candidates(firstName varchar(128), lastName varchar(128), candidateID int PRIMARY KEY auto_increment, partyAffiliation varchar(128), voteCount int DEFAULT 0);

CREATE TABLE Ballot(ballotName varchar(255), ballotID int PRIMARY KEY, startTime varchar(255), endTime varchar(255)); 

CREATE TABLE votes(timeCasted varchar(255), candidateID int, FOREIGN KEY(candidateID) REFERENCES Candidates(candidateID));

CREATE TABLE pollAdmin(firstName varchar(128), lastName varchar(128), adminID int PRIMARY KEY auto_increment, pass varchar(255));