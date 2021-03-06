CREATE TABLE USERS (
    ID BIGSERIAL,
    CREATED_AT DATE NOT NULL,
    FULLNAME VARCHAR(100) NOT NULL,
    EMAIL VARCHAR(50) NOT NULL,
    PASSWORD VARCHAR(100),
    LOCATION VARCHAR(100),
    BIO VARCHAR(100),
    WEB VARCHAR(100),
    PICTURE VARCHAR(100),
    CONSTRAINT USERS_PK PRIMARY KEY (ID),
	CONSTRAINT USERS_EMAIL_KEY UNIQUE (EMAIL)
);