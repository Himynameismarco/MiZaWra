CREATE TABLE CLIENT(
	ID UUID DEFAULT public.uuid_generate_v4() PRIMARY KEY,
	FIRST_NAME VARCHAR(128),
	LAST_NAME VARCHAR(128),
	EMAIL VARCHAR(64) NOT NULL UNIQUE,
	PASSWORD TEXT
);

CREATE TABLE JOURNAL(
	ID UUID DEFAULT public.uuid_generate_v4() PRIMARY KEY,
	OWNER_ID UUID ON DELETE CASCADE,
	POSTED_DATE TIMESTAMP,
	MODE INT2,
	TITLE VARCHAR(256),
	BODY TEXT,
	CONSTRAINT CLIENT_FK FOREIGN KEY (OWNER_ID) REFERENCES CLIENT(ID)
);

CREATE TABLE PROMPT(
    ID UUID DEFAULT public.uuid_generate_v4() PRIMARY KEY,
    MODE INT2,
    PROMPT TEXT
);