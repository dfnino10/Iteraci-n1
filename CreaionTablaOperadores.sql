CREATE TABLE OPERADORES(
id NUMBER(10,0),
nombre VARCHAR2(20),
registro NUMBER(20,0),
documento NUMBER(20,0),
categor�a VARCHAR2(20)
);

ALTER TABLE OPERADORES 
ADD CONSTRAINT categor�a CHECK(categor�a IN('Hotel', 'Hostal', 'Persona_Natural', 'Miembro_De_La_Comunidad', 'Vecino', 'Vivienda_Universitaria'));

ALTER TABLE OPERADORES
ADD PRIMARY KEY (id);

ALTER TABLE OPERADORES 
MODIFY nombre NOT NULL
MODIFY registro UNIQUE
MODIFY documento UNIQUE
MODIFY categor�a NOT NULL;