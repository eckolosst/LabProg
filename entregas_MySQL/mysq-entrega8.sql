-- a - Realice un procedimiento en PostgreSQL con un cursor que posea un búsqueda diferente a NEXT y permita moverse dentro del mismo.
--  Utilizar comando MOVE.
CREATE FUNCTION obtenerPriUltiSolicitud() returns int
AS $$
  DECLARE cursor_solicitud CURSOR FOR SELECT id_solicitud, fecha FROM Solicitud_prestamo ORDER BY fecha;
  DECLARE primer_solicitud int;
  DECLARE primer_fecha date;
  DECLARE ultima_solicitud int;
  DECLARE ultima_fecha date;
BEGIN
  OPEN cursor_solicitud;
  FETCH FIRST cursor_solicitud INTO primer_solicitud, primer_fecha;
  MOVE LAST FROM cursor_solicitud;
  FETCH cursor_solicitud INTO ultima_solicitud, ultima_fecha;
  CLOSE cursor_solicitud;
  RETURN ultima_solicitud;
END
$$ language 'plpgsql'

-- b - Realizar un procedimiento en PostgreSQL con un cursor FOR UPDATE que modifique los resultados del cursor mediante el comando
-- UPDATE/DELETE WHERE CURRENT OF y liste algún resultado.
CREATE FUNCTION modificar_cursor(reemplazo varchar(10)) returns setof record
AS $$
  DECLARE re RECORD;
  cant INT DEFAULT 0;
  DECLARE cursor_persona CURSOR FOR SELECT nombre, apellido FROM Personas WHERE nombre='Rolo';
  DECLAR nomb varchar(30);
  DECLAR ape varchar(30);
BEGIN
  SELECT COUNT(*) into cant FROM Persona WHERE nombre='Rolo';
  IF (cant > 0) THEN
    OPEN cursor_persona;
    LOOP
      FETCH cursor_persona INTO nom, ape;
      EXIT WHEN NOT FOUND;
      UPDATE Persona SET nombre=reemplazo WHERE CURRENT OF cursor_persona;
      SELECT nom, ape INTO re WHERE CURRENT OF cursor_persona; --Si no anda podría hacerse fuera del loop sin el where pero antes de cerrar el cursor
    END LOOP;
    CLOSE cursor_persona;
  ELSE
    SELECT 'No existen ROLOS' into re;
  END IF;
  RETURN re;
END
$$ language 'plpgsql'

-- c - Realizar un procedimiento (utilizando cursores) que recorra la relación Cuota concatenando el id con la fecha de vencimiento, las
-- cuales deberán ser luego listadas.
-- MySQL
DELIMITER //
CREATE PROCEDURE listarCuotas()
BEGIN
  DECLARE id INT;
  DECLARE vence DATE;
  DECLARE cadena VARCHAR(255);
  DECLARE i int;
  DECLARE fin int DEFAULT 0;
  DECLARE cursor_cuota CURSOR FOR SELECT id_cuota, vencimiento FROM Cuota;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin=1;

  SET cadena = '';
  SET i = 0;

  OPEN cursor_cuota;
  REPEAT
    FETCH cursor_cuota INTO id, vence;
    SET i = i + 1;
    SET cadena = CONCAT(cadena,i,' - ',id,' vence: ',vence,'\n');

  UNTIL (fin=1)
  END REPEAT;

  CLOSE cursor_cuota;
  SELECT cadena;
END //
DELIMITER;

CALL listarCuotas();

-- d - Realizar un procedimiento (utilizando cursores) que recorra la relación solicitud préstamo y modifique el monto en un 30% más si
-- alguno de sus garantes  tienen más de 70 años  y en un 15%  si tienen entre 60 y 70 años.
DELIMITER //
CREATE PROCEDURE aumentarMontoGarantesAncianos()
BEGIN
  DECLARE id int;
  DECLARE fecha_nac date;
  DECLARE mont FLOAT;
  DECLARE fin int DEFAULT 0;
  DECLARE edad int;
  DECLARE cursor_solicitud CURSOR FOR SELECT id_solicitud, monto, fecha_nacimiento AS fecha_garante FROM Solicitud_prestamo NATURAL JOIN Garante NATURAL JOIN Persona;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin=1;

  OPEN cursor_solicitud;
  REPEAT
    FETCH cursor_solicitud INTO id, mont, fecha_nac;
    SELECT diferenciaFechas(NOW(),fecha_nac) INTO edad;
    IF (edad > 365*70) THEN
      UPDATE Solicitud_prestamo SET monto=mont+mont*0.3 WHERE id_solicitud=id;
    ELSEIF (edad > 365*60) THEN
      UPDATE Solicitud_prestamo SET monto=mont+mont*0.15 WHERE id_solicitud=id;
    END IF;

  UNTIL (fin=1)
  END REPEAT;

  CLOSE cursor_solicitud;
  SELECT 1;
END //
DELIMITER;

CALL aumentarMontoGarantesAncianos();

-- e - Realice un procedimiento que posea un parámetro de entrada (cantidad de años),  uno de salida (tasa interés) y dos de entrada/salida
-- (estado, monto), busque aquellos préstamos que hayan iniciado hace más tiempo que la cantidad de años ingresada, posean el mismo estado
-- y monto, y le cambie la tasa de interés a un 5% más de lo que ya tenía. Debe listar el estado, monto, tasa de interés actual y cantidad
-- modificados.
--MySQL
DELIMITER //
CREATE PROCEDURE actualizarPrestamos(IN anios, OUT add_tasa FLOAT, INOUT mont)
BEGIN
  DECLARE id int;
  DECLARE mont FLOAT;
  DECLARE tasa FLOAT;
  DECLARE i int;
  DECLARE cant int;
  DECLARE fin int DEFAULT 0;
  DECLARE cursor_prestamo CURSOR FOR SELECT id_prestamo, monto, tasaInteres FROM Prestamo p WHERE difereciaFechas(NOW(), p.fecha)/365 > anios AND p.monto=mont;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin=1;

  SELECT COUNT(*) INTO cant FROM Prestamo p WHERE difereciaFechas(NOW(), p.fecha)/365 > anios AND p.monto=mont;
  CREATE TEMPORARY TABLE re (temp_id_prestamo int, temp_monto FLOAT, temp_tasa FLOAT, temp_index int, temp_total int);

  OPEN cursor_solicitud;
  REPEAT
    SET i = i + 1;
    FETCH cursor_prestamo INTO id, mont, tasa;
    UPDATE Prestamo SET tasaInteres= tasaInteres + add_tasa WHERE id_prestamo=id;
    INSERT INTO re VALUES (id, mont, tasa, i, cant);
    
  UNTIL (fin=1)
  END REPEAT;

  CLOSE cursor_solicitud;
  SELECT * FROM re;
  DROP TEMPORARY TABLE re;
END //
DELIMITER;

-- f - Realizar un procedimiento que dados todos los datos necesarios para ingresar una nueva solicitud de préstamo, realice lo siguiente.
-- Si la persona que lo está solicitando ya ha ido beneficiario de:
    -- 1 - ningún préstamo, al monto total de la solicitud le haga un descuento del 5%.
    -- 2 - 2 o más préstamos, al monto total le haga un descuento del 10%.
    -- 3 - Tres o más préstamos, al monto total le haga un descuento del 15%.
