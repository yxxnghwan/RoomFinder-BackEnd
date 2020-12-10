use roomfinderdb;

CREATE TABLE ACCOUNT(
	email varchar(40) PRIMARY KEY,
    password varchar(100) NOT NULL,
    account_type varchar(10) NOT NULL,
    CONSTRAINT chk_account_type CHECK(account_type IN('store', 'user', 'admin'))
);


CREATE TABLE USER(
	email varchar(40) PRIMARY KEY,
    phone varchar(20),
    user_name varchar(20) NOT NULL,
	CONSTRAINT fk_USER_email FOREIGN KEY(email) REFERENCES ACCOUNT(email) ON DELETE CASCADE
);

CREATE TABLE STORE(
	email varchar(40) PRIMARY KEY,
    store_name varchar(60) NOT NULL,
    company_id varchar(30) NOT NULL UNIQUE,
    address_line varchar(200) NOT NULL,
    telephone varchar(20),
    operating_time varchar(50) default "-",
    description varchar(1500),
    representing_image_extension varchar(10) NOT NULL,
    CONSTRAINT fk_STORE_email FOREIGN KEY(email) REFERENCES ACCOUNT(email) ON DELETE CASCADE
);

CREATE TABLE STORE_IMAGE(
	store_image_seq int PRIMARY KEY AUTO_INCREMENT,
    store_email varchar(40),
    file_name varchar(100),
    CONSTRAINT fk_STORE_IMAGE_store_email FOREIGN KEY(store_email) REFERENCES ACCOUNT(email) ON DELETE CASCADE
);

CREATE TABLE ROOM(
	room_seq int PRIMARY KEY AUTO_INCREMENT,
    room_name varchar(25) NOT NULL,
    price_per_hour int NOT NULL,
    description varchar(1500),
    capacity int,
    directory_name varchar(100),
    representing_image_extension varchar(10) NOT NULL,
    store_email varchar(40),
    CONSTRAINT fk_ROOM_store_email FOREIGN KEY(store_email) REFERENCES ACCOUNT(email) ON DELETE CASCADE
);

CREATE TABLE ROOM_IMAGE(
	room_image_seq int PRIMARY KEY AUTO_INCREMENT,
    room_seq int,
    file_name varchar(100),
    CONSTRAINT fk_ROOM_IMAGE_room_seq FOREIGN KEY(room_seq) REFERENCES ROOM(room_seq) ON DELETE CASCADE
);

CREATE TABLE RESERVATION(
	reservation_seq int PRIMARY KEY AUTO_INCREMENT,
    start_time datetime NOT NULL,
    end_time datetime NOT NULL,
    user_email varchar(40),
    room_seq int,
    inserted_time datetime default now(),
    CONSTRAINT fk_RESERVATION_user_email FOREIGN KEY(user_email) REFERENCES ACCOUNT(email) ON DELETE SET null,
    CONSTRAINT fk_RESERVATION_room_seq FOREIGN KEY(room_seq) REFERENCES ROOM(room_seq) ON DELETE SET null
);

CREATE TABLE PAYMENT(
	reservation_seq int PRIMARY KEY,
    total_price int NOT NULL,
    payment_method varchar(20) default 'yet',
    payment_company_pay_id varchar(150),
    CONSTRAINT fk_PAYMENT_reservation_seq FOREIGN KEY(reservation_seq) REFERENCES RESERVATION(reservation_seq) ON DELETE cascade
);