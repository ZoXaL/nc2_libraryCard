use nc2_libraryCard;

create table if not exists `libraryCard` (
	`bookId` int(5) AUTO_INCREMENT,
	`bookTitle` varchar(80) NOT NULL,
	`bookAuthor` varchar(80) NOT NULL,
	`obtainDate` date,
	`returnDate` date,
	PRIMARY KEY(`bookId`),
	UNIQUE(`bookTitle`)
);
