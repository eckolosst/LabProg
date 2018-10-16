CREATE VIEW prestamosLocal
AS SELECT
  id_prestamo, id_solicitud, id_tabla_referencia, tasaInteres, monto, fecha
FROM Prestamo
WHERE fecha < '2018-08-31'
WITH LOCAL CHECK OPTION;

CREATE VIEW prestamosCascade
AS SELECT
   id_prestamo, id_solicitud, id_tabla_referencia, tasaInteres, monto, fecha
FROM Prestamo
WHERE fecha < '2018-08-31'
WITH CASCADED CHECK OPTION;

-- inciso c
insert into prestamosLocal
    (id_prestamo ,id_solicitud ,id_tabla_referencia ,tasaInteres ,monto ,fecha)
values (1, 1, 1, 60, 100000, '2018-01-01');

insert into prestamosLocal
    (id_prestamo ,id_solicitud ,id_tabla_referencia ,tasaInteres ,monto ,fecha)
values (2, 2, 1, 60, 500000, '2018-09-04');
-- mysql> insert into prestamosLocal
--     ->     (id_prestamo ,id_solicitud ,id_tabla_referencia ,tasaInteres ,monto ,fecha)
--     -> values (2, 2, 1, 60, 500000, '2018-09-04');
-- ERROR 1369 (HY000): CHECK OPTION failed 'mutual_sol_de_mayo.prestamosLocal'

-- inciso d
insert into prestamosCascade
    (id_prestamo ,id_solicitud ,id_tabla_referencia ,tasaInteres ,monto ,fecha)
values (3, 3, 1, 60, 100000, '2018-01-01');

insert into prestamosCascade
    (id_prestamo ,id_solicitud ,id_tabla_referencia ,tasaInteres ,monto ,fecha)
values (4, 4, 1, 60, 500000, '2018-09-04');
-- mysql> insert into prestamosCascade
--     ->     (id_prestamo ,id_solicitud ,id_tabla_referencia ,tasaInteres ,monto ,fecha)
--     -> values (4, 4, 1, 60, 500000, '2018-09-04');
-- ERROR 1369 (HY000): CHECK OPTION failed 'mutual_sol_de_mayo.prestamosCascade'

-- incisos e y f
CREATE OR REPLACE  VIEW prestamosLocal2
AS SELECT
    id_prestamo, id_solicitud, id_tabla_referencia, tasaInteres , monto, fecha
FROM prestamosLocal
WHERE fecha >= '2018-01-01'
WITH LOCAL CHECK OPTION;

CREATE OR REPLACE  VIEW prestamosCascade2
AS SELECT
    id_prestamo, id_solicitud, id_tabla_referencia, tasaInteres, monto, fecha
FROM prestamosCascade
WHERE fecha >= '2018-01-01'
WITH CASCADED CHECK OPTION;

-- g
UPDATE  prestamosLocal2
SET fecha = '2018-08-30'
WHERE id_prestamo=1;
--El cambio se efectuó en prestamoLocal y prestamosLocal2 pero no en Prestamo

UPDATE  prestamosLocal2
SET fecha = '2018-09-01'
WHERE id_prestamo=3;
--Actualizó la tupla y la quitó de las vistas porque no cumplian con la condicion

-- h
UPDATE  prestamosCascade2
SET fecha = '2018-08-30'
WHERE id_prestamo=3;
--Actualizó las 2 vistas cascade y la tabla prestamo

UPDATE  prestamosCascade2
SET fecha = '2018-09-01'
WHERE id_prestamo=3;
--Actualizó la tupla y la quitó de las vistas porque no cumplian con la condicion
