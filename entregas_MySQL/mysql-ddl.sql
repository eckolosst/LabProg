CREATE TABLE Persona(
  tipo_doc varchar(3),
  nro_doc varchar(10),
  nombre varchar(20),
  apellido varchar(20),
  fecha_nacimiento DATE,
  estado_civil varchar(15) CHECK (estado_civil IN ('casado','soltero','comprometido')),
  domicilio varchar(30),
  email varchar(50),
  profesion varchar(20),
  empresa varchar(20),
  telefono int,
  PRIMARY KEY(tipo_doc, nro_doc));

CREATE TABLE Socio(
  id_socio int PRIMARY KEY AUTO_INCREMENT,
  tipo_doc varchar(3),
  nro_doc varchar(10),
  numero_socio int,
  FOREIGN KEY (tipo_doc,nro_doc) REFERENCES Persona(tipo_doc,nro_doc) on update cascade on delete cascade);

CREATE TABLE Garante(
  id_garante int PRIMARY KEY AUTO_INCREMENT,
  tipo_doc varchar(3),
  nro_doc varchar(10),
  FOREIGN KEY (tipo_doc,nro_doc) REFERENCES Persona(tipo_doc,nro_doc) on update cascade on delete cascade);

CREATE TABLE Periodo_afiliacion(
  id_socio int,
  id_periodo int,
  fecha_alta DATE,
  fecha_baja DATE,
  PRIMARY KEY(id_socio,id_periodo),
  FOREIGN KEY (id_socio) REFERENCES Socio(id_socio) on update cascade on delete restrict);

CREATE TABLE Solicitud_prestamo(
  id_solicitud int PRIMARY KEY AUTO_INCREMENT,
  id_garante int,
  id_socio int,
  fecha DATE,
  resultado BIT,
  monto FLOAT,
  FOREIGN KEY (id_socio) REFERENCES Socio(id_socio) on update cascade on delete restrict,
  FOREIGN KEY (id_garante) REFERENCES Garante(id_garante) on update cascade on delete restrict);

CREATE TABLE Tabla_de_referencia(
  id_tabla_referencia int PRIMARY KEY AUTO_INCREMENT,
  fecha_vigencia DATE,
  monto_max FLOAT,
  interes_por_mora FLOAT);

CREATE TABLE Prestamo(
  id_prestamo int PRIMARY KEY AUTO_INCREMENT,
  id_solicitud int NOT NULL,
  id_tabla_referencia int NOT NULL,
  fecha DATE;
  tasaInteres FLOAT,
  monto int,
  FOREIGN KEY (id_solicitud) REFERENCES Solicitud_prestamo(id_solicitud) on update cascade on delete restrict,
  FOREIGN KEY (id_tabla_referencia) REFERENCES Tabla_de_referencia(id_tabla_referencia) on update cascade on delete restrict);

CREATE TABLE Recibo_de_sueldo(
  id_recibo int PRIMARY KEY AUTO_INCREMENT,
  id_solicitud int NOT NULL,
  id_garante int,
  id_socio int,
  fecha DATE,
  monto FLOAT,
  FOREIGN KEY (id_solicitud) REFERENCES Solicitud_prestamo(id_solicitud) on update cascade on delete restrict,
  FOREIGN KEY (id_socio) REFERENCES Socio(id_socio) on update cascade on delete restrict,
  FOREIGN KEY (id_garante) REFERENCES Garante(id_garante) on update cascade on delete restrict);

CREATE TABLE Cuota(
  id_cuota int PRIMARY KEY AUTO_INCREMENT,
  capital FLOAT,
  interes FLOAT,
  vencimiento DATE);

CREATE TABLE Cuota_prestamo(
  id_cuota int PRIMARY KEY AUTO_INCREMENT,
  id_prestamo int NOT NULL,
  FOREIGN KEY (id_prestamo) REFERENCES Prestamo(id_prestamo) on update cascade on delete restrict,
  FOREIGN KEY (id_cuota) REFERENCES Cuota(id_cuota) on update cascade on delete restrict);

CREATE TABLE Cuota_socio(
  id_cuota int PRIMARY KEY AUTO_INCREMENT,
  id_socio int NOT NULL,
  FOREIGN KEY (id_socio) REFERENCES Socio(id_socio) on update cascade on delete restrict,
  FOREIGN KEY (id_cuota) REFERENCES Cuota(id_cuota) on update cascade on delete restrict);

CREATE TABLE Efectivo(
  id_pago int AUTO_INCREMENT,
  id_cuota int,
  monto FLOAT,
  fecha DATE,
  FOREIGN KEY (id_cuota) REFERENCES Cuota(id_cuota) on update cascade on delete restrict,
  PRIMARY KEY(id_pago,id_cuota));

CREATE TABLE Transferencia(
  id_pago int AUTO_INCREMENT,
  id_cuota int,
  monto FLOAT,
  fecha DATE,
  cuenta_origen int,
  nro_transaccion int,
  FOREIGN KEY (id_cuota) REFERENCES Cuota(id_cuota) on update cascade on delete restrict,
  PRIMARY KEY(id_pago,id_cuota));

CREATE TABLE Tarjeta(
  id_pago int AUTO_INCREMENT,
  id_cuota int,
  monto FLOAT,
  fecha DATE,
  nro_tarjeta int,
  banco varchar(30),
  titular varchar(30),
  FOREIGN KEY (id_cuota) REFERENCES Cuota(id_cuota) on update cascade on delete restrict,
  PRIMARY KEY(id_pago,id_cuota));

CREATE TABLE Sorteo(
  id_sorteo int PRIMARY KEY AUTO_INCREMENT,
  id_socio int,
  fecha_sorteo DATE,
  FOREIGN KEY (id_socio) REFERENCES Socio(id_socio) ON UPDATE cascade ON DELETE restrict);
