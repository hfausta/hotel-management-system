DROP TABLE IF EXISTS BookingRecord;
DROP TABLE IF EXISTS DiscountRate;
DROP TABLE IF EXISTS Booking;
DROP TABLE IF EXISTS HotelRoom;
DROP TABLE IF EXISTS RoomType;
DROP TABLE IF EXISTS HotelUnavailability;
DROP TABLE IF EXISTS Hotel;
DROP TABLE IF EXISTS HotelOwner;
DROP TABLE IF EXISTS HotelManager;
DROP TABLE IF EXISTS Consumer;
DROP TABLE IF EXISTS "User";

create table "User" (
	id			serial,
	name		text,
	address		text,
	phoneNum	INTEGER,
	email		VARCHAR(100) NOT NULL,
	PRIMARY KEY (id)
);

create table HotelOwner (
	id			INTEGER,
	userName	text NOT NULL,
	password	text NOT NULL,
	PRIMARY KEY (id), 
	FOREIGN KEY (id) REFERENCES "User"(id),
	CONSTRAINT userNameOwner_integrity UNIQUE(userName)
);

create table HotelManager (
	id			INTEGER,
	userName	text NOT NULL,
	password	text NOT NULL,
	PRIMARY KEY (id), 
	FOREIGN KEY (id) REFERENCES "User"(id) ,
	CONSTRAINT userNameManager_integrity UNIQUE(userName)
);

create table Consumer (
	id			INTEGER,
	PRIMARY KEY (id), 
	FOREIGN KEY (id) REFERENCES "User"(id)
);

create table Hotel (
	id			serial,
	name text NOT NULL,
	managerID	INTEGER NOT NULL UNIQUE,
	ownerID		INTEGER NOT NULL,
	address		text,	
	phone		INTEGER,
	PRIMARY KEY (id),
	FOREIGN KEY (managerID) REFERENCES HotelManager(id),
	FOREIGN KEY (ownerID) REFERENCES HotelOwner(id)
);

create table HotelUnavailability (
	id 			serial,
	hotelID		INTEGER NOT NULL,
	status		text NOT NULL,
	start		DATE NOT NULL,
	"end"		DATE NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY	(hotelID) REFERENCES Hotel(id) 
);

create table RoomType (
	id 			text,
	numPeople	INTEGER NOT NULL,
	extraBed	BOOLEAN NOT NULL,
	priceRate	decimal NOT NULL,
	PRIMARY KEY	(id)
);

create table HotelRoom (
	id serial,
	hotelID		INTEGER,
	roomNum		INTEGER,
	"type"		text NOT NULL,
	status		text NOT NULL DEFAULT 'AVAILABLE',
	PRIMARY KEY	(id),
	FOREIGN KEY (hotelID) REFERENCES Hotel(id),
	FOREIGN KEY ("type") REFERENCES RoomType(id),
	UNIQUE (hotelID,roomNum)
);

create table DiscountRate (
	id			SERIAL,
	hotelID		INTEGER NOT NULL,
	roomType	TEXT NOT NULL,
	start		DATE NOT NULL,
	"end"		DATE NOT NULL,
	discRate	decimal NOT NULL,
	PRIMARY KEY	(id),
	FOREIGN KEY (hotelID) REFERENCES Hotel(id),
	FOREIGN KEY (roomType) REFERENCES RoomType(id)
);

create table Booking (
	id			SERIAL,
	hotelID		INTEGER NOT NULL,
	consumerID	INTEGER NOT NULL,
	start		DATE NOT NULL,
	"end"		DATE NOT NULL,
	PIN			VARCHAR(10) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY	(hotelID) REFERENCES Hotel(id),
	FOREIGN KEY (consumerID) REFERENCES Consumer(id)
);

create table BookingRecord (
	id serial,
	bookingID	INTEGER,
	hotelID INTEGER,
	roomType	TEXT,	
	extraBed	BOOLEAN DEFAULT FALSE,
	room		INTEGER DEFAULT NULL REFERENCES HotelRoom(id),
	rmnumber INTEGER DEFAULT NULL,
	start date not null,
	"end" date not null,
	price		decimal NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (bookingID) REFERENCES Booking(id),
	FOREIGN KEY (roomType) REFERENCES RoomType(id),
	FOREIGN KEY (hotelID) REFERENCES Hotel(id)
);

insert into "User" (name,address,phoneNum,email) values ('John','1 Anzac Pde., NSW 2035',040111111,'john@gmail.com');
insert into HotelOwner values (1,'john','password');

insert into "User" (name,address,phoneNum,email) values ('Mary','2 Anzac Pde., NSW 2035',0401111112,'mary@gmail.com');
insert into HotelManager values (2,'mary','password');

insert into "User" (name,address,phoneNum,email) values ('Eric','3 Anzac Pde., NSW 2035',040111113,'eric@gmail.com');
insert into HotelManager values (3,'eric','password');

insert into Hotel (name,managerID,ownerID,address,phone) values ('Elizabeth Branch',2,1,'1 Elizabeth St., NSW 2000',040111114);
insert into Hotel (name,managerID,ownerID,address,phone) values ('George Branch',3,1,'1 George St., NSW 2000',040111115);

insert into RoomType values ('Single Room (with 1 single bed)',1,false,70);
insert into RoomType values ('Twin Bed (2 single beds)',2,true,120);
insert into RoomType values ('Queen (1 double bed)',2,true,160);
insert into RoomType values ('Executive (1 double bed, more facilities than Queen)',2,true,200);
insert into RoomType values ('Suite (2 double beds, most luxurious)',4,true,400);

insert into HotelRoom (hotelID,roomNum,"type") values (1,1,'Single Room (with 1 single bed)');
insert into HotelRoom (hotelID,roomNum,"type") values (1,2,'Twin Bed (2 single beds)');
insert into HotelRoom (hotelID,roomNum,"type") values (1,3,'Twin Bed (2 single beds)');
insert into HotelRoom (hotelID,roomNum,"type") values (1,4,'Queen (1 double bed)');
insert into HotelRoom (hotelID,roomNum,"type") values (1,5,'Executive (1 double bed, more facilities than Queen)');
insert into HotelRoom (hotelID,roomNum,"type") values (1,6,'Suite (2 double beds, most luxurious)');

insert into HotelRoom (hotelID,roomNum,"type") values (2,1,'Single Room (with 1 single bed)');
insert into HotelRoom (hotelID,roomNum,"type") values (2,2,'Twin Bed (2 single beds)');
insert into HotelRoom (hotelID,roomNum,"type") values (2,3,'Twin Bed (2 single beds)');
insert into HotelRoom (hotelID,roomNum,"type") values (2,4,'Queen (1 double bed)');
insert into HotelRoom (hotelID,roomNum,"type") values (2,5,'Executive (1 double bed, more facilities than Queen)');
insert into HotelRoom (hotelID,roomNum,"type") values (2,6,'Suite (2 double beds, most luxurious)');
