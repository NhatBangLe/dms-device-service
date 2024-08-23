CREATE TABLE device
(
    id       VARCHAR(100) NOT NULL,
    url      VARCHAR(255),
    username VARCHAR(100),
    password VARCHAR(100),
    type     SMALLINT     NOT NULL,
    CONSTRAINT pk_device PRIMARY KEY (id)
);