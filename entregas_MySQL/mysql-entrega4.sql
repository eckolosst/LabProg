--Ejercicio 1
  --Inciso a
  CREATE VIEW prestamoConSoli
  AS SELECT  *
  FROM Solicitud_prestamo s INNER JOIN Prestamo p
  ON p.id_solicitud = s.id_solicitud
  WITH LOCAL CHECK OPTION;

  --Inciso  b
CREATE VIEW prestamoConSoli
AS SELECT  *
FROM Solicitud_prestamo JOIN Prestamo p
p.fecha AS fecha_aprobacion;

  --Inciso  c
INSERT INTO prestamoConSoli
(id_garante, id_socio, fecha, resultado, monto, fecha_aprobacion, tasaInteres, monto_aprobado)
values (5, 5, '2016-04-12', 1, 50000, '2016-04-15', 35, 50000);
INSERT INTO prestamoConSoli
(id_garante, id_socio, fecha, resultado, monto, tasaInteres)
values (5, 5, '2016-04-12', 1, 50000, 35);

INSERT INTO prestamoConSoli1
(id_garante, id_socio, fecha, resultado, monto, fecha_aprobacion, tasaInteres, monto)
values (6, 6, '2018-05-23', 1, 70000, '2018-05-27', 25, 45000);
INSERT INTO prestamoConSoli1
(id_garante, id_socio, fecha, resultado, monto, tasaInteres)
values (6, 6, '2018-05-23', 1, 70000, 25);

ERROR 1393 (HY000): Can not modify more than one base table through a join view 'mutual_sol_de_mayo.prestamoConSoli1'

CREATE VIEW prestamoTabla
AS SELECT  *
FROM Prestamo NATURAL JOIN Tabla_de_referencia
WITH LOCAL CHECK OPTION;

INSERT INTO prestamoTabla
(id_solicitud, fecha, tasaInteres, monto, fecha_vigencia, monto_max, interes_por_mora)
values (4, '2018-05-23', 25, 30000, '2018-05-23', 70000, 2000);

--Ejercicio 2
  --Inciso a
ALTER TABLE Garante ADD COLUMN cantidadDeSolicitudesSinDefinir integer NOT NULL DEFAULT 0;

  --Inciso b
CREATE TRIGGER addSolicitud AFTER INSERT ON Solicitud_prestamo
FOR EACH ROW
UPDATE Garante SET cantidadDeSolicitudesSinDefinir = cantidadDeSolicitudesSinDefinir + 1
WHERE NEW.id_garante = id_garante;

--Inciso c
CREATE TRIGGER updateSolicitud AFTER INSERT ON Prestamo
FOR EACH ROW
  UPDATE Solicitud_prestamo SET monto = p.monto
  WHERE NEW.id_solicitud = Solicitud_prestamo.id_solicitud;

CREATE TRIGGER updateGarante AFTER UPDATE ON Solicitud_prestamo
FOR EACH ROW
  UPDATE Garante SET cantidadDeSolicitudesSinDefinir = cantidadDeSolicitudesSinDefinir - 1
  WHERE NEW.id_garante = Garante.id_garante;

--Inciso d
CREATE TRIGGER habilitarBorradoSocio BEFORE DELETE ON Socio
FOR EACH ROW
BEGIN
  DELETE FROM Sorteo WHERE OLD.id_socio = Sorteo.id_socio
  DELETE FROM Periodo_afiliacion WHERE OLD.id_socio = Periodo_afiliacion.id_socio
  DELETE FROM Recibo_de_sueldo WHERE OLD.id_socio = Recibo_de_sueldo.id_socio
  DELETE FROM Solicitud_prestamo WHERE OLD.id_socio = Solicitud_prestamo.id_socio
  DELETE FROM Cuota_socio WHERE OLD.id_socio = Cuota_socio.id_socio
END;

CREATE TRIGGER habilitarBorradoSolicitud1 BEFORE DELETE ON Solicitud_prestamo
FOR EACH ROW
  DELETE FROM Prestamo WHERE OLD.id_solicitud = Prestamo.id_solicitud;

--Inciso e
CREATE TABLE Log_Transaccion(
  numero_operacion int PRIMARY KEY AUTO_INCREMENT,
  operacion varchar(10)
);

--Inciso f
CREATE TRIGGER addLogUp AFTER UPDATE ON Socio
FOR EACH ROW INSERT INTO Log_Transaccion VALUES ('UPDATE');

UPDATE Socio SET numero_socio=130 WHERE id_socio=5;

CREATE TRIGGER addLogDe AFTER DELETE ON Socio
FOR EACH ROW INSERT INTO Log_Transaccion VALUES ('DELETE');

CREATE TRIGGER addLogIN AFTER INSERT ON Socio
FOR EACH ROW INSERT INTO Log_Transaccion VALUES ('INSERT');

--Inciso g
--MySQL
CREATE TRIGGER impedirBorrado BEFORE DELETE ON Cuota
FOR EACH ROW
signal sqlstate '45000' set message_text = 'Prohibido eliminar registros de Cuota';
