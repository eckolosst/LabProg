Transacciones en MySQL

1)
START TRANSACTION;
  UPDATE bombero set nombre='lucas';
COMMIT;

---------------

2)
START TRANSACTION;
  UPDATE bombero set nombre='fran' where nombre='lucas';
  INSERT into bombero(tipodoc,nrodoc,nombre,apellido,anios_servicio,localidad,genero) values
    ('dni','389200345','Lucas','Gimenez',4,'Neuquén','masculino');
COMMIT;

---------------
3)
START TRANSACTION;
  UPDATE bombero set nombre='lucia' where nombre='fran';
  INSERT into bombero(tipodoc,nrodoc,nombre,apellido,anios_servicio,localidad,genero) values
    ('dni','333555444','Lucia','Martinez',4,'Neuquén','masculino'),
ROLLBACK;

-----------------
4)
START TRANSACTION;
  UPDATE bombero set nombre='lucia' where nombre='fran';
  SAVEPOINT mipunto;
  UPDATE bombero SET nombre='camila' where nombre='lucia';
  ROLLBACK TO mipunto;
  INSERT into bombero(tipodoc,nrodoc,nombre,apellido,anios_servicio,localidad,genero) values
    ('dni','333555444','Lucia','Martinez',4,'Neuquén','masculino');
COMMIT;

----------------
5)
START TRANSACTION;
  UPDATE bombero set nombre='juliana' where nombre='lucia';
  SAVEPOINT mipunto;
  UPDATE bombero SET nombre='javier' where nombre='juliana';
  ROLLBACK TO mipunto;
  INSERT into bombero(tipodoc,nrodoc,nombre,apellido,anios_servicio,localidad,genero) values
  ('dni','333555444','Lucia','Martinez',4,'Neuquén','femenino');
ROLLBACK;

---------------

6)
START TRANSACTION;
  UPDATE bombero set nombre='juliana' where nombre='lucia';
  SAVEPOINT mipunto;
  UPDATE bombero SET nombre='javier' where nombre='juliana';
  ROLLBACK TO mipunto;
  INSERT into bombero(tipodoc,nrodoc,nombre,apellido,anios_servicio,localidad,genero) values
  ('dni','333555444','Lucia','Martinez',4,'Neuquén','masculino');
COMMIT;

----------------

7)
START TRANSACTION;
  UPDATE bombero set nombre='juliana' where nombre='lucia';
  SAVEPOINT mipunto;
  UPDATE bombero SET nombre='javier' where nombre='juliana';
  ROLLBACK TO mipunto;
  INSERT into bombero(tipodoc,nrodoc,nombre,apellido,anios_servicio,localidad,genero) values
  ('dni','444555666','Juliana','Gamargo',4,'Neuquén','femenino');
ROLLBACK;

-- Realice una transacción que inserte un préstamo, un pago asociado a ese préstamo  y dos pagos del mismo socio de cuotas de socio. Después
-- de cada paso se debe crear un punto de resguardo. Pruebe su funcionamiento colocando Rollback y Commit.
START TRANSACTION;
INSERT INTO Prestamo(id_solicitud, id_tabla_referencia, fecha, tasaInteres, monto) VALUES
  (6, 1, '2018-10-22', 1, 20000);
-- SAVEPOINT insert1;
-- INSERT INTO Efectivo(id_cuota, monto, fechaVencimiento) VALUES (?, 5000, '2018-12-15');
SAVEPOINT insert2;
INSERT INTO Efectivo(id_cuota, monto, fechaVencimiento) VALUES (2, 3000, '2018-10-02');
INSERT INTO Efectivo(id_cuota, monto, fechaVencimiento) VALUES (3, 3000, '2018-10-03');
COMMIT;
